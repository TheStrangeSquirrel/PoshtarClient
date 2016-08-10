package net.squirrel.poshtar.client.dialog;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.os.Bundle;
import net.squirrel.postar.client.R;

public class DialogReplaceSaveTrack extends DialogFragment implements DialogInterface.OnClickListener {
    private Activity activity;
    private DialogFragment childDialog;

    public void setChildDialog(DialogFragment childDialog) {
        this.childDialog = childDialog;
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder adb = new AlertDialog.Builder(getActivity()).setPositiveButton(R.string.yes, this).setNegativeButton(R.string.no, this)
                .setTitle(R.string.change_description);
        return adb.create();
    }

    @Override
    public void onClick(DialogInterface dialog, int which) {
        switch (which) {
            case Dialog.BUTTON_POSITIVE:
                if (childDialog != null) {
                    childDialog.show(getFragmentManager(), "DialogSaveTrack");
                }
                this.dismiss();
                break;
            case Dialog.BUTTON_NEGATIVE:
                this.dismiss();
                break;
        }
    }

}
