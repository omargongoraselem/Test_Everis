package com.example.cutocr;

import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.cutocr.detectarborde.ScanActivity;
import com.theartofdev.edmodo.cropper.CropImage;
import com.theartofdev.edmodo.cropper.CropImageView;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    ImageButton btn;
    ImageView img;
    Uri uri,newImage=null;
    TextView tipotxt;


    private final static int MY_PERMISSIONS_REQUEST_READ_EXTERNAL_STORAGE = 1;
    private static String[] PERMISOS = {
            Manifest.permission.CAMERA,
            Manifest.permission.WRITE_EXTERNAL_STORAGE,
            Manifest.permission.READ_EXTERNAL_STORAGE
    };
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        btn = findViewById(R.id.btnImagen);
        img = findViewById(R.id.imgScan);

        img.setOnClickListener(this);
        btn.setOnClickListener(this);
        permisos();
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
        CropImage.activity(imguri).setAllowFlipping(false)
        .setGuidelines(CropImageView.Guidelines.ON)
        .setMultiTouchEnabled(true)
        .setAllowRotation(true)
        .setAllowCounterRotation(true)
        .start(this);

    }

    private void permisos(){
        int permissionCheck = ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE);
        // Si aún no tiene el permiso concedido
        if(permissionCheck!= PackageManager.PERMISSION_GRANTED){
            if (ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.WRITE_EXTERNAL_STORAGE)){

            }else{
                ActivityCompat.requestPermissions(this, PERMISOS, MY_PERMISSIONS_REQUEST_READ_EXTERNAL_STORAGE);
            }
        }
    }
    @Override
    public void onRequestPermissionsResult(int requestCode, String permissions[], int[] grantResults){
        String mensaje = "";
        if(grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED){
            mensaje = "Permiso concedido";
        }else{
            mensaje = "Permiso no concedido";
        }
       Toast.makeText(this, mensaje, Toast.LENGTH_LONG).show();
    }


}
