package com.example.alber.arkanoid;

import android.content.res.AssetManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Paint;

import java.io.InputStream;

public class ParallaxGameObject extends GameObject {
    Bitmap bitmap,bitmapReversed;
    float speed = 100;
    boolean walking = true;
    float distance = 500.0f;
    int direction = 1;


    void loadImage(String file, AssetManager assetManager,
                   int width,int height,boolean matchW,boolean matchH){
        try{
            InputStream inputStream = assetManager.open(file);
            bitmap = BitmapFactory.decodeStream(inputStream);
            w = bitmap.getWidth();
            h = bitmap.getHeight();
            inputStream.close();
            changeSize(width,height,matchW,matchH);
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    void  changeSize(int width,int height,
                     boolean matchW,boolean matchH){
        if(matchH){
            int newWidth = (int)(w*height/h);
            bitmap = Bitmap.createScaledBitmap(bitmap,
                    newWidth,height,true);
            w = bitmap.getWidth();
            h = bitmap.getHeight();

            Matrix mirrorMatrix = new Matrix();
            mirrorMatrix.setScale(-1,1);

            bitmapReversed = Bitmap.createBitmap(bitmap,
                    0,0,(int)w,(int)h,
                    mirrorMatrix,true);

        }else if(matchW){
            int newHeight = (int)(h*width/w);
            bitmap = Bitmap.createScaledBitmap(bitmap,
                    width,newHeight,true);
            w = bitmap.getWidth();
            h = bitmap.getHeight();

            Matrix mirrorMatrix = new Matrix();
            mirrorMatrix.setScale(-1,1);

            bitmapReversed = Bitmap.createBitmap(bitmap,
                    0,0,(int)w,(int)h,
                    mirrorMatrix,true);

        }else{

            bitmap = Bitmap.createScaledBitmap(bitmap,
                    width,height,true);
            w = bitmap.getWidth();
            h = bitmap.getHeight();

            Matrix mirrorMatrix = new Matrix();
            mirrorMatrix.setScale(-1,1);

            bitmapReversed = Bitmap.createBitmap(bitmap,
                    0,0,(int)w,(int)h,
                    mirrorMatrix,true);

        }
    }

    public void walk(int pDirection){
        walking = true;
        distance = 500;
        direction = pDirection;
    }

    public void stopWalking(){
        distance = 0;
        walking = false;
    }

    @Override
    public void update(float deltaTime) {
        float walkDistance = deltaTime*speed/1000;
        distance -= walkDistance;
        x -= walkDistance*direction;
        if(x<(-2*w)){
            x = 0;
        }
    }

    @Override
    public void draw(Canvas canvas, Paint paint) {
        if(x > -w){
            canvas.drawBitmap(bitmap,x,y,paint);
        }
        canvas.drawBitmap(bitmapReversed,x+w,y,paint);
        canvas.drawBitmap(bitmapReversed,x-w,y,paint);
        if(x < 0){
            canvas.drawBitmap(bitmap,x+2*w,y,paint);
        }
        else{
            canvas.drawBitmap(bitmap,x-2*w,y,paint);
        }
        if(x < -w){
            canvas.drawBitmap(bitmapReversed,x+3*w,y,paint);

        }

    }
}
