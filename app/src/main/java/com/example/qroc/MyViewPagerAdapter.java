package com.example.qroc;

import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.viewpager.widget.PagerAdapter;

import java.util.List;

//ViewPager适配器
public class MyViewPagerAdapter extends PagerAdapter {
    private List<View> views;
    public MyViewPagerAdapter(){}
    @Override
    public int getCount() {
        return views.size();
    }

    public MyViewPagerAdapter(List<View> views) {
        this.views = views;
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
        return view == object;
    }

    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
        container.removeView(views.get(position));
    }

    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, int position) {
        container.addView(views.get(position));
        return views.get(position);
    }
}
