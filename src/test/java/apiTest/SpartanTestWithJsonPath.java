package apiTest;

import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import org.testng.annotations.BeforeClass;
import utilities.ConfigurationReader;

import static io.restassured.RestAssured.baseURI;

import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import utilities.ConfigurationReader;
import java.util.ArrayList;
import java.util.List;
import static io.restassured.RestAssured.*;
import static org.testng.Assert.*;


public class SpartanTestWithJsonPath {

    Response response;
    @BeforeClass
    public void beforeClass(){

        //It is coming from RestAssured
        baseURI = ConfigurationReader.get("spartan_api_url");
    }
    /*task with json
    Given accept type is json
    And path param spartan id is 11
    When user sends a get request to /spartans/{id}
    Then status code is 200
    And content type is Json
    And "id": 11,
        "name": "Nona"
        "gender": "Female"
        "phone": 7959094216
     */
    @Test
    public void test1() {
        response = given().accept(ContentType.JSON)
                .and().pathParam("id",11)
                .when().get(baseURI + "/api/spartans/{id}");
        assertEquals(response.statusCode(),200);
        assertEquals(response.contentType(),"application/json");

        //verify id and name with path()
        int id = response.path("id");
        String name = response.path("name");
        String gender = response.path("gender");
        long phone = response.path("phone");
        assertEquals(id,11);
        assertEquals(name,"Nona");
        assertEquals(gender,"Female");
        assertEquals(phone,7959094216l);

        //JSON PATH METHOD NOW:
        JsonPath jsonPath = response.jsonPath();

        int idJson = jsonPath.getInt("id");
        String nameJson = jsonPath.getString("name");
        String genderJson = jsonPath.getString("gender");
        long phoneJson = jsonPath.getLong("phone");
        System.out.println("Json id: " + idJson);
        System.out.println("Json name: " + nameJson);
        System.out.println("Json gender: " + genderJson);
        System.out.println("Json phone: " + phoneJson);

        //Json verification
        assertEquals(idJson,11);
        assertEquals(nameJson, "Nona");
        assertEquals(genderJson,"Female");
        assertEquals(phoneJson, 7959094216l);
    }
}
