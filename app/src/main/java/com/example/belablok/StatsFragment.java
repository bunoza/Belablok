package com.example.belablok;

import android.graphics.Color;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.jjoe64.graphview.GraphView;
import com.jjoe64.graphview.LegendRenderer;
import com.jjoe64.graphview.series.DataPoint;
import com.jjoe64.graphview.series.LineGraphSeries;

import java.util.ArrayList;
import java.util.List;

public class StatsFragment extends Fragment {

    private List<List<Integer>> dataListMi2D;
    private List<List<Integer>> dataListVi2D;
    Partije partije;
    private List<Integer> dataList1;
    private List<Integer> dataList2;
    private TextView winRateMi;
    private TextView winRateVi;
    private TextView tvbrojZvanjaMi;
    private TextView tvbrojZvanjaVi;
    private int brojOdigranihPartija = 0;
    private int brojPobjedenihMi = 0;
    private int brojPobjedenihVi = 0;
    private int brojZvanjaMi;
    private int brojZvanjaVi;
    private GraphView graphView;


    public static StatsFragment newInstance(List<Integer> dataList1, List<Integer> dataList2, Partije partije, int brojZvanjaMi, int brojZvanjaVi) {
        StatsFragment fragment = new StatsFragment();
        Bundle args = new Bundle();
        args.putIntegerArrayList("datax", (ArrayList<Integer>) dataList1);
        args.putIntegerArrayList("datay", (ArrayList<Integer>) dataList2);
        args.putSerializable("data2D", partije);
        args.putInt("brojZvanjaMi", brojZvanjaMi);
        args.putInt("brojZvanjaVi", brojZvanjaVi);
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
        updateTV();
        updateGraphData();

    }


    private void updateGraphData() {
        DataPoint[] pointsMi = new DataPoint[dataListMi2D.get(0).size()+1];
        DataPoint[] pointsVi = new DataPoint[dataListVi2D.get(0).size()+1];
        pointsMi[0] = new DataPoint(0,0);
        pointsVi[0] = new DataPoint(0,0);
        int i;
        int sumMi = 0, sumVi = 0;
        for(i = 0; i < dataListVi2D.get(0).size(); i++){
            sumMi += dataListMi2D.get(0).get(i);
            sumVi += dataListVi2D.get(0).get(i);
            pointsMi[i+1] = new DataPoint(i+1, sumMi);
            pointsVi[i+1] = new DataPoint(i+1, sumVi);
        }
        LineGraphSeries<DataPoint> seriesMi = new LineGraphSeries<>(pointsMi);
        LineGraphSeries<DataPoint> seriesVi = new LineGraphSeries<>(pointsVi);
        graphView.getViewport().setXAxisBoundsManual(true);
        graphView.getViewport().setYAxisBoundsManual(true);
        graphView.setTitle("Tijek partije");
        graphView.removeAllSeries();
        seriesMi.setColor(Color.BLUE);
        seriesMi.setTitle("Mi");
        seriesMi.setThickness(5);
        seriesVi.setThickness(5);
        graphView.addSeries(seriesMi);
        seriesVi.setColor(Color.RED);
        seriesVi.setTitle("Vi");
        graphView.addSeries(seriesVi);
        graphView.getViewport().setMinY(0);
        graphView.getViewport().setMaxY(1001);
        graphView.getViewport().setMinX(0);
        graphView.getViewport().setMaxX(dataListMi2D.get(0).size());
        graphView.getLegendRenderer().setVisible(true);
        graphView.getLegendRenderer().setAlign(LegendRenderer.LegendAlign.BOTTOM);
        graphView.getLegendRenderer().setBackgroundColor(Color.WHITE);
        graphView.getGridLabelRenderer().setHorizontalAxisTitle("Broj miješanja");
        graphView.getGridLabelRenderer().setVerticalAxisTitle("Bodovi");
        graphView.getViewport().setScrollable(true);
    }


    private void updateTV() {
        winRateMi.setText(getResources().getString(R.string.winrate, 100.0*brojPobjedenihMi/brojOdigranihPartija));
        winRateVi.setText(getResources().getString(R.string.winrate, 100.0*brojPobjedenihVi/brojOdigranihPartija));
        tvbrojZvanjaMi.setText(getResources().getString(R.string.broj_zvanja, brojZvanjaMi));
        tvbrojZvanjaVi.setText(getResources().getString(R.string.broj_zvanja, brojZvanjaVi));
    }

    private void calculateWinRates() {
        brojOdigranihPartija = dataListVi2D.size();
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
        tvbrojZvanjaMi = view.findViewById(R.id.brojZvanjaMi);
        tvbrojZvanjaVi = view.findViewById(R.id.brojZvanjaVi);
        graphView = view.findViewById(R.id.graphView);
    }

    private void setUpData() {
        this.dataList1 = getArguments().getIntegerArrayList("datax");
        this.dataList2 = getArguments().getIntegerArrayList("datay");

        if(getArguments().getSerializable("data2D") != null){
            partije = (Partije) getArguments().getSerializable("data2D");
            dataListMi2D = partije.getListaMi();
            dataListVi2D = partije.getListaVi();
        }
        brojZvanjaMi = getArguments().getInt("brojZvanjaMi", 0 );
        brojZvanjaVi = getArguments().getInt("brojZvanjaVi", 0 );
    }
}