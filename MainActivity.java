package com.example.qrandbarcodescanner;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

    Button button;

    public static final int CAMERA_REQUEST_PERMISSION=1;
    public static final int SCANNER_REQUEST_CODE=2;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        button=findViewById(R.id.btnscan);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
              startScanningActivity();
            }
        });
    }

    private void startScanningActivity(){
        if (checkSelfPermission(Manifest.permission.CAMERA)!= PackageManager.PERMISSION_GRANTED){
            requestPermissions(new String[] {Manifest.permission.CAMERA},CAMERA_REQUEST_PERMISSION);
        }else{
            startActivityForResult(new Intent(MainActivity.this,ScanActivity.class),SCANNER_REQUEST_CODE);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode==CAMERA_REQUEST_PERMISSION){
            if (grantResults[0]==PackageManager.PERMISSION_GRANTED){
                startActivityForResult(new Intent(MainActivity.this,ScanActivity.class),SCANNER_REQUEST_CODE);
            }
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode==SCANNER_REQUEST_CODE){
            if (resultCode==RESULT_OK){
               button.setText(data.getStringExtra("scanning_result"));
            }
        }
    }
}