package testcases.jira;

import api.resources.JiraResource;
import io.restassured.path.json.JsonPath;
import org.testng.annotations.Test;

import java.io.File;
import java.util.ArrayList;

import static io.restassured.RestAssured.*;

public class AddAttachmentToJiraTask {

    CreateIssue newIssue = new CreateIssue();

    @Test
    public void addAttachment(){
        /* below is the format given by JIRA API
         * curl -D- -u admin:admin -X POST -H "X-Atlassian-Token: no-check" -F "file=@myfile.txt" http://myhost/rest/api/2/issue/TEST-123/attachments
         * multiPart() is used to pass the file to be attached ---> header("Content-Type","multipart/form-data") needs to used
         * HTTP Method: POST
         * API resouce = /rest/api/2/issue/{issueIdOrKey}/attachments
         */

        newIssue.createSession();
        newIssue.createStory();
        String response = given().log().all()
                    .header("X-Atlassian-Token", "no-check").filter(newIssue.sessionFilter)
                    .header("Content-Type","multipart/form-data")
                    .pathParam("id",newIssue.session.getStoryId())
                    .multiPart("file", new File("src/test/files/jira_attachment.txt"))
                .when().post(JiraResource.ADD_ATTACHMENT)
                .then().log().all()
                    .assertThat().statusCode(200)
                    .extract().response().asString();

        JsonPath js = new JsonPath(response);
        ArrayList<String> id = js.get("id");
        newIssue.session.setAttachmentId( id.get(0));

        System.out.println("******* Attachment Created successfully, Id is: "+id.get(0)+" ******* ");
    }
}
