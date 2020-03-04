package com.clarigo.propertyfinderagent.Navigation_Drawer;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Display;
import android.view.GestureDetector;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.core.app.ActivityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.clarigo.propertyfinderagent.R;
import com.clarigo.propertyfinderagent.Utils.TypeFactory;
import com.clarigo.propertyfinderagent.app.SessionManager;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by Ravi on 29/07/15.
 */


public class FragmentDrawer extends Fragment {


    private static String TAG = FragmentDrawer.class.getSimpleName();
    private static String[] titles = null;
    private RecyclerView recyclerView;
    private ActionBarDrawerToggle mDrawerToggle;
    private DrawerLayout mDrawerLayout;
    private NavigationDrawerAdapter adapter;
    private View containerView;
    private FragmentDrawerListener drawerListener;
    RelativeLayout header_view_one, header_view_two;
    View layout;
    private TextView txtusername, txt_copyright;
    private SessionManager sessionManager;
    String strUserName = "";
    private CircleImageView img_user_Image;

    public FragmentDrawer() {

    }

    public static List<NavDrawerItem> getData() {
        List<NavDrawerItem> data = new ArrayList<>();


        // preparing navigation drawer items
        for (int i = 0; i < titles.length; i++) {
            NavDrawerItem navItem = new NavDrawerItem();
            navItem.setTitle(titles[i]);
            // navItem.setImageID(images[i]);
            data.add(navItem);
        }
        return data;
    }

    public void setDrawerListener(FragmentDrawerListener listener) {
        this.drawerListener = listener;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // drawer labels
        titles = getActivity().getResources().getStringArray(R.array.nav_drawer_labels);

    }

    @Override
    public void onResume() {

        super.onResume();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflating view layout
        layout = inflater.inflate(R.layout.fragment_navigation_drawer, container, false);
        // sessionManager = new SessionManager(getActivity());
        sessionManager = new SessionManager(getContext());
        try {
            WindowManager wm = (WindowManager) getActivity().getSystemService(Context.WINDOW_SERVICE);
            Display display = wm.getDefaultDisplay();
            DisplayMetrics metrics = new DisplayMetrics();
            display.getMetrics(metrics);
            int width = metrics.widthPixels;
            int height = metrics.heightPixels;
            System.out.println("width..." + width);
            System.out.println("width..." + height);
            if (width == 480 && height == 800) {
                header_view_one = (RelativeLayout) layout.findViewById(R.id.header_one);
                header_view_one.setVisibility(View.GONE);
                header_view_two.setVisibility(View.VISIBLE);
            }
        } catch (Exception e) {

        }
        img_user_Image = (CircleImageView) layout.findViewById(R.id.img_user_Image);
        recyclerView = (RecyclerView) layout.findViewById(R.id.drawerList);
        txtusername = (TextView) layout.findViewById(R.id.txtusername);
        txt_copyright = (TextView) layout.findViewById(R.id.txt_copyright);
        txt_copyright.setTypeface(TypeFactory.getInstance(getContext()).PoppinsBold());
        txtusername.setTypeface(TypeFactory.getInstance(getContext()).PoppinsBold());


        adapter = new NavigationDrawerAdapter(getActivity(), getData());
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerView.addOnItemTouchListener(new RecyclerTouchListener(getActivity(), recyclerView, new ClickListener() {
            @Override
            public void onClick(View view, int position) {
                drawerListener.onDrawerItemSelected(view, position);
                mDrawerLayout.closeDrawer(containerView);
                if (position == 5) {
                    Toast.makeText(getContext(), "Logout Successfully", Toast.LENGTH_SHORT).show();
                    sessionManager.Logout();
                    getActivity().finish();
                }
            }

            @Override
            public void onLongClick(View view, int position) {

            }
        }));

        return layout;
    }

    public void setUserName() {
        try {
            strUserName = sessionManager.getUser_details().getAgentName();
            Picasso.with(getContext()).load(sessionManager.getUser_details().getProfilepic()).error(R.drawable.profil).into(img_user_Image);
        } catch (Exception e) {
            e.printStackTrace();
            strUserName = "";
        }
        if (!strUserName.equalsIgnoreCase("")) {
            txtusername.setText(sessionManager.getUser_details().getAgentName());
        }

    }

    public boolean isPermissionGranted() {
        if (Build.VERSION.SDK_INT >= 23) {
            if (getActivity().checkSelfPermission(Manifest.permission.CALL_PHONE)
                    == PackageManager.PERMISSION_GRANTED) {
                Log.v("TAG", "Permission is granted");
                return true;
            } else {
                Log.v("TAG", "Permission is revoked");
                ActivityCompat.requestPermissions(getActivity(), new String[]{Manifest.permission.CALL_PHONE}, 1);
                return false;
            }
        } else { //permission is automatically granted on sdk<23 upon installation
            Log.v("TAG", "Permission is granted");
            return true;
        }
    }

    //    public void setUp(int fragmentId, DrawerLayout drawerLayout, final Toolbar toolbar) {
    public void setUp(int fragmentId, DrawerLayout drawerLayout) {
        containerView = getActivity().findViewById(fragmentId);
        mDrawerLayout = drawerLayout;
        mDrawerToggle = new ActionBarDrawerToggle(getActivity(), drawerLayout, R.string.drawer_open, R.string.drawer_close) {
            @Override
            public void onDrawerOpened(View drawerView) {
                super.onDrawerOpened(drawerView);
                getActivity().invalidateOptionsMenu();
                  setUserName();
            }

            @Override
            public void onDrawerClosed(View drawerView) {
                super.onDrawerClosed(drawerView);
                try {
                    getActivity().invalidateOptionsMenu();
                } catch (Exception e) {
                    e.printStackTrace();
                }
                  setUserName();
            }

            @Override
            public void onDrawerSlide(View drawerView, float slideOffset) {
                super.onDrawerSlide(drawerView, slideOffset);
                //  toolbar.setAlpha(1 - slideOffset / 2);
            }
        };

        mDrawerToggle.setDrawerIndicatorEnabled(false);
        mDrawerLayout.setDrawerListener(mDrawerToggle);
        mDrawerLayout.post(new Runnable() {
            @Override
            public void run() {
                mDrawerToggle.syncState();
            }
        });
    }

    public static interface ClickListener {
        public void onClick(View view, int position);

        public void onLongClick(View view, int position);
    }

    public interface FragmentDrawerListener {
        public void onDrawerItemSelected(View view, int position);
    }

    static class RecyclerTouchListener implements RecyclerView.OnItemTouchListener {

        private GestureDetector gestureDetector;
        private ClickListener clickListener;

        public RecyclerTouchListener(Context context, final RecyclerView recyclerView, final ClickListener clickListener) {
            this.clickListener = clickListener;
            gestureDetector = new GestureDetector(context, new GestureDetector.SimpleOnGestureListener() {
                @Override
                public boolean onSingleTapUp(MotionEvent e) {
                    return true;
                }

                @Override
                public void onLongPress(MotionEvent e) {
                    View child = recyclerView.findChildViewUnder(e.getX(), e.getY());
                    if (child != null && clickListener != null) {
                        clickListener.onLongClick(child, recyclerView.getChildPosition(child));
                    }
                }
            });
        }

        @Override
        public boolean onInterceptTouchEvent(RecyclerView rv, MotionEvent e) {

            View child = rv.findChildViewUnder(e.getX(), e.getY());
            if (child != null && clickListener != null && gestureDetector.onTouchEvent(e)) {
                clickListener.onClick(child, rv.getChildPosition(child));
            }
            return false;
        }

        @Override
        public void onTouchEvent(RecyclerView rv, MotionEvent e) {
        }

        @Override
        public void onRequestDisallowInterceptTouchEvent(boolean disallowIntercept) {

        }


    }
}
