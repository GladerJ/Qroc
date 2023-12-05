package com.example.qroc;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.text.Spannable;
import android.text.SpannableStringBuilder;
import android.text.method.LinkMovementMethod;
import android.text.style.ClickableSpan;
import android.util.Log;
import android.view.View;
import android.widget.*;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import com.example.qroc.pojo.User;
import com.example.qroc.util.*;
import com.fasterxml.jackson.core.JsonProcessingException;

import java.util.regex.Pattern;


public class LoginUser extends AppCompatActivity {
    //用来存储登录结果

    private Result result1;
    private Result result2;

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

        Intent intent = getIntent();


        setContentView(R.layout.login);

        TextView agreementSee = findViewById(R.id.agreement_see);
        CheckBox agreementConfirm = findViewById(R.id.agreement_confirm);
        agreementSee.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // 在这里编写弹出用户协议和隐私政策的操作
                // 例如，显示一个对话框
                AlertDialog.Builder builder = new AlertDialog.Builder(LoginUser.this);
                builder.setTitle("用户协议与隐私政策");
                builder.setMessage(TextMessage.USER_NEED_READ);
                builder.setPositiveButton("同意", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        agreementConfirm.setChecked(true);
                    }
                });
                builder.setNegativeButton("不同意", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        agreementConfirm.setChecked(false);
                    }
                });
                AlertDialog dialog = builder.create();
                dialog.show();
            }
        });
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

        if(intent != null){
            String username = intent.getStringExtra("username");
            String password = intent.getStringExtra("password");
            if(username != null){
                usernameInuput.setText(username);
            }
            if(password != null){
                passwordInput.setText(password);
            }
        }

        //提交密码
        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String userName = usernameInuput.getText().toString();
                String passWord = passwordInput.getText().toString();
                if(!agreementConfirm.isChecked()){
                    Toast.makeText(LoginUser.this, "请先同意用户协议与隐私政策!", Toast.LENGTH_SHORT).show();
                    return;
                }
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
                    submit.setEnabled(false);
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
                            if (respond == null) return;
                            try {
                                result1 = JsonUtils.jsonToResult(respond);

                            } catch (JsonProcessingException e) {
                                e.printStackTrace();
                            }

                        }
                    });
                    thread.start();
                    try {
                        thread.join(5000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    submit.setEnabled(true);
                    if(result1 == null){
                        Toast.makeText(LoginUser.this,"网络连接失败！", Toast.LENGTH_SHORT).show();
                        return;
                    }
                    if (result1.getCode() == 0) {
                        Toast.makeText(LoginUser.this, result1.getMsg(), Toast.LENGTH_SHORT).show();
                    }

                    //跳转到主页面
                    else {
                        Toast.makeText(LoginUser.this, "登陆成功！", Toast.LENGTH_SHORT).show();

                        MMKVUtils.init(getFilesDir().getAbsolutePath() + "/tmp");
                        MMKVUtils.putString("token",(String)result1.getData());


                        Intent intent = new Intent(LoginUser.this, MainActivity.class);
                        startActivity(intent);
                        finish();

                    }
                }

            }
        });

    }
}
