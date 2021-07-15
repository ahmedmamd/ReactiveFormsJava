package com.example.reactiveforms;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.smarteist.autoimageslider.SliderView;
import com.smarteist.autoimageslider.SliderViewAdapter;

import java.util.ArrayList;
import java.util.List;

public class SliderAdapter extends SliderViewAdapter<SliderAdapter.SliderAdapterVh> {
    Context context;
       String[] images = {"https://upload.wikimedia.org/wikipedia/commons/b/b6/Image_created_with_a_mobile_phone.png",
                           "https://besthqwallpapers.com/Uploads/20-11-2020/145812/thumb2-mount-fuji-japan-fujisan-autumn-red-trees.jpg",
                           "https://fiddymentfarm.rcsdk8.org/sites/main/files/main-images/camera_lense_0.jpeg"};

    public SliderAdapter(Context context) {
        this.context = context;
    }

    @Override
    public SliderAdapterVh onCreateViewHolder(ViewGroup parent) {

        return new SliderAdapterVh(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_sliderview,null));
    }

    @Override
    public void onBindViewHolder(SliderAdapterVh viewHolder, int position) {
        Glide.with(viewHolder.itemView).load(images[position]).into(viewHolder.imageslider);

    }

    @Override
    public int getCount() {
        return images.length;
    }

    public class SliderAdapterVh extends SliderViewAdapter.ViewHolder {
        ImageView imageslider;
        public SliderAdapterVh(View itemView) {
            super(itemView);
            imageslider = itemView.findViewById(R.id.image_slider);
        }
    }
}
