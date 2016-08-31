package com.nowenui.systemtweakerfree.fragments;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TableLayout;
import android.widget.TextView;

import com.nowenui.systemtweakerfree.R;

public class AboutProgramFragment extends Fragment {

    private TextView textView30, textView31;
    private boolean isClicked;

    public static AboutProgramFragment newInstance(Bundle bundle) {
        AboutProgramFragment priceListFragment = new AboutProgramFragment();

        if (bundle != null) {
            priceListFragment.setArguments(bundle);
        }

        return priceListFragment;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup parent, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_aboutprogram, parent, false);

        textView30 = (TextView) view.findViewById(R.id.textView30);

        textView30.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isClicked) {
                    return;
                }
                isClicked = true;
                v.postDelayed(new Runnable() {

                    @Override
                    public void run() {
                        isClicked = false;
                    }
                }, 1000);
                Intent intent = new Intent();
                Uri address = Uri.parse("http://NowenUI.com");
                Intent openlink = new Intent(Intent.ACTION_VIEW, address);
                startActivity(openlink);
            }
        });
        textView31 = (TextView) view.findViewById(R.id.textView31);

        textView31.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isClicked) {
                    return;
                }
                isClicked = true;
                v.postDelayed(new Runnable() {

                    @Override
                    public void run() {
                        isClicked = false;
                    }
                }, 1000);
                Intent intent = new Intent();
                Uri address = Uri.parse("http://NowenUI.com");
                Intent openlink = new Intent(Intent.ACTION_VIEW, address);
                startActivity(openlink);
            }
        });

        TableLayout csms = (TableLayout) view.findViewById(R.id.csms);

        csms.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isClicked) {
                    return;
                }
                isClicked = true;
                v.postDelayed(new Runnable() {

                    @Override
                    public void run() {
                        isClicked = false;
                    }
                }, 1000);
                Intent intent = new Intent();
                Uri address = Uri.parse("https://play.google.com/store/apps/details?id=com.nowenui.customsgphonepro");
                Intent openlink = new Intent(Intent.ACTION_VIEW, address);
                startActivity(openlink);
            }
        });

        TableLayout chtc = (TableLayout) view.findViewById(R.id.chtc);

        chtc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isClicked) {
                    return;
                }
                isClicked = true;
                v.postDelayed(new Runnable() {

                    @Override
                    public void run() {
                        isClicked = false;
                    }
                }, 1000);
                Intent intent = new Intent();
                Uri address = Uri.parse("https://play.google.com/store/apps/details?id=com.nowenui.customonephonepro");
                Intent openlink = new Intent(Intent.ACTION_VIEW, address);
                startActivity(openlink);
            }
        });


        TableLayout mxm = (TableLayout) view.findViewById(R.id.mxm);

        mxm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isClicked) {
                    return;
                }
                isClicked = true;
                v.postDelayed(new Runnable() {

                    @Override
                    public void run() {
                        isClicked = false;
                    }
                }, 1000);
                Intent intent = new Intent();
                Uri address = Uri.parse("https://play.google.com/store/apps/details?id=com.nowenui.sw2.maximusclocks.pro");
                Intent openlink = new Intent(Intent.ACTION_VIEW, address);
                startActivity(openlink);
            }
        });

        TextView csms1 = (TextView) view.findViewById(R.id.csms1);


        csms1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isClicked) {
                    return;
                }
                isClicked = true;
                v.postDelayed(new Runnable() {

                    @Override
                    public void run() {
                        isClicked = false;
                    }
                }, 1000);
                Intent intent = new Intent();
                Uri address = Uri.parse("https://play.google.com/store/apps/details?id=com.nowenui.customsgphonepro");
                Intent openlink = new Intent(Intent.ACTION_VIEW, address);
                startActivity(openlink);
            }
        });

        TextView chtc1 = (TextView) view.findViewById(R.id.chtc1);


        chtc1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isClicked) {
                    return;
                }
                isClicked = true;
                v.postDelayed(new Runnable() {

                    @Override
                    public void run() {
                        isClicked = false;
                    }
                }, 1000);
                Intent intent = new Intent();
                Uri address = Uri.parse("https://play.google.com/store/apps/details?id=com.nowenui.customonephonepro");
                Intent openlink = new Intent(Intent.ACTION_VIEW, address);
                startActivity(openlink);
            }
        });

        TextView mxm1 = (TextView) view.findViewById(R.id.mxm1);


        mxm1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isClicked) {
                    return;
                }
                isClicked = true;
                v.postDelayed(new Runnable() {

                    @Override
                    public void run() {
                        isClicked = false;
                    }
                }, 1000);
                Intent intent = new Intent();
                Uri address = Uri.parse("https://play.google.com/store/apps/details?id=com.nowenui.sw2.maximusclocks.pro");
                Intent openlink = new Intent(Intent.ACTION_VIEW, address);
                startActivity(openlink);
            }
        });


        TextView prolink = (TextView) view.findViewById(R.id.prolink);


        prolink.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isClicked) {
                    return;
                }
                isClicked = true;
                v.postDelayed(new Runnable() {

                    @Override
                    public void run() {
                        isClicked = false;
                    }
                }, 1000);
                Intent intent = new Intent();
                Uri address = Uri.parse("https://play.google.com/store/apps/details?id=com.nowenui.systemtweaker");
                Intent openlink = new Intent(Intent.ACTION_VIEW, address);
                startActivity(openlink);
            }
        });

        return view;
    }

}