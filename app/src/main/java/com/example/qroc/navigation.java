package com.example.qroc;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

public class NavigationActivity extends AppCompatActivity {

    private ImageView[] imageViews;
    private int[] imageViewsId = {
            R.id.imageView01,
            R.id.imageView02,
            R.id.imageView03,
            R.id.imageView04,
            R.id.imageView05,
    };
    private TextView[] textViews;
    private int[] textViewsId = {
            R.id.textView01,
            R.id.textView02,
            R.id.textView03,
            R.id.textView04,
            R.id.textView05
    };
    private Fragment[] fragments = {
            new HomeFragment(),
            new ServiceFragment(),
            new PartyFragment(),
            new NewsFragment(),

    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initializeViews();

        // 设置默认显示的Fragment
        getSupportFragmentManager().beginTransaction().replace(R.id.framelayout, fragments[0]).commit();

        // 设置点击事件
        for (int i = 0; i < imageViewsId.length; i++) {
            imageViews[i].setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    onTabItemSelected(view);
                }
            });
        }
    }

    private void initializeViews() {
        imageViews = new ImageView[imageViewsId.length];
        textViews = new TextView[textViewsId.length];

        for (int i = 0; i < imageViewsId.length; i++) {
            imageViews[i] = findViewById(imageViewsId[i]);
            textViews[i] = findViewById(textViewsId[i]);
        }
    }

    private void onTabItemSelected(View view) {
        for (int i = 0; i < imageViewsId.length; i++) {
            if (view.getId() == imageViewsId[i]) {
                getSupportFragmentManager().beginTransaction().replace(R.id.framelayout, fragments[i]).commit();
                imageViews[i].setImageDrawable(getDrawable(R.drawable.selected_icon)); // 设置选中状态的图片资源
                textViews[i].setTextColor(getResources().getColor(R.color.selected_text_color)); // 设置选中状态的文字颜色
            } else {
                imageViews[i].setImageDrawable(getDrawable(R.drawable.unselected_icon)); // 设置未选中状态的图片资源
                textViews[i].setTextColor(getResources().getColor(R.color.unselected_text_color)); // 设置未选中状态的文字颜色
            }
        }
    }
}