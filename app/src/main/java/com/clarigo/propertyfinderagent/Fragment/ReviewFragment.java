package com.clarigo.propertyfinderagent.Fragment;


import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import androidx.fragment.app.Fragment;

import com.clarigo.propertyfinderagent.Adapter.Review_Apdapter;
import com.clarigo.propertyfinderagent.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class ReviewFragment extends Fragment {

    private View rootview;
    private ListView list_reviews;
    private Review_Apdapter review_apdapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        rootview = inflater.inflate(R.layout.fragment_review, container, false);

        init();
        return rootview;
    }

    private void init() {
        list_reviews = rootview.findViewById(R.id.list_reviews);
        review_apdapter = new Review_Apdapter(getActivity());
        list_reviews.setAdapter(review_apdapter);

    }

}
