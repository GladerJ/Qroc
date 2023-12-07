package com.example.qroc;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.*;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.PagerAdapter;
import androidx.viewpager.widget.ViewPager;
import com.example.qroc.handler.SearchQuestionnaireAsyncTask;
import com.example.qroc.handler.TokenThread;
import com.example.qroc.pojo.behind.Questionnaire;
import com.example.qroc.util.JsonUtils;
import com.example.qroc.util.MMKVUtils;
import com.example.qroc.util.PostRequest;
import com.example.qroc.util.Result;
import com.fasterxml.jackson.core.JsonProcessingException;

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
                MMKVUtils.init(getFilesDir().getAbsolutePath() + "/tmp");
                String token = MMKVUtils.getString("token");
                if (token == null) {
                    loginOutDate();
                    return;
                }
                TokenThread thread = new TokenThread();
                thread.start();
                try {
                    thread.join();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                if (thread.getResult().getCode() == 0) {
                    loginOutDate();
                    return;
                }

                Intent intent = new Intent(MainActivity.this, CreateQuestionnaire.class);
                startActivity(intent);

            }
        });

//        Button searchButton = views.get(0).findViewById(R.id.search_it);
//        searchButton.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//
//                // 在这里处理按钮点击事件的逻辑
//                MMKVUtils.init(getFilesDir().getAbsolutePath() + "/tmp");
//                String token = MMKVUtils.getString("token");
//                if (token == null) {
//                    loginOutDate();
//                    return;
//                }
//                TokenThread thread1 = new TokenThread();
//                thread1.start();
//                try {
//                    thread1.join();
//                } catch (InterruptedException e) {
//                    e.printStackTrace();
//                }
//                if (thread1.getResult().getCode() == 0) {
//                    loginOutDate();
//                    return;
//                }
//
//                Intent intent = new Intent(com.example.qroc.MainActivity.this, QuestionnaireActivity.class);
//                Thread thread = new Thread(new Runnable() {
//                    @Override
//                    public void run() {
//                        Questionnaire questionnaire = new Questionnaire();
//                        questionnaire.setQuestionnaireId(23L);
//                        try {
//                            respond = PostRequest.post(RegisterUser.URL + "/searchQuestionnaire", JsonUtils.objectToJson(questionnaire));
//                        } catch (JsonProcessingException e) {
//                            e.printStackTrace();
//                        }
//                    }
//                });
//                thread.start();
//                try {
//                    thread.join();
//                } catch (InterruptedException e) {
//                    e.printStackTrace();
//                }
//                intent.putExtra("questionnaire", respond);
//                startActivity(intent);
//            }
//        });
        Button searchButton = views.get(0).findViewById(R.id.search_it);
        searchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // 在这里处理按钮点击事件的逻辑
                AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
                builder.setTitle("请输入你要搜索的问卷id：");
                final EditText searchEditText = new EditText(MainActivity.this);
                builder.setView(searchEditText);
                builder.setPositiveButton("搜索", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        String searchText = searchEditText.getText().toString();
                        SearchQuestionnaireAsyncTask searchQuestionnaireAsyncTask = new SearchQuestionnaireAsyncTask(MainActivity.this,Long.parseLong(searchText));
                        searchQuestionnaireAsyncTask.execute();
                    }
                });
                builder.setNegativeButton("取消", null);
                builder.setCancelable(false);
                builder.show();
            }
        });

        Button having = views.get(0).findViewById(R.id.have_it);
        having.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // 在这里处理按钮点击事件的逻辑
                Intent intent = new Intent(MainActivity.this,ShowQuestionnaire.class);
                startActivity(intent);
            }
        });
    }


    String respond;

    public void loginOutDate() {
        AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
        builder.setTitle("系统提示：").setMessage("登录失效，请重新登录！");
        builder.setIcon(R.mipmap.ic_launcher_round);
        builder.setPositiveButton("确认", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                //Toast.makeText(builder.getContext(), "你的手机已中毒",Toast.LENGTH_SHORT).show();
                /**注册成功后跳转到LoginUser页面
                 */
                Intent intent = new Intent(MainActivity.this, LoginUser.class);
                startActivity(intent);
                finish();
            }
        });
        builder.setCancelable(false);
        builder.show();
    }
}




