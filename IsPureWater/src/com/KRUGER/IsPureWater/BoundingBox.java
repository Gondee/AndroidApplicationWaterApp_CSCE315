package com.KRUGER.IsPureWater;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.RectF;

/**
 * Created by harrison on 11/20/13.
 */
public class BoundingBox {
    float xMin, xMax, yMin, yMax;
    private Paint paint;  // paint style and color
    private RectF bounds;

    public BoundingBox(int color) {
        paint = new Paint();
        paint.setColor(color);
        bounds = new RectF();
    }

    public RectF getBounds() {
        return bounds;
    }

    public void set(float x, int y,float width, float height) {
        xMin = x;
        xMax = x + width - 1;
        yMin = y;
        yMax = y + height - 1;
        // The box's bounds do not change unless the view's size changes
        bounds.set(xMin, yMin, xMax, yMax);
    }

    public void draw(Canvas canvas) {
        canvas.drawRect(bounds, paint);
    }
}
