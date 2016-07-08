package com.moffcomm.slothstay.ui.fragment;

import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AlertDialog;

/**
 * Created by jacobsFactory on 2015-09-09.
 */
public class OkCancelDialogFragment extends DialogFragment {

    private OkCancelDialogListener okCancelDialogListener;

    public OkCancelDialogFragment() {
    }

    public static OkCancelDialogFragment newInstance(String title, String message) {
        OkCancelDialogFragment okCancelDialogFragment = new OkCancelDialogFragment();
        Bundle bundle = new Bundle();
        if (title != null)
            bundle.putString("title", title);
        bundle.putString("message", message);
        okCancelDialogFragment.setArguments(bundle);
        return okCancelDialogFragment;
    }

    public static OkCancelDialogFragment newInstance(String title, String message, String ok, String cancel) {
        OkCancelDialogFragment okCancelDialogFragment = new OkCancelDialogFragment();
        Bundle bundle = new Bundle();
        if (title != null)
            bundle.putString("title", title);
        bundle.putString("message", message);
        if (ok != null)
            bundle.putString("ok", ok);
        if (cancel != null)
            bundle.putString("cancel", cancel);
        okCancelDialogFragment.setArguments(bundle);
        return okCancelDialogFragment;
    }

    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        if (getArguments().containsKey("title"))
            builder.setTitle(getArguments().getString("title"));
        final String ok = getArguments().containsKey("ok") == true ? getArguments().getString("ok") : getString(android.R.string.ok);
        final String cancel = getArguments().containsKey("cancel") == true ? getArguments().getString("cancel") : getString(android.R.string.cancel);
        builder.setMessage(getArguments().getString("message"))
                .setPositiveButton(ok, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        if (okCancelDialogListener == null)
                            return;
                        okCancelDialogListener.onDialogOkClick();
                    }
                })
                .setNegativeButton(cancel, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        if (okCancelDialogListener == null)
                            return;
                        okCancelDialogListener.onDialogCancelClick();
                    }
                });

        // Create the AlertDialog object and return it
        return builder.create();
    }

    public void setOkCancelDialogListener(OkCancelDialogListener okCancelDialogListener) {
        this.okCancelDialogListener = okCancelDialogListener;
    }

    public interface OkCancelDialogListener {
        public void onDialogOkClick();

        public void onDialogCancelClick();
    }

}