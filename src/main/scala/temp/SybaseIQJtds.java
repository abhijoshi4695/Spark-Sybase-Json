package temp;

import java.sql.*;

public class SybaseIQJtds {
    public static void main(String[] args) {
        // Example Java Program - Sybase SQL Anywhere 12 Database Connectivity with JDBC 4.0
        // uid - user id
        // pwd - password
        // eng - Sybase database server name
        // database - sybase database name
        // host - database host machine ip
        try {
            Class.forName("net.sourceforge.jtds.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        String dburl = "jdbc:jtds:sybase://abhishek-Lenovo-E40-80:2638/iqdemo";

        // Connect to Sybase Database
        Connection con;
        try {
            con = DriverManager.getConnection(dburl, "dba", "password");
            Statement statement = con.createStatement();

            ResultSet rs = statement.executeQuery("select count(1) from Employees");

            System.out.println(rs.getInt(1));

            rs.close();
            statement.close();
            con.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
