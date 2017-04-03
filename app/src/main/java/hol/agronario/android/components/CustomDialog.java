package hol.agronario.android.components;

import android.app.Activity;
import android.content.Context;

import com.gc.materialdesign.widgets.Dialog;

/**
 * Created by MarcosPaulo on 4/10/15.
 */
public class CustomDialog extends Dialog {

    Context mContext;

    @Override
    public void onBackPressed() {
        ((Activity) mContext).onBackPressed();
    }

    public CustomDialog(final Context context, String title, String message) {
        super(context, title, message);
        mContext = context;
        this.setCanceledOnTouchOutside(false);
    }
}
