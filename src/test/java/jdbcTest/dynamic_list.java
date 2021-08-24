package jdbcTest;

import org.testng.annotations.Test;

import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class dynamic_list {


    String dbUrl = "jdbc:oracle:thin:@52.87.154.190:1521:xe";
    String dbUsername = "hr";
    String dbpassword = "hr";

    @Test
    public void MetaDataExample() throws SQLException {
        //create connection
        Connection connection = DriverManager.getConnection(dbUrl,dbUsername,dbpassword);
        //create statement object
        Statement statement = connection.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_READ_ONLY);
        //run query and get results
        ResultSet resultSet = statement.executeQuery("select * from countries\n" +
                "where rownum < 6");

        //get thje result object metadata
        ResultSetMetaData resultSetMetaData = resultSet.getMetaData();

        //list for keeping all rows a map
        List<Map<String, Object>> queryData = new ArrayList<>();

        //num or columns
        int numOfColumn = resultSetMetaData.getColumnCount();
        System.out.println(numOfColumn);

        //loop through each row
        while ((resultSet.next())) {
            Map<String, Object> row = new HashMap<>();

            //LOOPS FOR GET INFO-TWO POSSIBILITIES:
            //1.for loop:
//            for(int i = 1; i <= numOfColumn; i++){
//               row.put(resultSetMetaData.getColumnName(i),resultSet.getObject(i)) ;
//            }
            //2.while loop
            int counter = 0;
            while(counter++ != numOfColumn) {
                row.put(resultSetMetaData.getColumnName(counter), resultSet.getObject(counter));
            }

            //add your map to your list
            queryData.add(row);

            System.out.println(row.toString());
        }

        //close all connections
        resultSet.close();
        statement.close();
        connection.close();
    }

}
