package com.clarigo.propertyfinderagent.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RatingBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.clarigo.propertyfinderagent.R;
import com.clarigo.propertyfinderagent.Utils.TypeFactory;
import com.clarigo.propertyfinderagent.dtos.history_dto.HISTORY_DATA_DTO;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.StringTokenizer;
import java.util.TimeZone;


public class History_Adapter extends RecyclerView.Adapter<History_Adapter.MyViewHolder> {

    private Context context;
    private ArrayList<HISTORY_DATA_DTO> history_data_dtos;

    public History_Adapter(Context context, ArrayList<HISTORY_DATA_DTO> history_data_dtos) {
        this.context = context;
        this.history_data_dtos = history_data_dtos;
    }

    @NonNull
    @Override
    public History_Adapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.history_item, parent, false);

        MyViewHolder viewHolder = new MyViewHolder(view); // pass the view to View Holder
        return viewHolder;

    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {

        holder.tv_from_address.setTypeface(TypeFactory.getInstance(context).PoppinsBold());
        holder.tv_to_address.setTypeface(TypeFactory.getInstance(context).PoppinsBold());
        holder.txt_from.setTypeface(TypeFactory.getInstance(context).PoppinsMedium());
        holder.txt_to.setTypeface(TypeFactory.getInstance(context).PoppinsMedium());
        try {
        holder.tv_to_address.setText(history_data_dtos.get(position).getLoca());
        holder.tv_from_address.setText(history_data_dtos.get(position).getAgentAddress());

            if (history_data_dtos != null) {
                if (history_data_dtos.get(position).getRating().getRating() != null) {
                    holder.ratingBar.setRating(Float.parseFloat(history_data_dtos.get(position).getRating().getRating()));
                }
                if (history_data_dtos.get(position).getPickupType().equals("1")) {
                    holder.tv_pickup_type.setText("PICK UP");
                } else if (history_data_dtos.get(position).getPickupType().equals("2")) {
                    holder.tv_pickup_type.setText("MEET UP");
                }


                String ser_date = history_data_dtos.get(position).getAcceptdate();

                //   2019-12-05 22:49:17
                Date today = new Date();
                SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
                simpleDateFormat.setTimeZone(TimeZone.getDefault());
                String Todaydate = simpleDateFormat.format(today);
//        String s_date = simpleDateFormat.format(ser_date);

                StringTokenizer tk = new StringTokenizer(ser_date);

                String date = tk.nextToken();  // <---  yyyy-mm-dd
                String time = tk.nextToken();
                String am = tk.nextToken();

                SimpleDateFormat sdf = new SimpleDateFormat("hh:mm:ss");
                SimpleDateFormat sdfs = new SimpleDateFormat("hh:mm");
                Date dt;
                String final_time = "";
                try {
                    dt = sdf.parse(time);
                    final_time = sdfs.format(dt);
                    System.out.println("Time Display: " + sdfs.format(dt)); // <-- I got result here
                } catch (ParseException e) {
                    e.printStackTrace();
                }
                if (date.equalsIgnoreCase(Todaydate)) {
                    holder.txt_time.setText("Today: " + final_time + " " + am);
                } else {
                    holder.txt_time.setText(date + " " + final_time + " " + am);
                }
                System.out.println("date........" + Todaydate + "      " + date + "   " + time);
            }
        } catch (Exception e) {
            e.printStackTrace();

        }
    }

    @Override
    public int getItemCount() {
        return null == history_data_dtos ? 0 : history_data_dtos.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView txt_from, txt_to, txt_time, tv_from_address, tv_pickup_type, tv_to_address;
        private RatingBar ratingBar;

        public MyViewHolder(View itemView) {
            super(itemView);

            txt_from = itemView.findViewById(R.id.txt_from);
            txt_to = itemView.findViewById(R.id.txt_to);
            tv_to_address = itemView.findViewById(R.id.tv_to_address);
            tv_pickup_type = itemView.findViewById(R.id.tv_pickup_type);
            tv_from_address = itemView.findViewById(R.id.tv_from_address);
            ratingBar = itemView.findViewById(R.id.ratingBar);
            txt_time = itemView.findViewById(R.id.txt_time);

        }
    }
}
