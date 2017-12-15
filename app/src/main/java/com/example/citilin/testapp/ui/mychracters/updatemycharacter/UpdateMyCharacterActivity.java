package com.example.citilin.testapp.ui.mychracters.updatemycharacter;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.design.widget.TextInputLayout;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.citilin.testapp.ImageManager;
import com.example.citilin.testapp.R;
import com.example.citilin.testapp.ui.mychracters.MyCharacter;

public class UpdateMyCharacterActivity extends AppCompatActivity
        implements UpdateMyCharacterPresenter.View {

    private static final String MY_CHARACTER_ID = "myCharacterId";
    private static final String MY_CHARACTER_NAME = "myCharacterName";
    private static final String MY_CHARACTER_SUPERPOWER = "myCharacterSuperPower";
    private static final String MY_CHARACTER = "myCharacter";
    private static final String MY_CHARACTER_PICTURE_PATH = "myCharacterPicturePath";
    private final int REQUEST_PICK_FROM_GALERY = 1;
    private final int REQUEST_PHOTO_FROM_CAMERA = 2;

    private EditText updateMyCharacterName;
    private EditText updateMyCharacterSuperPower;
    private MyCharacter myCharacter;
    private TextInputLayout inputLayoutName;
    private TextInputLayout inputLayoutSuperPower;
    private ImageView updateMyCharacterPicture;
    private Button pickPicture;
    private Button makePicture;
    private Uri imageUri;

    private UpdateMyCharacterPresenter updateMyCharacterPresenter;

    public static Intent getIntent(@NonNull Context context, @NonNull MyCharacter myCharacter) {
        Intent intent = new Intent(context, UpdateMyCharacterActivity.class);
        intent.putExtra(MY_CHARACTER, myCharacter);
        return intent;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_my_character);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        updateMyCharacterName = (EditText) findViewById(R.id.etUpdateMyCharacterName);
        updateMyCharacterSuperPower = (EditText) findViewById(R.id.etUpdateMyCharacterSuperPower);
        inputLayoutName = (TextInputLayout) findViewById(R.id.tilUpdateMyCharacterName);
        inputLayoutSuperPower = (TextInputLayout) findViewById(R.id.tilUpdateMyCharacterSuperPower);
        updateMyCharacterPicture = (ImageView) findViewById(R.id.imgUpdateMyCharacterPreView);
        pickPicture = (Button) findViewById(R.id.pickUpdatePicture);
        makePicture = (Button) findViewById(R.id.makeUpdatePicture);

        Intent intent = getIntent();
        if (intent != null) {
            myCharacter = getIntent().getParcelableExtra(MY_CHARACTER);
            imageUri = Uri.parse(myCharacter.getPicturePath());
            updateMyCharacterName.setText(myCharacter.getName());
            updateMyCharacterSuperPower.setText(myCharacter.getSuperPower());
            ImageManager.setPreview(this, updateMyCharacterPicture, myCharacter.getPicturePath());
        } else {
            finish();
        }

        pickPicture.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    if (ActivityCompat.checkSelfPermission(UpdateMyCharacterActivity.this, Manifest.permission.READ_EXTERNAL_STORAGE)
                            == PackageManager.PERMISSION_GRANTED) {
                        pickPhoto();
                    } else {
                        ActivityCompat.requestPermissions(UpdateMyCharacterActivity.this,
                                new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, REQUEST_PICK_FROM_GALERY);
                    }
                } catch (IllegalArgumentException e) {
                    e.printStackTrace();
                }
            }
        });

        makePicture.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    if ((ActivityCompat.checkSelfPermission(UpdateMyCharacterActivity.this, Manifest.permission.WRITE_EXTERNAL_STORAGE)
                            == PackageManager.PERMISSION_GRANTED)) {
                        takePhoto();
                    } else {

                        ActivityCompat.requestPermissions(UpdateMyCharacterActivity.this,
                                new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, REQUEST_PHOTO_FROM_CAMERA);
                    }
                } catch (IllegalArgumentException e) {
                    e.printStackTrace();
                }
            }
        });

        updateMyCharacterPresenter = new UpdateMyCharacterPresenter();
        updateMyCharacterPresenter.attachView(this);
    }

    private void pickPhoto() {
        Intent galleryIntent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(galleryIntent, REQUEST_PICK_FROM_GALERY);
    }

    private void takePhoto() {
        imageUri = ImageManager.createImageUri(this);
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        intent.putExtra(MediaStore.EXTRA_OUTPUT, imageUri);
        startActivityForResult(intent, REQUEST_PHOTO_FROM_CAMERA);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == RESULT_OK) {
            switch (requestCode) {
                case REQUEST_PICK_FROM_GALERY: {
                    imageUri = Uri.parse(ImageManager.getImagePath(this, data.getData()));
                    ImageManager.setPreview(this, updateMyCharacterPicture, imageUri.getPath());
                    break;
                }
                case REQUEST_PHOTO_FROM_CAMERA: {
                    Bitmap thumbnail;
                    try {
                        thumbnail = MediaStore.Images.Media.getBitmap(getContentResolver(), imageUri);
                        updateMyCharacterPicture.setImageBitmap(thumbnail);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode) {
            case REQUEST_PICK_FROM_GALERY: {
                if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    pickPhoto();
                } else {
                    updateMyCharacterPresenter.galleryPermissionDenied();
                }
                break;
            }
            case REQUEST_PHOTO_FROM_CAMERA: {
                if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    takePhoto();
                } else {
                    updateMyCharacterPresenter.cameraPermissionDenied();
                }
                break;
            }
        }
    }


    @Override
    public void onSuccess() {
        Toast.makeText(UpdateMyCharacterActivity.this, R.string.my_characters_updated, Toast.LENGTH_SHORT)
                .show();
        onBackPressed();
    }

    @Override
    public void onFailure() {
        Toast.makeText(UpdateMyCharacterActivity.this, R.string.update_my_character_fail, Toast.LENGTH_SHORT)
                .show();
    }

    @Override
    public void onValidateNameFail() {
        inputLayoutName.setError(getString(R.string.required_name));
    }

    @Override
    public void onValidateSuperPowerFail() {
        inputLayoutSuperPower.setError(getString(R.string.required_superpower));
    }

    @Override
    public void hideErrors() {
        inputLayoutName.setError(null);
        inputLayoutSuperPower.setError(null);
    }

    @Override
    public void galleryPermissionDenied() {
        Toast.makeText(UpdateMyCharacterActivity.this, R.string.galery_permission_denied, Toast.LENGTH_SHORT)
                .show();
    }

    @Override
    public void cameraPermissionDenied() {
        Toast.makeText(UpdateMyCharacterActivity.this, R.string.camera_permission_denied, Toast.LENGTH_SHORT)
                .show();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        updateMyCharacterPresenter.detachView();
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_activity_update_my_character, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_update_my_character: {
                myCharacter.setName(updateMyCharacterName.getText().toString());
                myCharacter.setSuperPower(updateMyCharacterSuperPower.getText().toString());
                myCharacter.setPicturePath(imageUri.getPath());
                updateMyCharacterPresenter.updateMyCharacter(myCharacter);
                return true;
            }
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
