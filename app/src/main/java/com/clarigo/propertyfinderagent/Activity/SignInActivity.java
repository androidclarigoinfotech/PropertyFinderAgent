package com.clarigo.propertyfinderagent.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.clarigo.propertyfinderagent.R;
import com.clarigo.propertyfinderagent.Utils.TypeFactory;
import com.clarigo.propertyfinderagent.Utils.Utility;
import com.clarigo.propertyfinderagent.app.SessionManager;
import com.clarigo.propertyfinderagent.dtos.USER_LOGIN_DATA_DTO;
import com.clarigo.propertyfinderagent.dtos.USER_LOGIN_DTO;
import com.clarigo.propertyfinderagent.retrofitHelper.APIClient;
import com.clarigo.propertyfinderagent.retrofitHelper.APIInterface;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.InstanceIdResult;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SignInActivity extends AppCompatActivity {

    private ImageView img_logo, img_back;
    private EditText edt_mobile, edt_password;
    private TextView txt_welcome, txt_plz_login, txt_sign_up, btn_signin;
    private String vMobile = "", vPassword = "";
    APIInterface apiInterface;
    private SessionManager sessionManager;
    private ArrayList<USER_LOGIN_DATA_DTO> user_login_data_dtos = new ArrayList<>();
    private ProgressBar prgress;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in);
        apiInterface = APIClient.getClient().create(APIInterface.class);
        prgress = findViewById(R.id.prgress);
        sessionManager = new SessionManager(this);
        FirebaseInstanceId.getInstance().getInstanceId().addOnSuccessListener(SignInActivity.this, new OnSuccessListener<InstanceIdResult>() {
            @Override
            public void onSuccess(InstanceIdResult instanceIdResult) {
                String device_token = instanceIdResult.getToken();
                sessionManager.setDeviceToken(device_token);
            }
        });
        init();
        click_events();
    }

    private void init() {
        btn_signin = findViewById(R.id.btn_signin);
        img_logo = findViewById(R.id.img_logo);
        img_back = findViewById(R.id.img_back);
        edt_mobile = findViewById(R.id.edt_mobile);
        edt_password = findViewById(R.id.edt_password);
        txt_welcome = findViewById(R.id.txt_welcome);
        txt_plz_login = findViewById(R.id.txt_plz_login);
        txt_sign_up = findViewById(R.id.txt_sign_up);

        btn_signin.setTypeface(TypeFactory.getInstance(SignInActivity.this).PoppinsBold());
    }

    private void click_events() {
        txt_sign_up.setOnClickListener(view -> {
            Intent intent = new Intent(SignInActivity.this, SignUpActivity.class);
            startActivity(intent);
        });
        btn_signin.setOnClickListener(view -> {
            if (Utility.isConnectingToInternet(SignInActivity.this)) {
                if (vaild_data()) {
                    signin_api();
                } else {
                    Toast.makeText(SignInActivity.this, "Please Connect Internet !", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private boolean vaild_data() {
        boolean isVailid = false;
        vMobile = edt_mobile.getText().toString().trim();
        vPassword = edt_password.getText().toString().trim();

        if (vMobile.isEmpty()) {
            isVailid = false;
            Toast.makeText(this, "Please Enter Vailid Email !", Toast.LENGTH_SHORT).show();
        } else if (vPassword.isEmpty()) {
            isVailid = false;
            Toast.makeText(this, "Please Enter Vailid Password !", Toast.LENGTH_SHORT).show();
        } else {
            isVailid = true;

        }
        return isVailid;
    }

    private void signin_api() {
        prgress.setVisibility(View.VISIBLE);
        final Call<USER_LOGIN_DTO> user_dtoCall = apiInterface.login_api(vMobile, vPassword, sessionManager.getDeviceToken());
        user_dtoCall.enqueue(new Callback<USER_LOGIN_DTO>() {
            @Override
            public void onResponse(@NotNull Call<USER_LOGIN_DTO> call, @NotNull Response<USER_LOGIN_DTO> response) {
                if (response.isSuccessful()) {
                    USER_LOGIN_DTO user_login_dto = response.body();
                    prgress.setVisibility(View.GONE);
                    if (user_login_dto == null) {
                        Toast.makeText(SignInActivity.this, "Unexpected response from server!", Toast.LENGTH_SHORT).show();
                        return;
                    }
                    if (user_login_dto.getResponse()){
                        user_login_data_dtos.clear();
                        user_login_data_dtos.addAll(user_login_dto.getData());
                        if (user_login_data_dtos.size() > 0) {
                            USER_LOGIN_DATA_DTO user_data_deto = user_login_data_dtos.get(0);
                            sessionManager.set_Agnet_id(user_data_deto.getAgentId());
                            System.out.println("sdsd...." + user_data_deto);
                        }
                        if (user_login_dto.getStatus() == 1) {
                            sessionManager.login();
                            Intent intent = new Intent(SignInActivity.this, MainActivity.class);
                            startActivity(intent);
                            finish();
                        } else {
                            Toast.makeText(SignInActivity.this, "Wrong Credentials...", Toast.LENGTH_SHORT).show();
                        }
                    }else {
                        Toast.makeText(SignInActivity.this, user_login_dto.getMessage(), Toast.LENGTH_SHORT).show();
                    }

                }
            }

            @Override
            public void onFailure(@NotNull Call<USER_LOGIN_DTO> call, @NotNull Throwable t) {
                Log.e("onFailure", " " + t.getMessage());
                prgress.setVisibility(View.GONE);
            }
        });
    }

}
