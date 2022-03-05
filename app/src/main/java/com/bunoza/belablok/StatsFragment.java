package com.bunoza.belablok;

import android.graphics.Color;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;

import com.jjoe64.graphview.GraphView;
import com.jjoe64.graphview.LegendRenderer;
import com.jjoe64.graphview.series.DataPoint;
import com.jjoe64.graphview.series.LineGraphSeries;

import java.util.ArrayList;
import java.util.List;


public class StatsFragment extends Fragment implements AdapterView.OnItemSelectedListener {

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
    private List<String> dropDownArray;
    private GraphView graphView;
    Spinner spinner;
    ArrayAdapter<CharSequence> adapter;
    LineGraphSeries<DataPoint> seriesMi;
    LineGraphSeries<DataPoint> seriesVi;


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
        return inflater.inflate(R.layout.fragment_stats, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        setUpViews(view);
        setUpData();
        calculateWinRates();
        updateTV();
        setUpDataForSpinner();
        setUpSpinner();
        selectLastPlayed();
    }

    private void setUpDataForSpinner() {
        dropDownArray = new ArrayList<>();
        String temp;
        int i;
        for(i = 0; i < dataList1.size(); i++){
            temp = i+1 +". " + dataList1.get(i) + " - " + dataList2.get(i);
            dropDownArray.add(i, temp);
        }
    }

    private void selectLastPlayed() {
        spinner.setSelection(dropDownArray.size()-1, true);
    }

    private void setUpSpinner() {
        CharSequence[] cs = dropDownArray.toArray(new CharSequence[dropDownArray.size()]);
        adapter = new ArrayAdapter<CharSequence>(getContext(), android.R.layout.simple_spinner_item, cs);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
    }


    private void updateGraphData(int odabranaPartija) {
        graphView.removeAllSeries();
        DataPoint[] pointsMi = new DataPoint[dataListMi2D.get(odabranaPartija).size() + 1];
        DataPoint[] pointsVi = new DataPoint[dataListVi2D.get(odabranaPartija).size() + 1];
        pointsMi[0] = new DataPoint(0, 0);
        pointsVi[0] = new DataPoint(0, 0);
        int i;
        int sumMi = 0, sumVi = 0;
        for (i = 0; i < dataListMi2D.get(odabranaPartija).size(); i++) {
            sumMi += dataListMi2D.get(odabranaPartija).get(i);
            sumVi += dataListVi2D.get(odabranaPartija).get(i);
            pointsMi[i + 1] = new DataPoint(i + 1, sumMi);
            pointsVi[i + 1] = new DataPoint(i + 1, sumVi);
        }
        seriesMi = new LineGraphSeries<>(pointsMi);
        seriesVi = new LineGraphSeries<>(pointsVi);
        graphView.addSeries(seriesMi);
        graphView.addSeries(seriesVi);

        graphView.getViewport().setXAxisBoundsManual(true);
        graphView.getViewport().setYAxisBoundsManual(true);

        graphView.setTitle("Tijek odabrane partije");
        seriesMi.setColor(Color.BLUE);
        seriesVi.setColor(Color.RED);
        seriesMi.setTitle("Mi");
        seriesVi.setTitle("Vi");
        seriesMi.setThickness(5);
        seriesVi.setThickness(5);
        seriesMi.setAnimated(true);
        seriesVi.setAnimated(true);
        graphView.getViewport().setMinY(0);
        graphView.setDrawingCacheBackgroundColor(Color.rgb(255,255,255));
        graphView.getViewport().setBorderColor(Color.rgb(255,255,255));
        graphView.getGridLabelRenderer().setGridColor(Color.rgb(255,255,255));
        graphView.getGridLabelRenderer().setHorizontalLabelsColor(Color.rgb(255,255,255));
        graphView.getGridLabelRenderer().setVerticalLabelsColor(Color.rgb(255,255,255));
        graphView.setTitleColor(Color.rgb(255,255,255));
        graphView.getGridLabelRenderer().setHorizontalAxisTitleColor(Color.rgb(255,255,255));
        graphView.getGridLabelRenderer().setVerticalAxisTitleColor(Color.rgb(255,255,255));
        graphView.setTitleTextSize(45);
        graphView.getViewport().setMaxY(1001);
        graphView.getViewport().setMinX(0);
        graphView.getViewport().setMaxX(dataListMi2D.get(odabranaPartija).size());
        graphView.getLegendRenderer().setVisible(true);
        graphView.getLegendRenderer().setAlign(LegendRenderer.LegendAlign.BOTTOM);
        graphView.getLegendRenderer().setBackgroundColor(Color.WHITE);
        graphView.getGridLabelRenderer().setHorizontalAxisTitle("Broj miješanja");
        graphView.getGridLabelRenderer().setVerticalAxisTitle("Bodovi");
    }


    private void updateTV() {
        winRateMi.setText(getResources().getString(R.string.winrate, 100.0*brojPobjedenihMi/brojOdigranihPartija));
        winRateVi.setText(getResources().getString(R.string.winrate, 100.0*brojPobjedenihVi/brojOdigranihPartija));
    }

    private void calculateWinRates() {
        brojOdigranihPartija = dataList1.size();
        int i;
        for(i = 0; i < brojOdigranihPartija; i++){
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
        graphView = view.findViewById(R.id.graphView);
        spinner = view.findViewById(R.id.spinner);
    }

    private void setUpData() {
        this.dataList1 = getArguments().getIntegerArrayList("datax");
        this.dataList2 = getArguments().getIntegerArrayList("datay");

        if(getArguments().getSerializable("data2D") != null){
            dataListMi2D = new ArrayList<>();
            dataListVi2D = new ArrayList<>();
            partije = (Partije) getArguments().getSerializable("data2D");

            for( List<Integer> sublist : partije.getListaMi()) {
                this.dataListMi2D.add(new ArrayList<>(sublist));
            }
            for( List<Integer> sublist : partije.getListaVi()) {
                this.dataListVi2D.add(new ArrayList<>(sublist));
            }        }

        spinner.setOnItemSelectedListener(this);
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        updateGraphData(position);
        for(int i = 0; i < parent.getChildCount(); i++){
            ((TextView) parent.getChildAt(i)).setTextColor(Color.rgb(250,250,250));
        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {
        updateGraphData(dataList1.size());
    }
}