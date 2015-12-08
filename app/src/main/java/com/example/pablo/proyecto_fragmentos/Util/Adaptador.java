package com.example.pablo.proyecto_fragmentos.Util;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.pablo.proyecto_fragmentos.R;

import java.io.File;
import java.util.List;

public class Adaptador extends ArrayAdapter<Contacto> {

    private int res;
    private LayoutInflater lInflator;
    private List<Contacto> agenda;

    static class ViewHolder {
        public TextView tv1, tv2;
        ImageView iv;
    }

    public Adaptador(Context context, List<Contacto> agenda) {
        super(context, R.layout.item_list,agenda);
        this.agenda = agenda;
        this.res=R.layout.item_list;
        lInflator = (LayoutInflater) context.getSystemService
                (Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder vh = new ViewHolder();
        if(convertView==null){
            convertView = lInflator.inflate(res, null);
            TextView tv = (TextView) convertView.findViewById(R.id.tvNombre);
            vh.tv1 = tv;
            tv= (TextView) convertView.findViewById(R.id.tvTlf);
            vh.tv2 = tv;
            ImageView iv = (ImageView)convertView.findViewById(R.id.ivPerso);
            vh.iv=iv;
            convertView.setTag(vh);
        } else {
            vh = (ViewHolder) convertView.getTag();
        }
        vh.tv1.setText(agenda.get(position).getNombre());
        vh.tv2.setText(agenda.get(position).getFirstTlf());
        //Si tiene una imagen, la mostraremos en miniatura
        if(!this.agenda.get(position).getFoto().isEmpty()) {
            File imgf= new File(agenda.get(position).getFoto());
            if(imgf.exists()) {
                Bitmap myBitmap = BitmapFactory.decodeFile(imgf.getAbsolutePath());
                vh.iv.setImageBitmap(myBitmap);
            }
        }
        return convertView;
    }

}