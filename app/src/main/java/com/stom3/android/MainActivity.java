package com.stom3.android;

import android.os.Bundle;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import com.stom3.android.api.response.IndexValue;
import com.stom3.android.api.response.MarketIndexes;
import com.stom3.android.behavior.ScrollAwareFABBehavior;
import com.stom3.android.storage.PreferencesHelper;

import java.util.HashMap;
import java.util.Map;

public class MainActivity extends AppCompatActivity  implements AppBarLayout.OnOffsetChangedListener{

    private Toolbar toolbar;
    private TabLayout tabLayout;
    private ViewPager viewPager;

    private FloatingActionButton fabView;

    private HashMap<String, HashMap<String, HashMap<String, HashMap<String, HashMap<String, HashMap<String, IndexValue>>>>>> indexes;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        indexes = PreferencesHelper.getInstance().getIndexes();

        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        getSupportActionBar().setTitle(null);
        //getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        viewPager = (ViewPager) findViewById(R.id.viewpager);

        setupViewPager(viewPager);

        tabLayout = (TabLayout) findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(viewPager);

        AppBarLayout appBarLayout = (AppBarLayout) findViewById(R.id.appbar);
        appBarLayout.addOnOffsetChangedListener(this);

        fabView = (FloatingActionButton) findViewById(R.id.fab);


    }

    private void setupViewPager(ViewPager viewPager) {
        ViewPagerAdapter adapter = new ViewPagerAdapter(getSupportFragmentManager());

        for(Map.Entry<String, HashMap<String, HashMap<String, HashMap<String, HashMap<String, HashMap<String, IndexValue>>>>>> market : indexes.entrySet()) {
            adapter.addFragment(MarketIndexesFragment.newInstance(new MarketIndexes(market.getKey(), market.getValue())), market.getKey());
        }

        viewPager.setAdapter(adapter);
    }

    @Override
    public void onOffsetChanged(AppBarLayout appBarLayout, int verticalOffset) {
        if(verticalOffset <= 0 && verticalOffset > -100) {
            ScrollAwareFABBehavior.animateOut(fabView);
        } else {
            ScrollAwareFABBehavior.animateIn(fabView);
        }
    }


}
