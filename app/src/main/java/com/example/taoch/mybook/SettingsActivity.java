package com.example.taoch.mybook;

import android.content.ContentValues;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import org.litepal.LitePal;

import java.util.List;

public class SettingsActivity extends AppCompatActivity {
    private EditText uname;
    private EditText paw;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        final Button change=(Button) findViewById(R.id.change);

        uname =(EditText)findViewById(R.id.uname);
        paw = (EditText)findViewById(R.id.paw) ;

        change.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boolean flag = false;
                int id = 1;
                String paws = "";
                List<user> users = LitePal.findAll(user.class);
                //把数据库里面的数据取出，存在list中
                for (user User : users) {
                    //检测是否匹配
                    if (uname.getText().toString().trim().equals(User.getName())) {
                        flag = true;
                        id = User.getId();
                        paws = paw.getText().toString();
                    }
                }
                if (flag) {
                    ContentValues values = new ContentValues();
                    values.put("paw", paws);
                    LitePal.update(user.class, values, id);
                    LitePal.update(user.class, values, id);
                    Toast.makeText(SettingsActivity.this, "修改成功,请重新登录", Toast.LENGTH_SHORT).show();
                    Intent mainIntent = new Intent(SettingsActivity.this, LoginActivity.class);
                    SettingsActivity.this.startActivity(mainIntent);
                } else {
                    Toast.makeText(SettingsActivity.this, "昵称错误，请重新输入", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}
