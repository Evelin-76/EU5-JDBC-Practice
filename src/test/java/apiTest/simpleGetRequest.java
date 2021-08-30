package apiTest;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.testng.Assert;
import org.testng.annotations.Test;

public class simpleGetRequest {

    String hrUrl= "http://54.237.100.89:1000/ords/hr/regions";

    @Test
    public void test1(){

        Response response = RestAssured.get(hrUrl);
        //it returns the status code 200 if it is success or 400 if it is not or whatever it is
        System.out.println("Status code success is: " + response.statusCode());

        //the json report will be printed here
        System.out.println("Json report:");
        response.prettyPrint();
    }

    /*test2:
    Given accept type is json
    When user send get request to regions endpoint
    Then response status code must be 200
    and body is json format
     */
    @Test
    public void test2(){
        Response response = RestAssured.given().accept(ContentType.JSON)
                .when().get(hrUrl);

        Assert.assertEquals(response.statusCode(),200);

        //verify content-type is application/json
        System.out.println("Content Type: " + response.contentType());
        Assert.assertEquals(response.contentType(),"application/json");

    }
}
