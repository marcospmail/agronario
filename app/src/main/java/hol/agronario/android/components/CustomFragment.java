package hol.agronario.android.components;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import hol.agronario.android.ActivityMain;
import hol.agronario.android.R;

/**
 * Created by Marcos on 24/02/2015.
 */
public class CustomFragment extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        setTransactions();

        return super.onCreateView(inflater, container, savedInstanceState);
    }

    protected void changeFragment(Fragment fragment, Bundle bundle) {
        if (getActivity() instanceof ActivityMain) {
            ((ActivityMain) getActivity()).changeFragment(fragment, bundle);
        }
    }

    protected void showLoadErrorDialog() {
        showDialog(R.string.load_error);
    }

    protected void showDialog(int msg) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setMessage(msg)
                .setNeutralButton(R.string.close, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });

        builder.create().show();
    }

    protected void setTransactions() {

    }

}
