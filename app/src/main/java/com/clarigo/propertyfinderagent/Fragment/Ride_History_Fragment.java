package com.clarigo.propertyfinderagent.Fragment;

import android.content.res.Resources;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.clarigo.propertyfinderagent.Activity.MainActivity;
import com.clarigo.propertyfinderagent.Adapter.History_Adapter;
import com.clarigo.propertyfinderagent.R;
import com.clarigo.propertyfinderagent.Utils.AppHelper;
import com.clarigo.propertyfinderagent.Utils.TypeFactory;
import com.clarigo.propertyfinderagent.Utils.Utility;
import com.clarigo.propertyfinderagent.app.SessionManager;
import com.clarigo.propertyfinderagent.dtos.history_dto.HISTORY_DATA_DTO;
import com.clarigo.propertyfinderagent.dtos.history_dto.HISTORY_DTO;
import com.clarigo.propertyfinderagent.retrofitHelper.APIClient;
import com.clarigo.propertyfinderagent.retrofitHelper.APIInterface;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class Ride_History_Fragment extends Fragment {

    private RecyclerView recyclerView;
    private View rootView;
    private RecyclerView.Adapter mAdapter;
    LinearLayoutManager layoutManager;
    private ImageView img_back;
    private TextView txt_ride_history;
    private APIInterface apiInterface;
    private SessionManager sessionManager;
    private ArrayList<HISTORY_DATA_DTO> history_data_dtos;
    int page_count = 0, last = 0;
    public boolean is_no_more_data = false;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        rootView = inflater.inflate(R.layout.fragment_ride__history, container, false);
        apiInterface = APIClient.getClient().create(APIInterface.class);
        sessionManager = new SessionManager(getActivity());
        MainActivity mainActivity= (MainActivity) getActivity();
        if (mainActivity!=null)
            mainActivity.heade_layout.setVisibility(View.GONE); //hide main toolebar

        init_view();
        return rootView;
    }

    private void init_view() {
        recyclerView = (RecyclerView) rootView.findViewById(R.id.recyclerView);
        img_back = rootView.findViewById(R.id.img_back);
        txt_ride_history = rootView.findViewById(R.id.txt_ride_history);
        txt_ride_history.setTypeface(TypeFactory.getInstance(getActivity()).PoppinsBold());

        click_event();

        layoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setHasFixedSize(true);

        history_data_dtos = new ArrayList<>();
        mAdapter = new History_Adapter(getActivity(), history_data_dtos);
        recyclerView.setAdapter(mAdapter);//set adapter of item views


        try {
            if (Utility.isConnectingToInternet(getActivity())) {
                getOrderHistory(page_count);
            } else {
                Toast.makeText(getActivity(), getResources().getString(R.string.internet_error), Toast.LENGTH_SHORT).show();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        AppHelper.setupLoadMore(recyclerView, new AppHelper.OnScrollToEnd() {
            @Override
            public void scrolledToEnd(int lastVisibleItem) {
                if (last != lastVisibleItem) {
                    last = lastVisibleItem;
                    page_count++;
                    try {
                        if (Utility.isConnectingToInternet(getActivity())) {
                            getOrderHistory(page_count);
                        } else {
                            Toast.makeText(getActivity(), getResources().getString(R.string.internet_error), Toast.LENGTH_SHORT).show();
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
        });
    }

    private void click_event() {
        img_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                MainActivity mainActivity = (MainActivity) getActivity();
                if (mainActivity != null)
                    mainActivity.displayView(0);
            }
        });
    }

    private void getOrderHistory(int vPage_count) {
        try {
            if (Utility.isConnectingToInternet(getActivity())) {
                Log.d("history....", String.valueOf(vPage_count));
                final Call<HISTORY_DTO> history_dtoCall = apiInterface.request_history_api(sessionManager.get_Agent_id(), vPage_count + "");
                history_dtoCall.enqueue(new Callback<HISTORY_DTO>() {
                    @Override
                    public void onResponse(Call<HISTORY_DTO> call, Response<HISTORY_DTO> response) {
                        try {
                            HISTORY_DTO history_dto = response.body();
                            if (history_dto.getResponse() == true || history_dto.getMessage().equals("Success")) {
                                history_data_dtos.addAll(history_dto.getHistory_data_dtos());
                                if (history_data_dtos != null) {
                                    if (mAdapter != null) {
                                        mAdapter.notifyDataSetChanged();
                                    }
                                }
                            } else {
                                if (page_count > 0) {
                                    --page_count;
                                }
                                last = 0;
                                Toast.makeText(getActivity(), "No Data Found !", Toast.LENGTH_SHORT).show();
                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }

                    @Override
                    public void onFailure(Call<HISTORY_DTO> call, Throwable t) {
                        Log.e("onFailure", " " + t.getMessage());
                        if (page_count > 0) {
                            --page_count;
                        }
                        last = 0;
                    }
                });
            } else {
                Toast.makeText(getActivity(), getResources().getString(R.string.internet_error), Toast.LENGTH_SHORT).show();
            }
        } catch (Resources.NotFoundException e) {
            e.printStackTrace();
        }
    }

}
