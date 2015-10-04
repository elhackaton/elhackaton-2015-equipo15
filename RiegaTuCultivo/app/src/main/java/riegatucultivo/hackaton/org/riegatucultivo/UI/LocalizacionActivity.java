package riegatucultivo.hackaton.org.riegatucultivo.UI;

import android.content.Context;
import android.content.Intent;
import android.location.Address;
import android.location.Geocoder;
import android.location.LocationManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;

import riegatucultivo.hackaton.org.riegatucultivo.Modelo.GeoPunto;
import riegatucultivo.hackaton.org.riegatucultivo.R;
import riegatucultivo.hackaton.org.riegatucultivo.Tools.PlaceAutocompleteAdapter;

/**
 * Activity encargada para buscar y seleccionar una localización, se podrá hacer tanto por mapa como por buscador.
 * Utiliza GoogleMap y API_PLACES_MAP
 */
public class LocalizacionActivity extends AppCompatActivity implements GoogleMap.OnMapClickListener, AdapterView.OnItemClickListener, GoogleMap.OnMyLocationButtonClickListener {

    private GoogleMap mapa;
    private Button btnMyPosition, btnAddMarket;
    private DireccionLocalizacion localizacionSeleccionada;
    private TextView info;
    private Toolbar barra;

    /**
     * GoogleApiClient wraps our service connection to Google Play Services and provides access
     * to the user's sign in state as well as the Google's APIs.
     */
    protected GoogleApiClient mGoogleApiClient;

    private PlaceAutocompleteAdapter mAdapter;

    private AutoCompleteTextView mAutocompleteView;

    private static final String PLACES_API_BASE = "https://maps.googleapis.com/maps/api/place";
    private static final String TYPE_AUTOCOMPLETE = "/autocomplete";
    private static final String OUT_JSON = "/json";

    //------------ make your specific key ------------
    private static final String API_KEY = "AIzaSyCr9yCVY4W2ABnMTp3nUKciTFSXrJjDTno";

    private LocationManager locationManager;

    ArrayList<String> resultList = new ArrayList<String>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_localizacion);

        barra = (Toolbar) findViewById(R.id.toolbar_localizacion);
        setSupportActionBar(barra);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.abc_ic_clear_mtrl_alpha);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);


        mapa = ((SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.google_map_localizacion)).getMap();
     //   mapa.setMapType(GoogleMap.MAP_TYPE_SATELLITE);

        locationManager = (LocationManager) getSystemService(LOCATION_SERVICE);

        btnMyPosition = (Button) findViewById(R.id.maps_mi_posicion);
     //   btnAddMarket = (Button) findViewById(R.id.maps_add_marcador);
        info = (TextView) findViewById(R.id.maps_info);


        mAutocompleteView = (AutoCompleteTextView) findViewById(R.id.autocomplete_places);

        mAutocompleteView.setAdapter(new GooglePlacesAutocompleteAdapter(this, android.R.layout.simple_list_item_1 /*R.layout.list_item*/));
        mAutocompleteView.setOnItemClickListener(this);



    }

    @Override
    public void onStart(){
        super.onStart();
        if (mapa!=null && comprobarGPSactivo()){
            mapa.setMyLocationEnabled(true);
            mapa.getUiSettings().setZoomControlsEnabled(false);
            mapa.getUiSettings().setCompassEnabled(true);
            mapa.setOnMapClickListener(this);
            mapa.setOnMyLocationButtonClickListener(myLocationButtonClickListener);
            mapa.setOnMapLoadedCallback(loadedCallback);
            btnMyPosition.setVisibility(View.VISIBLE);
            LatLng puntoPulsado = new LatLng(36.837085, -2.46467);
            mapa.addMarker(new MarkerOptions().position(puntoPulsado).
                    icon(BitmapDescriptorFactory
                            .defaultMarker(BitmapDescriptorFactory.HUE_YELLOW)));

            mapa.animateCamera(CameraUpdateFactory.newLatLngZoom(puntoPulsado, 15));
        } else {
            btnMyPosition.setVisibility(View.GONE);
            LatLng puntoPulsado = new LatLng(36.837085, -2.46467);
            LatLng puntoPulsado2 = new LatLng(36.807085, -2.46450);

            mapa.addMarker(new MarkerOptions().position(puntoPulsado2)
                    .title("Sensor Hª").snippet("50%").icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_YELLOW)));

            mapa.addMarker(new MarkerOptions().position(puntoPulsado)
                    .title("Sensor Tª").snippet("25 ºC")).showInfoWindow();


            mapa.animateCamera(CameraUpdateFactory.newLatLngZoom(puntoPulsado, 15));

        }
    }

    private GoogleMap.OnMapLoadedCallback loadedCallback = new GoogleMap.OnMapLoadedCallback(){

        @Override
        public void onMapLoaded() {
            if(mapa != null)
                animateCamera(null);
        }
    };

    private GoogleMap.OnMyLocationButtonClickListener myLocationButtonClickListener = new GoogleMap.OnMyLocationButtonClickListener(){

        @Override
        public boolean onMyLocationButtonClick() {
            if (mapa != null)
                animateCamera(null);
            return true;
        }
    };



    private boolean comprobarGPSactivo(){
        return locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER) || locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER);
    }


    /**
     * Mover a una determinada coordenada
     * @param latLng
     * @param zoom
     */
    public void irA(LatLng latLng, int zoom) {
        if (latLng != null) {
            mapa.animateCamera(CameraUpdateFactory.newLatLngZoom(latLng, zoom));
        }

    }



    public void toast(String mensaje){
        Toast.makeText(getApplicationContext(), mensaje, Toast.LENGTH_LONG).show();
    }

    public void animateCamera(View view) {
        if (mapa.getMyLocation() != null) {
            irA(new LatLng(mapa.getMyLocation().getLatitude(), mapa.getMyLocation().getLongitude()), 16);
        //    mapa.animateCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(mapa.getMyLocation().getLatitude(), mapa.getMyLocation().getLongitude()), 16));

            double latitud = mapa.getMyLocation().getLatitude();
            double longitud = mapa.getMyLocation().getLongitude();

            guardarDireccion("", new LatLng(latitud, longitud));
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_localizacion, menu);
        return true;
    }

    public void addMarker(View view) {
        mapa.addMarker(new MarkerOptions().position(
                new LatLng(mapa.getCameraPosition().target.latitude,
                        mapa.getCameraPosition().target.longitude)));

    }

    @Override
    public void onMapClick(LatLng puntoPulsado) {
        mapa.clear();
    //    guardarDireccion("", puntoPulsado);
        mapa.addMarker(new MarkerOptions().position(puntoPulsado).
                icon(BitmapDescriptorFactory
                        .defaultMarker(BitmapDescriptorFactory.HUE_YELLOW)));
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.


        switch (item.getItemId()){
            case R.id.action_save:
            {
                if (localizacionSeleccionada == null){
                    Toast.makeText(getApplicationContext(), getString(R.string.error_seleccionar_localizacion), Toast.LENGTH_LONG).show();
                } else {
                /*    Intent data = new Intent();
                    Bundle bundle = new Bundle();
                    bundle.putSerializable("localizacion", localizacionSeleccionada);
                    data.putExtras(bundle);
                    setResult(10, data);
                    finish();
*/                }

                return true;

            }
            case android.R.id.home:
            {
                Intent data = new Intent();
                setResult(0, data);
                finish();
                return true;
            }
        }

        return super.onOptionsItemSelected(item);
    }


    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

        String str = (String) parent.getItemAtPosition(position);
        guardarDireccion(str, null);

        Toast.makeText(this, str, Toast.LENGTH_SHORT).show();
    }

    /**
     * Almacena la nueva dirección en el objecto localizacionSeleccioanda
     * @param direccionSeleccionada
     */
    private void guardarDireccion(String direccionSeleccionada, LatLng coordenada){
        Geocoder coder = new Geocoder(getApplicationContext());
        List<Address> address;
        Address location = null;
        if(coordenada != null){
            try {
            if (coder.getFromLocation( coordenada.latitude, coordenada.longitude, 1).size() > 0){
                location = coder.getFromLocation( coordenada.latitude, coordenada.longitude, 1).get(0);
            }


            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            try {
                address = coder.getFromLocationName(direccionSeleccionada, 1);
                location = address.get(0);
                location = coder.getFromLocation( location.getLatitude(), location.getLongitude(), 1).get(0);

            } catch (IOException e) {

            }
        }

        if(location !=null) {
            try {
                location = coder.getFromLocation( location.getLatitude(), location.getLongitude(), 1).get(0);
            //    info.setText("Localidad: " + location.getLocality() + " , Lat y Long " + location.getLatitude() + " " + location.getLongitude() + " " + location.getPostalCode());
                localizacionSeleccionada = new DireccionLocalizacion();
                // Almacenamos la nueva localizacion seleccionada
                localizacionSeleccionada.setProvincia(location.getSubAdminArea());
                localizacionSeleccionada.setLocalidad(location.getLocality());
                if (location.getPostalCode() != null ){
                    localizacionSeleccionada.setCp(Integer.parseInt(location.getPostalCode()));
                }
                localizacionSeleccionada.setCoordenada(new GeoPunto(location.getLatitude(), location.getLongitude()));

                irA(new LatLng(location.getLatitude(), location.getLongitude()), 14);
            } catch (IOException e) {
                e.printStackTrace();
            }


        }


    }


    public  ArrayList<String> autocomplete(String input) {
     //   ArrayList<String> resultList = null;

        HttpURLConnection conn = null;
        StringBuilder jsonResults = new StringBuilder();
        try {
            StringBuilder sb = new StringBuilder(PLACES_API_BASE + TYPE_AUTOCOMPLETE + OUT_JSON);
            sb.append("?key=" + API_KEY);
            sb.append( "&components=country:es"+ /* "&types=(cities) "*/"&types=geocode");
            sb.append("&input=" + URLEncoder.encode(input, "utf8"));

            URL url = new URL(sb.toString());

            System.out.println("URL: "+url);
            conn = (HttpURLConnection) url.openConnection();
            InputStreamReader in = new InputStreamReader(conn.getInputStream());

            // Load the results into a StringBuilder
            int read;
            char[] buff = new char[1024];
            while ((read = in.read(buff)) != -1) {
                jsonResults.append(buff, 0, read);
            }
        } catch (MalformedURLException e) {
        //    android.util.Log.e(LOG_TAG, "Error processing Places API URL", e);
            return resultList;
        } catch (IOException e) {
       //     android.util.Log.e(LOG_TAG, "Error connecting to Places API", e);
            return resultList;
        } finally {
            if (conn != null) {
                conn.disconnect();
            }
        }

        try {

            // Create a JSON object hierarchy from the results
            JSONObject jsonObj = new JSONObject(jsonResults.toString());
            JSONArray predsJsonArray = jsonObj.getJSONArray("predictions");

            // Extract the Place descriptions from the results
            resultList = new ArrayList<String>(predsJsonArray.length());
            for (int i = 0; i < predsJsonArray.length(); i++) {
                System.out.println(predsJsonArray.getJSONObject(i).getString("description"));
                System.out.println("============================================================");
                resultList.add(predsJsonArray.getJSONObject(i).getString("description"));
            }
        } catch (JSONException e) {
    //        android.util.Log.e(LOG_TAG, "Cannot process JSON results", e);
        }


  //      new TareaAutocompletarMAPS(this).execute(input);

        return resultList;
    }

    @Override
    public boolean onMyLocationButtonClick() {
        return false;
    }

    class GooglePlacesAutocompleteAdapter extends ArrayAdapter<String> implements Filterable {
        private ArrayList<String> resultList;

        public GooglePlacesAutocompleteAdapter(Context context, int textViewResourceId) {
            super(context, textViewResourceId);
        }

        @Override
        public int getCount() {
            return resultList.size();
        }

        @Override
        public String getItem(int index) {
            return resultList.get(index);
        }

        @Override
        public Filter getFilter() {
            Filter filter = new Filter() {
                @Override
                protected FilterResults performFiltering(CharSequence constraint) {
                    FilterResults filterResults = new FilterResults();
                    if (constraint != null) {
                        // Retrieve the autocomplete results.
                        resultList = autocomplete(constraint.toString());

                        // Assign the data to the FilterResults
                        filterResults.values = resultList;
                        filterResults.count = resultList.size();
                    }
                    return filterResults;
                }

                @Override
                protected void publishResults(CharSequence constraint, FilterResults results) {
                    if (results != null && results.count > 0) {
                        notifyDataSetChanged();
                    } else {
                        notifyDataSetInvalidated();
                    }
                }
            };
            return filter;
        }
    }


    // Tarea Asíncrona para llamar al WS de consulta en segundo plano
    private class TareaAutocompletarMAPS extends AsyncTask<String, String, ArrayList> {

        private static final String PLACES_API_BASE = "https://maps.googleapis.com/maps/api/place";
        private static final String TYPE_AUTOCOMPLETE = "/autocomplete";
        private static final String OUT_JSON = "/json";

        //------------ make your specific key ------------
        private static final String API_KEY = "AIzaSyCr9yCVY4W2ABnMTp3nUKciTFSXrJjDTno";

        private Context context;

        public TareaAutocompletarMAPS(Context ctx){
            this.context = ctx;
        }

        protected ArrayList doInBackground(String... params) {
            ArrayList resultList = null;

            HttpURLConnection conn = null;
            StringBuilder jsonResults = new StringBuilder();
            try {
                StringBuilder sb = new StringBuilder(PLACES_API_BASE + TYPE_AUTOCOMPLETE + OUT_JSON);
                sb.append("?key=" + API_KEY);
                sb.append( "&components=country:es"+ /*"&types=(cities)*/ "&types=geocode&language=es_ES");
//                sb.append("&components=country:es&language=es_ES");
                sb.append("&input=" + URLEncoder.encode(params[0], "utf8"));
                URL url = new URL(sb.toString());
                conn = (HttpURLConnection) url.openConnection();
                InputStreamReader in = new InputStreamReader(conn.getInputStream());

                // Load the results into a StringBuilder
                int read;
                char[] buff = new char[1024];
                while ((read = in.read(buff)) != -1) {
                    jsonResults.append(buff, 0, read);
                }
            } catch (MalformedURLException e) {
                return resultList;
            } catch (IOException e) {
                return resultList;
            } finally {
                if (conn != null) {
                    conn.disconnect();
                }
            }

            try {
                // Create a JSON object hierarchy from the results
                JSONObject jsonObj = new JSONObject(jsonResults.toString());
                JSONArray predsJsonArray = jsonObj.getJSONArray("predictions");

                // Extract the Place descriptions from the results
                resultList = new ArrayList(predsJsonArray.length());
                for (int i = 0; i < predsJsonArray.length(); i++) {
                    System.out.println(predsJsonArray.getJSONObject(i).getString("description"));
                    System.out.println("============================================================");
                    resultList.add(predsJsonArray.getJSONObject(i).getString("description"));
                }
            } catch (JSONException e) {

            }
            return resultList;
        }

        protected void onPostExecute(ArrayList result) {
            if (result != null) {
                ((LocalizacionActivity) context).resultList.addAll(result);


            }
        }
    }

    // ocultar teclado
    @Override
    public boolean dispatchTouchEvent(MotionEvent event) {
        View view = getCurrentFocus();
        boolean ret = super.dispatchTouchEvent(event);

        if (view instanceof EditText) {
            View w = getCurrentFocus();
            int scrcoords[] = new int[2];
            w.getLocationOnScreen(scrcoords);
            float x = event.getRawX() + w.getLeft() - scrcoords[0];
            float y = event.getRawY() + w.getTop() - scrcoords[1];

            if (event.getAction() == MotionEvent.ACTION_UP
                    && (x < w.getLeft() || x >= w.getRight()
                    || y < w.getTop() || y > w.getBottom()) ) {
                InputMethodManager imm = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(getWindow().getCurrentFocus().getWindowToken(), 0);
            }
        }
        return ret;
    }


}