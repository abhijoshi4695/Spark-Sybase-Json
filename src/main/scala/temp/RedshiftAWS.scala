///*
//
//import com.amazonaws.auth.{AWSSessionCredentials, InstanceProfileCredentialsProvider}
//import org.apache.spark.sql.{DataFrame, SQLContext, SaveMode, SparkSession}
//
//object RedshiftAWS {
//
//  def main(args: Array[String]) {
//    val spark: SparkSession = SparkSession
//      .builder()
//      .appName("IrisBatch")
//      .master("local[*]")
//      .getOrCreate()
//
//    val tempMinioDir = "s3://abhijoshi4695-plotly/"
//
////    spark.conf.set("fs.s3a.access.key", "ACCESSKEY")
////    spark.conf.set("fs.s3a.secret.key", "SECRETKEY")
////    spark.conf.set("fs.s3a.endpoint", "http://127.0.0.1:9000")
////    spark.conf.set("fs.s3a.awsAccessKeyId", "AKIAICL532H7BQZEEGBA")
////    spark.conf.set("fs.s3a.awsSecretAccessKey","yxfxzR0EKRI/jffmMjfMRnRfrYfTMfm/3/xU4BYm")
////    spark.sparkContext.hadoopConfiguration.set("fs.s3a.access.key", "ACCESSKEY")
////    spark.sparkContext.hadoopConfiguration.set("fs.s3a.secret.key", "SECRETKEY")
////    spark.sparkContext.hadoopConfiguration.set("fs.s3a.endpoint", "http://127.0.0.1:9000")
////    spark.sparkContext.hadoopConfiguration.set("fs.s3a.awsAccessKeyId", "AKIAICL532H7BQZEEGBA")
////    spark.sparkContext.hadoopConfiguration.set("fs.s3a.awsSecretAccessKey","yxfxzR0EKRI/jffmMjfMRnRfrYfTMfm/3/xU4BYm")
//
//    //    spark.conf.set("fs.s3a.connection.ssl.enabled", "false")
//    //    spark.conf.set("forward_spark_s3_credentials", "true")
//
//    val jdbcUsername = "plotly"
//    val jdbcPassword = "Qmbdf#3DU]pP8a=CKTK}"
//    val jdbcHostname = "sql-connector-test.cfiaqtidutxu.us-east-1.redshift.amazonaws.com"
//    val jdbcPort = 5439
//    val jdbcDatabase = "plotly_datasets"
//    val jdbcUrl = s"jdbc:redshift://${jdbcHostname}:${jdbcPort}/${jdbcDatabase}?user=${jdbcUsername}&password=${jdbcPassword}"
//
//    val provider = new InstanceProfileCredentialsProvider();
//    val credentials: AWSSessionCredentials = provider.getCredentials.asInstanceOf[AWSSessionCredentials];
//    val token = credentials.getSessionToken;
//    val awsAccessKey = credentials.getAWSAccessKeyId;
//    val awsSecretKey = credentials.getAWSSecretKey
//
//    import spark.sqlContext.implicits._;
//    //    val df = spark.read.csv("s3a://plotly/PolicyLink.csv")
//    //    df.show
//
//    val salesDF = spark.sqlContext.read
//      .format("com.databricks.spark.redshift")
//      .option("url", jdbcUrl)
//      .option("tempdir", tempMinioDir)
//      .option("temporary_aws_access_key_id", awsAccessKey)
//      .option("temporary_aws_secret_access_key", awsSecretKey)
//      .option("temporary_aws_session_token", token)
//      .option("dbtable", "walmart_store_openings_1962_2006")
//      .load()
//
//    salesDF.show(10)
//    //    val sqlContext = spark.sqlContext
//    //
//    //    val sparkDF: DataFrame = spark.read.format("csv")
//    //      .option("header", "true")
//    //      .option("inferSchema", "true")
//    //      .load("/home/abhishek/PolicyLink.csv")
//    //
//    //    sparkDF.createOrReplaceTempView("policy")
//
//    //    val diamonds = sqlContext.read.format("csv")
//    //      .option("header", "true")
//    //      .option("inferSchema", "true")
//    //      .load("/home/abhishek/PolicyLink.csv").createOrReplaceTempView("diamonds")
//
//    //    sqlContext.sql("select * from diamonds limit 10")
//    //      .write
//    //      .format("com.databricks.spark.redshift")
//    //      .option("url", jdbcUrl)
//    //      .option("tempdir", tempMinioDir)
//    //      .option("dbtable", "diamonds")
//    //      .mode(SaveMode.Append)
//    //      .save()
//
//    //    sparkDF.write
//    //      .format("com.databricks.spark.redshift")
//    //      .option("url", jdbcUrl)
//    //      .option("dbtable", "policy")
//    //      .option("tempdir", tempMinioDir)
//    //      .mode("error")
//    //      .save()
//  }
//}*/
