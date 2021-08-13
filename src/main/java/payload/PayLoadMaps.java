package payload;

public class PayLoadMaps {

    public static String addPlace() {
        String jsonBody = "{\n" +
                            "  \"location\": {\n" +
                            "    \"lat\": -38.383494,\n" +
                            "    \"lng\": 33.427362\n" +
                            "  },\n" +
                            "  \"accuracy\": 50,\n" +
                            "  \"name\": \"FnF Fast Food\",\n" +
                            "  \"phone_number\": \"(+91) 99887 66554\",\n" +
                            "  \"address\": \"28, Madhapur, Hyderabad-3\",\n" +
                            "  \"types\": [\n" +
                            "    \"Fast Food\",\n" +
                            "    \"Restaurant\"\n" +
                            "  ],\n" +
                            "  \"website\": \"https://rahulshettyacademy.com\",\n" +
                            "  \"language\": \"French-IN\"\n" +
                            "}\n";
        return jsonBody;
    }

    public static String addNewPlace(String name, String phone) {
        String jsonBody = "{\n" +
                "  \"location\": {\n" +
                "    \"lat\": -38.383494,\n" +
                "    \"lng\": 33.427362\n" +
                "  },\n" +
                "  \"accuracy\": 50,\n" +
                "  \"name\": \""+name+"\",\n" +
                "  \"phone_number\": \"(+91) "+phone+"\",\n" +
                "  \"address\": \"28, Madhapur, Hyderabad-3\",\n" +
                "  \"types\": [\n" +
                "    \"Fast Food\",\n" +
                "    \"Restaurant\"\n" +
                "  ],\n" +
                "  \"website\": \"https://rahulshettyacademy.com\",\n" +
                "  \"language\": \"French-IN\"\n" +
                "}\n";
        return jsonBody;
    }

    public static String updatePlace(String placeId, String newAddress){
        String jsonBody = "{\n" +
                            "    \"place_id\":\""+ placeId +"\",\n" +
                            "    \"address\":\""+ newAddress +"\",\n" +
                            "    \"key\":\"qaclick123\"\n" +
                            "}";
        return jsonBody;
    }

    public static String coursePrice(){
        return "{\n" +
                "\"dashboard\" : {\n" +
                "\"purchaseAmount\" : 910,\n" +
                "\"website\" : \"https://rahulshettyacademy.com\" \n" +
                "},\n" +
                "\"courses\" : [\n" +
                "{\n" +
                "\"title\" : \"Selenium Java\",\n" +
                "\"price\" : 50,\n" +
                "\"copies\" : 6\n" +
                "},\n" +
                "{\n" +
                "\"title\" : \"Cypress\",\n" +
                "\"price\" : 40,\n" +
                "\"copies\" : 4\n" +
                "},\n" +
                "{\n" +
                "\"title\" : \"RPA\",\n" +
                "\"price\" : 45,\n" +
                "\"copies\" : 10\n" +
                "}\n" +
                "]\n" +
                "}";
    }
}
