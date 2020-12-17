package com.example.belablok;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.text.Editable;
import android.text.InputFilter;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;



public class InputActivity extends AppCompatActivity {


    static int nase;
    static int vase;
    EditText mi;
    EditText vi;
    boolean _ignore = false;
    int naseZvanje;
    int vaseZvanje;
    TextWatcher mitw;
    TextWatcher vitw;
    TextView prikazZvanjaMi;
    TextView prikazZvanjaVi;
    RadioButton miSmoZvali;
    RadioButton viSteZvali;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        setContentView(R.layout.activity_input);
        updateUI();

    }

    private void updateUI() {
        miSmoZvali = (RadioButton) findViewById(R.id.radioButton2);
        viSteZvali = (RadioButton) findViewById(R.id.radioButton);
        final Button mi20 = (Button) findViewById(R.id.mi20);
        final Button mi50 = (Button) findViewById(R.id.mi50);
        final Button mi100 = (Button) findViewById(R.id.mi100);
        final Button miBelot = (Button) findViewById(R.id.miBelot);
        final Button vi20 = (Button) findViewById(R.id.vi20);
        final Button vi50 = (Button) findViewById(R.id.vi50);
        final Button vi100 = (Button) findViewById(R.id.vi100);
        final Button viBelot = (Button) findViewById(R.id.viBelot);
        Button upis = (Button) findViewById(R.id.upis);
        Button brisiZvanja = (Button) findViewById(R.id.brisiZvanja);
        mi = (EditText) findViewById(R.id.editTextNumber);
        vi = (EditText) findViewById(R.id.editTextNumber2);
        prikazZvanjaMi = (TextView) findViewById(R.id.iznoszvanjaMi);
        prikazZvanjaVi = (TextView) findViewById(R.id.iznoszvanjaVi);
        naseZvanje = 0;
        vaseZvanje = 0;
        String temp = getString(R.string.zvanje)+ naseZvanje;
        prikazZvanjaMi.setText(temp);
        prikazZvanjaVi.setText(temp);

        mitw = new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }
            @SuppressLint("SetTextI18n")
            @Override
            public void afterTextChanged(Editable s) {
                if(s.toString().length() > 0){
                    if (_ignore)
                        return;
                    _ignore = true;
                    if((162 - Integer.parseInt(s.toString())) >=0) {
                        vi.setText(Integer.toString(162 - Integer.parseInt(s.toString())));
                    }else{
                        vi.setText("0");
                    }
                    _ignore = false;
                }
            }
        };

        vitw = new TextWatcher() {

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }
            @SuppressLint("SetTextI18n")
            @Override
            public void afterTextChanged(Editable s) {
                if(s.toString().length() > 0){
                    if (_ignore)
                        return;
                    _ignore = true;
                    if((162 - Integer.parseInt(s.toString())) >=0) {
                        mi.setText(Integer.toString(162 - Integer.parseInt(s.toString())));
                    }else{
                        mi.setText("0");
                    }
                    _ignore = false;
                }
            }
        };

        mi.addTextChangedListener(mitw);
        vi.addTextChangedListener(vitw);
        mi.setFilters(new InputFilter[]{new InputFilterMinMax("0","162")});
        vi.setFilters(new InputFilter[]{new InputFilterMinMax("0","162")});


        View.OnClickListener ocl = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(v.getId() == mi20.getId()){
                    if(naseZvanje + 20 < 1001) {
                        naseZvanje += 20;
                        promijeniPrikazMiZvanja();
                    }
                }
                else if(v.getId() == mi50.getId()){
                    if(naseZvanje + 50 < 1001) {
                        naseZvanje += 50;
                        promijeniPrikazMiZvanja();
                    }
                }
                else if(v.getId() == mi100.getId()){
                    if(naseZvanje + 100 < 1001) {
                        naseZvanje += 100;
                        promijeniPrikazMiZvanja();
                    }
                }
                else if(v.getId() == miBelot.getId()){
                    naseZvanje = 1001;
                    promijeniPrikazMiZvanja();
                }
                else if(v.getId() == vi20.getId()){
                    if(vaseZvanje + 20 < 1001) {
                        vaseZvanje += 20;
                        promijeniPrikazViZvanja();
                    }
                }
                else if(v.getId() == vi50.getId()){
                    if(vaseZvanje + 50 < 1001) {
                        vaseZvanje += 50;
                        promijeniPrikazViZvanja();
                    }
                }
                else if(v.getId() == vi100.getId()){
                    if(vaseZvanje + 100 < 1001) {
                        vaseZvanje += 100;
                        promijeniPrikazViZvanja();
                    }
                }
                else if(v.getId() == viBelot.getId()){
                    vaseZvanje = 1001;
                    promijeniPrikazViZvanja();
                }
            }
        };

        mi20.setOnClickListener(ocl);
        mi50.setOnClickListener(ocl);
        mi100.setOnClickListener(ocl);
        miBelot.setOnClickListener(ocl);
        vi20.setOnClickListener(ocl);
        vi50.setOnClickListener(ocl);
        vi100.setOnClickListener(ocl);
        viBelot.setOnClickListener(ocl);

        brisiZvanja.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                naseZvanje = 0;
                vaseZvanje = 0;
                promijeniPrikazMiZvanja();
                promijeniPrikazViZvanja();
            }
        });

        upis.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int nasiBodovi;
                int vasiBodovi;
                try {
                    nasiBodovi = Integer.parseInt(mi.getText().toString()) + naseZvanje;
                    vasiBodovi = Integer.parseInt(vi.getText().toString()) + vaseZvanje;
                    if(miSmoZvali.isChecked()){
                        if(nasiBodovi < vasiBodovi){
                            Toast.makeText(InputActivity.this , "Pali smo!", Toast.LENGTH_SHORT).show();
                            vasiBodovi += nasiBodovi;
                            nasiBodovi = 0;
                        }
                    }
                    else if(viSteZvali.isChecked()){
                        if(vasiBodovi < nasiBodovi){
                            Toast.makeText(InputActivity.this , "Pali ste!", Toast.LENGTH_SHORT).show();
                            nasiBodovi += vasiBodovi;
                            vasiBodovi = 0;
                        }
                    }
                    nase = nasiBodovi;
                    vase = vasiBodovi;
                    String stringToPassBack = nasiBodovi + " " + vasiBodovi;
                    Intent intent = new Intent();
                    intent.putExtra("keyName", stringToPassBack);
                    setResult(RESULT_OK, intent);
                    finish();
                }catch(NumberFormatException e){
                    Toast.makeText(InputActivity.this , "Unesite odgovarajuće podatke!", Toast.LENGTH_SHORT).show();
                }

            }
        });
    }

    @Override
    protected void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);

        try {
            outState.putInt("NASE_ZVANJE", naseZvanje);
            outState.putInt("VASE_ZVANJE", vaseZvanje);
        }
        catch (NumberFormatException ignored){

        }
    }

    @Override
    protected void onRestoreInstanceState(@NonNull Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        try{
            naseZvanje = savedInstanceState.getInt("NASE_ZVANJE");
            vaseZvanje = savedInstanceState.getInt("VASE_ZVANJE");
            String temp = getString(R.string.zvanje + naseZvanje);
            prikazZvanjaMi.setText(temp);
            temp = getString(R.string.zvanje + vaseZvanje);
            prikazZvanjaVi.setText(temp);
        }
        catch (NumberFormatException ignored){

        }
    }

    void promijeniPrikazMiZvanja(){
        String temp = getString(R.string.zvanje)+ naseZvanje;
        prikazZvanjaMi.setText(temp);
    }
    void promijeniPrikazViZvanja(){
        String temp = getString(R.string.zvanje)+ vaseZvanje;
        prikazZvanjaVi.setText(temp);
    }


}
