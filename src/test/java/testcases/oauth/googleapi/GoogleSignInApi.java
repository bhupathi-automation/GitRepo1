package testcases.oauth.googleapi;

import io.restassured.parsing.Parser;
import io.restassured.path.json.JsonPath;
import org.testng.Assert;
import pojo.oAuth.Api;
import pojo.oAuth.GetCoursePojo;
import pojo.oAuth.WebAutomation;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static io.restassured.RestAssured.given;

public class GoogleSignInApi {


    public static void main(String[] args) {
        /*Sign in to Google from browser to get the Authorization Code*/
        String authEndPoint="https://accounts.google.com/o/oauth2/v2/auth";
        String scope="https://www.googleapis.com/auth/userinfo.email";
        String auth_url="https://accounts.google.com/o/oauth2/v2/auth";
        String client_id="692183103107-p0m7ent2hk7suguv4vq22hjcfhcr43pj.apps.googleusercontent.com";
        String response_type="code";
        String redirect_uri="https://rahulshettyacademy.com/getCourse.php";
        String state="verifyfjdss";

//        String authenticationURL1 = authEndPoint + scope + auth_url + client_id + response_type + redirect_uri + state;
        String authenticationURL = String.format("%s?scope=%s&auth_url=%s&client_id=%s&response_type=%s&redirect_uri=%s&state=%s",
                                        authEndPoint,scope,auth_url,client_id,response_type,redirect_uri,state);
/*
        //Selenium code to sign in on Google page
        System.setProperty("webdriver.ie.driver","D:\\workspace\\drivers\\IEDriverServer.exe");
        WebDriver driver = new InternetExplorerDriver();
//        WebDriver driver = new ChromeDriver();
        driver.manage().window().maximize();
        driver.get(authenticationURL);
        driver.findElement(By.xpath("//input[@type='email']")).sendKeys("bhupathiraj253@gmail.com");
        driver.findElement(By.xpath("//input[@type='email']")).sendKeys(Keys.ENTER);
        driver.findElement(By.xpath("//input[@type='password']")).sendKeys("Thalapathy@6");
        driver.findElement(By.xpath("//input[@type='password']")).sendKeys(Keys.ENTER);

        String authCodeURL = driver.getCurrentUrl();
        driver.close();

 */
        String urlToGetAuthCode = "https://accounts.google.com/o/oauth2/v2/auth?scope=https://www.googleapis.com/auth/userinfo.email&auth_url=https://accounts.google.com/o/oauth2/v2/auth&client_id=692183103107-p0m7ent2hk7suguv4vq22hjcfhcr43pj.apps.googleusercontent.com&response_type=code&redirect_uri=https://rahulshettyacademy.com/getCourse.php&state=verifyfjdss";

        // Manually logged in to authorization URL to get below url
        String authCodeURL = "https://rahulshettyacademy.com/getCourse.php?state=verifyfjdss&code=4%2F0AX4XfWjzSF23IQDA1upWCyeYgbs5VIfnAJWXXwJ-xZOvbB-2vZ4J4FvUidUGTVBSN6HF0Q&scope=email+https%3A%2F%2Fwww.googleapis.com%2Fauth%2Fuserinfo.email+openid&authuser=0&prompt=none";

        String partialCode = authCodeURL.split("code=")[1];
        String authorizationCode = partialCode.split("&scope=")[0];

        /* Code to get the Access Token from Google API*/
        String tokenResponse = given().urlEncodingEnabled(false).log().all()
                .queryParams("code",authorizationCode)
                .queryParams("client_id","692183103107-p0m7ent2hk7suguv4vq22hjcfhcr43pj.apps.googleusercontent.com")
                .queryParams("client_secret","erZOWM9g3UtwNRj340YYaK_W")
                .queryParams("redirect_uri","https://rahulshettyacademy.com/getCourse.php")
                .queryParams("grant_type","authorization_code")
                .when().post("https://www.googleapis.com/oauth2/v4/token")
                .then().extract().response().asString();

        JsonPath js = new JsonPath(tokenResponse);
        String accessToken = js.getString("access_token");

        System.out.println("====> Access Token is: "+ accessToken);


        /*Actual test to get the details from Google API*/
        String finalResponse = given().queryParams("access_token", accessToken)
                .when().get("https://rahulshettyacademy.com/getCourse.php").asString();

        System.out.println(finalResponse);

        /* Serialization & Deserialization of response using POJO classes */
        GetCoursePojo responseObject = given().queryParam("access_token", accessToken)
                .expect().defaultParser(Parser.JSON)
                .when().get("https://rahulshettyacademy.com/getCourse.php").as(GetCoursePojo.class);

        System.out.println("Instructor is: "+responseObject.getInstructor());
        System.out.println("Expertise is: "+responseObject.getExpertise());

        //Test #1: Get the course tile of 2nd API course
        System.out.println("2nd Api course title is: "+responseObject.getCourses().getApi().get(1).getCourseTitle());

        //Test #2: Print the price of API course 'SoapUI Webservices Testing'
        List<Api> apiCourses = responseObject.getCourses().getApi();
        List<WebAutomation> webAutomationCourses = responseObject.getCourses().getWebAutomation();

        for(int i=0; i<apiCourses.size(); i++){
            if(apiCourses.get(i).getCourseTitle().equalsIgnoreCase("SoapUI Webservices Testing")){
                System.out.println("2nd Api course prices is: "+apiCourses.get(i).getPrice());
            }
        }

        //Test #3: Print all WebAutomation course titles
        ArrayList<String> actualList = new ArrayList<String>();

        for (int i=0; i<webAutomationCourses.size(); i++){
            actualList.add(webAutomationCourses.get(i).getCourseTitle());
            System.out.println("Title of WebAutomation Course "+(i+1)+" is: "+webAutomationCourses.get(i).getCourseTitle());
        }

        //Test #4: Compare the webAutomation course titles with expected list
        List<String> expectedList = Arrays.asList("Selenium Webdriver Java","Cypress","Protractor");
        try{
            Assert.assertTrue(actualList.equals(expectedList));
            System.out.println("PASS!!! WebAutomation course titles match with expected list");
        }catch (Exception e){
            System.out.println("FAILS!!! WebAutomation course titles doest NOT match with expected list");
            System.out.println(e.getStackTrace());
        }

    }
}
