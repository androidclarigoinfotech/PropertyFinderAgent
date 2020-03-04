package com.clarigo.propertyfinderagent.Utils;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.media.ExifInterface;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.provider.MediaStore;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Toast;

import androidx.core.content.FileProvider;

import com.clarigo.propertyfinderagent.BuildConfig;
import com.soundcloud.android.crop.Crop;

import java.io.File;
import java.io.FilterInputStream;
import java.io.IOException;
import java.io.InputStream;

import static android.app.Activity.RESULT_OK;


public class Utility {
    //SpotsDialog spotsDialog;

    public static Context mContext;
    public static Boolean isConnect = true;

    //
    public static void hideKeyboard(Context ctx) {
        InputMethodManager inputManager = (InputMethodManager) ctx.getSystemService(Context.INPUT_METHOD_SERVICE);
        // check if no view has focus:
        View v = ((Activity) ctx).getCurrentFocus();
        if (v == null)
            return;

        inputManager.hideSoftInputFromWindow(v.getWindowToken(), 0);
    }

    public static void hideKeyboard(Context ctx, View v) {
        InputMethodManager inputManager = (InputMethodManager) ctx.getSystemService(Context.INPUT_METHOD_SERVICE);
        // check if no view has focus:
        inputManager.hideSoftInputFromWindow(v.getWindowToken(), 0);
    }

/* public static boolean checkInternetConnection(Context context) {
        ConnectivityManager connectivity = (ConnectivityManager) context
                .getSystemService(Context.CONNECTIVITY_SERVICE);
        if (connectivity == null) {
            return false;
        } else {
            NetworkInfo[] info = connectivity.getAllNetworkInfo();
            if (info != null) {
                for (NetworkInfo anInfo : info) {
                    if (anInfo.getState() == NetworkInfo.State.CONNECTED) {
                        return true;
                    }
                }
            }
        }
        return false;
    }
}*/
    public static boolean isConnectingToInternet(Context context) {
        ConnectivityManager connectivity = null;
        try {
            connectivity = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
            if (connectivity != null) {
                NetworkInfo[] info = connectivity.getAllNetworkInfo();
                if (info != null)
                    for (int i = 0; i < info.length; i++)
                        if (info[i].getState() == NetworkInfo.State.CONNECTED) {
                            return true;
                        }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    public static String handleCropimage(int resultCode, Intent result, Context mContext) {
        File f = null;
        String profile_pic_ = "";
        if (resultCode == RESULT_OK) {
            f = new File(Crop.getOutput(result).getPath());
            String croped_file = "" + Crop.getOutput(result).getPath();
            //  PicturePath = croped_file;
            BitmapFactory.Options bmOptions = new BitmapFactory.Options();
            Bitmap bitmap = BitmapFactory.decodeFile(f.getAbsolutePath(), bmOptions);
            bitmap = Bitmap.createScaledBitmap(bitmap, 500, 500, true);
            bitmap = Utility.rotateImage(bitmap, f);
            profile_pic_ = croped_file;

        } else if (resultCode == Crop.RESULT_ERROR) {
            Toast.makeText(mContext, Crop.getError(result).getMessage(), Toast.LENGTH_SHORT).show();
        } else if (resultCode == Activity.RESULT_CANCELED) {
            Toast.makeText(mContext, "Canceled", Toast.LENGTH_SHORT).show();
        }
        return profile_pic_;
    }

    public static Bitmap rotateImage(final Bitmap bitmap, final File fileWithExifInfo) {
        if (bitmap == null) {
            return null;
        }
        Bitmap rotatedBitmap = bitmap;
        int orientation = 0;
        try {
            orientation = getImageOrientation(fileWithExifInfo.getAbsolutePath());
            if (orientation != 0) {
                Matrix matrix = new Matrix();
                matrix.postRotate(orientation, (float) bitmap.getWidth() / 2, (float) bitmap.getHeight() / 2);
                rotatedBitmap = Bitmap.createBitmap(bitmap, 0, 0, bitmap.getWidth(), bitmap.getHeight(), matrix, true);
                bitmap.recycle();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return rotatedBitmap;
    }

    public static int getImageOrientation(final String file) throws IOException {
        ExifInterface exif = new ExifInterface(file);
        int orientation = exif.getAttributeInt(ExifInterface.TAG_ORIENTATION, 1);
        switch (orientation) {
            case ExifInterface.ORIENTATION_NORMAL:
                return 0;
            case ExifInterface.ORIENTATION_ROTATE_90:
                return 90;
            case ExifInterface.ORIENTATION_ROTATE_180:
                return 180;
            case ExifInterface.ORIENTATION_ROTATE_270:
                return 270;
            default:
                return 0;
        }
    }

    public static Uri getCaptureImageOutputUri(Context mContext) {
        Uri outputFileUri = null;
        if (Build.VERSION.SDK_INT <= Build.VERSION_CODES.M) {
            File getImage = mContext.getExternalCacheDir();
            if (getImage != null) {
                outputFileUri = Uri.fromFile(new File(getImage.getPath(), "pickImageResult.jpeg"));
            }
        } else {
            File getImage = mContext.getExternalCacheDir();
            if (getImage != null) {
                outputFileUri = FileProvider.getUriForFile(mContext, BuildConfig.APPLICATION_ID + ".provider",
                        new File(getImage.getPath(), "pickImageResult.jpeg"));
            }
        }
        return outputFileUri;
    }

    public static Uri getPickImageResultUri(Intent data, Context mContext) {
        boolean isCamera = true;
        if (data != null && data.getData() != null) {
            String action = data.getAction();
            isCamera = action != null && action.equals(MediaStore.ACTION_IMAGE_CAPTURE);
        }
        return isCamera ? getCaptureImageOutputUri(mContext) : data.getData();
    }

    public static void beginCrop(Uri source, Context mContext) {
        File pro = new File(Utility.MakeDir("drift", mContext), System.currentTimeMillis() + ".jpg");
        Uri destination1 = Uri.fromFile(pro);
        Crop.of(source, destination1).asSquare().withAspect(500, 500).start((Activity) mContext);

    }

    public static String MakeDir(String filepath, Context appContext) {
        File path;
        if (!isExternalStorageAvailable() && !isExternalStorageReadOnly()) {
            path = new File(SaveFileIntoDir(filepath, appContext), filepath);
            if (!path.exists()) {
                path.mkdirs();
            }
        } else {
            path = new File(appContext.getExternalFilesDir(filepath), filepath);
            if (!path.exists()) {
                path.mkdirs();
            }
        }
        return path.toString();
    }

    public static boolean isExternalStorageReadOnly() {
        String extStorageState = Environment.getExternalStorageState();
        if (Environment.MEDIA_MOUNTED_READ_ONLY.equals(extStorageState)) {
            return true;
        }
        return false;
    }


    public static boolean isExternalStorageAvailable() {
        String extStorageState = Environment.getExternalStorageState();
        if (Environment.MEDIA_MOUNTED.equals(extStorageState)) {
            return true;
        }
        return false;
    }

    public static File SaveFileIntoDir(String filepath, Context appContext) {
        File directory = appContext.getDir(filepath, Context.MODE_PRIVATE);
        return directory;
    }

    static class FlushedInputStream extends FilterInputStream {
        public FlushedInputStream(InputStream inputStream) {
            super(inputStream);
        }
    }

}