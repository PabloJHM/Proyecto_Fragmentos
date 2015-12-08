package com.example.pablo.proyecto_fragmentos.Actividades;

import android.app.Activity;
import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;

import com.example.pablo.proyecto_fragmentos.Util.Contacto;
import com.example.pablo.proyecto_fragmentos.Fragmentos.Fragmento_Contacto;
import com.example.pablo.proyecto_fragmentos.R;

public class Activity_Secundaria extends Activity{
    private Contacto aux;
    private Intent i;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        i=this.getIntent();

        //Comprobamos si hay algun contacto guardado
        if(savedInstanceState!=null) {
            aux = savedInstanceState.getParcelable("valor");
        }

        //Comprobamos la orientacion de la actividad. Si esta de pie recogemos los datos del contacto
        //y lo mostramos
        if(getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT){
            setContentView(R.layout.activity_dos);
            Bundle b = getIntent().getExtras();
            aux=b.getParcelable("envia");
            Fragmento_Contacto fragmentoC = (Fragmento_Contacto) getFragmentManager().findFragmentById(R.id.fragment);
            fragmentoC.setDato(aux);
        //Si se gira guardamos los datos del contacto para mostrarlos cuando esté la pantalla tumbada
        } else {
            Bundle b=new Bundle();
            b.putParcelable("vuelta", aux);
            i.putExtras(b);
            setResult(Activity.RESULT_OK,i);
            finish();
        }
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putParcelable("valor", aux);
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        aux=savedInstanceState.getParcelable("valor");
    }

    @Override
    protected void onResume() {
        super.onResume();
        //Aquí recojemos los datos para que cuando salgamos de ver el contacto se guarde la imagen
        //que le hayamos puesto
        if(aux.getFoto()!=null) {
            Bundle b = new Bundle();
            b.putParcelable("vuelta", aux);
            i.putExtras(b);
            setResult(Activity.RESULT_CANCELED, i);
        }
    }
}
