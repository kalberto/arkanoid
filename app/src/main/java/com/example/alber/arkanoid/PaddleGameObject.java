package com.example.alber.arkanoid;

import android.content.res.AssetManager;

public class PaddleGameObject extends AnimatedImageGameObject {
    AssetManager manager;
    BallGameObject ball;
    boolean dieing = false;
    float deadTime = 5000;
    float currentDead = 0;
    float speed = 300;
    float maxPos = 1000;
    float startX, startY;
    boolean walking = false;
    float direction = 0;
    public PaddleGameObject(String file, AssetManager pManager, float pX, float pY){
        manager = pManager;
        loadImage(file,manager,1,3,false, null);
        startX = x = pX;
        startY = y = pY;
        colide = true;
        id = "paddle";
        layer = 5;
        isAlive = true;
    }

    public void update(float deltaTime){
        elapsedTime += deltaTime;
        if(isAlive){
            if(dieing){
                if(elapsedTime > 125){
                    currentFrame++;
                    if(currentFrame >= frames - 1){
                        currentFrame = 0;
                        dieing = false;
                        isAlive = false;
                        colide = true;
                    }
                }
            }else{
                currentFrame = 0;
            }
            if(walking && !dieing){
                walk(deltaTime);
            }
        }else{
            currentDead += deltaTime;
            if(currentDead >= deadTime){
                reset();
            }
        }
    }

    public void reset(){
        isAlive = true;
        currentFrame = 0;
        x = startX;
        y = startY;
        currentDead = 0;
    }

    public void startWalk(float pDirection){
        direction = pDirection;
        walking = true;
    }

    public void stopWalk(){
        direction = 0;
        walking = false;
    }

    public void walk(float deltaTime){
        float possibleX = x + speed*direction*deltaTime*0.001f;
        if(direction > 0){
            if((possibleX + w) > maxPos)
                possibleX = maxPos-w;
        }else{
            if((possibleX)< 0)
                possibleX = 0;
        }
        if(possibleX != x){
            x = possibleX;
            if(ball.isOnPaddle && ball.isAlive)
                ball.walk(deltaTime,direction);
        }
    }

    public void destroy(){
        colide = false;
        dieing = true;
    }
}
