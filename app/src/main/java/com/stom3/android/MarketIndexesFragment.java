package com.stom3.android;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.stom3.android.api.response.IndexesMarket;
import com.stom3.android.api.response.IndexesQuality;
import com.stom3.android.api.response.IndexesWoodType;
import com.stom3.android.view.CustomPager;

public class MarketIndexesFragment extends Fragment{

    public static final String ARG_MARKET_INDEXES = "market_indexes";

    private IndexesMarket marketIndexes;
    private View view;

    public static MarketIndexesFragment newInstance(IndexesMarket indexes) {
        MarketIndexesFragment f = new MarketIndexesFragment();

        // Supply index input as an argument.
        Bundle args = new Bundle();
        args.putParcelable(ARG_MARKET_INDEXES, indexes);
        f.setArguments(args);

        return f;
    }



    public MarketIndexesFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        marketIndexes = getArguments().getParcelable(ARG_MARKET_INDEXES);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        if (view != null){
            return view;
        }

        view =  inflater.inflate(R.layout.fragment_market_indexes, container, false);
        LinearLayout indexesContainer = (LinearLayout) view.findViewById(R.id.indexes_container);

        for(IndexesWoodType woodTypeindexes : marketIndexes.getWoodTypes()) {

            View tabsLayout = inflater.inflate(R.layout.tabs, container, false);
            indexesContainer.addView(tabsLayout);

            TabLayout tabs = (TabLayout) tabsLayout.findViewById(R.id.tabs);
            TextView tabsTitle = (TextView) tabsLayout.findViewById(R.id.tabs_title);
            tabsTitle.setText(woodTypeindexes.getName());

            CustomPager qualityViewPager = new CustomPager(getActivity());
            qualityViewPager.setId(View.generateViewId());
            indexesContainer.addView(qualityViewPager);

            ViewPagerAdapter qualityPagerAdapter = new ViewPagerAdapter(getChildFragmentManager());

            for(IndexesQuality qualityIndexes : woodTypeindexes.getQualities()) {
                qualityPagerAdapter.addFragment(QualityIndexesFragment.newInstance(qualityIndexes), qualityIndexes.getName());
            }

            qualityViewPager.setAdapter(qualityPagerAdapter);

            tabs.setupWithViewPager(qualityViewPager);
        }

        return view;
    }

}