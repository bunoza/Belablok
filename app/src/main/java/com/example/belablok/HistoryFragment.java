package com.example.belablok;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class HistoryFragment extends Fragment {

    private List<Integer> dataList1;
    private List<Integer> dataList2;
    private RecyclerView recycler;
    private RecyclerAdapter recyclerAdapter;
    private TextView sumaMi;
    private TextView sumaVi;
    private Integer pobjedeMi = 0;
    private Integer pobjedeVi = 0;

    public static HistoryFragment newInstance(List<Integer> dataList1, List<Integer> dataList2) {
        HistoryFragment fragment = new HistoryFragment();
        Bundle args = new Bundle();
        args.putIntegerArrayList("datax", (ArrayList<Integer>) dataList1);
        args.putIntegerArrayList("datay", (ArrayList<Integer>) dataList2);
        fragment.setArguments(args);
        return fragment;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_history, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initUI(view);
        this.dataList1 = getArguments().getIntegerArrayList("datax");
        this.dataList2 = getArguments().getIntegerArrayList("datay");
        setupRecyclerData();
        if(dataList1.get(0) == null)
        Toast.makeText(getContext(), "no data", Toast.LENGTH_SHORT).show();
        for(int i = 0; i < dataList1.size(); i++){
            if(dataList1.get(i) < dataList2.get(i)){
                pobjedeVi++;
            }else if(dataList1.get(i) > dataList2.get(i)){
                pobjedeMi++;
            }else{
                pobjedeMi += 1;
                pobjedeVi += 1;
            }
        }
        sumaMi.setText(pobjedeMi.toString());
        sumaVi.setText(pobjedeVi.toString());
    }

    private void initUI(View view) {
        recycler = view.findViewById(R.id.RecyclerViewMi);
        recycler.setLayoutManager(new LinearLayoutManager(getActivity()));
        recycler.addItemDecoration(new DividerItemDecoration(recycler.getContext(), DividerItemDecoration.VERTICAL));
        recyclerAdapter = new RecyclerAdapter();
        recycler.setAdapter(recyclerAdapter);
        sumaMi = view.findViewById(R.id.pobjedeMi);
        sumaVi = view.findViewById(R.id.pobjedeVi);
    }
    private void setupRecyclerData() {
        this.recyclerAdapter.addData(dataList1, dataList2);
    }

}
