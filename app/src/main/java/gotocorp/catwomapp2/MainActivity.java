package gotocorp.catwomapp2;

import android.content.Context;
import android.content.Intent;
import android.location.Address;
import android.location.Criteria;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import gotocorp.catwomapp2.adapter.AlertAdapter;
import gotocorp.catwomapp2.entity.Alert;
import gotocorp.catwomapp2.entity.User;
import gotocorp.catwomapp2.webservice.AlertsJsonReceiver;
import gotocorp.catwomapp2.webservice.WebServiceHandler;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {
    private User userConnected;
    AlertAdapter alertAdapter;
    List<Alert> alerts;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        alerts = new ArrayList<Alert>();

        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        //On initialise nos villes si on a la bdd vide (premier lancement de l'appli)

        alertAdapter = new AlertAdapter(
                this,
//                R.layout.activity_sql_lite1, //TODO comprendre ce paramètre (apparement le style d'affichage de la liste view)
                alerts
        );
        AlertsJsonReceiver receiver = new AlertsJsonReceiver(alerts, alertAdapter);
        receiver.execute();

        // on set l'adapter de la liste
        ListView listViewAlert = (ListView) findViewById(R.id.alertListView);
        listViewAlert.setAdapter(alertAdapter);

        final MyGlobalCatwoman globalVariable = (MyGlobalCatwoman) getApplicationContext();
        userConnected = globalVariable.getUserConnected();

        Button btnAlert = (Button) findViewById(R.id.btnAlert);
        btnAlert.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Location location = getLocalisation();
                UserCreateAlertTask task = new UserCreateAlertTask(userConnected.getId(), location);
                task.execute();
            }
        });
        Button btnRefresh = (Button) findViewById(R.id.btnRefresh);
        btnRefresh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertsJsonReceiver receiver = new AlertsJsonReceiver(alerts, alertAdapter);
                receiver.execute();
            }
        });

    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_manage) {

        } else if (id == R.id.nav_share) {

        } else if (id == R.id.nav_send) {

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }



    public Location getLocalisation() {

            Geocoder geocoder;
            String bestProvider;
            List<Address> user = null;
            double lat;
            double lng;
            Location location;

            LocationManager lm = (LocationManager) this.getSystemService(Context.LOCATION_SERVICE);
            try {

            Criteria criteria = new Criteria();
            bestProvider = lm.getBestProvider(criteria, false);
            location = lm.getLastKnownLocation(bestProvider);

            if (location == null){
            }else{
                geocoder = new Geocoder(this);
                try {
                    user = geocoder.getFromLocation(location.getLatitude(), location.getLongitude(), 1);
                    lat=(double)user.get(0).getLatitude();
                    lng=(double)user.get(0).getLongitude();
                    location.setLatitude(lat);
                    location.setLatitude(lng);

                }catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }catch (SecurityException e) {
            e.printStackTrace();
                location = null;
        }

        return location;

    }

    /**
     * Represents an asynchronous login/registration task used to authenticate
     * the user.
     */
    public class UserCreateAlertTask extends AsyncTask<Void, Void, Boolean> {

        private final int user;
        private final Location location;
        private final String categorie;

        UserCreateAlertTask(int userId, Location loc) {
            user = userId;
            location = loc;
            categorie = "help";
        }

        @Override
        protected Boolean doInBackground(Void... params) {
            // Creating service handler class instance
            WebServiceHandler wsh = new WebServiceHandler();

            // Making a request to url and getting response
            String jsonStr = wsh.doCreateAlertServiceCall(user, location, categorie);

            Log.d("Response: ", "> " + jsonStr);

            if (jsonStr != null && jsonStr != "400" ) {
                try {
                    JSONObject userJSON = new JSONObject(jsonStr);

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
        }


    }
}