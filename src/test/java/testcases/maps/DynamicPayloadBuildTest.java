package testcases.maps;

import payload.PayLoadMaps;
import io.restassured.RestAssured;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import static io.restassured.RestAssured.*;

public class DynamicPayloadBuildTest {

    @Test (dataProvider ="inputData")
    public void addNewPlace(String restaurentName, String phoneNumber){

        RestAssured.baseURI = "https://rahulshettyacademy.com";
        String response;

        response = given().log().all()
                .queryParam("key", "qaclick123")
                .header("Content-Type","application/json")
                .body(PayLoadMaps.addNewPlace(restaurentName,phoneNumber))
        .when().post("/maps/api/place/add/json")
        .then().log().all()
                .assertThat().statusCode(200).extract().response().asString();
    }

    @DataProvider (name = "inputData")
    /* 2D Arrays are used in  DataProvider
    *  2D Array - Array of Arrays - multiple array of same length & type */
    public Object[][] restaurentDetails(){
        Object[][] data = new Object[][]{
                {"Restaurent11","9988776651"},
                {"Restaurent12","9988776652"},
                {"Restaurent13","9988776653"}
        };
        return data;
    }

}
