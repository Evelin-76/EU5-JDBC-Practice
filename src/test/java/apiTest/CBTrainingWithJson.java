package apiTest;

import io.restassured.http.ContentType;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import utilities.ConfigurationReader;

import static io.restassured.RestAssured.given;
import static org.testng.Assert.*;

import static io.restassured.RestAssured.baseURI;

public class CBTrainingWithJson {

    Response response;
    @BeforeClass
    public void beforeClass(){

        //It is coming from RestAssured
        baseURI = ConfigurationReader.get("cybertek_api_url");
    }
    @Test
    public void test1(){

      response =  given().accept(ContentType.JSON)
                .and().pathParam("id",24161)
                .when().get("/student/{id}");
        assertEquals(response.statusCode(),200);
        assertEquals(response.contentType(),"application/json;charset=UTF-8");

        JsonPath jsonPath = response.jsonPath();

        String firstName = jsonPath.getString("students.firstName[0]");
        String lastName = jsonPath.getString("students.lastName[0]");
        int batch = jsonPath.getInt("students.batch[0]");
        String contact = jsonPath.getString("students.contact[0].phone");
        System.out.println(contact);
        assertEquals(firstName,"Vera");

        String city = jsonPath.getString("students.company[0].address.city");
        System.out.println("City: " + city);
        assertEquals(city,"Chicago");

        int zipCode = jsonPath.getInt("students.company[0].address.zipCode");
        System.out.println("Zip Code: " + zipCode);
        assertEquals(zipCode,60606);




    }
}
