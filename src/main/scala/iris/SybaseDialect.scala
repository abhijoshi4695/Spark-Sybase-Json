package iris

import org.apache.spark.sql.jdbc.{JdbcDialect, JdbcType}
import org.apache.spark.sql.types._

class SybaseDialect extends JdbcDialect {

  override def canHandle(url: String) : Boolean = url.startsWith("jdbc:sqlanywhere")

  override def getJDBCType(dt: DataType): Option[JdbcType] = dt match {
    case BooleanType => Option(JdbcType("BIT", java.sql.Types.BIT))
    case _ => None
  }
}
