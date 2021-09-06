package apiTest;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.specification.Argument;
import static org.hamcrest.Matchers.*;
import org.hamcrest.Matchers;
import org.testng.annotations.Test;

import java.util.List;

import static io.restassured.RestAssured.*;
import static org.hamcrest.Matcher.*;


public class HamcrestMatchersApiTest {
    /*
    Given accept type is json
    And path param id is 15
    When user sends a get request to spartans/{id}
    Then status code is 200
    And content type is Json
    And json data has following:
    "id": 15,
    "name": "Meta",
    "gender": "Female",
    "phone": 1938695106
     */

    //USING CHAINING METHOD WITHOUT GPATH THROUGH RESTASSURED:
    String spartan_api_url = "http://54.237.100.89:8000";

    @Test
    public void OneSpartanWithHamcrest() {

        given().accept(ContentType.JSON)
                .and().pathParam("id", 15)
                .when().get(spartan_api_url + "/api/spartans/{id}")
                .then().statusCode(200)
                .and().assertThat().contentType(equalTo("application/json"))
                .and().assertThat().body("id",equalTo(15),
                        "name",equalTo("Meta"),
                        "gender",equalTo("Female"),
                        "phone",equalTo(1938695106))
                .log().all();
    }

    @Test
    public void teacherData(){
        given().accept(ContentType.JSON)
                .and().pathParam("id",10776)
                .when().get("http://api.cybertektraining.com/teacher/{id}")
                .then().assertThat().statusCode(200)
                .and().assertThat().contentType(equalTo("application/json;charset=UTF-8"))
             //   .and().body("firstName[0]",equalTo("Clyde"))
               // .and().body("teachers.lastName[0]",equalTo("Reilly"))
                .and().header("Content-Length",equalTo("285"))
                .log().all();
    }
    @Test
    public void teacherDataCorrect(){
        given().accept(ContentType.JSON)
                .and().pathParam("id", 10776)
                .when().get("http://api.cybertektraining.com/teacher/{id}")
                        .then().assertThat().statusCode(200)
                        .and().assertThat().contentType(equalTo("application/json;charset=UTF-8"))
                        .and().header("Connection", equalTo("Keep-Alive"))
                        .and().header("Content-Length", equalTo("285"))
                        .and().header("Date", notNullValue())
                        .and().assertThat().body("teachers.firstName[0]", equalTo("Clyde"),
                                "teachers.lastName[0]", equalTo("Reilly"),
                                "teachers.gender[0]", equalTo("Male"))
                        .log().all()
                        .log().headers();
    }

    @Test
    public void teachersWithDepartments(){

        given().accept(ContentType.JSON)
                .and().pathParam("name","Computer")
                .when().get("http://api.cybertektraining.com/teacher/department/{name}")
                .then().statusCode(200)
                .and().contentType(equalTo("application/json;charset=UTF-8"))
                .and().body("teachers.firstName",hasItems("Alexander","Marteen"));
    }
}
