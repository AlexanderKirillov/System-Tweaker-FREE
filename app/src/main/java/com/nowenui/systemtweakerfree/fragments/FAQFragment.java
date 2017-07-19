package com.nowenui.systemtweakerfree.fragments;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.nowenui.systemtweakerfree.R;

public class FAQFragment extends Fragment {

    public static FAQFragment newInstance(Bundle bundle) {
        FAQFragment FAQ = new FAQFragment();

        if (bundle != null) {
            FAQ.setArguments(bundle);
        }

        return FAQ;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup parent, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fixes, parent, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
    }


}