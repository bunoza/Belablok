package com.example.belablok;

import android.graphics.Typeface;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class NameViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

    private TextView tvMi;
    private TextView tvVi;
    private NameClickListener clickListener;


    public NameViewHolder(@NonNull View itemView, NameClickListener listener ) {
        super(itemView);
        this.clickListener = listener;
        tvMi =  itemView.findViewById(R.id.tvMi);
        tvVi =  itemView.findViewById(R.id.tvVi);
        itemView.setOnClickListener(this);
    }

    public void setName(Integer x, Integer y){
        tvMi.setText(x.toString());
        tvVi.setText(y.toString());

        if(clickListener ==null){

            if(x > y){
                tvMi.setTypeface(null, Typeface.BOLD);
            }else if(x < y){
                tvVi.setTypeface(null, Typeface.BOLD);
            }else{
                tvMi.setTypeface(null, Typeface.BOLD);
                tvVi.setTypeface(null, Typeface.BOLD);
            }        }
    }

    @Override
    public void onClick(View v) {
        if(clickListener !=null){
            clickListener.onNameClick(getAdapterPosition());
        }
    }
}