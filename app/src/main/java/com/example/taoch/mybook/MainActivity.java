package com.example.taoch.mybook;
/*
* 启动页面
*/
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class MainActivity extends AppCompatActivity {

    private  final int SPLASH_DISPLAY_LENGHT = 4000;//两秒后进入系统，时间可自行调整
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        getSupportActionBar().hide();//隐藏标题栏
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

  //在BZLaunchActivity停留2秒然后进入BZLaunchActivity
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
