package com.example.alber.arkanoid;

import android.graphics.Canvas;
import android.graphics.Paint;

public class GameObject {
    public float x =0.0f,y=0.0f,w=0.0f,h=0.0f;
    public int layer = 0;
    public String id = "";
    public boolean shouldDelete = false;
    public boolean colide = false;
    public boolean pulling = false;
    boolean isAlive;

    public void update(float deltaTime){

    }

    public void draw(Canvas canvas, Paint paint){

    }

    public float getPosXRigth(){
        return x+w;
    }

    public float getPosXLeft(){
        return x;
    }

    public float getPosYBot(){
        return y+h;
    }

    public float getPosYTop(){
        return y;
    }
}
