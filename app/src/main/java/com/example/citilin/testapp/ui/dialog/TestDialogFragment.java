package com.example.citilin.testapp.ui.dialog;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.example.citilin.testapp.R;

public class TestDialogFragment extends Fragment implements MyDialogFragment.DialogContract {

    private TextView name;
    private Button edit;

    public TestDialogFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_test_dialog, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        name = (TextView) view.findViewById(R.id.testName);

        edit = (Button) view.findViewById(R.id.editNameBTN);

        name.setText("Неизвестное имя");

        edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FragmentManager fragmentManager = getFragmentManager();
                MyDialogFragment myDialogFragment = MyDialogFragment.newInstance("Введите имя");
                myDialogFragment.setDialogContract(TestDialogFragment.this);
                myDialogFragment.show(fragmentManager, "TAG_FRAGMENT");
            }
        });


    }

    @Override
    public void getName(String name) {
        Log.e("TEST_FRAGMENT", name);
        this.name.setText(name);
    }
}
