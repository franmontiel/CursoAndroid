package com.franmontiel.commons.dialogs;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.DialogInterface.OnCancelListener;
import android.util.Log;

import com.franmontiel.commons.R;

public class ProgressDialogUtil {
    private final static String TAG = ProgressDialog.class.getSimpleName();

    /* Singleton */
    private ProgressDialogUtil() {
    }

    public static ProgressDialogUtil getInstance() {
        return SingletonHolder.instance;
    }

    private static class SingletonHolder {
        public static final ProgressDialogUtil instance = new ProgressDialogUtil();
    }

	/* END Singleton */

    private ProgressDialog progressDialog;

    public void dismissProgressDialog() {
        if (progressDialog != null && progressDialog.isShowing()) {
            try {
                progressDialog.dismiss();
            } catch (Exception e) {
                Log.w(TAG, e);
            }
        }
    }

    public boolean isShowingProgressDialog() {
        return progressDialog != null && progressDialog.isShowing();
    }

    public void showProgressDialog(Activity activityContext) {
        showProgressDialog(activityContext, "",
                activityContext.getString(R.string.please_wait));
    }

    public void showProgressDialog(Activity activityContext, int title,
                                   int message) {
        showProgressDialog(activityContext, activityContext.getString(title),
                activityContext.getString(message));
    }

    public void showProgressDialog(Activity activityContext, int message) {
        showProgressDialog(activityContext, "",
                activityContext.getString(message));
    }

    public void showProgressDialog(final Activity activityContext,
                                   String title, String message) {
        dismissProgressDialog();

        this.progressDialog = createProgressDialog(activityContext, title,
                message);

        this.progressDialog.show();
    }

    private static ProgressDialog createProgressDialog(
            final Activity activityContext, String title, String message) {
        final ProgressDialog progressDialog = new ProgressDialog(
                activityContext);
        progressDialog.setCancelable(true);
        progressDialog.setCanceledOnTouchOutside(false);
        progressDialog.setOnCancelListener(new OnCancelListener() {

            @Override
            public void onCancel(DialogInterface dialog) {
                progressDialog.dismiss();
                activityContext.finish();
            }
        });
        progressDialog.setTitle(title);
        progressDialog.setMessage(message);

        return progressDialog;
    }

    public void showDeterminateProgressDialog(Activity activityContext,
                                              int message, int max, int progress) {
        dismissProgressDialog();
        this.progressDialog = createDeterminateProgressDialog(activityContext,
                "", activityContext.getString(message), max, progress);

        this.progressDialog.show();
    }

    private static ProgressDialog createDeterminateProgressDialog(
            final Activity activityContext, String title, String message,
            int max, int progress) {
        ProgressDialog progressDialog = createProgressDialog(activityContext,
                title, message);
        progressDialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
        progressDialog.setMax(max);
        progressDialog.setProgress(progress);

        return progressDialog;
    }

    public void updateDeterminateProgressDialog(int progress) {
        if (isShowingProgressDialog()) {
            this.progressDialog.setProgress(progress);
        }
    }
}
