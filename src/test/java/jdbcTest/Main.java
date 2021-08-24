package jdbcTest;

import java.sql.*;

public class Main {
    public static void main(String[] args) throws SQLException {
        String dbUrl = "jdbc:oracle:thin:@52.87.154.190:1521:xe";
        String dbUsername = "hr";
        String dbpassword = "hr";

        //creating connection
        Connection connection = DriverManager.getConnection(dbUrl,dbUsername,dbpassword);
        //creating statement object
        Statement statement = connection.createStatement();
        //run query and get the result in resultset object
        ResultSet resultSet = statement.executeQuery("Select * from regions");

        //move pointer to first row
        resultSet.next();
        System.out.println(resultSet.getString("region_name"));
        System.out.println(resultSet.getString(2));
        System.out.println(resultSet.getInt(1) + "-" + resultSet.getString("region_name"));

      resultSet.next();
        System.out.println(resultSet.getString("region_name"));
        System.out.println(resultSet.getString(2));
        System.out.println(resultSet.getInt(1) + "-" + resultSet.getString(2));

        while (resultSet.next()){
            System.out.println(resultSet.getInt(1) + "-" + resultSet.getString("region_name"));
        }
            ResultSet resultSetEmployees = statement.executeQuery("Select * from departments");

            while (resultSetEmployees.next()){
                System.out.println(resultSetEmployees.getString(1) + "-" + resultSetEmployees.getString(2) + "-" +
                        resultSetEmployees.getString(3) + "-" + resultSetEmployees.getString(4));
            }


        //close all connections
        resultSet.close();
        statement.close();
        connection.close();
    }
}
