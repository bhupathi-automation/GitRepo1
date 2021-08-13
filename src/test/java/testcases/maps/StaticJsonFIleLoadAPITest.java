package testcases.maps;

import io.restassured.RestAssured;
import org.testng.annotations.Test;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

import static io.restassured.RestAssured.given;

public class StaticJsonFIleLoadAPITest {

    @Test
    public void addNewPlace(){

        RestAssured.baseURI = "https://rahulshettyacademy.com";
        String payLoad = null;
        String response;

        /* Convert the Json --> byte[] --> String */
        try {
             payLoad = new String(Files.readAllBytes(Paths.get("D:\\API Testing\\add_place_maps.json")));
        } catch (IOException e) {
            e.printStackTrace();
        }


        given().log().all()
                .queryParam("key", "qaclick123")
                .header("Content-Type","application/json")
                .body(payLoad)
                .when().post("/maps/api/place/add/json")
                .then().log().all()
                .assertThat().statusCode(200).extract().response().asString();
    }
}
