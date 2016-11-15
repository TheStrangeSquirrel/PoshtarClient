/*
 * Copyright © 2016, Malyshev Vladislav,  thestrangesquirrel@gmail.com. This work is licensed under the Creative Commons Attribution-NonCommercial-NoDerivs 3.0 Unported License. To view a copy of this license, visit http://creativecommons.org/licenses/by-nc-nd/3.0/ or send a letter to Creative Commons, PO Box 1866, Mountain View, CA 94042, USA.
 */

package net.squirrel.poshtar.client;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
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

public class RateManager {
    private static final int DEFAULT_NUMBER_VISITS_TO_SHOW = 22;
    private static final String KEY_RATE_PREFERENCES = "ratePreferences";
    private static final String KEY_IS_SHOW = "isShow";
    private static final String KEY_NUMBER_VISITS_TO_SHOW = "numberVisitsToShow";
    private static final String TAG_FRAGMENT_RATE = "rateFragment";
    static FragmentManager fragmentManager;
    private static SharedPreferences sharedPreferences;
    private RateFragment rateFragment;
    private int numberVisitsToShow;
    private boolean isShow;

    public RateManager() {
        sharedPreferences = AppPoshtar.getContext().getSharedPreferences(KEY_RATE_PREFERENCES, Context.MODE_PRIVATE);
        isShow = sharedPreferences.getBoolean(KEY_IS_SHOW, true);
        numberVisitsToShow = sharedPreferences.getInt(KEY_NUMBER_VISITS_TO_SHOW, DEFAULT_NUMBER_VISITS_TO_SHOW);
        rateFragment = new RateFragment();
    }

    public void show(FragmentManager fragmentManager) {
        RateManager.fragmentManager = fragmentManager;
        if (isShow) {
            if (numberVisitsToShow == 0) {
                numberVisitsToShow = DEFAULT_NUMBER_VISITS_TO_SHOW;
                FragmentTransaction transaction = fragmentManager.beginTransaction();
                transaction.add(R.id.rateContainer, rateFragment, TAG_FRAGMENT_RATE);
                transaction.commit();
            } else {
                numberVisitsToShow--;
            }
            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.putInt(KEY_NUMBER_VISITS_TO_SHOW, numberVisitsToShow);
            editor.commit();
        }

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
                            editor.putBoolean(KEY_IS_SHOW, false);
                            editor.commit();
                            break;
                        case R.id.bNo:
                            //NOP
                            break;
                    }
                    doRemove();
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

        public void doRemove() {
            FragmentTransaction transaction = fragmentManager.beginTransaction();
            Fragment fragment = fragmentManager.findFragmentByTag(TAG_FRAGMENT_RATE);
            transaction.remove(fragment).commit();
        }

        @Override
        public void onAttach(Activity activity) {
            fragmentManager = ((FragmentActivity) activity).getSupportFragmentManager();
            super.onAttach(activity);
        }

        @Override
        public void onSaveInstanceState(Bundle outState) {
            super.onSaveInstanceState(outState);
        }
    }
}