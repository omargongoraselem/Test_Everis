package com.example.demoocr.imgocr;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.util.SparseArray;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.demoocr.R;
import com.google.android.gms.vision.Frame;
import com.google.android.gms.vision.text.TextBlock;
import com.google.android.gms.vision.text.TextRecognizer;
import com.theartofdev.edmodo.cropper.CropImage;
import com.theartofdev.edmodo.cropper.CropImageView;

import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

public class ImageDetectedOcr extends AppCompatActivity implements View.OnClickListener {
    private static final int MY_PERMISSION_STORAGE = 1;
    Button captureBtm;// ocrBtn;
    ImageView cameraImg;
    static final int REQUEST_IMAGE_CAPTURE = 1;
    String TAG="ImageOCR";
    TextView txtresult;
///ALgoritmo
    String textValorS="";
    //reconocimiento de datos TAG existentes
    String[] identificador={"LOCALIDAD","DOMICILIO","CURP","CLAVE DE ELECTOR","ESTADO","MUNICIPIO","NOMBRE","SECCION","EMISION","VIGENCIA",
            "FECHA DE NACIMIENTO"};
    //Listapara almacenar TAGs encontrados en el documento
    String[] idLargos={"CLAVE","AÑO","FECHA"};

    List<String> list = new ArrayList<>();



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_image_detected_ocr);

        captureBtm = findViewById(R.id.btnCamara);
      //  ocrBtn = findViewById(R.id.btnOcr);
        cameraImg = findViewById(R.id.img);
        txtresult = findViewById(R.id.txtResult);

        //ocrBtn.setOnClickListener(this);
        captureBtm.setOnClickListener(this);



    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case  R.id.btnCamara:
  //              dispatchTakePictureIntent();
                m();
                break;
            /*case R.id.btnOcr:
    //            getTextFromImage();
                break;*/
        }
    }


    public void m(){
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED &&
                ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, MY_PERMISSION_STORAGE);
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, MY_PERMISSION_STORAGE);
        } else {
            CropImage.activity()
                    .setGuidelines(CropImageView.Guidelines.ON)
                    .setAspectRatio(1, 1) //You can skip this for free form aspect ratio)
                    .start(this);
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE) {
            CropImage.ActivityResult result = CropImage.getActivityResult(data);
            if (resultCode == RESULT_OK) {
                Uri resultUri = result.getUri();
                // Set uri as Image in the ImageView:
               cameraImg.setImageURI(resultUri);
                try {
                    Bitmap bitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver() , resultUri);
                    getTextFromImage(bitmap);
                } catch (IOException e) {
                    e.printStackTrace();
                }

                // Bitmap bitmap= result.getBitmap();
                //cameraImg.setImageBitmap(bitmap);
            } else if (resultCode == CropImage.CROP_IMAGE_ACTIVITY_RESULT_ERROR_CODE) {
                Exception error = result.getError();
            }
        }
    }


    private void getTextFromImage(Bitmap image){
        //pasamos la imagen como bitmap
        TextRecognizer textRecognizer = new TextRecognizer.Builder(getApplicationContext()).build();
        if (!textRecognizer.isOperational()){
            Toast.makeText(this,"No se encontro texto",Toast.LENGTH_LONG).show();
        }else {
            Frame frame= new Frame.Builder().setBitmap(image).build();
            SparseArray<TextBlock> items = textRecognizer.detect(frame);
            StringBuilder sB = new StringBuilder();
            for (int i = 0; i < items.size() ; i++){
                TextBlock myItem = items.valueAt(i);
                sB.append(myItem.getValue());
                sB.append("\n");
            }
            //txtresult.setText(sB.toString());
            tags(sB.toString());
            textValorS ="";
        }

    }




    //primer paso pasar cadena a mayuscula
    //segundo quitar saltos de linea
    //tercero crear array apartir de los espacios
    //cuarto hacer recorrido y validar los tag existentes
    public void tags(String texto){
        String mayusculas = texto.toUpperCase();
        String replace = mayusculas.replaceAll("\n"," ");
        String[] arrayTexto= replace.split(" ");
        Log.e("Tamaño",""+arrayTexto.length);
        for (int x=0;x<arrayTexto.length;x++){
            for (int i = 0 ; i < identificador.length ; i++){
                if(arrayTexto[x].equals(identificador[i])){
                    // Log.e("Palabra encontrada","+++++++++++++++++++++++ "+identificador[i]);
                    list.add(identificador[i]);
                }else{
                    for (int n = 0; n < idLargos.length ; n++ ){
                        if (arrayTexto[x].equals(idLargos[n])){
                            casosIdLargo(arrayTexto[x],arrayTexto[x+1],arrayTexto[x+2]);
                        }
                    }
                }
            }
        }
        Set<String> hashSet = new LinkedHashSet<>(list);
        list.clear();
        list.addAll(hashSet);



        for (int y = 0 ; y < list.size(); y++ ){
            //   list = list.stream().distinct().collect(Collectors.toList());
            //Log.e("MIs TAG","++++++ " +list.get(y));
            palabraEncontra(list.get(y),replace+"  ");
        }


    }

    private void casosIdLargo(String arrayTexto,String val1, String val2) {
        String textoConcatenado;
        switch (arrayTexto){
            case "CLAVE":
            case "FECHA":
            case "AÑO":

                textoConcatenado = arrayTexto+" "+val1+ " "+val2;
                for (int i = 0 ; i < identificador.length ; i++){
                    if(textoConcatenado.equals(identificador[i])) {
                        // Log.e("Palabra encontrada","++++++++++++++++++++++++++ "+identificador[i]);
                        list.add(identificador[i]);
                    }
                }
                break;
        }

    }


    private void  getNombre(String text){
        int index = 0;
        String nomb = "";
        if (list.size() == 1) {
            String[] arrayTexto= text.split(" ");

            if (arrayTexto.length >=3){
                for (int recorrido = 1 ; recorrido <= 3 ; recorrido++ ){
                    nomb+=arrayTexto[recorrido]+" ";
                }
            }
            /*
            int inicio = text.indexOf("NOMBRE");
            int fin = text.indexOf(" ", inicio + 7);
            Log.e("Nombre ",text.substring(inicio + 7, fin));*/

            textValorS +="\nNombre: "+nomb ;
            txtresult.setText(textValorS);
        }else {
            for (int i = 0; i < list.size(); i++) {
                boolean intIndex = text.contains(list.get(i));
                if (intIndex) {
                    if (list.get(i).equals("NOMBRE")) {
                        int inicio = text.indexOf("NOMBRE");
                        int fin = text.indexOf(list.get(i+1), inicio + 7);
                        String name = text.substring(inicio + 7, fin);
                        String[] arrayTexto= name.split(" ");
                        if (arrayTexto.length<=4){
                            for (int x=0 ; x<arrayTexto.length; x++){
                                nomb+=arrayTexto[x]+" ";
                            }
                        }else if (arrayTexto.length >4){
                            for (int recorrido = 0 ; recorrido < 3 ; recorrido++ ){
                                nomb+=arrayTexto[recorrido]+" ";
                            }
                        }

                        textValorS +="\nNombre: "+nomb;
                        txtresult.setText(textValorS);
                    }
                }
            }
        }

    }

    private void extractNameIFE(String s) {
        String result = "";
        String[] lines = s.split(" ");
        for (int x = 0 ; x<= lines.length ; x++){
            if (lines[x].equals("NOMBRE") ) {
                result += lines[x+1]+" "+lines[x+2]+" "+lines[x+3];
            }
        }
        textValorS +="\nNombre: "+result;
        txtresult.setText(textValorS);


    }


    private void  getDomicilio(String text){
        if (list.size() == 1) {
            int inicio = text.indexOf("DOMICILIO");
            int fin = text.indexOf(" ", inicio + 10);
            String dom = text.substring(inicio + 10, fin);
            String[] arrayTexto= dom.split(" ");
            String domicilio = "";
            for (int y = 0; y < 6;y++ ){
                domicilio += arrayTexto[y]+" ";
            }
            textValorS +="\n Domicilio: "+domicilio;
            txtresult.setText(textValorS);
        }else {
            for (int i = 0; i < list.size(); i++) {
                boolean intIndex = text.contains(list.get(i));
                if (intIndex) {
                    if (list.get(i).equals("DOMICILIO")) {
                        int inicio = text.indexOf("DOMICILIO");
                        int fin = text.indexOf(list.get(i+1), inicio + 10);
                        String dom = text.substring(inicio + 10, fin);
                        String[] arrayTexto= dom.split(" ");
                        String domicilio = "";
                        for (int y = 0; y < 6;y++ ){
                            domicilio += arrayTexto[y]+" ";
                        }

                        Log.e("DOMICILIO ",text.substring(inicio + 10, fin));
                        textValorS +="\n Domicilio: "+domicilio;
                        txtresult.setText(textValorS);
                    }
                }
            }
        }
    }



    public void palabraEncontra(String palabra,String text){
        switch (palabra){
            case"NOMBRE":
                getNombre(text);
             //   extractNameIFE(text);
                break;
            case "DOMICILIO":
                getDomicilio(text);
                break;
            case "CLAVE DE ELECTOR":
                datoUnico(text,"CLAVE DE ELECTOR",17);

                break;
            case "CURP":
                datoUnico(text,"CURP",5);

                break;
            case "ESTADO":
                datoUnico(text,"ESTADO",7);
                break;
            case "MUNICIPIO":
                datoUnico(text,"MUNICIPIO",10);
                break;
            case "SECCION":
                datoUnico(text,"SECCION",8);
                break;
            case "LOCALIDAD":
                datoUnico(text,"LOCALIDAD",10);
                break;
            case "EMISION":
                datoUnico(text,"EMISION",8);
                break;
            case "VIGENCIA":
                datoUnico(text,"VIGENCIA",9);
                break;
            case "FECHA DE NACIMIENTO":
                datoUnico(text,"FECHA DE NACIMIENTO",20);
                break;

        }
    }



    public void datoUnico(String text,String tag, int separcion){
        int inicio = text.indexOf(tag);
        int fin = text.indexOf(" ", inicio + separcion);
        Log.e(tag, text.substring(inicio + separcion, fin) + "\n");
        textValorS+="\n"+tag+": "+text.substring(inicio + separcion, fin);
        txtresult.setText(textValorS);

    }






/*
     private void getTextFromImage(){
        //pasamos la imagen como bitmap
        Bitmap image =  BitmapFactory.decodeResource(getApplicationContext().getResources(),R.drawable.i);
        TextRecognizer textRecognizer = new TextRecognizer.Builder(getApplicationContext()).build();
        if (!textRecognizer.isOperational()){
            Toast.makeText(this,"No se encontro texto",Toast.LENGTH_LONG).show();
        }else {
            Frame frame= new Frame.Builder().setBitmap(image).build();
            SparseArray<TextBlock> items = textRecognizer.detect(frame);
            StringBuilder sB = new StringBuilder();
            for (int i = 0; i < items.size() ; i++){
                TextBlock myItem = items.valueAt(i);
                sB.append(myItem.getValue());
                sB.append("\n");
            }
            txtresult.setText(sB.toString());
        }

    }




    private void dispatchTakePictureIntent() {
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
            startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE);
        }
    }



    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        Bitmap bitmap = (Bitmap)data.getExtras().get("data");
        cameraImg.setImageBitmap(bitmap);
        getTextFromCamera(bitmap);

    }


    private void processImage(Bitmap bitmap) {
        Bitmap bitmap2;

        Matrix matrix = new Matrix();
       // matrix.postRotate(90);
        if (bitmap.getWidth() == bitmap.getHeight()) {
            int xc = (int) ((bitmap.getWidth() / 2) - (bitmap.getWidth() * 0.213));
            int yc = (int) ((bitmap.getHeight() / 2) - (bitmap.getHeight() * 0.327));
            Bitmap bitmapTemp = Bitmap.createBitmap(bitmap, xc, yc, (int) (bitmap.getWidth() * 0.425), (int) (bitmap.getHeight() * 0.654));
            bitmap2 = Bitmap.createBitmap(bitmapTemp, 0, 0, bitmapTemp.getWidth(), bitmapTemp.getHeight(), matrix, true);
            cameraImg.setImageBitmap(bitmap2);

        } else {
            int xc = (int) ((bitmap.getWidth() / 2) - (bitmap.getWidth() * 0.37));
            int yc = (int) ((bitmap.getHeight() / 2) - (bitmap.getHeight() * 0.19));
            Bitmap bitmapTemp = Bitmap.createBitmap(bitmap, xc, yc, (int) (bitmap.getWidth() * 0.74), (int) (bitmap.getHeight() * 0.37));
            bitmap2 = Bitmap.createBitmap(bitmapTemp, 0, 0, bitmapTemp.getWidth(), bitmapTemp.getHeight(), matrix, true);
            cameraImg.setImageBitmap(bitmap2);
            getTextFromCamera(bitmap2);
        }
    }


*/


}
