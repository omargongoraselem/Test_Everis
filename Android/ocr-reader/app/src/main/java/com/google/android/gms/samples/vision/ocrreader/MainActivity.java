/*
 * Copyright (C) The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.google.android.gms.samples.vision.ocrreader;

import android.content.Intent;
import android.os.Bundle;
import android.app.Activity;
import android.util.Log;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.TextView;

import com.google.android.gms.common.api.CommonStatusCodes;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

/**
 * Main activity demonstrating how to pass extra parameters to an activity that
 * recognizes text.
 */
public class MainActivity extends Activity implements View.OnClickListener {

    // Use a compound button so either checkbox or switch widgets work.
    private CompoundButton autoFocus;
    private CompoundButton useFlash;
    private TextView statusMessage;
    private TextView textValue;
    String textValorS="";


    private static final int RC_OCR_CAPTURE = 9003;
    private static final String TAG = "MainActivity";

    //reconocimiento de datos TAG existentes
    String[] identificador={"LOCALIDAD","DOMICILIO","CURP","CLAVE DE ELECTOR","ESTADO","MUNICIPIO","NOMBRE","SECCION","EMISION","VIGENCIA",
            "FECHA DE NACIMIENTO"};
    //Listapara almacenar TAGs encontrados en el documento
    String[] idLargos={"CLAVE","AÑO","FECHA"};

    List<String> list = new ArrayList<>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        statusMessage = (TextView)findViewById(R.id.status_message);
        textValue = (TextView)findViewById(R.id.text_value);

        autoFocus = (CompoundButton) findViewById(R.id.auto_focus);
        useFlash = (CompoundButton) findViewById(R.id.use_flash);

        findViewById(R.id.read_text).setOnClickListener(this);
    }

    /**
     * Called when a view has been clicked.
     *
     * @param v The view that was clicked.
     */
    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.read_text) {
            // launch Ocr capture activity.
            Intent intent = new Intent(this, OcrCaptureActivity.class);
            intent.putExtra(OcrCaptureActivity.AutoFocus, autoFocus.isChecked());
            intent.putExtra(OcrCaptureActivity.UseFlash, useFlash.isChecked());
            textValue.setText("");
            list.clear();
            textValorS = "";

            startActivityForResult(intent, RC_OCR_CAPTURE);
        }
    }

    /**
     * Called when an activity you launched exits, giving you the requestCode
     * you started it with, the resultCode it returned, and any additional
     * data from it.  The <var>resultCode</var> will be
     * {@link #RESULT_CANCELED} if the activity explicitly returned that,
     * didn't return any result, or crashed during its operation.
     * <p/>
     * <p>You will receive this call immediately before onResume() when your
     * activity is re-starting.
     * <p/>
     *
     * @param requestCode The integer request code originally supplied to
     *                    startActivityForResult(), allowing you to identify who this
     *                    result came from.
     * @param resultCode  The integer result code returned by the child activity
     *                    through its setResult().
     * @param data        An Intent, which can return result data to the caller
     *                    (various data can be attached to Intent "extras").
     * @see #startActivityForResult
     * @see #createPendingResult
     * @see #setResult(int)
     */
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(requestCode == RC_OCR_CAPTURE) {
            if (resultCode == CommonStatusCodes.SUCCESS) {
                if (data != null) {
                    String text = data.getStringExtra(OcrCaptureActivity.TextBlockObject);
                    statusMessage.setText(R.string.ocr_success);
                  //  textValue.setText(text);

                    tags(text);
                } else {
                    statusMessage.setText(R.string.ocr_failure);
                    Log.d(TAG, "No Text captured, intent data is null");
                }
            } else {
                statusMessage.setText(String.format(getString(R.string.ocr_error),
                        CommonStatusCodes.getStatusCodeString(resultCode)));
            }
        }
        else {
            super.onActivityResult(requestCode, resultCode, data);
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
            textValue.setText(textValorS);
        }else {
            for (int i = 0; i < list.size(); i++) {
                boolean intIndex = text.contains(list.get(i));
                if (intIndex) {
                    if (list.get(i).equals("NOMBRE")) {
                        int inicio = text.indexOf("NOMBRE");
                        int fin = text.indexOf(list.get(i+1), inicio + 7);
                        Log.e("Nombre ",text.substring(inicio + 7, fin));
                        textValorS +="\nNombre: "+text.substring(inicio + 7, fin);
                        textValue.setText(textValorS);
                    }
                }
            }
        }

    }




    private void  getDomicilio(String text){
        if (list.size() == 1) {
            int inicio = text.indexOf("DOMICILIO");
            int fin = text.indexOf(" ", inicio + 10);
            Log.e("DOMICILIO ",text.substring(inicio + 10, fin));
            textValorS +="\n Domicilio: "+text.substring(inicio + 10, fin);
            textValue.setText(textValorS);
        }else {
            for (int i = 0; i < list.size(); i++) {
                boolean intIndex = text.contains(list.get(i));
                if (intIndex) {
                    if (list.get(i).equals("DOMICILIO")) {
                        int inicio = text.indexOf("DOMICILIO");
                        int fin = text.indexOf(list.get(i+1), inicio + 10);
                        Log.e("DOMICILIO ",text.substring(inicio + 10, fin));
                        textValorS +="\n Domicilio: "+text.substring(inicio + 10, fin);
                        textValue.setText(textValorS);
                    }
                }
            }
        }
    }



    public void palabraEncontra(String palabra,String text){
        switch (palabra){
            case"NOMBRE":
                getNombre(text);


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
                    textValue.setText(textValorS);

    }


}
