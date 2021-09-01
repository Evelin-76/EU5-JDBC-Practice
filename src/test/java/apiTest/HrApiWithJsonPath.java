package apiTest;

import io.restassured.http.ContentType;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import utilities.ConfigurationReader;
import java.util.ArrayList;
import java.util.List;
import static io.restassured.RestAssured.*;
import static org.testng.Assert.*;
import static io.restassured.RestAssured.baseURI;

public class HrApiWithJsonPath {

    Response response;
    @BeforeClass
    public void beforeClass(){

        //It is coming from RestAssured
        baseURI = ConfigurationReader.get("hr_api_url");
    }

    @Test
    public void test1(){

        response = get("/countries");

        //assign to jsonpath
        JsonPath jsonPath = response.jsonPath();
        String secondCountryName = jsonPath.getString("items.country_name[1]");
        System.out.println("Second country name: " + secondCountryName);

        //get one link base on index
        String scndLinkHref = jsonPath.getString("items.links[1].href[0]");
        System.out.println("Second link reference: " + scndLinkHref);

        //list of all links with json method
        int count = 0;
        for(int i = 0; i <=6; i++) {
            List<String> allLinks = jsonPath.getList("items.links.href["+ count++ +"]");

            for (String allLink : allLinks) {
                System.out.println("Link " + count + ":" + allLink);
            }

        }
        //get all country names where there region id is equal to 2
        List<String> contryNamesWithRegionIdIs2 = jsonPath.getList("items.findAll " +
                "{it.region_id == 2}.country_name");
        System.out.println("Country names where region id is 2: " + contryNamesWithRegionIdIs2);

    }
    @Test
    public void test2(){

        response = given().queryParams("limit", 107)
                .when().get("/employees");

        JsonPath jsonPath = response.jsonPath();
        //get all firstname of employees who are working as IT_PROG
        List<String> employeesWorkingIt_Prog =  jsonPath.getList("items.findAll " +
                "{it.job_id==\"IT_PROG\"}.first_name");
        System.out.println("Firstname employees of IT_PROG: "  + employeesWorkingIt_Prog);

        //filter by email employees earns more than 10k
        List<String> emailMore10 = jsonPath.getList("items.findAll" +
                "{it.salary > 10000}.email");
        System.out.println("Email of employees salary more than 100000: " + emailMore10);

        //filter first name of who is making max salary
        String maxSalary = jsonPath.getString("items.max " +
                "{it.salary}.first_name");
        String maxSalary2 = jsonPath.getString("items.max " +
                "{it.salary}.last_name");
        System.out.println("Employee earns max salary: " + maxSalary + " " +maxSalary2);

    }
}
