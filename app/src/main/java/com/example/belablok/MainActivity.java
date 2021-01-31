package com.example.belablok;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;
import android.os.Handler;

import com.google.android.material.floatingactionbutton.FloatingActionButton;


import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class MainActivity extends AppCompatActivity implements NameClickListener{

    private static final int SECOND_ACTIVITY_REQUEST_CODE = 0;
    private static  int THIRD_ACTIVITY_REQUEST_CODE = 1;

    FloatingActionButton fab;
    boolean doubleBackToExitPressedOnce = false;
    private List<Integer> nasaIgraHistory = new ArrayList<>();
    private List<Integer> vasaIgraHistory = new ArrayList<>();
    private List<List<Integer>> dataListMi2D = new ArrayList<>();
    private List<List<Integer>> dataListVi2D = new ArrayList<>();
    private List<Integer> nasaIgra;
    private List<Integer> vasaIgra;
    static String[] dealeri = {"Ja", "Desni protivnik", "Partner", "Lijevi protivnik"};
    static int dealerCounter;
    AlertDialog.Builder builderGotovaIgra, builderNovaIgra;
    TextView djelitelj;
    TextView sumaMi, sumaVi;
    RecyclerAdapter recyclerAdapter;
    RecyclerView recycler;
    private Partije partije = new Partije();
    int brojZvanjaMi, brojZvanjaVi = 0;
    boolean gotovaIgra = false;


    @Override
    protected void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        StringBuilder stringBuilder1 = new StringBuilder();
        StringBuilder stringBuilder2 = new StringBuilder();
        for(int i = 0; i < nasaIgra.size(); i++){
            if(i !=0){
                stringBuilder1.append(",");
                stringBuilder2.append(",");
            }
            stringBuilder1.append(nasaIgra.get(i));
            stringBuilder2.append(vasaIgra.get(i));
        }
        outState.putInt("DEALER_COUNTER", dealerCounter);
        try{
        outState.putString("NASA_IGRA", stringBuilder1.toString());
        outState.putString("VASA_IGRA", stringBuilder2.toString());
        }
        catch(NumberFormatException ignored){

        }

    }


    @Override
    protected void onRestoreInstanceState(@NonNull Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);

        String[] dividers1 = savedInstanceState.getString("NASA_IGRA").split(",");
        String[] dividers2 = savedInstanceState.getString("VASA_IGRA").split(",");
        try {
            for (int i = 0; i < dividers1.length; i++) {
                nasaIgra.add(Integer.parseInt(dividers1[i]));
                vasaIgra.add(Integer.parseInt(dividers2[i]));
            }
        }
        catch (NumberFormatException ignored){

        }
            updateTable();
            updateSums();
            String temp = getString(R.string.dijeli) + dealeri[savedInstanceState.getInt("DEALER_COUNTER")];
           djelitelj.setText(temp);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.mymenu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == R.id.mybutton) {
            if(!nasaIgraHistory.isEmpty() && !vasaIgraHistory.isEmpty()){
                Intent intent = new Intent(MainActivity.this, StatsActivity.class);
                intent.putIntegerArrayListExtra("nasaHistory", (ArrayList<Integer>) nasaIgraHistory);
                intent.putIntegerArrayListExtra("vasaHistory", (ArrayList<Integer>) vasaIgraHistory);
                partije.setListaMi(dataListMi2D);
                partije.setListaVi(dataListVi2D);
                intent.putExtra("data2D", partije);
                intent.putExtra("brojZvanjaMi", brojZvanjaMi);
                intent.putExtra("brojZvanjaVi", brojZvanjaVi);
                startActivity(intent);
            }else{
                Toast.makeText(this, "Još nije odigrana ni jedna partija!", Toast.LENGTH_SHORT).show();
            }
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        if (doubleBackToExitPressedOnce) {
            super.onBackPressed();
            return;
        }
        this.doubleBackToExitPressedOnce = true;
        Toast.makeText(this, "Ako izađete, rezultati će se izgubiti.", Toast.LENGTH_SHORT).show();
        new Handler().postDelayed(new Runnable() {

            @Override
            public void run() {
                doubleBackToExitPressedOnce=false;
            }
        }, 2000);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        setContentView(R.layout.activity_main);
        initUI();

    }

    private void initUI() {
        builderGotovaIgra = new AlertDialog.Builder(MainActivity.this);
        builderGotovaIgra.setIcon(Drawable.createFromPath("drawable/ic_dialog.xml"));
        builderGotovaIgra.setTitle(getString(R.string.igra_je_gotova));
        builderGotovaIgra.setMessage(R.string.nastavkom_ce_se_obrisati);
        builderGotovaIgra.setPositiveButton(R.string.nastavi, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                gotovaIgra = false;
                updateDealerCounterOnWin();
                nasaIgra.clear();
                vasaIgra.clear();
                setupRecyclerData();
                updateSums();
            }
        });
        builderGotovaIgra.setNegativeButton("Odustani", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });

        builderNovaIgra = new AlertDialog.Builder(MainActivity.this);
        builderNovaIgra.setTitle("Nova igra?");
        builderNovaIgra.setMessage(R.string.nastavkom_ce_se_obrisati);
        builderNovaIgra.setPositiveButton(R.string.nastavi, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                updateDealerCounter();
                nasaIgra.clear();
                vasaIgra.clear();
                setupRecyclerData();
                updateSums();
                gotovaIgra = false;
            }
        });
        builderNovaIgra.setNegativeButton("Odustani", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });

        sumaVi = findViewById(R.id.sumaVi);
        sumaMi = findViewById(R.id.sumaMi);
        sumaVi.setText("0");
        sumaMi.setText("0");
        djelitelj = findViewById(R.id.dealer);
        if(djelitelj.getText().toString().trim().length() == 0)
            setDealerRandom();
        djelitelj.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                updateDealerCounter();
                return true;
            }
        });
        nasaIgra = new ArrayList<>();
        vasaIgra = new ArrayList<>();
        fab = findViewById(R.id.fab);
        recycler = findViewById(R.id.RecyclerViewMi);
        recycler.setLayoutManager(new LinearLayoutManager(this));
        recycler.addItemDecoration(new DividerItemDecoration(recycler.getContext(), DividerItemDecoration.VERTICAL));
        recyclerAdapter = new RecyclerAdapter(this);
        recycler.setAdapter(recyclerAdapter);

        fab.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {
               if (gameIsOver()) {
                   builderGotovaIgra.show();
               } else {

                   Intent intent = new Intent(MainActivity.this, InputActivity.class);
                   startActivityForResult(intent, SECOND_ACTIVITY_REQUEST_CODE);
               }
           }
        });

        fab.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                builderNovaIgra.show();
                return true;
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == SECOND_ACTIVITY_REQUEST_CODE) {
            if (resultCode == RESULT_OK) { // Activity.RESULT_OK
                String returnString = data.getStringExtra("keyName");
                updateDealerCounter();
                int nase, vase;
                String[] temp = returnString.split(" ");
                nase = Integer.parseInt(temp[0]);
                vase = Integer.parseInt(temp[1]);
                nasaIgra.add(nase);
                vasaIgra.add(vase);
                updateSums();
                if (gameIsOver()) {
                    gotovaIgra = true;
                    Toast.makeText(MainActivity.this, getString(R.string.igra_je_gotova), Toast.LENGTH_SHORT).show();
                    nasaIgraHistory.add(nasaIgraHistory.size(), Integer.parseInt(sumaMi.getText().toString()));
                    vasaIgraHistory.add(vasaIgraHistory.size(), Integer.parseInt(sumaVi.getText().toString()));
                    dataListMi2D.add(dataListMi2D.size(), new ArrayList<Integer>(nasaIgra));
                    dataListVi2D.add(dataListVi2D.size(), new ArrayList<Integer>(vasaIgra));
                    updateDealerCounterOnWin();
                }
                if (data.getBooleanExtra("brojZvanjaMi", false)) {
                    brojZvanjaMi++;
                } else {
                    brojZvanjaVi++;
                }
                updateTable();
            }
        }
        if (requestCode == THIRD_ACTIVITY_REQUEST_CODE) {
            if (resultCode == RESULT_OK) { // Activity.RESULT_OK
                String returnString = data.getStringExtra("keyName");
                int nase, vase;
                String[] temp = returnString.split(" ");
                nase = Integer.parseInt(temp[0]);
                vase = Integer.parseInt(temp[1]);
                nasaIgra.set(THIRD_ACTIVITY_REQUEST_CODE - 20, nase);
                vasaIgra.set(THIRD_ACTIVITY_REQUEST_CODE - 20, vase);
                updateSums();

                if (gameIsOver()) {
                    Toast.makeText(MainActivity.this, getString(R.string.igra_je_gotova), Toast.LENGTH_SHORT).show();
                    if (gotovaIgra) {
                        if (!nasaIgraHistory.isEmpty()) {
                            nasaIgraHistory.set(nasaIgraHistory.size() - 1, Integer.parseInt(sumaMi.getText().toString()));
                            vasaIgraHistory.set(vasaIgraHistory.size() - 1, Integer.parseInt(sumaVi.getText().toString()));
                            dataListMi2D.set(dataListMi2D.size() - 1, new ArrayList<Integer>(nasaIgra));
                            dataListVi2D.set(dataListVi2D.size() - 1, new ArrayList<Integer>(vasaIgra));
                        } else {
                            nasaIgraHistory.add(nasaIgraHistory.size(), Integer.parseInt(sumaMi.getText().toString()));
                            vasaIgraHistory.add(vasaIgraHistory.size(), Integer.parseInt(sumaVi.getText().toString()));
                            dataListMi2D.add(dataListMi2D.size(), new ArrayList<Integer>(nasaIgra));
                            dataListVi2D.add(dataListVi2D.size(), new ArrayList<Integer>(vasaIgra));
                        }
                    } else {
                        nasaIgraHistory.add(nasaIgraHistory.size(), Integer.parseInt(sumaMi.getText().toString()));
                        vasaIgraHistory.add(vasaIgraHistory.size(), Integer.parseInt(sumaVi.getText().toString()));
                        dataListMi2D.add(dataListMi2D.size(), new ArrayList<Integer>(nasaIgra));
                        dataListVi2D.add(dataListVi2D.size(), new ArrayList<Integer>(vasaIgra));
                        gotovaIgra = true;
                    }

                    updateDealerCounterOnWin();
                } else {
                    if (gotovaIgra) {
                        if (!nasaIgraHistory.isEmpty()) {
                            nasaIgraHistory.remove(nasaIgraHistory.size() - 1);
                            vasaIgraHistory.remove(vasaIgraHistory.size() - 1);
                            dataListMi2D.remove(dataListMi2D.size() - 1);
                            dataListVi2D.remove(dataListVi2D.size() - 1);
                            gotovaIgra = false;
                        } else {

                        }
                    } else {
                    }

                }
                updateTable();
                THIRD_ACTIVITY_REQUEST_CODE = 1;
            }
        }
    }

    public static int getRandomIntegerBetween(){
        Random rand = new Random();
        return rand.nextInt(4);
    }
    public static int getRandomIntegerBetween02(){
        Random rand = new Random();
        return rand.nextInt(2);
    }
    public void updateDealerCounter(){
        if(dealerCounter == 3){
            dealerCounter = 0;
        }else{
            dealerCounter++;
        }
        String text = getString(R.string.dijeli)+dealeri[dealerCounter];
        djelitelj.setText(text);
    }
    public void updateDealerCounterOnWin(){
        if(Integer.parseInt(sumaMi.getText().toString()) > Integer.parseInt(sumaVi.getText().toString())){
            if(getRandomIntegerBetween02() == 0){
                dealerCounter= 0;
            }else{
                dealerCounter = 2;
            }
            String text = getString(R.string.dijeli)+dealeri[dealerCounter];
            djelitelj.setText(text);
        }
        else if(Integer.parseInt(sumaMi.getText().toString()) < Integer.parseInt(sumaVi.getText().toString())){
            if(getRandomIntegerBetween02() == 0){
                dealerCounter= 1;
            }else{
                dealerCounter = 3;
            }
            String text = getString(R.string.dijeli)+dealeri[dealerCounter];
            djelitelj.setText(text);
        }
        else{
            setDealerRandom();
        }
    }
    public void setDealerRandom(){
        dealerCounter = getRandomIntegerBetween();
        String text = getString(R.string.dijeli)+dealeri[dealerCounter];
        djelitelj.setText(text);
    }
    public boolean gameIsOver(){
        return Integer.parseInt(sumaMi.getText().toString()) > 1000 || Integer.parseInt(sumaVi.getText().toString()) > 1000;
    }
    public void updateSums(){
        int sum1 = 0;
        int sum2 = 0;
        for(int i = 0; i < nasaIgra.size(); i++){
            sum1 = sum1 + nasaIgra.get(i);
            sum2 = sum2 + vasaIgra.get(i);
        }
        sumaMi.setText(String.valueOf(sum1));
        sumaVi.setText(String.valueOf(sum2));
    }
    public void updateTable() {
        setupRecyclerData();
    }

    @Override
    public void onNameClick(int position) {
//        Toast.makeText(this, "Position: " + position, Toast.LENGTH_SHORT).show();
        THIRD_ACTIVITY_REQUEST_CODE = position+20;
        Intent intent = new Intent(MainActivity.this, InputActivity.class);
        startActivityForResult(intent, THIRD_ACTIVITY_REQUEST_CODE);
    }

//    public void addCell(int x, int y){
//        recyclerAdapter.addNewCell(x, y);
//    }

    private void setupRecyclerData(){
        recyclerAdapter.addData(nasaIgra, vasaIgra);
    }
}
