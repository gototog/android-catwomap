package gotocorp.catwomapp2;

import android.content.Context;
import android.content.Intent;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.util.Log;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import gotocorp.catwomapp2.entity.Alert;
import gotocorp.catwomapp2.entity.User;
import gotocorp.catwomapp2.entity.UserHelpAlert;
import gotocorp.catwomapp2.webservice.WebServiceHandler;

public class MapSingleAlertActivity extends FragmentActivity implements OnMapReadyCallback, GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener {

    private GoogleMap mMap;
    private GoogleApiClient mGoogleApiClient;
    private Alert alert;
    private User userConnected;
    private List<Marker> markers;
    public List<MarkerOptions> options;
//    private CityRepository cityRepository;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_map);
        options = new ArrayList<MarkerOptions>();




        Intent myIntent = getIntent(); // gets the previously created intent
        int alertId = myIntent.getIntExtra("alertId", 0);
        final MyGlobalCatwoman globalVariable = (MyGlobalCatwoman) getApplicationContext();
        userConnected = globalVariable.getUserConnected();

        PopulateAlertMapTask asynTask = new PopulateAlertMapTask(alertId, this);
        asynTask.execute();




//        //instancie le repository
//        cityRepository = new CityRepository(this);
//        //On ouvre la connexion avec la base de données
//        cityRepository.open();
//        alert = cityRepository.getCityByName(cityNameParameter);
    }



    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;



        Geocoder coder = new Geocoder(this);
        User user = userConnected;

        //on va essayer d'utiliser l'api de google :]
//        try {
            //valeurs
            String fullName = user.getFullName();
            String description = "Moi ";

            //geoloc
//            List<Address> address = coder.getFromLocationName(user.refreshPositionFromWebService().concat(", France"), 1);
//            Address location= address.get(0);

            //on affiche le point sur la map
            LatLng locationPoint = user.getLocation();


        for(int i =0; i< options.size(); i++) {

            Marker marker = mMap.addMarker(options.get(i));
            //le premier = l'alerte
            if(i == 0) {
                marker.showInfoWindow();//on affiche l'info directement

            }
        }



        Marker people = mMap.addMarker(new MarkerOptions().position(locationPoint)
                        .title(fullName)
                        .snippet(description)
                        .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_ORANGE))
        );

        //petit zoom entre 2(max) et 20(min)
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(locationPoint, 13));

    }

    @Override
    public void onConnectionFailed(ConnectionResult connectionResult) {
        Log.i("test", "gmap fail.");
    }

    @Override
    public void onConnected(Bundle bundle) {
        Log.i("test", "gmap connected: c'est good.");
    }

    @Override
    public void onConnectionSuspended(int i) {
        Log.i("test", "gmap suspended. tu peux reconnect");
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        overridePendingTransition(R.anim.slide_up, R.anim.slide_down);
    }

    /**
     * Represents an asynchronous login/registration task used to authenticate
     * the user.
     */
    public class PopulateAlertMapTask extends AsyncTask<Void, Void, Boolean> {

        private final int alert;
        private MapSingleAlertActivity that;
//        private List<MarkerOptions> options;

        PopulateAlertMapTask(int alertId, MapSingleAlertActivity its) {
            alert = alertId;
            that = its;
//            options = new ArrayList<MarkerOptions>();
        }

        @Override
        protected Boolean doInBackground(Void... params) {
            // Creating service handler class instance
            WebServiceHandler wsh = new WebServiceHandler();

            // Making a request to url and getting response
            String jsonStr = wsh.doGetAlertServiceCall(String.valueOf(alert));

            Log.d("Response: ", "> " + jsonStr);

            if (jsonStr != null && jsonStr != "400" ) {
                try {
                    JSONObject alertJSON = new JSONObject(jsonStr);
                    Alert alert = new Alert(alertJSON);
                    MarkerOptions markerOpt = new MarkerOptions().position(alert.getLocation())
                            .title(alert.getCategory())
                            .snippet(alert.getUserCreator().getFullName())

                            ;
                    that.options.add(markerOpt);


                    for(int i =0; i< alert.getUserHelpsAlerts().size(); i++) {
                        UserHelpAlert uHelpAlert;
                        uHelpAlert = alert.getUserHelpsAlerts().get(i);
                        User user = uHelpAlert.getUser();
                         markerOpt = new MarkerOptions().position(user.getLocation())
                                .title(user.getFullName())
                                .snippet(user.getEmail())
                                .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_VIOLET))
                                ;
                        that.options.add(markerOpt);
                    }


                    return true;
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
            return false;
        }

        @Override
        protected void onPostExecute(final Boolean success) {

            if (success) {
                //            Intent i = new Intent(MainActivity.this, MainActivity.class);
                //            startActivity(i);
            } else {

            }
// Obtain the SupportMapFragment and get notified when the map is ready to be used.
            SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                    .findFragmentById(R.id.map);
            mapFragment.getMapAsync(that);



            mGoogleApiClient = new GoogleApiClient.Builder(that)
                    .addConnectionCallbacks(that)
                    .addOnConnectionFailedListener(that)
                    .addApi(LocationServices.API)
                    .build();
        }


    }
}

