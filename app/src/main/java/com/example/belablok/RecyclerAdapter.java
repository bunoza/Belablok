package com.example.belablok;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class RecyclerAdapter extends RecyclerView.Adapter<NameViewHolder> {

    private List<Integer> dataListMi = new ArrayList<>();
    private List<Integer> dataListVi = new ArrayList<>();
    private NameClickListener clickListener;

    public RecyclerAdapter(NameClickListener clickListener) {
        this.clickListener = clickListener;
    }
    public RecyclerAdapter(){
    }

    @NonNull
    @Override
    public NameViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View cellView = LayoutInflater.from(parent.getContext()).inflate(R.layout.cell_name, parent, false);
        return new NameViewHolder(cellView, clickListener);
    }

    @Override
    public void onBindViewHolder(@NonNull NameViewHolder holder, int position) {
        holder.setName(dataListMi.get(position), dataListVi.get(position));
    }

    @Override
    public int getItemCount() {
        return dataListMi.size();
    }

    public void addData(List<Integer> data1 , List<Integer> data2){
        this.dataListMi.clear();
        this.dataListVi.clear();
        this.dataListMi.addAll(data1);
        this.dataListVi.addAll(data2);
        notifyDataSetChanged();
    }
//    public void addData2D(List<List<Integer>> data1 , List<List<Integer>> data2){
//        this.dataListMi2D.clear();
//        this.dataListVi2D.clear();
//        this.dataListMi2D.addAll(data1);
//        this.dataListVi2D.addAll(data2);
//        notifyDataSetChanged();
//    }

    public void addNewCell(int x, int y){
            dataListMi.add(getItemCount(), x);
            dataListVi.add(getItemCount(), y);
            notifyItemInserted(getItemCount());
    }

    public void removeCell(int position){
        if(dataListMi.size() > position && dataListVi.size() > position){
            dataListMi.remove(position);
            dataListVi.remove(position);
            notifyItemRemoved(position);
        }
    }
}
