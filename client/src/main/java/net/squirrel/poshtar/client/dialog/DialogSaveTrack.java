package net.squirrel.poshtar.client.dialog;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import net.squirrel.postar.client.R;

/**
 * Activity causing dialog must be implement DialogueResultListener
 */
public class DialogSaveTrack extends android.app.DialogFragment {
    private DialogueResultListener listener;
    private EditText eDescription;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setStyle(DialogFragment.STYLE_NORMAL, R.style.PoshtarDialog);
    }


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
}
