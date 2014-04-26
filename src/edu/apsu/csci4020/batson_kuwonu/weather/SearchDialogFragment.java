package edu.apsu.csci4020.batson_kuwonu.weather;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;

public class SearchDialogFragment extends DialogFragment{

    public interface SearchDialogListener {
        public void onSearchPositiveClick(DialogFragment dialogFragment, String zip_code_value);
    }

    // Use this instance of the interface to deliver action events
    SearchDialogListener mListener;

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        // Use the Builder class for convenient dialog construction
        final AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        // Get the layout inflater
        final LayoutInflater inflater = getActivity().getLayoutInflater();

        // Inflate and set the layout for the dialog
        // Pass null as the parent view because its going in the dialog layout
        final View view = inflater.inflate(R.layout.dialog_search, null);
        builder.setView(view)
                .setMessage(getResources().getString(R.string.search_title))
                .setPositiveButton(getResources().getString(R.string.search_positive), new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        EditText et = (EditText) view.findViewById(R.id.et_zip_code);
                        mListener.onSearchPositiveClick(SearchDialogFragment.this, et.getText().toString());
                    }
                })
                .setNegativeButton(getResources().getString(R.string.search_negative), null);
        // Create the AlertDialog object and return it
        return builder.create();
    }

    // Override the Fragment.onAttach() method to instantiate the NoticeDialogListener
    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        // Verify that the host activity implements the callback interface
        try {
            // Instantiate the NoticeDialogListener so we can send events to the host
            mListener = (SearchDialogListener) activity;
        } catch (ClassCastException e) {
            // If caught, the activity doesn't implement the interface
            e.printStackTrace();
        }
    }
}
