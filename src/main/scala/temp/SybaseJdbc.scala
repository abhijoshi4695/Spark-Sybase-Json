package temp

import java.sql.{Connection, DriverManager, SQLException, Statement}

object SybaseJdbc {
  var statement: Statement = _

  def main(args: Array[String]): Unit = {

//    val driver = "com.sybase.jdbc4.jdbc.SybDriver"

    val host = "abhishek-Lenovo-E40-80"
    val port = "2638"
    val dbName = "iqdemo"
    val url = "jdbc:sybase:Tds:" + host + ":" + port + "?ServiceName=" + dbName
    val username = "dba"
    val password = "password"

    var connection:Connection = null

    try {
//      Class.forName(driver)
      connection = DriverManager.getConnection(url, username, password)

      statement = connection.createStatement()
      selectQuery("SELECT * from Employees")

    } catch {
      case e: SQLException => e.printStackTrace()
    }
    connection.close()
  }

  def selectQuery(query: String): Unit = {
    println(query)

    val rs = statement.executeQuery(query)
    val rsmd = rs.getMetaData
    val colCount = rsmd.getColumnCount

    while (rs.next) {
      var i = 1

      while (i <= colCount) {
        val cell = rs.getObject(i)
        if (cell == null) print("NULL, ") else print(cell.toString + ", ")
        i = i + 1
      }
      println
    }
  }
}