package com.example.alber.arkanoid;

import android.content.res.AssetManager;
import android.widget.Button;
import android.widget.TextView;

public class BallGameObject extends AnimatedImageGameObject {
    AssetManager manager;
    PaddleGameObject paddle;
    TextGameObject score;
    TextGameObject life;
    public Button resetButton;
    public TextView textFimJogo;

    boolean dieing = false, isOnPaddle = true, reseting = false;
    float deadTime = 5000, maxPosX = 1000, maxPosY = 700, currentDead = 0, speed = 500,speedPaddle = 300,startX, startY;
    float directionX = 0, directionY = 0 ;
    public BallGameObject(String file, AssetManager pManager, float pX, float pY, PaddleGameObject pPaddle){
        manager = pManager;
        loadImage(file,manager,1,1,false,null);
        startX = x = pX;
        startY = y = pY;
        colide = true;
        paddle = pPaddle;
        id = "ball";
        isAlive = true;
    }

    public void update(float deltaTime){
        if(reseting){
            reseting = false;
        }
        if(!isAlive){
            currentDead += deltaTime;
            if(currentDead >= deadTime){
                if(life.count <= 0)
                    reset();
                else
                    respawn();
            }
        }else{
            if(!isOnPaddle){
                float nextX = x + directionX*speed*deltaTime*0.001f;
                if((nextX + w) < maxPosX && (nextX) > 0)
                    x = nextX;
                else
                    changeDirX();
                float nextY = y + directionY*speed*deltaTime*0.001f;
                if(nextY > 0){
                    if(nextY + h >= maxPosY){
                        paddle.destroy();
                        destroy();
                    }else{
                        y = nextY;
                    }
                }else{
                    y = 0;
                    changeDirY();
                }
            }
        }
    }

    public void changeDirX(){
        directionX = -directionX;
    }
    public void changeDirY(){
        directionY = -directionY;
    }

    public void shootBall(){
        directionX = -0.5f;
        directionY = -0.5f;
        isOnPaddle = false;
    }

    public void walk(float deltaTime, float direction){
        if(isOnPaddle){
            x += speedPaddle*direction*deltaTime*0.001f;
            if(direction > 0){
                if((x + w) > maxPosX)
                    x = maxPosX-w;
            }else{
                if((x)< 0)
                    x = 0;
            }
        }
    }

    public void addScore(int pPoint){
        score.addCount(pPoint);
    }

    public void respawn(){
        isAlive = true;
        isOnPaddle = true;
        x = startX;
        y = startY;
        currentDead = 0;
    }

    public void reset(){
        isAlive = true;
        isOnPaddle = true;
        x = startX;
        y = startY;
        currentDead = 0;
        score.count = 0;
        life.count = 0;
        reseting = true;
    }

    public void destroy(){
        life.addCount(-1);
        isAlive = false;
    }
}
