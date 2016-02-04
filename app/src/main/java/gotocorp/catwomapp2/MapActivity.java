package gotocorp.catwomapp2;

import android.content.Intent;
import android.location.Address;
import android.location.Geocoder;
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
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import java.io.IOException;
import java.util.List;

import gotocorp.catwomapp2.entity.Alert;

public class MapActivity extends FragmentActivity implements OnMapReadyCallback, GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener {

    private GoogleMap mMap;
    private GoogleApiClient mGoogleApiClient;
    private Alert alert;
//    private CityRepository cityRepository;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_map);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        Intent myIntent = getIntent(); // gets the previously created intent
        String cityNameParameter = myIntent.getStringExtra("cityName");

        mGoogleApiClient = new GoogleApiClient.Builder(this)
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .addApi(LocationServices.API)
                .build();


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
        LatLng locationPoint = new LatLng(48.05, 12.85); //la ville fucking, en autriche xD (ma valeur par defaut)
        String cityName = "Fucking";
        String description = "Oui oui, c'est une exception...";
        //on va essayer d'utiliser l'api :]
        try {
            //valeurs
            cityName = alert.getName();
            description = "Nombre d'habitants: ".concat( alert.getMapCoordinate() );

            //geoloc
            List<Address> address = coder.getFromLocationName(cityName.concat(", France"),1 );
            Address location= address.get(0);
            location.getLatitude();
            location.getLongitude();

            //on affiche le point sur la map
            locationPoint = new LatLng(location.getLatitude(), location.getLongitude());


        } catch (IOException e) {
            Log.e("gecoloc", "getFromLocationName: got RemoteException", e);
        }



        Marker city = mMap.addMarker(new MarkerOptions().position(locationPoint)
                        .title(cityName)
                        .snippet(description)
        );
        city.showInfoWindow();//on affiche l'info directement
        //petit zoom entre 2(max) et 20(min)
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(locationPoint, 10));

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
}

