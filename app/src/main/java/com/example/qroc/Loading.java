package com.example.qroc;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.widget.ProgressBar;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import com.example.qroc.handler.MyAsyncTask;
import com.example.qroc.util.Result;

public class Loading extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.loading);
        ProgressBar progressBar = findViewById(R.id.progressBar);
        progressBar.setMax(1000);
        MyAsyncTask myAsyncTask = new MyAsyncTask(progressBar,getFilesDir().getAbsolutePath() + "/tmp",this);
        myAsyncTask.execute();

    }
    //后期这里要发送数据包
    public void decide(Result result){
        if(result.getCode()==1){
            Intent intent = new Intent(Loading.this, MainActivity.class);
            startActivity(intent);
            finish();
            return;
        }
        Intent intent = new Intent(Loading.this, LoginUser.class);
        startActivity(intent);
        finish();
    }

    public void longTimeError(){
        androidx.appcompat.app.AlertDialog.Builder builder = new androidx.appcompat.app.AlertDialog.Builder(Loading.this);
        builder.setTitle("连接超时");
        builder.setMessage(TextMessage.CONNECT_ERROR_MESSAGE);
        builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                System.exit(0);
            }
        });
        AlertDialog dialog = builder.create();
        dialog.show();
    }
}
