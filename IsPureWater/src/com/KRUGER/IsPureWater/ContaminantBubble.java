package com.KRUGER.IsPureWater;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.RectF;
import android.util.Log;

/**
 * Created by harrison on 11/20/13. 192
 */
public class ContaminantBubble {
    private Contaminant contaminant; // Contaminant information
    private float radius = 60; // Radius
    private float x = 80; // Center (x,y)
    private float y = 80;
    private float speedX = 5f; // Speed (x,y)
    private float speedY = 5f;
    private RectF bounds;
    private Paint paint;

    // Constructor
    public ContaminantBubble(Contaminant c, int color) {
        contaminant = c;
        bounds = new RectF();
        paint = new Paint();
        paint.setColor(color);
    }

    public void setCenter(int numBubbles, int index, int width, int height) {
        int column = index%4;
        int column_tab = (width-160)/4; // Used 160 to leave 20 between edge of bubble and screen
        x += (column*column_tab);

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
    }

    public void moveWithCollisionDetection(BoundingBox box) {
        // Get new (x,y) position
        x += speedX;
        y += speedY;
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

    public void draw(Canvas canvas) {
        bounds.set(x-radius, y-radius, x+radius, y+radius);
        canvas.drawOval(bounds, paint);
    }
}

