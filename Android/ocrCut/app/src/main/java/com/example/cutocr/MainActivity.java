package com.example.cutocr;

import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.cutocr.detectarborde.ScanActivity;
import com.theartofdev.edmodo.cropper.CropImage;
import com.theartofdev.edmodo.cropper.CropImageView;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    ImageButton btn;
    ImageView img;
    Uri uri,newImage=null;
    TextView tipotxt;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        btn = findViewById(R.id.btnImagen);
        img = findViewById(R.id.imgScan);

        img.setOnClickListener(this);
        btn.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btnImagen:
                CropImage.startPickImageActivity(this);
                break;
            case R.id.imgScan:
                if (newImage != null ){
                    Intent intent = new Intent(this,readerOCR.class);
                    intent.putExtra("imagen",newImage);
                    startActivity(intent);
                    //startScan(newImage);
                }

                break;
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == CropImage.PICK_IMAGE_CHOOSER_REQUEST_CODE && resultCode == Activity.RESULT_OK) {
            Uri imguri = CropImage.getPickImageResultUri(this, data);
            if (CropImage.isReadExternalStoragePermissionsRequired(this, imguri)) {
                uri = imguri;
                requestPermissions(new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, 0);
            }else {
                startCrop(imguri);
            }
        }
        if (requestCode == CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE){
            CropImage.ActivityResult result = CropImage.getActivityResult(data);
            if (resultCode == RESULT_OK){
                img.setImageURI(result.getUri());
                newImage = result.getUri();
            }
        }
    }

    private void startCrop(Uri imguri) {
        CropImage.activity(imguri)
        .setGuidelines(CropImageView.Guidelines.ON)
        .setMultiTouchEnabled(true)
        .start(this);

    }
}
