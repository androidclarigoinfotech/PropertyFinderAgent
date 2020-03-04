package com.clarigo.propertyfinderagent.Fragment;


import android.Manifest;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;

import com.bumptech.glide.Glide;
import com.clarigo.propertyfinderagent.Activity.MainActivity;
import com.clarigo.propertyfinderagent.Adapter.Vehical_adapter;
import com.clarigo.propertyfinderagent.R;
import com.clarigo.propertyfinderagent.Utils.TypeFactory;
import com.clarigo.propertyfinderagent.app.SessionManager;
import com.clarigo.propertyfinderagent.dtos.SaveagentvehicleModel;
import com.clarigo.propertyfinderagent.imageUtils.Croperino;
import com.clarigo.propertyfinderagent.imageUtils.CroperinoConfig;
import com.clarigo.propertyfinderagent.imageUtils.CroperinoFileUtil;
import com.clarigo.propertyfinderagent.retrofitHelper.APIClient;
import com.clarigo.propertyfinderagent.retrofitHelper.APIInterface;

import org.jetbrains.annotations.NotNull;

import java.io.File;
import java.util.Objects;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * A simple {@link Fragment} subclass.
 */
public class My_Car_Fragment extends Fragment implements View.OnClickListener {
    private APIInterface apiInterface;
    private LinearLayout lay_edit_form;
    private ListView vehical_list;
    private View rootview;
    private SessionManager sessionManager;
    private TextView tv_vehical_information, tv_vehical_Documents, btn_submit, btn_add_vehical;
    private Vehical_adapter vehical_adapter;
    private EditText edt_car_make, edt_car_model, edt_seaters;
    private ImageView img_car_registration, upload_car_insurence, upload_other_documents;
    private TextView tv_other_doc, tv_car_insu, tv_car_reg;
    private String carRegi = "", carInsurence = "", otherDocument = "";
    private int requastcode;
    ProgressBar progress;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        rootview = inflater.inflate(R.layout.fragment_my_car, container, false);
        apiInterface = APIClient.getClient().create(APIInterface.class);
        new CroperinoConfig("IMG_" + System.currentTimeMillis() + ".jpg", "/property/Pictures", "/sdcard/property/Pictures");
        CroperinoFileUtil.setupDirectory(getActivity());
        sessionManager = new SessionManager(getActivity());


        init();
        img_car_registration.setOnClickListener(v -> {
            requastcode = 4;
            if (CroperinoFileUtil.verifyStoragePermissions(getActivity())) {
                prepareChooser();
            }
        });
        upload_car_insurence.setOnClickListener(v -> {
            requastcode = 5;
            if (CroperinoFileUtil.verifyStoragePermissions(getActivity())) {
                prepareChooser();
            }
        });
        upload_other_documents.setOnClickListener(v -> {
            requastcode = 6;
            if (CroperinoFileUtil.verifyStoragePermissions(getActivity())) {
                prepareChooser();
            }
        });

        LocalBroadcastManager.getInstance(Objects.requireNonNull(getActivity())).registerReceiver(
                new BroadcastReceiver() {
                    @Override
                    public void onReceive(Context context, Intent intent) {
                        if (intent != null) {
                            String mFileTemp = intent.getStringExtra(MainActivity.IMAGE_PATH);
                            String type = intent.getStringExtra("type");
                            if (mFileTemp != null && type != null) {
                                File file = new File(mFileTemp);
                                if (type.equals("Car_registration")) {
                                    carRegi = mFileTemp;
                                    tv_car_reg.setText(file.getName());
                                } else if (type.equals("Car_insurence")) {
                                    carInsurence = mFileTemp;
                                    tv_car_insu.setText(file.getName());
                                } else if (type.equals("other_document")) {
                                    otherDocument = mFileTemp;
                                    tv_other_doc.setText(file.getName());
                                }
                            }
                        }
                    }
                }, new IntentFilter(MainActivity.ACTION_IMAGE_BROADCAST_DOC)
        );

        return rootview;
    }

    private void prepareChooser() {
        CroperinoConfig.REQUEST_CROP_PHOTO = requastcode;
        Croperino.prepareChooser(getActivity(), "Select image from", ContextCompat.getColor(Objects.requireNonNull(getActivity()), android.R.color.background_dark));
    }

    private void prepareCamera() {
        Croperino.prepareCamera(getActivity());
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == CroperinoFileUtil.REQUEST_CAMERA) {
            for (int i = 0; i < permissions.length; i++) {
                String permission = permissions[i];
                int grantResult = grantResults[i];

                if (permission.equals(Manifest.permission.CAMERA)) {
                    if (grantResult == PackageManager.PERMISSION_GRANTED) {
                        prepareCamera();
                    }
                }
            }
        } else if (requestCode == CroperinoFileUtil.REQUEST_EXTERNAL_STORAGE) {
            boolean wasReadGranted = false;
            boolean wasWriteGranted = false;

            for (int i = 0; i < permissions.length; i++) {
                String permission = permissions[i];
                int grantResult = grantResults[i];

                if (permission.equals(Manifest.permission.READ_EXTERNAL_STORAGE)) {
                    if (grantResult == PackageManager.PERMISSION_GRANTED) {
                        wasReadGranted = true;
                    }
                }
                if (permission.equals(Manifest.permission.WRITE_EXTERNAL_STORAGE)) {
                    if (grantResult == PackageManager.PERMISSION_GRANTED) {
                        wasWriteGranted = true;
                    }
                }
            }

            if (wasReadGranted && wasWriteGranted) {
                prepareChooser();
            }
        }
    }

    private void init() {
        edt_car_make = rootview.findViewById(R.id.edt_car_make);
        edt_car_model = rootview.findViewById(R.id.edt_car_model);
        edt_seaters = rootview.findViewById(R.id.edt_seaters);
        img_car_registration = rootview.findViewById(R.id.img_car_registration);
        upload_car_insurence = rootview.findViewById(R.id.upload_car_insurence);
        upload_other_documents = rootview.findViewById(R.id.upload_other_documents);
        tv_other_doc = rootview.findViewById(R.id.tv_other_doc);
        tv_car_insu = rootview.findViewById(R.id.tv_car_insu);
        tv_car_reg = rootview.findViewById(R.id.tv_car_reg);
        progress = rootview.findViewById(R.id.progress);

        tv_vehical_information = rootview.findViewById(R.id.tv_vehical_information);
        tv_vehical_Documents = rootview.findViewById(R.id.tv_vehical_Documents);
        btn_submit = rootview.findViewById(R.id.btn_submit);
        lay_edit_form = rootview.findViewById(R.id.lay_edit_form);
        vehical_list = rootview.findViewById(R.id.vehical_list);
        btn_add_vehical = rootview.findViewById(R.id.btn_add_vehical);

        tv_vehical_information.setTypeface(TypeFactory.getInstance(getActivity()).PoppinsSemiBold());
        tv_vehical_Documents.setTypeface(TypeFactory.getInstance(getActivity()).PoppinsSemiBold());
        btn_submit.setTypeface(TypeFactory.getInstance(getActivity()).PoppinsSemiBold());
        btn_add_vehical.setTypeface(TypeFactory.getInstance(getActivity()).PoppinsSemiBold());

        btn_add_vehical.setOnClickListener(this);
        btn_submit.setOnClickListener(this);

        vehical_adapter = new Vehical_adapter(getActivity());
        vehical_list.setAdapter(vehical_adapter);
        vehical_adapter.notifyDataSetChanged();
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_add_vehical:
                lay_edit_form.setVisibility(View.VISIBLE);
                btn_add_vehical.setVisibility(View.GONE);
                break;
            case R.id.btn_submit:
                if (validation())
                    submitVehicleData();
                break;
        }
    }

    private boolean validation() {
        if (edt_car_make.getText().toString().trim().isEmpty()) {
            edt_car_make.setError("Required");
            return false;
        }
        if (edt_car_model.getText().toString().trim().isEmpty()) {
            edt_car_model.setError("Required");
            return false;
        }
        if (edt_seaters.getText().toString().trim().isEmpty()) {
            edt_seaters.setError("Required");
            return false;
        }

        if (carRegi.isEmpty()) {
            Toast.makeText(getActivity(), "select Vehicle Registration !", Toast.LENGTH_SHORT).show();
            return false;
        }
        if (carInsurence.isEmpty()) {
            Toast.makeText(getActivity(), "select Vehicle insurence", Toast.LENGTH_SHORT).show();
            return false;
        }
        if (otherDocument.isEmpty()) {
            Toast.makeText(getActivity(), "select other document", Toast.LENGTH_SHORT).show();
            return false;
        }

        return true;
    }

    private void submitVehicleData() {
        progress.setVisibility(View.VISIBLE);
        MultipartBody.Part car_regPart;
        File car = new File(carRegi);
        if (!car.exists()) {
            car_regPart = MultipartBody.Part.createFormData("car_reg", "");
        } else {
            RequestBody requestBody = RequestBody.create(MediaType.parse("image/jpeg"), car);
            car_regPart = MultipartBody.Part.createFormData("car_reg", car.getName(), requestBody);
        }
        MultipartBody.Part other_docPart;
        File other = new File(otherDocument);
        if (!other.exists()) {
            other_docPart = MultipartBody.Part.createFormData("other_doc", "");
        } else {
            RequestBody requestBody = RequestBody.create(MediaType.parse("image/jpeg"), other);
            other_docPart = MultipartBody.Part.createFormData("other_doc", other.getName(), requestBody);
        }

        MultipartBody.Part vehicle_insPart;
        File vehicle = new File(carInsurence);
        if (!vehicle.exists()) {
            vehicle_insPart = MultipartBody.Part.createFormData("vehicle_ins", "");
        } else {
            RequestBody requestBody = RequestBody.create(MediaType.parse("image/jpeg"), vehicle);
            vehicle_insPart = MultipartBody.Part.createFormData("vehicle_ins", vehicle.getName(), requestBody);
        }

        MultipartBody.Part agent_idPart = MultipartBody.Part.createFormData("agent_id", sessionManager.get_Agent_id());
        MultipartBody.Part vehicle_makePart = MultipartBody.Part.createFormData("vehicle_make", edt_car_make.getText().toString());
        MultipartBody.Part car_modelPart = MultipartBody.Part.createFormData("car_model", edt_car_model.getText().toString());
        MultipartBody.Part seatersPart = MultipartBody.Part.createFormData("seaters", edt_seaters.getText().toString());

        apiInterface.saveAgentVehicle(agent_idPart, vehicle_makePart, car_modelPart, seatersPart, car_regPart, other_docPart, vehicle_insPart).enqueue(new Callback<SaveagentvehicleModel>() {
            @Override
            public void onResponse(@NotNull Call<SaveagentvehicleModel> call, @NotNull Response<SaveagentvehicleModel> response) {
                SaveagentvehicleModel saveagentvehicleModel = response.body();
                progress.setVisibility(View.GONE);
                if (saveagentvehicleModel == null)
                    return;
                if (saveagentvehicleModel.getResponse()) {
                    lay_edit_form.setVisibility(View.GONE);
                    btn_add_vehical.setVisibility(View.GONE);
                    vehical_list.setVisibility(View.VISIBLE);
                    Toast.makeText(getActivity(), saveagentvehicleModel.getMessage(), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(@NotNull Call<SaveagentvehicleModel> call, @NotNull Throwable t) {
                progress.setVisibility(View.GONE);
            }
        });
    }
}
