package gotocorp.catwomapp2.entity;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * La classe Alertes, composant principal de l'appli.
 */
public class Alert {
    private int id;
    private String name;
    private String posLong;
    private String posLat;
    private String posCity;
    private String posDep;
    private String posCountry;
    private String category;
    private String status;

    private final static String TAG_ID ="id";

    private final static String TAG_NAME ="name";
    private final static String TAG_MAP_LONG ="position_long";
    private final static String TAG_MAP_LAT="position_lat";
    private final static String TAG_MAP_CITY="position_city";
    private final static String TAG_MAP_DEP="position_dep";
    private final static String TAG_MAP_COUNTRY="position_country";
    private final static String TAG_STATUS="status";
    private final static String TAG_CATEGORY="category";


    public Alert(JSONObject jsonObject) {
        try {
            this.id = Integer.parseInt(jsonObject.getString(TAG_ID));
            this.name = jsonObject.optString(TAG_NAME);
            this.posLong = jsonObject.getString(TAG_MAP_LONG);
            this.posLat = jsonObject.getString(TAG_MAP_LAT);
            this.posCity = jsonObject.getString(TAG_MAP_CITY);
            this.posDep = jsonObject.getString(TAG_MAP_DEP);
            this.posCountry = jsonObject.getString(TAG_MAP_COUNTRY);
            this.status = jsonObject.getString(TAG_STATUS);
            this.category = jsonObject.getString(TAG_CATEGORY);
        } catch (JSONException e) {
            e.printStackTrace();
        }

    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLatLong() {
        return posLat.concat(",").concat(posLong);
    }



    public String getPosLong() {
        return posLong;
    }

    public void setPosLong(String posLong) {
        this.posLong = posLong;
    }

    public String getPosLat() {
        return posLat;
    }

    public void setPosLat(String posLat) {
        this.posLat = posLat;
    }

    public String getPosCity() {
        return posCity;
    }

    public void setPosCity(String posCity) {
        this.posCity = posCity;
    }

    public String getPosDep() {
        return posDep;
    }

    public void setPosDep(String posDep) {
        this.posDep = posDep;
    }

    public String getPosCountry() {
        return posCountry;
    }

    public void setPosCountry(String posCountry) {
        this.posCountry = posCountry;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
