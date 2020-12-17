package com.example.belablok;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;
import android.os.Handler;

import com.google.android.material.floatingactionbutton.FloatingActionButton;


import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class MainActivity extends AppCompatActivity {

    private static final int SECOND_ACTIVITY_REQUEST_CODE = 0;
    private static final int THIRD_ACTIVITY_REQUEST_CODE = 1;

    FloatingActionButton fab;
    boolean doubleBackToExitPressedOnce = false;
    static List<Integer> nasaIgra;
    static List<Integer> vasaIgra;
    static String[] dealeri = {"Ja", "Desni protivnik", "Partner", "Lijevi protivnik"};
    static int dealerCounter;
    AlertDialog.Builder builderGotovaIgra, builderNovaIgra;
    LinearLayout linearLayoutMi;
    LinearLayout linearLayoutVi;
    TextView djelitelj;
    TextView sumaMi, sumaVi;
    int editID1, editID2;

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
        updateUI();
    }

    private void updateUI() {
        builderGotovaIgra = new AlertDialog.Builder(MainActivity.this);
        builderGotovaIgra.setIcon(Drawable.createFromPath("drawable/ic_dialog.xml"));
        builderGotovaIgra.setTitle(getString(R.string.igra_je_gotova));
        builderGotovaIgra.setMessage(R.string.nastavkom_ce_se_obrisati);
        builderGotovaIgra.setPositiveButton(R.string.nastavi, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                if(Integer.parseInt(sumaMi.getText().toString()) > 1000){
                    if (dealerCounter == 0 || dealerCounter == 2){
                        updateDealerCounter();
                    }
                }else {
                    if (dealerCounter == 1 || dealerCounter == 3) {
                        updateDealerCounter();
                    }
                }
                nasaIgra.clear();
                vasaIgra.clear();
                linearLayoutMi.removeAllViews();
                linearLayoutVi.removeAllViews();
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
                if(Integer.parseInt(sumaMi.getText().toString()) > 1000){
                    if (dealerCounter == 0 || dealerCounter == 2){
                        updateDealerCounter();
                    }
                }else {
                    if (dealerCounter == 1 || dealerCounter == 3) {
                        updateDealerCounter();
                    }
                }
                nasaIgra.clear();
                vasaIgra.clear();
                linearLayoutMi.removeAllViews();
                linearLayoutVi.removeAllViews();
                updateSums();
            }
        });
        builderNovaIgra.setNegativeButton("Odustani", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });

        sumaVi = (TextView) findViewById(R.id.sumaVi);
        sumaMi = (TextView) findViewById(R.id.sumaMi);
        sumaVi.setText("0");
        sumaMi.setText("0");
        djelitelj = (TextView) findViewById(R.id.dealer);
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
        fab = (FloatingActionButton) findViewById(R.id.fab);
        linearLayoutMi = (LinearLayout) findViewById((R.id.linear1));
        linearLayoutVi = (LinearLayout) findViewById((R.id.linear2));

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
                String[] temp = returnString.split( " ");
                nase = Integer.parseInt(temp[0]);
                vase = Integer.parseInt(temp[1]);
                nasaIgra.add(nase);
                vasaIgra.add(vase);
                updateSums();
                if(gameIsOver()){
                    Toast.makeText(MainActivity.this , getString(R.string.igra_je_gotova), Toast.LENGTH_SHORT).show();
                }
               updateTable();
            }
        }
        if (requestCode == THIRD_ACTIVITY_REQUEST_CODE) {
            if (resultCode == RESULT_OK) { // Activity.RESULT_OK
                String returnString = data.getStringExtra("keyName");
                int nase, vase;
                String[] temp = returnString.split( " ");
                nase = Integer.parseInt(temp[0]);
                vase = Integer.parseInt(temp[1]);
//                nasaIgra.add(nase);
//                vasaIgra.add(vase);
                for(int i = 0; i < nasaIgra.size(); i++){
                    if(nasaIgra.get(i) == editID1 && vasaIgra.get(i) == editID2){
                        nasaIgra.set(i, nase);
                        vasaIgra.set(i, vase);
                        Log.d("tag", "stigo2");
                        break;
                    }
                }
                updateSums();
                if(gameIsOver()){
                    Toast.makeText(MainActivity.this , getString(R.string.igra_je_gotova), Toast.LENGTH_SHORT).show();
                }
                updateTable();
            }
        }
    }
    public static int getRandomIntegerBetween(){
        Random rand = new Random();
        return rand.nextInt(4);
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
        linearLayoutMi.removeAllViews();
        linearLayoutVi.removeAllViews();
        int value1;
        int value2;
        for (int i = 0; i < nasaIgra.size(); i++) {
            TableRow row1 = new TableRow(MainActivity.this);
            TableRow row2 = new TableRow(MainActivity.this);
            value1 = nasaIgra.get(i);
            value2 = vasaIgra.get(i);
            final TextView tempView1 = new TextView(MainActivity.this);
            final TextView tempView2 = new TextView(MainActivity.this);
            tempView1.setText(String.valueOf(value1));
            tempView2.setText(String.valueOf(value2));
            tempView1.setGravity(Gravity.CENTER);
            tempView2.setGravity(Gravity.CENTER);
            tempView1.setTextSize(25);
            tempView2.setTextSize(25);
            tempView1.setTextColor(Color.BLACK);
            tempView2.setTextColor(Color.BLACK);
            tempView1.setClickable(true);
            tempView2.setClickable(true);
            View.OnClickListener tempListener = new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    editID1 = Integer.parseInt(tempView1.getText().toString());
                    editID2 = Integer.parseInt(tempView2.getText().toString());
                    Intent intent = new Intent(MainActivity.this, InputActivity.class);
                    startActivityForResult(intent, THIRD_ACTIVITY_REQUEST_CODE);
                }
            };
            tempView1.setOnClickListener(tempListener);
            tempView2.setOnClickListener(tempListener);
            row1.addView(tempView1);
            row2.addView(tempView2);
            linearLayoutMi.addView(row1);
            linearLayoutVi.addView(row2);
        }
    }
}
