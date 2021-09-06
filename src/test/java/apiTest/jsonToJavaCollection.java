package apiTest;

import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import utilities.ConfigurationReader;

import java.math.BigDecimal;
import java.util.Map;

import static org.testng.Assert.*;

import static io.restassured.RestAssured.baseURI;
import static io.restassured.RestAssured.*;

public class jsonToJavaCollection {

    Response response;
    @BeforeClass
    public void beforeClass(){

        //It is coming from RestAssured
        baseURI = ConfigurationReader.get("spartan_api_url");
    }
    @Test
    public void SpartanToMap(){
       response = given().accept(ContentType.JSON)
                .and().pathParam("id",15)
                .when().get("/api/spartans/{id}");
        assertEquals(response.statusCode(),200);

        //we will convert json response to java map
        Map<String,Object> jsonDataMAp = response.body().as(Map.class);
        System.out.println("JsonDataMap: " + jsonDataMAp);

        String name = (String) jsonDataMAp.get("name");
        assertEquals(name,"Meta");
        double phone = (double) jsonDataMAp.get("phone");
        System.out.println("Phone: " + phone);
        assertEquals(phone,1.938695106E9);
        //wrapper String to BigDecimal number
        BigDecimal phone1 = new BigDecimal(String.valueOf(jsonDataMAp.get("phone")));
        System.out.println("Phone parsed from String to BigDecimal: " + phone1);

    }



}
