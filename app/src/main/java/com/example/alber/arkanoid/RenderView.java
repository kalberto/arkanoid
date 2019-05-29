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
    TextGameObject winTGB;
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

        initVars();

        GameResources.getInstance().winText = winTGB;
        ball = new BallGameObject("ball.png", context.getAssets(),200,600,paddle);
        paddle = new PaddleGameObject("teste_paddle.png",context.getAssets(),200, 650);

        ball.score = scoreTGB;
        ball.life = lifeTGB;
        ball.paddle = paddle;
        paddle.ball = ball;
        GameResources.getInstance().addObject(paddle);
        GameResources.getInstance().addObject(ball);
        BrickGameObject brick = new BrickGameObject("brick.png",context.getAssets(),0,100,1,1,false,null,1,1);
        for(int i = 1; i < 12; i++){
            GameResources.getInstance().addObject(new BrickGameObject("brick.png",context.getAssets(),(55*i),155,1,1,true,brick.anim,1,1));
            GameResources.getInstance().numberBricks+=1;
        }
        brick = new BrickGameObject("brick2.png",context.getAssets(),0,100,2,1,false,null,1,1);
        for(int i = 1; i < 12; i++){
            GameResources.getInstance().addObject(new BrickGameObject("brick2.png",context.getAssets(),(55*i),100,2,1,true,brick.anim,2,2));
            GameResources.getInstance().numberBricks+=1;
        }
        startTime = System.nanoTime();
    }

    protected void initVars(){

        //WIN
        winTGB = new TextGameObject();
        winTGB.x = 10;
        winTGB.y = 250;
        winTGB.textSize = 75;
        winTGB.color = Color.rgb(0,255,0);
        winTGB.text = "VOCÊ GANHOU";
        winTGB.count = 0;
        winTGB.drawCount = false;
        winTGB.layer = 100;
        winTGB.isAlive = false;
        GameResources.getInstance().addObject(winTGB);

        //SCORE
        scoreTGB = new TextGameObject();
        scoreTGB.x = 20;
        scoreTGB.y = 45;
        scoreTGB.textSize = 40;
        scoreTGB.color = Color.rgb(0,255,0);
        scoreTGB.text = "Score :";
        scoreTGB.count = 0;
        scoreTGB.layer = 100;
        scoreTGB.isAlive = true;
        GameResources.getInstance().addObject(scoreTGB);
        //LIFE
        lifeTGB = new TextGameObject();
        lifeTGB.x = 550;
        lifeTGB.y = 45;
        lifeTGB.textSize = 40;
        lifeTGB.color = Color.rgb(0,255,0);
        lifeTGB.text = "Vidas: ";
        lifeTGB.count = 3;
        lifeTGB.layer = 100;
        lifeTGB.isAlive = true;
        GameResources.getInstance().addObject(lifeTGB);
    }

    @Override
    protected void onLayout(boolean changed,
                            int left, int top, int right, int bottom) {
        super.onLayout(changed, left, top, right, bottom);
        paddle.maxPos = ball.maxPosX = getWidth();
        winTGB.y = getHeight()*0.5f;
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
            float middle = getWidth() * 0.5f;
            if(middle > event.getX()){
                paddle.startWalk(-1);
            }else if(middle < event.getX()){
                if(paddle.x + paddle.w < getWidth())
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
            if(ball.isOnPaddle)
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
