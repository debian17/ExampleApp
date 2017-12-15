package com.example.citilin.testapp.ui.webview;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.support.annotation.Nullable;
import android.text.TextPaint;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;

import com.example.citilin.testapp.R;

public class Counter extends View implements View.OnTouchListener {

    private static final String DEFAULT_TITLE = "Title";
    private static final int DEFAULT_INT_VALUE = 0;
    private static final int Y_VALUE_OFFSET = 70;

    private TextPaint textPaint;
    private float textSize;
    private int textColor;
    private int backgroundColor;
    private String title;
    private int value;
    private float scaledDensity;

    public Counter(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        textPaint = new TextPaint(Paint.ANTI_ALIAS_FLAG);

        TypedArray attributes = context.obtainStyledAttributes(attrs, R.styleable.Counter);

        try {
            textSize = attributes.getFloat(R.styleable.Counter_counter_textSize, DEFAULT_INT_VALUE);
            textColor = attributes.getInt(R.styleable.Counter_counter_textColor, DEFAULT_INT_VALUE);
            backgroundColor = attributes.getInt(R.styleable.Counter_counter_backgroundColor, DEFAULT_INT_VALUE);
            title = attributes.getString(R.styleable.Counter_counter_title);
            value = attributes.getInt(R.styleable.Counter_counter_value, DEFAULT_INT_VALUE);
        } finally {
            attributes.recycle();
        }
        if (title == null) {
            title = DEFAULT_TITLE;
        }
        scaledDensity = getResources().getDisplayMetrics().scaledDensity;

        textPaint.setTextSize(Math.round(textSize * scaledDensity));

        setOnTouchListener(this);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        Log.e("widthMeasureSpec", String.valueOf(widthMeasureSpec));
        Log.e("heightMeasureSpec", String.valueOf(heightMeasureSpec));
    }

    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        super.onLayout(changed, left, top, right, bottom);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        canvas.drawColor(backgroundColor);

        int centreX = getWidth() / 2;
        int centreY = getHeight() / 2;

        textPaint.setColor(Color.parseColor("#ffffff"));
        canvas.drawLine(centreX, 0, centreX, canvas.getHeight(), textPaint);

        float titleWidth = textPaint.measureText(title);

        float titleX = Math.round(centreX - titleWidth * 0.5);
        textPaint.setColor(textColor);
        canvas.drawText(title, titleX, centreY, textPaint);

        float valueWidth = textPaint.measureText(String.valueOf(value));
        float valueX = Math.round(centreX - valueWidth * 0.5f);
        canvas.drawText(String.valueOf(value), valueX, centreY + Y_VALUE_OFFSET, textPaint);
    }

    public void plusOne() {
        value += 1;
        invalidate();
    }

    public void minusOne() {
        value -= 1;
        invalidate();
    }

    @Override
    public boolean onTouch(View view, MotionEvent motionEvent) {
        int action = motionEvent.getActionMasked();

        switch (action) {
            case MotionEvent.ACTION_DOWN: {
                Log.d("onTouch", "DOWN");
                break;
            }
            case MotionEvent.ACTION_UP: {
                Log.d("onTouch", "UP");
                break;
            }
            case MotionEvent.ACTION_CANCEL: {
                Log.d("onTouch", "CANCEL");
                break;
            }
        }
        return true;
    }
}
