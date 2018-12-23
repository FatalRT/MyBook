package com.example.taoch.mybook;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import org.litepal.LitePal;
import org.litepal.crud.DataSupport;
import org.litepal.tablemanager.Connector;

import java.util.List;

public class LoginActivity extends AppCompatActivity {

    private EditText name;
    private EditText paw;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        final Button login = (Button) findViewById(R.id.login);
        final Button signup= (Button) findViewById(R.id.signup);
        name =(EditText)findViewById(R.id.name);
        paw = (EditText)findViewById(R.id.paw) ;

        //登录
      login.setOnClickListener(new View.OnClickListener() {
          @Override
          public void onClick(View v) {

              //设置成功匹配的标志，假设为false，即假设不匹配
              boolean flag=false;
              List<user> loginusers = LitePal.findAll(user.class);
              //把数据库里面的数据取出，存在list中
              for (user loginuser : loginusers) {

                  //检测是否匹配
                  if(name.getText().toString().trim().equals(loginuser.getName()) &&
                          (paw.getText().toString().trim().equals(loginuser.getPaw())))
                  {
                      //匹配就设true
                      flag=true;
                  }
              }
              if (flag)
              {
                  //如果匹配才进入主页面
                  Intent s = new Intent(LoginActivity.this, HomeActivity.class);
                  startActivity(s);
                  LoginActivity.this.finish();
              }else{
                  Toast.makeText(LoginActivity.this,"登录失败,请检查用户名或密码",Toast.LENGTH_SHORT).show();
              }

          }
      });

      //注册
      signup.setOnClickListener(new View.OnClickListener() {
          @Override
          public void onClick(View v) {
              user signupuser;
              LitePal.getDatabase();
              signupuser=new user();
              String strname=name.getText().toString();
              String strpassword=paw.getText().toString();
              signupuser.setName(strname);
              signupuser.setPaw(strpassword);
              signupuser.save();
              Toast.makeText(LoginActivity.this,"注册成功，请登录",Toast.LENGTH_SHORT).show();

          }
      });
    }
}
