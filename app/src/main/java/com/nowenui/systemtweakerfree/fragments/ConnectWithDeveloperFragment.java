package com.nowenui.systemtweakerfree.fragments;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.view.ContextThemeWrapper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import com.github.mrengineer13.snackbar.SnackBar;
import com.nowenui.systemtweakerfree.R;
import com.nowenui.systemtweakerfree.Utility;

import java.util.concurrent.CountDownLatch;

public class ConnectWithDeveloperFragment extends Fragment {

    String androidversion;
    String rom, connecttext, emailtext;
    private boolean isClicked;

    public static ConnectWithDeveloperFragment newInstance(Bundle bundle) {
        ConnectWithDeveloperFragment ConnectWithDeveloper = new ConnectWithDeveloperFragment();

        if (bundle != null) {
            ConnectWithDeveloper.setArguments(bundle);
        }

        return ConnectWithDeveloper;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup parent, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_connectwithdev, parent, false);
    }

    @Override
    public void onViewCreated(final View view, Bundle savedInstanceState) {

        Button send = view.findViewById(R.id.send);
        send.setBackgroundResource(R.drawable.roundbuttoncal);
        send.setTextColor(Color.WHITE);
        send.setTextSize(20);

        final Spinner spinner12 = view.findViewById(R.id.spinner12);
        ArrayAdapter adapter =
                ArrayAdapter.createFromResource(getActivity(), R.array.rom, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);


        spinner12.setAdapter(adapter);

        //////////////////////////////////
        ////// Sending........ ///////////
        //////////////////////////////////

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
                EditText android = view.findViewById(R.id.android);
                EditText email = view.findViewById(R.id.email);
                EditText connect = view.findViewById(R.id.connect);
                if ((android.getText().toString().matches("")) || (email.getText().toString().matches("")) || (spinner12.getSelectedItem().toString().matches("(не выбрано)")) || (connect.getText().toString().matches(""))) {
                    if (Utility.getTheme(getActivity().getApplicationContext()) == 1) {
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
                    }
                    if (Utility.getTheme(getActivity().getApplicationContext()) == 2) {
                        new AlertDialog.Builder(new ContextThemeWrapper(getContext(), R.style.AlertDialogDark))
                                .setTitle(R.string.error)
                                .setMessage(R.string.pola)
                                .setPositiveButton(R.string.yasno, new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int which) {
                                        dialog.dismiss();
                                    }
                                })
                                .setIcon(R.drawable.warning)
                                .show();
                    }
                    if (Utility.getTheme(getActivity().getApplicationContext()) == 3) {
                        new AlertDialog.Builder(new ContextThemeWrapper(getContext(), R.style.AlertDialogBlack))
                                .setTitle(R.string.error)
                                .setMessage(R.string.pola)
                                .setPositiveButton(R.string.yasno, new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int which) {
                                        dialog.dismiss();
                                    }
                                })
                                .setIcon(R.drawable.warning)
                                .show();
                    }

                } else {
                    new third(getActivity(), view).executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);
                }

            }

        });
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
            EditText android = rootView.findViewById(R.id.android);
            androidversion = "Версия Android: " + android.getText().toString() + "\n";

            EditText email = rootView.findViewById(R.id.email);
            emailtext = "E-mail для обратной связи: " + email.getText().toString() + "\n";

            Spinner spinner12 = rootView.findViewById(R.id.spinner12);
            rom = "Прошивка: " + spinner12.getSelectedItem().toString() + "\n";

            EditText connect = rootView.findViewById(R.id.connect);
            connecttext = "Описание проблемы: " + connect.getText().toString() + "\n";
        }


        @Override
        public Wrapper5 doInBackground(String... args) {
            final Wrapper5 w = new Wrapper5();

            final CountDownLatch latch = new CountDownLatch(1);

            Intent intent = new Intent(Intent.ACTION_SENDTO);
            intent.setType("text/plain");
            intent.putExtra(Intent.EXTRA_SUBJECT, "System Tweaker FREE (HELP)");
            intent.putExtra(Intent.EXTRA_TEXT, androidversion + emailtext + rom + connecttext);
            intent.setData(Uri.parse("mailto:nowenui@bk.ru"));
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intent);


            latch.countDown();
            return w;
        }

        @Override
        public void onPostExecute(Wrapper5 w) {
            new SnackBar.Builder(getActivity()).withMessage(getContext().getResources().getString(R.string.ok)).withBackgroundColorId(R.color.textview1good).show();

        }
    }

}