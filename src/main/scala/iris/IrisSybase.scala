package iris

import java.sql.{Driver, DriverManager}

import org.apache.spark.sql.SparkSession

object IrisSybase {
  def main(args: Array[String]): Unit = {
    val spark: SparkSession = SparkSession
      .builder()
      .appName("IrisBatch")
      .master("local[*]")
      .getOrCreate()

//    val driver = "com.sybase.jdbc4.jdbc.SybDriver"
//    Class.forName(driver)

    val host = "abhishek-Lenovo-E40-80"
    val port = "2638"
    val dbName = "iqdemo"
    val user = "dba"
    val password = "password"
    val url = "jdbc:sybase:Tds:" + host + ":" + port + "?ServiceName=" + dbName
//    val url = "jdbc:sqlanywhere:uid=" + user + ";pwd=" + password + ";eng=" + host + ";database=" + dbName + ";links=tcpip(host=127.0.0.1:2638)"
//    val url = "jdbc:sqlanywhere:UserID=dba;Password=password;eng=" + host + ";database=" + dbName + ""
    val prop = new java.util.Properties
    prop.setProperty("user", user)
    prop.setProperty("password", password)
    System.setProperty("java.library.path", "/home/abhishek/SybaseIQ/IQ-16_1/lib64")
//    prop.setProperty("driver", driver)

    val employee = spark.read.jdbc(url, "Employees", prop)
    employee.show(10)

//    employee.write.mode(SaveMode.Overwrite).jdbc(url, "SameEmployees", prop)
  }
}