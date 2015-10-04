package riegatucultivo.hackaton.org.riegatucultivo.UI;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;

import riegatucultivo.hackaton.org.riegatucultivo.Modelo.Sector;
import riegatucultivo.hackaton.org.riegatucultivo.R;

/**
 * Activity para añadir o modificar un servicio, para evitar usar 2 iguales, se ha decido que se pueda modificar o añadir un servicio. Tan solo especificando la acción al iniciar la activity.
 * Created by Sebas on 03/08/2015.
 */
public class FormularioSectorActivity extends AppCompatActivity /*implements View.OnClickListener*/ {

    private Toolbar barra;
    private EditText nombreSector;
    private ImageView abrir_localizacion;
    private final int REQUEST_CODE_LOCALIZACION = 6;
    private final int RESULT_OK = 10;
//    private DireccionLocalizacion direccion;
    private Spinner spinnerCTipoCultivos;
    private ProgressDialog pDialog;
    private Sector sector;
    private boolean modificarView = false;



    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.formulario_sector);

        barra = (Toolbar) findViewById(R.id.toolbarFormServicio);
        setSupportActionBar(barra);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.abc_ic_clear_mtrl_alpha);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        nombreSector = (EditText) findViewById(R.id.nombre_sector_form);
/*
        abrir_localizacion =(ImageView) findViewById(R.id.icono_localizacion_servicio_form);
        abrir_localizacion.setOnClickListener(this);
*/
        spinnerCTipoCultivos = (Spinner) findViewById(R.id.sector_form_tipo_cultivo_sp);
        loadSpinnerTiposCultivo();
    //    loadSpinnerTiposAgua();

    //    setServicio();
    }

 /*   private void setSector() {
        if (getIntent() != null && getIntent().getExtras() != null){
            Bundle b= getIntent().getExtras();
            servicio = (Servicio) b.getSerializable("servicio");
            direccion = servicio.getDireccion();
            if (b.getBoolean("modificar", false) ){ // ocultamos el botón de contratar
                modificarView = true;
                getSupportActionBar().setTitle("Modificar " + servicio.getNombreServicio());
                cargarServicio();
            }
        }
        else
        getSupportActionBar().setTitle(getString(R.string.title_activity_add_servicio));
    }
*/
/*    private void cargarServicio() {
        if (servicio != null){
            nombreServicio.setText(servicio.getNombreServicio());
            despcripcion.setText(servicio.getDescripcionServicio());
            precioPicker.setValue((int) servicio.getPrecioServicio());
            spinnerCategorias.setSelection(servicio.getIdCategoria());
            localizacion.setText(servicio.getDireccion().getLocalidad() + " (" + servicio.getDireccion().getProvincia() + ")");
            cp.setText(String.format("%05d", servicio.getDireccion().getCp()));
            if (servicio.getTipoTarificacion().equals("servicio")){
                porServicioRB.setChecked(true);
            }

        }

    }*/

    private void loadSpinnerTiposCultivo(){
        ArrayAdapter adapter = ArrayAdapter.createFromResource(this, R.array.array_tipo_cultivo, android.R.layout.simple_spinner_dropdown_item);
        spinnerCTipoCultivos.setAdapter(adapter);

    }


/*
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_localizacion, menu);
        return true;
    }
*/

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.

       switch (item.getItemId()){
  /*           case R.id.action_save:// Guardar o Modificar
                if (modificarView){
                    modificarServicio();
                } else guardarServicio();

                break;
*/
            case android.R.id.home: // Cancelar
            {
               /* if (  comprobarCambios()){
                    if (modificarView){
                        mostrarDialogo(R.string.dialog_discard_msg);
                        // mostramos un alert para advertirle que se perderán los cambios
                    }else {
                        // mostramos un alert de que no se creará el servicio
                        mostrarDialogo(R.string.dialog_discard_service);
                    }
                 return true;
                }*/
                finish();
                return true;
            }
        }


        return super.onOptionsItemSelected(item);
    }




/*    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if ((requestCode == REQUEST_CODE_LOCALIZACION) && (resultCode == RESULT_OK)){
            direccion = (DireccionLocalizacion)  data.getExtras().getSerializable("localizacion");
            localizacion.setText(direccion.getLocalidad() + " ("+ direccion.getProvincia() + ")");
            localizacion.setError(null);
            cp.setText(String.format("%05d", direccion.getCp()));
            cp.setError(null);
        }

    }*/

/*

    @Override
    public void onClick(View v) {
        Intent in;
        switch (v.getId()){
            case R.id.icono_localizacion_servicio_form:
                in = new Intent(this, LocalizacionActivity.class);
                startActivityForResult(in, REQUEST_CODE_LOCALIZACION);
                break;
        }
    }

    private void guardarServicio(){
        if (comprobarFormulario()){
            pDialog = new ProgressDialog(this);
            pDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
            pDialog.setMessage(getString(R.string.dialog_info_add_service));
            pDialog.setCancelable(true);
            pDialog.setMax(100);
            new BD_SERVICIO(1,this, pDialog, obtenerServicioForm()).execute();
        }

    }

    private void modificarServicio() {
        if (comprobarFormulario()){
            pDialog = new ProgressDialog(this);
            pDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
            pDialog.setMessage(getString(R.string.dialog_info_saving_service));
            pDialog.setCancelable(true);
            pDialog.setMax(100);
            new BD_SERVICIO(2,this, pDialog, obtenerServicioForm()).execute();
        }
    }

    public void mostrarDialogoModificado(){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage(getString(R.string.dialog_update_service_msg_ok))
                .setTitle(getString(R.string.title_info))
                .setIcon(R.drawable.ic_info_black_24dp)
                .setCancelable(false)
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        Intent data = new Intent();
                        Bundle bundle = new Bundle();
                        bundle.putSerializable("servicioActualizado", obtenerServicioForm());
                        data.putExtras(bundle);
                        setResult(RESULT_OK, data);
                        finish();
                    }
                });
        AlertDialog alert = builder.create();
        alert.show();
    }


    protected boolean comprobarFormulario(){
        boolean comprobacion = true;
        if (nombreServicio.getText().toString().isEmpty()) {
            comprobacion = false;
            nombreServicio.setError(getString(R.string.error_vacio));
        }if (despcripcion.getText().toString().isEmpty()){
            comprobacion = false;
            despcripcion.setError(getString(R.string.error_vacio));
        }
        if (rGroupTarficacion.getCheckedRadioButtonId()<0 || spinnerCategorias.getSelectedItemPosition()<1 || localizacion.getText().toString().isEmpty()){
            comprobacion = false;
            mostrarSnackBar(getString(R.string.error_todos_vacios));
        }

        return comprobacion;
    }

    private boolean comprobarCambios() {
        if (modificarView){ // comprobamos si hay cambios entre servicio actual con el guardado en la bd, esta comprabacion se realiza cuando van a modificar un servicio
            if (servicio != null){
                return ! this.servicio.equals(obtenerServicioForm()); // Devuelve false si no hay cambios, true en el caso de haber cambios
            }
        }
        // comprueba si se ha escrito en algún campo del formulario en el caso de abrir la venta para insertar un nuevo servicio
        return ! nombreServicio.getText().toString().isEmpty() || ! localizacion.getText().toString().isEmpty() || ! despcripcion.getText().toString().isEmpty();
    }

    public void mostrarSnackBar(String mensaje){
        Snackbar snackbar = Snackbar.make(this.findViewById(android.R.id.content), mensaje, Snackbar.LENGTH_LONG);
        View snackBarView = snackbar.getView();
        snackBarView.setBackgroundColor( getResources().getColor( R.color.rojo ));
        snackbar.show();


    }

    public Servicio obtenerServicioForm(){
        Servicio servicioActual = new Servicio();
        if (modificarView && servicio != null){
            servicioActual.setIdServicio(servicio.getIdServicio());
        }
        servicioActual.setNombreServicio(nombreServicio.getText().toString());
        servicioActual.setDescripcionServicio(despcripcion.getText().toString());
        servicioActual.setPrecioServicio(precioPicker.getValue());
        servicioActual.setIdCategoria(spinnerCategorias.getSelectedItemPosition());
        servicioActual.setDireccion(direccion);
        servicioActual.setPropietario(CargarUsuario());
        if (rGroupTarficacion.getCheckedRadioButtonId() == R.id.rb_porServicio_form){
            servicioActual.setTipoTarificacion("servicio");
        } else {
            servicioActual.setTipoTarificacion("hora");

        }

        return servicioActual;
    }
*/


/*    private Usuario CargarUsuario(){
        if(getSharedPreferences("USUARIO_LOGIN", Context.MODE_PRIVATE) != null) {
            SharedPreferences preferencias = getSharedPreferences("USUARIO_LOGIN", Context.MODE_PRIVATE);
            Gson gson = new Gson();
            // Leemos desde SP nuestro objeto
            String json = preferencias.getString("USUARIO", "");
            // Des-serializamos en base a nuestro objeto

            return gson.fromJson(json, Usuario.class);
        }

        return null;
    }


    private void mostrarDialogo(int id) {

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setIcon(R.drawable.ic_report_problem_black_24dp);
        builder.setCancelable(false);
        builder.setTitle(getString(R.string.dialog_title_alert));
        builder.setMessage(getString(id));
        builder.setPositiveButton(getString(R.string.dialog_ok), new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                // User clicked OK button
                finish();
            }
        });
        builder.setNegativeButton(getString(R.string.dialog_cancel), new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                // User cancelled the dialog
            }
        });



        builder.show();
    }*/

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
