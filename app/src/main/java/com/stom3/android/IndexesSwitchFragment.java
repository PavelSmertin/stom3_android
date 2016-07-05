package com.stom3.android;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.stom3.android.api.response.WoodTypeIndexes;


public class IndexesSwitchFragment extends Fragment {
    public static final String ARG_WOODTYPE_INDEXES = "market_indexes";

    private WoodTypeIndexes woodTypeIndexes;



    public IndexesSwitchFragment() {
        // Required empty public constructor
    }


    public static IndexesSwitchFragment newInstance(WoodTypeIndexes indexes) {
        IndexesSwitchFragment f = new IndexesSwitchFragment();
        Bundle args = new Bundle();
        args.putParcelable(ARG_WOODTYPE_INDEXES, indexes);
        f.setArguments(args);
        return f;
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            woodTypeIndexes = getArguments().getParcelable(ARG_WOODTYPE_INDEXES);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_indexes_switch, container, false);

        FragmentManager fragmentManager = getChildFragmentManager();

        /*tabLayout = (TabLayout) findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(viewPager);*/

        /*for(Map.Entry<String, HashMap<String, HashMap<String, HashMap<String, IndexValue>>>> quality : woodTypeIndexes.getIndexes().entrySet()) {

            QualityIndexesFragment qualityIndexesFragment = QualityIndexesFragment.newInstance(new QualityIndexes(quality.getKey(), quality.getValue()));
            fragmentManager
                    .beginTransaction()
                    .add(R.id.indexes_container, qualityIndexesFragment)
                    .commit();
        }
*/
        /*ViewPagerAdapter adapter = new ViewPagerAdapter(getChildFragmentManager());

        for(Map.Entry<String, HashMap<String, HashMap<String, HashMap<String, IndexValue>>>> quality : woodTypeIndexes.getIndexes().entrySet()) {
            adapter.addFragment(QualityIndexesFragment.newInstance(new QualityIndexes(quality.getKey(), quality.getValue())), quality.getKey());
        }

        ViewPager viewPager = (ViewPager) view.findViewById(R.id.indexes_block_pager);
        viewPager.setAdapter(adapter);*/

        return view;
    }

}
