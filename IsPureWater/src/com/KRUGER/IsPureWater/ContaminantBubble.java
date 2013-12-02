package com.KRUGER.IsPureWater;

import android.content.res.AssetManager;
import android.graphics.Bitmap;
import android.graphics.Canvas;
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
    private float radius = 60; // Radius
    private float x, y; // Center (x,y)
    private float speedX, speedY = 2f; // Speed (x,y)
    private RectF bounds = new RectF();
    private Bitmap bubbleBitMap;

    // Constructor
    public ContaminantBubble(Contaminant c, Bitmap b, int num, int width, int height, int angleInDegrees) {
        contaminant = c;
        x = 0;
        y = 0;
        bubbleBitMap = b;
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

        this.speedX = (float)(1 * Math.cos(Math.toRadians(angleInDegree)));
        this.speedY = (float)(1 * Math.sin(Math.toRadians(angleInDegree)));

        Log.d("X"+index, String.valueOf(x));
        Log.d("Y"+index, String.valueOf(y));

    }

    public void moveWithCollisionDetection(BoundingBox box) {
        // Get new (x,y) position
        if(speedX < 3)
            x += speedX;
        else
            speedX = 3;
        if(speedY < 3)
            y += speedY;
        else
            speedY = 3;
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
        if(radius*b.getRadius()*4+25 >= distance)
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
        /*
        float dx = x - b.getX();
        float dy = y - b.getY();
        float d = (float)Math.sqrt(dx*dx + dy*dy);
        float vp1, vp2, vx1, vx2, vy1, vy2;
        vx1 = speedX;
        vx2 = b.getSpeedX();
        vy1 = speedY;
        vy2 = b.getSpeedY();
        vp1 = vx1*dx/d + vy1*dy/d;
        vp2 = vx2*dx/d + vy2*dy/d;

        // Unit vector in direction of collision
        float ax = dx/d;
        float ay = dy/d;

        // Projection of velocities
        float va1 = vx1*ax +vy1*ay;
        float vb1 = -vx1*ay + vy1*ax;
        float va2 = vx2*ax + vy2*ay;
        float vb2 = -vx2 + vy2*ax;

        // New velocities
        float vaP1 = va1 + (va1-va2);
        float vaP2 = va2 + (va1-va2);

        // Undo projections
        vx1 = vaP1*ax - vb1*ay;
        vy1 = vaP1*ay + vb1*ax;
        vx2 = vaP2*ax - vb2*ay;
        vy2 = vaP2*ay + vb2*ax;

        speedX = vx1;
        speedY = vy1;
        b.setSpeedX(vx2);
        b.setSpeedY(vy2);
        */

        float angle = getMoveAngle();
        float otherAngle = b.getMoveAngle();

        speedX = b.getSpeed()*(float)Math.cos(Math.toRadians(otherAngle));
        speedY = b.getSpeed()*(float)Math.sin(Math.toRadians(otherAngle));
        b.setSpeedX((float)(getSpeed()*Math.cos(Math.toRadians(angle))));
        b.setSpeedY((float)(getSpeed()*Math.sin(Math.toRadians(angle))));



    }
    public void draw(Canvas canvas) {
        bounds.set(x-radius, y-radius, x+radius, y+radius);
        Paint paint = new Paint();
        paint.setAntiAlias(true);
        paint.setFilterBitmap(true);
        paint.setDither(true);
        canvas.drawBitmap(bubbleBitMap, null, bounds, paint);
    }
}

