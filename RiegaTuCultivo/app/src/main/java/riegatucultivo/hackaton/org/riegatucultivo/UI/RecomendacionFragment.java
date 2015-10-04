package riegatucultivo.hackaton.org.riegatucultivo.UI;

import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import riegatucultivo.hackaton.org.riegatucultivo.Adapters.RVAdapterRecomendaciones;
import riegatucultivo.hackaton.org.riegatucultivo.Modelo.Recomendacion;
import riegatucultivo.hackaton.org.riegatucultivo.R;
import riegatucultivo.hackaton.org.riegatucultivo.Tools.Constantes;
import riegatucultivo.hackaton.org.riegatucultivo.Tools.VolleySingleton;

/**
 * Created by Sebas on 03/10/2015.
 */
public class RecomendacionFragment  extends Fragment implements View.OnClickListener {

    private RecyclerView rv_recomendaciones;
    private SwipeRefreshLayout swipeRefreshRecomendaciones;

    private RVAdapterRecomendaciones adapter;
    private RecyclerView.LayoutManager lManager;
    private ArrayList<Recomendacion> recomendacionesList;
    private JSONObject parametrosBusqueda;

    public RecomendacionFragment() {
        recomendacionesList = new ArrayList<Recomendacion>();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, final ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_recomendacion, container, false);
        rv_recomendaciones = (RecyclerView) view.findViewById(R.id.recomendaciones_recycler_view);

        // Usar un administrador para LinearLayout
        lManager = new LinearLayoutManager(getActivity());
        rv_recomendaciones.setLayoutManager(lManager);

        // Crear un nuevo adaptador
        adapter = new RVAdapterRecomendaciones(new ArrayList<Recomendacion>() );
        rv_recomendaciones.setAdapter(adapter);

        swipeRefreshRecomendaciones = (SwipeRefreshLayout) view.findViewById(R.id.swipe_refresh_servicios);
        swipeRefreshRecomendaciones.setColorSchemeResources(
                R.color.primary_color,
                R.color.s2,
                R.color.s3,
                R.color.s4
        );

        swipeRefreshRecomendaciones.setOnRefreshListener(
                new SwipeRefreshLayout.OnRefreshListener() {
                    @Override
                    public void onRefresh() {
                        obtenerDatos();
                    }
                }
        );

        // gracias a esto obtenemos la locación con el gps
        obtenerDatos();

        return view;
    }


    @Override
    public void onClick(View v) {

    }




    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        // Do something that differs the Activity's menu here
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        /*switch (item.getItemId()) {
            case R.id.action_search:
                Intent in = new Intent(getActivity(), BuscadorActivity.class);
                startActivityForResult(in, REQUEST_CODE_BUSCADOR);
                return true;
            default:
                break;
        }*/

        return false;
    }


    /**
     * Carga el adaptador con las metas obtenidas
     * en la respuesta
     */
    public void obtenerDatos() {
        swipeRefreshRecomendaciones.setRefreshing(true);
        String url = Constantes.GET_SENSORES;
            final JsonObjectRequest jsonObjReq = new JsonObjectRequest(Request.Method.GET,
                    url , null, new Response.Listener<JSONObject>() {

                @Override
                public void onResponse(JSONObject response) {
                    procesarRespuesta(response);
                }
            }, new Response.ErrorListener() {

                @Override
                public void onErrorResponse(VolleyError error) {
                    Log.d(MainActivity.class.getSimpleName().toString(), "Volley Error: " + error.getMessage());
                }
            }) {

                @Override
                public Map<String, String> getHeaders() throws AuthFailureError {
                    HashMap<String, String> headers = new HashMap<String, String>();
                    headers.put("Content-Type", "application/json; charset=utf-8");
                    headers.put("Accept", "application/json");
                    return headers;
                }
                @Override
                public String getBodyContentType() {
                    return "application/json; charset=utf-8" + getParamsEncoding();
                }
            };

            VolleySingleton.getInstance(getActivity()).addToRequestQueue(jsonObjReq);
    }

    /**
     * Interpreta los resultados de la respuesta y así
     * realizar las operaciones correspondientes
     *
     * @param response Objeto Json con la respuesta
     */
    private void procesarRespuesta(JSONObject response) {
        try {
            // Obtener atributo "estado"
            String estado = null;
            try {
                estado = response.getString("clave");
            } catch (JSONException e) {
                e.printStackTrace();
            }

            switch (estado) {
                case "OK": // EXITO
                    if ( response.getJSONArray("sensores") != null) {
                        JSONArray respuestaServicios_JSONarray = response.getJSONArray("sensores");
                        recomendacionesList.clear();
                        /*for (int i = 0; i < respuestaServicios_JSONarray.length(); i++) {
                            recomendacionesList.add(new Sensor(respuestaServicios_JSONarray.getJSONObject(i)));
                        }*/
                        // establecemos el adapter con las recomendaciones
                    //    setAdapterRecomendaciones(recomendacionesList);
                    }
                    break;
                case "KO": // FALLIDO
                    adapter.clear();
                    Snackbar.make(getView(), "no hay datos", Snackbar.LENGTH_LONG).show();
                    break;
                default:
                    adapter.clear();
                    Snackbar.make(getView(), "no existen", Snackbar.LENGTH_LONG).show();
                    break;
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }

        // Parar la animación del indicador
        swipeRefreshRecomendaciones.setRefreshing(false);

    }




    protected void setEnableRefresh(boolean enableRefresh){
        swipeRefreshRecomendaciones.setEnabled(enableRefresh);
    }

}
