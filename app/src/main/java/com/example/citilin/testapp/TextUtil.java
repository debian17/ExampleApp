package com.example.citilin.testapp;

import android.text.TextUtils;
import android.widget.TextView;

/**
 * Created by Андрей on 28-Aug-17.
 */

public class TextUtil {

    public static void setText(TextView textView, String text, String defaultText) {
        if (TextUtils.isEmpty(text)) {
            textView.setText(defaultText);
        } else {
            textView.setText(text);
        }
    }

}
