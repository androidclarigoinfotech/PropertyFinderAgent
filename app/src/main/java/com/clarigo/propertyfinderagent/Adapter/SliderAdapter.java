package com.clarigo.propertyfinderagent.Adapter;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.clarigo.propertyfinderagent.R;
import com.clarigo.propertyfinderagent.dtos.propertyfinder.PROPERTY_MAIN_DTO;
import com.smarteist.autoimageslider.SliderViewAdapter;

import java.util.List;


public class SliderAdapter extends SliderViewAdapter<SliderAdapter.SliderAdapterVH> {

    private Context context;
    private int mCount = 0;
    private List<String> photos = null;
    private Integer mlsId;
    private List<PROPERTY_MAIN_DTO> property_main_dtos;

    public SliderAdapter(Context context, List<String> photos, List<PROPERTY_MAIN_DTO> property_main_dtos, Integer mlsId) {
        this.context = context;
        this.photos = photos;
        this.property_main_dtos = property_main_dtos;
        this.mlsId = mlsId;
    }

    @Override
    public int getCount() {
        //slider view count could be dynamic size
        return mCount;
    }

    public void setCount(int count) {
        this.mCount = count;
    }

    @Override
    public SliderAdapterVH onCreateViewHolder(ViewGroup parent) {
        View inflate = LayoutInflater.from(parent.getContext()).inflate(R.layout.image_slider_layout_item, null);
        return new SliderAdapterVH(inflate);
    }

    @Override
    public void onBindViewHolder(SliderAdapterVH viewHolder, final int position) {

        viewHolder.textViewDescription.setTextSize(16);
        viewHolder.textViewDescription.setTextColor(Color.WHITE);
        viewHolder.imageGifContainer.setVisibility(View.GONE);
        Glide.with(context)
                .load(photos.get(position))
                .fitCenter()
                .into(viewHolder.imageViewBackground);
    }

    class SliderAdapterVH extends SliderViewAdapter.ViewHolder {

        View itemView;
        ImageView imageViewBackground;
        ImageView imageGifContainer;
        TextView textViewDescription;

        public SliderAdapterVH(View itemView) {
            super(itemView);
            imageViewBackground = itemView.findViewById(R.id.iv_auto_image_slider);
            imageGifContainer = itemView.findViewById(R.id.iv_gif_container);
            textViewDescription = itemView.findViewById(R.id.tv_auto_image_slider);
            this.itemView = itemView;
        }
    }
}