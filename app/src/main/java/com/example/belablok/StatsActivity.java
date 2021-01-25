package com.example.belablok;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.PagerAdapter;
import androidx.viewpager.widget.ViewPager;

import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.widget.Toast;

import com.google.android.material.tabs.TabLayout;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class StatsActivity extends AppCompatActivity{

    private ViewPager mViewPager;
    private TabLayout mTabLayout;
    private List<Integer> nasaIgraHistory;
    private List<Integer> vasaIgraHistory;
    private ScreenSlidePagerAdapter screenSlidePagerAdapter;
    private Partije partije;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_stats);
        initViews();
        setUpPager();
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
    }


    private void initViews() {
        mViewPager = findViewById(R.id.viewPager);
        mTabLayout = findViewById(R.id.tab);

        nasaIgraHistory = new ArrayList<>();
        vasaIgraHistory = new ArrayList<>();

        if(!(getIntent().getIntegerArrayListExtra("nasaHistory")).isEmpty()) {
            nasaIgraHistory.addAll(getIntent().getIntegerArrayListExtra("nasaHistory"));
            vasaIgraHistory.addAll(getIntent().getIntegerArrayListExtra("vasaHistory"));
        }

        if(getIntent().getSerializableExtra("data2D") != null){
            partije = (Partije) getIntent().getSerializableExtra("data2D");
        }



    }
    private void setUpPager() {
//        ScreenSlidePagerAdapter pagerAdapter = new ScreenSlidePagerAdapter(getSupportFragmentManager());
        screenSlidePagerAdapter = new ScreenSlidePagerAdapter((getSupportFragmentManager()));
        screenSlidePagerAdapter.addFragment(HistoryFragment.newInstance(nasaIgraHistory,vasaIgraHistory), "POVIJEST");
        screenSlidePagerAdapter.addFragment(StatsFragment.newInstance(nasaIgraHistory,vasaIgraHistory, partije, getIntent().getIntExtra("brojZvanjaMi", 0), getIntent().getIntExtra("brojZvanjaVi", 0)), "STATISTIKA");
        mViewPager.setAdapter(screenSlidePagerAdapter);
        mTabLayout.setupWithViewPager(mViewPager);
    }
}