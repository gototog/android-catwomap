package gotocorp.catwomapp2.entity;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by Goto on 16/02/2016.
 */
public class UserHelpAlert {

    int id;
    User user;
    Alert alert;

    private final static String TAG_ID ="id";


    public UserHelpAlert(JSONObject jsonObject) {
        try {

            this.id = Integer.parseInt(jsonObject.getString(TAG_ID));

            if(jsonObject.has("user")) {
                user= new User( jsonObject.getJSONObject("user") );
            }


        } catch (JSONException e) {
            e.printStackTrace();
        }

    }

    public int getId() {
        return id;
    }

    public User getUser() {
        return user;
    }

    public Alert getAlert() {
        return alert;
    }
}
