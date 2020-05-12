package com.example.cutocr;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Bitmap;
import android.graphics.Matrix;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.util.SparseArray;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.vision.Frame;
import com.google.android.gms.vision.text.TextBlock;
import com.google.android.gms.vision.text.TextRecognizer;

import java.io.IOException;
import java.net.URI;
import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

public class readerOCR extends AppCompatActivity {
    TextView resultText,tipotxt;
    ImageView imageView;
    String textFinal = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reader_o_c_r);
        resultText = findViewById(R.id.txtResult);
        imageView = findViewById(R.id.img2);
        tipotxt = findViewById(R.id.txtTipo);

     /*   Bitmap photo = Datos.photoFinishBitmap;
        if (photo != null) {
           imageView.setImageDrawable(new BitmapDrawable(getResources(), photo));
           getTextTipo(photo);
        }
*/
       Bundle extras = getIntent().getExtras();
        Object img = extras.get("imagen");

        Uri image = (Uri) img;

        //imageView.setImageURI(image);
        Bitmap bitmapResult = null;
        try {
            bitmapResult = MediaStore.Images.Media.getBitmap(this.getContentResolver() , image);
        } catch (IOException e) {
            e.printStackTrace();
        }
        if (!image.equals(null)){
            imageView.setImageBitmap(bitmapResult);
             getTextTipo(bitmapResult);
        }



    }



    private void getTextTipo(Bitmap image){
            String texto = getTextFromImage(image);
            String ocrText = texto.replaceAll("[-+.^*_]","");
            if (ocrText.contains("CREDENCIAL PARA VOTAR")){
                resultText.setText(null);
                tipotxt.setText("CREDENCIAL PARA VOTAR");
                tags(ocrText);
//                resultText.setText(ocrText);
            }else if (ocrText.contains("ESTADOS UNIDOS MEXICANOS")){
                tipotxt.setText("ACTA DE NACIMIENTO");
                resultText.setText(null);
                textFinal += "Nombre: " +processNombre(image)+"\n";
                textFinal +=  "Fecha de nacimiento: "+processFechaNacimiento(image)+"\n";
                textFinal +=  "Lugar de nacimiento: "+processLugarNacimiento(image)+"\n";
                resultText.setText(textFinal);
            }else{
                resultText.setText(null);
                tipotxt.setText("otro tipo");
                textFinal += "Nombre: " +processNombre(image)+"\n";
               textFinal +=  "Fecha de nacimiento: "+processFechaNacimiento(image)+"\n";
                textFinal +=  "Lugar de nacimiento: "+processLugarNacimiento(image)+"\n";
                resultText.setText(textFinal);
            }
     //       resultText.setText(ocrText);
    }


//Nombre
    private String processNombre(Bitmap bitmap) {
        String rest = "";
        Bitmap bitmap2;
        Matrix matrix = new Matrix();
        //int yc = (int) ((bitmap.getHeight() ) - (bitmap.getHeight() * 0.19));
        int yc = (int) (bitmap.getHeight() * 0.28);//626
        Bitmap bitmapTemp = Bitmap.createBitmap(bitmap, 0, yc, (int) (bitmap.getWidth()-200 ), 48);
            bitmap2 = Bitmap.createBitmap(bitmapTemp, 0, 0, bitmapTemp.getWidth(), bitmapTemp.getHeight(), matrix, true);
            //imageView.setImageBitmap(bitmap2);
            rest = nombre(getTextFromImage(bitmap2));//getTextFromImage(bitmap2);
            return rest;
    }
//fecha de nacimiento
    private String processFechaNacimiento(Bitmap bitmap) {
        Bitmap bitmap2;
        String rest = "";
        Matrix matrix = new Matrix();
        //int yc = (int) ((bitmap.getHeight() ) - (bitmap.getHeight() * 0.19));
        int yc = (int) (bitmap.getHeight() * 0.30);//626
        Bitmap bitmapTemp = Bitmap.createBitmap(bitmap, 0, yc, (int) (bitmap.getWidth() ), 90);
        bitmap2 = Bitmap.createBitmap(bitmapTemp, 0, 0, bitmapTemp.getWidth(), bitmapTemp.getHeight(), matrix, true);
        //imageView.setImageBitmap(bitmap2);
        rest = fechaNacimiento(getTextFromImage(bitmap2));
        return rest;
    }
    //fecha de nacimiento
    private String processLugarNacimiento(Bitmap bitmap) {
        Bitmap bitmap2;
        String result;
        Matrix matrix = new Matrix();
        //int yc = (int) ((bitmap.getHeight() ) - (bitmap.getHeight() * 0.19));
        int yc = (int) (bitmap.getHeight() * 0.33);//626
        Bitmap bitmapTemp = Bitmap.createBitmap(bitmap, 0, yc, (int) (bitmap.getWidth()-300), 50);
        bitmap2 = Bitmap.createBitmap(bitmapTemp, 0, 0, bitmapTemp.getWidth(), bitmapTemp.getHeight(), matrix, true);
        //imageView.setImageBitmap(bitmap2);
        result = lugarNacimiento(getTextFromImage(bitmap2));
        return result;
    }


//Para obtener datos
    private String getTextFromImage(Bitmap image){
        String result="";
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
            Log.e("TEXTO",sB.toString());
            result = sB.toString();
        }
        String mayusculas = result.toUpperCase();
        String replaceP = mayusculas.replaceAll("[-+.^*_]","");
        String ocrText = replaceP.replaceAll("\n"," ");
        return ocrText;
    }





    private String  nombre(String text){
        int inicio = text.indexOf("NOMBRE:");
        String nombre = text.substring(inicio + 8);
        /*int index = 0;
        String nomb = "";
            String[] arrayTexto= text.split(" ");
            if (arrayTexto.length >=4){
                for (int recorrido = 1 ; recorrido <= 3 ; recorrido++ ){
                    nomb+=arrayTexto[recorrido]+" ";
                }
            }else{
                for (int recorrido = 1 ; recorrido <= arrayTexto.length ; recorrido++ ){
                    nomb+=arrayTexto[recorrido]+" ";
                }
            }*/
        return nombre;
    }
    private String fechaNacimiento(String text){
        String fecha ="";
        String remplace = text.replaceAll("[A-Za-z:]","");
        String[] arrayTexto= remplace.split(" ");
        if (arrayTexto != null){
            int tam = arrayTexto[0].length();
            int index=0;
            for (int v = 0 ; v< arrayTexto.length; v++){
                if (tam<arrayTexto[v].length()){
                    tam = arrayTexto[v].length();
                    index = v;
                }
            }

             fecha = arrayTexto[index];
        }

        return fecha;
    }


    private String lugarNacimiento(String text){
            int inicio = text.indexOf("LUGAR DE NACIMIENTO:");
            String dom = text.substring(inicio + 21);
        return dom;
    }

    //__________________________________________ OCR para ine ife______________________________
    String TAG="ImageOCR";
    ///ALgoritmo
    String textValorS="";
    //reconocimiento de datos TAG existentes
    String[] identificador={"LOCALIDAD","DOMICILIO","CURP","CLAVE DE ELECTOR","ESTADO","MUNICIPIO","NOMBRE","SECCION","EMISION","VIGENCIA",
            "FECHA DE NACIMIENTO","SEXO"};
    //Listapara almacenar TAGs encontrados en el documento
    String[] idLargos={"CLAVE","AÑO","FECHA"};

    List<String> list = new ArrayList<>();


    //primer paso pasar cadena a mayuscula
    //segundo quitar saltos de linea
    //tercero crear array apartir de los espacios
    //cuarto hacer recorrido y validar los tag existentes
    public void tags(String texto){
        String mayusculas = texto.toUpperCase();
        String replaceP = mayusculas.replaceAll("[-+.^:,*_]","");
        String replace = replaceP.replaceAll("\n"," ");
        String[] arrayTexto= replace.split(" ");
        Log.e("TEX",replace);
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
            if (arrayTexto.length >=4){
                for (int recorrido = 1 ; recorrido <= 3 ; recorrido++ ){
                    nomb+=arrayTexto[recorrido]+" ";
                }
            }else{
                for (int recorrido = 1 ; recorrido <= arrayTexto.length ; recorrido++ ){
                    nomb+=arrayTexto[recorrido]+" ";
                }
            }
            /*
            int inicio = text.indexOf("NOMBRE");
            int fin = text.indexOf(" ", inicio + 7);
            Log.e("Nombre ",text.substring(inicio + 7, fin));*/

            textValorS +="\nNombre: "+nomb ;
             resultText.setText(textValorS);
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
                        resultText.setText(textValorS);
                    }
                }
            }
        }

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
               resultText.setText(textValorS);
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
                        for (int y = 0; y <= 4;y++ ){
                            domicilio += arrayTexto[y]+" ";
                        }

                        Log.e("DOMICILIO ",text.substring(inicio + 10, fin));
                        textValorS +="\n Domicilio: "+domicilio;
                        resultText.setText(textValorS);
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
            case "EDAD":
                datoUnico(text,"EDAD",5);
            case "SEXO":
                datoUnico(text,"EDAD",5);
                break;
            case "FECHA DE NACIMIENTO":
                datoUnico(text,"FECHA DE NACIMIENTO",20);
                break;

        }
    }



    public void datoUnico(String text,String tag, int separcion){
        String result;
        int inicio = text.indexOf(tag);
        int fin = text.indexOf(" ", inicio + separcion);
        String textoTag = text.substring(inicio + separcion, fin);
        Log.e(tag, textoTag + "\n");
        textValorS+="\n"+tag+": "+ textoTag;
        resultText.setText(textValorS);

    }




}
