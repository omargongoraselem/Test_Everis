package com.example.cutocr;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import com.scanlibrary.ScanActivity;
import com.scanlibrary.ScanConstants;

import java.io.ByteArrayOutputStream;
import java.io.FileOutputStream;
import java.io.IOException;

public class NewScanActivity extends AppCompatActivity implements View.OnClickListener {
    Button camera,gallery,scan;
    ImageView img;
    int REQUEST_CODE = 99;
    Bitmap newImage=null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_scan);
        camera = findViewById(R.id.btnCamara);
        gallery = findViewById(R.id.btnGaleria);
        img = findViewById(R.id.imgScan);
        scan = findViewById(R.id.btnscann);
        camera.setOnClickListener(this);
        gallery.setOnClickListener(this);
        img.setOnClickListener(this);
        scan.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btnCamara:
                openCamera();
                break;
            case R.id.btnGaleria:
                openGallery();
                break;
            case R.id.btnscann:
               /* img.buildDrawingCache();
                setBitmap_transfer(img.getDrawingCache());*/
                Datos.photoFinishBitmap = newImage;
                Intent intent = new Intent(NewScanActivity.this, readerOCR.class);
                //intent.putExtra("imagen",newImage);
                startActivity(intent);
                 //startScan(newImage);

                break;
        }
    }

/*
    private static Bitmap bitmap_transfer;

    public static Bitmap getBitmap_transfer() {
        return bitmap_transfer;
    }
    public static void setBitmap_transfer(Bitmap bitmap_transfer_param) {
        bitmap_transfer = bitmap_transfer_param;
    }*/

    public void openCamera(){
        int preference = ScanConstants.OPEN_CAMERA;
        Intent intent = new Intent(this, ScanActivity.class);
        intent.putExtra(ScanConstants.OPEN_INTENT_PREFERENCE, preference);
        startActivityForResult(intent, REQUEST_CODE);
    }

    public void  openGallery(){
        int preference = ScanConstants.OPEN_MEDIA;
        Intent intent = new Intent(this, ScanActivity.class);
        intent.putExtra(ScanConstants.OPEN_INTENT_PREFERENCE, preference);
        startActivityForResult(intent, REQUEST_CODE);
    }
    public String createImageFromBitmap(Bitmap bitmap) {
        String fileName = "myImage";
        //no .png or .jpg needed
        try { ByteArrayOutputStream bytes = new ByteArrayOutputStream();
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, bytes);
            FileOutputStream fo = openFileOutput(fileName, Context.MODE_PRIVATE);
            fo.write(bytes.toByteArray());
            // remember close file output
            fo.close();
        } catch (Exception e) {
            e.printStackTrace();
            fileName = null;
        } return fileName;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_CODE && resultCode == Activity.RESULT_OK) {
            Uri uri = data.getExtras().getParcelable(ScanConstants.SCANNED_RESULT);
            Bitmap bitmap = null;
            try {
                bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), uri);
                getContentResolver().delete(uri, null, null);
                img.setImageBitmap(bitmap);
                newImage = bitmap;
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
