package testcases.general;

import payload.PayLoadMaps;
import io.restassured.path.json.JsonPath;
import org.testng.annotations.Test;

public class ComplexJsonParse {
    /* Workout questions on Nested Json
     *  1. Print no. of courses returned by API
     *  2. Print purchase amount
     *  3. Print Title of first course
     *  4. Print all course titles and their respective Prices
     *  5. Print no. of copies sold by RPA course
     *  6. Verify if sum of all course prices matches with purchase amount */

    private JsonPath js = new JsonPath(PayLoadMaps.coursePrice());

    private int totalAmount;
    private int totalNoOfCourses;

    @Test (priority =0)
    public void simpleJsonFieldsReading(){
        /* This addresses first 3 questions */
        totalAmount = js.getInt("dashboard.purchaseAmount");
        totalNoOfCourses = js.getInt("courses.size");

        String courseOneTitle = js.get("courses[0].title");

        System.out.println("Total purchase amount is: "+ totalAmount);
        System.out.println("Total no. of courses are: "+ totalNoOfCourses);
        System.out.println("Title of first course is: "+ courseOneTitle);

        int totalSalesOfCourseOne = js.getInt("courses[0].price") * js.getInt("courses[0].copies");
        System.out.println("totalSalesOfCourseOne: "+ totalSalesOfCourseOne);

    }

    @Test (priority = 1)
    public void printCourseTitleAndPrice(){
        /* 4. Print all course titles and their respective Prices */
        System.out.println("\n====> Below are the course titles & their prices in format of \"Title\" : \"Price\" ");

        for(int i=0; i<totalNoOfCourses; i++){
            System.out.println(js.get("courses["+i+"].title") + " : " +js.get("courses["+i+ "].price").toString());
        }
    }

    @Test (priority = 2)
    public void numberOfCopiesSoldByRPA(){
        /* 5. Print no. of copies sold by RPA course */
        for(int i=0; i<totalNoOfCourses; i++){
            if(js.get("courses["+i+"].title").toString().equalsIgnoreCase("RPA")){
                System.out.println("====> Total no. of copies sold by RPA is: "+ js.get("courses["+i+"].copies"));
                break;
            }
        }
    }

    @Test(priority = 3)
    public void sumValidation(){
        /* 6. Verify if sum of all course prices matches with purchase amount */
        int sum=0;
        int price, copies;
        for(int i=0; i<totalNoOfCourses; i++){
            price = js.getInt("courses["+i+"].price");
            copies = js.getInt("courses["+i+"].copies");
            sum = sum + (price * copies) ;
        }
        System.out.println("total sum is : "+sum);
        if(sum == totalAmount){
            System.out.println("====> Sum of all course prices matches with purchase amount of "+totalAmount);
        } else{
            System.out.println("====> Sum of all course prices does NOT matches with purchase amount of "+totalAmount);
        }
    }
}
