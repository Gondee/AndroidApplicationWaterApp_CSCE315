package com.KRUGER.IsPureWater;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.RectF;

/**
 * Created by harrison on 11/20/13.
 */
public class ContaminantBubble {
    float radius = 80; // Radius
    float x = radius + 20; // Center (x,y)
    float y = radius + 20;
    float speedX = 0f; // Speed (x,y)
    float speedY = 0f;
    private RectF bounds;
    private Paint paint;

    // Constructor
    public ContaminantBubble(int color) {
        bounds = new RectF();
        paint = new Paint();
        paint.setColor(color);
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

