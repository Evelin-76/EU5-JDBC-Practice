package apiTest;


import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import java.util.HashMap;
import java.util.Map;
import static io.restassured.RestAssured.baseURI;
import static io.restassured.RestAssured.given;
import static org.testng.AssertJUnit.assertEquals;
import static org.testng.AssertJUnit.assertTrue;


public class SpartanTestWithParameters {

    Response response;

    @BeforeClass
    public void beforeClass(){

        //It is coming from RestAssured
        baseURI = "http://54.237.100.89:8000";
    }
    /*TASK test1
    Given accept type is json
    And Id parameter value is 5
    When user sends GET request to /api/spartans/{id}
    Then response status code should be 200
    And response content-type: application/json
    And "Blythe" should be in response payload
     */

    @Test
    public void test1(){

       response = given().accept(ContentType.JSON)
               .and().pathParam("id",5)//->it makes it dynamic id changing value 5 for other value
                .when().get(baseURI + "/api/spartans/{id}");//id here is 5

      assertEquals(response.statusCode(),200);
        assertEquals(response.contentType(),"application/json");
        assertTrue(response.body().asString().contains("Blythe"));
    }
     /*TASK test2
    Given accept type is json
    And Id parameter value is 500
    When user sends GET request to /api/spartans/{id}
    Then response status code should be 404
    And response content-type: application/json
    And "Not Found" should be in response payload
     */

    @Test
    public void test2(){
        response = given().accept(ContentType.JSON)
                .and().pathParam("id",500)//->make id dynamic
                .when().get(baseURI + "/api/spartans/{id}");// id here is 500

        assertEquals(response.statusCode(),404);
        assertEquals(response.contentType(),"application/json");
        assertTrue(response.body().asString().contains("Not Found"));
    }

     /*TASK test3
    Given accept type is json are:
    gender|Female
    nameCantains|e
    When user sends GET request to /api/search
    Then response status code should be 200
    And response content-type: application/json;charset= UTF-8
    And "Female" should be in response payload
    And "Janette" should be in response payload
     */
    @Test
    public void test3(){
        response = given().accept(ContentType.JSON)
                .and().queryParam("gender","Female")//key and value
                .and().queryParam("nameContains","e")//key and value
                .when().get(baseURI + "/api/spartans/search");
        assertEquals(response.statusCode(),200);
        assertEquals(response.contentType(),"application/json");
        assertTrue(response.body().asString().contains("Female"));
        assertTrue(response.body().asString().contains("Janette"));
    }

    @Test
    public void test4WithMaps(){

        //create a map and add query parameters
        Map<String,Object> queryMap = new HashMap<>();
        queryMap.put("gender","Female");
        queryMap.put("nameContains","e");

        response = given().accept(ContentType.JSON)
                .and().queryParams(queryMap)
                .when().get("api/spartans/search");

        //response verification:
        assertEquals(response.statusCode(),200);
        assertEquals(response.contentType(),"application/json");
        assertTrue(response.body().asString().contains("Female"));
        assertTrue(response.body().asString().contains("Janette"));
    }
}
