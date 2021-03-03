package com.bunoza.belablok;
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
import android.widget.ImageButton;
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
    TextView mi20Count;
    TextView mi50Count;
    TextView mi100Count;
    TextView miBelotCount;
    TextView vi20Count;
    TextView vi50Count;
    TextView vi100Count;
    TextView viBelotCount;
    int mi20CountValue, mi50CountValue, mi100CountValue, miBelotCountValue, vi20CountValue, vi50CountValue, vi100CountValue, viBelotCountValue;
    ImageButton miMinus20, miMinus50, miMinus100, miMinusBelot, viMinus20, viMinus50, viMinus100, viMinusBelot;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        setContentView(R.layout.activity_input);
        updateUI();

    }

    private void updateUI() {
        mi20CountValue = 0;
        mi50CountValue = 0;
        mi100CountValue = 0;
        miBelotCountValue = 0;
        vi20CountValue = 0;
        vi50CountValue = 0;
        vi100CountValue = 0;
        viBelotCountValue = 0;

        miMinus20 = findViewById(R.id.miMinus20);
        miMinus50 = findViewById(R.id.miMinus50);
        miMinus100 = findViewById(R.id.miMinus100);
        miMinusBelot = findViewById(R.id.miMinusBelot);
        viMinus20 = findViewById(R.id.viMinus20);
        viMinus50 = findViewById(R.id.viMinus50);
        viMinus100 = findViewById(R.id.viMinus100);
        viMinusBelot = findViewById(R.id.viMinusBelot);

        miSmoZvali = findViewById(R.id.radioButton2);
        viSteZvali = findViewById(R.id.radioButton);

        mi20Count = findViewById(R.id.mi20count);
        mi50Count = findViewById(R.id.mi50count);
        mi100Count = findViewById(R.id.mi100count);
        miBelotCount = findViewById(R.id.miBelotcount);
        vi20Count = findViewById(R.id.vi20count);
        vi50Count = findViewById(R.id.vi50count);
        vi100Count = findViewById(R.id.vi100count);
        viBelotCount = findViewById(R.id.viBelotcount);
        final Button mi20 = findViewById(R.id.mi20);
        final Button mi50 = findViewById(R.id.mi50);
        final Button mi100 = findViewById(R.id.mi100);
        final Button miBelot = findViewById(R.id.miBelot);
        final Button vi20 = findViewById(R.id.vi20);
        final Button vi50 = findViewById(R.id.vi50);
        final Button vi100 = findViewById(R.id.vi100);
        final Button viBelot = findViewById(R.id.viBelot);
        Button upis = findViewById(R.id.upis);
        Button brisiZvanja = findViewById(R.id.brisiZvanja);
        mi = findViewById(R.id.editTextNumber);
        vi = findViewById(R.id.editTextNumber2);
        prikazZvanjaMi = findViewById(R.id.iznoszvanjaMi);
        prikazZvanjaVi = findViewById(R.id.iznoszvanjaVi);
        naseZvanje = 0;
        vaseZvanje = 0;
        String temp = getString(R.string.zvanje)+ naseZvanje;
        prikazZvanjaMi.setText(temp);
        prikazZvanjaVi.setText(temp);

        View.OnClickListener oclMinus = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(v.getId() == miMinus20.getId()){
                    if(mi20CountValue > 0){
                        mi20CountValue--;
                        naseZvanje -=20;
                        promijeniPrikazMiZvanja();
                    }
                    if(mi20CountValue == 0){
                        miMinus20.setVisibility(View.INVISIBLE);
                    }
                }
                else if(v.getId() == miMinus50.getId()){
                    if(mi50CountValue > 0){
                        mi50CountValue--;
                        naseZvanje -=50;
                        promijeniPrikazMiZvanja();
                    }
                    if(mi50CountValue == 0){
                        miMinus50.setVisibility(View.INVISIBLE);
                    }
                }
                else if(v.getId() == miMinus100.getId()){
                    if(mi100CountValue > 0){
                        mi100CountValue--;
                        naseZvanje -=100;
                        promijeniPrikazMiZvanja();
                    }
                    if(mi100CountValue == 0){
                        miMinus100.setVisibility(View.INVISIBLE);
                    }
                }
                else if(v.getId() == miMinusBelot.getId()){
                    if(miBelotCountValue > 0){
                        miBelotCountValue--;
                        naseZvanje -=1001;
                        promijeniPrikazMiZvanja();
                    }
                    if(miBelotCountValue == 0){
                        miMinusBelot.setVisibility(View.INVISIBLE);
                    }
                }
                else if(v.getId() == viMinus20.getId()){
                    if(vi20CountValue > 0){
                        vi20CountValue--;
                        vaseZvanje -=20;
                        promijeniPrikazViZvanja();
                    }
                    if(vi20CountValue == 0){
                        viMinus20.setVisibility(View.INVISIBLE);
                    }
                }
                else if(v.getId() == viMinus50.getId()){
                    if(vi50CountValue > 0){
                        vi50CountValue--;
                        vaseZvanje -=50;
                        promijeniPrikazViZvanja();
                    }
                    if(vi50CountValue == 0){
                        viMinus50.setVisibility(View.INVISIBLE);
                    }
                }
                else if(v.getId() == viMinus100.getId()){
                    if(vi100CountValue > 0){
                        vi100CountValue--;
                        vaseZvanje -=100;
                        promijeniPrikazViZvanja();
                    }
                    if(vi100CountValue == 0){
                        viMinus100.setVisibility(View.INVISIBLE);
                    }
                }
                else if(v.getId() == viMinusBelot.getId()){
                    if(viBelotCountValue > 0){
                        viBelotCountValue--;
                        vaseZvanje -=1001;
                        promijeniPrikazViZvanja();
                    }
                    if(viBelotCountValue == 0){
                        viMinusBelot.setVisibility(View.INVISIBLE);
                    }
                }
            }
        };

        miMinus20.setOnClickListener(oclMinus);
        miMinus50.setOnClickListener(oclMinus);
        miMinus100.setOnClickListener(oclMinus);
        miMinusBelot.setOnClickListener(oclMinus);
        viMinus20.setOnClickListener(oclMinus);
        viMinus50.setOnClickListener(oclMinus);
        viMinus100.setOnClickListener(oclMinus);
        viMinusBelot.setOnClickListener(oclMinus);

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
                    if((162 - Integer.parseInt(s.toString())) >0) {
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
                    if((162 - Integer.parseInt(s.toString())) >0) {
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
                    if(mi20CountValue < 7) {
                        miMinus20.setVisibility(View.VISIBLE);
                        naseZvanje += 20;
                        mi20CountValue++;
                        promijeniPrikazMiZvanja();
                    }
                }
                else if(v.getId() == mi50.getId()){
                    if(mi50CountValue < 5) {
                        miMinus50.setVisibility(View.VISIBLE);
                        naseZvanje += 50;
                        mi50CountValue++;
                        promijeniPrikazMiZvanja();
                    }
                }
                else if(v.getId() == mi100.getId()){
                    if(mi100CountValue < 5) {
                        miMinus100.setVisibility(View.VISIBLE);
                        naseZvanje += 100;
                        mi100CountValue++;
                        promijeniPrikazMiZvanja();
                    }
                }
                else if(v.getId() == miBelot.getId()){
                    if(miBelotCountValue < 2) {
                        miMinusBelot.setVisibility(View.VISIBLE);
                        naseZvanje += 1001;
                        miBelotCountValue++;
                        promijeniPrikazMiZvanja();
                    }
                }
                else if(v.getId() == vi20.getId()){
                    if(vi20CountValue < 7) {
                        viMinus20.setVisibility(View.VISIBLE);
                        vaseZvanje += 20;
                        vi20CountValue++;
                        promijeniPrikazViZvanja();
                    }
                }
                else if(v.getId() == vi50.getId()){
                    if(vi50CountValue < 5) {
                        viMinus50.setVisibility(View.VISIBLE);
                        vaseZvanje += 50;
                        vi50CountValue++;
                        promijeniPrikazViZvanja();
                    }
                }
                else if(v.getId() == vi100.getId()){
                    if(vi100CountValue < 5) {
                        viMinus100.setVisibility(View.VISIBLE);
                        vaseZvanje += 100;
                        vi100CountValue++;
                        promijeniPrikazViZvanja();
                    }
                }
                else if(v.getId() == viBelot.getId()){
                    if(viBelotCountValue < 2) {
                        viMinusBelot.setVisibility(View.VISIBLE);
                        vaseZvanje += 1001;
                        viBelotCountValue++;
                        promijeniPrikazViZvanja();
                    }
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
                vi20CountValue = 0;
                vi50CountValue = 0;
                vi100CountValue = 0;
                viBelotCountValue = 0;
                mi20CountValue = 0;
                mi50CountValue = 0;
                mi100CountValue = 0;
                miBelotCountValue = 0;
                miMinus20.setVisibility(View.INVISIBLE);
                miMinus50.setVisibility(View.INVISIBLE);
                miMinus100.setVisibility(View.INVISIBLE);
                miMinusBelot.setVisibility(View.INVISIBLE);
                viMinus20.setVisibility(View.INVISIBLE);
                viMinus50.setVisibility(View.INVISIBLE);
                viMinus100.setVisibility(View.INVISIBLE);
                viMinusBelot.setVisibility(View.INVISIBLE);
                promijeniPrikazMiZvanja();
                promijeniPrikazViZvanja();
            }
        });

        upis.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int nasiBodovi;
                int vasiBodovi;
                boolean brojZvanjaMi = false;
                boolean brojZvanjaVi = false;
                try {
                    nasiBodovi = Integer.parseInt(mi.getText().toString()) + naseZvanje;
                    vasiBodovi = Integer.parseInt(vi.getText().toString()) + vaseZvanje;
                    if(miSmoZvali.isChecked()){
                        if(nasiBodovi <= vasiBodovi){
                            Toast.makeText(InputActivity.this , "Pali smo!", Toast.LENGTH_SHORT).show();
                            vasiBodovi += nasiBodovi;
                            nasiBodovi = 0;
                        }
                        brojZvanjaMi = true;
                    }
                    else {
                        if(vasiBodovi <= nasiBodovi){
                            Toast.makeText(InputActivity.this , "Pali ste!", Toast.LENGTH_SHORT).show();
                            nasiBodovi += vasiBodovi;
                            vasiBodovi = 0;
                        }
                        brojZvanjaVi = true;
                    }
                    nase = nasiBodovi;
                    vase = vasiBodovi;
                    String stringToPassBack = nasiBodovi + " " + vasiBodovi;
                    Intent intent = new Intent();
                    intent.putExtra("keyName", stringToPassBack);
                    intent.putExtra("brojZvanjaMi", brojZvanjaMi);
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
        if(mi20CountValue > 0){
            mi20Count.setText("x" + mi20CountValue);
        }else{
            mi20Count.setText("");
        }
        if(mi50CountValue > 0){
            mi50Count.setText("x" + mi50CountValue);
        }else{
            mi50Count.setText("");
        }
        if(mi100CountValue > 0){
            mi100Count.setText("x" + mi100CountValue);
        }else{
            mi100Count.setText("");
        }
        if(miBelotCountValue > 0){
            miBelotCount.setText("x" + miBelotCountValue);
        }else{
            miBelotCount.setText("");
        }

        String temp = getString(R.string.zvanje)+ naseZvanje;
        prikazZvanjaMi.setText(temp);
    }
    void promijeniPrikazViZvanja(){
        if(vi20CountValue > 0){
            vi20Count.setText(vi20CountValue + "x");
        }else{
            vi20Count.setText("");
        }
        if(vi50CountValue > 0){
            vi50Count.setText(vi50CountValue  + "x");
        }else{
            vi50Count.setText("");
        }
        if(vi100CountValue > 0){
            vi100Count.setText(vi100CountValue + "x");
        }else{
            vi100Count.setText("");
        }
        if(viBelotCountValue > 0){
            viBelotCount.setText(viBelotCountValue + "x");
        }else{
            viBelotCount.setText("");
        }
        String temp = getString(R.string.zvanje)+ vaseZvanje;
        prikazZvanjaVi.setText(temp);
    }


}
