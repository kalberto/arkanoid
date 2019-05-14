package com.example.alber.arkanoid;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;

public class RenderView extends View {
    Paint paint = new Paint();
    float startTime;
    TextGameObject scoreTGB;
    TextGameObject lifeTGB;
    GestureDetector gestureDetector;
    ParallaxGameObject fundoPGB;
    PaddleGameObject paddle;
    BallGameObject ball;

    public RenderView(Context context) {
        super(context);

        gestureDetector = new GestureDetector(context,
                new MyGestureListener());

        startTime = System.nanoTime();
        scoreTGB = new TextGameObject();
        scoreTGB.x = 20;
        scoreTGB.y = 45;
        scoreTGB.textSize = 40;
        scoreTGB.color = Color.rgb(0,255,0);
        scoreTGB.text = "Score :";
        scoreTGB.count = 0;
        scoreTGB.layer = 100;
        scoreTGB.isAlive = true;
        lifeTGB = new TextGameObject();
        lifeTGB.x = 600;
        lifeTGB.y = 45;
        lifeTGB.textSize = 40;
        lifeTGB.color = Color.rgb(0,255,0);
        lifeTGB.text = "Vidas: ";
        lifeTGB.count = 3;
        lifeTGB.layer = 100;
        lifeTGB.isAlive = true;
        GameResources.getInstance().addObject(scoreTGB);
        GameResources.getInstance().addObject(lifeTGB);
        paddle = new PaddleGameObject("teste_paddle.png",context.getAssets(),200, 650);
        ball = new BallGameObject("ball.png", context.getAssets(),200,600,paddle);
        ball.score = scoreTGB;
        paddle.ball = ball;
        GameResources.getInstance().addObject(paddle);
        GameResources.getInstance().addObject(ball);

        BrickGameObject brick = new BrickGameObject("brick.png",context.getAssets(),0,100,false,null,1,1);
        for(int i = 1; i < 10; i++){
            GameResources.getInstance().addObject(new BrickGameObject("brick.png",context.getAssets(),(100*i),150,true,brick.anim,1,1));
        }
        brick = new BrickGameObject("brick2.png",context.getAssets(),0,100,false,null,1,1);
        for(int i = 1; i < 10; i++){
            GameResources.getInstance().addObject(new BrickGameObject("brick2.png",context.getAssets(),(100*i),100,true,brick.anim,1,1));
        }
        startTime = System.nanoTime();
    }

    @Override
    protected void onLayout(boolean changed,
                            int left, int top, int right, int bottom) {
        super.onLayout(changed, left, top, right, bottom);

        if(fundoPGB == null){
            fundoPGB = new ParallaxGameObject();
            fundoPGB.isAlive = true;
            fundoPGB.loadImage("fundo_arkanoid.jpg",
                    getContext().getAssets(),
                    getWidth(),getHeight(),false,true);
            GameResources.getInstance().addObject(fundoPGB);
        }else{
            fundoPGB.changeSize(getWidth(),getHeight(),
                    false,true);
        }
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if(event.getAction() == MotionEvent.ACTION_DOWN){
            if(paddle.x > event.getX()){
                paddle.startWalk(-1);
            }else if(paddle.x < event.getX()){
                paddle.startWalk(1);
            }
        }
        if(event.getAction() == MotionEvent.ACTION_UP){
            paddle.stopWalk();
        }
        return gestureDetector.onTouchEvent(event);
    }

    @Override
    public void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        float deltaTime = (System.nanoTime() - startTime)/1000000.0f;
        startTime = System.nanoTime();
        canvas.drawRGB(200,200,200);
        if(GameResources.getInstance().numberOfDead >= 4){
            GameResources.getInstance().reset();
            scoreTGB.count = 0;
        }
        GameResources.getInstance().
                updateAndDraw(canvas,paint,deltaTime);

        invalidate();
    }

    private final class MyGestureListener
            extends GestureDetector.SimpleOnGestureListener{

        @Override
        public boolean onDown(MotionEvent e) {
            return true;
        }

        @Override
        public boolean onSingleTapConfirmed(MotionEvent e) {
            return super.onSingleTapConfirmed(e);
        }

        @Override
        public boolean onDoubleTap(MotionEvent e) {
            ball.shootBall();
            return super.onDoubleTap(e);
        }

        @Override
        public void onLongPress(MotionEvent e) {
            super.onLongPress(e);
        }

        @Override
        public boolean onFling(MotionEvent e1, MotionEvent e2,
                               float velocityX, float velocityY) {
            boolean result = false;

            float diffX = e2.getX() - e1.getX();
            float diffY = e2.getY() - e1.getY();

            if(Math.abs(diffX) > Math.abs(diffY)){//horizontal
                if(Math.abs(diffX) > 100 && Math.abs(velocityX) > 100){
                    if(diffX > 0){//Swipe Right
                    }else{
                    }
                }
            }else{//vertical
                if(Math.abs(diffY) > 100 && Math.abs(velocityY) > 100){
                    if(diffY > 0){//Swipe Down
                    }else{
                    }
                }
            }

            return result;
        }

        @Override
        public boolean onSingleTapUp(MotionEvent e){
            return super.onSingleTapUp(e);

        }
    }
}
