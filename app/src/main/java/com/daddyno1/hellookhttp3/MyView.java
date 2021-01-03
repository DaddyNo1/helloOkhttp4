package com.daddyno1.hellookhttp3;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

import androidx.annotation.Nullable;

public class MyView extends View {

    float lastedX;
    float lastedY;

    Paint paint = new Paint();
    {
        paint.setColor(Color.BLUE);
    }

    public MyView(Context context) {
        super(context);
    }

    public MyView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public MyView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {

        if(event.getAction() == MotionEvent.ACTION_DOWN){
            lastedX = event.getX();
            lastedY = event.getY();
        }else if(event.getAction() == MotionEvent.ACTION_MOVE){
            float x = event.getX();
            float y = event.getY();
            float deltaX =  x - lastedX;
            float deltaY = y - lastedY;
            layout(getLeft() + (int)deltaX, getTop() + (int)deltaY, getRight() + (int)deltaX, getBottom() + (int)deltaY);
        }

        return true;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        canvas.drawCircle(60, 60, 60, paint);
    }
}
