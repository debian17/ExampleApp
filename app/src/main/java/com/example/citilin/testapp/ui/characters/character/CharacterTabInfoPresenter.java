package com.example.citilin.testapp.ui.characters.character;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v4.app.FragmentManager;

import com.example.citilin.testapp.R;
import com.example.citilin.testapp.network.ImageLoader;
import com.example.citilin.testapp.ui.characters.ImageCacheManager;
import com.vk.sdk.api.VKError;
import com.vk.sdk.api.photo.VKImageParameters;
import com.vk.sdk.api.photo.VKUploadImage;
import com.vk.sdk.dialogs.VKShareDialogBuilder;

import java.io.File;

import io.reactivex.disposables.CompositeDisposable;

/**
 * Created by Андрей on 29-Aug-17.
 */

class CharacterTabInfoPresenter {

    private Context context;
    private File imageFile;
    private Uri imageUri;
    private String shareText;
    private FragmentManager fragmentManager;
    private CompositeDisposable compositeDisposable;
    private static final String TAG = "VK_SHARE_DIALOG";

    private View view;

    public interface View {
        void onShareFailed();

        void startProgress();

        void stopProgress();

        void shareToVKComplete();

    }

    CharacterTabInfoPresenter(Context context) {
        this.context = context;
        compositeDisposable = new CompositeDisposable();
    }

    public void attachView(@NonNull View view) {
        this.view = view;
    }

    public void detachView() {
        view = null;
        compositeDisposable.clear();
    }

    void shareCharacter(int characterId, String imageUrl, String text) {

        if (!ImageCacheManager.createCacheDir(context)) {
            view.onShareFailed();
            return;
        }
        imageFile = ImageCacheManager.createImageFile(context, String.valueOf(characterId));
        shareText = text;
        if (imageFile.exists()) {
            imageUri = ImageCacheManager.getUriForFile(context, imageFile);
            sendIntent(context, imageUri, shareText);
        } else {
            ImageLoader imageLoader = new ImageLoader();
            view.startProgress();
            compositeDisposable.add(imageLoader.loadImage(imageUrl)
                    .subscribe(this::imageToShareLoaded, this::imageToShareLoadError));
        }
    }

    private void imageToShareLoaded(Bitmap bitmap) {
        if(view!=null){
            if (bitmap == null) {
                view.onShareFailed();
            } else {
                imageFile = ImageCacheManager.saveBitmapToFile(bitmap, imageFile);
                imageUri = ImageCacheManager.getUriForFile(context, imageFile);
                sendIntent(context, imageUri, shareText);
            }
            view.stopProgress();
        }
    }

    private void imageToShareLoadError(Throwable t) {
        if(view!=null){
            view.stopProgress();
            view.onShareFailed();
        }
    }

    private void sendIntent(Context context, Uri imageUri, String text) {
        Intent sharingIntent = new Intent(Intent.ACTION_SEND);
        sharingIntent.putExtra(Intent.EXTRA_TEXT, text);
        sharingIntent.putExtra(Intent.EXTRA_STREAM, imageUri);
        sharingIntent.setType("*/*");
        if (context.getPackageManager().queryIntentActivities(sharingIntent, 0).size() != 0) {
            context.startActivity(Intent.createChooser(sharingIntent, context.getString(R.string.share_hero)));
        } else {
            view.onShareFailed();
        }
    }

    void postVK(FragmentManager fragmentManager, String imageUrl, String text) {
        this.fragmentManager = fragmentManager;
        shareText = text;
        ImageLoader imageLoader = new ImageLoader();
        view.startProgress();
        compositeDisposable.add(imageLoader.loadImage(imageUrl)
                .subscribe(this::imageForVKLoaded, this::imageForVKLoadedError));
    }

    private void imageForVKLoaded(Bitmap bitmap) {
        VKShareDialogBuilder builder = new VKShareDialogBuilder();
        builder.setText(shareText);
        builder.setShareDialogListener(new VKShareDialogBuilder.VKShareDialogListener() {
            @Override
            public void onVkShareComplete(int postId) {
                view.shareToVKComplete();
            }

            @Override
            public void onVkShareCancel() {
            }

            @Override
            public void onVkShareError(VKError error) {
                view.onShareFailed();
            }
        });
        builder.setAttachmentImages(new VKUploadImage[]{
                new VKUploadImage(bitmap, VKImageParameters.pngImage())
        });

        if (view != null) {
            view.stopProgress();
            builder.show(fragmentManager, TAG);
        }
    }

    private void imageForVKLoadedError(Throwable t) {
        if (view != null) {
            view.stopProgress();
            view.onShareFailed();
        }
    }
}
