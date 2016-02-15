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


    private final static String TAG_ID ="id";
    private final static String TAG_FIRSTNAME ="firstname";
    private final static String TAG_LASTNAME ="lastname";
    private final static String TAG_EMAIL ="email";

    private final static String TAG_MAP_LONG ="position_long";
    private final static String TAG_MAP_LAT="position_lat";
    private final static String TAG_MAP_CITY="position_city";
    private final static String TAG_MAP_DEP="position_dep";
    private final static String TAG_MAP_COUNTRY="position_country";

    public User() {

    }
    public User(JSONObject jsonObject) {
        try {
            this.id = Integer.parseInt(jsonObject.getString(TAG_ID));
            this.firstname = jsonObject.getString(TAG_FIRSTNAME);
            this.lastname = jsonObject.getString(TAG_LASTNAME);
            this.email = jsonObject.getString(TAG_EMAIL);
            this.posLong = jsonObject.getString(TAG_MAP_LONG);
            this.posLat = jsonObject.getString(TAG_MAP_LAT);
            this.posCity = jsonObject.getString(TAG_MAP_CITY);
            this.posDep = jsonObject.getString(TAG_MAP_DEP);
            this.posCountry = jsonObject.getString(TAG_MAP_COUNTRY);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public void initFromWebService() {
        String email = "test@email.test";
        String firstname = "John";
        String lastname = "Doe";
        Integer id = 1337;
        this.id = id;
        this.email = email;
        this.firstname = firstname;
        this.lastname = lastname;
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
}
