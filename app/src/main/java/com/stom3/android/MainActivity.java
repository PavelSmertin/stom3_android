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
import android.view.View;

import com.stom3.android.api.response.IndexesMarket;
import com.stom3.android.auth.AuthActivity;
import com.stom3.android.behavior.ScrollAwareFABBehavior;
import com.stom3.android.storage.PreferencesHelper;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.LinkedList;
import java.util.Locale;

public class MainActivity extends AppCompatActivity  implements AppBarLayout.OnOffsetChangedListener{

    private Toolbar toolbar;
    private TabLayout tabLayout;
    private ViewPager viewPager;

    private FloatingActionButton fabView;

    private LinkedList<IndexesMarket> indexes;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        indexes = PreferencesHelper.getInstance().getIndexes();

        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        //getSupportActionBar().setDisplayHomeAsUpEnabled(true);


        Calendar calendar = Calendar.getInstance();
        if(!PreferencesHelper.getInstance().isAuth()) {
            calendar.add(Calendar.MONTH, -1);
            toolbar.setSubtitle(getString(R.string.main_subtitle));
        }

        SimpleDateFormat format1 = new SimpleDateFormat("d MMMM yyyy", Locale.getDefault());
        String date = format1.format(calendar.getTime());

        getSupportActionBar().setTitle(String.format(getString(R.string.main_title), date));



        //getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        viewPager = (ViewPager) findViewById(R.id.viewpager);
        setupViewPager(viewPager);

        tabLayout = (TabLayout) findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(viewPager);
        fabView = (FloatingActionButton) findViewById(R.id.fab);

        if (!PreferencesHelper.getInstance().isAuth()) {
            fabView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(MainActivity.this, AuthActivity.class);
                    startActivity(intent);
                }
            });

            AppBarLayout appBarLayout = (AppBarLayout) findViewById(R.id.appbar);
            appBarLayout.addOnOffsetChangedListener(this);
        }


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

        for(IndexesMarket market : indexes) {
            adapter.addFragment(MarketIndexesFragment.newInstance(market), market.getName());
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
