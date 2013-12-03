package com.KRUGER.IsPureWater;

import android.content.res.AssetManager;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.graphics.Matrix;
import android.graphics.drawable.Drawable;
import android.util.Log;

import java.util.ArrayList;

/**
 * Created by harrison on 11/20/13. 192
 */
public class ContaminantBubble {
    private Contaminant contaminant; // Contaminant information
    private float radius; // Radius
    private float x, y; // Center (x,y)
    private float speedX, speedY; // Speed (x,y)
    private RectF bounds = new RectF();
    private Bitmap bubbleBitMap;
    private Paint textPaint;

    // Constructor
    public ContaminantBubble(Contaminant c, Bitmap b, int num, int width, int height, int angleInDegrees) {
        contaminant = c;
        x = 0;
        y = 0;
        // set radius by finding text width
        textPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        // text color - #3D3D3D
        textPaint.setColor(Color.rgb(61, 61, 61));
        // text size in pixels
        textPaint.setTextSize((int) (12));
        // text shadow
        textPaint.setShadowLayer(1f, 0f, 1f, Color.WHITE);
        // center alignment
        textPaint.setTextAlign(Paint.Align.CENTER);
        //radius
        float textSize = textPaint.measureText(contaminant.getName())/2;
        if(textSize < 60)
            radius = 60;
        else
            radius = textSize;
        // scale bitMap
        bubbleBitMap = Bitmap.createScaledBitmap(b,Math.round(2*radius), Math.round(2*radius), false);
        setBubble(num, width, height, angleInDegrees);
    }

    public float getX() {
        return x;
    }

    public float getY() {
        return y;
    }

    public float getRadius() {
        return radius;
    }

    public float getSpeedX() {
        return speedX;
    }

    public float getSpeedY() {
        return speedY;
    }

    public void setSpeedX(float vx) {
        speedX = vx;
    }

    public void setSpeedY(float vy) {
        speedY = vy;
    }

    public void setBubble(int index, int width, int height, int angleInDegree) {
        int column = index%4;
        int column_tab = (width-160)/4; // Used 160 to leave 20 gap between the bubbles bounds and bounding box
        x += column_tab+(column*column_tab);

        int row_tab = (height-160)/4;

        switch(index) {
            case 0: case 1:
            case 2: case 3:
                y = y;
                break;
            case 4: case 5:
            case 6: case 7:
                y += (row_tab);
                break;
            case 8: case 9:
            case 10: case 11:
                y += (row_tab*2);
                break;
            case 12: case 13:
            case 14: case 15:
                y += (row_tab*3);
                break;
            case 16: case 17:
            case 18: case 19:
                y += (row_tab*4);
                break;
            default:
                y = 0;
        }

        this.speedX = (float)(2 * Math.cos(Math.toRadians(angleInDegree)));
        this.speedY = (float)(2 * Math.sin(Math.toRadians(angleInDegree)));

        Log.d("X" + index, String.valueOf(x));
        Log.d("Y"+index, String.valueOf(y));

    }

    public void moveWithCollisionDetection(BoundingBox box) {
        // Get new (x,y) position
        if(speedX <= 4)
            x += speedX;
        else {
            x += 4;
            speedX = 4;
        }
        if(speedY <= 4)
            y += speedY;
        else {
            y += 4;
            speedY = 4;
        }
        // Detect collision and react
        if (x + radius > box.xMax) {
            speedX = -speedX;
            x = box.xMax-radius;
        } else if (x - radius < box.xMin) {
            speedX = -speedX;
            x = box.xMin+radius;
        }
        if (y + radius > box.yMax) {
            speedY = -speedY;
            y = box.yMax - radius;
        } else if (y - radius < box.yMin) {
            speedY = -speedY;
            y = box.yMin + radius;
        }
    }

    // collision with bubbles
    public boolean bubbleCollisionDetection(ContaminantBubble b) {
        float tempX = b.getX()+b.getSpeedX();
        float tempY = b.getY()+b.getSpeedY();
        float tempRadius = b.getRadius();
        float distance = ((x+speedX - tempX) * (x+speedX - tempX) + (y+speedY - tempY) * (y+speedY - tempY));
        if((radius*2)*(b.getRadius()*2)+25 >= distance)
            return true;
        else
            return false;
    }

    public boolean bubbleCollisionDetection(float touchX, float touchY) {
        float distance = ((x - touchX) * (x - touchX) + (y - touchY) * (y - touchY));
        if((radius*radius) >= distance)
            return true;
        else
            return false;
    }

    // Magnitude of speed
    public float getSpeed() {
        return (float)Math.sqrt(speedX * speedX + speedY * speedY);
    }

    // Angle
    public float getMoveAngle() {
        return (float)Math.toDegrees(Math.atan2(speedY, speedX));
    }

    public void bubbleCollisionHandler(ContaminantBubble b) {
        float angle = getMoveAngle();
        float otherAngle = b.getMoveAngle();

        speedX = b.getSpeed()*(float)Math.cos(Math.toRadians(otherAngle));
        speedY = b.getSpeed()*(float)Math.sin(Math.toRadians(otherAngle));
        b.setSpeedX((float) (getSpeed() * Math.cos(Math.toRadians(angle))));
        b.setSpeedY((float)(getSpeed()*Math.sin(Math.toRadians(angle))));



    }
    public void draw(Canvas canvas) {
        bounds.set(x-radius, y-radius, x+radius, y+radius);
        // set paint for bitmap
        Paint paint = new Paint();
        paint.setAntiAlias(true);
        paint.setFilterBitmap(true);
        paint.setDither(true);
        canvas.drawBitmap(bubbleBitMap, null, bounds, paint);
        canvas.drawText(contaminant.getName(), x, y, textPaint);
    }
}

