package apiTest;

import com.sun.xml.internal.ws.server.ServerRtException;
import groovyjarjarantlr4.v4.codegen.model.SrcOp;
import io.restassured.response.Response;
import org.testng.annotations.BeforeClass;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import static io.restassured.RestAssured.*;
import static org.testng.AssertJUnit.assertEquals;
import static org.testng.AssertJUnit.assertTrue;


import static io.restassured.RestAssured.baseURI;

public class SpartanTestWithPath {

    Response response;

    @BeforeClass
    public void beforeClass(){

        //It is coming from RestAssured
        baseURI = "http://54.237.100.89:8000";
    }

    /*TASK test
    Given accept type is json
    And path parameter id is 10
    When user sends GET request to /api/spartans/{id}
    Then response status code should be 200
    And response content-type: application/json
    And response payload values match the following:
    id is 10,
    name us "Lorenza",
    gender is "Female",
    phone is 3312820936
     */

    @Test
    public void test1() {
        response = given().accept(ContentType.JSON)
                .and().pathParam("id",10)//dynamic id
                .when().get(baseURI + "/api/spartans/{id}");//getting id number

        assertEquals(response.statusCode(),200);
        assertEquals(response.contentType(),"application/json");
       // response.prettyPrint();

        //printing each key value in the json body/payload
        System.out.println(response.path("id").toString());
        System.out.println(response.path("name").toString());
        System.out.println(response.path("gender").toString());
        System.out.println(response.path("phone").toString());

        //save json key values
        int id = response.path("id");
        String name = response.path("name");
        String gender = response.path("gender");
        long phone = response.path("phone");

        System.out.println("id: " + id);
        System.out.println("name: " + name);
        System.out.println("gender: " + gender);
        System.out.println("phone: " + phone);

        //assert one by one
        assertEquals(id,10);
        assertEquals(name, "Lorenza");
        assertEquals(gender, "Female");
        assertEquals(phone, 3312820936l);
    }

    @Test
    public void getAllSpartanWithPath(){
        response = given().accept(ContentType.JSON)
                .when().get("/api/spartans");
        assertEquals(response.statusCode(),200);
     //   assertEquals(response.contentType(),"application/json");-->sustitute this for the
        //getHeader so we can specify that "Content-Type" contains "application/json" :
        assertEquals(response.getHeader("Content-Type"), "application/json");

        System.out.println(response.path("id[0]").toString());

        int firstId = response.path("id[0]");
        System.out.println("First id: " + firstId);
        String nameFistId = response.path("name[0]");
        System.out.println("Name: " + nameFistId);
        String genderFirstId = response.path("gender[0]").toString();
        System.out.println("Gender: " + genderFirstId);
        long phoneFirstId = response.path("phone[0]");
        System.out.println("Phone: " + phoneFirstId);

        //SINTAX IS CALL Gpath  AND IT GIVES US THE INFO
        //WE CAN START WITH POSITIVE NUMBERS(1,2,3...) FOR GET INFO FROM THE BEGINNING
        //OR WITH NEGATIVE NUMBERS(-1.-2,-3...) TO GET THE INFO STARTING FROM THE END
        //to get the last index info
        int lastFirstId = response.path("id[-1]");
        System.out.println("Last id: " + lastFirstId);
        String lastName = response.path("name[-1]");

        String penultimoName = response.path("name[-2]");
        System.out.println("Penúltimno name: " + penultimoName);

        //make a list with all info
        List<String> names = response.path("name");
        System.out.println("All names list:");
        System.out.println(names);

        System.out.println("All phones numbers:");
        List<Object> phones = response.path("phone");

        int count = 1;
        for (Object phone : phones) {
            System.out.println("Phone " + count++);
            System.out.println(phone);
        }


    }
}
