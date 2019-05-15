package com.example.alber.arkanoid;

import android.content.res.AssetManager;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;

public class BrickGameObject extends AnimatedImageGameObject {
    AssetManager manager;
    int point;
    int life;
    public BrickGameObject(String file, AssetManager pManager, float pX, float pY, boolean hasBitmap, Bitmap[] bitmaps, int pPoints, int pLife){
        manager = pManager;
        loadImage(file,manager,1,1, hasBitmap, bitmaps);
        point = pPoints;
        life = pLife;
        x = pX;
        y = pY;
        colide = true;
        id = "brick";
        layer = 5;
        isAlive = true;
    }

    public void update(float deltaTime){
        currentFrame = 0;
    }

    public void destroy(){
        colide = false;
        isAlive = false;
    }

    public void reset(){
        colide = true;
        isAlive = true;
    }

    public boolean hit(){
        life--;
        if(life <= 0){
            destroy();
            return true;
        }
        return false;
    }

    @Override
    public void draw(Canvas canvas, Paint paint) {
        canvas.drawBitmap(anim[currentFrame],x,y,paint);
        super.draw(canvas, paint);
    }
}
