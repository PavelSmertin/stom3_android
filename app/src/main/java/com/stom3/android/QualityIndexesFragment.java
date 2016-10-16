package com.stom3.android;

import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.provider.Settings;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.GridView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.stom3.android.api.ResponseCallback;
import com.stom3.android.api.User;
import com.stom3.android.api.response.IndexValue;
import com.stom3.android.api.response.IndexesLength;
import com.stom3.android.api.response.IndexesQuality;
import com.stom3.android.api.response.IndexesWoods;

import java.util.LinkedList;
import java.util.List;


public class QualityIndexesFragment extends Fragment {

    public static final String ARG_QUALITY_INDEXES = "quality_indexes";

    private IndexesQuality qualityIndexes;
    private Toast mToast;

    public static QualityIndexesFragment newInstance(IndexesQuality indexes) {
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

        LinearLayout view = (LinearLayout) inflater.inflate(R.layout.fragment_quality_indexes, container, false);

        for (IndexesLength lengthIndexes : qualityIndexes.getLengths()) {
            View cardView = inflater.inflate(R.layout.length_card, container, false);
            view.addView(cardView);

            TextView lengthName = (TextView) cardView.findViewById(R.id.length_title);
            if (!lengthIndexes.getName().equalsIgnoreCase("Не задано")) {
                lengthName.setText(lengthIndexes.getName() + " м");
            } else {
                lengthName.setVisibility(View.GONE);
            }

            LinearLayout lengthContainer = (LinearLayout) cardView.findViewById(R.id.length_container);

            boolean first = true;
            for (IndexesWoods woodIndexes : lengthIndexes.getWoods()) {


                final List<Integer> sizes = new LinkedList<>();
                final List<String> categories = new LinkedList<>();

                for (IndexValue sizeIndexes : woodIndexes.getValues()) {
                    sizes.add(sizeIndexes.getValue());
                    categories.add(sizeIndexes.getCategoryId());
                }

                if (first) {
                    first = false;
                    List<String> sizesTitles = new LinkedList<>();
                    boolean isNum = true;
                    for (IndexValue sizeIndexes : woodIndexes.getValues()) {
                        try {
                            //noinspection ResultOfMethodCallIgnored
                            Float.parseFloat(sizeIndexes.getSize());
                        } catch (NumberFormatException e) {
                            isNum = false;
                        }
                        sizesTitles.add(sizeIndexes.getSize());
                    }

                    ArrayAdapter<String> adapter;
                    if (isNum) {
                        adapter = new ArrayAdapter<>(getActivity(), R.layout.item_title, R.id.index_title, sizesTitles);
                    } else {
                        adapter = new ArrayAdapter<>(getActivity(), R.layout.item_title_raw, R.id.index_title, sizesTitles);
                    }
                    GridView indexesGrid = new GridView(getActivity());
                    indexesGrid.setNumColumns(woodIndexes.getValues().size());
                    indexesGrid.setAdapter(adapter);
                    lengthContainer.addView(indexesGrid);
                }

                TextView woodName = new TextView(getActivity());
                woodName.setText(woodIndexes.getName());
                lengthContainer.addView(woodName);

                ArrayAdapter<Integer> adapter = new ArrayAdapter<>(getActivity(), R.layout.item, R.id.index_value, sizes);
                GridView indexesGrid = new GridView(getActivity());
                indexesGrid.setNumColumns(woodIndexes.getValues().size());
                indexesGrid.setAdapter(adapter);
                indexesGrid.setSelector(new ColorDrawable(Color.TRANSPARENT));
                lengthContainer.addView(indexesGrid);
                indexesGrid.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> adapterView, final View view, int i, long l) {

                        String androidId = Settings.Secure.getString(getActivity().getContentResolver(), Settings.Secure.ANDROID_ID);

                        if (androidId.length() > 0) {
                            if (mToast == null && getActivity() != null) {
                                mToast = Toast.makeText(getActivity(), "", Toast.LENGTH_SHORT);
                            }
                            User.subscribeCategory(androidId, categories.get(i), new ResponseCallback() {
                                @Override
                                public void onResponse(Object response) {
                                    mToast.setText(getString(R.string.subscribtions_successfully_added));
                                    mToast.show();
                                }

                                @Override
                                public void onError(String error) {
                                    mToast.setText(error);
                                    mToast.show();
                                }
                            });
                        }

                    }


                });
            }
        }


        return view;
    }

}