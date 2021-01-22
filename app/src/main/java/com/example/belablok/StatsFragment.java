package com.example.belablok;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class StatsFragment extends Fragment {

    private List<List<Integer>> dataListMi2D = new ArrayList<>();
    private List<List<Integer>> dataListVi2D = new ArrayList<>();
    Partije partije = new Partije();
    private List<Integer> dataList1;
    private List<Integer> dataList2;
    private TextView winRateMi;
    private TextView winRateVi;
    private int brojOdigranihPartija = 0;
    private int brojPobjedenihMi = 0;
    private int brojPobjedenihVi = 0;

    public static StatsFragment newInstance(List<Integer> dataList1, List<Integer> dataList2, Partije partije) {
        StatsFragment fragment = new StatsFragment();
        Bundle args = new Bundle();
        args.putIntegerArrayList("datax", (ArrayList<Integer>) dataList1);
        args.putIntegerArrayList("datay", (ArrayList<Integer>) dataList2);
        args.putSerializable("data2D", partije);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_stats, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        setUpViews(view);
        setUpData();
        calculateWinRates();
        showWinRates();
    }

    private void showWinRates() {
        winRateMi.setText(getResources().getString(R.string.winrate, 100.0*brojPobjedenihMi/brojOdigranihPartija));
        winRateVi.setText(getResources().getString(R.string.winrate, 100.0*brojPobjedenihVi/brojOdigranihPartija));
    }

    private void calculateWinRates() {
        brojOdigranihPartija = dataListMi2D.size();
        int i;
        for(i = 0; i < dataListMi2D.size(); i++){
            if(dataList1.get(i) < dataList2.get(i)){
                brojPobjedenihVi++;
            }else if(dataList1.get(i) > dataList2.get(i)){
                brojPobjedenihMi++;
            }else{
            }
        }
    }

    private void setUpViews(View view) {
        winRateMi = view.findViewById(R.id.tvWinRateMi);
        winRateVi = view.findViewById(R.id.tvWinRateVi);
    }

    private void setUpData() {
        this.dataList1 = getArguments().getIntegerArrayList("datax");
        this.dataList2 = getArguments().getIntegerArrayList("datay");

        if(getArguments().getSerializable("data2D") != null){
            partije = (Partije) getArguments().getSerializable("data2D");
            dataListMi2D.clear();
            dataListVi2D.clear();
            dataListMi2D.addAll(partije.getListaMi());
            dataListVi2D.addAll(partije.getListaVi());
        }

    }
}