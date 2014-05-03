package edu.apsu.csci4020.batson_kuwonu.weather;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.os.Bundle;

public class LocationDialogFragment extends DialogFragment{

    public interface LocationDialogListener {
        public void onLocationPositiveClick(DialogFragment dialogFragment);
    }

    // Use this instance of the interface to deliver action events
    LocationDialogListener mListener;

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        // Use the Builder class for convenient dialog construction
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle(getResources().getString(R.string.location_title))
                .setPositiveButton(getResources().getString(R.string.dialog_positive), new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        mListener.onLocationPositiveClick(LocationDialogFragment.this);
                    }
                })
                .setNegativeButton(getResources().getString(R.string.dialog_negative), null);

        // Create the alert dialog
        return builder.create();
    }

    // Override the Fragment.onAttach() method to instantiate the NoticeDialogListener
    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        // Verify that the host activity implements the callback interface
        try {
            // Instantiate the NoticeDialogListener so we can send events to the host
            mListener = (LocationDialogListener) activity;
        } catch (ClassCastException e) {
            // If caught, the activity doesn't implement the interface
            e.printStackTrace();
        }
    }
}
