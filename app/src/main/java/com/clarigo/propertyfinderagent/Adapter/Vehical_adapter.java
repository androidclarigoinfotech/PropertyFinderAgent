package com.clarigo.propertyfinderagent.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.clarigo.propertyfinderagent.R;
import com.clarigo.propertyfinderagent.Utils.TypeFactory;

public class Vehical_adapter extends BaseAdapter {

    private Context context;

    public Vehical_adapter(Context context) {
        this.context = context;
    }

    @Override
    public int getCount() {
        return 2;
    }

    @Override
    public Object getItem(int i) {
        return i;
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    private class ViewHolder {
        TextView tv_vehical_model, tv_vehical_make;
    }

    @Override
    public View getView(int i, View convert_view, ViewGroup viewGroup) {
        ViewHolder viewHolder;
        if (convert_view == null) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(context.LAYOUT_INFLATER_SERVICE);
            convert_view = inflater.inflate(R.layout.vehical_item, null);
            viewHolder = new ViewHolder();
            viewHolder.tv_vehical_model = convert_view.findViewById(R.id.tv_vehical_model);
            viewHolder.tv_vehical_make = convert_view.findViewById(R.id.tv_vehical_make);
            convert_view.setTag(viewHolder);

        } else {
            viewHolder = (ViewHolder) convert_view.getTag();
        }

        viewHolder.tv_vehical_model.setTypeface(TypeFactory.getInstance(context).PoppinsSemiBold());
        return convert_view;
    }
}
