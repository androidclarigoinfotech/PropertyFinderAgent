package com.clarigo.propertyfinderagent.constant;

import android.Manifest;
import android.app.Activity;
import android.content.pm.PackageManager;

import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

public class Constants {

    public static final int MY_PERMISSIONS_REQUEST_STORAGE = 1001;
    public static final int MY_PERMISSIONS_REQUEST_LOCATION = 1002;
    public static final int MY_PERMISSIONS_REQUEST_CAMERA = 1003;

    public static boolean checkStoragePermission(Activity context) {
        if (ContextCompat.checkSelfPermission(context,
                Manifest.permission.READ_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED) {

            if (ActivityCompat.shouldShowRequestPermissionRationale(context, Manifest.permission.READ_EXTERNAL_STORAGE)) {
                ActivityCompat.requestPermissions(context, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, MY_PERMISSIONS_REQUEST_STORAGE);

            } else {

                ActivityCompat.requestPermissions(context, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, MY_PERMISSIONS_REQUEST_STORAGE);
            }
            return false;
        } else {
            return true;
        }
    }

    public boolean checkLocationPermission(Activity context) {
        if (ContextCompat.checkSelfPermission(context,
                Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {

            if (ActivityCompat.shouldShowRequestPermissionRationale(context,
                    Manifest.permission.ACCESS_FINE_LOCATION)) {
                ActivityCompat.requestPermissions(context, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, MY_PERMISSIONS_REQUEST_STORAGE);
            } else {
                ActivityCompat.requestPermissions(context, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, MY_PERMISSIONS_REQUEST_LOCATION);
            }

            return false;
        } else {
            return true;
        }
    }

    public static boolean checkCameraPermission(Activity context) {
        if (ContextCompat.checkSelfPermission(context,
                Manifest.permission.CAMERA)
                != PackageManager.PERMISSION_GRANTED) {

            if (ActivityCompat.shouldShowRequestPermissionRationale(context, Manifest.permission.CAMERA)) {
                ActivityCompat.requestPermissions(context, new String[]{Manifest.permission.CAMERA}, MY_PERMISSIONS_REQUEST_CAMERA);
            } else {
                ActivityCompat.requestPermissions(context, new String[]{Manifest.permission.CAMERA}, MY_PERMISSIONS_REQUEST_CAMERA);
            }
            return false;
        } else {
            return true;
        }
    }
}
