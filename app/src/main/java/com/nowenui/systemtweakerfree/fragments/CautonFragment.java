package com.nowenui.systemtweakerfree.fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;

import com.nowenui.systemtweakerfree.R;

public class CautonFragment extends Fragment {


    public static CautonFragment newInstance(Bundle bundle) {
        CautonFragment priceListFragment = new CautonFragment();

        if (bundle != null) {
            priceListFragment.setArguments(bundle);
        }

        return priceListFragment;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup parent, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_cauton, parent, false);

        CheckBox cau = (CheckBox) view.findViewById(R.id.cau);
        if (cau.isChecked()) {
            cau.setEnabled(false);
        }

        return view;
    }

}