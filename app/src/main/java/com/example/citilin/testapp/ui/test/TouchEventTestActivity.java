package com.example.citilin.testapp.ui.test;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;

import com.example.citilin.testapp.R;

public class TouchEventTestActivity extends AppCompatActivity implements View.OnTouchListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_touch_event_test);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {

        int action = event.getActionMasked();

        switch (action) {

            case MotionEvent.ACTION_MOVE: {
                //Log.e("EVENT", "MOVE");
                break;
            }

            case MotionEvent.ACTION_UP: {
                Log.e("EVENT", "UP");
                break;
            }

            case MotionEvent.ACTION_DOWN: {
                Log.e("EVENT", "DOWN");
                //Log.d("DOWN", String.valueOf(event.getX()) + "   " + String.valueOf(event.getY()));
                break;
            }

            case MotionEvent.ACTION_CANCEL: {

                break;
            }

            case MotionEvent.ACTION_OUTSIDE: {

                break;
            }

        }
        return super.onTouchEvent(event);
    }

    @Override
    public boolean onTouch(View view, MotionEvent motionEvent) {
        int action = motionEvent.getActionMasked();

        switch (action) {

            case MotionEvent.ACTION_DOWN: {
                Log.e("onTouch", "DOWN");
                break;
            }

            case MotionEvent.ACTION_UP: {
                Log.e("onTouch", "UP");
                break;
            }

        }

        return true;
    }
}
