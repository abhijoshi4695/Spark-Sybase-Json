package iris

import com.typesafe.config.ConfigFactory
import org.apache.spark.sql.jdbc.JdbcDialects
import org.apache.spark.sql.{SaveMode, SparkSession}

object IrisSybase {
  def main(args: Array[String]): Unit = {
    val spark: SparkSession = SparkSession
      .builder()
      .appName("IrisBatch")
      .master("local[*]")
      .getOrCreate()

    val config = ConfigFactory.load("application.conf").getConfig("sybase")

    val host = config.getString("host")
    val port = config.getString("port")
    val dbName = config.getString("dbName")

    val url = "jdbc:sybase:Tds:" + host + ":" + port + "?ServiceName=" + dbName
//    val url = "jdbc:sqlanywhere:uid=" + user + ";pwd=" + password + ";eng=" + host + ";database=" + dbName + ";links=tcpip(host=127.0.0.1:2638)"
//    val url = "jdbc:sqlanywhere:UserID=dba;Password=password;Host=" + host + ":" + port + ";DatabaseName=" + dbName
    val prop = new java.util.Properties
    prop.setProperty("user", config.getString("user"))
    prop.setProperty("password", config.getString("password"))

    val employee = spark.read.jdbc(url, "Employees", prop)
    employee.show(10)

    val dialect = new SybaseDialect
    JdbcDialects.registerDialect(dialect)
    employee.write.mode(SaveMode.Overwrite).jdbc(url, "SameEmployees", prop)
    JdbcDialects.unregisterDialect(dialect)
  }
}