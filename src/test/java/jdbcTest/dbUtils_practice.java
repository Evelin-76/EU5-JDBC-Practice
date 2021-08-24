package jdbcTest;

import org.testng.annotations.Test;
import utilities.DBUtils;

import java.util.List;
import java.util.Map;

public class dbUtils_practice {

    @Test
    public void test1(){

        //create connection
        DBUtils.createConnection();

        //get info from the list of map method
        List<Map<String, Object>> queryResultMap = DBUtils.getQueryResultMap("select first_name" +
                " from employees where rownum < 6");
        for (Map<String, Object> stringObjectMap : queryResultMap) {
            System.out.println(stringObjectMap.toString());
        }

        //get info of only one specific row
        Map<String, Object> rowMap = DBUtils.getRowMap("select first_name,last_name,salary,job_id" +
                " from employees where employee_id = 100");
        System.out.println(rowMap.toString());

        //close connection
        DBUtils.destroy();



    }
}
