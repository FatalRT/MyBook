package com.example.taoch.mybook;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.KeyEvent;

public class ExpendActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_expend);
    }
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK){
            Intent i = new Intent(ExpendActivity.this,  BillActivity.class);
            startActivity(i);
            overridePendingTransition(R.anim.in_from_left, R.anim.out_to_right);
            finish();
        }
        return true;
    }

}
