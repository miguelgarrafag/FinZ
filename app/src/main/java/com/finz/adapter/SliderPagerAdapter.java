package com.finz.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v4.view.PagerAdapter;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.finz.R;
import com.finz.RestDinamicConstant;
import com.finz.rest.RestConstant;
import com.finz.rest.slider.entity.Slider;

import java.util.List;

public class SliderPagerAdapter extends PagerAdapter {

    private List<Slider> items;
    private Context context;

    public SliderPagerAdapter(Context context, List<Slider> items) {
        this.items = items;
        this.context = context;
    }

    @Override
    public int getCount() {
        return items.size();
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object o) {
        return view == o;
    }

    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, int position) {

        Slider item = items.get(position);
        View itemView = ((AppCompatActivity) context).getLayoutInflater().inflate(R.layout.slider, container, false);

        ImageView image = itemView.findViewById(R.id.image);
        TextView title = itemView.findViewById(R.id.title);
        TextView msg = itemView.findViewById(R.id.msg);

        Glide.with(context)
                .load(RestDinamicConstant.URL_IMAGES + RestConstant.SLIDERS + item.getImage() + RestConstant.ALT)
                .apply(new RequestOptions()
                        .placeholder(R.drawable.ic_zeta)
                        .skipMemoryCache(true))
                .into(image);

        title.setText(item.getTitle());
        msg.setText(item.getMessage());

        container.addView(itemView);

        return itemView;
    }

    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
        container.removeView((LinearLayout) object);
    }
}
