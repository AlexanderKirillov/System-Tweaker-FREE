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
        CautonFragment Cauton = new CautonFragment();

        if (bundle != null) {
            Cauton.setArguments(bundle);
        }

        return Cauton;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup parent, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_cauton, parent, false);
    }


    @Override
    public void onViewCreated(final View view, Bundle savedInstanceState) {
        CheckBox cau = view.findViewById(R.id.cau);
        if (cau.isChecked()) {
            cau.setEnabled(false);
        }
    }

}