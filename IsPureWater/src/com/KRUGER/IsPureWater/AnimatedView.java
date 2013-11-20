package com.KRUGER.IsPureWater;

import android.content.Context;
import android.graphics.Color;
import android.view.View;
import android.graphics.Canvas;
import android.util.AttributeSet;


/**
 * Created by harrison on 11/20/13.
 */
public class AnimatedView extends View {
    private BoundingBox box;
    private ContaminantBubble bubble;

    // For touch inputs
    private float previousX;
    private float previousY;

    public AnimatedView(Context context) {
        super(context);
        createBubble();
    }

    public AnimatedView(Context context, AttributeSet attrs) {
        super(context, attrs);
        createBubble();
    }

    public AnimatedView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        createBubble();
    }


    private void createBubble() {
        this.setFocusableInTouchMode(true);
        box = new BoundingBox(Color.TRANSPARENT);
        bubble = new ContaminantBubble(Color.RED);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        // Draw the components
        super.onDraw(canvas);
        box.draw(canvas);
        bubble.draw(canvas);

        // Update position of the bubble
        bubble.moveWithCollisionDetection(box);

        // Delay
        try {
            Thread.sleep(1);
        } catch (InterruptedException e) { }

        invalidate();  // Force a re-draw
    }
}

