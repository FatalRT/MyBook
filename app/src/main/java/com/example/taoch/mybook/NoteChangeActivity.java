package com.example.taoch.mybook;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import org.litepal.LitePal;
import org.litepal.crud.DataSupport;

import java.util.Calendar;
public class NoteChangeActivity extends AppCompatActivity {

    private ImageView btn_delete,btn_resave;
    private EditText title,text;

    public static final String BIANQIAN_ID = "bianqian_id";
    public static final String BIANQIAN_TITLE = "bianqian_title";
    public static final String BIANQIAN_TEXT = "bianqian_text";
    private  int  year,day,month;
    private Calendar cal;
    private String id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_note_change);

        btn_delete = (ImageView)findViewById(R.id.bianqian_delete);
        btn_resave = (ImageView)findViewById(R.id.btn_resave);
        title = (EditText)findViewById(R.id.title) ;
        text = (EditText)findViewById(R.id.text) ;
        Intent intent_get = getIntent();
        id =intent_get.getStringExtra(BIANQIAN_ID) ;
        String title_text =intent_get.getStringExtra(BIANQIAN_TITLE) ;
        String text_text =intent_get.getStringExtra(BIANQIAN_TEXT) ;
        title.setText(title_text);
        text.setText(text_text);
        cal = Calendar.getInstance();
        year=cal.get(Calendar.YEAR);
        month=cal.get(Calendar.MONTH);
        day=cal.get(Calendar.DAY_OF_MONTH);

        btn_resave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!title.getText().toString().equals("")){

                    note note = new note();
                    note.setData(""+day);
                    note.setTitle(title.getText().toString());
                    note.setText(text.getText().toString());
                    note.setYear(""+year);
                    note.setMonth(""+(++month));
                    note.updateAll("id = ? ",id);
                    Toast toast=Toast.makeText(NoteChangeActivity.this,"保存成功",Toast.LENGTH_LONG);
                    toast.show();
                    Intent i = new Intent(NoteChangeActivity.this, NoteActivity.class);
                    startActivity(i);
                    overridePendingTransition(R.anim.in_from_left, R.anim.out_to_right);
                    finish();
                }
                else {
                    Toast toast=Toast.makeText(NoteChangeActivity.this,"标题不可为空",Toast.LENGTH_LONG);
                    toast.show();
                }

            }
        });
        btn_delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LitePal.deleteAll(note.class,"id = ?",id);
                Intent i = new Intent(NoteChangeActivity.this, NoteActivity.class);
                startActivity(i);
                overridePendingTransition(R.anim.in_from_left, R.anim.out_to_right);
                Toast toast=Toast.makeText(NoteChangeActivity.this,"删除成功",Toast.LENGTH_LONG);
                toast.show();
                finish();

            }
        });

    }
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK){
            Intent i = new Intent(NoteChangeActivity.this,  NoteActivity.class);
            startActivity(i);
            overridePendingTransition(R.anim.in_from_left, R.anim.out_to_right);
            finish();
        }
        return true;
    }
}

