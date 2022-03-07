package org.helmo.memories.view.dialog;

import android.app.Activity;
import android.app.AlertDialog;
import android.view.LayoutInflater;

import org.helmo.memories.R;

public class LoadingDialog {

    Activity context;
    AlertDialog dialog;

    public LoadingDialog(Activity context) {
        this.context = context;
    }

    public void startLoadingDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);

        LayoutInflater inflater = context.getLayoutInflater();
        builder.setView(inflater.inflate(R.layout.loading_dialog, null));
        builder.setCancelable(false);

        dialog = builder.create();
        dialog.show();
    }

    public void dismissDialog() {
        dialog.dismiss();
    }
}
