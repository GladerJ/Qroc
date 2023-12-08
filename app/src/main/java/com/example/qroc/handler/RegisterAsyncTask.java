package com.example.qroc.handler;

import android.app.Activity;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.widget.EditText;
import android.widget.Toast;
import androidx.appcompat.app.AlertDialog;
import com.example.qroc.LoginUser;
import com.example.qroc.MainActivity;
import com.example.qroc.R;
import com.example.qroc.RegisterUser;
import com.example.qroc.pojo.Mail;
import com.example.qroc.pojo.User;
import com.example.qroc.util.*;
import com.fasterxml.jackson.core.JsonProcessingException;

public class RegisterAsyncTask extends AsyncTask<Void, Integer, Integer> {
    private Result result1;//用来存取用户名是否存在的结果
    private Result result2;//用来存取邮箱是否存在的结果
    private Result result3;//用来存取验证码是否正确
    private Result result4;//注册完成后的结果

    private String username;
    private String password;
    private String mail;
    private EditText verifyCode;

    private Dialog dialog;

    private Activity activity;

    public RegisterAsyncTask(String username, String password, String mail, EditText verifyCode, Activity activity) {
        this.username = username;
        this.password = password;
        this.mail = mail;
        this.verifyCode = verifyCode;
        this.activity = activity;
    }


    @Override
    protected void onProgressUpdate(Integer... values) {
        //显示

    }

    @Override
    protected void onPostExecute(Integer aBoolean) {
        CircleLoading.closeDialog(dialog);
        if (aBoolean == -1){
            Toast.makeText(activity,"未知错误，请联系管理员!",Toast.LENGTH_SHORT).show();
        }
        else if(aBoolean == 0){
            Toast.makeText(activity,"用户名已存在!",Toast.LENGTH_SHORT).show();
        }
        else if(aBoolean == 1){
            Toast.makeText(activity,"验证码有误!",Toast.LENGTH_SHORT).show();
        }
        else if(aBoolean == 2){
            Toast.makeText(activity,"用户创建失败，请联系管理员!",Toast.LENGTH_SHORT).show();
        }
        else{
            AlertDialog.Builder builder = new AlertDialog.Builder(activity);
            builder.setTitle("系统提示：").setMessage("注册用户成功！");
            builder.setIcon(R.mipmap.ic_launcher_round);
            builder.setPositiveButton("确认", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    //Toast.makeText(builder.getContext(), "你的手机已中毒",Toast.LENGTH_SHORT).show();
                    /**注册成功后跳转到LoginUser页面
                     */
                    Intent intent = new Intent(activity,LoginUser.class);
                    intent.putExtra("username",username);
                    intent.putExtra("password",password);
                    activity.startActivity(intent);
                    activity.finish();
                }
            });
            builder.setCancelable(false);
            builder.show();
        }

    }


    @Override
    protected void onPreExecute() {
        dialog = CircleLoading.showLoadDialog(activity, "加载中...", true);
    }

    @Override
    protected Integer doInBackground(Void... voids) {
        User user = new User();
        user.setUsername(username);
        try {
            result1 = JsonUtils.jsonToResult(PostRequest.post(RegisterUser.URL + "/existUsername", JsonUtils.objectToJson(user)));
        } catch (JsonProcessingException e) {
            return -1;
        }

        if (result1==null || result1.getCode() == 0) return 0;//已存在该用户名
        Mail verifyMail = new Mail();
        verifyMail.setEmail(mail);
        verifyMail.setCode((verifyCode.getText().toString()));
        try {
            System.out.println(JsonUtils.objectToJson(verifyMail));
            String tmpJson = PostRequest.post(RegisterUser.URL+"/verify", JsonUtils.objectToJson(verifyMail));
            System.out.println(tmpJson);
            result3 = JsonUtils.jsonToResult(tmpJson);
        } catch (JsonProcessingException e) {
            return -1;
        }
        if(result3 == null || result3.getCode() == 0) return 1;//邮箱重复

        User user1 = new User();
        user1.setUsername(username);
        user1.setEmail(mail);
        user1.setPassword(password);

        try {
            result4 = JsonUtils.jsonToResult(PostRequest.post(RegisterUser.URL+"/register", JsonUtils.objectToJson(user1)));
        } catch (JsonProcessingException e) {
            return -1;
        }

        if (result4 == null || result4.getCode() == 0) return 2;//验证码有误
        return 3;


    }


}
