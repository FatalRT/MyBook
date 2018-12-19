package com.example.taoch.mybook;


import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

public class NoteAdapter extends RecyclerView.Adapter<NoteAdapter.ViewHolder> {

    private Context mcontext;

    private List<note> mbianqianList;
    @NonNull
    @Override
    public NoteAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        if (mcontext == null){
            mcontext = viewGroup.getContext();
        }

        View view = LayoutInflater.from(mcontext).inflate(R.layout.note_item,viewGroup,false);
        final ViewHolder holder = new ViewHolder(view);
        holder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int position = holder.getAdapterPosition();
                note note_class = mbianqianList.get(position);
                Intent intent = new Intent(mcontext,NoteChangeActivity.class);
                intent.putExtra(NoteChangeActivity.BIANQIAN_ID,""+note_class.getId());
                intent.putExtra(NoteChangeActivity.BIANQIAN_TITLE,note_class.getTitle());
                intent.putExtra(NoteChangeActivity.BIANQIAN_TEXT,note_class.getText());
                mcontext.startActivity(intent);
                ((Activity)mcontext).overridePendingTransition(R.anim.in_from_right, R.anim.out_to_left);
                ((Activity)mcontext).finish();
            }
        });
        return holder;
    }
    public NoteAdapter(List<note> bianqian_classeslist){
        mbianqianList = bianqian_classeslist;
    }

    @Override
    public void onBindViewHolder(@NonNull NoteAdapter.ViewHolder viewHolder, int i) {
        note note_class = mbianqianList.get(i);
        viewHolder.bianqianData.setText(note_class.getYear()+"年"+note_class.getMonth()+"月"+note_class.getData()+"日");
        viewHolder.bianqianTitle.setText(note_class.getTitle());

    }

    @Override
    public int getItemCount() {
        return mbianqianList.size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        CardView cardView;
        TextView bianqianData,bianqianTitle;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            cardView = (CardView) itemView;
            bianqianData = (TextView) itemView.findViewById(R.id.bianqian_data);
            bianqianTitle = (TextView) itemView.findViewById(R.id.bianqian_title);
        }
    }
}
