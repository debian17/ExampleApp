package com.example.citilin.testapp.ui;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.example.citilin.testapp.R;

public class TestSecondActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test_second);
        Log.d("TestSecondActivity", "onCreate");
    }

    @Override
    protected void onStart() {
        super.onStart();
        Log.d("TestSecondActivity", "onStart");
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.d("TestSecondActivity", "onResume");
    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.d("TestSecondActivity", "onPause");
    }

    @Override
    protected void onStop() {
        super.onStop();
        Log.d("TestSecondActivity", "onStop");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.d("TestSecondActivity", "onDestroy");
    }
}
