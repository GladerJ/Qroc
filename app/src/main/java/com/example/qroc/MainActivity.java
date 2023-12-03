package com.example.qroc;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.PagerAdapter;
import androidx.viewpager.widget.ViewPager;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {


    private ViewPager viewPager;
    private RadioGroup radioGroup;
    private RadioButton tab1, tab2;
    private List<View> views;

    private String token;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);

        initView();


        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                switch (i) {
                    case R.id.zy:
                        viewPager.setCurrentItem(0);
                        break;
                    case R.id.me:
                        viewPager.setCurrentItem(1);
                        break;
                }
            }
        });

    }

    private void initView() {
        //初始化控件
        viewPager = findViewById(R.id.viewpager);
        radioGroup = findViewById(R.id.tab);
        tab1 = findViewById(R.id.zy);
        tab2 = findViewById(R.id.me);

        views = new ArrayList<>();
        views.add(LayoutInflater.from(this).inflate(R.layout.main_page, null));
        views.add(LayoutInflater.from(this).inflate(R.layout.mine, null));

        viewPager.setAdapter(new MyViewPagerAdapter(views));

        //滑动
        tab1.setTextColor(Color.BLUE);
        viewPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                if (position == 0) {
                    tab1.setCompoundDrawablesWithIntrinsicBounds(null, getResources().getDrawable(R.drawable.zy), null, null);
                    tab2.setCompoundDrawablesWithIntrinsicBounds(null, getResources().getDrawable(R.drawable.me_s), null, null);
                    tab1.setTextColor(Color.BLUE);
                    tab2.setTextColor(Color.BLACK);
                } else {
                    tab1.setCompoundDrawablesWithIntrinsicBounds(null, getResources().getDrawable(R.drawable.zy_s), null, null);
                    tab2.setCompoundDrawablesWithIntrinsicBounds(null, getResources().getDrawable(R.drawable.me), null, null);
                    tab2.setTextColor(Color.BLUE);
                    tab1.setTextColor(Color.BLACK);
                }
                tab1.setChecked(position == 0);
                tab2.setChecked(position != 0);
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
        Button createButton = views.get(0).findViewById(R.id.create_it);
        createButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // 在这里处理按钮点击事件的逻辑
                Intent intent = new Intent(MainActivity.this, CreateQuestionnaire.class);
                startActivity(intent);
            }
        });
    }
}

