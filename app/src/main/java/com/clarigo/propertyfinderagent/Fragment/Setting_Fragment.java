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
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;
import androidx.viewpager.widget.ViewPager;

import com.bumptech.glide.Glide;
import com.clarigo.propertyfinderagent.Activity.MainActivity;
import com.clarigo.propertyfinderagent.Adapter.CustomFragmentPagerAdapter;
import com.clarigo.propertyfinderagent.R;
import com.clarigo.propertyfinderagent.Utils.TypeFactory;
import com.clarigo.propertyfinderagent.app.SessionManager;
import com.clarigo.propertyfinderagent.dtos.UpdateProfileModel;
import com.clarigo.propertyfinderagent.imageUtils.Croperino;
import com.clarigo.propertyfinderagent.imageUtils.CroperinoConfig;
import com.clarigo.propertyfinderagent.imageUtils.CroperinoFileUtil;
import com.clarigo.propertyfinderagent.retrofitHelper.APIClient;
import com.clarigo.propertyfinderagent.retrofitHelper.APIInterface;
import com.google.android.material.tabs.TabLayout;
import com.squareup.picasso.Picasso;

import org.jetbrains.annotations.NotNull;

import java.io.File;
import java.util.Objects;

import de.hdodenhof.circleimageview.CircleImageView;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Setting_Fragment extends Fragment {

    private TabLayout tabLayout;
    private ViewPager viewPager;
    private CustomFragmentPagerAdapter customFragmentPagerAdapter;
    private TextView tv_acceptence, tv_rating, tv_total_trips;
    private EditText edt_name;
    private CircleImageView profilePic;
    private ImageView iv_camera;
    private SessionManager sessionManager;
    private APIInterface apiInterface;
    private String mFileTemp = "";
    private ProgressBar progress;

    @Nullable
    @Override

    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootview = inflater.inflate(R.layout.setting_fragment, container, false);
        Objects.requireNonNull(getActivity()).getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_VISIBLE | WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);
        apiInterface = APIClient.getClient().create(APIInterface.class);
        new CroperinoConfig("IMG_" + System.currentTimeMillis() + ".jpg", "/property/Pictures", "/sdcard/property/Pictures");
        CroperinoFileUtil.setupDirectory(getActivity());

        MainActivity mainActivity = (MainActivity) getActivity();
        if (mainActivity != null) {
            mainActivity.lay_header1.setVisibility(View.GONE);
            mainActivity.tv_header_title.setVisibility(View.VISIBLE);
        }
        ini(rootview);

        customFragmentPagerAdapter = new CustomFragmentPagerAdapter(getActivity().getSupportFragmentManager());
        customFragmentPagerAdapter.addFragment(new ReviewFragment(), getResources().getString(R.string.reviews));
        customFragmentPagerAdapter.addFragment(new My_Car_Fragment(), getResources().getString(R.string.my_car));
        customFragmentPagerAdapter.addFragment(new DocumentFragment(), getResources().getString(R.string.documents));
        viewPager.setAdapter(customFragmentPagerAdapter);
        tabLayout.setupWithViewPager(viewPager);


        iv_camera.setOnClickListener(v -> {
            if (CroperinoFileUtil.verifyStoragePermissions(getActivity())) {

                prepareChooser();
            }
        });
        LocalBroadcastManager.getInstance(Objects.requireNonNull(getActivity())).registerReceiver(
                new BroadcastReceiver() {
                    @Override
                    public void onReceive(Context context, Intent intent) {
                        if (intent != null) {
                            mFileTemp = intent.getStringExtra(MainActivity.IMAGE_PATH);
                            if (mFileTemp != null) {
                                try {
                                    Glide.with(getActivity()).load(mFileTemp).asBitmap().into(profilePic);
                                } catch (Exception ignored) {
                                }
                                updateProfile();
                            }
                        }
                    }
                }, new IntentFilter(MainActivity.ACTION_IMAGE_BROADCAST)
        );

        return rootview;
    }

    private void prepareChooser() {
        CroperinoConfig.REQUEST_CROP_PHOTO=3;
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

    @Override
    public void onResume() {
        super.onResume();

    }

    private void updateProfile() {
        progress.setVisibility(View.VISIBLE);
        MultipartBody.Part imagePartTwo;
        if (mFileTemp == null || mFileTemp.equals("")) {
            imagePartTwo = MultipartBody.Part.createFormData("image", "");
        } else {
            File file_two = new File(mFileTemp);
            RequestBody requestBody = RequestBody.create(MediaType.parse("image/jpeg"), file_two);
            imagePartTwo = MultipartBody.Part.createFormData("image", file_two.getName(), requestBody);
        }
        MultipartBody.Part agent_id = MultipartBody.Part.createFormData("agent_id", sessionManager.get_Agent_id());

        apiInterface.updateProfile(agent_id, imagePartTwo).enqueue(new Callback<UpdateProfileModel>() {
            @Override
            public void onResponse(@NotNull Call<UpdateProfileModel> call, @NotNull Response<UpdateProfileModel> response) {
                UpdateProfileModel updateProfileModel = response.body();
                progress.setVisibility(View.GONE);
                if (updateProfileModel == null) {
                    Toast.makeText(getActivity(), "Unexpected response from server !", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (updateProfileModel.getResponse()) {
                    Toast.makeText(getActivity(), updateProfileModel.getMessage(), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(@NotNull Call<UpdateProfileModel> call, @NotNull Throwable t) {
                progress.setVisibility(View.GONE);
            }
        });
    }

    private void ini(View rootview) {
        sessionManager = new SessionManager(getActivity());
        progress = rootview.findViewById(R.id.progress);
        tabLayout = rootview.findViewById(R.id.tabLayout);
        viewPager = rootview.findViewById(R.id.viewPager);
        tv_acceptence = rootview.findViewById(R.id.tv_acceptence);
        tv_rating = rootview.findViewById(R.id.tv_rating);
        tv_total_trips = rootview.findViewById(R.id.tv_total_trips);
        edt_name = rootview.findViewById(R.id.edt_name);
        profilePic = rootview.findViewById(R.id.profilePic);
        iv_camera = rootview.findViewById(R.id.iv_camera);

        tv_acceptence.setTypeface(TypeFactory.getInstance(getActivity()).PoppinsBold());
        tv_rating.setTypeface(TypeFactory.getInstance(getActivity()).PoppinsBold());
        tv_total_trips.setTypeface(TypeFactory.getInstance(getActivity()).PoppinsBold());
        edt_name.setTypeface(TypeFactory.getInstance(getActivity()).PoppinsBold());

        try {
            if (sessionManager.getUser_details().getProfilepic() != null) {
                Picasso.with(getActivity()).load(sessionManager.getUser_details().getProfilepic()).error(R.drawable.profil).into(profilePic);
                edt_name.setText(sessionManager.getUser_details().getAgentName());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}
