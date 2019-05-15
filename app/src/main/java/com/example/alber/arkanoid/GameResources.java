package com.example.alber.arkanoid;

import android.graphics.Canvas;
import android.graphics.Paint;

import java.util.ArrayList;
import java.util.List;

class GameResources {
    private static final GameResources ourInstance = new GameResources();

    int numberOfDead = 0;

    static GameResources getInstance() {
        return ourInstance;
    }

    private GameResources() {
    }

    List<GameObject> gameObjects = new ArrayList<>();

    public void addObject(GameObject obj){
        for(int i = 0;i<gameObjects.size();i++){
            if(obj.layer <= gameObjects.get(i).layer){
                gameObjects.add(i,obj);
                return;
            }
        }
        gameObjects.add(obj);
    }
    public void updateAndDraw(Canvas canvas, Paint paint,
                              float deltaTime){
        for (GameObject obj: gameObjects) {
            obj.update(deltaTime);
            if(obj.isAlive)
                obj.draw(canvas,paint);
            if(obj.colide){
                if(obj.id.equals("ball")){
                    for (GameObject obj2: gameObjects){
                        if(obj2.colide){
                            if(obj2.id.equals("paddle")){
                                if(((AnimatedImageGameObject)obj).getBoundBox().intersect(((AnimatedImageGameObject)obj2).getBoundBox())){
                                    obj.y = obj2.y - obj.w - 10;
                                    ((BallGameObject)obj).changeDirY();
                                    float relativeX = ((obj.x+obj.w*0.5f) - (obj2.x + obj2.w*0.5f))/(obj2.w*0.5f);
                                    float relativeY = -(float)Math.sqrt(1 - relativeX*relativeX);
                                    ((BallGameObject)obj).directionX = relativeX;
                                    ((BallGameObject)obj).directionY = relativeY;
                                }
                            }
                            if(obj2.id.equals("brick")){
                                if(((AnimatedImageGameObject)obj).getBoundBox().intersect(((AnimatedImageGameObject)obj2).getBoundBox())){
                                    if(((BrickGameObject)obj2).hit()){
                                        ((BallGameObject)obj).addScore(((BrickGameObject)obj2).point);
                                        ((BrickGameObject) obj2).destroy();
                                    }
                                    if(obj.getPosXLeft() <= obj2.getPosXRigth() || obj.getPosXRigth() >= obj2.getPosXLeft())
                                        ((BallGameObject)obj).changeDirX();
                                    if(obj.getPosYTop() <= obj2.getPosYBot() || obj.getPosYBot() >= obj2.getPosYTop())
                                        ((BallGameObject)obj).changeDirY();
                                }
                            }
                        }
                    }
                }
            }
        }
    }

    public void reset(){
        numberOfDead = 0;
        for (GameObject obj: gameObjects) {
            if(obj.id.equals("brick")){
                ((BrickGameObject)obj).reset();
            }else if(obj.id.equals("paddle")){
                ((PaddleGameObject)obj).reset();
            }else if(obj.id.equals("ball")){
                ((BallGameObject)obj).reset();
            }
        }
    }


}
