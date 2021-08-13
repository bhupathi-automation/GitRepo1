package payload;

public class PayLoadJira {

    public static String createSession(){
        return new String("{ \"username\": \"bhupathiraj253\", \"password\": \"Jira@123\" }");
    }

    public static String createStory(String summary, String desc){
        return new String("{    \n" +
                "    \"fields\": {\n" +
                "        \"project\": {\n" +
                "            \"key\": \"RSAT\"\n" +
                "        },\n" +
                "        \"summary\": \""+summary+"\",\n" +
                "\t\t\"description\": \""+desc+"\",\n" +
                "        \"issuetype\": {\n" +
                "            \"name\": \"Story\"\n" +
                "        }\n" +
                "    }\n" +
                "}");
    }

    public static String createBug(String summary, String desc){
        return new String("{    \n" +
                "    \"fields\": {\n" +
                "        \"project\": {\n" +
                "            \"key\": \"RSAT\"\n" +
                "        },\n" +
                "        \"summary\": \""+summary+"\",\n" +
                "\t\t\"description\": \""+desc+"\",\n" +
                "        \"issuetype\": {\n" +
                "            \"name\": \"Bug\"\n" +
                "        }\n" +
                "    }\n" +
                "}");
    }

    public static String addComment(String comment){
        return new String("{\n" +
                "    \"body\": \""+comment+"\",\n" +
                "    \"visibility\": {\n" +
                "        \"type\": \"role\",\n" +
                "        \"value\": \"Administrators\"\n" +
                "    }\n" +
                "}");
    }

}
