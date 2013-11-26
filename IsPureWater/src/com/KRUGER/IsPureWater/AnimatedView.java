package com.KRUGER.IsPureWater;

import android.content.Context;
import android.graphics.Color;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Display;
import android.view.View;
import android.graphics.Canvas;
import android.util.AttributeSet;

import org.xmlpull.v1.XmlPullParserException;

import java.io.IOException;
import java.util.ArrayList;


/**
 * Created by harrison on 11/20/13.
 */
public class AnimatedView extends View {
    private BoundingBox box;
    private ArrayList<ContaminantBubble> bubbles = new ArrayList<ContaminantBubble>();

    // Height and width of layout
    private int width;
    private int height;

    // For touch inputs
    private float previousX;
    private float previousY;

    public AnimatedView(Context context) {
        super(context);
        createBox();
    }

    public AnimatedView(Context context, AttributeSet attrs) {
        super(context, attrs);
        createBox();
    }

    public AnimatedView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        createBox();
    }

    private void createBox() {
        box = new BoundingBox(Color.TRANSPARENT);
    }

    public void setContaminantBubbles(ArrayList<Contaminant> cl) {
        bubbles.clear();
        int color;
        int i = 0;
        for(Contaminant c : cl) {
            if(c.isOverLegalLimit)
                color = Color.RED;
            else if(c.isOverHealthLimit)
                color = Color.YELLOW;
            else
                color = Color.GREEN;
            bubbles.add(new ContaminantBubble(c, color));
            i++;
        }
        invalidate();
    }

    @Override
    protected void onSizeChanged(int xNew, int yNew, int xOld, int yOld){
        width = xNew;
        height = yNew;
        Log.d("WIDTHHEIGHT", width+"."+height);
        int i = 0;
        for(ContaminantBubble c : bubbles) {
            bubbles.get(i).setCenter(bubbles.size(), i, width, height);
            i++;
        }
        box.set(0, 0, width, height);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        // Draw the components
        super.onDraw(canvas);
        box.draw(canvas);

        if(!bubbles.isEmpty()) {
            for(ContaminantBubble b : bubbles) {
                b.draw(canvas);

                // Update position of the bubble
                b.moveWithCollisionDetection(box);
            }
        }

        // Delay
        try {
            Thread.sleep(1);
        } catch (InterruptedException e) { }

        invalidate();  // Force a re-draw
    }
}

