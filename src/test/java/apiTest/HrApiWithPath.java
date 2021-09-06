package apiTest;

import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import utilities.ConfigurationReader;
import java.util.List;

import static io.restassured.RestAssured.baseURI;
import static io.restassured.RestAssured.given;
import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertTrue;


public class HrApiWithPath {

    Response response;
    @BeforeClass
    public void beforeClass(){

        //It is coming from RestAssured
        baseURI = ConfigurationReader.get("hr_api_url");
    }

    @Test
    public void getCountriesWithPath(){
        response = given().accept(ContentType.JSON)
                .and().queryParams("q","{\"region_id\":2}")
                .when().get(baseURI + "/countries");

        assertEquals(response.statusCode(),200);

        //print limit value
        System.out.println("Response path limit: " + response.path("limit"));
        //print hasMore value
        System.out.println("Response path hasMore: " + response.path("hasMore"));

        //ENTER TO VALUES THAT BELONG ONE OBJECT NESTED INSIDE OTHER. DIFFERENTS LEVELS
        String id1 = response.path("items.country_id[0]");
        System.out.println("First Country id: " + id1);
        String countryName1 = response.path("items.country_name[0]");
        System.out.println("First Country name: " + countryName1);

        String id2 = response.path("items.country_id[2]");
        System.out.println("Second Country id: " + id2);
        String countryName2 = response.path("items.country_name[2]");
        System.out.println("Second Country name: " + countryName2);



        //list of all contries id
        List<String> countriesId = response.path("items.country_id");
        System.out.println("Countries id as array list: ");
        System.out.println(countriesId);

        //example getting same with json method for practices
        List<Object> jsonlistCountryID = response.jsonPath().getList("items.country_id");
        System.out.println("Country id from Json method using list:"+ jsonlistCountryID);
        System.out.println();

        int counter = 1;
        for (String s : countriesId) {
            System.out.println("Country id " + counter++);
            System.out.println(s);
        }

        //one object nested in a nested object.Inside levels
        //1.getting it as object as array:
        Object link1Href = response.path("items.links[1].href");
        System.out.println("Link second response as object array: " + link1Href);
        //2.getting it as object as array:
        String link1HrefStr = response.path("items.links[1].href[0]");
        System.out.println("Link second response as  string: " + link1HrefStr);


        List<String> allLinksHref = response.path("items.links.href");
        System.out.println("All links in array list:");
        System.out.println(allLinksHref);
        System.out.println("*************************");
        int count = 1;
        for (Object o : allLinksHref) {
            System.out.println("Link " + count++);
            System.out.println(o);
        }

        //assert that all regions' ids are equal to 2
        List<Integer> regionId = response.path("items.region_id");

        System.out.println("Region id: " + regionId);
        for (int integer : regionId) {
            System.out.println(integer);
            assertEquals(integer,2);
        }
    }
    @Test
    public void test2(){

        response = given().accept(ContentType.JSON)
                .and().queryParams("q","{\"job_id\":\"IT_PROG\"}")
                .when().get("/employees");
        assertEquals(response.statusCode(),200);
        assertEquals(response.contentType(),"application/json");
        assertTrue(response.body().asString().contains("IT_PROG"));

        //makes sure we have only IT-PROG as job_id
        List<String> jobIds = response.path("items.job_id");
        System.out.println("Job id as array list: " + jobIds);
        for (String jobId : jobIds) {
            assertEquals(jobId,"IT_PROG");
        }

    }
}
