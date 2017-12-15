package com.example.citilin.testapp.ui.dialog;


import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.example.citilin.testapp.R;

public class MyDialogFragment extends DialogFragment {

    private static String TITLE = "title";

    private View viewContainer;

    private EditText input_name_ET;
    private Button getNameBTN;

    DialogContract dialogContract;

    interface DialogContract {
        void getName(String name);
    }

    public MyDialogFragment() {
    }

    public void setDialogContract(DialogContract dialogContract) {
        this.dialogContract = dialogContract;
    }

    public static MyDialogFragment newInstance(String title) {
        MyDialogFragment myDialogFragment = new MyDialogFragment();
        Bundle args = new Bundle();
        args.putString(TITLE, title);
        myDialogFragment.setArguments(args);
        return myDialogFragment;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_my_dialog, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        input_name_ET = (EditText) viewContainer.findViewById(R.id.input_name_ET);
        getNameBTN = (Button) viewContainer.findViewById(R.id.getNameBTN);

        getNameBTN.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.e("TAG", input_name_ET.getText().toString());
            }
        });

        getDialog().setTitle(getArguments().getString(TITLE));

        input_name_ET.requestFocus();
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {

        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(getContext());
        viewContainer = View.inflate(getContext(), R.layout.fragment_my_dialog, null);
        alertDialogBuilder.setView(viewContainer);

        alertDialogBuilder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                String name = String.valueOf(input_name_ET.getText().toString());
                dialogContract.getName(name);
            }
        });

        alertDialogBuilder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                if (dialogInterface != null) {
                    dialogInterface.dismiss();
                }
            }
        });

        return alertDialogBuilder.create();

    }
}
