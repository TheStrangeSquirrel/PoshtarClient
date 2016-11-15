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
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import net.squirrel.postar.client.R;

public class DialogReplaceSaveTrack extends DialogFragment implements DialogInterface.OnClickListener {
    private DialogFragment childDialog;

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder adb = new AlertDialog.Builder(getActivity()).setPositiveButton(R.string.yes, this).setNegativeButton(R.string.no, this)
                .setTitle(R.string.change_save_track);
        return adb.create();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        setStyle(DialogFragment.STYLE_NORMAL, R.style.PoshtarDialog);
        return super.onCreateView(inflater, container, savedInstanceState);
    }

    @Override
    public void onClick(DialogInterface dialog, int which) {
        switch (which) {
            case Dialog.BUTTON_POSITIVE:
                new DialogSaveTrack().show(getFragmentManager(), "DialogSaveTrackInReplace");
                this.dismiss();
                break;
            case Dialog.BUTTON_NEGATIVE:
                this.dismiss();
                break;
        }
    }
}
