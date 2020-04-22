package com.example.demonotificaciones;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.google.firebase.messaging.FirebaseMessaging;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    Button especifico,topico;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        especifico = findViewById(R.id.btnEspecifico);
        topico = findViewById(R.id.btnTopico);
        especifico.setOnClickListener(this);
        topico.setOnClickListener(this);

    }


    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btnEspecifico:

                break;
            case R.id.btnTopico:
                break;
        }
    }


    private void notificacionEspecifica(){
    }

    private void notificacionTopica(){

    }

}
