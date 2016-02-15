package gotocorp.catwomapp2.webservice;

import android.os.AsyncTask;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;

import gotocorp.catwomapp2.entity.Alert;


public class AlertsJsonReceiver extends AsyncTask<Void, Void, Void> {


    private final static String TAG_ALERTS ="alerts";


    JSONArray alertsJSON = null;
    List<Alert> alertsList;

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        // Showing progress dialog
//        pDialog = new ProgressDialog(MainActivity.this);
//        pDialog.setMessage("Please wait...");
//        pDialog.setCancelable(false);
//        pDialog.show();

    }

    // URL to get contacts JSON


    @Override
    protected Void doInBackground(Void... arg0) {
        // Creating service handler class instance
        WebServiceHandler wsh = new WebServiceHandler();

        // Making a request to url and getting response
        String jsonStr = wsh.doGetAlertsServiceCall();

        Log.d("Response: ", "> " + jsonStr);

        if (jsonStr != null) {
            try {
                JSONObject jsonObj = new JSONObject(jsonStr);

                // Getting JSON Array node
                alertsJSON = jsonObj.getJSONArray(TAG_ALERTS);

                // looping through All alerts
                for (int i = 0; i < alertsJSON.length(); i++) {
                    JSONObject jsonObject = alertsJSON.getJSONObject(i);

                    // tmp hashmap for single contact
                    Alert alert = new Alert(jsonObject);

                    // adding contact to contact list
                    alertsList.add(alert);
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        } else {
            Log.e("WebServiceHandler", "Couldn't get any data from the url");
        }

        return null;
    }

    @Override
    protected void onPostExecute(Void result) {
        super.onPostExecute(result);
        // Dismiss the progress dialog
//        if (pDialog.isShowing())
//            pDialog.dismiss();
        /**
         * Updating parsed JSON data into ListView
         * */
//        ListAdapter adapter = new SimpleAdapter(
//                MainActivity.this, alertsList,
//                R.layout._alerts_list, new String[]{TAG_NAME, TAG_EMAIL,
//                TAG_PHONE_MOBILE}, new int[]{R.id.name,
//                R.id.email, R.id.mobile});
//
//        setListAdapter(adapter);
    }
}
