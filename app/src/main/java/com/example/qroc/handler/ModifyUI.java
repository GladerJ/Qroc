package com.example.qroc.handler;

import android.os.Handler;
import android.os.Message;
import android.widget.Button;


public class ModifyUI extends Handler {
    private Button bt;
    public ModifyUI(Button bt){
        this.bt = bt;
    }
    @Override
    public void handleMessage(Message msg){
        if(msg.what == 1){
            String result = (String) msg.obj;
            bt.setText(result);
            if(result.equals("-1")){
                bt.setText("获取验证码");
                bt.setEnabled(true);
            }
            else{
                bt.setEnabled(false);
            }

        }
    }
}
