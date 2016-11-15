/*
 * Copyright © 2016, Malyshev Vladislav,  thestrangesquirrel@gmail.com. This work is licensed under the Creative Commons Attribution-NonCommercial-NoDerivs 3.0 Unported License. To view a copy of this license, visit http://creativecommons.org/licenses/by-nc-nd/3.0/ or send a letter to Creative Commons, PO Box 1866, Mountain View, CA 94042, USA.
 */

package net.squirrel.poshtar.client.dialog;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;
import android.view.View;
import android.widget.EditText;
import net.squirrel.postar.client.R;

/**
 * Activity causing dialog must be implement DialogueResultListener
 */
public class DialogSaveTrack extends DialogFragment {
    private DialogueResultListener listener;
    private EditText eDescription;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setStyle(DialogFragment.STYLE_NORMAL, R.style.PoshtarDialog);
    }


    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        View view = getActivity().getLayoutInflater().inflate(R.layout.dialog_add_description, null);
        eDescription = (EditText) view.findViewById(R.id.eDescription);
        AlertDialog.Builder adb = new AlertDialog.Builder(getActivity()).setTitle(R.string.dialog_add_description).setView(view)
                .setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dismiss();
                    }
                })
                .setPositiveButton(R.string.contin, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        listener = (DialogueResultListener) getActivity();
                        listener.onDialogResult(eDescription.getText().toString());
                        dismiss();
                    }
                });
        return adb.create();
    }

    public interface DialogueResultListener {
        void onDialogResult(String res);
    }
}
