package com.clarigo.propertyfinderagent.Activity;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.location.LocationManager;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;

import com.clarigo.propertyfinderagent.Fragment.HomeFragment;
import com.clarigo.propertyfinderagent.Fragment.Ride_History_Fragment;
import com.clarigo.propertyfinderagent.Fragment.Setting_Fragment;
import com.clarigo.propertyfinderagent.Navigation_Drawer.FragmentDrawer;
import com.clarigo.propertyfinderagent.Navigation_Drawer.LocaleHelper;
import com.clarigo.propertyfinderagent.R;
import com.clarigo.propertyfinderagent.Utils.TypeFactory;
import com.clarigo.propertyfinderagent.app.MyApplication;
import com.clarigo.propertyfinderagent.app.SessionManager;
import com.clarigo.propertyfinderagent.dtos.USER_DETAILS_DATA_DTO;
import com.clarigo.propertyfinderagent.dtos.USER_DETAILS_DTO;
import com.clarigo.propertyfinderagent.imageUtils.Croperino;
import com.clarigo.propertyfinderagent.imageUtils.CroperinoConfig;
import com.clarigo.propertyfinderagent.imageUtils.CroperinoFileUtil;
import com.clarigo.propertyfinderagent.retrofitHelper.APIClient;
import com.clarigo.propertyfinderagent.retrofitHelper.APIInterface;
import com.clarigo.propertyfinderagent.services.Request_Retrive_Service;
import com.shashank.sony.fancydialoglib.FancyAlertDialog;
import com.shashank.sony.fancydialoglib.FancyAlertDialogListener;
import com.shashank.sony.fancydialoglib.Icon;
import com.squareup.picasso.Picasso;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class MainActivity extends AppCompatActivity implements FragmentDrawer.FragmentDrawerListener {

    private FragmentDrawer drawerFragment;
    private DrawerLayout drawerLayout;
    Fragment fragment = null;

    private ImageView drawer_img1;
    public RelativeLayout lay_header1;
    public TextView tv_header_title;
    private TextView txt_going, tv_user_name, tv_goodmorning;
    public LinearLayout heade_layout;
    APIInterface apiInterface;
    private SessionManager sessionManager;
    ArrayList<USER_DETAILS_DATA_DTO> user_details_data_dtos = new ArrayList<>();
    private ImageView img_agent;
    private String vNotificationType = "0", vMessage = "", vREQ_ID = "", vTitle = "", vNoti_Type_String = "", vAgent_id = "";
    public static final String ACTION_IMAGE_BROADCAST = MainActivity.class.getName() + "sendImage";
    public static final String ACTION_IMAGE_BROADCAST_DOC = MainActivity.class.getName() + "DocsendImage";
    public static final String IMAGE_PATH = "Image_Path";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        apiInterface = APIClient.getClient().create(APIInterface.class);
        sessionManager = new SessionManager(this);
        //mToolbar = findViewById(R.id.toolbar);
        //  setSupportActionBar(mToolbar);
//        getSupportActionBar().hide();
        statusCheck();


        drawer_img1 = findViewById(R.id.drawer_img1);
        txt_going = findViewById(R.id.txt_going);
        tv_user_name = findViewById(R.id.tv_name);
        tv_goodmorning = findViewById(R.id.tv_goodmorning);
        img_agent = findViewById(R.id.img_agent);
        heade_layout = findViewById(R.id.heade_layout);
        drawer_img1 = findViewById(R.id.drawer_img1);
        lay_header1 = findViewById(R.id.lay_header1);
        tv_header_title = findViewById(R.id.header_title);
        txt_going.setTypeface(TypeFactory.getInstance(this).PoppinsBold());
        tv_user_name.setTypeface(TypeFactory.getInstance(this).PoppinsBold());
        tv_header_title.setTypeface(TypeFactory.getInstance(this).PoppinsSemiBold());
        drawer_img1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    InputMethodManager inputManager = (InputMethodManager) MainActivity.this
                            .getSystemService(Context.INPUT_METHOD_SERVICE);
                    inputManager.hideSoftInputFromWindow(
                            MainActivity.this.getCurrentFocus()
                                    .getWindowToken(),
                            InputMethodManager.HIDE_NOT_ALWAYS);
                } catch (Exception e) {

                }
                if (drawerLayout.isDrawerVisible(GravityCompat.START)) {
                    drawerLayout.closeDrawer(GravityCompat.START);
                } else {
                    drawerLayout.openDrawer(GravityCompat.START);
                }
            }
        });
        drawerLayout = findViewById(R.id.drawer_layout);
        drawerFragment = (FragmentDrawer) getSupportFragmentManager().findFragmentById(R.id.fragment_navigation_drawer);
//        drawerFragment.setUp(R.id.fragment_navigation_drawer, (DrawerLayout) findViewById(R.id.drawer_layout), mToolbar);
        drawerFragment.setUp(R.id.fragment_navigation_drawer, (DrawerLayout) findViewById(R.id.drawer_layout));
        drawerFragment.setDrawerListener(this);
        drawerLayout.setScrimColor(getResources().getColor(R.color.transparent));
        displayView(0);


        try {
            vNotificationType = getIntent().getStringExtra("NOTIFICATION_TYPE");
            vAgent_id = getIntent().getStringExtra("AGENT_ID");
        } catch (Exception e) {

        }

        if (vNotificationType == null) {
            vNotificationType = "";
        }
        if (vAgent_id == null) {
            vAgent_id = "";
        }

        if (vNotificationType.trim().equalsIgnoreCase("1")) {
            try {
                if (!vAgent_id.equalsIgnoreCase("")) {
                    sessionManager.set_Agnet_id(vAgent_id);

                    Request_Retrive_Service.request_retrive_service.startService(new Intent(MainActivity.this, Request_Retrive_Service.class));
                    displayView(0);
                }
            } catch (Exception e) {

            }
        }

        if (vNotificationType.trim().equalsIgnoreCase("2")) {
            try {
                vMessage = getIntent().getStringExtra("MSG_BODY");
            } catch (Exception e) {
            }
            try {
                vREQ_ID = getIntent().getStringExtra("REQ_ID");
            } catch (Exception e) {
            }
            try {
                showCancelRequestDialog(vNotificationType, vMessage, vREQ_ID);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        Log.e("requestCode ", "requestCode " + requestCode);
        switch (requestCode) {
            case CroperinoConfig.REQUEST_TAKE_PHOTO:
                if (resultCode == Activity.RESULT_OK) {
                    Croperino.runCropImage(CroperinoFileUtil.getTempFile(), this, true, 0, 0, R.color.gray, R.color.gray_variant);
                }
                break;
            case CroperinoConfig.REQUEST_PICK_FILE:
                if (resultCode == Activity.RESULT_OK) {
                    CroperinoFileUtil.newGalleryFile(data, this);
                    Croperino.runCropImage(CroperinoFileUtil.getTempFile(), this, true, 0, 0, R.color.gray, R.color.gray_variant);
                }
                break;
            case 3:
                if (resultCode == Activity.RESULT_OK) {
                    Uri i = Uri.fromFile(CroperinoFileUtil.getTempFile());
                    String mFileTemp = CroperinoFileUtil.getPath(this, i);
                    Intent intent = new Intent(ACTION_IMAGE_BROADCAST);
                    intent.putExtra(IMAGE_PATH, mFileTemp);
                    LocalBroadcastManager.getInstance(this).sendBroadcast(intent);
//                    profile_img.setImageURI(i);
                }
                break;
            case 4:
                //carRegistraion for add vehicle
                if (resultCode == Activity.RESULT_OK) {
                    Uri i = Uri.fromFile(CroperinoFileUtil.getTempFile());
                    String mFileTemp = CroperinoFileUtil.getPath(this, i);
                    Intent intent = new Intent(ACTION_IMAGE_BROADCAST_DOC);
                    intent.putExtra(IMAGE_PATH, mFileTemp);
                    intent.putExtra("type", "Car_registration");
                    LocalBroadcastManager.getInstance(this).sendBroadcast(intent);
                }
                break;
            case 5:
                // car insurence for add vehicle
                if (resultCode == Activity.RESULT_OK) {
                    Uri i = Uri.fromFile(CroperinoFileUtil.getTempFile());
                    String mFileTemp = CroperinoFileUtil.getPath(this, i);
                    Intent intent = new Intent(ACTION_IMAGE_BROADCAST_DOC);
                    intent.putExtra(IMAGE_PATH, mFileTemp);
                    intent.putExtra("type", "Car_insurence");
                    LocalBroadcastManager.getInstance(this).sendBroadcast(intent);
                }
                break;
            case 6:
                //other document for add vehicle
                if (resultCode == Activity.RESULT_OK) {
                    Uri i = Uri.fromFile(CroperinoFileUtil.getTempFile());
                    String mFileTemp = CroperinoFileUtil.getPath(this, i);
                    Intent intent = new Intent(ACTION_IMAGE_BROADCAST_DOC);
                    intent.putExtra(IMAGE_PATH, mFileTemp);
                    intent.putExtra("type", "other_document");
                    LocalBroadcastManager.getInstance(this).sendBroadcast(intent);
                }
                break;
            default:
                break;
        }
    }

    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(LocaleHelper.onAttach(base));
    }

    @Override
    public void onDrawerItemSelected(View view, int position) {
        displayView(position);
    }

    public void displayView(int position) {

        switch (position) {

            case 0:
                User_Details_API();
                fragment = new HomeFragment();
                tv_header_title.setVisibility(View.GONE);
                lay_header1.setVisibility(View.VISIBLE);

                break;

            case 1:
                fragment = new Ride_History_Fragment();
                lay_header1.setVisibility(View.GONE);
                break;
            case 2:
                fragment = new Setting_Fragment();
                lay_header1.setVisibility(View.GONE);
                tv_header_title.setVisibility(View.VISIBLE);
                tv_header_title.setText(getString(R.string.my_setting));
                break;


            default:
                break;
        }
        if (fragment != null) {
            FragmentManager fragmentManager = getSupportFragmentManager();
            int backCount = fragmentManager.getBackStackEntryCount();
            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction().addToBackStack(null);
            if (backCount == 2) {
                fragmentManager.popBackStack();
            }
            fragmentTransaction.replace(R.id.container_body, fragment);
            fragmentTransaction.commit();
        }
    }

    @Override
    public void onBackPressed() {
        FragmentManager fragmentManager = getSupportFragmentManager();
        int backCount = fragmentManager.getBackStackEntryCount();
        if (backCount > 1) {
            super.onBackPressed();
        } else {
            finish();
        }
    }

    public void User_Details_API() {
        final Call<USER_DETAILS_DTO> user_details_dtoCall = apiInterface.userDetails_api(sessionManager.get_Agent_id());
        user_details_dtoCall.enqueue(new Callback<USER_DETAILS_DTO>() {
            @Override
            public void onResponse(@NotNull Call<USER_DETAILS_DTO> call, @NotNull Response<USER_DETAILS_DTO> response) {
                USER_DETAILS_DTO user_details_dto = response.body();
                if (user_details_dto == null) {
                    Toast.makeText(MainActivity.this, "unexpected response from server!", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (user_details_dto.getStatus() == 1) {
                    try {
                        if (user_details_data_dtos.size() > 0) {
                            user_details_data_dtos.clear();
                        }
                        user_details_data_dtos.addAll(user_details_dto.getData());
                        USER_DETAILS_DATA_DTO user_details_data_dto = user_details_data_dtos.get(0);
                        if (user_details_data_dto != null) {
                            sessionManager.setUserDetail(user_details_data_dto);
                            if (!sessionManager.getDeviceToken().equalsIgnoreCase(sessionManager.getUser_details().getDeviceToken())) {
                                sessionManager.Logout();
                                finish();
                            }
                            tv_user_name.setText(user_details_data_dto.getAgentName());
                            System.out.println("......." + sessionManager.getUser_details().getOnlineStatus());
                            try {
                                if (sessionManager.getUser_details().getProfilepic() != null) {
                                    Picasso.with(MainActivity.this).load(sessionManager.getUser_details().getProfilepic()).error(R.drawable.profil).resize(300, 300).into(img_agent);
                                }

                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                } else {
                    Toast.makeText(MainActivity.this, user_details_dto.getMessage(), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(@NotNull Call<USER_DETAILS_DTO> call, @NotNull Throwable t) {
                Log.e("onFaliure..", "onFailure: ", t);
            }
        });
    }

    public void statusCheck() {
        final LocationManager manager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        if (!manager.isProviderEnabled(LocationManager.GPS_PROVIDER) && !manager.isProviderEnabled(LocationManager.NETWORK_PROVIDER)) {
            buildAlertMessageNoGps();
            Log.e("show dialog", "show dialog");
        }
    }

    private void buildAlertMessageNoGps() {
        final AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage("Your GPS seems to be disabled, do you want to enable it?")
                .setCancelable(false)
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    public void onClick(final DialogInterface dialog, final int id) {
                        startActivity(new Intent(android.provider.Settings.ACTION_LOCATION_SOURCE_SETTINGS));
                    }
                })
                .setNegativeButton("No", new DialogInterface.OnClickListener() {
                    public void onClick(final DialogInterface dialog, final int id) {
                        dialog.cancel();
                    }
                });
        final AlertDialog alert = builder.create();
        alert.show();
    }

    public void showCancelRequestDialog(final String vNoti, final String mes_body, final String vReqID) {
        try {
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    new FancyAlertDialog.Builder(MainActivity.this)
                            .setTitle(getString(R.string.app_name))
                            .setBackgroundColor(Color.parseColor("#3DB24B"))  //Don't pass R.color.colorvalue
                            .setMessage(mes_body)
                            .setPositiveBtnBackground(Color.parseColor("#3DB24B"))  //Don't pass R.color.colorvalue
                            .setPositiveBtnText(getString(R.string.ok))
                            .setNegativeBtnBackground(Color.parseColor("#FCB3B0B0"))  //Don't pass R.color.colorvalue
                            .setAnimation(com.shashank.sony.fancydialoglib.Animation.POP)
                            .hideNegativeButton(true)
                            .isCancellable(false)
                            .setIcon(R.drawable.noti_dialoge, Icon.Visible)
                            .OnPositiveClicked(new FancyAlertDialogListener() {
                                @Override
                                public void OnClick() {
                                    if (vNoti.equalsIgnoreCase("2")) {
                                        displayView(0);
                                        //getOrderHistoryDetail(vReqID);
                                    } else if (vNoti.equalsIgnoreCase("3")) {
                                        displayView(0);
                                    }
                                }
                            })
                            .build();
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
            // TODO: handle exception
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (Request_Retrive_Service.request_retrive_service != null) {
            Request_Retrive_Service.request_retrive_service.stopSelf();
            MainActivity.this.stopService(new Intent(MainActivity.this, Request_Retrive_Service.class));
            Toast.makeText(MainActivity.this, "service stop agent", Toast.LENGTH_SHORT).show();

            MyApplication.activity_distroy();
            Log.e("dfsfsfff", "on distroy");
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        MyApplication.activityResumed();
        Log.e("dfsfsfff", "on resume");
    }

    @Override
    protected void onPause() {
        super.onPause();
        MyApplication.activityPaused();
        Log.e("dfsfsfff", "on Pause");
    }

    @Override
    protected void onStop() {
        super.onStop();
        MyApplication.activityStop();
        Log.e("dfsfsfff", "on stop");
    }

}
