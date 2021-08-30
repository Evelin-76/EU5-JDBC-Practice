package jdbcTest;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import org.testng.annotations.Test;

import java.sql.*;

public class jdbc_example {

    String dbUrl = "jdbc:oracle:thin:@52.87.154.190:1521:xe";
    String dbUsername = "hr";
    String dbpassword = "hr";

    @Test
    public void test2() throws SQLException {
        //create connection
        Connection connection = DriverManager.getConnection(dbUrl,dbUsername,dbpassword);
        //create statement object
        Statement statement = connection.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_READ_ONLY);
        //run query and get results
        ResultSet resultSet = statement.executeQuery("select * from departments");

        //how many rows we have:
        //go to last row
        resultSet.last();
        //get the row count
        int rowCount = resultSet.getRow();
        System.out.println(rowCount);

        //print everything
        //we need to move the before first so we can use next to pring all queries
        resultSet.beforeFirst();
        while (resultSet.next()){
            System.out.println(resultSet.getString(2));
        }

        //close all connections
        resultSet.close();
        statement.close();
        connection.close();
    }
    @Test
    public void MetaDataExample() throws SQLException {
        //create connection
        Connection connection = DriverManager.getConnection(dbUrl,dbUsername,dbpassword);
        //create statement object
        Statement statement = connection.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_READ_ONLY);
        //run query and get results
        ResultSet resultSet = statement.executeQuery("select * from employees");

       //get the datebase related data inside the dbMetadata object
        DatabaseMetaData databaseMetaData = connection.getMetaData();

        System.out.println("User = " + databaseMetaData.getDatabaseProductName());
        System.out.println("Database Product Name = " + databaseMetaData.getDatabaseProductVersion());
        System.out.println("Driver name = " + databaseMetaData.getDriverName());
        System.out.println("Driver version = " + databaseMetaData.getDriverVersion());


        //get thje result object metadata
        ResultSetMetaData resultSetMetaData = resultSet.getMetaData();

        //hot manay columns we have?
        int colCount = resultSetMetaData.getColumnCount();
        System.out.println(colCount);

        //column names
      //  System.out.println(resultSetMetaData.getColumnName(1));
      //  System.out.println(resultSetMetaData.getColumnName(2));

        //print all the column name dynamically:
        //1.one way with for loop:
//        for(int i = 1; i <= colCount; i++){
//            System.out.println(resultSetMetaData.getColumnName(i));
//        }

        //2.another waywith while loop
        int count = 0;
        while(count++ != colCount) {
            System.out.println(resultSetMetaData.getColumnName(count++));
            count--;
        }

        //close all connections
        resultSet.close();
        statement.close();
        connection.close();
    }

}
