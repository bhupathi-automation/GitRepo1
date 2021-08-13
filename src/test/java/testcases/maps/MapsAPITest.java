package testcases.maps;

import payload.PayLoadMaps;
import io.restassured.RestAssured;
import io.restassured.path.json.JsonPath;
import org.testng.Assert;

import static io.restassured.RestAssured.*; // given(), when(), then() methods come from this class
import static org.hamcrest.Matchers.*;      // equalTo() for assertions come from this class

public class MapsAPITest {

    public static void main(String[] args) {
        /* given() - all input details - queryParam(), header(), body()
        *  when() - submit the API - HTTP method(resource)
        *  then() - validate the response - assertThat(), statusCode()
        *  ---> use 'log().all()' to log all the process flow
        *  'JsonPath' is used to parse the Json response body (from String from)*/

        RestAssured.baseURI = "https://rahulshettyacademy.com";
        String newAddress = "242, Lingarajapuram, Bangalore - 84, Karnataka";
        String response;

        /* POST - create a new Address on Maps*/
        response = given().log().all()
                .queryParam("key", "qaclick123")
                .header("Content-Type","application/json")
                .body(PayLoadMaps.addPlace())
        .when()
                .post("/maps/api/place/add/json")
        .then()
                .assertThat().statusCode(200).body("status", equalTo("OK")).body("scope", equalTo("APP"))
                .header("Server","Apache/2.4.18 (Ubuntu)")
                .extract().response().asString();
        System.out.println("----- Response is -----\n" + response);

        JsonPath js = new JsonPath(response);
        String placeId = js.getString("place_id");
        
        System.out.println("place ID is: "+ placeId);

    /* PUT - Update the place (Address)*/
        given().log().all()
                .queryParam("key", "qaclick123")
                .header("Content-Type","application/json")
                .body(PayLoadMaps.updatePlace(placeId, newAddress))
        .when().put("maps/api/place/update/json")
        .then().log().all()
                .assertThat().statusCode(200).body("msg",equalTo("Address successfully updated"));

    /* GET - Verify the updated Address is reflecting */
        String getPlaceResponse;
        getPlaceResponse = given().log().all()
                .queryParam("key", "qaclick123").queryParam("place_id","2df173f0549c876ec772b4a85aca3b2f")
        .when().get("maps/api/place/get/json")
        .then().log().all()
                .assertThat().statusCode(200)
                .header("Server","Apache/2.4.18 (Ubuntu)").header("Content-Length","294").header("Connection","Keep-Alive").header("Content-Type","application/json;charset=UTF-8")
                .body("address", equalTo("242, Lingarajapuram, Bangalore - 84, Karnataka"))
                .extract().response().asString();

        System.out.println("----- Response is -----\n" + getPlaceResponse);

        JsonPath js1 = new JsonPath(getPlaceResponse);
        String actualAddress = js1.getString("address");
        System.out.println("----> New Address is : "+actualAddress);

        // TestNG assertions
        Assert.assertEquals(actualAddress, newAddress);


        System.out.println("=====success=====");

    }
}
