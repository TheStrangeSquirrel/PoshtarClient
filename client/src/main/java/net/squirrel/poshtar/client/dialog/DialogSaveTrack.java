package net.squirrel.poshtar.client.dialog;

import android.app.Dialog;
import android.app.DialogFragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import net.squirrel.postar.client.R;

/**
 * Activity causing dialog must be implement DialogueResultListener
 */
public class DialogSaveTrack extends android.app.DialogFragment implements View.OnClickListener {
    private DialogueResultListener listener;
    private EditText eDescription;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setStyle(DialogFragment.STYLE_NORMAL, R.style.PoshtarDialog);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        listener = (DialogueResultListener) getActivity();

        Dialog dialog = getDialog();
        dialog.setTitle(getText(R.string.dialog_add_description));

        View v = inflater.inflate(R.layout.dialog_add_description, null);
        eDescription = (EditText) v.findViewById(R.id.eDescription);
        v.findViewById(R.id.bContinue).setOnClickListener(this);
        v.findViewById(R.id.bCancel).setOnClickListener(this);
        return v;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.bCancel:
                dismiss();
                break;
            case R.id.bContinue:
                listener.onDialogResult(eDescription.getText().toString());
                dismiss();
                break;
        }
    }
}
