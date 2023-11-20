package com.example.qroc;

import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import com.example.qroc.handler.ModifyUI;
import com.example.qroc.pojo.Mail;
import com.example.qroc.pojo.User;
import com.example.qroc.util.JsonUtils;
import com.example.qroc.util.PostRequest;
import com.example.qroc.util.Result;
import com.fasterxml.jackson.core.JsonProcessingException;
import okhttp3.*;
import org.json.JSONObject;

import java.io.IOException;
import java.nio.BufferUnderflowException;
import java.util.Random;
import java.util.regex.Pattern;

public class RegisterUser extends AppCompatActivity {

    private Result result1;//用来存取用户名是否存在的结果
    private Result result2;//用来存取邮箱是否存在的结果
    private Result result3;//用来存取验证码是否正确
    private Result result4;//注册完成后的结果
    private static final String URL = "http://10.0.2.2:8080";


    public final static Pattern partern = Pattern.compile("[a-zA-Z0-9]+[\\.]{0,1}[a-zA-Z0-9]+@[a-zA-Z0-9]+\\.[a-zA-Z]+");

    /**
     * 验证输入的邮箱格式是否符合
     *
     * @param email
     * @return 是否合法
     */
    public static boolean emailFormat(String email) {
        boolean isMatch = partern.matcher(email).matches();
        return isMatch;
    }

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
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.register);
        //获取验证码的按钮
        Button getVerifyCode = findViewById(R.id.getverifycode);
        //用户名
        EditText username = findViewById(R.id.username);
        //输入密码
        EditText passwordInput = findViewById(R.id.password_input);
        //确认密码
        EditText passwordConfirm = findViewById(R.id.password_confirm);
        //验证码
        EditText verifyCode = findViewById(R.id.verifycode);
        //用户名
        EditText email = findViewById(R.id.email);
        //提交按钮
        Button submit = findViewById(R.id.submit);
        //提交密码
        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String mail = email.getText().toString();
                String passport = passwordInput.getText().toString();
                String passportConfirm = passwordConfirm.getText().toString();
                String userName = username.getText().toString();
                if ("".equals(passport)) {
                    Toast.makeText(RegisterUser.this, "注意密码不能为空!", Toast.LENGTH_SHORT).show();
                } else if (passport.length() < 6) {
                    Toast.makeText(RegisterUser.this, "密码长度太短，请输入至少六位!", Toast.LENGTH_SHORT).show();
                } else if (!passportConfirm.equals(passport)) {
                    Toast.makeText(RegisterUser.this, "两次输入的密码不同!", Toast.LENGTH_SHORT).show();
                } else if ("".equals(mail)) {
                    Toast.makeText(RegisterUser.this, "邮箱不能为空!", Toast.LENGTH_SHORT).show();
                } else if (!emailFormat(mail)) {
                    Toast.makeText(RegisterUser.this, "邮箱不合法!", Toast.LENGTH_SHORT).show();
                } else if ("".equals(userName)) {
                    Toast.makeText(RegisterUser.this, "用户名不能为空!", Toast.LENGTH_SHORT).show();
                } else if (!usernameFormat(userName)) {
                    Toast.makeText(RegisterUser.this, "用户名只能包含大小写字母和数字!", Toast.LENGTH_SHORT).show();
                } else {
                    Thread thread1 = new Thread(new Runnable() {
                        @Override
                        public void run() {
                            User user = new User();
                            user.setUsername(userName);
                            try {
                                result1 = JsonUtils.jsonToResult(PostRequest.post(URL + "/existUsername", JsonUtils.objectToJson(user)));
                            } catch (JsonProcessingException e) {
                                e.printStackTrace();
                            }
                        }
                    });
                    thread1.start();
                    try {
                        thread1.join();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    if (result1.getCode() == 0) {
                        Toast.makeText(RegisterUser.this, result1.getMsg(), Toast.LENGTH_SHORT).show();
                    } else {
                        Thread thread2 = new Thread(new Runnable() {
                            @Override
                            public void run() {
                                Mail verifyMail = new Mail();
                                verifyMail.setEmail(mail);
                                verifyMail.setCode((verifyCode.getText().toString()));
                                try {
                                    result3 = JsonUtils.jsonToResult(PostRequest.post(URL+"/verify", JsonUtils.objectToJson(verifyMail)));
                                } catch (JsonProcessingException e) {
                                    e.printStackTrace();
                                }
                            }
                        });
                        thread2.start();
                        try {
                            thread2.join();
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                        if (result3 != null && result3.getCode() == 0) {
                            Toast.makeText(RegisterUser.this, result3.getMsg(), Toast.LENGTH_SHORT).show();
                        } else if (result3 != null) {
                            Thread thread3 = new Thread(new Runnable() {
                                @Override
                                public void run() {
                                    User user = new User();
                                    user.setUsername(userName);
                                    user.setEmail(mail);
                                    user.setPassword(passport);
                                    try {
                                        result4 = JsonUtils.jsonToResult(PostRequest.post(URL+"/register", JsonUtils.objectToJson(user)));
                                    } catch (JsonProcessingException e) {
                                        e.printStackTrace();
                                    }
                                }
                            });
                            thread3.start();
                            try {
                                thread3.join();
                            } catch (InterruptedException e) {
                                e.printStackTrace();
                            }
                            Toast.makeText(RegisterUser.this, result4.getMsg(), Toast.LENGTH_SHORT).show();
                        }


                    }
                }


            }
        });


        //获取验证码监听事件
        getVerifyCode.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String mail = email.getText().toString();
                if ("".equals(mail)) {
                    Toast.makeText(RegisterUser.this, "邮箱不能为空!", Toast.LENGTH_SHORT).show();
                } else if (!emailFormat(mail)) {
                    Toast.makeText(RegisterUser.this, "邮箱不合法!", Toast.LENGTH_SHORT).show();
                } else {
                    Thread thread = new Thread(new Runnable() {
                        @Override
                        public void run() {
                            User user = new User();
                            user.setEmail(mail);
                            String response = null;
                            try {
                                response = PostRequest.post(URL+"/existEmail", JsonUtils.objectToJson(user));
                            } catch (JsonProcessingException e) {
                                e.printStackTrace();
                            }
                            try {
                                result2 = JsonUtils.jsonToResult(response);
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
                    if (result2 != null && result2.getCode() == 0) {
                        Toast.makeText(RegisterUser.this, result2.getMsg(), Toast.LENGTH_SHORT).show();
                    } else {
                        //                    //安卓中子线程不能直接操作UI
                        // 消息机制接管UI组件
                        Handler handler = new ModifyUI(getVerifyCode);
                        new Thread(new Runnable() {
                            @Override
                            public void run() {
                                int second = 60;
                                while (second >= -1) {
                                    Message msg = Message.obtain();
                                    msg.what = 1;
                                    msg.obj = second + "";
                                    handler.sendMessage(msg);
                                    second--;
                                    try {
                                        Thread.sleep(1000);
                                    } catch (InterruptedException e) {
                                        e.printStackTrace();
                                    }
                                }
                            }
                        }).start();
                        //发送邮件
                        new Thread(new Runnable() {
                            @Override
                            public void run() {
                                Mail mailTest = new Mail();
                                mailTest.setEmail(mail);
                                try {
                                    String s = PostRequest.post(URL + "/sendVerifyCode",JsonUtils.objectToJson(mailTest));
                                } catch (JsonProcessingException e) {
                                    e.printStackTrace();
                                }
                            }
                        }).start();
                    }
                }
            }
        });
    }
}