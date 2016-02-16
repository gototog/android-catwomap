//package gotocorp.catwomapp2.webservice;
//
//import android.os.AsyncTask;
//import android.util.Log;
//
//import org.json.JSONArray;
//import org.json.JSONException;
//import org.json.JSONObject;
//
//import java.util.List;
//
//import gotocorp.catwomapp2.adapter.AlertAdapter;
//import gotocorp.catwomapp2.entity.Alert;
//import gotocorp.catwomapp2.entity.User;
//
//
//public class AuthenticateJsonReceiver extends AsyncTask<Void, Void, Void> {
//
//
//    private final static String TAG_ALERTS ="alerts";
//
//
//    JSONArray alertsJSON = null;
//    User user;
//    AlertAdapter alertAdapter;
//    String email, pass;
//
//    public AuthenticateJsonReceiver(User user, String email, String pass ) {
//        this.user = user;
//        this.email = email;
//        this.pass = pass;
//
//    }
//    public AuthenticateJsonReceiver(String email, String pass ) {
//        this.email = email;
//        this.pass = pass;
//
//    }
//
//    @Override
//    protected void onPreExecute() {
//        super.onPreExecute();
//        // Showing progress dialog
//
//    }
//
//
//    @Override
//    protected Void doInBackground(Void... arg0) {
//        // Creating service handler class instance
//        WebServiceHandler wsh = new WebServiceHandler();
//
//        // Making a request to url and getting response
//        String jsonStr = wsh.doAuthenticateServiceCall(email, pass);
//
//        Log.d("Response: ", "> " + jsonStr);
//
//        if (jsonStr != null && jsonStr != "400" ) {
//            try {
//                JSONObject userJSON = new JSONObject(jsonStr);
//
//
//                // looping through All alerts
////                for (int i = 0; i < alertsJSON.length(); i++) {
////                    JSONObject jsonObject = alertsJSON.getJSONObject(i);
//
//                    // tmp hashmap for single contact
//                    user = new User(userJSON);
//
//
////                }
//            } catch (JSONException e) {
//                e.printStackTrace();
//            }
//        } else {
//            Log.e("WebServiceHandler", "Couldn't get any data from the url");
//        }
//
//        return null;
//    }
//
//    @Override
//    protected void onPostExecute(Void result) {
//        super.onPostExecute(result);
//
//    }
//}
