package com.example.qroc.util;

import android.app.Dialog;
import android.content.Context;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import com.example.qroc.R;


public class CircleLoading {
    /**
     * 显示Dialog
     * @param context 上下文对象
     * @param msg 提示内容
     * @param isCancelable 是否可以点击取消
     * @return
     */
    public static Dialog showLoadDialog(Context context, String msg, boolean isCancelable) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View v = inflater.inflate(R.layout.circle_loading, null);
        RelativeLayout layout = (RelativeLayout) v.findViewById(R.id.dialog_bg);

// main.xml中的ImageView
        ImageView loadImage = (ImageView) v.findViewById(R.id.load_iv);
        TextView pointTextView = (TextView) v.findViewById(R.id.point_tv);
// 加载动画
        Animation hyperspaceJumpAnimation = AnimationUtils.loadAnimation(context, R.anim.rotating_animation);
// 使用ImageView显示动画
        loadImage.startAnimation(hyperspaceJumpAnimation);
        pointTextView.setText(msg);
        Dialog loadingDialog = new Dialog(context, R.style.TransDialogStyle);
        loadingDialog.setContentView(layout);
        loadingDialog.setCancelable(isCancelable);
        loadingDialog.setCanceledOnTouchOutside(false);


        Window window = loadingDialog.getWindow();
        WindowManager.LayoutParams lp = window.getAttributes();
        lp.width = WindowManager.LayoutParams.WRAP_CONTENT;
        lp.height = WindowManager.LayoutParams.WRAP_CONTENT;
        window.setGravity(Gravity.CENTER);
        window.setAttributes(lp);
        window.setWindowAnimations(R.style.PopWindowAnimStyle);
        loadingDialog.show();
        return loadingDialog;
    }

    /**
     * 关闭dialog
     */
    public static void closeDialog(Dialog mCircularLoading) {
        if (mCircularLoading != null && mCircularLoading.isShowing()) {
            mCircularLoading.dismiss();
        }
    }

}