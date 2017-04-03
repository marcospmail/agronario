package hol.agronario.android.util;

import android.content.Context;
import android.view.View;

import com.afollestad.materialdialogs.DialogAction;
import com.afollestad.materialdialogs.MaterialDialog;
import com.afollestad.materialdialogs.Theme;

import hol.agronario.android.ActivityMain;
import hol.agronario.android.R;
import hol.agronario.android.SplashScreenActivity;

/**
 * Created by MarcosPaulo on 01/04/15.
 */
public class Util {

    private Context mContext;
    static public final String APP_TAG = "dicioAgro";

    public final static String ARGUMENT_ID_PALAVRA = "ID_PALAVRA";
    public final static String ARGUMENT_ID_TRADUCAO = "ID_TRADUCAO";
    public final static String ARGUMENT_ID_IDIOMA = "ID_IDIOMA";

    public static MaterialDialog customDialog;

    public Util(Context context) {
        mContext = context;
    }

    private void initApp() {
        ((SplashScreenActivity) mContext).initApp();
    }

    public void closeApp() {
        ((SplashScreenActivity) mContext).finish();
    }

    public void showDialog(final Context context, String title, final boolean finishApp) {

        customDialog = new MaterialDialog.Builder(context)
                .content(title)
                .theme(Theme.LIGHT)
                .cancelable(false)
                .positiveText(context.getString(R.string.OK))
                .show();

        if (finishApp) {
            View.OnClickListener onClickListener = new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (mContext instanceof SplashScreenActivity) {
                        closeApp();
                    } else if (context instanceof ActivityMain) {
                        ((ActivityMain) mContext).finish();
                    }
                }
            };

            View positiveButton = customDialog.getActionButton(DialogAction.POSITIVE);
            positiveButton.setOnClickListener(onClickListener);
        }
    }


    static public void showDialog(Context context, String title, String message, String positiveText, View.OnClickListener positiveButtonClickListener) {
        customDialog = new MaterialDialog.Builder(context)
                .title(title)
                .content(message)
                .theme(Theme.LIGHT)
                .cancelable(false)
                .positiveText(positiveText)
                .show();

        View positiveButton = customDialog.getActionButton(DialogAction.POSITIVE);
        positiveButton.setOnClickListener(positiveButtonClickListener);
    }

    static public void showDialog(Context context, String title, String message, String positiveText, String negativeText, View.OnClickListener positiveButtonClickListener) {
        customDialog = new MaterialDialog.Builder(context)
                .title(title)
                .content(message)
                .cancelable(false)
                .theme(Theme.LIGHT)
                .positiveText(positiveText)
                .autoDismiss(true)
                .negativeText(negativeText).show();

        View positiveButton = customDialog.getActionButton(DialogAction.POSITIVE);
        positiveButton.setOnClickListener(positiveButtonClickListener);
        View negativeButton = customDialog.getActionButton(DialogAction.NEGATIVE);
        negativeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                customDialog.dismiss();
            }
        });
    }

    static public void closeDialog() {
        if (customDialog.isShowing()) {
            customDialog.cancel();
        }
    }
}

