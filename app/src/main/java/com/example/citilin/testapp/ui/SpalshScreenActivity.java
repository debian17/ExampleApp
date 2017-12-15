package com.example.citilin.testapp.ui;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;

import com.example.citilin.testapp.R;

public class SpalshScreenActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_spalsh_screen);

        new Handler().postDelayed(() -> {
            startActivity(new Intent(SpalshScreenActivity.this, MenuActivity.class)
                    .setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP));
            finish();
        }, 3000);
    }
}
