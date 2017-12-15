package com.example.citilin.testapp.ui.characters;

import android.content.Context;
import android.graphics.Bitmap;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v4.content.FileProvider;
import android.text.format.Formatter;

import com.example.citilin.testapp.R;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

/**
 * Created by Андрей on 29-Aug-17.
 */

public class ImageCacheManager {

    private static final String SHARING_IMAGES_PATH = "/SharingPictures";
    private static final String FILE_EXTENSION = ".jpg";

    public static File createImageFile(Context context, @NonNull String fileName) {
        return new File(context.getFilesDir(), SHARING_IMAGES_PATH + "/" + fileName + FILE_EXTENSION);
    }

    public static boolean createCacheDir(Context context) {
        File storageDir = new File(context.getFilesDir(), SHARING_IMAGES_PATH);
        return storageDir.exists() || storageDir.mkdirs();
    }

    public static Uri getUriForFile(Context context, File file) {
        return FileProvider.getUriForFile(context, context.getString(R.string.authority), file);
    }

    public static File saveBitmapToFile(Bitmap bitmap, File file) {
        FileOutputStream fileOutputStream;
        try {
            fileOutputStream = new FileOutputStream(file);
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, fileOutputStream);
            fileOutputStream.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return file;
    }

    public static boolean cleanCache(Context context) {
        File cacheDir = new File(context.getFilesDir(), SHARING_IMAGES_PATH);
        File[] listFile = cacheDir.listFiles();
        boolean result = true;
        if (listFile != null) {
            for (File file : listFile) {
                if (!file.delete()) {
                    result = false;
                }
            }
            return result;
        } else {
            return true;
        }
    }

    public static String calculateCacheSize(Context context) {
        File cacheDir = new File(context.getFilesDir(), SHARING_IMAGES_PATH);
        File[] listFile = cacheDir.listFiles();
        if (listFile == null || listFile.length == 0) {
            return Formatter.formatFileSize(context, 0);
        } else {
            return Formatter.formatFileSize(context, getLengthFromFileS(listFile));
        }
    }

    private static long getLengthFromFileS(File[] files) {
        long size = 0;
        for (File file : files) {
            size += file.length();
        }
        return size;
    }
}
