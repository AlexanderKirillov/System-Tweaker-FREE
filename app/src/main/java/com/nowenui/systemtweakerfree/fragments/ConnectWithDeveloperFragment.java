package com.nowenui.systemtweakerfree.fragments;


import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.nowenui.systemtweakerfree.R;

public class ConnectWithDeveloperFragment extends Fragment {

    private EditText connect;
    private Button send;

    public static ConnectWithDeveloperFragment newInstance(Bundle bundle) {
        ConnectWithDeveloperFragment priceListFragment = new ConnectWithDeveloperFragment();

        if (bundle != null) {
            priceListFragment.setArguments(bundle);
        }

        return priceListFragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup parent, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_connectwithdev, parent, false);

        send = (Button) view.findViewById(R.id.send);
        send.setBackgroundResource(R.drawable.roundbuttoncal);
        send.setTextColor(Color.WHITE);
        send.setTextSize(20);

        send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                connect = (EditText) getActivity().findViewById(R.id.connect);

                Intent i = new Intent(Intent.ACTION_SEND);
                i.setType("message/rfc822");
                i.putExtra(Intent.EXTRA_EMAIL, new String[]{"NowenUI@bk.ru"});
                i.putExtra(Intent.EXTRA_SUBJECT, "[System Tweaker][HELP]");
                i.putExtra(Intent.EXTRA_TEXT, connect.getText().toString());

                try {
                    startActivity(Intent.createChooser(i, getResources().getString(R.string.openemailapp)));
                } catch (android.content.ActivityNotFoundException ex) {
                    Toast.makeText(getActivity(), getResources().getString(R.string.noemail), Toast.LENGTH_SHORT).show();
                }

            }
        });


        return view;
    }

}