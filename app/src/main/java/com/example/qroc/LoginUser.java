package com.example.qroc;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.text.Spannable;
import android.text.SpannableStringBuilder;
import android.text.method.LinkMovementMethod;
import android.text.style.ClickableSpan;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import java.util.regex.Pattern;


public class LoginUser extends AppCompatActivity {

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
                Intent intent = new Intent(LoginUser.this,RegisterUser.class);
                startActivity(intent);

            }

        };
        stringBuilder.setSpan(clickableSpan,0,4, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
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
                String passport = passwordInput.getText().toString();

                if ("".equals(passport)) {
                    if("".equals(userName)){
                        Toast.makeText(LoginUser.this, "请先输入用户名!", Toast.LENGTH_SHORT).show();
                        return;
                    }
                    Toast.makeText(LoginUser.this, "注意密码不能为空!", Toast.LENGTH_SHORT).show();
                } else if (passport.length() < 6) {
                    Toast.makeText(LoginUser.this, "密码长度太短，请输入至少六位!", Toast.LENGTH_SHORT).show();
                }
                else if("".equals(userName)){
                    Toast.makeText(LoginUser.this, "注意用户名不能为空!", Toast.LENGTH_SHORT).show();
                }
                else if(!usernameFormat(userName)){
                    Toast.makeText(LoginUser.this, "用户名只能包含大小写字母和数字!", Toast.LENGTH_SHORT).show();
                }


            }
        });

    }
}
