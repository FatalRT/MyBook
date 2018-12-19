package com.example.taoch.mybook;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import java.util.Calendar;
public class NewNoteActivity extends AppCompatActivity {

    private ImageView save;
    private EditText text,title;
    private  int  year,day,month;
    private Calendar cal;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_note);

        save = (ImageView) findViewById(R.id.btn_save);
        text = (EditText)findViewById(R.id.text);
        title = (EditText)findViewById(R.id.title);
        cal = Calendar.getInstance();
        year=cal.get(Calendar.YEAR);
        month=cal.get(Calendar.MONTH);
        day=cal.get(Calendar.DAY_OF_MONTH);
        month=month+1;

        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!title.getText().toString().equals("")){

                    note note = new note();
                    note.setData(""+day);
                    note.setTitle(title.getText().toString());
                    note.setText(text.getText().toString());
                    note.setYear(""+year);
                    note.setMonth(""+month);
                    note.save();
                    Toast toast=Toast.makeText(NewNoteActivity.this,"保存成功",Toast.LENGTH_LONG);
                    toast.show();
                    text.setText("");
                    title.setText("");
                    Intent s = new Intent(NewNoteActivity.this, NoteActivity.class);
                    NewNoteActivity.this.startActivity(s);
                    NewNoteActivity.this.finish();
                }
                else {
                    Toast toast=Toast.makeText(NewNoteActivity.this,"标题不可为空",Toast.LENGTH_LONG);
                    toast.show();
                }

            }
        });
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK){
            Intent i = new Intent(NewNoteActivity.this, NoteActivity.class);
            i.putExtra("flag",1);
            startActivity(i);
            overridePendingTransition(R.anim.in_from_left, R.anim.out_to_right);
            finish();
        }
        return true;
    }
}
