package com.ruoyu.pigroad.myapplication.Ui;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.callback.StringCallback;
import com.lzy.okgo.model.Response;
import com.ruoyu.pigroad.myapplication.GGJActivity;
import com.ruoyu.pigroad.myapplication.Login.LoginActivity;
import com.ruoyu.pigroad.myapplication.R;

import org.json.JSONException;
import org.json.JSONObject;

import butterknife.BindView;
import butterknife.ButterKnife;

import static com.ruoyu.pigroad.myapplication.Config.Config_API.API_URL;

/**
 * Created by PIGROAD on 2018/9/10
 * Email:920015363@qq.com
 */
public class AppStartActivity extends Activity {

    @BindView(R.id.tv_time)
    TextView tv_time;
    @BindView(R.id.iv_bg)
    ImageView iv_bg;

    private MyCountdownTimer countdowntimer;
    private String login_token;

    private String once_login;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_appstart);
        ButterKnife.bind(this);
        this.init();
    }

    /**
     * 初始化
     */
    private void init() {
        //拿token
        SharedPreferences sp = getSharedPreferences("USER", MODE_PRIVATE);
        login_token = sp.getString("user_token", null);

//        tv_time.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                redirectTo();
//                finish();
//            }
//        });

        countdowntimer = new MyCountdownTimer(5000, 1000);
        countdowntimer.start();

        Glide.with(this).load(R.drawable.app_start_bg).into(iv_bg);
    }

    /**
     * 继承 CountDownTimer 防范
     * 参数：  倒计时总数，单位为毫秒、每隔多久调用onTick一次
     * 重写 父类的方法 onTick() 、 onFinish()
     */
    protected class MyCountdownTimer extends CountDownTimer {

        public MyCountdownTimer(long millisInFuture, long countDownInterval) {
            super(millisInFuture, countDownInterval);
        }

        @Override
        public void onTick(long millisUntilFinished) {
            tv_time.setText("倒计时(" + millisUntilFinished / 1000 + ")");
        }

        @Override
        public void onFinish() {
            finish();
            redirectTo();
        }
    }

    private void redirectTo() {
       if (isFrist()){
           startActivity(new Intent(AppStartActivity.this,FristChooseActivity.class));
           finish();
       }else {
           startActivity(new Intent(AppStartActivity.this,GGJActivity.class));
           finish();
       }
    }

    /**
     * 判断是否为 第一次登陆
     */
    private boolean isFrist(){
        SharedPreferences sp = this.getSharedPreferences("LoginUser", MODE_PRIVATE);
        once_login = sp.getString("once_login", "");
        if (once_login.equals("")){
            return true;
        }else{
            return false;
        }
    }

}
