package com.example.flyingfish;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Typeface;
import android.graphics.BitmapFactory;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Toast;


public class FlyingFishView extends View {
    private Bitmap fish[] = new Bitmap[2];
    private int fishX = 10;
    private int fishY;
    private int canvasWidth, canvasHeight;
    private Bitmap backgroundImage;
    private Paint scorePaint = new Paint();
    private Bitmap life[] = new Bitmap[2];
    private boolean touch = false;
    private int yellowX;
    private int yellowY;
    private Paint yellowPaint = new Paint();
    private int score,lifeCounterofFish;
    private int greenX, greenY, greenSpeed = 20;
    private Paint greenPaint = new Paint();

    private int redX, redY, redSpeed = 25;
    private Paint redPaint = new Paint();

  private int fishSpeed;

    public FlyingFishView(Context context) {
        super(context);
        fish[0]= BitmapFactory.decodeResource(getResources(),R.drawable.fish1);
        fish[1]= BitmapFactory.decodeResource(getResources(),R.drawable.fish2);
        backgroundImage = BitmapFactory.decodeResource(getResources(), R.drawable.background);

        yellowPaint.setColor(Color.YELLOW);
        yellowPaint.setAntiAlias(false);

        greenPaint.setColor(Color.GREEN);
        greenPaint.setAntiAlias(false);

       redPaint.setColor(Color.RED);
        redPaint.setAntiAlias(false);

        scorePaint.setColor(Color.WHITE);
        scorePaint.setTextSize(70);
        scorePaint.setTypeface(Typeface.DEFAULT_BOLD);
        scorePaint.setAntiAlias(true);

        life[0] = BitmapFactory.decodeResource(getResources(), R.drawable.hearts);
        life[1] = BitmapFactory.decodeResource(getResources(), R.drawable.heart_grey);

        fishY = 550;
        score = 0;
        lifeCounterofFish = 3;
    }

    @Override
    protected void onDraw(Canvas canvas)
    {
        super.onDraw(canvas);
        canvasWidth = canvas.getWidth();
        canvasHeight = canvas.getHeight();

        canvas.drawBitmap(backgroundImage, 0, 0, null);

        int minFishY = fish[0].getHeight();
        int maxFishY = canvasHeight - fish[0].getHeight()*3;
        fishY = fishY + fishSpeed;

        if (fishY < minFishY){
            fishY = minFishY;
        }
        if (fishY > maxFishY){
            fishY = maxFishY;
        }

        fishSpeed = fishSpeed + 2;
        if (touch)
        {
            canvas.drawBitmap(fish[1], fishX, fishY, null);
            touch = false;
        }
        else{
            canvas.drawBitmap(fish[0], fishX, fishY, null);
        }


        int yellowSpeed = 6;
        yellowX = yellowX - yellowSpeed;

        if (hitBallChecker( yellowX, yellowY )){
            score = score + 10;            // whenever fish gets the yellow ball, we will increase the score by 10
            yellowX = - 100;

        }
        if(yellowX < 0){
            yellowX = canvasWidth + 21;
            yellowY = (int)Math.floor(Math.random()*(maxFishY - minFishY)) + minFishY; // for appearence of ball in random
            // postion on screen which will be created by canvas.drawCircle method below and the fish will get that ball,
            // As the fish gets the ball, it will increment in score of  player
        }

        canvas.drawCircle(yellowX, yellowY, 25, yellowPaint);
        // creating a random ball which will appear on random

        greenX = greenX - greenSpeed;

        if (hitBallChecker( greenX, greenY )){
            score = score + 20;            // whenever fish gets the yellow ball, we will increase the score by 10
           greenX = - 100;

        }
        if(greenX < 0){
            greenX = canvasWidth + 21;
           greenY = (int)Math.floor(Math.random()*(maxFishY - minFishY)) + minFishY; // for appearence of ball in random
            // postion on screen which will be created by canvas.drawCircle method below and the fish will get that ball,
            // As the fish gets the ball, it will increment in score of  player
        }

        canvas.drawCircle(greenX, greenY, 25, greenPaint);


        redX = redX - redSpeed;

        if (hitBallChecker( redX, redY )){

           redX = - 100;
           lifeCounterofFish--;

           if (lifeCounterofFish == 0){
               Toast.makeText( getContext(), "Game Over", Toast.LENGTH_SHORT ).show( );

           Intent gameOverIntent= new Intent( getContext(), GameOverActivity.class );
           gameOverIntent.addFlags( Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK );
           getContext().startActivity( gameOverIntent );
           }

        }
        if(redX < 0){
          redX = canvasWidth + 21;
            redY = (int)Math.floor(Math.random()*(maxFishY - minFishY)) + minFishY; // for appearence of ball in random
            // postion on screen which will be created by canvas.drawCircle method below and the fish will get that ball,
            // As the fish gets the ball, it will increment in score of  player
        }

        canvas.drawCircle(redX, redY, 30, redPaint);

        canvas.drawText("Score : " +score, 20, 60, scorePaint);   // position thatswhy we used Math.random above in calculations
        for (int i=0;i<3 ;i++){
            int x=(int) (580 + life[0].getWidth() * 1.5*i);
            int y= 30;

            if(i<lifeCounterofFish)
            {
                canvas.drawBitmap(life[0], x, y, null);
            }
            else{

                canvas.drawBitmap(life[1], x, y, null);
            }

        }






    }

    //whenever fish gets that ball, that ball should disappear and incremenet in marks

    public boolean hitBallChecker(int x, int y){
        if (fishX < x && x < (fishX + fish[0].getWidth()) && fishY < y && y < (fishY + fish[0].getHeight() ) )
        {
            return true;
        }

        return false;
    }

    public boolean onTouchEvent(MotionEvent event){
        if (event.getAction() == MotionEvent.ACTION_DOWN){
            touch = true;
            fishSpeed = -22;
        }
        return true;
    }
}
