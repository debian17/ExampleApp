package com.example.citilin.testapp;

import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.v4.content.FileProvider;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;
import com.example.citilin.testapp.network.ApiHelper;

import java.io.File;
import java.io.IOException;

/**
 * Created by Андрей on 28-Aug-17.
 */

public class ImageManager {

    private static final String IMAGES_PATH = "/Marvel/Pictures";

    public static void setImage(ImageView imageView, boolean circleMode, String imagePath) {
        Context context = App.getAppContext();
        if (imagePath == null) {
            if (circleMode) {
                Glide.with(context)
                        .load(R.drawable.stub)
                        .apply(RequestOptions.circleCropTransform()
                                .diskCacheStrategy(DiskCacheStrategy.ALL))
                        .into(imageView);
            } else {
                Glide.with(context)
                        .load(R.drawable.stub)
                        .into(imageView);
            }
        } else {
            if (circleMode) {
                Glide.with(context)
                        .load(imagePath)
                        .apply(RequestOptions.circleCropTransform()
                                .placeholder(R.drawable.stub)
                                .diskCacheStrategy(DiskCacheStrategy.ALL))
                        .into(imageView);
            } else {
                Glide.with(context)
                        .load(imagePath)
                        .apply(RequestOptions.placeholderOf(R.drawable.stub)
                                .diskCacheStrategy(DiskCacheStrategy.ALL))
                        .into(imageView);
            }
        }
    }

    public static String getImagePath(Context context, Uri selectedImage) {

        String[] filePath = {MediaStore.Images.Media.DATA};
        Cursor c;
        int columnIndex;

        c = context.getContentResolver().query(selectedImage, filePath, null, null, null);
        if (c != null) {
            c.moveToFirst();
            columnIndex = c.getColumnIndexOrThrow(filePath[0]);
            String result = c.getString(columnIndex);
            if (!c.isClosed()) {
                c.close();
            }
            return result;
        }
        return null;
    }

    public static void setPreview(Context context, ImageView imageView, @Nullable String picturePath) {
        if (picturePath == null) {
            Glide.with(context)
                    .load(R.drawable.stub)
                    .into(imageView);
        } else {
            File file = new File(picturePath);
            Uri uri = Uri.fromFile(file);
            Glide.with(context)
                    .load(uri)
                    .into(imageView);
        }
    }

    public static Uri createImageUri(Context context) {
        return FileProvider.getUriForFile(context, context.getString(R.string.authority), createImageFile());
    }

    private static File createImageFile() {
        String timeStamp = ApiHelper.getTime();
        String imageFileName = "JPEG_" + timeStamp + "_";
        File root = Environment.getExternalStorageDirectory();
        File storageDir = new File(root, IMAGES_PATH);
        storageDir.mkdirs();
        File image = null;
        try {
            image = File.createTempFile(imageFileName, ".jpg", storageDir);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return image;
    }

}
