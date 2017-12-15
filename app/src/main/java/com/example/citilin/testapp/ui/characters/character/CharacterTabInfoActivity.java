package com.example.citilin.testapp.ui.characters.character;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.example.citilin.testapp.R;
import com.example.citilin.testapp.ui.characters.Character;
import com.example.citilin.testapp.ui.characters.character.comics.ComicsTabFragment;
import com.example.citilin.testapp.ui.characters.character.event.EventsTabFragment;
import com.example.citilin.testapp.ui.characters.character.series.SeriesTabFragment;
import com.example.citilin.testapp.ui.characters.character.stories.StoriesTabFragment;
import com.vk.sdk.VKAccessToken;
import com.vk.sdk.VKCallback;
import com.vk.sdk.VKScope;
import com.vk.sdk.VKSdk;
import com.vk.sdk.api.VKError;

public class CharacterTabInfoActivity extends AppCompatActivity implements CharacterTabInfoPresenter.View {

    public static final String CHARACTER_ID = "characterId";
    static final String CHARACTER_NAME = "characterName";
    static final String IMAGE_URL = "imageURL";
    static final String INFO_URL = "infoURL";
    static final String MARVEL_URL = "http://marvel.com/";

    private int characterId;
    private String imageUrl;
    private String shareText;

    private Toolbar toolbar;
    private TabLayout tabLayout;
    private ViewPager viewPager;
    private ImageView heroImage;
    private CollapsingToolbarLayout collapsingToolbarLayout;
    private ProgressDialog progressDialog;

    private CharacterTabInfoPresenter characterTabInfoPresenter;

    public static Intent getIntent(@NonNull Context context, @NonNull Character character) {
        Intent intent = new Intent(context, CharacterTabInfoActivity.class);
        Log.d("ID", String.valueOf(character.id));
        intent.putExtra(CHARACTER_ID, character.id);
        intent.putExtra(CHARACTER_NAME, character.name);
        intent.putExtra(IMAGE_URL, character.getImagePath());
        try {
            if (character.urls.length > 0) {
                intent.putExtra(INFO_URL, character.urls[0].url);
            }
        } catch (NullPointerException e) {
            intent.putExtra(INFO_URL, MARVEL_URL);
        }

        return intent;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tab_hero_info);

        characterTabInfoPresenter = new CharacterTabInfoPresenter(this);

        Intent intent = getIntent();
        if (intent != null) {
            String characterName = intent.getStringExtra(CHARACTER_NAME);
            setTitle(characterName);
            characterId = intent.getIntExtra(CHARACTER_ID, 0);
            imageUrl = intent.getStringExtra(IMAGE_URL);
            String infoUrl = intent.getStringExtra(INFO_URL);
            if (infoUrl == null) {
                infoUrl = MARVEL_URL;
            }
            shareText = characterName + " " + infoUrl;
        } else {
            Toast.makeText(CharacterTabInfoActivity.this, R.string.error_loading_data, Toast.LENGTH_SHORT)
                    .show();
        }

        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        toolbar.setNavigationOnClickListener(v -> finish());

        viewPager = (ViewPager) findViewById(R.id.container);
        setupViewPager(viewPager, characterId);

        tabLayout = (TabLayout) findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(viewPager);
        heroImage = (ImageView) findViewById(R.id.vgHeroImage);

        collapsingToolbarLayout = (CollapsingToolbarLayout) findViewById(R.id.collapsing_toolbar);
        collapsingToolbarLayout.setCollapsedTitleTextAppearance(R.style.CollapsedAppBar);
        collapsingToolbarLayout.setExpandedTitleTextAppearance(R.style.ExpendedppBar);

        Glide.with(this)
                .load(imageUrl)
                .apply(RequestOptions.circleCropTransform()
                        .placeholder(R.drawable.stub))
                .into(heroImage);

        characterTabInfoPresenter.attachView(this);
    }

    private void setupViewPager(ViewPager viewPager, int characterId) {
        CharacterPagerAdapter adapter = new CharacterPagerAdapter(getSupportFragmentManager(), characterId);

        adapter.addFragment(new ComicsTabFragment(), getString(R.string.comics));
        adapter.addFragment(new StoriesTabFragment(), getString(R.string.stories));
        adapter.addFragment(new EventsTabFragment(), getString(R.string.events));
        adapter.addFragment(new SeriesTabFragment(), getString(R.string.series));

        viewPager.setAdapter(adapter);
    }

//    private void setupViewPager(ViewPager viewPager, int characterId) {
//        CustomPagerAdapter adapter = new CustomPagerAdapter(CharacterTabInfoActivity.this);
//
////        adapter.addFragment(new ComicsTabFragment(), getString(R.string.comics));
////        adapter.addFragment(new StoriesTabFragment(), getString(R.string.stories));
////        adapter.addFragment(new EventsTabFragment(), getString(R.string.events));
////        adapter.addFragment(new SeriesTabFragment(), getString(R.string.series));
//
//        viewPager.setAdapter(adapter);
//    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_activity_tab_hero_info, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_share: {
                characterTabInfoPresenter.shareCharacter(characterId, imageUrl, shareText);
                return true;
            }
            case R.id.action_share_vk: {
                if (VKSdk.isLoggedIn()) {
                    characterTabInfoPresenter.postVK(getSupportFragmentManager(), imageUrl, shareText);
                } else {
                    VKSdk.login(CharacterTabInfoActivity.this, VKScope.WALL, VKScope.PHOTOS);
                }
                return true;
            }
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        VKSdk.onActivityResult(requestCode, resultCode, data, new VKCallback<VKAccessToken>() {
            @Override
            public void onResult(VKAccessToken res) {
                characterTabInfoPresenter.postVK(getSupportFragmentManager(), imageUrl, shareText);
            }

            @Override
            public void onError(VKError error) {
                Toast.makeText(CharacterTabInfoActivity.this, R.string.vk_login_error, Toast.LENGTH_LONG)
                        .show();
            }
        });
        super.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    public void onShareFailed() {
        Toast.makeText(CharacterTabInfoActivity.this, R.string.sharing_failed, Toast.LENGTH_SHORT)
                .show();
    }

    @Override
    public void startProgress() {
        progressDialog = ProgressDialog.show(this, null, getString(R.string.sharing_process), true, false);
    }

    @Override
    public void stopProgress() {
        progressDialog.dismiss();
        progressDialog = null;
    }

    @Override
    public void shareToVKComplete() {
        Toast.makeText(CharacterTabInfoActivity.this, getString(R.string.share_vk_completed), Toast.LENGTH_LONG)
                .show();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        characterTabInfoPresenter.detachView();
    }
}
