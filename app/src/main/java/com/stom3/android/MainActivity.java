package com.stom3.android;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

import com.stom3.android.api.response.IndexValue;
import com.stom3.android.api.response.MarketIndexes;
import com.stom3.android.auth.AuthActivity;
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

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        if (!PreferencesHelper.getInstance().isAuth()) {
            getMenuInflater().inflate(R.menu.main, menu);
        }
        return true;
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.action_auth) {
            Intent intent = new Intent(MainActivity.this, AuthActivity.class);
            startActivity(intent);
            return true;
        }
        return super.onOptionsItemSelected(item);
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
