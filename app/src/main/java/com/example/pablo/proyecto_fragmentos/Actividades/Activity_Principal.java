package com.example.pablo.proyecto_fragmentos.Actividades;

import android.app.Activity;
import android.os.Bundle;

import com.example.pablo.proyecto_fragmentos.Util.Contacto;
import com.example.pablo.proyecto_fragmentos.Fragmentos.Fragmento_Contacto;
import com.example.pablo.proyecto_fragmentos.Fragmentos.Fragmento_Lista;
import com.example.pablo.proyecto_fragmentos.R;
import com.example.pablo.proyecto_fragmentos.Util.onFragmentoInteraccionListener;


public class Activity_Principal extends Activity implements onFragmentoInteraccionListener {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_uno);
    }

    //Accion al seleccionar un elemento de la lista. Depende de si esta el movil vertical o horizontal
    @Override
    public void onInteraccion(Contacto aux) {
        Fragmento_Contacto fragmentoC = (Fragmento_Contacto) getFragmentManager().findFragmentById(R.id.fragmento_contacto);
        Fragmento_Lista fragmentoL = (Fragmento_Lista) getFragmentManager().findFragmentById(R.id.fragment2);
        if (fragmentoC == null || !fragmentoC.isInLayout()) {
            //Vertical
            fragmentoL.lanzaIntent(aux);
        } else {
            //Horizontal
            fragmentoC.setDato(aux);
        }
    }

}
