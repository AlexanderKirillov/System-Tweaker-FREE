package com.nowenui.systemtweakerfree.fragments;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;

import com.nowenui.systemtweakerfree.R;

public class FAQFragment extends Fragment {

    public static FAQFragment newInstance(Bundle bundle) {
        FAQFragment messagesFragment = new FAQFragment();

        if (bundle != null) {
            messagesFragment.setArguments(bundle);
        }

        return messagesFragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup parent, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_sovets_powersaving, parent, false);

        WebView webView = (WebView) view.findViewById(R.id.webView_power);
        webView.loadUrl("file:///android_asset/fixes.html");

        return view;
    }


}