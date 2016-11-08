package net.squirrel.poshtar.client;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import net.squirrel.postar.client.R;

interface RemoveFragmentListener {
    void doRemove();
}

public class RateManager implements RemoveFragmentListener {
    private static final int DEFAULT_NUMBER_VISITS_TO_SHOW = 42;
    private static final String KEY_RATE_PREFERENCES = "ratePreferences";
    private static final String KEY_IS_SHOW = "isShow";
    private static final String KEY_NUMBER_VISITS_TO_SHOW = "numberVisitsToShow";
    private static SharedPreferences sharedPreferences;
    private FragmentManager fragmentManager;
    private RateFragment rateFragment;
    private int numberVisitsToShow;
    private boolean isShow;

    public RateManager() {
        sharedPreferences = AppPoshtar.getContext().getSharedPreferences(KEY_RATE_PREFERENCES, Context.MODE_PRIVATE);
        isShow = sharedPreferences.getBoolean(KEY_IS_SHOW, true);
        numberVisitsToShow = sharedPreferences.getInt(KEY_NUMBER_VISITS_TO_SHOW, DEFAULT_NUMBER_VISITS_TO_SHOW);
        rateFragment = new RateFragment();
        rateFragment.setRemoveFragmentListener(this);
    }

    public void show(FragmentManager fragmentManager) {
        this.fragmentManager = fragmentManager;
        if (isShow) {
            if (numberVisitsToShow == 0) {
                numberVisitsToShow = DEFAULT_NUMBER_VISITS_TO_SHOW;
                FragmentTransaction transaction = fragmentManager.beginTransaction();
                transaction.add(R.id.rateContainer, rateFragment);
                transaction.commit();
            } else {
                numberVisitsToShow--;
            }
            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.putInt(KEY_NUMBER_VISITS_TO_SHOW, numberVisitsToShow);
            editor.commit();
        }

    }

    @Override
    public void doRemove() {
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.remove(rateFragment);
        transaction.commit();
    }

    public static class RateFragment extends Fragment {
        private RemoveFragmentListener listener;

        @Override
        public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
            View view = inflater.inflate(R.layout.fargment_rate, null);
            setListeners(view);
            return view;
        }

        private void setListeners(View view) {
            CheckBox cbNotRemind = (CheckBox) view.findViewById(R.id.cbNotRemind);
            SharedPreferences.Editor editor = sharedPreferences.edit();
            class ClickListener implements View.OnClickListener {
                @Override
                public void onClick(View v) {
                    int id = v.getId();
                    if (cbNotRemind.isChecked() || id == R.id.bAlready) {
                        editor.putBoolean(KEY_IS_SHOW, false);
                        editor.commit();
                    }
                    switch (id) {
                        case R.id.bRate:
                            Context context = AppPoshtar.getContext();
                            Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("market://details?id=" + context.getString(R.string.app_name)))
                                    .setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                            getActivity().startActivity(intent);
//                            context.startActivity(intent);
                            editor.putBoolean(KEY_IS_SHOW, false);
                            editor.commit();
                            break;
                        case R.id.bNo:
                            //NOP
                            break;
                    }

                    if (listener != null) {
                        listener.doRemove();
                    }
                }
            }

            Button bRate = (Button) view.findViewById(R.id.bRate);
            bRate.setOnClickListener(new ClickListener());
            Button bNo = (Button) view.findViewById(R.id.bNo);
            bNo.setOnClickListener(new ClickListener());
            Button bAlready = (Button) view.findViewById(R.id.bAlready);
            bAlready.setOnClickListener(new ClickListener());

            Button bWriteAut = (Button) view.findViewById(R.id.bWriteAut);
            bWriteAut.setOnClickListener((v) -> {
                Uri emailUri = Uri.parse(getContext().getString(R.string.email));
                Intent gmailIntent = new Intent(Intent.ACTION_SEND, emailUri);
                gmailIntent.setClassName("com.google.android.gm", "com.google.android.gm.ComposeActivityGmail");
                getContext().startActivity(gmailIntent);
            });

        }

        void setRemoveFragmentListener(RemoveFragmentListener listener) {
            this.listener = listener;
        }
    }
}