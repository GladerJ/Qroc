package com.example.qroc;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.text.Spannable;
import android.text.SpannableStringBuilder;
import android.text.method.LinkMovementMethod;
import android.text.style.ClickableSpan;
import android.view.View;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public class LoginUser extends AppCompatActivity {
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
    }
}
