package specbuilders.maps;

import io.restassured.builder.RequestSpecBuilder;
import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.http.ContentType;
import io.restassured.specification.RequestSpecification;
import io.restassured.specification.ResponseSpecification;
import payload.MapsJavaObjPayLoad;

import static io.restassured.RestAssured.given;

public class MapsSpecBuilder {

    public static RequestSpecification requestSpecification(){
        RequestSpecification requestSpec =  new RequestSpecBuilder()
                .setBaseUri("https://rahulshettyacademy.com")
                .addQueryParam("key","qaclick123")
                .setContentType(ContentType.JSON).build();

        return requestSpec;
    }

    public static ResponseSpecification responseSpecification(){
        ResponseSpecification responseSpec = new ResponseSpecBuilder()
                .expectStatusCode(200)
//                .expectBody()
                .expectContentType(ContentType.JSON).build();

        return responseSpec;
    }

    public void specBuilderDemo(){
        RequestSpecification req = new RequestSpecBuilder()
                .setBaseUri("https://rahulshettyacademy.com")
                .addQueryParam("key","qaclick123")
                .setContentType(ContentType.JSON).build();

        RequestSpecification requestSpec = given()
                .spec(req)
                .body(MapsJavaObjPayLoad.addPlace());

        ResponseSpecification responseSpec = new ResponseSpecBuilder()
                .expectStatusCode(200).expectContentType(ContentType.JSON).build();
    }
}
