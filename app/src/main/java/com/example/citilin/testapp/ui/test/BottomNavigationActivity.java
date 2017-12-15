package com.example.citilin.testapp.ui.test;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;

import com.example.citilin.testapp.R;

public class BottomNavigationActivity extends AppCompatActivity {

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.navigation_home:
                    changeFragment(new HomeFragment());
                    break;
                case R.id.navigation_dashboard:
                    changeFragment(new DashboardFragment());
                    break;
                case R.id.navigation_notifications:
                    changeFragment(new NotificationsFragment());
                    break;
            }
            return true;
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bottom_navigation);
        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
        if (savedInstanceState == null) {
            changeFragment(new HomeFragment());
        }
    }

    private void changeFragment(Fragment newFragment) {
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.content, newFragment)
                .commit();
    }

}
