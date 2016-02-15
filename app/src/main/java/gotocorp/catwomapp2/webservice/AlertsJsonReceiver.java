package gotocorp.catwomapp2.webservice;

import android.os.AsyncTask;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import gotocorp.catwomapp2.adapter.AlertAdapter;
import gotocorp.catwomapp2.entity.Alert;


public class AlertsJsonReceiver extends AsyncTask<Void, Void, Void> {


    private final static String TAG_ALERTS ="alerts";


    JSONArray alertsJSON = null;
    List<Alert> alertsList;
    AlertAdapter alertAdapter;

    public AlertsJsonReceiver(List<Alert> alerts, AlertAdapter alertAdapter) {
        this.alertsList = alerts;
        this.alertAdapter = alertAdapter;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        // Showing progress dialog
//        pDialog = new ProgressDialog(MainActivity.this);
//        pDialog.setMessage("Please wait...");
//        pDialog.setCancelable(false);
//        pDialog.show();
        Log.d("question ", "pre exec");

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
                JSONArray alertsJSON = new JSONArray(jsonStr);


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
        alertAdapter.notifyDataSetChanged();

    }
}
