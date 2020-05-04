package com.example.cutocr;

import android.util.Log;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

public class ocrDetectar {
    String TAG="ImageOCR";
    ///ALgoritmo
    String textValorS="";
    //reconocimiento de datos TAG existentes
    String[] identificador={"LOCALIDAD","DOMICILIO","CURP","CLAVE DE ELECTOR","ESTADO","MUNICIPIO","NOMBRE","SECCION","EMISION","VIGENCIA",
            "FECHA DE NACIMIENTO"};
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
           // txtresult.setText(textValorS);
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
     //                   txtresult.setText(textValorS);
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
       // txtresult.setText(textValorS);


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
         //   txtresult.setText(textValorS);
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
    //                    txtresult.setText(textValorS);
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
        String textoTag = text.substring(inicio + separcion, fin);
        Log.e(tag, textoTag + "\n");
        textValorS+="\n"+tag+": "+ textoTag;
  //      txtresult.setText(textValorS);

    }

}
