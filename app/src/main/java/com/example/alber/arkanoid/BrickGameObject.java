package com.example.alber.arkanoid;

import android.content.res.AssetManager;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;

public class BrickGameObject extends AnimatedImageGameObject {
    AssetManager manager;
    int point;
    int life;
    int startLife;
    public BrickGameObject(String file, AssetManager pManager, float pX, float pY,int framesW, int framesH, boolean hasBitmap, Bitmap[] bitmaps, int pPoints, int pLife){
        manager = pManager;
        loadImage(file,manager,framesW,framesH, hasBitmap, bitmaps);
        point = pPoints;
        startLife = life = pLife;
        x = pX;
        y = pY;
        colide = true;
        id = "brick";
        layer = 5;
        isAlive = true;
        currentFrame = 0;
    }

    public void update(float deltaTime){

    }

    public void destroy(){
        colide = false;
        isAlive = false;
    }

    public void reset(){
        colide = true;
        life = startLife;
        isAlive = true;
    }

    public boolean hit(){
        life--;
        if(life <= 0){
            destroy();
            return true;
        }else{
            currentFrame++;
            return false;
        }
    }
}
