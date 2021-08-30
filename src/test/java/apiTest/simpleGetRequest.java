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
    When user send get request to regions
    Then response status code must be 200
    and body is json format
     */
    @Test
    public void test2(){//WITH TestNG
        Response response = RestAssured.given().accept(ContentType.JSON)
                .when().get(hrUrl);

        Assert.assertEquals(response.statusCode(),200);

        //verify content-type is application/json
        System.out.println("Content Type: " + response.contentType());
        Assert.assertEquals(response.contentType(),"application/json");

    }

    @Test
    public void test3(){//WITH RestAssured
        RestAssured.given().accept((ContentType.JSON))
                .when().get(hrUrl).then()
                .assertThat().statusCode(200)
                .and().contentType("application/json");

    }

    /*test4 TASK:
    Given accept type is json
    When user send get request to regions/2
    Then response status code must be 200
    and body is json format
    and response body contains Americas
     */

    @Test
    public void test5(){//with TestNG assertions
     Response response = RestAssured.given().accept(ContentType.JSON)
                .when().get(hrUrl + "/2");

     //verify status
        Assert.assertEquals(response.getStatusCode(),200);

        //verify content type
        Assert.assertEquals(response.contentType(),"application/json");

        //verify body contains Americas
        response.body().asString().contains("Americas");
    }
}
