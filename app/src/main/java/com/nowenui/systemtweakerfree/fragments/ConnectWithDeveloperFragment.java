package com.nowenui.systemtweakerfree.fragments;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.nowenui.systemtweakerfree.R;

import java.util.concurrent.CountDownLatch;


public class ConnectWithDeveloperFragment extends Fragment {

    String androidversion;
    String rom, connecttext, emailtext;
    private EditText connect;
    private Button send;
    private boolean isClicked;

    public static ConnectWithDeveloperFragment newInstance(Bundle bundle) {
        ConnectWithDeveloperFragment priceListFragment = new ConnectWithDeveloperFragment();

        if (bundle != null) {
            priceListFragment.setArguments(bundle);
        }

        return priceListFragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup parent, Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.fragment_connectwithdev, parent, false);

        send = (Button) view.findViewById(R.id.send);
        send.setBackgroundResource(R.drawable.roundbuttoncal);
        send.setTextColor(Color.WHITE);
        send.setTextSize(20);

        final Spinner spinner12 = (Spinner) view.findViewById(R.id.spinner12);
        ArrayAdapter adapter =
                ArrayAdapter.createFromResource(getActivity(), R.array.rom, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);


        spinner12.setAdapter(adapter);


        send.setOnClickListener(new View.OnClickListener() {
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
                }, 3000);
                EditText android = (EditText) view.findViewById(R.id.android);
                EditText email = (EditText) view.findViewById(R.id.email);
                EditText connect = (EditText) view.findViewById(R.id.connect);
                if ((android.getText().toString().matches("")) || (email.getText().toString().matches("")) || (spinner12.getSelectedItem().toString().matches("(не выбрано)")) || (connect.getText().toString().matches(""))) {
                    new AlertDialog.Builder(getContext())
                            .setTitle(R.string.error)
                            .setMessage(R.string.pola)
                            .setPositiveButton(R.string.yasno, new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int which) {
                                    dialog.dismiss();
                                }
                            })
                            .setIcon(R.drawable.warning)
                            .show();
                } else {
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) {
                        new third(getActivity(), view).executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);
                    } else {
                        new third(getActivity(), view).execute();
                    }
                }

            }

        });


        return view;
    }

    public class Wrapper5 {
        boolean suc;
    }

    public class third extends AsyncTask<String, Void, Wrapper5> {

        private Context mContext;
        private View rootView;


        public third(Context context, View rootView) {
            this.mContext = context;
            this.rootView = rootView;

        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            EditText android = (EditText) rootView.findViewById(R.id.android);
            androidversion = "Версия Android: " + android.getText().toString() + "\n";

            EditText email = (EditText) rootView.findViewById(R.id.email);
            emailtext = "E-mail для обратной связи: " + email.getText().toString() + "\n";

            Spinner spinner12 = (Spinner) rootView.findViewById(R.id.spinner12);
            rom = "Прошивка: " + spinner12.getSelectedItem().toString() + "\n";

            connect = (EditText) rootView.findViewById(R.id.connect);
            connecttext = "Описание проблемы: " + connect.getText().toString() + "\n";
        }


        @Override
        public Wrapper5 doInBackground(String... args) {
            final Wrapper5 w = new Wrapper5();

            final CountDownLatch latch = new CountDownLatch(1);

            MailSend m = new MailSend("XXXXXX@gmail.com", "XXXXXXXXXXXXX");
            String[] toArr = {"NowenUI@bk.ru"};
            m.setTo(toArr);
            m.setFrom("User");
            m.setSubject("System Tweaker FREE (HELP)");

            m.setBody(androidversion + emailtext + rom + connecttext);

            try {
                boolean i = m.send();
                if (i == true) {
                    w.suc = true;

                } else {
                    w.suc = false;
                }

            } catch (Exception e2) {

                e2.printStackTrace();
            }

            latch.countDown();
            return w;
        }

        @Override
        public void onPostExecute(Wrapper5 w) {

            if (w.suc) {
                Toast.makeText(getActivity(), R.string.sentsucess, Toast.LENGTH_SHORT).show();
            }
            if (!w.suc) {
                Toast.makeText(getActivity(), R.string.senterror, Toast.LENGTH_SHORT).show();
            }

        }
    }

}