package api.resources;

public class JiraResource {
    public static String BASE_URL = "http://localhost:8080";
    public static String NEW_SESSION_RESOURCE = "/rest/auth/1/session";
    public static String NEW_ISSUE_RESOURCE = "/rest/api/2/issue";
    public static String ADD_COMMENT = "/rest/api/2/issue/{id}/comment";
    public static String EDIT_COMMENT = "/rest/api/2/issue/{key}/comment/{id}";
    public static String ADD_ATTACHMENT = "/rest/api/2/issue/{id}/attachments";
    public static String GET_ISSUE_DETAILS = "/rest/api/2/issue/{id}";
}
