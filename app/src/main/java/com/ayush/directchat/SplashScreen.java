package com.ayush.directchat;

import static android.Manifest.permission.READ_CALL_LOG;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.ayush.directchat.Utility.StorageUtil;


public class SplashScreen extends AppCompatActivity {

    private static final int READ_CALL = 54;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        StorageUtil storage = new StorageUtil(this);

        if (storage.getMode()){
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
        }else {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
        }

        checkPermission();
       // openHomeActivity();
    }

   private void checkPermission(){

        if (ContextCompat.checkSelfPermission(getApplicationContext(), READ_CALL_LOG) == PackageManager.PERMISSION_GRANTED){
            openHomeActivity();
        }else {
            requestStoragePermission();
        }
    }

    private void requestStoragePermission() {
        if (ActivityCompat.shouldShowRequestPermissionRationale(SplashScreen.this, READ_CALL_LOG)) {
            new AlertDialog.Builder(this)
                    .setTitle("READ_CALL_LOG Permission needed")
                    .setMessage("This permission is required to display the call logs.")
                    .setPositiveButton("ok", (dialog, which) -> ActivityCompat.requestPermissions(SplashScreen.this, new String[]{READ_CALL_LOG}, READ_CALL))
                    .setNegativeButton("cancel", (dialog, which) -> dialog.dismiss())
                    .create().show();
        } else {
            ActivityCompat.requestPermissions(SplashScreen.this, new String[]{READ_CALL_LOG}, READ_CALL);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {

        if (requestCode == READ_CALL) {
            if (grantResults.length > 0) {
                if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    openHomeActivity();
                } else {
                    Toast.makeText(SplashScreen.this, "Permission Denied", Toast.LENGTH_SHORT).show();
                    finish();
                }
            }
        } else {
            super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        }
    }

    private void openHomeActivity() {
        Intent intent = new Intent(SplashScreen.this,HomeActivity.class);
        startActivity(intent);
        finish();
    }
}
