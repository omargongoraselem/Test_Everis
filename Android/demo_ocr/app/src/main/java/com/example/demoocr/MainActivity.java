package com.example.demoocr;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.TextView;

import com.example.demoocr.imgocr.ImageDetectedOcr;
import com.google.android.gms.common.api.CommonStatusCodes;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private CompoundButton autoFocus;
    private CompoundButton useFlash;
    private TextView statusMessage;
    private TextView textValue;
    String textValorS="";
    FloatingActionButton floatingActionButton;


    private static final int RC_OCR_CAPTURE = 9003;
    private static final String TAG = "MainActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        statusMessage = findViewById(R.id.status_message);
        textValue = findViewById(R.id.text_value);

        autoFocus =  findViewById(R.id.auto_focus);
        useFlash =  findViewById(R.id.use_flash);
        floatingActionButton = findViewById(R.id.floatingActionButton);

        floatingActionButton.setOnClickListener(this);

        findViewById(R.id.read_text).setOnClickListener(this);

    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(requestCode == RC_OCR_CAPTURE) {
            if (resultCode == CommonStatusCodes.SUCCESS) {
                if (data != null) {
                    String text = data.getStringExtra(CaptureActivity.TextBlockObject);
                    statusMessage.setText("Texto Reconocido");
                      textValue.setText(text);

                    //tags(text);
                } else {
                    statusMessage.setText("Error al reconocer");
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

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.read_text) {
            // launch Ocr capture activity.
            Intent intent = new Intent(this, CaptureActivity.class);
            intent.putExtra(CaptureActivity.AutoFocus, autoFocus.isChecked());
            intent.putExtra(CaptureActivity.UseFlash, useFlash.isChecked());
            textValue.setText("");
            textValorS = "";
            startActivityForResult(intent, RC_OCR_CAPTURE);
        }else  if (v.getId() == R.id.floatingActionButton){
            Intent intent= new Intent(this, ImageDetectedOcr.class);
            startActivity(intent);
        }
    }
}
