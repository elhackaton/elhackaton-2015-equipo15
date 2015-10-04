package riegatucultivo.hackaton.org.riegatucultivo.UI;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.view.ActionMode;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import org.json.JSONArray;

import java.util.ArrayList;
import java.util.List;

import riegatucultivo.hackaton.org.riegatucultivo.Adapters.RVAdapterMisSectores;
import riegatucultivo.hackaton.org.riegatucultivo.Modelo.Sector;
import riegatucultivo.hackaton.org.riegatucultivo.R;

/**
 * Fragment para mostrar los servicios de un usuario
 */
public class MisSectoresFragment extends Fragment implements  RVAdapterMisSectores.ViewHolder.ClickListener {


    private List<Integer> itemSelecteds;
    private RVAdapterMisSectores myadapter;
    MyActionModeCallback actionModeCallBack = new MyActionModeCallback();

    ActionMode myActionMode;

    private RecyclerView rv_servicios;


    public MisSectoresFragment() {

    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, final ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_sectores, container, false);


        rv_servicios = (RecyclerView) view.findViewById(R.id.sectores_recycler_view);
        myadapter = new RVAdapterMisSectores(new ArrayList<Sector>(), this);
        rv_servicios.setAdapter(myadapter);
        rv_servicios.setItemAnimator(new DefaultItemAnimator());
        rv_servicios.setLayoutManager(new LinearLayoutManager(getActivity()));


        return view;
    }

    @Override
    public void onStart(){
        super.onStart();
    }

    @Override
    public void onResume(){
        super.onResume();
     ///   obtenerMisServicios();

        cargarSectores();
    }

    private void cargarSectores() {
        ArrayList<Sector> nuevosSectores = new ArrayList<Sector>();
        Sector sectorN;
        for (int i=0; i<6; i++){
            sectorN = new Sector();
            sectorN.setIdSector(i);
            sectorN.setNombreSector("Sector " + i);
            int a = (int) (Math.random()*(1-4)+4);
            String [] sectores = getResources().getStringArray(R.array.array_tipo_cultivo);
        //    sectorN.setTipoAgua(sectores[a]);
            sectorN.setTipoSector(sectores[a]);
            nuevosSectores.add(sectorN);
        }

        myadapter.addAll(nuevosSectores);
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            default:
                break;
        }

        return false;
    }

/*    *//**
     * Carga el adaptador con las metas obtenidas
     * en la respuesta
     *//*
    public void obtenerMisServicios() {

        String url = Constantes.GET_SERVICIOS_USUARIO + "?idUsuario=" + CargarUsuario().getIdUsuario();

        JsonObjectRequest jsonObjReq = new JsonObjectRequest(Request.Method.GET,
                url , null, new Response.Listener<JSONObject>() {

            @Override
            public void onResponse(JSONObject response) {
                procesarRespuesta(response);
            }
        }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d(getTag(), "Volley Error: " + error.getMessage());
            }
        }) {

            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                HashMap<String, String> headers = new HashMap<String, String>();
                headers.put("Content-Type", "application/json; charset=utf-8");
                return headers;
            }

        };
        VolleySingleton.getInstance(getActivity()).addToRequestQueue(jsonObjReq);
    }

    *//**
     * Interpreta los resultados de la respuesta y así
     * realizar las operaciones correspondientes
     *
     *//*
    private void procesarRespuesta(JSONObject response) {
        try {
            // Obtener atributo "estado"
            String estado = response.getString("clave");

            switch (estado) {
                case "All Services by user OK": // EXITO
                        if ( response.getJSONArray("servicios") != null) {
                            JSONArray respuestaServicios_JSONarray = response.getJSONArray("servicios");
                            ArrayList<Servicio> servicios = new ArrayList<Servicio>();
                            for (int i = 0; i < respuestaServicios_JSONarray.length(); i++) {
                                servicios.add(new Servicio(respuestaServicios_JSONarray.getJSONObject(i)));
                            }
                            if (servicios.size() < 1){
                                infoMisServicios.setText(getString(R.string.info_sin_servicios));
                            } else {
                                try {
                                    infoMisServicios.setText(getString(R.string.info_borrar_mis_servicios));
                                } catch (Exception e){

                                }

                            }
                            myadapter.addAll(servicios);
                        }
                    break;
                case "Servicios Eliminados OK": // EXITO
                    Snackbar.make(rv_servicios, getString(R.string.info_servicios_eliminados_ok), Snackbar.LENGTH_LONG).show();
                    myadapter.removeItems(itemSelecteds);
                    myActionMode.finish();
                    break;
                case "All Services by user KO": // FALLIDO
                    infoMisServicios.setText(getString(R.string.info_sin_servicios));
                //    Snackbar.make(rv_servicios, "No tienes servicios, puedes añadir servicios.", Snackbar.LENGTH_LONG).show();
                    break;
            }


        } catch (JSONException e) {
            e.printStackTrace();
        }

    }


    private Usuario CargarUsuario(){
        if(getActivity().getSharedPreferences("USUARIO_LOGIN", Context.MODE_PRIVATE) != null) {
            SharedPreferences preferencias = getActivity().getSharedPreferences("USUARIO_LOGIN", Context.MODE_PRIVATE);
            Gson gson = new Gson();
            // Leemos desde SP nuestro objeto
            String json = preferencias.getString("USUARIO", "");
            // Des-serializamos en base a nuestro objeto

            return gson.fromJson(json, Usuario.class);
        }

        return null;
    }*/
    // OTRA PRUEBA
    @Override
    public void onItemClicked(int position) {
        if (myActionMode != null) {
            toggleSelection(position);
        } else {
        /*    Intent in = new Intent(getActivity(), VerServicioActivity.class);
            in.putExtra("servicio", myadapter.getServicio(position));
            in.putExtra("modificar", true);
            getActivity().startActivity(in);*/
        }

    }

    @Override
    public boolean onItemLongClicked(int position) {
        if (myActionMode == null) {
            myActionMode = ((MainActivity) getActivity()). startSupportActionMode(actionModeCallBack);
        }

        toggleSelection(position);

        return true;

    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        getActivity().getMenuInflater().inflate(R.menu.menu_main, menu);
    }

    private void toggleSelection(int position) {
        myadapter.toggleSelection(position);
        int count = myadapter.getSelectedItemCount();

        if (count == 0) {
            myActionMode.finish();
        } else {
            myActionMode.setTitle(String.valueOf(count) + getString(R.string.info_servicios_seleccionados));
            myActionMode.invalidate();
        }
    }

    private class MyActionModeCallback implements ActionMode.Callback {
        @SuppressWarnings("unused")
        private final String TAG = MyActionModeCallback.class.getSimpleName();

        @Override
        public boolean onCreateActionMode(ActionMode mode, Menu menu) {
            mode.getMenuInflater().inflate(R.menu.menu_eliminar, menu);
            return true;
        }

        @Override
        public boolean onPrepareActionMode(ActionMode mode, Menu menu) {
            return false;
        }

        @Override
        public boolean onActionItemClicked(ActionMode mode, MenuItem item) {
            switch (item.getItemId()) {
                case R.id.menu_item_delete_servicio:
                    JSONArray idServiciosEliminarJSON = new JSONArray();
                    ArrayList<Integer> servEliminar =  myadapter.getIdItems();
                    for (int id : servEliminar){
                        idServiciosEliminarJSON.put(id);
                    }
                    itemSelecteds = myadapter.getSelectedItems();
                    //    eliminarServiciosBD(new JSONObject().put("idServicios", idServiciosEliminarJSON));
                    return true;

                default:
                    return false;
            }
        }

        @Override
        public void onDestroyActionMode(ActionMode mode) {
            myadapter.clearSelection();
            myActionMode = null;
        }
    }

/*
    public void eliminarServiciosBD(JSONObject serviciosEliminar){
        final JsonObjectRequest jsonObjReq = new JsonObjectRequest(Request.Method.POST,
                Constantes.POST_ELIMINAR_SERVICIOS , serviciosEliminar, new Response.Listener<JSONObject>() {

            @Override
            public void onResponse(JSONObject response) {
                procesarRespuesta(response);
            }
        }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d(BuscadorActivity.class.getSimpleName().toString(), "Volley Error: " + error.getMessage());
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
*/


}
