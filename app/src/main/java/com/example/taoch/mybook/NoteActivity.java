package com.example.taoch.mybook;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import org.litepal.LitePal;
import java.util.ArrayList;
import java.util.List;

public class NoteActivity extends AppCompatActivity {

    private List<note> note_classesList = new ArrayList<>();
    private ImageView newnote;
    private NoteAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_note);

        initBianqian();
        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.recycle_view);
        GridLayoutManager layoutManager = new GridLayoutManager(this,2);
        recyclerView.setLayoutManager(layoutManager);
        adapter = new NoteAdapter(note_classesList);
        recyclerView.setAdapter(adapter);
        newnote = (ImageView)findViewById(R.id.newnote);
        newnote.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent s = new Intent(NoteActivity.this, NewNoteActivity.class);
                NoteActivity.this.startActivity(s);
                NoteActivity.this.finish();
            }
        });
    }
    private void initBianqian() {
        note_classesList.clear();
        note_classesList=LitePal.findAll(note.class);
    }

}
