package com.example.pablo.proyecto_fragmentos.Util;


import android.os.Parcel;
import android.os.Parcelable;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;


public class Contacto implements Serializable,Comparable<Contacto>,Parcelable {

    private long id;
    private String nombre,foto;
    private List<String> tlf;

    /****************************************Constructores***************************************/
    public Contacto(String nombre,String foto, long id, List<String> tlf) {
        this.nombre = nombre;
        this.foto=foto;
        this.id = id;
        this.tlf = tlf;
    }

    public Contacto(){
        this("","",0,new ArrayList<String>());
    }

    /************************************Getters and Setters**************************************/
    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getFirstTlf() {
        return tlf.get(0);
    }

    public String getSelectedTlf(int pos){return tlf.get(pos);}

    public String getTlf() {
        String s="";
        for(String a:tlf) {
            s += a + "\n";
        }
        return s;
    }

    public int getSize() {
        return tlf.size();
    }

    public void setTlf(List<String> tlf) {
        this.tlf=tlf;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getNombre() {
        return nombre;
    }

    public String getFoto() {
        if(foto!=null)
            return foto;
        else
            return "";
    }

    public void setFoto(String foto) {
        this.foto = foto;
    }

    public List<String> getArrayTlf(){
        return tlf;
    }
    @Override
    public String toString() {
        return "Contacto{" +
                "nombre='" + nombre + '\'' +
                ", id=" + id +
                ", tlf=" + tlf +
                ", foto="+ foto+
                '}';
    }

    /****************************************Comparadores*****************************************/
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Contacto contacto = (Contacto) o;

        return id == contacto.id;

    }

    @Override
    public int hashCode() {
        return (int) (id ^ (id >>> 32));
    }

    @Override
    public int compareTo(Contacto contacto) {
        int r=this.nombre.compareToIgnoreCase(contacto.nombre);
        if(r==0){
            r= (int) (this.id - contacto.id);
        }
        return r;
    }

    /*******************************************Parcelable*****************************************/

    // Metodos necesarios para definir Parcelables que he necesitado a la hora de pasar un Contacto
    // entre actividades

    public static final Creator<Contacto> CREATOR = new Creator<Contacto>() {
        @Override
        public Contacto createFromParcel(Parcel in) {
            return new Contacto(in);
        }

        @Override
        public Contacto[] newArray(int size) {
            return new Contacto[size];
        }
    };

    //Constuctor del Parcelable
    public Contacto(Parcel parcel){
        nombre= new String();
        tlf=new ArrayList<>();
        foto=new String();
        readToParcel(parcel);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    //Insertar y leer del parcelable
    public void writeToParcel(Parcel parcel,int pos){
        parcel.writeString(nombre);
        parcel.writeLong(id);
        parcel.writeStringList(tlf);
        parcel.writeString(foto);
    }

    public void readToParcel(Parcel parcel){
        nombre=parcel.readString();
        id= parcel.readLong();
        parcel.readStringList(tlf);
        foto=parcel.readString();
    }
}
