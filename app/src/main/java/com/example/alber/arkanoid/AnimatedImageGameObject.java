package com.example.alber.arkanoid;

import android.content.res.AssetManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;

import java.io.InputStream;

public class AnimatedImageGameObject extends GameObject {
    Bitmap anim[];
    int frames;
    int currentFrame = 0;

    public void loadImage(String file, AssetManager manager, int framesW, int framesH, boolean hasBitmap ,Bitmap[] pBitmap){
        frames = framesW*framesH;
        if(hasBitmap){
            anim = pBitmap;
            w = pBitmap[0].getWidth()/framesW;
            h = pBitmap[0].getHeight()/framesH;
        }
        else{
            try{
                InputStream inputStream = manager.open(file);
                Bitmap bitmap = BitmapFactory.decodeStream(inputStream);
                inputStream.close();

                anim = new Bitmap[frames];
                w = bitmap.getWidth()/framesW;
                h = bitmap.getHeight()/framesH;
                int index = 0;
                for(int i = 0; i < framesW;i++){
                    for (int j = 0; j < framesH; j++){
                        anim[index] = Bitmap.createBitmap(bitmap, (int)(i*w),(int)(j*h),(int)w,(int)h);
                        index++;
                    }
                }

            }catch(Exception e){
                e.printStackTrace();
            }
        }
    }

    float elapsedTime = 0;
    float timeToNextFrame = 125;
    @Override
    public void update(float deltaTime) {
        super.update(deltaTime);
        elapsedTime += deltaTime;
        if(elapsedTime > 125){
            elapsedTime = 0;
            currentFrame++;
            if(currentFrame >= frames)
                currentFrame = 0;
        }
    }

    public Rect getBoundBox(){
        return new Rect((int)(x),(int)(y),(int)(x + w),(int)(y + h));
    }

    @Override
    public void draw(Canvas canvas, Paint paint) {
        super.draw(canvas, paint);
        canvas.drawBitmap(anim[currentFrame],x,y,paint);
    }
}
