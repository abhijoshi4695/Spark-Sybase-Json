package iris

import org.apache.spark.sql.{DataFrame, SaveMode, SparkSession}
import org.zalando.spark.jsonschema.SchemaConverter

import org.apache.commons.compress.compressors.gzip.GzipCompressorInputStream
import org.apache.commons.compress.archivers.tar.TarArchiveInputStream
import org.apache.spark.input.PortableDataStream
import scala.util.Try
import java.nio.charset._

import scala.io.Source

object IrisJson {

  def main(args: Array[String]) {
    val spark: SparkSession = SparkSession
      .builder()
      .appName("IrisBatch")
      .master("local[*]")
      .getOrCreate()

    /* Read Parameters */

    //Auto-infer schema
    /*
      PERMISSIVE : sets other fields to null when it meets a corrupted record and puts the malformed string into a new field configured by spark.sql.columnNameOfCorruptRecord. When a schema is set by user, it sets null for extra fields.
      DROPMALFORMED : ignores the whole corrupted records.
      FAILFAST: throws an exception when it meets corrupted records.
     */
    val readMode = "FAILFAST"
    val multiline = "true" //true, false
    //Auto-detection : UTF-8 (default), UTF-16BE, UTF-16LE, UTF-16, UTF-32BE, UTF-32LE, UTF-32
    val lineSep = "\n"
    val allowSingleQuotes = "false" //true, false
    val allowComments = "false" //true
    val columnNameOfCorruptRecord = "CorruptRecord" //PERMISSIVE mode
    val jsonFile = "/home/abhishek/JsonSample/multi.tar.xz"
//    val jsonFile = "hdfs://localhost:54321/usr/hive/warehouse/jsonCompressed/*"
    val schemaFile = ""
//    val jsonFile = "/home/abhishek/JsonSample/Product.json"
//    val schemaFile = "/home/abhishek/JsonSample/ProductSchema.json"

    /* Write parameters */
    val compression = "bzip2" //Auto-parse file with compressions bzip2, deflate, gzip, lz4, snappy
    val writeMode = SaveMode.Append //Overwrite, ErrorIfExists, Ignore
    val outputFile = "/home/abhishek/JsonSample/out.json"

    /* Both read and write */
    val dateFormat = "yyyy-MM-dd"
    val timestampFormat = "yyyy-MM-dd'T'HH:mm:ss.SSSZZ"

    val df = readArchivedFile(spark, jsonFile)
    df.show
//    val df = readJsonFile(spark, jsonFile, schemaFile, readMode, multiline, lineSep, dateFormat, timestampFormat, allowSingleQuotes, allowComments, columnNameOfCorruptRecord)
//    df.show()
//
//    writeJsonFile(df, outputFile, writeMode, compression, dateFormat, timestampFormat)
  }

  def readJsonFile(spark: SparkSession,
                   jsonFile: String,
                   schemaFile: String,
                   mode: String,
                   multiline: String,
                   lineSep: String,
                   dateFormat: String,
                   timestampFormat: String,
                   allowSingleQuotes: String,
                   allowComments: String,
                   columnNameOfCorruptRecord: String): DataFrame = {
    if (schemaFile == "") {
      spark.read
        .option("mode", mode)
        .option("multiline", multiline)
        .option("lineSep", lineSep)
        .option("dateFormat", dateFormat)
        .option("timestampFormat", timestampFormat)
        .option("allowSingleQuotes", allowSingleQuotes)
        .option("allowComments", allowComments)
        .option("columnNameOfCorruptRecord", columnNameOfCorruptRecord)
        .json(jsonFile)
    } else {
      val fileContents = Source.fromFile(schemaFile).getLines.mkString
      val schema = SchemaConverter.convertContent(fileContents)

      spark.read
        .schema(schema)
        .option("mode", mode)
        .option("multiline", multiline)
        .option("lineSep", lineSep)
        .option("dateFormat", dateFormat)
        .option("timestampFormat", timestampFormat)
        .option("allowSingleQuotes", allowSingleQuotes)
        .option("allowComments", allowComments)
        .option("columnNameOfCorruptRecord", columnNameOfCorruptRecord)
        .json(jsonFile)
    }
  }

  def writeJsonFile(df: DataFrame, outputFilePath: String, mode: SaveMode,
                    compression: String, dateFormat: String, timestampFormat: String): Unit = {
    df.coalesce(1).write.mode(mode)
      .option("compression", compression)
      .option("dateFormat", dateFormat)
      .option("timestampFormat", timestampFormat)
      .json(outputFilePath)
  }

  def extractFiles(ps: PortableDataStream, n: Int = 1024) = Try {
    val tar = new TarArchiveInputStream(new GzipCompressorInputStream(ps.open))
    Stream.continually(Option(tar.getNextTarEntry))
      .takeWhile(_.isDefined).flatten
      .filter(!_.isDirectory)
      .map(e => {
        Stream.continually {
          val buffer = Array.fill[Byte](n)(-1)
          val i = tar.read(buffer, 0, n)
          (i, buffer.take(i))
        }
          .takeWhile(_._1 > 0).flatMap(_._2)
          .toArray})
      .toArray
  }

  def decode(charset: Charset = StandardCharsets.UTF_8)(bytes: Array[Byte]) =
    new String(bytes, StandardCharsets.UTF_8)

  def readArchivedFile(spark: SparkSession, path: String): DataFrame = {
    val jsonRDD = spark.sparkContext.binaryFiles(path)
      .flatMapValues(x => extractFiles(x).toOption)
      .mapValues(_.map(decode()))

    spark.sqlContext.read.json(jsonRDD.map(_._2).flatMap(x => x))
  }
}
