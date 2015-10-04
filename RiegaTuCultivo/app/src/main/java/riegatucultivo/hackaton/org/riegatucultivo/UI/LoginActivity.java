package riegatucultivo.hackaton.org.riegatucultivo.UI;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ScrollView;
import android.widget.TextView;

import riegatucultivo.hackaton.org.riegatucultivo.R;


public class LoginActivity extends AppCompatActivity implements View.OnClickListener {

    private TextView registrar, probar, recuperarPass, login;
    private Button login_btn ;
    private EditText usuario, clave;
    private ProgressDialog pDialog;
    private ScrollView scroll;
    public Boolean RESULTADO = false;
    private CheckBox guardarUserPass;
    Toolbar barra;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

/*        barra = (Toolbar) findViewById(R.id.toolbar);
        barra.setTitle("PeopleServices");
        setSupportActionBar(barra);*/

        login_btn = (Button) findViewById(R.id.login_btn);
        login_btn.setOnClickListener(this);

        recuperarPass = (TextView) findViewById(R.id.login_recuperar_clave);
        recuperarPass.setOnClickListener(this);

 /*       Typeface tp = Typeface.createFromAsset(getAssets(), "fonts/Roboto-Regular.ttf");
        login_btn.setTypeface(tp);
        login_btn.setTextSize(TypedValue.COMPLEX_UNIT_SP, 22);*/

        registrar = (TextView) findViewById(R.id.registrar_log_tv);
        registrar.setOnClickListener(this);


        usuario = (EditText) findViewById(R.id.usuario_login_text);
        clave = (EditText) findViewById(R.id.pass_login_text);
        guardarUserPass = (CheckBox) findViewById(R.id.guardarUsuarioPass_login);

        if (getIntent() != null) {
            if ( getIntent().getBooleanExtra("LOG_OUT", false)){
                CargarPreferencias(false);
            } else {
                CargarPreferencias(true);
            }
        } else {
            CargarPreferencias(true);
        }


    }



    @Override
    protected void onResume() {
        super.onResume();
    }

    private boolean comprobarLogin(){
        boolean comprobacion=true;
       if (usuario.getText().length() < 5){
           usuario.setError(getString(R.string.error_nombre_usuario));
           comprobacion = false;
       }
       if (clave.getText().length() < 6){
           clave.setError(getString(R.string.error_pass_long));
           comprobacion = false;
           return comprobacion;
       }

        return usuario.getText().toString().equals("prueba") && clave.getText().toString().equals("prueba1");
    }



    @Override
    public void onClick(View v) {
        Intent in;
        switch (v.getId()) {
            /*case R.id.registrar_log_tv:
                in = new Intent(this, RegistroParte1Activity.class);
                startActivity(in);
                break;*/
            case  R.id.login_btn:
                if (comprobarLogin()){
                    Intent intent = new Intent(this, MainActivity.class);
                    startActivity(intent);;
                }
                break;
            /*case R.id.login_recuperar_clave:
                in = new Intent(this, RecuperarPassActivity.class);
                startActivity(in);
                break;*/
        }

    }

    public void GuardarPreferencias(){
        SharedPreferences preferenciaUserPass = getSharedPreferences("Login", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = preferenciaUserPass.edit();
        editor.putBoolean("Guardar", guardarUserPass.isChecked());
        if (guardarUserPass.isChecked()){
            editor.putString("Username", usuario.getText().toString());
            editor.putString("Pass", clave.getText().toString());
        } else {
            editor.putString("Username", "");
            editor.putString("Pass", "");
        }

        editor.commit();
    }

    private void CargarPreferencias(boolean entrarAuto){
        SharedPreferences preferencias = getSharedPreferences("Login", Context.MODE_PRIVATE);

        if (preferencias.getBoolean("Guardar", false)) {

            guardarUserPass.setChecked(true);
            usuario.setText(preferencias.getString("Username", ""));
            clave.setText(preferencias.getString("Pass", ""));
            if (entrarAuto)
                comprobarLogin();
        }



    }


    public void MostrarSnackBar(int idString){

        Snackbar snackbar = Snackbar.make(this.findViewById(android.R.id.content), getString(idString), Snackbar.LENGTH_LONG);
        View snackBarView = snackbar.getView();
        snackBarView.setBackgroundColor( getResources().getColor( R.color.rojo ));
        snackbar.show();


    }


    // OCULTAR TECLADO

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


