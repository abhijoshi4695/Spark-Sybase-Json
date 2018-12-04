package temp;

import java.sql.*;

public class SybaseIQJconnect {
    public static void main(String[] args) {

        try {
            Class.forName("com.sybase.jdbc4.jdbc.SybDriver");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        String dburl = "jdbc:sybase:Tds:abhishek-Lenovo-E40-80:2638?ServiceName=iqdemo";

        // Connect to Sybase Database
        Connection con;
        try {
            con = DriverManager.getConnection(dburl, "dba", "password");
            Statement statement = con.createStatement();

            ResultSet rs = statement.executeQuery("select * from Employees");
            ResultSetMetaData metaData = rs.getMetaData();

            int columnCount = metaData.getColumnCount();

            while(rs.next()) {
                for(int columnIndex = 1; columnIndex <= columnCount; columnIndex++) {
                    Object object = rs.getObject(columnIndex);
                    System.out.printf("%s, ", object == null ? "NULL" : object.toString());
                }
                System.out.printf("%n");
            }
            rs.close();
            statement.close();
            con.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
