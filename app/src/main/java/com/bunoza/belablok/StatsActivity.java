package com.bunoza.belablok;

import static android.preference.PreferenceManager.getDefaultSharedPreferences;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import android.content.SharedPreferences;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import com.google.android.material.tabs.TabLayout;


public class StatsActivity extends AppCompatActivity{

    private ViewPager mViewPager;
    private TabLayout mTabLayout;
    private ScreenSlidePagerAdapter screenSlidePagerAdapter;

    SharedPreferences prefs;
    SharedPreferences.Editor editor;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_stats);
        initViews();
        initPrefs();
        setUpPager();
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
    }

    private void initPrefs() {
        prefs = getDefaultSharedPreferences(getApplicationContext());
        editor = prefs.edit();
    }


    private void initViews() {
        mViewPager = findViewById(R.id.viewPager);
        mTabLayout = findViewById(R.id.tab);
    }
    private void setUpPager() {
        screenSlidePagerAdapter = new ScreenSlidePagerAdapter((getSupportFragmentManager()));
        screenSlidePagerAdapter.addFragment(HistoryFragment.newInstance(getIntent().getIntegerArrayListExtra("nasaHistory"),
                getIntent().getIntegerArrayListExtra("vasaHistory")), "POVIJEST");
        screenSlidePagerAdapter.addFragment(StatsFragment.newInstance(getIntent().getIntegerArrayListExtra("nasaHistory"),
                getIntent().getIntegerArrayListExtra("vasaHistory"), (Partije) getIntent().getSerializableExtra("data2D"),
                getIntent().getIntExtra("brojZvanjaMi", 0), getIntent().getIntExtra("brojZvanjaVi", 0)), "STATISTIKA");
        mViewPager.setAdapter(screenSlidePagerAdapter);
        mTabLayout.setupWithViewPager(mViewPager);
    }
}