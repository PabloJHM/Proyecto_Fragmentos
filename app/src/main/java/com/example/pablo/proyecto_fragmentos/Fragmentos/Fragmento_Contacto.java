package com.example.pablo.proyecto_fragmentos.Fragmentos;

import android.app.Activity;
import android.app.Fragment;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.pablo.proyecto_fragmentos.Util.Contacto;
import com.example.pablo.proyecto_fragmentos.R;

import java.io.File;

public class Fragmento_Contacto extends Fragment{
    Contacto aux;
    View viewFragment;
    TextView tv1,tv2;
    private ImageView iv;

    public Fragmento_Contacto(){}

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        viewFragment = inflater.inflate(R.layout.layout_fragmento_contacto, container, false);
        tv1=(TextView)viewFragment.findViewById(R.id.tvNombre);
        tv2=(TextView)viewFragment.findViewById(R.id.tvNum);
        iv=(ImageView)viewFragment.findViewById(R.id.ivFoto);
        //Al pulsar en la foto nos abrirá un intent para seleccionar una imagen de la galeria
        iv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent photoPickerIntent = new Intent(Intent.ACTION_PICK);
                photoPickerIntent.setType("image/*");
                startActivityForResult(photoPickerIntent, 1);
            }
        });
        return viewFragment;
    }

    //Mostrar los datos
    public void setDato(Contacto aux){
        this.aux=aux;
        tv1.setText(this.aux.getNombre());
        tv2.setText(this.aux.getTlf());
        //Comprobamos si tiene una imagen el contacto. Si es así la mostraremos, de lo contrario
        //mostraremos una imagen predeterminada
        if(!this.aux.getFoto().isEmpty()) {
            //Creamos un archivo temporal para la foto
            File imgf = new File(this.aux.getFoto());
            if (imgf.exists()) {
                Bitmap myBitmap = BitmapFactory.decodeFile(imgf.getAbsolutePath());
                iv.setImageBitmap(myBitmap);
                imgf.delete();
            }
        } else {
            Bitmap myBitmap = BitmapFactory.decodeResource(getResources(),R.drawable.personajillo);
            iv.setImageBitmap(myBitmap);
        }

    }

    //Resultado de seleccionar la imagen
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            case 1:
                if (resultCode == Activity.RESULT_OK) {
                    String filePath = getPathFromCameraData(data, this.getActivity());
                    File imgFile = new File(filePath);
                    if (imgFile.exists()) {
                        //Mostramos la imagen y guardamos la ruta en el contacto
                        iv = (ImageView) viewFragment.findViewById(R.id.ivFoto);
                        Bitmap myBitmap = BitmapFactory.decodeFile(imgFile.getAbsolutePath());
                        iv.setImageBitmap(myBitmap);
                        aux.setFoto(filePath);
                    }

                }
        }
    }
    public static String getPathFromCameraData(Intent data, Context context) {
        Uri selectedImage = data.getData();
        String[] filePathColumn = { MediaStore.Images.Media.DATA };
        Cursor cursor = context.getContentResolver().query(selectedImage,
                filePathColumn, null, null, null);
        cursor.moveToFirst();
        int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
        String picturePath = cursor.getString(columnIndex);
        cursor.close();
        return picturePath;
    }
}
