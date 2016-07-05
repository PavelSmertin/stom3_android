package com.stom3.android;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.GridView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.stom3.android.api.response.IndexValue;
import com.stom3.android.api.response.QualityIndexes;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class QualityIndexesFragment extends Fragment{

    public static final String ARG_QUALITY_INDEXES = "quality_indexes";

    private QualityIndexes qualityIndexes;

    public static QualityIndexesFragment newInstance(QualityIndexes indexes) {
        QualityIndexesFragment f = new QualityIndexesFragment();

        // Supply index input as an argument.
        Bundle args = new Bundle();
        args.putParcelable(ARG_QUALITY_INDEXES, indexes);
        f.setArguments(args);

        return f;
    }

    public static QualityIndexesFragment newInstance() {
        QualityIndexesFragment f = new QualityIndexesFragment();
        Bundle args = new Bundle();
        f.setArguments(args);
        return f;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        qualityIndexes = getArguments().getParcelable(ARG_QUALITY_INDEXES);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        LinearLayout view =  (LinearLayout) inflater.inflate(R.layout.fragment_quality_indexes, container, false);

        for(Map.Entry<String, HashMap<String, HashMap<String, IndexValue>>> lengthIndexes : qualityIndexes.getIndexes().entrySet()) {
            View cardView = inflater.inflate(R.layout.length_card, container, false);
            view.addView(cardView);

            TextView lengthName = (TextView) cardView.findViewById(R.id.length_title);
            lengthName.setText(lengthIndexes.getKey() + " м");

            LinearLayout lengthContainer = (LinearLayout) cardView.findViewById(R.id.length_container);

            for(Map.Entry<String, HashMap<String, IndexValue>> woodIndexes : lengthIndexes.getValue().entrySet()) {


                List<Integer> sizes = new ArrayList<>();
                TextView woodName = new TextView(getActivity());
                woodName.setText(woodIndexes.getKey());
                lengthContainer.addView(woodName);

                for(Map.Entry<String, IndexValue> sizeIndexes : woodIndexes.getValue().entrySet()) {
                    sizes.add(sizeIndexes.getValue().getValue());
                }

                ArrayAdapter<Integer> adapter = new ArrayAdapter<>(getActivity(), R.layout.item, R.id.index_value, sizes);

                GridView indexesGrid = new GridView(getActivity());
                indexesGrid.setNumColumns(woodIndexes.getValue().size());
                indexesGrid.setAdapter(adapter);

                lengthContainer.addView(indexesGrid);
            }
        }


        return view;
    }

}