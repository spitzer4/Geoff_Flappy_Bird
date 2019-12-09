package com.example.geoffflappybird;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Typeface;
import android.media.MediaPlayer;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Toast;
import android.os.Vibrator;

import java.net.Inet4Address;

public class GeoffFlappyView extends View {
    private Bitmap geoff[] = new Bitmap[2];
    private int geoffX = 10;
    private int geoffY;
    private int geoffSpeed;

    private Context c = getContext();

    private int canvasWidth, canvasHeight;

    private int obstacleX, obstacleY, obstacleSpeed = 16;
    private Paint obstaclePaint = new Paint();

    private int secondobsX, secondobsY, secondobsSpeed = 20;
    private Paint secondobsPaint = new Paint();

    private int dangerX, dangerY, dangerSpeed = 30;
    private Paint dangerPaint = new Paint();

    private int score;
    private int lifeCounter;

    private boolean touch = false;

    private Bitmap background;
    private Paint scorePaint = new Paint();
    private Bitmap lives[] = new Bitmap[2];


    public GeoffFlappyView(Context context) {
        super(context);

        geoff[0] = BitmapFactory.decodeResource(getResources(), R.drawable.geoffbird);
        geoff[1] = BitmapFactory.decodeResource(getResources(), R.drawable.geoffbird);

        background = BitmapFactory.decodeResource(getResources(), R.drawable.siebel);

        obstaclePaint.setColor(Color.GREEN);
        obstaclePaint.setAntiAlias(false);

        secondobsPaint.setColor(Color.CYAN);
        secondobsPaint.setAntiAlias(false);

        dangerPaint.setColor(Color.RED);
        dangerPaint.setAntiAlias(false);

        scorePaint.setColor(Color.WHITE);
        scorePaint.setTextSize(70);
        scorePaint.setTypeface(Typeface.DEFAULT_BOLD);
        scorePaint.setAntiAlias(true);

        lives[0] = BitmapFactory.decodeResource(getResources(), R.drawable.aliveheart);
        lives[1] = BitmapFactory.decodeResource(getResources(), R.drawable.deadheart);

        geoffY = 550;
        score = 0;
        lifeCounter = 3;

    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        canvasWidth = canvas.getWidth();
        canvasHeight = canvas.getHeight();

        canvas.drawBitmap(background, 0, 0, null);

        int minGeoffY = geoff[0].getHeight();
        int maxGeoffY = canvasHeight - geoff[0].getHeight() * 3;
        geoffY = geoffY + geoffSpeed;
        if (geoffY < minGeoffY) {
            geoffY = minGeoffY;
        }
        if (geoffY > maxGeoffY) {
            geoffY = maxGeoffY;
        }
        geoffSpeed = geoffSpeed + 2;
        if (touch) {
            canvas.drawBitmap(geoff[1], geoffX, geoffY, null);
            touch = false;
        } else {
            canvas.drawBitmap(geoff[0], geoffX, geoffY, null);
        }


        obstacleX = obstacleX - obstacleSpeed;

        if (hitObstacleChecker(obstacleX, obstacleY)) {
            score += 10;
            obstacleX = -100;
        }

        if (obstacleX < 0) {
            obstacleX = canvasWidth + 21;
            obstacleY = (int) Math.floor(Math.random() * (maxGeoffY - minGeoffY)) + minGeoffY;
        }

        canvas.drawCircle(obstacleX, obstacleY, 40, obstaclePaint);

        secondobsX = secondobsX - secondobsSpeed;

        if (hitObstacleChecker(secondobsX, secondobsY)) {
            score += 20;
            secondobsX = -100;
        }

        if (secondobsX < 0) {
            secondobsX = canvasWidth + 21;
            secondobsY = (int) Math.floor(Math.random() * (maxGeoffY - minGeoffY)) + minGeoffY;
        }

        canvas.drawCircle(secondobsX, secondobsY, 25, secondobsPaint);

        dangerX = dangerX - dangerSpeed;

        if (hitObstacleChecker(dangerX, dangerY)) {
            dangerX = -100;
            lifeCounter--;
            if (lifeCounter <= 0) {

                Toast.makeText(getContext(), "Game Over", Toast.LENGTH_SHORT).show();

                Vibrator v = (Vibrator) c.getSystemService(Context.VIBRATOR_SERVICE);
                v.vibrate(400);

                Intent gameOver = new Intent(getContext(), GameOverActivity.class);
                gameOver.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                gameOver.putExtra("score", score);
                getContext().startActivity(gameOver);
            }
        }

        if (dangerX < 0) {
            dangerX = canvasWidth + 21;
            dangerY = (int) Math.floor(Math.random() * (maxGeoffY - minGeoffY)) + minGeoffY;
        }

        canvas.drawCircle(dangerX, dangerY, 30, dangerPaint);

        canvas.drawText("Score: " + score, 20, 60, scorePaint);

        for (int i = 0; i < 3; i++) {
            int x = (int) (800 + lives[0].getWidth() * 1.5 * i);
            int y = 10;

            if (i < lifeCounter) {
                canvas.drawBitmap(lives[0], x, y, null);
            } else {
                canvas.drawBitmap(lives[1], x, y, null);
            }
        }

    }


    public boolean hitObstacleChecker(int x, int y) {
        if (geoffX < x && x < (geoffX + geoff[0].getWidth()) && geoffY < y && y < (geoffY + geoff[0].getHeight())) {
            return true;
        }
        return false;
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if (event.getAction() == MotionEvent.ACTION_DOWN) {
            touch = true;
            geoffSpeed = -22;
        }
        return true;
    }
}
