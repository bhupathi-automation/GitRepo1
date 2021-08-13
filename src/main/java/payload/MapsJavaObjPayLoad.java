package payload;

import pojo.maps.AddPlacePojo;
import pojo.maps.Location;

import java.util.Arrays;

public class MapsJavaObjPayLoad {

    public static AddPlacePojo addPlace(){
        AddPlacePojo addPlacePayLoad = new AddPlacePojo();
        Location locationObj = new Location();

        String[] types = {"Fast Food","Restaurant1"};
        locationObj.setLat(-38.383494);
        locationObj.setLng(33.427362);

        addPlacePayLoad.setAccuracy(50);
        addPlacePayLoad.setName("FnF Fast Food");
        addPlacePayLoad.setPhone_number("+(91) 99887 66554");
        addPlacePayLoad.setAddress("28, Madhapur, Hyderabad-3");
        addPlacePayLoad.setWebsite("https://rahulshettyacademy.com");
        addPlacePayLoad.setLanguage("French-IN");

        addPlacePayLoad.setTypes(Arrays.asList(types));
        addPlacePayLoad.setLocation(locationObj);

        return addPlacePayLoad;
    }


}
