package gotocorp.catwomapp2.entity;

import com.google.android.gms.maps.model.LatLng;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Un utilisateur (servira a afficher qui est derrière les alertes, qui se déplace sur la map))
 */
public class User {
    private int id;
    private String firstname;
    private String lastname;
    private String email;
    private String posLong;
    private String posLat;
    private String posCity;
    private String posDep;
    private String posCountry;
    private String photo;


    private final static String TAG_ID ="id";
    private final static String TAG_FIRSTNAME ="firstname";
    private final static String TAG_LASTNAME ="lastname";
    private final static String TAG_EMAIL ="email";

    private final static String TAG_MAP_LONG ="position_long";
    private final static String TAG_MAP_LAT="position_lat";
    private final static String TAG_MAP_CITY="position_city";
    private final static String TAG_MAP_DEP="position_dep";
    private final static String TAG_MAP_COUNTRY="position_country";
    private final static String TAG_PHOTO="photo";

    public User() {

    }
    public User(JSONObject jsonObject) {
        try {
            this.id = Integer.parseInt(jsonObject.getString(TAG_ID));
            this.firstname = jsonObject.optString(TAG_FIRSTNAME);
            this.lastname = jsonObject.optString(TAG_LASTNAME);
            this.email = jsonObject.getString(TAG_EMAIL);
            this.posLong = jsonObject.optString(TAG_MAP_LONG);
            this.posLat = jsonObject.optString(TAG_MAP_LAT);
            this.posCity = jsonObject.optString(TAG_MAP_CITY);
            this.posDep = jsonObject.optString(TAG_MAP_DEP);
            this.posCountry = jsonObject.optString(TAG_MAP_COUNTRY);
            this.photo = jsonObject.optString(TAG_PHOTO);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }



    public String getFullName() {
        return firstname.concat(" ").concat(lastname);
    }
    public String getFirstname() {
        return firstname;
    }

    public String getLastname() {
        return lastname;
    }

    public String getEmail() {
        return email;
    }

    public int getId() {
        return id;
    }

    public String getPosLong() {
        return posLong;
    }

    public String getPosLat() {
        return posLat;
    }

    public String getPosCity() {
        return posCity;
    }

    public String getPosDep() {
        return posDep;
    }

    public String getPosCountry() {
        return posCountry;
    }

    public String getPhoto() {
        return photo;
    }

    public LatLng getLocation() {
        LatLng loc = new LatLng(Double.parseDouble(posLong),Double.parseDouble(posLat));
        return loc;
    }
}
