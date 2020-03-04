package com.clarigo.propertyfinderagent.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.clarigo.propertyfinderagent.R;
import com.clarigo.propertyfinderagent.Utils.TypeFactory;

public class Review_Apdapter extends BaseAdapter {

    private Context context;

    public Review_Apdapter(Context context) {
        this.context = context;
    }

    @Override
    public int getCount() {
        return 5;
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
        TextView tv_comments;
    }

    @Override
    public View getView(int i, View convertView, ViewGroup parent) {
        final ViewHolder viewHolder;

        if (convertView == null) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.review_items, null);
            viewHolder = new ViewHolder();
            viewHolder.tv_comments = convertView.findViewById(R.id.tv_comments);
            convertView.setTag(viewHolder);

        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }

        viewHolder.tv_comments.setTypeface(TypeFactory.getInstance(context).PoppinsLight());
        return convertView;
    }
}
