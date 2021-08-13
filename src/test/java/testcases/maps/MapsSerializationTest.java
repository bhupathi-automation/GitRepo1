package testcases.maps;

import io.restassured.RestAssured;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.http.ContentType;
import io.restassured.parsing.Parser;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import io.restassured.specification.ResponseSpecification;
import org.testng.annotations.Test;
import payload.MapsJavaObjPayLoad;
import specbuilders.maps.MapsSpecBuilder;

import static io.restassured.RestAssured.given;

public class MapsSerializationTest {

    String addPlaceResouce = "/maps/api/place/add/json";

    @Test
    public void pojoSerializationTest() {
        RestAssured.baseURI = "https://rahulshettyacademy.com";

        String response = given().log().all()
                .queryParam("key","qaclick123").header("Content-Type","application/json")
                .body(MapsJavaObjPayLoad.addPlace())
                .when().post(addPlaceResouce)
                .then().log().all().assertThat().statusCode(200).extract().response().asString();

        System.out.println(response);
    }

    @Test
    public void serializationWithSpecBuilder(){
        Response response = given().spec(MapsSpecBuilder.requestSpecification()).body(MapsJavaObjPayLoad.addPlace())
                .when().post(addPlaceResouce)
                .then().spec(MapsSpecBuilder.responseSpecification()).extract().response();

        System.out.println("Response is: \n"+response.asString());
    }


}
