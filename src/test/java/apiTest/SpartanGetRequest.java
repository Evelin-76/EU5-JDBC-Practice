package apiTest;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.testng.Assert;
import org.testng.annotations.Test;
import static io.restassured.RestAssured.*;

public class SpartanGetRequest {

    String spartanUrl = "http://54.237.100.89:8000";
    Response response;

    @Test
    public void test1() {
        Response response = RestAssured.given().accept(ContentType.JSON)
                .when().get(spartanUrl + "/api/spartans");
        System.out.println(response.statusCode());
        System.out.println("Body: ");
                 response.prettyPrint();

    }

    /*TASK test2
    When users sends a get request to /api/spartans/3
    Them status code should be 200
    And content type should be applications/json;chartset=UFT-8
    And json body should contain Fidole
     */

    @Test
    public void test2(){

        //Class RestAssure added to dependencies was imported so we work on it directly
        response = when().get(spartanUrl + "/api/spartans/3");
              //verify status code
        Assert.assertEquals(response.statusCode(),200);

        //verify content type
        Assert.assertEquals(response.contentType(),"application/json;charset=UTF-8");

        //verify body contains Fidole
       Assert.assertTrue(response.body().asString().contains("Fidole"));

    }
    /*TASK test3
    Given no headers provided
    When Users sends GEt request to api/hello
    Then response status cose should be 200
    And Content type header should be "text/plain/;charset=UFT-8"
    And header should contain date
    And Contect-Lenght should be 17
    And body should be "Hello from Sparta"
     */

    @Test
    public void test3(){

       response = when().get(spartanUrl + "/api/hello");
       Assert.assertEquals(response.statusCode(),200);
       Assert.assertEquals(response.contentType(),"text/plain;charset=UTF-8");

       //verify we have headers named date and content length
        System.out.println("Content Length: " + response.header("Content-Length"));
        System.out.println("Date: " + response.header("Date"));
       Assert.assertTrue(response.headers().hasHeaderWithName("Date"));
       Assert.assertTrue(response.headers().hasHeaderWithName("Content-Length"));

       //veryfy hello from sparta
        Assert.assertEquals(response.body().asString(),"Hello from Sparta");
    }
}
