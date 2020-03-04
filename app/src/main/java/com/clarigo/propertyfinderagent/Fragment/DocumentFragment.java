package com.clarigo.propertyfinderagent.Fragment;


import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import com.bumptech.glide.Glide;
import com.clarigo.propertyfinderagent.R;
import com.clarigo.propertyfinderagent.Utils.ImagePicker;
import com.clarigo.propertyfinderagent.Utils.TypeFactory;
import com.clarigo.propertyfinderagent.app.SessionManager;
import com.clarigo.propertyfinderagent.dtos.SaveagentvehicleModel;
import com.clarigo.propertyfinderagent.retrofitHelper.APIClient;
import com.clarigo.propertyfinderagent.retrofitHelper.APIInterface;

import org.jetbrains.annotations.NotNull;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;
import java.util.Objects;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import pub.devrel.easypermissions.EasyPermissions;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static android.Manifest.permission.READ_EXTERNAL_STORAGE;
import static android.Manifest.permission.WRITE_EXTERNAL_STORAGE;

/**
 * A simple {@link Fragment} subclass.
 */
public class DocumentFragment extends Fragment implements EasyPermissions.PermissionCallbacks {

    private ImageView img_car_registration, img_upload_real_estate, upload_driver_licence;
    private EditText edt_first_name, edt_last_name, edt_dre, edt_email_login;
    private TextView tv_agent_information, tv_agent_membership, tv_agent_Documents, tv_agent_Registration, btn_submit, tvv_phone;
    private RelativeLayout relative_layout;
    private String vF_Name = "", vLast_Name = "", vPassword = "", vConfirm_Password = "", vDRE = "", vPhone_Num = "", vReg_Mls = "", vAssoc_Realtors = "",
            vEkey = "", vEmail = "";
    private APIInterface apiInterface;
    private SessionManager sessionManager;
    boolean is_File_one = false;
    private InputMethodManager mInputMethodManager;

    private View rootview;
    private static final int PICK_IMAGE_ID = 234; // the number doesn't matter


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        rootview = inflater.inflate(R.layout.fragment_document, container, false);
        apiInterface = APIClient.getClient().create(APIInterface.class);
        init();
        btn_submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        return rootview;
    }




    private void init() {
        sessionManager = new SessionManager(getActivity());
        edt_first_name = rootview.findViewById(R.id.edtt_first_name);
        edt_last_name = rootview.findViewById(R.id.edtt_last_name);
        edt_dre = rootview.findViewById(R.id.edtt_dre);
        tvv_phone = rootview.findViewById(R.id.tvv_phone);
        img_car_registration = rootview.findViewById(R.id.img_car_registration_);

        tv_agent_information = rootview.findViewById(R.id.tv_agent_information);
        tv_agent_Documents = rootview.findViewById(R.id.tv_agent_Documents);
        btn_submit = rootview.findViewById(R.id.btn_submit);


        String name = sessionManager.getUser_details().getAgentName();
        String[] separated = name.split(" ");
        edt_first_name.setText(separated[0]);
        edt_last_name.setText(separated[1]);
        edt_dre.setText(sessionManager.getUser_details().getDre());
        tvv_phone.setText(sessionManager.getUser_details().getAgentPhone());

        tv_agent_information.setTypeface(TypeFactory.getInstance(getActivity()).PoppinsSemiBold());
        tv_agent_Documents.setTypeface(TypeFactory.getInstance(getActivity()).PoppinsSemiBold());
        btn_submit.setTypeface(TypeFactory.getInstance(getActivity()).PoppinsSemiBold());

        img_car_registration.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                    if (ContextCompat.checkSelfPermission(Objects.requireNonNull(getActivity()), Manifest.permission.CAMERA) == PackageManager.PERMISSION_GRANTED
                            && ContextCompat.checkSelfPermission(getActivity(), WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED
                            && ContextCompat.checkSelfPermission(getActivity(), READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED) {
                        selectImage();
                    } else {
                        requestCameraPermission();
                    }
                } else {
                    selectImage();
                }
            }
        });
    }

    private void selectImage() {
        Intent chooseImageIntent = ImagePicker.getPickImageIntent(getActivity());
        startActivityForResult(chooseImageIntent, PICK_IMAGE_ID);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (requestCode) {
            case PICK_IMAGE_ID:
                Bitmap bitmap = ImagePicker.getImageFromResult(getActivity(), resultCode, data);
                Uri xxx = getLocalBitmapUri(bitmap);
                File file = new File(xxx.getPath());
                System.out.println("zzzzzzzzzz" + file);

                Glide.with(getActivity()).load(xxx).into(img_car_registration);

                // TODO use bitmap
                break;
            default:
                super.onActivityResult(requestCode, resultCode, data);
                break;
        }
    }

    private void requestCameraPermission() {
        if (ActivityCompat.shouldShowRequestPermissionRationale(Objects.requireNonNull(getActivity()), Manifest.permission.CAMERA)) {
        }
        ActivityCompat.requestPermissions(getActivity(), new String[]{Manifest.permission.CAMERA, WRITE_EXTERNAL_STORAGE, READ_EXTERNAL_STORAGE}, 1);
    }

    @Override
    public void onPermissionsGranted(int requestCode, @NotNull List<String> perms) {
    }

    @Override
    public void onPermissionsDenied(int requestCode, @NotNull List<String> perms) {

    }

    public static Uri getLocalBitmapUri(Bitmap bmp) {
        // Store image to default external storage directory
        Uri bmpUri = null;
        try {
            File file = new File(Environment.getExternalStoragePublicDirectory(
                    Environment.DIRECTORY_DOWNLOADS), "share_image_" + System.currentTimeMillis() + ".png");
            file.getParentFile().mkdirs();
            FileOutputStream out = new FileOutputStream(file);
            bmp.compress(Bitmap.CompressFormat.PNG, 90, out);
            out.close();
            bmpUri = Uri.fromFile(file);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return bmpUri;
    }
}
