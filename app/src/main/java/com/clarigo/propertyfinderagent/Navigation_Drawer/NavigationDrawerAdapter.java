package com.clarigo.propertyfinderagent.Navigation_Drawer;


import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.clarigo.propertyfinderagent.R;
import com.clarigo.propertyfinderagent.Utils.TypeFactory;

import java.util.Collections;
import java.util.List;

public class NavigationDrawerAdapter extends RecyclerView.Adapter<NavigationDrawerAdapter.MyViewHolder> {
    List<NavDrawerItem> data = Collections.emptyList();
    private LayoutInflater inflater;
    private Context context;

    public NavigationDrawerAdapter(Context context, List<NavDrawerItem> data) {
        this.context = context;
        inflater = LayoutInflater.from(context);
        this.data = data;
    }

    public void delete(int position) {
        data.remove(position);
        notifyItemRemoved(position);
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.nav_drawer_row, parent, false);


        MyViewHolder holder = new MyViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        NavDrawerItem current = data.get(position);
        holder.title.setText(current.getTitle());
        holder.title.setTypeface(TypeFactory.getInstance(context).PoppinsBold());

        holder.imgIcon.setImageResource(current.getImageID());
        if(position==1 || position==3)
        {
            holder.view.setVisibility(View.VISIBLE);
            holder.view.setPadding(0,20,0,20);
        }
        else if(position==2){
            holder.view.setPadding(0,20,0,20);
        }

    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder {
        TextView title;
        ImageView imgIcon;
        View view;

        public MyViewHolder(View itemView) {
            super(itemView);
            title = itemView.findViewById(R.id.title);
            imgIcon = itemView.findViewById(R.id.drawer_icon);
            view=itemView.findViewById(R.id.view);
        }
    }
}
