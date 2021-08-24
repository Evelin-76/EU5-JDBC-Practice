package jdbcTest;

import org.testng.annotations.Test;

import java.sql.*;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;
import java.util.Map;

public class listOFMapExample {

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
        ResultSet resultSet = statement.executeQuery("select * from employees\n" +
                "where rownum < 6");

        //get the datebase related data inside the dbMetadata object
        DatabaseMetaData databaseMetaData = connection.getMetaData();

        System.out.println("User = " + databaseMetaData.getDatabaseProductName());
        System.out.println("Database Product Name = " + databaseMetaData.getDatabaseProductVersion());
        System.out.println("Driver name = " + databaseMetaData.getDriverName());
        System.out.println("Driver version = " + databaseMetaData.getDriverVersion());

        //get thje result object metadata
        ResultSetMetaData resultSetMetaData = resultSet.getMetaData();

        //list for keeping all rows a map
        List<Map<String, Object>> queryData = new ArrayList<>();

        Map<String,Object> row1 = new Hashtable<>();
        row1.put("first_name", "Steven");
        row1.put("last_name","King");
        row1.put("salary",24000);
        row1.put("job_id","AD_PRES");

        System.out.println(row1.toString());

        Map<String,Object> row2 = new Hashtable<>();
        row2.put("first_name", "Neena");
        row2.put("last_name","Kochhar");
        row2.put("salary",17000);
        row2.put("job_id","AD_VP");

        System.out.println(row2.get("salary"));

        //adding rows to list
        queryData.add(row1);
        queryData.add(row2);

        //get steven last name directly from the list
        System.out.println(queryData.get(0));
        System.out.println(queryData.get(0).get("last_name"));


         //close all connections
        resultSet.close();
        statement.close();
        connection.close();
    }

}
