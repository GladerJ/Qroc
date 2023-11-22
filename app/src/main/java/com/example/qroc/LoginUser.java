package com.example.qroc;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.text.Spannable;
import android.text.SpannableStringBuilder;
import android.text.method.LinkMovementMethod;
import android.text.style.ClickableSpan;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import com.example.qroc.pojo.User;
import com.example.qroc.util.JsonUtils;
import com.example.qroc.util.MMKVUtils;
import com.example.qroc.util.PostRequest;
import com.example.qroc.util.Result;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.tencent.mmkv.MMKV;

import java.io.*;
import java.util.regex.Pattern;


public class LoginUser extends AppCompatActivity {
    //用来存储登录结果
    private Result result1;



    /**
     * 判断用户名是否合法
     *
     * @param username
     * @return
     */
    public static boolean usernameFormat(String username) {
        String regex = "^[a-z0-9A-Z]+$";
        return Pattern.matches(regex, username);
    }

    @Override
    protected void onCreate(@Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login);

        /**从登录界面通过点击“注册用户”跳转到RegisterUser页面
         *
         */
        TextView PrivacyPolicy = findViewById(R.id.register_user);
        SpannableStringBuilder stringBuilder = new SpannableStringBuilder(PrivacyPolicy.getText());
        ClickableSpan clickableSpan = new ClickableSpan() {
            @Override
            public void onClick(@NonNull View widget) {
                Intent intent = new Intent(LoginUser.this, RegisterUser.class);
                startActivity(intent);
            }

        };

        stringBuilder.setSpan(clickableSpan, 0, 4, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        PrivacyPolicy.setMovementMethod(LinkMovementMethod.getInstance());
        PrivacyPolicy.setHighlightColor(Color.TRANSPARENT);
        PrivacyPolicy.setText(stringBuilder);

        //用户名
        EditText usernameInuput = findViewById(R.id.username);
        //输入密码
        EditText passwordInput = findViewById(R.id.password);
        //提交按钮
        Button submit = findViewById(R.id.submit);

        //提交密码
        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String userName = usernameInuput.getText().toString();
                String passWord = passwordInput.getText().toString();

                if ("".equals(userName)) {
                    Toast.makeText(LoginUser.this, "注意用户名不能为空!", Toast.LENGTH_SHORT).show();
                    return;
                } else if (!usernameFormat(userName)) {
                    Toast.makeText(LoginUser.this, "用户名只能包含大小写字母和数字!", Toast.LENGTH_SHORT).show();
                    return;
                } else if ("".equals(passWord)) {
//                    if("".equals(userName)){
//                        Toast.makeText(LoginUser.this, "请先输入用户名!", Toast.LENGTH_SHORT).show();
//                        return;
//                    }
                    Toast.makeText(LoginUser.this, "注意密码不能为空!", Toast.LENGTH_SHORT).show();
                    return;
                } else if (passWord.length() < 6) {
                    Toast.makeText(LoginUser.this, "密码长度太短，请输入至少六位!", Toast.LENGTH_SHORT).show();
                    return;
                } else {
                    Thread thread = new Thread(new Runnable() {
                        @Override
                        public void run() {
                            User user = new User();
                            //:lpl
                            //:123456
                            //{"username":"lpl","password":"123456"}
                            user.setUsername(userName);
                            user.setPassword(passWord);
                            String json = null;
                            try {
                                json = JsonUtils.objectToJson(user);
                            } catch (JsonProcessingException e) {
                                e.printStackTrace();
                            }

                            String respond = PostRequest.post(RegisterUser.URL + "/login", json);
                            Log.d("登录测试", respond);
                            try {
                                result1 = JsonUtils.jsonToResult(respond);

                            } catch (JsonProcessingException e) {
                                e.printStackTrace();
                            }

                        }
                    });
                    thread.start();
                    try {
                        thread.join();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    if (result1.getCode() == 0) {
                        Toast.makeText(LoginUser.this, result1.getMsg(), Toast.LENGTH_SHORT).show();
                    }

                    //跳转到主页面
                    else {
                        Toast.makeText(LoginUser.this, "登陆成功！", Toast.LENGTH_SHORT).show();

                        MMKVUtils.init(getFilesDir().getAbsolutePath() + "/tmp");
                        MMKVUtils.putString("token",(String)result1.getData());
                    }
                }

            }
        });

    }
}
