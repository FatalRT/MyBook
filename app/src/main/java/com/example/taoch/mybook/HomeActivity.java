package com.example.taoch.mybook;


import android.content.Intent;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.TextView;
import android.widget.Toast;

import com.hitomi.cmlibrary.CircleMenu;
import com.hitomi.cmlibrary.OnMenuSelectedListener;
import com.hitomi.cmlibrary.OnMenuStatusChangeListener;

public class HomeActivity extends AppCompatActivity {
    private  final int Waitdo = 1800;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        CircleMenu circleMenu = (CircleMenu) findViewById(R.id.circle_menu);
        /*
         *setMainMenu 设置主按钮(打开/关闭)的背景色，以及打开/关闭的图标。图标支持 Resource、Bitmap、Drawable 形式
         * addSubMenu 添加一个子菜单项，包括子菜单的背景色以及图标 。图标支持 Resource、Bitmap、Drawable 形式
         */
        circleMenu.setMainMenu(Color.parseColor("#FFFFFF"), R.mipmap.ic_launcher_round, R.mipmap.ic_launcher_round)
                .addSubMenu(Color.parseColor("#81C7D4"), R.mipmap.ic_launcher_round)
                .addSubMenu(Color.parseColor("#69B0AC"), R.mipmap.ic_launcher_round)
                .addSubMenu(Color.parseColor("#F4A7B9"), R.mipmap.ic_launcher_round)
                .addSubMenu(Color.parseColor("#ECB88A"), R.mipmap.ic_launcher_round)
                .setOnMenuSelectedListener(new OnMenuSelectedListener() {

                    @Override
                    public void onMenuSelected(int index) {
                        switch (index)
                        {
                            case 0:
                                Toast.makeText(HomeActivity.this,"登录",Toast.LENGTH_SHORT).show();break;
                            case 1:
                                Toast.makeText(HomeActivity.this,"账本",Toast.LENGTH_SHORT).show();break;
                            case 2:
                                Toast.makeText(HomeActivity.this,"便签",Toast.LENGTH_SHORT).show();break;
                            case 3:
                                new android.os.Handler().postDelayed(new Runnable() {
                                    @Override
                                    public void run() {
                                        Intent mainIntent = new Intent(HomeActivity.this,AboutActivity.class);
                                        HomeActivity.this.startActivity(mainIntent);
                                    }
                                },Waitdo);
                                break;
                        }
                    }

                }).setOnMenuStatusChangeListener(new OnMenuStatusChangeListener() {

            @Override
            public void onMenuOpened() {
            }

            @Override
            public void onMenuClosed() {
            }

        });
    }
}
