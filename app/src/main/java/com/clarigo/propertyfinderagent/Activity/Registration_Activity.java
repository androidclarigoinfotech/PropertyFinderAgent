package com.clarigo.propertyfinderagent.Activity;

import android.Manifest;
import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ToggleButton;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.bumptech.glide.Glide;
import com.clarigo.propertyfinderagent.R;
import com.clarigo.propertyfinderagent.Utils.TypeFactory;
import com.clarigo.propertyfinderagent.Utils.Utility;
import com.clarigo.propertyfinderagent.dtos.SIGNUP_DTO;
import com.clarigo.propertyfinderagent.retrofitHelper.APIClient;
import com.clarigo.propertyfinderagent.retrofitHelper.APIInterface;
import com.soundcloud.android.crop.Crop;

import org.jetbrains.annotations.NotNull;

import java.io.File;
import java.util.List;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import pub.devrel.easypermissions.EasyPermissions;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static android.Manifest.permission.READ_EXTERNAL_STORAGE;
import static android.Manifest.permission.WRITE_EXTERNAL_STORAGE;

public class Registration_Activity extends AppCompatActivity implements View.OnClickListener, EasyPermissions.PermissionCallbacks {

    private ToggleButton tb_registered_mls, tb_association, tb_ekey_access;
    private Button tv_send_approval;
    private ImageView img_back, img_upload_real_estate, upload_driver_licence;
    private EditText edt_first_name, edt_last_name, edt_dre, edt_phone, edt_email_login, edt_password, edt_repeat_password;
    private TextView tv_agent_information, tv_agent_membership, tv_agent_Documents, tv_agent_Registration;
    private RelativeLayout relative_layout;
    private String vF_Name = "", vLast_Name = "", vPassword = "", vConfirm_Password = "", vDRE = "", vPhone_Num = "", vReg_Mls = "", vAssoc_Realtors = "",
            vEkey = "", vEmail = "";
    APIInterface apiInterface;

    String mFileTempone = "", mFileTempTwo = "";
    boolean is_File_one = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_agent_registration);
        apiInterface = APIClient.getClient().create(APIInterface.class);

        init();
        click_events();
    }

    private void init() {
        tb_registered_mls = findViewById(R.id.tb_registered_mls);
        tb_association = findViewById(R.id.tb_association);
        tb_ekey_access = findViewById(R.id.tb_ekey_access);
        img_back = findViewById(R.id.img_back);
        img_upload_real_estate = findViewById(R.id.img_upload_real_estate);
        upload_driver_licence = findViewById(R.id.upload_driver_licence);
        edt_first_name = findViewById(R.id.edt_first_name);
        edt_last_name = findViewById(R.id.edt_last_name);
        edt_dre = findViewById(R.id.edt_dre);
        edt_phone = findViewById(R.id.edt_phone);
        edt_email_login = findViewById(R.id.edt_email_login);
        edt_password = findViewById(R.id.edt_password);
        edt_repeat_password = findViewById(R.id.edt_repeat_password);
        tv_agent_information = findViewById(R.id.tv_agent_information);
        tv_agent_membership = findViewById(R.id.tv_agent_membership);
        tv_agent_Documents = findViewById(R.id.tv_agent_Documents);
        tv_agent_Registration = findViewById(R.id.tv_agent_Registration);
        tv_send_approval = findViewById(R.id.tv_send_approval);
        relative_layout = findViewById(R.id.relative_layout);

        tv_agent_information.setTypeface(TypeFactory.getInstance(this).PoppinsSemiBold());
        tv_agent_membership.setTypeface(TypeFactory.getInstance(this).PoppinsSemiBold());
        tv_agent_Documents.setTypeface(TypeFactory.getInstance(this).PoppinsSemiBold());
        tv_agent_Registration.setTypeface(TypeFactory.getInstance(this).PoppinsSemiBold());
    }

    private void click_events() {
        img_back.setOnClickListener(this);
        tb_registered_mls.setOnClickListener(this);
        tv_send_approval.setOnClickListener(this);
        tb_association.setOnClickListener(this);
        tb_ekey_access.setOnClickListener(this);
        upload_driver_licence.setOnClickListener(this);
        img_upload_real_estate.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.img_back:
                finish();
                break;
            case R.id.tv_send_approval:
                if (validData()) {
                    signup_api();
                }

                break;
            case R.id.tb_registered_mls:
                if (tb_registered_mls.isChecked()) {
                    vReg_Mls = "Yes";
                } else {
                    vReg_Mls = "No";
                }
                break;
            case R.id.tb_association:
                if (tb_association.isChecked()) {
                    vAssoc_Realtors = "Yes";
                } else {
                    vAssoc_Realtors = "No";
                }
                break;
            case R.id.tb_ekey_access:
                if (tb_ekey_access.isChecked()) {
                    vEkey = "Yes";
                } else {
                    vEkey = "No";
                }
                break;
            case R.id.upload_driver_licence:
                is_File_one = false;
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                    if (ContextCompat.checkSelfPermission(Registration_Activity.this, Manifest.permission.CAMERA) == PackageManager.PERMISSION_GRANTED
                            && ContextCompat.checkSelfPermission(Registration_Activity.this, WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED
                            && ContextCompat.checkSelfPermission(Registration_Activity.this, READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED) {
                        selectImage();
                    } else {
                        requestCameraPermission();
                    }
                } else {
                    selectImage();
                }
                break;
            case R.id.img_upload_real_estate:
                is_File_one = true;
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                    if (ContextCompat.checkSelfPermission(Registration_Activity.this, Manifest.permission.CAMERA) == PackageManager.PERMISSION_GRANTED
                            && ContextCompat.checkSelfPermission(Registration_Activity.this, WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED
                            && ContextCompat.checkSelfPermission(Registration_Activity.this, READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED) {
                        selectImage();
                    } else {
                        requestCameraPermission();
                    }
                } else {
                    selectImage();
                }
                break;

        }
    }


    private void selectImage() {
        final CharSequence[] options = {"Camera",
                ("Gallary"), getString(R.string.close)};
        android.app.AlertDialog.Builder builder = new android.app.AlertDialog.Builder(Registration_Activity.this);
        builder.setTitle("Add Image");
        builder.setItems(options, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int item) {
                if (item == 0) {
                    Uri outputFileUri = Utility.getCaptureImageOutputUri(Registration_Activity.this);
                    Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                    intent.putExtra(MediaStore.EXTRA_OUTPUT, outputFileUri);
                    startActivityForResult(intent, 1);
                } else if (item == 1) {
                    Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                    startActivityForResult(intent, 2);
                } else if (item == 2) {
                    dialog.dismiss();
                }
            }
        });
        builder.show();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == Activity.RESULT_OK) {
            if (requestCode == 1) {
                Uri imageUri = Utility.getPickImageResultUri(data, Registration_Activity.this);
                Utility.beginCrop(imageUri, Registration_Activity.this);
            } else if (requestCode == 2) {
                Uri selectedImage = data.getData();
                Utility.beginCrop(selectedImage, Registration_Activity.this);
            } else if (requestCode == Crop.REQUEST_CROP) {
                if (is_File_one) {
                    mFileTempone = Utility.handleCropimage(resultCode, data, Registration_Activity.this);
                    Glide.with(Registration_Activity.this).load(mFileTempone).asBitmap().into(img_upload_real_estate);
                } else {
                    mFileTempTwo = Utility.handleCropimage(resultCode, data, Registration_Activity.this);
                    Glide.with(Registration_Activity.this).load(mFileTempTwo).asBitmap().into(upload_driver_licence);

                }
            }
        }
    }

    private void requestCameraPermission() {
        if (ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.CAMERA)) {
        }
        ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.CAMERA, WRITE_EXTERNAL_STORAGE, READ_EXTERNAL_STORAGE}, 1);
    }

    @Override
    public void onPermissionsGranted(int requestCode, @NotNull List<String> perms) {
    }

    @Override
    public void onPermissionsDenied(int requestCode, @NotNull List<String> perms) {

    }


    private void signup_api() {
        MultipartBody.Part imagePartone, imagePartTwo;
        if (mFileTempone == null || mFileTempone.equals("")) {
            imagePartone = MultipartBody.Part.createFormData("realstatelicense", "");
        } else {
            File file_one = new File(mFileTempone);
            RequestBody requestBody = RequestBody.create(MediaType.parse("multipart/form-data"), file_one);
            imagePartone = MultipartBody.Part.createFormData("realstatelicense", String.valueOf(file_one), requestBody);
        }

        if (mFileTempTwo == null || mFileTempTwo.equals("")) {
            imagePartTwo = MultipartBody.Part.createFormData("dri_license", "");
        } else {
            File file_two = new File(mFileTempTwo);
            RequestBody requestBody = RequestBody.create(MediaType.parse("multipart/form-data"), file_two);
            imagePartTwo = MultipartBody.Part.createFormData("dri_license", String.valueOf(file_two), requestBody);
        }
        MultipartBody.Part _vName = MultipartBody.Part.createFormData("name", vF_Name + " " + vLast_Name);
        MultipartBody.Part _vEmail = MultipartBody.Part.createFormData("email", vEmail);
        MultipartBody.Part _vPhone = MultipartBody.Part.createFormData("phone", vPhone_Num);
        MultipartBody.Part _vPass = MultipartBody.Part.createFormData("password", vPassword);
        MultipartBody.Part _vConf_pass = MultipartBody.Part.createFormData("confirm_password", vConfirm_Password);
        MultipartBody.Part _vDRE = MultipartBody.Part.createFormData("dre", vDRE);
        MultipartBody.Part _vReg_Mls = MultipartBody.Part.createFormData("rmls", vReg_Mls);
        MultipartBody.Part _vAss_Realtors = MultipartBody.Part.createFormData("aor", vAssoc_Realtors);
        MultipartBody.Part _vEkey = MultipartBody.Part.createFormData("ekey", vEkey);


        Call<SIGNUP_DTO> profile_dtoCall = apiInterface.signup_responce(_vName, _vEmail, _vPhone, _vPass, _vConf_pass, _vDRE, _vReg_Mls, _vAss_Realtors, _vEkey, imagePartone, imagePartTwo);
        profile_dtoCall.enqueue(new Callback<SIGNUP_DTO>() {
            @Override
            public void onResponse(@NotNull Call<SIGNUP_DTO> call, @NotNull Response<SIGNUP_DTO> response) {
                SIGNUP_DTO profile_update_dto = response.body();
                if (profile_update_dto == null) {
                    Toast.makeText(Registration_Activity.this, "Unexpected response from server!", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (profile_update_dto.getResponse().equalsIgnoreCase("true")) {
                    Toast.makeText(Registration_Activity.this, profile_update_dto.getMessage(), Toast.LENGTH_SHORT).show();
                    show_propety_details_dailoge(relative_layout);
                }
            }

            @Override
            public void onFailure(@NotNull Call<SIGNUP_DTO> call, @NotNull Throwable t) {
                Log.e("onFaliure..", "onFailure: ", t);

            }
        });

    }

    private boolean validData() {
        vF_Name = edt_first_name.getText().toString().trim();
        vLast_Name = edt_last_name.getText().toString().trim();
        vEmail = edt_email_login.getText().toString().trim();
        vPassword = edt_password.getText().toString().trim();
        vConfirm_Password = edt_repeat_password.getText().toString().trim();
        vPhone_Num = edt_phone.getText().toString().trim();
        vDRE = edt_dre.getText().toString().trim();
        boolean isValid = false;

        if (vF_Name.isEmpty()) {
            isValid = false;
            Toast.makeText(this, "Please enter first name !", Toast.LENGTH_SHORT).show();
        } else if (vLast_Name.isEmpty()) {
            isValid = false;
            Toast.makeText(this, "Please enter last name !", Toast.LENGTH_SHORT).show();
        } else if (vEmail.isEmpty()) {
            isValid = false;
            Toast.makeText(this, "Please enter vaiid email address !", Toast.LENGTH_SHORT).show();
        } else if (vDRE.isEmpty()) {
            isValid = false;
            Toast.makeText(this, "Please enter vaiid DRE !", Toast.LENGTH_SHORT).show();
        } else if (vPhone_Num.isEmpty()) {
            isValid = false;
            Toast.makeText(this, "Please Enter Mobile Number !", Toast.LENGTH_SHORT).show();
        } else if (edt_phone.getText().toString().length() < 10 || edt_phone.getText().toString().length() > 11) {
            isValid = false;
            Toast.makeText(this, "Please Enter Vailid Mobile Number !", Toast.LENGTH_SHORT).show();
        } else if (!android.util.Patterns.EMAIL_ADDRESS.matcher(vEmail).matches()) {
            isValid = false;
            Toast.makeText(this, "Please enter vaiid email address !", Toast.LENGTH_SHORT).show();
        } else if (vPassword.isEmpty()) {
            isValid = false;
            Toast.makeText(this, "Please enter vaiid password !", Toast.LENGTH_SHORT).show();
        } else if (vPassword.length() < 6 || vPassword.length() > 12) {
            isValid = false;
            Toast.makeText(this, "Password should greater then 6 charecters !", Toast.LENGTH_SHORT).show();
        } else if (!vPassword.equals(vConfirm_Password)) {
            Toast.makeText(this, "Password doesn't match repeat password !", Toast.LENGTH_SHORT).show();

            isValid = false;
        } else {
            isValid = true;
        }
        return isValid;
    }


    private void show_propety_details_dailoge(RelativeLayout relative_layout) {

        LayoutInflater inflater = (LayoutInflater) this.getSystemService(LAYOUT_INFLATER_SERVICE);
        View popupView = inflater.inflate(R.layout.registration_successful_dailoge, null);

        // create the popup window
        int width = LinearLayout.LayoutParams.MATCH_PARENT;
        int height = LinearLayout.LayoutParams.MATCH_PARENT;
        boolean focusable = true; // lets taps outside the popup also dismiss it
        final PopupWindow popupWindow = new PopupWindow(popupView, width, height, focusable);

        // show the popup window
        // which view you pass in doesn't matter, it is only used for the window tolken

        popupWindow.showAtLocation(relative_layout, Gravity.CENTER, 0, 0);

        TextView txt_address, tv_address_property, tv_bed_details, tv_bath_details, tv_miles_details;
        Button btn_close;

        btn_close = popupView.findViewById(R.id.btn_close);
        btn_close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getBaseContext(), SignInActivity.class);
                startActivity(intent);
                finish();
                popupWindow.dismiss();
            }
        });
    }

}
