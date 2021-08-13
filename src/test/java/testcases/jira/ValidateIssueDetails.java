package testcases.jira;

import Utils.Session;
import api.resources.JiraResource.*;
import io.restassured.RestAssured;
import io.restassured.path.json.JsonPath;
import org.testng.Assert;
import org.testng.annotations.Test;

import static api.resources.JiraResource.*;
import static io.restassured.RestAssured.*;

public class ValidateIssueDetails {

    CreateIssue newIssue = new CreateIssue();
    AddAttachmentToJiraTask addAttachmentToJiraTask = new AddAttachmentToJiraTask();

    @Test
    public void validateStoryDetails(){
        RestAssured.baseURI = BASE_URL;

        addAttachmentToJiraTask.addAttachment();
        newIssue = addAttachmentToJiraTask.newIssue;

        String storySummary = "Automation_Story";
        String storyDescription = "This is a new story created from Automation framework";
        String attachmentId = newIssue.session.getAttachmentId();
        String attachmentName = "jira_attachment.txt";

        String actualSummary;
        String actualDescription;
        String actualFileName=null;

        String response = given().log().all()
                                .header("Content-Type","application/json")
                                .pathParam("id",newIssue.session.getStoryId())
                                .filter(newIssue.sessionFilter)
                            .when().get(GET_ISSUE_DETAILS)
                            .then().log().all()
                                .assertThat().statusCode(200)
                                .extract().response().asString();

        JsonPath js = new JsonPath(response);

        /* Validate story summary */
        actualSummary = js.getString("fields.summary");
        if(storySummary.equalsIgnoreCase(actualSummary)){
            System.out.println("Summary: "+storySummary+" - validation PASSED");
        } else{
            System.out.println("Summary: "+storySummary+" - validation FAILED");
        }

        /* Validate story Description */
        actualDescription = js.getString("fields.description");
        if(storyDescription.equalsIgnoreCase(actualDescription)){
            System.out.println("Description: "+storyDescription+" - validation PASSED");
        } else{
            System.out.println("Description: "+storyDescription+" - validation FAILED");
        }

        /* Validate story Attachment */
        String tempId;
        int filesCount = js.getInt("fields.attachment.size");

        for (int i=0; i<filesCount; i++){
            tempId = js.get("fields.attachment["+i+"].id").toString();
            if(attachmentId.equalsIgnoreCase(tempId)){
                actualFileName = js.get("fields.attachment["+i+"].filename").toString();
            }
        }

        if(attachmentName.equalsIgnoreCase(actualFileName)){
            Assert.assertEquals(actualFileName, attachmentName);
            System.out.println("Attachment: "+ attachmentName + " - validation PASSED");
        } else {
            Assert.assertEquals(actualFileName, attachmentName);
            System.out.println("Attachment: "+ attachmentName + " - validation FAILED");
        }

    }

    @Test
    public void validateComment(){
        /* Filter the response using 'queryParam("fields","comment")'
         * 1. Iterate through all the comments present in response, Check if commentId exist in the response
         * 2. If exists, retrieve the comment.body --> verify it
         * 3. 'fields.comment.comments' is the path for all the comments in the response */
        RestAssured.baseURI = BASE_URL;

        newIssue.createSession();
        newIssue.createStory();
        newIssue.addComment();

        String commentId = newIssue.session.getCommentId();
        String expectedComment = "Automation - Adding Comment";
        String actualComment = null;
        String tempCommentId;

        String response = given().log().all()
                    .header("Content-Type","application/json")
                    .pathParam("id", newIssue.session.getStoryId())
                    .queryParam("fields","comment")
                    .filter(newIssue.sessionFilter)
                .when().get(GET_ISSUE_DETAILS)
                .then().log().all()
                    .assertThat().statusCode(200)
                    .extract().response().asString();

        JsonPath js = new JsonPath(response);
        int commentsCount = js.getInt("fields.comment.comments.size()");

        for(int i=0; i<commentsCount; i++){
            tempCommentId = js.get("fields.comment.comments["+i+"].id").toString();
            System.out.println("retrieved comment Id is: "+tempCommentId);

            if(commentId.equalsIgnoreCase(tempCommentId)){
                actualComment = js.getString("fields.comment.comments["+i+"].body");
            }
        }

        System.out.println("Actual comment retrieved is --> "+actualComment);
        Assert.assertEquals(actualComment, expectedComment);
        System.out.println("Comment validation PASSED");
    }
}
