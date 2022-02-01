package com.clarigo.propertyfinderagent.Fragment;


import android.Manifest;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.BitmapShader;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Point;
import android.graphics.RectF;
import android.graphics.Shader;
import android.graphics.drawable.Drawable;
import android.location.Geocoder;
import android.location.Location;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.os.Looper;
import android.os.SystemClock;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Interpolator;
import android.view.animation.LinearInterpolator;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.ProgressBar;
import android.widget.RatingBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;

import com.arsy.maps_library.MapRipple;
import com.clarigo.propertyfinderagent.Activity.MainActivity;
import com.clarigo.propertyfinderagent.Adapter.SliderAdapter;
import com.clarigo.propertyfinderagent.R;
import com.clarigo.propertyfinderagent.Utils.TypeFactory;
import com.clarigo.propertyfinderagent.Utils.Utility;
import com.clarigo.propertyfinderagent.app.SessionManager;
import com.clarigo.propertyfinderagent.dtos.AGENT_STATUS_DTO;
import com.clarigo.propertyfinderagent.dtos.CANCLE_REQUEST_DTO;
import com.clarigo.propertyfinderagent.dtos.REFRESH_AGENT_STATUS_DTO;
import com.clarigo.propertyfinderagent.dtos.USER_DETAILS_DATA_DTO;
import com.clarigo.propertyfinderagent.dtos.propertyfinder.Address;
import com.clarigo.propertyfinderagent.dtos.propertyfinder.Geo;
import com.clarigo.propertyfinderagent.dtos.propertyfinder.Mls;
import com.clarigo.propertyfinderagent.dtos.propertyfinder.PROPERTY_MAIN_DTO;
import com.clarigo.propertyfinderagent.dtos.propertyfinder.Property;
import com.clarigo.propertyfinderagent.dtos.request_data.ACCEPT_REQUEST_DTO;
import com.clarigo.propertyfinderagent.dtos.request_data.PICKUPUSERDTO;
import com.clarigo.propertyfinderagent.dtos.request_data.REQUEST_DTO;
import com.clarigo.propertyfinderagent.map.HttpConnection;
import com.clarigo.propertyfinderagent.map.ParserCallBack;
import com.clarigo.propertyfinderagent.map.ParserTask;
import com.clarigo.propertyfinderagent.map.UpdateLocationService;
import com.clarigo.propertyfinderagent.retrofitHelper.APIClient;
import com.clarigo.propertyfinderagent.retrofitHelper.APIInterface;
import com.clarigo.propertyfinderagent.services.GET_User_Location_service;
import com.clarigo.propertyfinderagent.services.Request_Retrive_Service;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.Projection;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.Polyline;
import com.google.android.gms.maps.model.PolylineOptions;
import com.google.android.gms.maps.model.SquareCap;
import com.google.android.material.bottomsheet.BottomSheetBehavior;
import com.koushikdutta.ion.Ion;
import com.smarteist.autoimageslider.IndicatorAnimations;
import com.smarteist.autoimageslider.IndicatorView.draw.controller.DrawController;
import com.smarteist.autoimageslider.SliderAnimations;
import com.smarteist.autoimageslider.SliderView;
import com.squareup.picasso.Picasso;

import org.jetbrains.annotations.NotNull;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Objects;
import java.util.TimeZone;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;

import de.hdodenhof.circleimageview.CircleImageView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static android.content.Context.LAYOUT_INFLATER_SERVICE;
import static com.google.android.gms.maps.model.JointType.ROUND;

/**
 * A simple {@link Fragment} subclass.
 */
public class HomeFragment extends Fragment implements OnMapReadyCallback {

    private View view;
    private GoogleMap mGoogleMap;
    private ArrayList<Marker> markers = new ArrayList<>();
    private LocationRequest mLocationRequest;
    private FusedLocationProviderClient mFusedLocationClient;
    private Marker property_markers = null, ser_property_marker = null;
    private Marker user_marker = null;
    private Marker driverMarker = null;
    private LatLng propertyLatLng, sev_pro_latlng;
    private LatLng driverLatLng;
    private LatLng userLatLan;
    private SupportMapFragment mapFrag;
    private Polyline polyline;
    private List<Polyline> polylines;
    private float scale = 0f;
    private RelativeLayout layout_map;
    private static final int MY_PERMISSIONS_REQUEST_LOCATION = 99;
    private TextView statusOnlineTv, onlineTv, offlineTv;
    private PopupWindow popupWindow, popupWindow1;
    //setup
    private APIInterface apiInterfacse, apiInterface;
    private SessionManager sessionManager;
    //// //DTOs
    private PROPERTY_MAIN_DTO property_main_dto = null;
    private Address address_dto = null;
    private Property property_dto = null;
    private RelativeLayout online_container, offline_container;
    ArrayList<USER_DETAILS_DATA_DTO> user_details_data_dtos = new ArrayList<>();
    private String vStatus = "";
    //timezone date
    private TimeZone tz;
    private String timezone = "";
    private String date = "";
    private CountDownTimer yourCountDownTimer;
    private Long mTImeLeftMillies;
    private boolean is_cancle_req = false;

    ///bottom sheet request///
    private BottomSheetBehavior behavior_bs_on_off, behavior_bs_request, behavior_bs_pickup_arrived, behavior_bs_meetup_arrived;
    ///////bottom Sheets////
    private LinearLayout bottom_sheet_on_off, bottom_sheet_request, bottom_sheet_driver_pickup_arrived, bottom_sheet_lets_go, bottom_sheet_driver_meetup_complete_trip;
    //get bottom sheet request/////
    private TextView btn_cancel_request, tv_user_name, tv_method, btn_accept_request, tv_timer;
    private RelativeLayout lay_btn_accept_request;
    private TextView tv_total_distance, tv_total_time;
    private ImageView call_icon;
    /// bottom_sheet_driver_pickup_arrived///////
    private TextView btn_cancel, txt_driving_pickup, btn_arrived, tv_property_infermation, tv_navigate, tv_rating, tv_rider_waiting_timer;
    private ImageView img_usert;
    private ProgressBar prog_arrived, prog_property_loc, prog_drop_off;
    private String user_picker_address = "";

    /// bottom_sheet_driver_meetup_arrived///////
    private TextView txt_meetup, btn_cancel_meetup, btn_arrived_meetup, tv_rating_meetup, tv_use_name_meetup;
    private CircleImageView img_user_meetup;
    private LinearLayout lay_call_icon_meetup;
    ///////Request params////
    private String vUser_name = "", vUser_Mobile = "", vEmail = "", timmer_time = "", req_time = "", vEmr_Num = "", vLocation = "", vProperty_id = "", vPickup_Type = "", vReq_id = "", vUser_id = "", vUser_Image = "";
    private double vUser_lat, vUser_lng, vProperty_lat, vProperty_lng;
    private int mode_type;
    private MapRipple mapRipple;
    public static boolean is_Request = false;
    private static final int REQUEST_PHONE_CALL = 1;
    private ImageView img_focus_btn;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_home, container, false);

        apiInterface = APIClient.getClient().create(APIInterface.class);
        apiInterfacse = APIClient.getClient_with_header().create(APIInterface.class);
        sessionManager = new SessionManager(getActivity());
        MainActivity mainActivity = (MainActivity) getActivity();
        if (mainActivity != null) {
            mainActivity.heade_layout.setVisibility(View.VISIBLE);
            mainActivity.lay_header1.setVisibility(View.VISIBLE);
            mainActivity.tv_header_title.setVisibility(View.GONE);
        }
        mFusedLocationClient = LocationServices.getFusedLocationProviderClient(Objects.requireNonNull(getActivity()));
        mapFrag = (SupportMapFragment) getChildFragmentManager().findFragmentById(R.id.mMap);
        Objects.requireNonNull(mapFrag).getMapAsync(this);
        tz = TimeZone.getDefault();
        System.out.println("TimeZone   " + tz.getDisplayName(false, TimeZone.SHORT) + " Timezon id :: " + tz.getID());
        timezone = tz.getID();
        //   2019-12-05 22:49:17
        Date today = new Date();
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss a");
        date = format.format(today);
        System.out.println("Time......" + timezone + " " + date);
        polylines = new ArrayList<>();

        initView();

        if (Utility.isConnectingToInternet(getActivity())) {
            if (SessionManager.IS_REQUEST) {
                if (Request_Retrive_Service.request_retrive_service != null) {
                    getActivity().stopService(new Intent(getActivity(), Request_Retrive_Service.class));
                }
                get_Request_API();
            } else {
//                getActivity().startService(new Intent(getActivity(), Request_Retrive_Service.class));
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                    getActivity().startForegroundService(new Intent(getActivity(), Request_Retrive_Service.class));
                } else {
                    getActivity().startService(new Intent(getActivity(), Request_Retrive_Service.class));
                }
            }
            Agent_status_api();
        } else {
            Toast.makeText(getActivity(), getResources().getString(R.string.internet_error), Toast.LENGTH_SHORT).show();
        }

        LocalBroadcastManager.getInstance(getActivity()).registerReceiver(
                new BroadcastReceiver() {
                    @Override
                    public void onReceive(Context context, Intent intent) {
                        if (intent != null) {
                            double lati_user = intent.getDoubleExtra(GET_User_Location_service.EXTRA_LATITUDE_USER, 0);
                            double longi_user = intent.getDoubleExtra(GET_User_Location_service.EXTRA_LONGITUDE_USER, 0);
                            double lati_property = intent.getDoubleExtra(GET_User_Location_service.EXTRA_LATITUDE_PROPERTY, 0);
                            double longi_property = intent.getDoubleExtra(GET_User_Location_service.EXTRA_LONGITUDE_PROPERTY, 0);
                            String property_id = intent.getStringExtra(GET_User_Location_service.EXTRA_PROPERTY_ID);
                            int mode_type = intent.getIntExtra(GET_User_Location_service.EXTRA_MODE_TYPE, 0);
                            if (lati_user != 0 && longi_user != 0 && lati_property != 0 && longi_property != 0 && property_id != null && mode_type != 0) {
                                show_markers_locations(lati_user, longi_user, property_id, lati_property, longi_property, mode_type);
                            }
                        }
                    }
                }, new IntentFilter(GET_User_Location_service.ACTION_LOCATION_BROADCAST)
        );
        LocalBroadcastManager.getInstance(getActivity()).registerReceiver(
                new BroadcastReceiver() {
                    @Override
                    public void onReceive(Context context, Intent intent) {
                        if (intent != null) {
                            Log.e("request sync", "request sync ");
                            String syn = intent.getStringExtra(Request_Retrive_Service.SYNCREQ);
                            if (syn != null)
                                if (syn.equals("sync"))
                                    get_Request_API();
                        }
                    }
                }, new IntentFilter(Request_Retrive_Service.ACTION_LOCATION_BROADCAST)
        );
        return view;
    }

    private void initView() {
        img_focus_btn = view.findViewById(R.id.img_focus_btn);
        bottom_sheet_on_off = view.findViewById(R.id.bottom_sheet_on_off);
        bottom_sheet_request = view.findViewById(R.id.bottom_sheet_request);
        bottom_sheet_driver_pickup_arrived = view.findViewById(R.id.bottom_sheet_driver_pickup_arrived);
        bottom_sheet_lets_go = view.findViewById(R.id.bottom_sheet_lets_go);
        bottom_sheet_driver_meetup_complete_trip = view.findViewById(R.id.bottom_sheet_driver_meetup_complete_trip);

        behavior_bs_on_off = BottomSheetBehavior.from(bottom_sheet_on_off);
        behavior_bs_request = BottomSheetBehavior.from(bottom_sheet_request);
        behavior_bs_pickup_arrived = BottomSheetBehavior.from(bottom_sheet_driver_pickup_arrived);
        behavior_bs_meetup_arrived = BottomSheetBehavior.from(bottom_sheet_driver_meetup_complete_trip);

        //bottom sheet  on_off////
        get_View_botttom_sheet_on_off();


        //////////bottom sheet driver arrived  pickup find views//////
        btn_cancel = view.findViewById(R.id.btn_cancel);
        tv_user_name = view.findViewById(R.id.tv_user_name_arrived);
        txt_driving_pickup = view.findViewById(R.id.txt_driving_pickup);
        btn_arrived = view.findViewById(R.id.btn_arrived);
        tv_property_infermation = view.findViewById(R.id.tv_property_infermation);
        tv_navigate = view.findViewById(R.id.tv_navigate);
        tv_rider_waiting_timer = view.findViewById(R.id.tv_rider_waiting_timer);
        img_usert = view.findViewById(R.id.img_usert);
        call_icon = view.findViewById(R.id.call_icon);
        tv_rating = view.findViewById(R.id.tv_rating);
        prog_arrived = view.findViewById(R.id.prog_arrived);
        prog_property_loc = view.findViewById(R.id.prog_property_loc);
        prog_drop_off = view.findViewById(R.id.prog_drop_off);

        btn_cancel.setTypeface(TypeFactory.getInstance(getActivity()).PoppinsRegular());
        tv_user_name.setTypeface(TypeFactory.getInstance(getActivity()).PoppinsBold());
        txt_driving_pickup.setTypeface(TypeFactory.getInstance(getActivity()).PoppinsBold());
        btn_arrived.setTypeface(TypeFactory.getInstance(getActivity()).PoppinsBold());
        tv_property_infermation.setTypeface(TypeFactory.getInstance(getActivity()).PoppinsRegular());
        tv_navigate.setTypeface(TypeFactory.getInstance(getActivity()).PoppinsRegular());

        //////////bottom sheet driver meetup find views//////
        txt_meetup = view.findViewById(R.id.txt_meetup);
        btn_cancel_meetup = view.findViewById(R.id.btn_cancel_meetup);
        btn_arrived_meetup = view.findViewById(R.id.btn_arrived_meetup);
        tv_rating_meetup = view.findViewById(R.id.tv_rating_meetup);
        img_user_meetup = view.findViewById(R.id.img_user_meetup);
        lay_call_icon_meetup = view.findViewById(R.id.lay_call_icon_meetup);
        tv_use_name_meetup = view.findViewById(R.id.tv_use_name_meetup);

        btn_cancel_meetup.setTypeface(TypeFactory.getInstance(getActivity()).PoppinsRegular());
        txt_meetup.setTypeface(TypeFactory.getInstance(getActivity()).PoppinsBold());
        btn_arrived_meetup.setTypeface(TypeFactory.getInstance(getActivity()).PoppinsBold());
        tv_rating_meetup.setTypeface(TypeFactory.getInstance(getActivity()).PoppinsRegular());
    }

    //get bottom sheet request/////
    private void get_view_bottom_sheet_new_request(String vRequest_Id, String vTimmer_time, String vReq_time) throws ParseException {

        behavior_bs_request.setState(BottomSheetBehavior.STATE_EXPANDED);
        btn_cancel_request = view.findViewById(R.id.btn_cancel_request);
        tv_user_name = view.findViewById(R.id.tv_user_name);
        tv_method = view.findViewById(R.id.tv_method);
        btn_accept_request = view.findViewById(R.id.btn_accept_request);
        tv_timer = view.findViewById(R.id.tv_timer);
        lay_btn_accept_request = view.findViewById(R.id.lay_btn_accept_request);
        tv_total_distance = view.findViewById(R.id.tv_distance);
        tv_total_time = view.findViewById(R.id.tv_time_taken);

        tv_method.setTypeface(TypeFactory.getInstance(getActivity()).PoppinsBold());
        tv_user_name.setTypeface(TypeFactory.getInstance(getActivity()).PoppinsBold());
        btn_cancel_request.setTypeface(TypeFactory.getInstance(getActivity()).PoppinsRegular());
        btn_accept_request.setTypeface(TypeFactory.getInstance(getActivity()).PoppinsBold());
        tv_total_distance.setTypeface(TypeFactory.getInstance(getActivity()).PoppinsBold());
        tv_total_time.setTypeface(TypeFactory.getInstance(getActivity()).PoppinsBold());

        lay_btn_accept_request.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (Utility.isConnectingToInternet(getActivity())) {
                    Accept_Request_api();
                } else {
                    Toast.makeText(getActivity(), getResources().getString(R.string.internet_error), Toast.LENGTH_SHORT).show();
                }


            }
        });

        btn_cancel_request.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    is_cancle_req = true;
                    cancle_request_dialoge(vRequest_Id, "test");
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });

        try {
            Date today = new Date();
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("hh:mm:ss a");
            simpleDateFormat.setTimeZone(TimeZone.getDefault());

            SimpleDateFormat simpleDateFormat1 = new SimpleDateFormat("hh:mm:ss");
            simpleDateFormat1.setTimeZone(TimeZone.getDefault());

            DateFormat outputformat = new SimpleDateFormat("HH:mm:ss");
            outputformat.setTimeZone(TimeZone.getDefault());

            Date vReq_date = null, vCurrent_date = null, vTimer_date = null;
            String Req_output1 = null, vCurrent_output2 = null, vTimer_time_output = null;
            /////////////////////request time//////////////////
            vReq_date = simpleDateFormat.parse(vReq_time);
            Req_output1 = outputformat.format(vReq_date);

            ArrayList<String> timestampsList = new ArrayList<String>();
            timestampsList.add(Req_output1);
            timestampsList.add(vTimmer_time);


            long tm = 0;
            for (String tmp : timestampsList) {
                String[] arr = tmp.split(":");
                tm += Integer.parseInt(arr[2]);
                tm += 60 * Integer.parseInt(arr[1]);
                tm += 3600 * Integer.parseInt(arr[0]);
            }

            long hh = tm / 3600;
            tm %= 3600;
            long mm = tm / 60;
            tm %= 60;
            long ss = tm;

            String vserver_time = format(hh) + ":" + format(mm) + ":" + format(ss);
            /////////////////////timer value/////////////
            vTimer_date = outputformat.parse(vserver_time);
            System.out.println("....................." + vTimer_date.getTime());
//            /////////////////current time//////////////////////
            final String time_current = simpleDateFormat.format(today);
            vCurrent_date = simpleDateFormat.parse(time_current);
            vCurrent_output2 = outputformat.format(vCurrent_date);


            if (vTimer_date.getTime() >= vCurrent_date.getTime()) {
                long mills = vTimer_date.getTime() - vCurrent_date.getTime();
                long diffSeconds = (mills / 1000) % 60;
                long diffMinutes = mills / (60 * 1000);
                long diffHours = mills / (60 * 60 * 1000);

                String diff = diffMinutes + ":" + diffSeconds;
                System.out.println("Time in seconds: " + diffSeconds + " seconds.");
                System.out.println("Time in minutes: " + diffMinutes + " minutes.");
                System.out.println("Time in hours: " + diffHours + " hours.");
                timer_set(vRequest_Id, diff);
            } else {
                Toast.makeText(getActivity(), "time is more then current time", Toast.LENGTH_SHORT).show();
            }
        } catch (ParseException | NumberFormatException e) {
            e.printStackTrace();
        }
    }

    private static String format(long s) {
        if (s < 10) return "0" + s;
        else return "" + s;
    }


    private void timer_set(final String req_id, final String time) {
        try {
            long min = Integer.parseInt(time.substring(0, 2));
            long sec = Integer.parseInt(time.substring(3));
            System.out.println("cdsdddda....................." + min + "                " + sec);

            long t = (min * 60L) + sec;
            long result = TimeUnit.SECONDS.toMillis(t);
            mTImeLeftMillies = result;

            if (is_cancle_req) {
                if (yourCountDownTimer != null) {
                    yourCountDownTimer.cancel();
                }
                MainActivity mainActivity = (MainActivity) getActivity();
                if (mainActivity != null)
                    mainActivity.displayView(0);
            } else {
                yourCountDownTimer = new CountDownTimer(result, 1000) {
                    public void onTick(long millisUntilFinished) {
                        // mTextField.setText("seconds remaining: " + millisUntilFinished / 1000);
                        mTImeLeftMillies = millisUntilFinished;
                        int minutes = (int) (mTImeLeftMillies / 1000) / 60;
                        int seconds = (int) (mTImeLeftMillies / 1000) % 60;
                        String vTImeLeftFormatted = String.format(Locale.getDefault(), "%02d:%02d", minutes, seconds);
                        tv_timer.setText(vTImeLeftFormatted);
                    }

                    public void onFinish() {
                        if (is_cancle_req) {
                            if (yourCountDownTimer != null) {
                                yourCountDownTimer.cancel();
                            }
                        } else {
                            is_cancle_req = false;
                            tv_timer.setText("00:00");
                            if (Utility.isConnectingToInternet(getActivity())) {
                                if (yourCountDownTimer != null) {
                                    yourCountDownTimer.cancel();
                                }
                                MainActivity mainActivity = (MainActivity) getActivity();
                                if (mainActivity != null)
                                    mainActivity.displayView(0);
                            } else {
                                if (getActivity() != null)
                                    Toast.makeText(getActivity(), "No Internet Connection !", Toast.LENGTH_SHORT).show();
                            }
                        }
                    }
                }.start();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    //bottom sheet  on_off////
    private void get_View_botttom_sheet_on_off() {
        statusOnlineTv = view.findViewById(R.id.tv_online_status);
        onlineTv = view.findViewById(R.id.online_tv);
        offlineTv = view.findViewById(R.id.offline_tv);
        online_container = view.findViewById(R.id.online_container);
        offline_container = view.findViewById(R.id.offline_container);

        online_container.setVisibility(View.GONE);
        offline_container.setVisibility(View.GONE);
        statusOnlineTv.setText(" ");
        onlineTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    vStatus = String.valueOf(1);
                    online_container.setVisibility(View.GONE);
                    offline_container.setVisibility(View.VISIBLE);
                    statusOnlineTv.setText("You're Online");
                    if (Utility.isConnectingToInternet(getActivity())) {
                        Current_Status_API();
                    } else {
                        Toast.makeText(getActivity(), getResources().getString(R.string.internet_error), Toast.LENGTH_SHORT).show();
                    }
                } catch (Resources.NotFoundException e) {
                    e.printStackTrace();
                }
            }
        });
        offlineTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    vStatus = String.valueOf(0);
                    offline_container.setVisibility(View.GONE);
                    online_container.setVisibility(View.VISIBLE);
                    statusOnlineTv.setText("You're Offline");
                    if (Utility.isConnectingToInternet(getActivity())) {
                        Current_Status_API();
                    } else {
                        Toast.makeText(getActivity(), getResources().getString(R.string.internet_error), Toast.LENGTH_SHORT).show();
                    }
                } catch (Resources.NotFoundException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    /////bottom sheet arriverd meetup///////
    private void get_view_bottom_sheet_driver_meetup_arrived(String vReq_id, String vUsername, String v_user_img, String v_user_mob, String vProperty_id, String vBS_Name) {
        try {
            behavior_bs_meetup_arrived.setState(BottomSheetBehavior.STATE_EXPANDED);
            tv_use_name_meetup.setText(vUsername);

            Picasso.with(getActivity()).load(v_user_img).error(R.drawable.profil).resize(300, 300).into(img_usert);
        } catch (Exception e) {
            e.printStackTrace();
        }

        btn_cancel_meetup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    cancle_request_api(HomeFragment.this.vReq_id, "test");
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
        btn_arrived_meetup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    if (Utility.isConnectingToInternet(getActivity())) {
                        if (vBS_Name.equals("arrived_status")) {
                            call_arrived_user_api(vReq_id);
                        } else if (vBS_Name.equals("complete_trip")) {
                            open_review_rating_screen(vReq_id, vUsername, v_user_img);
                        } else {
                            if (getActivity() != null) {
                                getActivity().recreate();
                            }
                        }
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });

        lay_call_icon_meetup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    Intent callIntent = new Intent(Intent.ACTION_CALL);
                    callIntent.setData(Uri.parse(v_user_mob));


                    if (ActivityCompat.checkSelfPermission(Objects.requireNonNull(getActivity()),
                            Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
                        ActivityCompat.requestPermissions(getActivity(), new String[]{Manifest.permission.CALL_PHONE}, REQUEST_PHONE_CALL);

                    }
                    startActivity(callIntent);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

    /// bottom_sheet_driver_pickup_arrived///////
    private void get_view_bottom_sheet_driver_pickup_arrived(String vReq_id, String vUsername, String v_user_img, String v_user_mob, String vProperty_id, String vBS_Name) {
        behavior_bs_pickup_arrived.setState(BottomSheetBehavior.STATE_EXPANDED);
        try {
            Picasso.with(getActivity()).load(v_user_img).error(R.drawable.profil).resize(300, 300).into(img_usert);
        } catch (Exception e) {
            e.printStackTrace();
        }

        btn_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                cancle_request_api(HomeFragment.this.vReq_id, "test");
            }
        });
        btn_arrived.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    if (Utility.isConnectingToInternet(getActivity())) {
                        if (vBS_Name.equals("arrived_status")) {
                            call_arrived_user_api(vReq_id);
                        } else if (vBS_Name.equals("pickedup_status")) {
                            call_pickedup_user_api(vReq_id);
                        } else if (vBS_Name.equals("arrived_property_status ")) {
                            call_arrived_at_property_api(vReq_id);
                        } else if (vBS_Name.equals("ready_for_dropoff")) {
                            call_ready_for_dropoff_api(vReq_id);
                        } else if (vBS_Name.equals("dropoff_status")) {
                            call_drop_off_api(vReq_id);
                        } else if (vBS_Name.equals("complete_trip")) {

                            open_review_rating_screen(vReq_id, vUsername, v_user_img);
                        } else {
                            if (getActivity() != null) {
                                getActivity().recreate();
                            }
                        }
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });

        call_icon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    Intent callIntent = new Intent(Intent.ACTION_CALL);
                    callIntent.setData(Uri.parse(v_user_mob));


                    if (ActivityCompat.checkSelfPermission(Objects.requireNonNull(getActivity()),
                            Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
                        ActivityCompat.requestPermissions(getActivity(), new String[]{Manifest.permission.CALL_PHONE}, REQUEST_PHONE_CALL);

                    }
                    startActivity(callIntent);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

    private float ratings = 0;
    private String review_commnt = "";
    private ProgressBar reviewProgrss;

    private void open_review_rating_screen(String vReq_id, String tv_name, String img_usert) {
        LayoutInflater inflater = (LayoutInflater) Objects.requireNonNull(getActivity()).getSystemService(LAYOUT_INFLATER_SERVICE);
        View popupView = inflater.inflate(R.layout.activity_review_rating, null);
        // create the popup window
        int width = LinearLayout.LayoutParams.MATCH_PARENT;
        int height = LinearLayout.LayoutParams.MATCH_PARENT;
        boolean focusable = true; // lets taps outside the popup also dismiss it
        popupWindow1 = new PopupWindow(popupView, width, height, focusable);
        final String comment;
        // show the popup window
        // which view you pass in doesn't matter, it is only used for the window tolken
        popupWindow1.showAtLocation(view, Gravity.CENTER, 0, 0);
        final SliderView img_property;
        final TextView txt_trip_completed, tv_user_name, tv_time, tv_price, tv_distance, txt_how_was, txt_submit;
        final RatingBar ratingBar;
        final ImageView img_submit;

        final CircleImageView profile_img;
        EditText edt_comment;

// Find views
        txt_trip_completed = popupView.findViewById(R.id.txt_trip_completed);
        tv_user_name = popupView.findViewById(R.id.tv_user_name);
        tv_time = popupView.findViewById(R.id.tv_time);
        tv_price = popupView.findViewById(R.id.tv_price);
        tv_distance = popupView.findViewById(R.id.tv_distance);
        txt_how_was = popupView.findViewById(R.id.txt_how_was);
        txt_submit = popupView.findViewById(R.id.txt_submit);
        ratingBar = popupView.findViewById(R.id.ratingBar);
        edt_comment = popupView.findViewById(R.id.edt_comment);
        reviewProgrss = popupView.findViewById(R.id.reviewProgrss);
        img_submit = popupView.findViewById(R.id.img_submit);
        profile_img = popupView.findViewById(R.id.profile_img);

// Type face
        txt_trip_completed.setTypeface(TypeFactory.getInstance(getContext()).PoppinsBold());
        tv_user_name.setTypeface(TypeFactory.getInstance(getContext()).PoppinsSemiBold());
        tv_time.setTypeface(TypeFactory.getInstance(getContext()).PoppinsBold());
        tv_price.setTypeface(TypeFactory.getInstance(getContext()).PoppinsBold());
        tv_distance.setTypeface(TypeFactory.getInstance(getContext()).PoppinsBold());
        txt_how_was.setTypeface(TypeFactory.getInstance(getContext()).PoppinsBold());
        txt_submit.setTypeface(TypeFactory.getInstance(getContext()).PoppinsBold());

        if (tv_name != null) {
            tv_user_name.setText(tv_name);
        }
        if (!img_usert.equals("")) {
            try {
                Picasso.with(getActivity()).load(String.valueOf(img_usert)).error(R.drawable.user_img).resize(300, 300).into(profile_img);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        ratingBar.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener() {
            @Override
            public void onRatingChanged(RatingBar ratingBar, float rating, boolean b) {
                //Toast.makeText(getActivity(), (int) ratingBar.getRating()+"   "+ rating, Toast.LENGTH_SHORT).show();
                ratings = rating;
            }
        });

        //Click events
        img_submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    review_commnt = edt_comment.getText().toString().trim();
                    if (edt_comment.getText().toString().trim().isEmpty()) {
                        Toast.makeText(getActivity(), "Please write commnets !", Toast.LENGTH_SHORT).show();
                    } else if (ratings == 0) {
                        Toast.makeText(getActivity(), "Please give rating !", Toast.LENGTH_SHORT).show();
                    } else {
                        call_complete_trip_api(vReq_id);
                        popupWindow1.dismiss();
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

    private void call_complete_trip_api(String vReq_id) {
        if (reviewProgrss != null)
            reviewProgrss.setVisibility(View.VISIBLE);
        final Call<PICKUPUSERDTO> pickupuserdtoCall = apiInterface.request_complete_trip(vReq_id, 9, date, timezone, review_commnt, ratings, sessionManager.get_User_id(), sessionManager.get_Agent_id(), "agent");
        pickupuserdtoCall.enqueue(new Callback<PICKUPUSERDTO>() {
            @Override
            public void onResponse(@NotNull Call<PICKUPUSERDTO> call, @NotNull Response<PICKUPUSERDTO> response) {
                if (reviewProgrss != null)
                    reviewProgrss.setVisibility(View.GONE);
                try {
                    PICKUPUSERDTO pickupuserdto = response.body();
                    if (pickupuserdto == null)
                        return;
                    if (pickupuserdto.getResponse()) {
                        if (getActivity() != null) {
                            if (mGoogleMap != null) {
                                mGoogleMap.clear();
                            }
                            getActivity().recreate();
                        }
                    } else {
                        Toast.makeText(getActivity(), !pickupuserdto.getMessage().isEmpty() ? pickupuserdto.getMessage() : "", Toast.LENGTH_SHORT).show();
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(@NotNull Call<PICKUPUSERDTO> call, @NotNull Throwable t) {
                Log.e("onFaliure..", "onFailure: ", t);
                if (reviewProgrss != null)
                    reviewProgrss.setVisibility(View.GONE);
            }
        });
    }

    private void call_drop_off_api(String vReq_id) {
        final Call<PICKUPUSERDTO> pickupuserdtoCall = apiInterface.request_dropoff(vReq_id, 8, date, timezone);
        pickupuserdtoCall.enqueue(new Callback<PICKUPUSERDTO>() {
            @Override
            public void onResponse(@NotNull Call<PICKUPUSERDTO> call, @NotNull Response<PICKUPUSERDTO> response) {
                try {
                    PICKUPUSERDTO pickupuserdto = response.body();
                    if (pickupuserdto == null)
                        return;
                    if (pickupuserdto.getResponse().equals(true)) {
                        MainActivity mainActivity = (MainActivity) getActivity();
                        if (mainActivity != null)
                            mainActivity.displayView(0);
                    } else {
                        Toast.makeText(getActivity(), !pickupuserdto.getMessage().isEmpty() ? pickupuserdto.getMessage() : "", Toast.LENGTH_SHORT).show();
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(@NotNull Call<PICKUPUSERDTO> call, @NotNull Throwable t) {
                Log.e("onFaliure..", "onFailure: ", t);
            }
        });
    }

    private void call_ready_for_dropoff_api(String vReq_id) {
        final Call<PICKUPUSERDTO> pickupuserdtoCall = apiInterface.request_ready_for_dropoff(vReq_id, 7, date, timezone);
        pickupuserdtoCall.enqueue(new Callback<PICKUPUSERDTO>() {
            @Override
            public void onResponse(@NotNull Call<PICKUPUSERDTO> call, @NotNull Response<PICKUPUSERDTO> response) {

                PICKUPUSERDTO pickupuserdto = response.body();
                if (pickupuserdto == null)
                    return;
                if (pickupuserdto.getResponse().equals(true)) {
                    MainActivity mainActivity = (MainActivity) getActivity();
                    if (mainActivity != null)
                        mainActivity.displayView(0);
                } else {
                    Toast.makeText(getActivity(), !pickupuserdto.getMessage().isEmpty() ? pickupuserdto.getMessage() : "", Toast.LENGTH_SHORT).show();
                }

            }

            @Override
            public void onFailure(@NotNull Call<PICKUPUSERDTO> call, @NotNull Throwable t) {
                Log.e("onFaliure..", "onFailure: ", t);
            }
        });
    }

    private void call_arrived_at_property_api(String vReq_id) {
        final Call<PICKUPUSERDTO> pickupuserdtoCall = apiInterface.request_arrived_property(vReq_id, 6, date, timezone);
        pickupuserdtoCall.enqueue(new Callback<PICKUPUSERDTO>() {
            @Override
            public void onResponse(@NotNull Call<PICKUPUSERDTO> call, @NotNull Response<PICKUPUSERDTO> response) {

                PICKUPUSERDTO pickupuserdto = response.body();
                if (pickupuserdto == null)
                    return;
                if (pickupuserdto.getResponse().equals(true)) {
                    MainActivity mainActivity = (MainActivity) getActivity();
                    if (mainActivity != null)
                        mainActivity.displayView(0);
                } else {
                    Toast.makeText(getActivity(), !pickupuserdto.getMessage().isEmpty() ? pickupuserdto.getMessage() : "", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(@NotNull Call<PICKUPUSERDTO> call, @NotNull Throwable t) {
                Log.e("onFaliure..", "onFailure: ", t);
            }
        });
    }

    private void call_pickedup_user_api(String vReq_id) {
        final Call<PICKUPUSERDTO> pickupuserdtoCall = apiInterface.request_pickedup(vReq_id, 5, date, timezone);
        pickupuserdtoCall.enqueue(new Callback<PICKUPUSERDTO>() {
            @Override
            public void onResponse(@NotNull Call<PICKUPUSERDTO> call, @NotNull Response<PICKUPUSERDTO> response) {

                PICKUPUSERDTO pickupuserdto = response.body();
                if (pickupuserdto == null)
                    return;
                if (pickupuserdto.getResponse().equals(true)) {
                    MainActivity mainActivity = (MainActivity) getActivity();
                    if (mainActivity != null)
                        mainActivity.displayView(0);
                } else {
                    Toast.makeText(getActivity(), !pickupuserdto.getMessage().isEmpty() ? pickupuserdto.getMessage() : "", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(@NotNull Call<PICKUPUSERDTO> call, @NotNull Throwable t) {
                Log.e("onFaliure..", "onFailure: ", t);
            }
        });
    }

    private void call_arrived_user_api(String vReq_id) {
        final Call<PICKUPUSERDTO> pickupuserdtoCall = apiInterface.request_arrived(vReq_id, 4, date, timezone);
        pickupuserdtoCall.enqueue(new Callback<PICKUPUSERDTO>() {
            @Override
            public void onResponse(@NotNull Call<PICKUPUSERDTO> call, @NotNull Response<PICKUPUSERDTO> response) {
                PICKUPUSERDTO pickupuserdto = response.body();
                if (pickupuserdto == null)
                    return;
                if (pickupuserdto.getResponse().equals(true)) {
                    MainActivity mainActivity = (MainActivity) getActivity();
                    if (mainActivity != null) {
                        mainActivity.displayView(0);
                    }
                } else {
                    Toast.makeText(getActivity(), !pickupuserdto.getMessage().isEmpty() ? pickupuserdto.getMessage() : "", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(@NotNull Call<PICKUPUSERDTO> call, @NotNull Throwable t) {
                Log.e("onFaliure..", "onFailure: ", t);
            }
        });
    }

    private void get_Request_API() {
        //     Toast.makeText(getActivity(), lat + "  " + lng, Toast.LENGTH_SHORT).show();
        try {
            Log.e("agent id", "agent id " + sessionManager.get_Agent_id());
            if (!sessionManager.get_Agent_id().equals("")) {
                final Call<REQUEST_DTO> agent_list_dtos = apiInterface.get_request_notification(sessionManager.get_Agent_id());
                agent_list_dtos.enqueue(new Callback<REQUEST_DTO>() {
                    @Override
                    public void onResponse(@NotNull Call<REQUEST_DTO> call, @NotNull Response<REQUEST_DTO> response) {
                        REQUEST_DTO request_dto = response.body();
                        if (UpdateLocationService.updateLocationService == null) {
                            if (ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                                // TODO: Consider calling
                                //    ActivityCompat#requestPermissions
                                // here to request the missing permissions, and then overriding
                                //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                                //                                          int[] grantResults)
                                // to handle the case where the user grants the permission. See the documentation
                                // for ActivityCompat#requestPermissions for more details.
                                return;
                            }
                            mFusedLocationClient.requestLocationUpdates(mLocationRequest, mLocationCallback, Looper.myLooper());
                            Objects.requireNonNull(getActivity()).startService(new Intent(getActivity(), UpdateLocationService.class));
                        }
                        try {
                            if (request_dto == null)
                                return;
                            Log.e("getRequest data", "getRequest data " + response.body());
                            if (request_dto.getStatus() == 1) {
                                if (request_dto.getRequestdata() != null) {
                                    SessionManager.IS_REQUEST = true;
                                    switch (request_dto.getRequestdata().getStatus()) {
                                        case "0":
                                            try {
                                                if (Utility.isConnectingToInternet(getActivity())) {
                                                    is_Request = true;
                                                    getAddress(driverLatLng.latitude, driverLatLng.longitude);
                                                    bottom_sheet_on_off.setVisibility(View.GONE);

                                                    vUser_name = request_dto.getRequestdata().getFullName() != null ? request_dto.getRequestdata().getFullName() : "";
                                                    vEmail = request_dto.getRequestdata().getEmail() != null ? request_dto.getRequestdata().getEmail() : "";
                                                    vEmr_Num = request_dto.getRequestdata().getEmrNum() != null ? request_dto.getRequestdata().getEmrNum() : "";
                                                    vUser_Mobile = request_dto.getRequestdata().getPhone() != null ? request_dto.getRequestdata().getPhone() : "";
                                                    vLocation = request_dto.getRequestdata().getLocation() != null ? request_dto.getRequestdata().getLocation() : "";
                                                    vPickup_Type = request_dto.getRequestdata().getPickupType() != null ? request_dto.getRequestdata().getPickupType() : "";
                                                    vProperty_id = request_dto.getRequestdata().getPropertyId() != null ? request_dto.getRequestdata().getPropertyId() : "";
                                                    vReq_id = request_dto.getRequestdata().getAgId() != null ? request_dto.getRequestdata().getAgId() : "";
                                                    vUser_id = request_dto.getRequestdata().getUserId() != null ? request_dto.getRequestdata().getUserId() : "";
                                                    vUser_Image = request_dto.getRequestdata().getProfilepic() != null ? request_dto.getRequestdata().getProfilepic() : "";
                                                    vUser_lat = Double.parseDouble(request_dto.getRequestdata().getUserlat());
                                                    vUser_lng = Double.parseDouble(request_dto.getRequestdata().getUserlng());
                                                    vProperty_lat = Double.parseDouble(request_dto.getRequestdata().getLat());
                                                    vProperty_lng = Double.parseDouble(request_dto.getRequestdata().getLng());
                                                    timmer_time = request_dto.getRequestdata().getTimmer_time() != null ? request_dto.getRequestdata().getTimmer_time() : "";
                                                    req_time = request_dto.getRequestdata().getReq_time() != null ? request_dto.getRequestdata().getReq_time() : "";
                                                    tv_user_name.setText(vUser_name);
                                                    sessionManager.setUser_id(vUser_id);

                                                    bottom_sheet_request.setVisibility(View.VISIBLE);
                                                    //get bottom sheet request/////
                                                    get_view_bottom_sheet_new_request(vReq_id, timmer_time, req_time);


                                                    if (vPickup_Type.equals("1")) {
                                                        mode_type = 1;
                                                        tv_method.setText("Method: Pick Up");
                                                        show_markers_locations(vUser_lat, vUser_lng, vProperty_id, vProperty_lat, vProperty_lng, mode_type);

                                                    } else if (vPickup_Type.equals("2")) {
                                                        mode_type = 2;
                                                        tv_method.setText("Method: Meet Up");
                                                        show_markers_locations(vUser_lat, vUser_lng, vProperty_id, vProperty_lat, vProperty_lng, mode_type);
                                                    }
                                                } else {
                                                    Toast.makeText(getActivity(), getResources().getString(R.string.internet_error), Toast.LENGTH_SHORT).show();
                                                }
                                            } catch (NumberFormatException e) {
                                                e.printStackTrace();
                                            }
                                            break;
                                        case "3":
                                            try {
                                                if (Utility.isConnectingToInternet(getActivity())) {
                                                    is_Request = true;

                                                    bottom_sheet_on_off.setVisibility(View.GONE);
                                                    bottom_sheet_request.setVisibility(View.GONE);
                                                    vUser_name = request_dto.getRequestdata().getFullName() != null ? request_dto.getRequestdata().getFullName() : "";
                                                    vEmail = request_dto.getRequestdata().getEmail() != null ? request_dto.getRequestdata().getEmail() : "";
                                                    vEmr_Num = request_dto.getRequestdata().getEmrNum() != null ? request_dto.getRequestdata().getEmrNum() : "";
                                                    vUser_Mobile = request_dto.getRequestdata().getPhone() != null ? request_dto.getRequestdata().getPhone() : "";
                                                    vLocation = request_dto.getRequestdata().getLocation() != null ? request_dto.getRequestdata().getLocation() : "";
                                                    vPickup_Type = request_dto.getRequestdata().getPickupType() != null ? request_dto.getRequestdata().getPickupType() : "";
                                                    vProperty_id = request_dto.getRequestdata().getPropertyId() != null ? request_dto.getRequestdata().getPropertyId() : "";
                                                    vReq_id = request_dto.getRequestdata().getAgId() != null ? request_dto.getRequestdata().getAgId() : "";
                                                    vUser_id = request_dto.getRequestdata().getUserId() != null ? request_dto.getRequestdata().getUserId() : "";
                                                    vUser_Image = request_dto.getRequestdata().getProfilepic() != null ? request_dto.getRequestdata().getProfilepic() : "";
                                                    vUser_lat = Double.parseDouble(request_dto.getRequestdata().getUserlat());
                                                    vUser_lng = Double.parseDouble(request_dto.getRequestdata().getUserlng());
                                                    vProperty_lat = Double.parseDouble(request_dto.getRequestdata().getLat());
                                                    vProperty_lng = Double.parseDouble(request_dto.getRequestdata().getLng());
                                                    sessionManager.setUser_id(vUser_id);

                                                    tv_user_name.setText(vUser_name);
                                                    if (vPickup_Type.equals("1")) {
                                                        mode_type = 1;
                                                        show_markers_locations(vUser_lat, vUser_lng, vProperty_id, vProperty_lat, vProperty_lng, mode_type);
                                                        Objects.requireNonNull(getActivity()).stopService(new Intent(getActivity(), GET_User_Location_service.class));
                                                        bottom_sheet_driver_pickup_arrived.setVisibility(View.VISIBLE);
                                                        /// bottom_sheet_driver_pickup_arrived///////
                                                        get_view_bottom_sheet_driver_pickup_arrived(vReq_id, vUser_name, vUser_Image, vUser_Mobile, vProperty_id, "arrived_status");
                                                        prog_arrived.setProgress(80);
                                                    } else if (vPickup_Type.equals("2")) {
                                                        mode_type = 2;
                                                        bottom_sheet_driver_meetup_complete_trip.setVisibility(View.VISIBLE);
                                                        sessionManager.set_user_app_details(vProperty_id, vProperty_lat, vProperty_lng, mode_type);
//                                                        Objects.requireNonNull(getActivity()).startService(new Intent(getActivity(), GET_User_Location_service.class));
                                                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                                                            getActivity().startForegroundService(new Intent(getActivity(), GET_User_Location_service.class));
                                                        } else {
                                                            getActivity().startService(new Intent(getActivity(), GET_User_Location_service.class));
                                                        }
                                                        get_view_bottom_sheet_driver_meetup_arrived(vReq_id, vUser_name, vUser_Image, vUser_Mobile, vProperty_id, "arrived_status");
                                                        //    show_markers_locations(vUser_lat, vUser_lng, vProperty_id, vProperty_lat, vProperty_lng, mode_type);
                                                    }
                                                } else {
                                                    Toast.makeText(getActivity(), getResources().getString(R.string.internet_error), Toast.LENGTH_SHORT).show();
                                                }
                                            } catch (NumberFormatException e) {
                                                e.printStackTrace();
                                            }
                                            break;
                                        case "4":
                                            try {
                                                if (Utility.isConnectingToInternet(getActivity())) {
                                                    is_Request = true;
                                                    bottom_sheet_on_off.setVisibility(View.GONE);
                                                    bottom_sheet_request.setVisibility(View.GONE);
                                                    vUser_name = request_dto.getRequestdata().getFullName() != null ? request_dto.getRequestdata().getFullName() : "";
                                                    vEmail = request_dto.getRequestdata().getEmail() != null ? request_dto.getRequestdata().getEmail() : "";
                                                    vEmr_Num = request_dto.getRequestdata().getEmrNum() != null ? request_dto.getRequestdata().getEmrNum() : "";
                                                    vUser_Mobile = request_dto.getRequestdata().getPhone() != null ? request_dto.getRequestdata().getPhone() : "";
                                                    vLocation = request_dto.getRequestdata().getLocation() != null ? request_dto.getRequestdata().getLocation() : "";
                                                    vPickup_Type = request_dto.getRequestdata().getPickupType() != null ? request_dto.getRequestdata().getPickupType() : "";
                                                    vProperty_id = request_dto.getRequestdata().getPropertyId() != null ? request_dto.getRequestdata().getPropertyId() : "";
                                                    vReq_id = request_dto.getRequestdata().getAgId() != null ? request_dto.getRequestdata().getAgId() : "";
                                                    vUser_id = request_dto.getRequestdata().getUserId() != null ? request_dto.getRequestdata().getUserId() : "";
                                                    vUser_Image = request_dto.getRequestdata().getProfilepic() != null ? request_dto.getRequestdata().getProfilepic() : "";
                                                    vUser_lat = Double.parseDouble(request_dto.getRequestdata().getUserlat());
                                                    vUser_lng = Double.parseDouble(request_dto.getRequestdata().getUserlng());
                                                    vProperty_lat = Double.parseDouble(request_dto.getRequestdata().getLat());
                                                    vProperty_lng = Double.parseDouble(request_dto.getRequestdata().getLng());
                                                    sessionManager.setUser_id(vUser_id);
                                                    tv_user_name.setText(vUser_name);
                                                    if (vPickup_Type.equals("1")) {
                                                        mode_type = 1;
                                                        //  tv_method.setText("Method: Pick Up");
                                                        show_markers_locations(vUser_lat, vUser_lng, vProperty_id, vProperty_lat, vProperty_lng, mode_type);
                                                        if (GET_User_Location_service.get_user_location_service != null) {
                                                            Objects.requireNonNull(getActivity()).stopService(new Intent(getActivity(), GET_User_Location_service.class));
                                                        }
                                                        bottom_sheet_driver_pickup_arrived.setVisibility(View.VISIBLE);
                                                        /// bottom_sheet_driver_pickup_arrived///////
                                                        get_view_bottom_sheet_driver_pickup_arrived(vReq_id, vUser_name, vUser_Image, vUser_Mobile, vProperty_id, "pickedup_status");
                                                        txt_driving_pickup.setText("Waiting to Pick Up :");
                                                        btn_arrived.setText("PICKED UP");
                                                        prog_arrived.setProgress(100);
                                                    } else if (vPickup_Type.equals("2")) {
                                                        mode_type = 2;

                                                        bottom_sheet_driver_meetup_complete_trip.setVisibility(View.VISIBLE);
                                                        sessionManager.set_user_app_details(vProperty_id, vProperty_lat, vProperty_lng, mode_type);
                                                        get_view_bottom_sheet_driver_meetup_arrived(vReq_id, vUser_name, vUser_Image, vUser_Mobile, vProperty_id, "complete_trip");
                                                        btn_arrived_meetup.setText("COMPLETE TRIP");

                                                        if (GET_User_Location_service.get_user_location_service == null) {
//                                                            Objects.requireNonNull(getActivity()).startService(new Intent(getActivity(), GET_User_Location_service.class));
                                                            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                                                                getActivity().startForegroundService(new Intent(getActivity(), GET_User_Location_service.class));
                                                            } else {
                                                                getActivity().startService(new Intent(getActivity(), GET_User_Location_service.class));
                                                            }
                                                        }
                                                    }
                                                } else {
                                                    Toast.makeText(getActivity(), getResources().getString(R.string.internet_error), Toast.LENGTH_SHORT).show();
                                                }
                                            } catch (NumberFormatException e) {
                                                e.printStackTrace();
                                            }
                                            break;
                                        case "5":
                                            try {
                                                if (Utility.isConnectingToInternet(getActivity())) {
                                                    is_Request = true;
                                                    bottom_sheet_on_off.setVisibility(View.GONE);
                                                    bottom_sheet_request.setVisibility(View.GONE);
                                                    vUser_name = request_dto.getRequestdata().getFullName() != null ? request_dto.getRequestdata().getFullName() : "";
                                                    vEmail = request_dto.getRequestdata().getEmail() != null ? request_dto.getRequestdata().getEmail() : "";
                                                    vEmr_Num = request_dto.getRequestdata().getEmrNum() != null ? request_dto.getRequestdata().getEmrNum() : "";
                                                    vUser_Mobile = request_dto.getRequestdata().getPhone() != null ? request_dto.getRequestdata().getPhone() : "";
                                                    vLocation = request_dto.getRequestdata().getLocation() != null ? request_dto.getRequestdata().getLocation() : "";
                                                    vPickup_Type = request_dto.getRequestdata().getPickupType() != null ? request_dto.getRequestdata().getPickupType() : "";
                                                    vProperty_id = request_dto.getRequestdata().getPropertyId() != null ? request_dto.getRequestdata().getPropertyId() : "";
                                                    vReq_id = request_dto.getRequestdata().getAgId() != null ? request_dto.getRequestdata().getAgId() : "";
                                                    vUser_id = request_dto.getRequestdata().getUserId() != null ? request_dto.getRequestdata().getUserId() : "";
                                                    vUser_Image = request_dto.getRequestdata().getProfilepic() != null ? request_dto.getRequestdata().getProfilepic() : "";
                                                    vUser_lat = Double.parseDouble(request_dto.getRequestdata().getUserlat());
                                                    vUser_lng = Double.parseDouble(request_dto.getRequestdata().getUserlng());
                                                    vProperty_lat = Double.parseDouble(request_dto.getRequestdata().getLat());
                                                    vProperty_lng = Double.parseDouble(request_dto.getRequestdata().getLng());
                                                    sessionManager.setUser_id(vUser_id);

                                                    tv_user_name.setText(vUser_name);
                                                    if (vPickup_Type.equals("1")) {
                                                        mode_type = 1;
                                                        //  tv_method.setText("Method: Pick Up");
                                                        show_markers_locations(vUser_lat, vUser_lng, vProperty_id, vProperty_lat, vProperty_lng, mode_type);
                                                        if (GET_User_Location_service.get_user_location_service != null) {
                                                            Objects.requireNonNull(getActivity()).stopService(new Intent(getActivity(), GET_User_Location_service.class));
                                                        }

                                                        bottom_sheet_driver_pickup_arrived.setVisibility(View.VISIBLE);
                                                        /// bottom_sheet_driver_pickup_arrived///////
                                                        get_view_bottom_sheet_driver_pickup_arrived(vReq_id, vUser_name, vUser_Image, vUser_Mobile, vProperty_id, "arrived_property_status ");
                                                        txt_driving_pickup.setText("Driving to the Property :");
                                                        txt_driving_pickup.setTextSize(13);
                                                        btn_arrived.setText("Arrived AT Property");
                                                        prog_arrived.setProgress(100);
                                                        prog_property_loc.setProgress(60);
                                                    } else if (vPickup_Type.equals("2")) {
                                                        mode_type = 2;
                                                        //    tv_method.setText("Method: Meet Up");
                                                        sessionManager.set_user_app_details(vProperty_id, vProperty_lat, vProperty_lng, mode_type);
                                                        try {
//                                                            Objects.requireNonNull(getActivity()).startService(new Intent(getActivity(), GET_User_Location_service.class));
                                                            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                                                                getActivity().startForegroundService(new Intent(getActivity(), GET_User_Location_service.class));
                                                            } else {
                                                                getActivity().startService(new Intent(getActivity(), GET_User_Location_service.class));
                                                            }
                                                        } catch (Exception e) {
                                                            e.printStackTrace();
                                                        }
                                                        //    show_markers_locations(vUser_lat, vUser_lng, vProperty_id, vProperty_lat, vProperty_lng, mode_type);
                                                    }
                                                } else {
                                                    Toast.makeText(getActivity(), getResources().getString(R.string.internet_error), Toast.LENGTH_SHORT).show();
                                                }
                                            } catch (NumberFormatException e) {
                                                e.printStackTrace();
                                            }
                                            break;
                                        case "6":
                                            try {
                                                if (Utility.isConnectingToInternet(getActivity())) {
                                                    is_Request = true;
                                                    bottom_sheet_on_off.setVisibility(View.GONE);
                                                    bottom_sheet_request.setVisibility(View.GONE);
                                                    vUser_name = request_dto.getRequestdata().getFullName() != null ? request_dto.getRequestdata().getFullName() : "";
                                                    vEmail = request_dto.getRequestdata().getEmail() != null ? request_dto.getRequestdata().getEmail() : "";
                                                    vEmr_Num = request_dto.getRequestdata().getEmrNum() != null ? request_dto.getRequestdata().getEmrNum() : "";
                                                    vUser_Mobile = request_dto.getRequestdata().getPhone() != null ? request_dto.getRequestdata().getPhone() : "";
                                                    vLocation = request_dto.getRequestdata().getLocation() != null ? request_dto.getRequestdata().getLocation() : "";
                                                    vPickup_Type = request_dto.getRequestdata().getPickupType() != null ? request_dto.getRequestdata().getPickupType() : "";
                                                    vProperty_id = request_dto.getRequestdata().getPropertyId() != null ? request_dto.getRequestdata().getPropertyId() : "";
                                                    vReq_id = request_dto.getRequestdata().getAgId() != null ? request_dto.getRequestdata().getAgId() : "";
                                                    vUser_id = request_dto.getRequestdata().getUserId() != null ? request_dto.getRequestdata().getUserId() : "";
                                                    vUser_Image = request_dto.getRequestdata().getProfilepic() != null ? request_dto.getRequestdata().getProfilepic() : "";
                                                    vUser_lat = Double.parseDouble(request_dto.getRequestdata().getUserlat());
                                                    vUser_lng = Double.parseDouble(request_dto.getRequestdata().getUserlng());
                                                    vProperty_lat = Double.parseDouble(request_dto.getRequestdata().getLat());
                                                    vProperty_lng = Double.parseDouble(request_dto.getRequestdata().getLng());
                                                    sessionManager.setUser_id(vUser_id);

                                                    tv_user_name.setText(vUser_name);
                                                    if (vPickup_Type.equals("1")) {
                                                        mode_type = 1;
                                                        //  tv_method.setText("Method: Pick Up");
                                                        show_markers_locations(vUser_lat, vUser_lng, vProperty_id, vProperty_lat, vProperty_lng, mode_type);
                                                        if (GET_User_Location_service.get_user_location_service != null) {
                                                            Objects.requireNonNull(getActivity()).stopService(new Intent(getActivity(), GET_User_Location_service.class));
                                                        }

                                                        bottom_sheet_driver_pickup_arrived.setVisibility(View.VISIBLE);
                                                        /// bottom_sheet_driver_pickup_arrived///////
                                                        get_view_bottom_sheet_driver_pickup_arrived(vReq_id, vUser_name, vUser_Image, vUser_Mobile, vProperty_id, "ready_for_dropoff");
                                                        txt_driving_pickup.setText("Showing property to :");
                                                        txt_driving_pickup.setTextSize(14);
                                                        btn_arrived.setText("READY FOR DROPOFF");
                                                        prog_arrived.setProgress(100);
                                                        prog_property_loc.setProgress(100);
                                                    } else if (vPickup_Type.equals("2")) {
                                                        mode_type = 2;
                                                        //    tv_method.setText("Method: Meet Up");
                                                        sessionManager.set_user_app_details(vProperty_id, vProperty_lat, vProperty_lng, mode_type);
                                                        try {
//                                                            Objects.requireNonNull(getActivity()).startService(new Intent(getActivity(), GET_User_Location_service.class));
                                                            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                                                                getActivity().startForegroundService(new Intent(getActivity(), GET_User_Location_service.class));
                                                            } else {
                                                                getActivity().startService(new Intent(getActivity(), GET_User_Location_service.class));
                                                            }
                                                        } catch (Exception e) {
                                                            e.printStackTrace();
                                                        }
                                                        //    show_markers_locations(vUser_lat, vUser_lng, vProperty_id, vProperty_lat, vProperty_lng, mode_type);
                                                    }
                                                } else {
                                                    Toast.makeText(getActivity(), getResources().getString(R.string.internet_error), Toast.LENGTH_SHORT).show();
                                                }
                                            } catch (NumberFormatException e) {
                                                e.printStackTrace();
                                            }
                                            break;
                                        case "7":
                                            try {
                                                if (Utility.isConnectingToInternet(getActivity())) {
                                                    is_Request = true;
                                                    bottom_sheet_on_off.setVisibility(View.GONE);
                                                    bottom_sheet_request.setVisibility(View.GONE);
                                                    vUser_name = request_dto.getRequestdata().getFullName() != null ? request_dto.getRequestdata().getFullName() : "";
                                                    vEmail = request_dto.getRequestdata().getEmail() != null ? request_dto.getRequestdata().getEmail() : "";
                                                    vEmr_Num = request_dto.getRequestdata().getEmrNum() != null ? request_dto.getRequestdata().getEmrNum() : "";
                                                    vUser_Mobile = request_dto.getRequestdata().getPhone() != null ? request_dto.getRequestdata().getPhone() : "";
                                                    vLocation = request_dto.getRequestdata().getLocation() != null ? request_dto.getRequestdata().getLocation() : "";
                                                    vPickup_Type = request_dto.getRequestdata().getPickupType() != null ? request_dto.getRequestdata().getPickupType() : "";
                                                    vProperty_id = request_dto.getRequestdata().getPropertyId() != null ? request_dto.getRequestdata().getPropertyId() : "";
                                                    vReq_id = request_dto.getRequestdata().getAgId() != null ? request_dto.getRequestdata().getAgId() : "";
                                                    vUser_id = request_dto.getRequestdata().getUserId() != null ? request_dto.getRequestdata().getUserId() : "";
                                                    vUser_Image = request_dto.getRequestdata().getProfilepic() != null ? request_dto.getRequestdata().getProfilepic() : "";
                                                    vUser_lat = Double.parseDouble(request_dto.getRequestdata().getUserlat());
                                                    vUser_lng = Double.parseDouble(request_dto.getRequestdata().getUserlng());
                                                    vProperty_lat = Double.parseDouble(request_dto.getRequestdata().getLat());
                                                    vProperty_lng = Double.parseDouble(request_dto.getRequestdata().getLng());
                                                    sessionManager.setUser_id(vUser_id);

                                                    tv_user_name.setText(vUser_name);
                                                    if (vPickup_Type.equals("1")) {
                                                        mode_type = 1;
                                                        //  tv_method.setText("Method: Pick Up");
                                                        show_markers_locations(vUser_lat, vUser_lng, vProperty_id, vProperty_lat, vProperty_lng, mode_type);
                                                        if (GET_User_Location_service.get_user_location_service != null) {
                                                            Objects.requireNonNull(getActivity()).stopService(new Intent(getActivity(), GET_User_Location_service.class));
                                                        }

                                                        bottom_sheet_driver_pickup_arrived.setVisibility(View.VISIBLE);
                                                        /// bottom_sheet_driver_pickup_arrived///////
                                                        get_view_bottom_sheet_driver_pickup_arrived(vReq_id, vUser_name, vUser_Image, vUser_Mobile, vProperty_id, "dropoff_status");
                                                        txt_driving_pickup.setText("Dropping-off :");
                                                        btn_arrived.setText("DROP OFF COMPLETED");
                                                        prog_arrived.setProgress(100);
                                                        prog_property_loc.setProgress(100);
                                                        prog_drop_off.setProgress(50);
                                                    } else if (vPickup_Type.equals("2")) {
                                                        mode_type = 2;
                                                        //    tv_method.setText("Method: Meet Up");
                                                        sessionManager.set_user_app_details(vProperty_id, vProperty_lat, vProperty_lng, mode_type);
                                                        try {
//                                                            Objects.requireNonNull(getActivity()).startService(new Intent(getActivity(), GET_User_Location_service.class));
                                                            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                                                                getActivity().startForegroundService(new Intent(getActivity(), GET_User_Location_service.class));
                                                            } else {
                                                                getActivity().startService(new Intent(getActivity(), GET_User_Location_service.class));
                                                            }

                                                        } catch (Exception e) {
                                                            e.printStackTrace();
                                                        }
                                                        //    show_markers_locations(vUser_lat, vUser_lng, vProperty_id, vProperty_lat, vProperty_lng, mode_type);
                                                    }
                                                } else {
                                                    Toast.makeText(getActivity(), getResources().getString(R.string.internet_error), Toast.LENGTH_SHORT).show();
                                                }
                                            } catch (NumberFormatException e) {
                                                e.printStackTrace();
                                            }
                                            break;
                                        case "8":
                                            try {
                                                if (Utility.isConnectingToInternet(getActivity())) {
                                                    is_Request = true;
                                                    bottom_sheet_on_off.setVisibility(View.GONE);
                                                    bottom_sheet_request.setVisibility(View.GONE);
                                                    vUser_name = request_dto.getRequestdata().getFullName() != null ? request_dto.getRequestdata().getFullName() : "";
                                                    vEmail = request_dto.getRequestdata().getEmail() != null ? request_dto.getRequestdata().getEmail() : "";
                                                    vEmr_Num = request_dto.getRequestdata().getEmrNum() != null ? request_dto.getRequestdata().getEmrNum() : "";
                                                    vUser_Mobile = request_dto.getRequestdata().getPhone() != null ? request_dto.getRequestdata().getPhone() : "";
                                                    vLocation = request_dto.getRequestdata().getLocation() != null ? request_dto.getRequestdata().getLocation() : "";
                                                    vPickup_Type = request_dto.getRequestdata().getPickupType() != null ? request_dto.getRequestdata().getPickupType() : "";
                                                    vProperty_id = request_dto.getRequestdata().getPropertyId() != null ? request_dto.getRequestdata().getPropertyId() : "";
                                                    vReq_id = request_dto.getRequestdata().getAgId() != null ? request_dto.getRequestdata().getAgId() : "";
                                                    vUser_id = request_dto.getRequestdata().getUserId() != null ? request_dto.getRequestdata().getUserId() : "";
                                                    vUser_Image = request_dto.getRequestdata().getProfilepic() != null ? request_dto.getRequestdata().getProfilepic() : "";
                                                    vUser_lat = Double.parseDouble(request_dto.getRequestdata().getUserlat());
                                                    vUser_lng = Double.parseDouble(request_dto.getRequestdata().getUserlng());
                                                    vProperty_lat = Double.parseDouble(request_dto.getRequestdata().getLat());
                                                    vProperty_lng = Double.parseDouble(request_dto.getRequestdata().getLng());
                                                    sessionManager.setUser_id(vUser_id);
                                                    tv_user_name.setText(vUser_name);
                                                    if (vPickup_Type.equals("1")) {
                                                        mode_type = 1;
                                                        //  tv_method.setText("Method: Pick Up");
                                                        show_markers_locations(vUser_lat, vUser_lng, vProperty_id, vProperty_lat, vProperty_lng, mode_type);
                                                        if (GET_User_Location_service.get_user_location_service != null) {
                                                            Objects.requireNonNull(getActivity()).stopService(new Intent(getActivity(), GET_User_Location_service.class));
                                                        }

                                                        bottom_sheet_driver_pickup_arrived.setVisibility(View.VISIBLE);
                                                        /// bottom_sheet_driver_pickup_arrived///////
                                                        get_view_bottom_sheet_driver_pickup_arrived(vReq_id, vUser_name, vUser_Image, vUser_Mobile, vProperty_id, "complete_trip");
                                                        txt_driving_pickup.setText("Dropping-off :");
                                                        btn_arrived.setText("COMPLETE TRIP");
                                                        prog_arrived.setProgress(100);
                                                        prog_property_loc.setProgress(100);
                                                        prog_drop_off.setProgress(100);
                                                    } else if (vPickup_Type.equals("2")) {
                                                        mode_type = 2;
                                                        //    tv_method.setText("Method: Meet Up");
                                                        try {
                                                            if (GET_User_Location_service.get_user_location_service == null) {
//                                                                Objects.requireNonNull(getActivity()).startService(new Intent(getActivity(), GET_User_Location_service.class));
                                                                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                                                                    getActivity().startForegroundService(new Intent(getActivity(), GET_User_Location_service.class));
                                                                } else {
                                                                    getActivity().startService(new Intent(getActivity(), GET_User_Location_service.class));
                                                                }
                                                            }

                                                        } catch (Exception e) {
                                                            e.printStackTrace();
                                                        }
                                                    }
                                                } else {
                                                    Toast.makeText(getActivity(), getResources().getString(R.string.internet_error), Toast.LENGTH_SHORT).show();
                                                }
                                            } catch (NumberFormatException e) {
                                                e.printStackTrace();
                                            }
                                            break;
                                        case "9":
                                            try {
                                                if (Utility.isConnectingToInternet(getActivity())) {
                                                    SessionManager.IS_REQUEST = false;
                                                    is_Request = false;
                                                    bottom_sheet_driver_pickup_arrived.setVisibility(View.GONE);
                                                    if (GET_User_Location_service.get_user_location_service != null) {
                                                        GET_User_Location_service.get_user_location_service.stopSelf();
                                                        Objects.requireNonNull(getActivity()).stopService(new Intent(getActivity(), GET_User_Location_service.class));
                                                    }
                                                    if (getActivity() != null) {
                                                        getActivity().recreate();
                                                    }
                                                } else {
                                                    Toast.makeText(getActivity(), getResources().getString(R.string.internet_error), Toast.LENGTH_SHORT).show();
                                                }
                                            } catch (Exception e) {
                                                e.printStackTrace();
                                            }
                                            break;
                                        case "2":
                                            try {
                                                if (Utility.isConnectingToInternet(getActivity())) {
                                                    SessionManager.IS_REQUEST = false;
                                                    is_Request = false;
                                                    bottom_sheet_driver_pickup_arrived.setVisibility(View.GONE);
                                                    if (GET_User_Location_service.get_user_location_service != null) {
                                                        GET_User_Location_service.get_user_location_service.stopSelf();
                                                        Objects.requireNonNull(getActivity()).stopService(new Intent(getActivity(), GET_User_Location_service.class));
                                                        MainActivity mainActivity = (MainActivity) getActivity();
                                                        if (mainActivity != null) {
                                                            mainActivity.showCancelRequestDialog("2", "Request Canceled by User !", "");
                                                        }
                                                    }
                                                } else {
                                                    Toast.makeText(getActivity(), getResources().getString(R.string.internet_error), Toast.LENGTH_SHORT).show();
                                                }
                                            } catch (Exception e) {
                                                e.printStackTrace();
                                            }
                                            break;
                                    }
                                }
                            } else if (request_dto.getStatus() == 0) {
                                try {
                                    SessionManager.IS_REQUEST = false;
                                    is_Request = false;
                                    if (GET_User_Location_service.get_user_location_service != null) {
                                        GET_User_Location_service.get_user_location_service.stopSelf();
                                        Objects.requireNonNull(getActivity()).stopService(new Intent(getActivity(), GET_User_Location_service.class));
                                    }
                                    //                            if (mGoogleMap != null) {
                                    //                                mGoogleMap.clear();
                                    //                            }
                                    if (property_markers != null) {
                                        property_markers.remove();
                                    }
                                    if (Utility.isConnectingToInternet(getActivity())) {
                                        getPropertyListApi("2");
                                    } else {
                                        Toast.makeText(getActivity(), getString(R.string.internet_error), Toast.LENGTH_SHORT).show();
                                    }

                                    bottom_sheet_on_off.setVisibility(View.VISIBLE);
                                    bottom_sheet_request.setVisibility(View.GONE);
                                    bottom_sheet_driver_pickup_arrived.setVisibility(View.GONE);
                                } catch (Exception e) {
                                    e.printStackTrace();
                                }

                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }

                    @Override
                    public void onFailure(@NotNull Call<REQUEST_DTO> call, @NotNull Throwable t) {
                        Log.e("onFaliure..", "onFailure: ", t);
                    }
                });
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void show_markers_locations(double vUser_lat, double vUser_lng, String vProperty_id, double vProperty_lat, double vProperty_lng, int vMode) {
        try {
            if (vUser_lat != 0.0 || vUser_lng != 0.0) {
                if (user_marker != null) {
                    user_marker.remove();
                }
                Bitmap bitmap = BitmapFactory.decodeResource(getContext().getResources(), R.drawable.location_marker_green);
                ByteArrayOutputStream out = new ByteArrayOutputStream();
                bitmap.compress(Bitmap.CompressFormat.PNG, 50, out);

                //    Toast.makeText(getActivity(), vUser_lat + "  " + vUser_lng, Toast.LENGTH_SHORT).show();
                userLatLan = new LatLng(vUser_lat, vUser_lng);
                MarkerOptions markerOptions = new MarkerOptions();
                markerOptions.position(userLatLan);
                markerOptions.title("user_marker");
                markerOptions.anchor(0.2f, 0.2f);
                markerOptions.icon(BitmapDescriptorFactory.fromBitmap(bitmap));
                user_marker = mGoogleMap.addMarker(markerOptions);
            }

            getPropertyListApi("1");

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void getPropertyListApi(String vPro_marker_type) {
        try {
            Call<List<PROPERTY_MAIN_DTO>> property_dtoCall = apiInterfacse.getproperty("Basic c2ltcGx5cmV0czpzaW1wbHlyZXRz");
            property_dtoCall.enqueue(new Callback<List<PROPERTY_MAIN_DTO>>() {
                @Override
                public void onResponse(@NotNull Call<List<PROPERTY_MAIN_DTO>> call, @NotNull Response<List<PROPERTY_MAIN_DTO>> response) {

                    try {
                        if (response.isSuccessful()) {
                            List<PROPERTY_MAIN_DTO> property_main_dtos = response.body();
                            if (property_main_dtos.size() > 0) {
                                for (int i = 0; i < property_main_dtos.size(); i++) {
                                    property_main_dto = property_main_dtos.get(i);
                                    Geo geo_dto = property_main_dto.getGeo();
                                    System.out.println("fdefewfee...." + geo_dto.getLat() + "   " + geo_dto.getLng() + "   id......" + property_main_dto.getMlsId());
                                    //this  method show all the property on map
                                    if (vPro_marker_type.equals("1")) {

                                        if (vProperty_lat != 0.0 || vProperty_lng != 0.0) {
                                            if (ser_property_marker != null) {
                                                ser_property_marker.remove();
                                            }
                                            sev_pro_latlng = new LatLng(22.761958, 75.896382);
                                            //    sev_pro_latlng = new LatLng(vProperty_lat, vProperty_lng);
                                            MarkerOptions markerOptions = new MarkerOptions();
                                            markerOptions.position(sev_pro_latlng);
                                            markerOptions.title("ser_property_marker");
                                            markerOptions.snippet(vProperty_id);
                                            markerOptions.anchor(0.5f, 0.5f);
                                            markerOptions.icon(BitmapDescriptorFactory.fromResource(R.drawable.home_with_ripple));
                                            ser_property_marker = mGoogleMap.addMarker(markerOptions);

                                            if (sev_pro_latlng != null) {
                                                Handler handler = new Handler();
                                                handler.postDelayed(
                                                        new Runnable() {
                                                            public void run() {
                                                                //     ripple_start_handle();
                                                            }
                                                        }, 3000L);
                                            }
                                        }
                                        mGoogleMap.setOnMarkerClickListener(new GoogleMap.OnMarkerClickListener() {
                                            @Override
                                            public boolean onMarkerClick(final Marker marker) {
                                                try {
                                                    if (marker.getTitle().equalsIgnoreCase("ser_property_marker")) {
                                                        System.out.println("misid.........." + marker.getSnippet());

                                                        if (Utility.isConnectingToInternet(getActivity())) {
                                                            show_full_propety_details_dailoge(property_main_dtos, Integer.valueOf(marker.getSnippet()));
                                                        } else {
                                                            Toast.makeText(getActivity(), getResources().getString(R.string.internet_error), Toast.LENGTH_SHORT).show();
                                                        }
                                                    }
                                                } catch (NumberFormatException | Resources.NotFoundException e) {
                                                    e.printStackTrace();
                                                }
                                                return true;
                                            }
                                        });
                                        tv_property_infermation.setOnClickListener(new View.OnClickListener() {
                                            @Override
                                            public void onClick(View view) {
                                                if (Utility.isConnectingToInternet(getActivity())) {
                                                    show_full_propety_details_dailoge(property_main_dtos, Integer.valueOf(vProperty_id));
                                                } else {
                                                    Toast.makeText(getActivity(), getResources().getString(R.string.internet_error), Toast.LENGTH_SHORT).show();
                                                }
                                            }
                                        });
                                    } else if (vPro_marker_type.equals("2")) {
                                        Show_property_markers_on_map(property_main_dtos.get(i).getGeo().getLat(), property_main_dtos.get(i).getGeo().getLng(), property_main_dtos, property_main_dtos.get(i).getMlsId());
                                    }

                                }
                            }
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }

                @Override
                public void onFailure(@NotNull Call<List<PROPERTY_MAIN_DTO>> call, @NotNull Throwable t) {
                    Log.e("onFailure", "onFailure " + t.getMessage());
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    private void Show_property_markers_on_map(Double lat, Double lng, final List<PROPERTY_MAIN_DTO> property_main_dtos, Integer mlsId) {
        try {
            if ((MainActivity) getActivity() != null) {
                Bitmap bitmap = BitmapFactory.decodeResource(getActivity().getResources(), R.drawable.house);
                ByteArrayOutputStream out = new ByteArrayOutputStream();
                bitmap.compress(Bitmap.CompressFormat.PNG, 50, out);
                propertyLatLng = new LatLng(lat, lng);
                MarkerOptions markerOptions = new MarkerOptions();
                markerOptions.position(propertyLatLng);
                markerOptions.title("Property_Marker");
                markerOptions.snippet(String.valueOf(mlsId));
                markerOptions.icon(BitmapDescriptorFactory.fromBitmap(bitmap));
                property_markers = mGoogleMap.addMarker(markerOptions);
                //        mGoogleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(userLatLng, 12));

                mGoogleMap.setOnMarkerClickListener(new GoogleMap.OnMarkerClickListener() {
                    @Override
                    public boolean onMarkerClick(final Marker marker) {
                        if (marker.getTitle().equalsIgnoreCase("Property_Marker")) {
                            System.out.println("misid.........." + marker.getSnippet());

                            if (Utility.isConnectingToInternet(getActivity())) {
                                show_full_propety_details_dailoge(property_main_dtos, Integer.valueOf(marker.getSnippet()));
                            } else {
                                Toast.makeText(getActivity(), getResources().getString(R.string.internet_error), Toast.LENGTH_SHORT).show();
                            }
                        }
                        return true;
                    }
                });
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    @Override
    public void onMapReady(GoogleMap googleMap) {

        try {
            if (mGoogleMap != null) {
                mGoogleMap.clear();
            }
            mGoogleMap = googleMap;
            mGoogleMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);
            mGoogleMap.isMyLocationEnabled();
            mLocationRequest = new LocationRequest();
            mLocationRequest.setInterval(1000); // two minute interval
            mLocationRequest.setFastestInterval(1000);
            mLocationRequest.setPriority(LocationRequest.PRIORITY_BALANCED_POWER_ACCURACY);
            final int padding_100dp = (int) (480 * scale + 0.5f);

            mGoogleMap.getUiSettings().setMapToolbarEnabled(true);

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                if (ContextCompat.checkSelfPermission(Objects.requireNonNull(getActivity()), Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
                    getActivity().startService(new Intent(getActivity(), UpdateLocationService.class));
                    //Location Permission already granted
                    mFusedLocationClient.requestLocationUpdates(mLocationRequest, mLocationCallback, Looper.myLooper());
                    mGoogleMap.setMyLocationEnabled(false);
                } else {
                    //Request Location Permission
                    checkLocationPermission();
                }
            } else {
                mFusedLocationClient.requestLocationUpdates(mLocationRequest, mLocationCallback, Looper.myLooper());
                mGoogleMap.setMyLocationEnabled(false);
                Objects.requireNonNull(getActivity()).startService(new Intent(getActivity(), UpdateLocationService.class));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    private LocationCallback mLocationCallback = new LocationCallback() {

        @Override
        public void onLocationResult(LocationResult locationResult) {

            try {
                List<Location> locationList = locationResult.getLocations();
                if (locationList.size() > 0) {
                    //The last location in the list is the newest
                    Location location = locationList.get(locationList.size() - 1);
                    Bitmap bitmap = BitmapFactory.decodeResource(Objects.requireNonNull(getActivity()).getResources(), R.drawable.location_marker_red);
                    ByteArrayOutputStream out = new ByteArrayOutputStream();
                    bitmap.compress(Bitmap.CompressFormat.PNG, 50, out);


                    if (is_Request) {
                        driverLatLng = new LatLng(location.getLatitude(), location.getLongitude());
                        MarkerOptions markerOptions = new MarkerOptions();
                        markerOptions.position(driverLatLng);
                        markerOptions.title(getResources().getString(R.string.Driver_Position));
                        markerOptions.flat(true);
                        markerOptions.icon(BitmapDescriptorFactory.fromBitmap(bitmap));
                        //mGoogleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(driverLatLng, 22));
                        if (driverMarker != null) {
                            driverMarker.remove();
                        }
                        driverMarker = mGoogleMap.addMarker(markerOptions);

                        if (mode_type == 1) {
                            boundMapOnCenter_3(driverMarker, ser_property_marker, user_marker);
                            //animateMarker(mGoogleMap, driverMarker, driverLatLng, false);
                        } else if (mode_type == 2) {
                            boundMapOnCenter(driverMarker, ser_property_marker);
                            //animateMarker(mGoogleMap, driverMarker, driverLatLng, false);
                        }

                        String url = "";
                        FetchUrl FetchUrl = new FetchUrl();
                        // Getting URL to the Google Directions API
                        if (driverLatLng != null && sev_pro_latlng != null && userLatLan != null)
                            url = getUrl(driverLatLng, sev_pro_latlng, userLatLan, mode_type);
                        Log.d("onMapClick", url.toString());
                        // Start downloading json data from Google Directions API
                        FetchUrl.execute(url);
                    } else {
                        if (driverMarker != null) {
                            driverMarker.remove();
                        }
                        driverLatLng = new LatLng(location.getLatitude(), location.getLongitude());
                        MarkerOptions markerOptions = new MarkerOptions();
                        markerOptions.position(driverLatLng);
                        markerOptions.title(getResources().getString(R.string.Driver_Position));
                        markerOptions.flat(true);
                        markerOptions.icon(BitmapDescriptorFactory.fromBitmap(bitmap));
                        mGoogleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(driverLatLng, 16));
                        driverMarker = mGoogleMap.addMarker(markerOptions);
                        getAddress(driverLatLng.latitude, driverLatLng.longitude);


                        img_focus_btn.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                CameraUpdate cameraUpdate = CameraUpdateFactory.newLatLngZoom(driverLatLng, 16);
                                mGoogleMap.animateCamera(cameraUpdate);
                                getAddress(driverLatLng.latitude, driverLatLng.longitude);
                            }
                        });
                    }
                }
            } catch (Exception ignored) {
            }
        }
    };

    private void getAddress(double latitude, double longitude) {
        Geocoder geocoder;
        List<android.location.Address> addresses;
        geocoder = new Geocoder(getActivity(), Locale.getDefault());

        Log.e("latitude", "latitude--" + latitude);

        try {
            Log.e("latitude", "inside latitude--" + latitude);
            addresses = geocoder.getFromLocation(latitude, longitude, 1);

            if (addresses != null && addresses.size() > 0) {
                user_picker_address = addresses.get(0).getAddressLine(0);
                String city = addresses.get(0).getLocality();
                String state = addresses.get(0).getAdminArea();
                String country = addresses.get(0).getCountryName();
                String postalCode = addresses.get(0).getPostalCode();
                String knownName = addresses.get(0).getFeatureName();
            }
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    private void ripple_start_handle() {
        try {
            MapRipple mapRipple = new MapRipple(mGoogleMap, sev_pro_latlng, Objects.requireNonNull(getActivity()));
            mapRipple.withNumberOfRipples(3);
            mapRipple.withFillColor(getResources().getColor(R.color.green));
            mapRipple.withStrokeColor(Color.BLACK);
            mapRipple.withStrokewidth(20);    // 10dp
            mapRipple.withDistance(400); // 2000 metres radius
            mapRipple.withRippleDuration(12000);    //12000ms
            mapRipple.withTransparency(0.6f);
            mapRipple.startRippleMapAnimation();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    ////////////////////Map routes//////////////

    // show route from customer's source location to customer's destination location...
//    public void getRouteFromSourceToDestination(final LatLng source_point, final LatLng property, final LatLng dest_point) {
//        Routing routing = new Routing.Builder()
//                .travelMode(AbstractRouting.TravelMode.DRIVING)
//                .waypoints(source_point, dest_point, property)
//                .withListener(new RoutingListener() {
//                    @Override
//                    public void onRoutingFailure(RouteException e) {
//                        // progressDialog.dismiss();
//                        if (e != null) {
//                            Toast.makeText(getActivity(), "Error: " + e.getMessage(), Toast.LENGTH_LONG).show();
//                        } else {
//                            Toast.makeText(getActivity(), "something_wrong", Toast.LENGTH_SHORT).show();
//                        }
//                    }
//
//                    @Override
//                    public void onRoutingStart() {
//                        Log.e("check", "onRoutingStart");
//
//                    }
//
//                    @Override
//                    public void onRoutingSuccess(ArrayList<Route> route, int i) {
//
//                        List<Polyline> polylines = new ArrayList<>();
//                        if (polylines.size() > 0) {
//                            for (Polyline poly : polylines) {
//                                poly.remove();
//                            }
//                        }
//                        polylines = new ArrayList<>();
//                        try {
//                            System.out.println("route..." + route);
//
//                            if (getActivity() != null) {
//                                String time = route.get(0).getDurationText();
//
//                                String[] arrOfStr = time.split(" ");
//
//                                String fist_index = arrOfStr[0];
//                                String second_index = arrOfStr[1];
//                                if (second_index.equalsIgnoreCase("mins") || second_index.equalsIgnoreCase("min")) {
//                                    //tv_time_arrive.setText(fist_index + " " + getResources().getString(R.string.mints) + " " + getResources().getString(R.string.minutes_to_arrive));
//                                    Toast.makeText(getActivity(), fist_index + " " + getResources().getString(R.string.mints) + " " + getResources().getString(R.string.minutes_to_arrive), Toast.LENGTH_SHORT).show();
//                                } else if (second_index.equalsIgnoreCase("hr") || second_index.equalsIgnoreCase("hrs")) {
//                                    //tv_time_arrive.setText(fist_index + " " + getResources().getString(R.string.hrs) + " " + getResources().getString(R.string.minutes_to_arrive));
//                                    Toast.makeText(getActivity(), fist_index + " " + getResources().getString(R.string.hrs) + " " + getResources().getString(R.string.minutes_to_arrive), Toast.LENGTH_SHORT).show();
//
//                                } else {
//                                    // tv_time_arrive.setText(fist_index + " " + second_index + " " + getResources().getString(R.string.minutes_to_arrive));
//                                    Toast.makeText(getActivity(), fist_index + " " + second_index + " " + getResources().getString(R.string.minutes_to_arrive), Toast.LENGTH_SHORT).show();
//                                }
//                            }
//
//                            //add route(s) to the map.
//                            for (int j = 0; j < route.size(); j++) {
//
//                                //In case of more than 5 alternative routes
//
//                                PolylineOptions polyOptions = new PolylineOptions();
//                                polyOptions.color(getResources().getColor(R.color.colorBlack));
//                                polyOptions.width(10 + j * 3);
//                                polyOptions.addAll(route.get(j).getPoints());
//                                Polyline polyline = mGoogleMap.addPolyline(polyOptions);
//                                polylines.add(polyline);
//                            }
//
//
//                            //     mGoogleMap.addPolyline(new PolylineOptions().addAll(route.get(0).getPoints()).width(10 + 0 * 2).color(getResources().getColor(R.color.colorBlack)).geodesic(true));
//                        } catch (Exception e) {
//                            e.printStackTrace();
//                        }
//                    }
//
//                    @Override
//                    public void onRoutingCancelled() {
//                        Log.e("check", "onRoutingCancelled");
//
//                    }
//                })
//                .alternativeRoutes(true)
//                .waypoints(source_point, dest_point)
//                .key("AIzaSyAv0CGME54AwtcjEIsW5iE0-jQfSfZYe4c")
//                .build();
//        routing.execute();
//    }

    private String getUrl(LatLng Agent_location, LatLng Property_location, LatLng userLatLan, int vPickup_type) {
        String url = null;
        try {
            // Waypoints
            String waypoints = "";
            waypoints = "waypoints=";

            /////// waya user location
            if (userLatLan != null)
                if (userLatLan.latitude != 0.0 || userLatLan.longitude != 0.0) {
                    waypoints += userLatLan.latitude + "," + userLatLan.longitude + "|";
                }
            // Origin of route
//                String str_user_location = "origin=" + userLatLan.latitude + "," + userLatLan.longitude;

            // Origin of route
            String str_agent_location = "origin=" + Agent_location.latitude + "," + Agent_location.longitude;

            // Destination of route
            String str_property_location = "destination=" + Property_location.latitude + "," + Property_location.longitude;

            String mode = "mode=driving";

            // Sensor enabled
            String sensor = "sensor=false";
            //   String params = waypoints + "&" + sensor;


//            String key = "key=" + "AIzaSyAv0CGME54AwtcjEIsW5iE0-jQfSfZYe4c";
//            String key = "key=" + "AIzaSyCxMCSVK_QzXdpQTY034oaC-arO-C4U_sQ";
            String key = "key=" + "AIzaSyAyLtolI46f77ebinBi8syGuJ0y5YfWozg";
            // Output format
            String output = "json";
            String parameters = "";
            String url2 = "";
            ////////pickup mode//////
            if (vPickup_type == 1) {
                parameters = str_agent_location + "&" + str_property_location + "&" + sensor + "&" + waypoints + "&" + mode + "alternatives=true" + "&" + key;
            } else if (vPickup_type == 2) { ////// meetup mode///////
                // Building the parameters to the web service
                parameters = str_agent_location + "&" + str_property_location + "&" + sensor + "&" + mode + "&" + key;
                url2 = getUrl2(userLatLan, sev_pro_latlng);
                FetchUrl FetchUrl = new FetchUrl();
                FetchUrl.execute(url2);

            }
            // String parameters = params + "&" + key;

            // Building the url to the web service
            url = "https://maps.googleapis.com/maps/api/directions/" + output + "?" + parameters;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return url;
    }

    String url2 = "";

    private String getUrl2(LatLng userLatLan, LatLng Property_location) {

        try {
            // Origin of route
            String str_user_location = "origin=" + userLatLan.latitude + "," + userLatLan.longitude;

            // Destination of route
            String str_property_location = "destination=" + Property_location.latitude + "," + Property_location.longitude;

            String mode = "mode=driving";

            // Sensor enabled
            String sensor = "sensor=false";
            //   String params = waypoints + "&" + sensor;


//            String key = "key=" + "AIzaSyAv0CGME54AwtcjEIsW5iE0-jQfSfZYe4c";
            String key = "key=" + "AIzaSyAyLtolI46f77ebinBi8syGuJ0y5YfWozg";
            // Output format
            String output = "json";
            String parameters = "";


            parameters = str_user_location + "&" + str_property_location + "&" + sensor + "&" + mode + "&" + key;
            // String parameters = params + "&" + key;

            // Building the url to the web service
            url2 = "https://maps.googleapis.com/maps/api/directions/" + output + "?" + parameters;

        } catch (Exception e) {
            e.printStackTrace();
        }
        return url2;
    }

    private void showTime(String distance, String time) {
        try {
            if (tv_total_distance != null && tv_total_time != null)
                if (distance != null && time != null) {
                    tv_total_distance.setText(distance);
                    tv_total_time.setText(time);
                }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void animateMarker(final GoogleMap map, final Marker marker, final LatLng toPosition, final boolean hideMarker) {
        final Handler handler = new Handler();
        final long start = SystemClock.uptimeMillis();
        Projection proj = map.getProjection();
        Point startPoint = proj.toScreenLocation(marker.getPosition());
        final LatLng startLatLng = proj.fromScreenLocation(startPoint);
        final long duration = 400;

        final Interpolator interpolator = new LinearInterpolator();

        handler.post(new Runnable() {
            @Override
            public void run() {
                long elapsed = SystemClock.uptimeMillis() - start;
                float t = interpolator.getInterpolation((float) elapsed / duration);
                double lng = t * toPosition.longitude + (1 - t) * startLatLng.longitude;
                double lat = t * toPosition.latitude + (1 - t) * startLatLng.latitude;

                marker.setPosition(new LatLng(lat, lng));

                if (t < 1.0) {
                    // Post again 16ms later.
                    handler.postDelayed(this, 16);
                } else {
                    if (hideMarker) {
                        marker.setVisible(false);
                    } else {
                        marker.setVisible(true);
                    }
                }
            }
        });
    }


    // Fetches data from url passed
    private class FetchUrl extends AsyncTask<String, Void, String> {
        @Override
        protected String doInBackground(String... url) {
            String data = "";
            try {
                HttpConnection http = new HttpConnection();
                data = http.readUrl(url[0]);
            } catch (Exception e) {
                Log.d("Background Task", e.toString());
            }
            return data;
        }

        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);
            try {
                if (result != null) {
                    if (is_Request) {
                        new ParserTask(new ParserCallBack() {
                            @Override
                            public void getroot(List<List<HashMap<String, String>>> routes) {
                                ArrayList<LatLng> points = null;
                                PolylineOptions polyLineOptions = null;
                                String distance = "";
                                String duration = "";
                                try {
                                    if (polylines.size() > 0) {
                                        for (Polyline poly : polylines) {
                                            poly.remove();
                                        }
                                    }
                                    polylines = new ArrayList<>();
                                    // traversing through routes
                                    if (routes != null) {
                                        if (!routes.isEmpty()) {
                                            routes.size();
                                            for (int i = 0; i < routes.size(); i++) {

                                                points = new ArrayList<LatLng>();
                                                polyLineOptions = new PolylineOptions();
                                                List<HashMap<String, String>> path = routes.get(i);
                                                try {
                                                    // Fetching all the points in i-th route
                                                    for (int j = 0; j < path.size(); j++) {
                                                        HashMap<String, String> point = path.get(j);

                                                        if (j == 0) {    // Get distance from the list
                                                            distance = (String) point.get("distance");
                                                            continue;
                                                        } else if (j == 1) { // Get duration from the list
                                                            duration = (String) point.get("duration");
                                                            continue;
                                                        }

                                                        if (point.get("lat") != null || point.get("lng") != null) {
                                                            double lat = Double.parseDouble(point.get("lat"));
                                                            double lng = Double.parseDouble(point.get("lng"));
                                                            LatLng position = new LatLng(lat, lng);
                                                            points.add(position);
                                                        }
                                                    }

                                                    showTime(distance, duration);
                                                } catch (Exception e) {
                                                    e.printStackTrace();
                                                }

                                                polyLineOptions.addAll(points);

                                            }
                                            polyLineOptions = new PolylineOptions();
                                            polyLineOptions.color(Color.BLACK);
                                            polyLineOptions.width(6);
                                            polyLineOptions.startCap(new SquareCap());
                                            polyLineOptions.endCap(new SquareCap());
                                            polyLineOptions.jointType(ROUND);
                                            polyLineOptions.addAll(points);

                                        }
                                        if (polyLineOptions != null) {
                                            polyline = mGoogleMap.addPolyline(polyLineOptions);
                                            polylines.add(polyline);
                                        }

                                    }

                                } catch (NumberFormatException e) {
                                    e.printStackTrace();
                                }
                            }
                        }).execute(result);
                    } else {
                        MainActivity mainActivity = (MainActivity) getActivity();
                        if (mainActivity != null)
                            mainActivity.recreate();
                    }

                }

            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }


    private void show_full_propety_details_dailoge(List<PROPERTY_MAIN_DTO> property_main_dtos, Integer mlsId) {
        LayoutInflater inflater = (LayoutInflater) Objects.requireNonNull(getActivity()).getSystemService(LAYOUT_INFLATER_SERVICE);
        View popupView = inflater.inflate(R.layout.property_details_dialog, null);
        // create the popup window
        int width = LinearLayout.LayoutParams.MATCH_PARENT;
        int height = LinearLayout.LayoutParams.MATCH_PARENT;
        boolean focusable = true; // lets taps outside the popup also dismiss it
        popupWindow1 = new PopupWindow(popupView, width, height, focusable);

        // show the popup window
        // which view you pass in doesn't matter, it is only used for the window tolken
        popupWindow1.showAtLocation(view, Gravity.CENTER, 0, 0);
        final SliderView img_property;
        TextView tv_amount, tv_address, btn_dropoff_, tv_proerty_type, tv_garage_type,
                tv_bed_rooms, tv_area_cost, tv_bath_rooms, tv_year, tv_total_area, tv_days,
                txt_home_details, tv_home_details;
        Button btn_dismiss;
// Find views
        tv_amount = popupView.findViewById(R.id.tv_amount);
        tv_address = popupView.findViewById(R.id.tv_address);
        btn_dropoff_ = popupView.findViewById(R.id.btn_dropoff_);
        tv_proerty_type = popupView.findViewById(R.id.tv_proerty_type);
        tv_garage_type = popupView.findViewById(R.id.tv_garage_type);
        tv_bed_rooms = popupView.findViewById(R.id.tv_bed_rooms);
        tv_area_cost = popupView.findViewById(R.id.tv_area_cost);
        tv_bath_rooms = popupView.findViewById(R.id.tv_bath_rooms);
        tv_year = popupView.findViewById(R.id.tv_year);
        tv_total_area = popupView.findViewById(R.id.tv_total_area);
        tv_days = popupView.findViewById(R.id.tv_days);
        txt_home_details = popupView.findViewById(R.id.txt_home_details);
        tv_home_details = popupView.findViewById(R.id.tv_home_details);
        btn_dismiss = popupView.findViewById(R.id.btn_dismiss);
        img_property = popupView.findViewById(R.id.img_property);

// Type face
        tv_amount.setTypeface(TypeFactory.getInstance(getContext()).PoppinsBold());
        tv_address.setTypeface(TypeFactory.getInstance(getContext()).PoppinsSemiBold());
        tv_proerty_type.setTypeface(TypeFactory.getInstance(getContext()).PoppinsMedium());
        tv_garage_type.setTypeface(TypeFactory.getInstance(getContext()).PoppinsMedium());
        tv_garage_type.setTypeface(TypeFactory.getInstance(getContext()).PoppinsMedium());
        tv_bed_rooms.setTypeface(TypeFactory.getInstance(getContext()).PoppinsMedium());
        tv_bath_rooms.setTypeface(TypeFactory.getInstance(getContext()).PoppinsMedium());
        tv_area_cost.setTypeface(TypeFactory.getInstance(getContext()).PoppinsMedium());
        tv_year.setTypeface(TypeFactory.getInstance(getContext()).PoppinsMedium());
        tv_total_area.setTypeface(TypeFactory.getInstance(getContext()).PoppinsMedium());
        tv_days.setTypeface(TypeFactory.getInstance(getContext()).PoppinsMedium());
        txt_home_details.setTypeface(TypeFactory.getInstance(getContext()).PoppinsSemiBold());
        tv_home_details.setTypeface(TypeFactory.getInstance(getContext()).PoppinsMedium());
        btn_dropoff_.setTypeface(TypeFactory.getInstance(getContext()).PoppinsBold());
        btn_dismiss.setTypeface(TypeFactory.getInstance(getContext()).PoppinsBold());

        for (int i = 0; i < property_main_dtos.size(); i++) {
            if (property_main_dtos.get(i).getMlsId().equals(mlsId)) {
                //   vPropertyId_maker = property_main_dtos.get(i).getMlsId();
                if (property_main_dtos.get(i).getAddress() != null) {
                    address_dto = property_main_dtos.get(i).getAddress();
                }
                if (property_main_dtos.get(i).getProperty() != null) {
                    property_dto = property_main_dtos.get(i).getProperty();
                }
                if (property_main_dtos.get(i).getMls() != null) {
                    Mls mls = property_main_dtos.get(i).getMls();
                    tv_days.setText(mls != null ? mls.getDaysOnMarket() + " days" : " ");

                }
                if (property_main_dtos.get(i).getPhotos().size() > 0) {
                    for (int j = 0; j < property_main_dtos.get(i).getPhotos().size(); j++) {
                        final SliderAdapter adapter = new SliderAdapter(getActivity(), property_main_dtos.get(i).getPhotos(), property_main_dtos, mlsId);
                        adapter.setCount(property_main_dtos.get(i).getPhotos().size());
                        img_property.setSliderAdapter(adapter);
                        img_property.setIndicatorAnimation(IndicatorAnimations.SLIDE); //set indicator animation by using SliderLayout.IndicatorAnimations. :WORM or THIN_WORM or COLOR or DROP or FILL or NONE or SCALE or SCALE_DOWN or SLIDE and SWAP!!
                        img_property.setSliderTransformAnimation(SliderAnimations.CUBEINROTATIONTRANSFORMATION);
                        img_property.setAutoCycleDirection(SliderView.AUTO_CYCLE_DIRECTION_RIGHT);
                        img_property.setIndicatorSelectedColor(Color.WHITE);
                        img_property.setIndicatorUnselectedColor(Color.GRAY);
                        img_property.startAutoCycle();

                        img_property.setOnIndicatorClickListener(new DrawController.ClickListener() {
                            @Override
                            public void onIndicatorClicked(int position) {
                                img_property.setCurrentPagePosition(position);
                            }
                        });
                    }
                }
                tv_bed_rooms.setText(property_dto != null ? property_dto.getBedrooms() + " BED" : " ");
                tv_bath_rooms.setText(property_dto != null ? property_dto.getBathsFull() + " BATH" : " ");
                tv_year.setText(property_dto != null ? property_dto.getYearBuilt() + "" : " ");
                //    tv_proerty_type.setText(property_dto != null ? property_dto.getBathsFull() + " BATH" : " ");
                //       tv_garage_type.setText(property_dto != null ? property_dto.getBathsFull() + " BATH" : " ");
                //  tv_area_cost.setText(property_dto != null ? property_dto.getBathsFull() + " " : " ");
                tv_total_area.setText(property_dto != null ? property_dto.getArea() + " sqft" : " ");
                tv_address.setText(address_dto != null ? address_dto.getFull() + ", " + address_dto.getCity() + ", " + address_dto.getState() + ", " + address_dto.getCountry() + ", "
                        + address_dto.getPostalCode() : " ");
                tv_home_details.setText(property_dto != null ? "Laundry Features :- " + property_dto.getLaundryFeatures() + "\n" + "Interior Features :- "
                        + property_dto.getInteriorFeatures() + "\n" + "Exterior Features :- " + property_dto.getExteriorFeatures()
                        + "\n" + "Additional Rooms :- " + property_dto.getAdditionalRooms() + "\n" + "Construction :- " + property_dto.getConstruction() : " ");
                int abc = property_main_dtos.get(i).getListPrice();
                tv_amount.setText("$ " + abc + "");
                break;
            }
        }


        //Click events
        btn_dismiss.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                popupWindow1.dismiss();
            }
        });
        btn_dropoff_.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                popupWindow1.dismiss();
            }
        });


    }

    private void Accept_Request_api() {
        final Call<ACCEPT_REQUEST_DTO> accept_request_dtoCall = apiInterface.accept_request_api(vReq_id, "3", date, timezone, user_picker_address);
        accept_request_dtoCall.enqueue(new Callback<ACCEPT_REQUEST_DTO>() {
            @Override
            public void onResponse(@NotNull Call<ACCEPT_REQUEST_DTO> call, @NotNull Response<ACCEPT_REQUEST_DTO> response) {
                ACCEPT_REQUEST_DTO accept_request_dto = response.body();
                if (accept_request_dto == null)
                    return;
                if (accept_request_dto.getResponse()) {
                    MainActivity mainActivity = (MainActivity) getActivity();
                    if (mainActivity != null)
                        mainActivity.displayView(0);
                } else {
                    Toast.makeText(getActivity(), !accept_request_dto.getMessage().isEmpty() ? accept_request_dto.getMessage() : "", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(@NotNull Call<ACCEPT_REQUEST_DTO> call, @NotNull Throwable t) {
                Log.e("onFaliure..", "onFailure: ", t);
            }
        });
    }

    private void Agent_status_api() {
        final Call<REFRESH_AGENT_STATUS_DTO> refresh_agent_status_dtoCall = apiInterface.refresh_agent_status(sessionManager.get_Agent_id());
        refresh_agent_status_dtoCall.enqueue(new Callback<REFRESH_AGENT_STATUS_DTO>() {
            @Override
            public void onResponse(@NotNull Call<REFRESH_AGENT_STATUS_DTO> call, @NotNull Response<REFRESH_AGENT_STATUS_DTO> response) {
                REFRESH_AGENT_STATUS_DTO refresh_agent_status_dto = response.body();
                vStatus = refresh_agent_status_dto.getOnlineStatus();
                if (refresh_agent_status_dto.getResponse().equals(true)) {
                    if (vStatus != null) {
                        if (vStatus.equals("1")) {
                            online_container.setVisibility(View.GONE);
                            offline_container.setVisibility(View.VISIBLE);
                            statusOnlineTv.setText("You're Online");
                        } else if (vStatus.equals("0")) {
                            offline_container.setVisibility(View.GONE);
                            online_container.setVisibility(View.VISIBLE);
                            statusOnlineTv.setText("You're Offline");
                        }
                    }
                } else {
                    Toast.makeText(getActivity(), !refresh_agent_status_dto.getMessage().isEmpty() ? refresh_agent_status_dto.getMessage() : "", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(@NotNull Call<REFRESH_AGENT_STATUS_DTO> call, @NotNull Throwable t) {
                Log.e("onFaliure..", "onFailure: ", t);
            }
        });
    }

    private void Current_Status_API() {
        final Call<AGENT_STATUS_DTO> agent_status_dtoCall = apiInterface.agent_current_status(sessionManager.get_Agent_id(), Integer.parseInt(vStatus));
        agent_status_dtoCall.enqueue(new Callback<AGENT_STATUS_DTO>() {
            @Override
            public void onResponse(@NotNull Call<AGENT_STATUS_DTO> call, @NotNull Response<AGENT_STATUS_DTO> response) {
                AGENT_STATUS_DTO agent_status_dto = response.body();
                if (agent_status_dto == null)
                    return;

                if (agent_status_dto.getStatus() == 1) {
                    Toast.makeText(getActivity(), agent_status_dto.getMessage(), Toast.LENGTH_SHORT).show();
                }

            }

            @Override
            public void onFailure(@NotNull Call<AGENT_STATUS_DTO> call, @NotNull Throwable t) {
                Log.e("onFailure", " " + t.getMessage());
            }
        });
    }

    private void cancle_request_api(String req_id, String comment) {

        final Call<CANCLE_REQUEST_DTO> cancle_request_dtoCall = apiInterface.cancle_request_api(req_id, comment, 2, "Agent", date, timezone, user_picker_address);
        cancle_request_dtoCall.enqueue(new Callback<CANCLE_REQUEST_DTO>() {
            @Override
            public void onResponse(@NotNull Call<CANCLE_REQUEST_DTO> call, @NotNull Response<CANCLE_REQUEST_DTO> response) {
                CANCLE_REQUEST_DTO cancle_request_dto = response.body();
                if (cancle_request_dto == null)
                    return;
                if (cancle_request_dto.getResponse()) {
                    //    bottom_sheet_timer.setVisibility(View.GONE);
                    if (getActivity() != null) {
                        MainActivity mainActivity = (MainActivity) getActivity();
                        if (mainActivity != null)
                            mainActivity.recreate();
                        Toast.makeText(getActivity(), cancle_request_dto.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(getActivity(), cancle_request_dto.getMessage(), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(@NotNull Call<CANCLE_REQUEST_DTO> call, @NotNull Throwable t) {
                Log.e("onFaliure..", "onFailure: ", t);

            }
        });
    }

    private void cancle_request_dialoge(String vReq_id, String comment) {
        final AlertDialog.Builder builder = new AlertDialog.Builder(Objects.requireNonNull(getActivity()));
        builder.setMessage("Are you sure cancel this request ?")
                .setCancelable(false)
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    public void onClick(final DialogInterface dialog, final int id) {
                        if (Utility.isConnectingToInternet(getActivity())) {
                            cancle_request_api(vReq_id, comment);
                        } else {
                            Toast.makeText(getActivity(), "Please connect internet !", Toast.LENGTH_SHORT).show();
                        }
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

    private void boundMapOnCenter(Marker marker1, Marker marker2) {
        try {
            if (markers == null) {
                markers = new ArrayList<>();
            }
            markers.clear();
            // markers.add(currentLocationMarker);
            markers.add(marker1);
            markers.add(marker2);
            LatLngBounds.Builder builder = new LatLngBounds.Builder();
            for (Marker marker : markers) {
                builder.include(marker.getPosition());
            }
            LatLngBounds bounds = builder.build();
            int width = getResources().getDisplayMetrics().widthPixels;
            int padding = (int) (width * 0.20); //
            CameraUpdate cu = CameraUpdateFactory.newLatLngBounds(bounds, padding);
            mGoogleMap.animateCamera(cu);
        } catch (Exception ignored) {

        }
    }

    private void boundMapOnCenter_3(Marker marker1, Marker marker2, Marker marker3) {
        try {
            if (markers == null) {
                markers = new ArrayList<>();
            }
            markers.clear();
            // markers.add(currentLocationMarker);
            markers.add(marker1);
            markers.add(marker2);
            markers.add(marker3);
            LatLngBounds.Builder builder = new LatLngBounds.Builder();
            for (Marker marker : markers) {
                builder.include(marker.getPosition());
            }
            LatLngBounds bounds = builder.build();
            int width = getResources().getDisplayMetrics().widthPixels;
            int padding = (int) (width * 0.20); //
            CameraUpdate cu = CameraUpdateFactory.newLatLngBounds(bounds, padding);
            mGoogleMap.animateCamera(cu);
        } catch (Exception ignored) {

        }
    }

    private void checkLocationPermission() {
        if (ContextCompat.checkSelfPermission(Objects.requireNonNull(getActivity()), Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {
            // Should we show an explanation?
            if (ActivityCompat.shouldShowRequestPermissionRationale(getActivity(),
                    Manifest.permission.ACCESS_FINE_LOCATION)) {

                // Show an explanation to the user *asynchronously* -- don't block
                // this thread waiting for the user's response! After the user
                // sees the explanation, try again to request the permission.
                new AlertDialog.Builder(getActivity())
                        .setTitle(getResources().getString(R.string.Location_Permission_Needed))
                        .setMessage(getResources().getString(R.string.This_app_needs_the_Location_permission_please_accept_to_use_location_functionality))
                        .setPositiveButton(getResources().getString(R.string.ok), new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                //Prompt the user once explanation has been shown
                                ActivityCompat.requestPermissions(getActivity(),
                                        new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                                        MY_PERMISSIONS_REQUEST_LOCATION);
                            }
                        })
                        .create()
                        .show();
            } else {
                // No explanation needed, we can request the permission.
                ActivityCompat.requestPermissions(getActivity(),
                        new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                        MY_PERMISSIONS_REQUEST_LOCATION);
            }
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NotNull String permissions[], @NotNull int[] grantResults) {
        switch (requestCode) {
            case MY_PERMISSIONS_REQUEST_LOCATION: {
                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    // permission was granted, yay! Do the
                    // location-related task you need to do.
                    if (ContextCompat.checkSelfPermission(Objects.requireNonNull(getActivity()),
                            Manifest.permission.ACCESS_FINE_LOCATION)
                            == PackageManager.PERMISSION_GRANTED) {


                        mFusedLocationClient.requestLocationUpdates(mLocationRequest,
                                mLocationCallback, Looper.myLooper());
                        mGoogleMap.setMyLocationEnabled(false);
                    }
                } else {
                    // if not allow a permission, the application will exit
                    Intent intent = new Intent(Intent.ACTION_MAIN);
                    intent.addCategory(Intent.CATEGORY_HOME);
                    intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    startActivity(intent);
                    getActivity().finish();
                    System.exit(0);
                }
            }
            case REQUEST_PHONE_CALL:
                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {

                    // permission was granted, yay! Do the
                    // contacts-related task you need to do.
                } else {

                    // permission denied, boo! Disable the
                    // functionality that depends on this permission.
                    Toast.makeText(getActivity(), "Permission denied to call request !", Toast.LENGTH_SHORT).show();
                }
                return;
        }
    }

    private Bitmap createAgentBitmap() {
        Bitmap result = null;
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        try {
            result = Bitmap.createBitmap(dp(40), dp(75), Bitmap.Config.ARGB_8888);
            result.eraseColor(Color.TRANSPARENT);
            result.compress(Bitmap.CompressFormat.PNG, 20, out);
            Canvas canvas = new Canvas(result);
            Drawable drawable = getResources().getDrawable(R.drawable.marker_pin);
            drawable.setBounds(0, 0, dp(40), dp(75));
            drawable.draw(canvas);
            Paint roundPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
            RectF bitmapRect = new RectF();
            canvas.save();
            Bitmap bitmap = null;
            try {
                bitmap = Ion.with(Objects.requireNonNull(getActivity())).load(sessionManager.getUser_details().getProfilepic()).asBitmap().get();
                bitmap.compress(Bitmap.CompressFormat.PNG, 20, out);
            } catch (InterruptedException | ExecutionException e) {
                e.printStackTrace();
            }
            //    Bitmap bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.profil);
            //Bitmap bitmap = BitmapFactory.decodeFile(path.toString()); /*generate bitmap here if your image comes from any url*/
            if (bitmap != null) {
                BitmapShader shader = new BitmapShader(bitmap, Shader.TileMode.CLAMP, Shader.TileMode.CLAMP);
                Matrix matrix = new Matrix();
                float scale = dp(28) / (float) bitmap.getWidth();
                matrix.postTranslate(dp(5), dp(5));
                matrix.postScale(scale, scale);
                roundPaint.setShader(shader);
                shader.setLocalMatrix(matrix);
                bitmapRect.set(dp(6), dp(5), dp(28 + 6), dp(28 + 5));
                canvas.drawRoundRect(bitmapRect, dp(15), dp(15), roundPaint);
            }
            canvas.restore();
            try {
                canvas.setBitmap(null);
            } catch (Exception e) {
            }
        } catch (Throwable t) {
            t.printStackTrace();
        }
        return result;
    }

    private Bitmap createUserBitmap(String vUser_Image) {
        Bitmap result = null;
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        try {
            result = Bitmap.createBitmap(dp(40), dp(75), Bitmap.Config.ARGB_8888);
            result.eraseColor(Color.TRANSPARENT);
            result.compress(Bitmap.CompressFormat.PNG, 20, out);
            Canvas canvas = new Canvas(result);
            Drawable drawable = getResources().getDrawable(R.drawable.marker_pin);
            drawable.setBounds(0, 0, dp(40), dp(75));
            drawable.draw(canvas);
            Paint roundPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
            RectF bitmapRect = new RectF();
            canvas.save();
            Bitmap bitmap = null;
            try {
                bitmap = Ion.with(getActivity()).load(vUser_Image).asBitmap().get();
                bitmap.compress(Bitmap.CompressFormat.PNG, 20, out);

            } catch (InterruptedException e) {
                e.printStackTrace();
            } catch (ExecutionException e) {
                e.printStackTrace();
            }
            //    Bitmap bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.profil);
            //Bitmap bitmap = BitmapFactory.decodeFile(path.toString()); /*generate bitmap here if your image comes from any url*/
            if (bitmap != null) {
                BitmapShader shader = new BitmapShader(bitmap, Shader.TileMode.CLAMP, Shader.TileMode.CLAMP);
                Matrix matrix = new Matrix();
                float scale = dp(28) / (float) bitmap.getWidth();
                matrix.postTranslate(dp(5), dp(5));
                matrix.postScale(scale, scale);
                roundPaint.setShader(shader);
                shader.setLocalMatrix(matrix);
                bitmapRect.set(dp(6), dp(5), dp(28 + 6), dp(28 + 5));
                canvas.drawRoundRect(bitmapRect, dp(15), dp(15), roundPaint);
            }
            canvas.restore();
            try {
                canvas.setBitmap(null);
            } catch (Exception e) {
            }
        } catch (Throwable t) {
            t.printStackTrace();
        }
        return result;
    }

    public int dp(float value) {
        if (value == 0) {
            return 0;
        }
        return (int) Math.ceil(getResources().getDisplayMetrics().density * value);
    }

    @Override
    public void onStop() {
        super.onStop();
        if (mapRipple != null) {
            if (mapRipple.isAnimationRunning()) {
                mapRipple.stopRippleMapAnimation();
            }
        }

    }

}
