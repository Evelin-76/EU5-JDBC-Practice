package apiTest;

import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import utilities.ConfigurationReader;

import static io.restassured.RestAssured.baseURI;
import static io.restassured.RestAssured.given;

public class HrApiParameterTest {

    Response response;
    @BeforeClass
    public void beforeClass(){

        //It is coming from RestAssured
        baseURI = ConfigurationReader.get("hr_api_url");
    }
    /*TASK test1
    Given accept tyoe us json
    And parameters: q = {"region_id":2}
    when users send a GET reqpuest to "/countries"
    Then status code is 200
    And Content type is application/json
    And Payload shous contain "United States of America"
    {"region_id":2}

     */

    @Test
    public void  test1(){
       response = given().accept(ContentType.JSON)
                .and().queryParams("q","{\"region_id\":2}")
                .when().get("/countries");
        Assert.assertEquals(response.statusCode(),200);
        Assert.assertEquals(response.contentType(),"application/json");
        Assert.assertTrue(response.body().asString().contains("United States of America"));

    }
}
