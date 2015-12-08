package com.example.pablo.proyecto_fragmentos.Fragmentos;

import android.app.Activity;
import android.app.Fragment;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import com.example.pablo.proyecto_fragmentos.Actividades.Activity_Secundaria;
import com.example.pablo.proyecto_fragmentos.Util.Adaptador;
import com.example.pablo.proyecto_fragmentos.Util.Agenda;
import com.example.pablo.proyecto_fragmentos.Util.Contacto;
import com.example.pablo.proyecto_fragmentos.R;
import com.example.pablo.proyecto_fragmentos.Util.onFragmentoInteraccionListener;

import java.util.ArrayList;
import java.util.List;

public class Fragmento_Lista extends Fragment {
    private View viewFragment;
    private List<Contacto> agenda;
    private Agenda a;
    private ListView lv;
    private Adaptador ad;
    private onFragmentoInteraccionListener listener;
    private static onFragmentoInteraccionListener listenerest;
    private static Context context;


    public Fragmento_Lista(){
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        context=getActivity();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        viewFragment= inflater.inflate(R.layout.layout_fragmento_lista, container, false);
        init();
        return viewFragment;
    }

    //Por alguna razón, solo entra en el onAttach deprecated. No entra en onAttach(Context c), aun
    //si no pongo este onAttach, de modo que el listener se quedaba vacio
    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        a =new Agenda(activity.getApplicationContext());
        if(activity instanceof onFragmentoInteraccionListener){
            listener= (onFragmentoInteraccionListener) activity;
            //Guardo el listener en un static para evitar que se pierda.
            listenerest=listener;
        }else{
            throw new ClassCastException(getActivity().getString(R.string.Mensaje));
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        listener = null;
    }

    @Override
    public void onStart() {
        super.onStart();
        generaAdaptador();
    }

    public void init(){
        agenda = a.getListaCont();
        generaAdaptador();
        //Al igual que con el listener, guardo el contexto en un static para evitar que se pierda
        context = this.getActivity();
    }

    public void generaAdaptador(){
        lv=(ListView)viewFragment.findViewById(R.id.lista);
        ad = new Adaptador(this.getActivity(),agenda);
        lv.setAdapter(ad);
        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                listener.onInteraccion(agenda.get(position));
            }
        });
        a.ordenar();
    }

    //Abre la actividad del contacto
    public void lanzaIntent(Contacto aux){
        Intent i = new Intent(context,Activity_Secundaria.class);
        Bundle b=new Bundle();
        b.putSerializable("envia", aux);
        i.putExtras(b);
        startActivityForResult(i, 1);
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        Contacto aux=data.getParcelableExtra("vuelta");
        //Dependiendo de como salgamos de la actividad_secundaria. Si salimos por girar el movil
        // simplemente mostraremos los datos del contacto que estabamos mirando en la derecha.
        if(resultCode== Activity.RESULT_OK&&requestCode==1){
            listenerest.onInteraccion(aux);
        } else { //Si salimos pulsando atras en el movil, recogemos el contacto para cambiar la imagen y mostrala en el listview
            System.out.println(aux.toString());
            a.guardarEditar(aux);
            generaAdaptador();
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        generaAdaptador();
    }

    //Metodos para salvar la lista en caso de girar el movil
    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        if (savedInstanceState != null) {
            agenda=(List)savedInstanceState.getParcelableArrayList("lista");
        }
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        ArrayList<Contacto> guardaAgenda=(ArrayList<Contacto>) agenda;
        outState.putParcelableArrayList("lista",guardaAgenda);
    }
}
