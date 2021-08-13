package testcases.jira;

import Utils.Session;
import payload.PayLoadJira;
import io.restassured.filter.session.SessionFilter;
import api.resources.JiraResource;

import io.restassured.RestAssured;
import io.restassured.path.json.JsonPath;
import org.testng.annotations.Test;


import static io.restassured.RestAssured.*;

public class CreateIssue {
    /* 'SessionFilter' class stores the session details in the filter() method
            --> when you use filter() in the diff test, it returns the previously stored session (cookies) details
    *  for the path parameter (JiraResource.ADD_COMMENT), you need to provide same key as in the response to pathParam()
    *  relaxedHTTPSValidation() --> bypasses the HTTPS certificate validations in real time projects */

    Session session = new Session();
    SessionFilter sessionFilter = new SessionFilter();

    @Test (priority = 0, enabled = true)
    public void createSession() {
        String response;

        setBaseURI();
        response = given().relaxedHTTPSValidation()
                    .header("Content-Type", "application/json").body(PayLoadJira.createSession()).filter(sessionFilter)
                .when().post(JiraResource.NEW_SESSION_RESOURCE)
                .then().assertThat().statusCode(200).extract().response().asString();

        JsonPath js = new JsonPath(response);
        session.setId(js.getString("session.name"));
        session.setValue(js.getString("session.value"));

//        System.out.println("Response is =====> "+response);
//        System.out.println("ID from session is: "+session.getId());
//        System.out.println("Value from session is: "+session.getValue());
    }

    @Test (priority = 1)
    public void createStory() {
        createSession();
        System.out.println("ID from session is: " + session.getId());
        System.out.println("Value from session is: " + session.getValue());
        System.out.println("\n********** session has been created successfully **********\n");

        setBaseURI();
        String response = given().log().all()
                .header("Content-Type","application/json").filter(sessionFilter)
//                .header("Cookie",session.getId() +"="+ session.getValue())
                .body(PayLoadJira.createStory("Automation_Story","This is a new story created from Automation framework"))
        .when().post(JiraResource.NEW_ISSUE_RESOURCE)
        .then().log().all().assertThat().statusCode(201).extract().response().asString();

        JsonPath js = new JsonPath(response);
        session.setStoryId(js.getString("id"));
        System.out.println("\n ==========> Story ID is: "+session.getStoryId());
        //10007
    }

    @Test (priority = 2)
    public void addComment(){
//        createSession();
        setBaseURI();

        String response = given().log().all()
                .pathParam("id",session.getStoryId())
                .header("Content-Type","application/json").filter(sessionFilter)
//                .header("Cookie",session.getId() +"="+ session.getValue())
                .body(PayLoadJira.addComment("Automation - Adding Comment"))
        .when().post(JiraResource.ADD_COMMENT)
        .then().log().all()
                .assertThat().statusCode(201).extract().response().asString();

        JsonPath js = new JsonPath(response);
        session.setCommentId(js.getString("id"));
        //comment ID = 10002, 10003
    }

    @Test (priority = 3)
    public void editComment(){
//        createSession();
        setBaseURI();

        given().log().all()
                .pathParam("key",session.getStoryId()).pathParam("id",session.getCommentId())
                .header("Content-Type","application/json").filter(sessionFilter)
//                .header("Cookie",session.getId() +"="+ session.getValue())
                .body(PayLoadJira.addComment("Automation - Editing 2nd Comment"))
                .when().put(JiraResource.EDIT_COMMENT)
                .then().log().all().assertThat().statusCode(200);

        //comment ID = 10002, 10003
    }

    public void setBaseURI(){
        RestAssured.baseURI = JiraResource.BASE_URL;
    }
}
