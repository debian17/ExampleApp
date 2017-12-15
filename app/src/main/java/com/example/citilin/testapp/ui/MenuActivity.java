package com.example.citilin.testapp.ui;

import android.app.NotificationManager;
import android.content.Context;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.example.citilin.testapp.App;
import com.example.citilin.testapp.R;
import com.example.citilin.testapp.ui.about.AboutFragment;
import com.example.citilin.testapp.ui.characters.CharactersFragment;
import com.example.citilin.testapp.ui.characters.ImageCacheManager;
import com.example.citilin.testapp.ui.contacts.ContactsFragment;
import com.example.citilin.testapp.ui.dialog.TestDialogFragment;
import com.example.citilin.testapp.ui.mychracters.MyCharactersFragment;
import com.example.citilin.testapp.ui.rxjava.RxJava2Fragment;
import com.example.citilin.testapp.ui.test.TestFragment;
import com.example.citilin.testapp.ui.time.TimeFragment;
import com.example.citilin.testapp.ui.webview.WebViewFragment;

public class MenuActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    private static final int DRAWER_GRAVITY = GravityCompat.START;
    private DrawerLayout drawerLayout;
    private volatile boolean wantToExit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);
        //Log.d("MenuActivity", "onCreate");
        wantToExit = false;

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        drawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawerLayout, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawerLayout.setDrawerListener(toggle);
        toggle.syncState();
        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        NotificationManager mNotificationManager =
                (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);

        mNotificationManager.cancelAll();

//        Bundle remoteInput = RemoteInput.getResultsFromIntent(getIntent());
//        if (remoteInput != null) {
//            Toast.makeText(MenuActivity.this, remoteInput.getCharSequence("keyReply"), Toast.LENGTH_SHORT)
//                    .show();
//        }

//        Intent intent = getIntent();
//        if (intent != null) {
//            Toast.makeText(MenuActivity.this, intent.getStringExtra("key"), Toast.LENGTH_SHORT)
//                    .show();
//        }

        ImageCacheManager.createCacheDir(App.getAppContext());
        if (savedInstanceState == null) {
            changeFragment(new WebViewFragment());
        }
    }

    @Override
    protected void onStart() {
        super.onStart();
        //Log.d("MenuActivity", "onStart");
    }

    @Override
    protected void onResume() {
        super.onResume();
        //Log.d("MenuActivity", "onResume");
    }

    @Override
    protected void onPause() {
        super.onPause();
        //Log.d("MenuActivity", "onPause");
    }

    @Override
    protected void onStop() {
        super.onStop();
        //Log.d("MenuActivity", "onStop");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        //Log.d("MenuActivity", "onDestroy");
    }

    @Override
    public void onBackPressed() {
        if (!wantToExit) {
            Toast.makeText(MenuActivity.this, R.string.press_again_to_exit, Toast.LENGTH_SHORT).show();
            wantToExit = true;
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        int id = item.getItemId();
        wantToExit = false;

        switch (id) {
            case R.id.listCharacters:
                changeFragment(new CharactersFragment());
                break;
            case R.id.aboutApp:
                changeFragment(new AboutFragment());
                break;
            case R.id.listMyCharacters: {
                changeFragment(new MyCharactersFragment());
                break;
            }
            case R.id.timeFragment: {
                changeFragment(new TimeFragment());
                break;
            }
            case R.id.test: {
                changeFragment(new TestFragment());
                break;
            }
            case R.id.web: {
                changeFragment(new WebViewFragment());
                break;
            }
            case R.id.rxjava: {
                changeFragment(new RxJava2Fragment());
                break;
            }
            case R.id.contacts: {
                changeFragment(new ContactsFragment());
                break;
            }
            case R.id.dialog: {
                changeFragment(new TestDialogFragment());
                break;
            }
        }
        drawerLayout.closeDrawer(DRAWER_GRAVITY);
        return true;
    }

    private void changeFragment(Fragment newFragment) {
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.vgFragmentContainer, newFragment)
                .commit();
    }

}
