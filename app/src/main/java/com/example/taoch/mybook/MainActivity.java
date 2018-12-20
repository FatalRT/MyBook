package com.example.taoch.mybook;
/*
* 启动页面
*/
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Window;
import android.view.WindowManager;

import org.litepal.LitePal;

public class MainActivity extends AppCompatActivity {

    private  final int SPLASH_DISPLAY_LENGHT = 1500;//1.5秒后进入系统，时间可自行调整
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        Window window = getWindow();
        //隐藏状态栏
        //定义全屏参数
        int flag=WindowManager.LayoutParams.FLAG_FULLSCREEN;
        //设置当前窗体为全屏显示
        window.setFlags(flag, flag);

//        getSupportActionBar().hide();//隐藏标题栏
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        LitePal.getDatabase();
        //在startActivity停留1.5秒然后进入HomeActivity
        new android.os.Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent mainIntent = new Intent(MainActivity.this,HomeActivity.class);
                MainActivity.this.startActivity(mainIntent);
                MainActivity.this.finish();
            }
        },SPLASH_DISPLAY_LENGHT);

    }
}
