package net.squirrel.poshtar.client.dialog;

import android.app.Activity;
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
    private Activity activity;
    private DialogueResultListener listener;
    private EditText description;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        listener = (DialogueResultListener) getActivity();
        getDialog().setTitle(getText(R.string.dialog_add_description));
        View v = inflater.inflate(R.layout.dialog_add_description, null);
        description = (EditText) v.findViewById(R.id.description);
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
                listener.onDialogResult(description.getText().toString());
                dismiss();
                break;
        }
    }
}
