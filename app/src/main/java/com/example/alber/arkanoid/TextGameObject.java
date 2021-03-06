package com.example.alber.arkanoid;

import android.graphics.Canvas;
import android.graphics.Paint;

public class TextGameObject extends GameObject {
    int color = 0;
    float textSize = 10.0f;
    boolean drawCount = true;
    String text = "";
    int count = 0;

    @Override
    public void draw(Canvas canvas, Paint paint) {
        paint.reset();
        paint.setColor(color);
        paint.setTextSize(textSize);
        if(drawCount)
            canvas.drawText(text + ' ' + count,x,y,paint);
        else
            canvas.drawText(text,x,y,paint);
    }

    public void addCount(int pPoint){
        count += pPoint;
    }
}
