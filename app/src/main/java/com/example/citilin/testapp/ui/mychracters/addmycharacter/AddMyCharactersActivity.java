package com.example.citilin.testapp.ui.mychracters.addmycharacter;

import android.Manifest;
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
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.citilin.testapp.ImageManager;
import com.example.citilin.testapp.R;

public class AddMyCharactersActivity extends AppCompatActivity implements AddMyCharacterPresenter.View {

    private final int REQUEST_PICK_FROM_GALLERY = 1;
    private final int REQUEST_PHOTO_FROM_CAMERA = 2;

    private EditText myCharacterName;
    private EditText myCharacterSuperPower;
    private CheckBox addManyCharacters;
    private TextInputLayout inputLayoutName;
    private TextInputLayout inputLayoutSuperPower;
    private Button addPicture;
    private Button takePhoto;
    private ImageView myCharacterPreView;
    private Uri imageUri;

    private AddMyCharacterPresenter addMyCharacterPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_my_characters);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        myCharacterName = (EditText) findViewById(R.id.etInputMyCharacterName);
        myCharacterSuperPower = (EditText) findViewById(R.id.etInputMyCharacterSuperPower);
        addManyCharacters = (CheckBox) findViewById(R.id.cbAddManyCharacters);
        inputLayoutName = (TextInputLayout) findViewById(R.id.tilMyCharacterName);
        inputLayoutSuperPower = (TextInputLayout) findViewById(R.id.tilMyCharacterSuperPower);
        addPicture = (Button) findViewById(R.id.pickPicture);
        takePhoto = (Button) findViewById(R.id.makePicture);
        myCharacterPreView = (ImageView) findViewById(R.id.imgMyCharacterPreView);

        addMyCharacterPresenter = new AddMyCharacterPresenter();

        ImageManager.setPreview(this, myCharacterPreView, null);

        addPicture.setOnClickListener(v -> {
            try {
                if ((ActivityCompat.checkSelfPermission(AddMyCharactersActivity.this, Manifest.permission.READ_EXTERNAL_STORAGE)
                        == PackageManager.PERMISSION_GRANTED)) {
                    pickPhoto();
                } else {
                    ActivityCompat.requestPermissions(AddMyCharactersActivity.this,
                            new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, REQUEST_PICK_FROM_GALLERY);
                }
            } catch (IllegalArgumentException e) {
                e.printStackTrace();
            }
        });

        takePhoto.setOnClickListener(v -> {
            try {
                if ((ActivityCompat.checkSelfPermission(AddMyCharactersActivity.this, Manifest.permission.WRITE_EXTERNAL_STORAGE)
                        == PackageManager.PERMISSION_GRANTED)) {
                    takePhoto();
                } else {
                    ActivityCompat.requestPermissions(AddMyCharactersActivity.this,
                            new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},
                            REQUEST_PHOTO_FROM_CAMERA);
                }
            } catch (IllegalArgumentException e) {
                e.printStackTrace();
            }
        });

        inputLayoutName.setHint(getString(R.string.name_hint));
        myCharacterName.setOnFocusChangeListener((v, hasFocus) -> {
            if (hasFocus) {
                inputLayoutName.setHint(getString(R.string.name_hint_on_focus));
            } else {
                inputLayoutName.setHint(getString(R.string.name_hint));
            }
        });

        inputLayoutSuperPower.setHint(getString(R.string.superpower_hint));
        myCharacterSuperPower.setOnFocusChangeListener((v, hasFocus) -> {
            if (hasFocus) {
                inputLayoutSuperPower.setHint(getString(R.string.superpower_hint_on_focus));
            } else {
                inputLayoutSuperPower.setHint(getString(R.string.superpower_hint));
            }
        });

        addMyCharacterPresenter.attachView(this);
    }

    private void pickPhoto() {
        Intent galleryIntent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(galleryIntent, REQUEST_PICK_FROM_GALLERY);
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
                case REQUEST_PICK_FROM_GALLERY: {
                    imageUri = Uri.parse(ImageManager.getImagePath(this, data.getData()));
                    myCharacterPreView.setImageURI(imageUri);
                    break;
                }
                case REQUEST_PHOTO_FROM_CAMERA: {
                    Bitmap thumbnail;
                    try {
                        thumbnail = MediaStore.Images.Media.getBitmap(getContentResolver(), imageUri);
                        myCharacterPreView.setImageBitmap(thumbnail);
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
            case REQUEST_PICK_FROM_GALLERY: {
                if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    pickPhoto();
                } else {
                    addMyCharacterPresenter.galleryPermissionDenied();
                }
                break;
            }
            case REQUEST_PHOTO_FROM_CAMERA: {
                if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    takePhoto();
                } else {
                    addMyCharacterPresenter.cameraPermissionDenied();
                }
                break;
            }
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        addMyCharacterPresenter.detachView();
    }

    @Override
    public void onAdded() {
        Toast.makeText(AddMyCharactersActivity.this, R.string.my_character_added, Toast.LENGTH_SHORT)
                .show();
    }

    @Override
    public void onFailure() {
        Toast.makeText(AddMyCharactersActivity.this, R.string.my_character_add_fail, Toast.LENGTH_SHORT)
                .show();
    }

    @Override
    public void resetUI() {
        myCharacterName.setText(null);
        myCharacterSuperPower.setText(null);
        imageUri = null;
        ImageManager.setImage(myCharacterPreView, false, null);
    }

    @Override
    public void onAddFinish() {
        onBackPressed();
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
    public void onValidatePicturePathFail() {
        Toast.makeText(AddMyCharactersActivity.this, R.string.pick_my_character_picture, Toast.LENGTH_SHORT)
                .show();
    }

    @Override
    public void hideErrors() {
        inputLayoutName.setError(null);
        inputLayoutSuperPower.setError(null);
    }

    @Override
    public void galleryPermissionDenied() {
        Toast.makeText(AddMyCharactersActivity.this, R.string.galery_permission_denied, Toast.LENGTH_SHORT)
                .show();
    }

    @Override
    public void cameraPermissionDenied() {
        Toast.makeText(AddMyCharactersActivity.this, R.string.camera_permission_denied, Toast.LENGTH_SHORT)
                .show();
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_activity_add_my_characters, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_add_my_character: {
                String name = myCharacterName.getText().toString();
                String superPower = myCharacterSuperPower.getText().toString();
                addMyCharacterPresenter.addMyCharacter(name, superPower, imageUri, addManyCharacters.isChecked());
                return true;
            }
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
