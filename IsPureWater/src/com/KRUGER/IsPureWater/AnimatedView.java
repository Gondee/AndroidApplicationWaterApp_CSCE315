package com.KRUGER.IsPureWater;

import android.content.Context;
import android.content.res.AssetManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.RectF;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Display;
import android.view.MotionEvent;
import android.view.View;
import android.graphics.Canvas;
import android.util.AttributeSet;

import org.xmlpull.v1.XmlPullParserException;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Random;


/**
 * Created by harrison on 11/20/13.
 */
public class AnimatedView extends View {
    Context appContext;
    private BoundingBox box;
    private ArrayList<ContaminantBubble> bubbles = new ArrayList<ContaminantBubble>();
    private ArrayList<RectF> bounds = new ArrayList<RectF>();

    // Height and width of layout
    private int width;
    private int height;

    // For touch inputs
    private float previousX;
    private float previousY;

    public AnimatedView(Context context) {
        super(context);
        appContext = context;
        createBox();
        DisplayMetrics metrics = context.getResources().getDisplayMetrics();
        width = metrics.widthPixels;
        height = metrics.heightPixels;
    }

    public AnimatedView(Context context, AttributeSet attrs) {
        super(context, attrs);
        appContext = context;
        createBox();
        DisplayMetrics metrics = context.getResources().getDisplayMetrics();
        width = metrics.widthPixels;
        height = metrics.heightPixels;
    }

    public AnimatedView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        appContext = context;
        createBox();
        DisplayMetrics metrics = context.getResources().getDisplayMetrics();
        width = metrics.widthPixels;
        height = metrics.heightPixels;
    }

    private void createBox() {
        box = new BoundingBox(Color.TRANSPARENT);
    }

    public Bitmap getBubbleBitmap(String color) {
        AssetManager manager = appContext.getAssets();
        InputStream open = null;
        try {
            open = manager.open(color+"Bubble.png");
            Bitmap originalBitmap = BitmapFactory.decodeStream(open);
            return originalBitmap;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public void setContaminantBubbles(Collection<Contaminant> cl) {
        bubbles.clear();
        String color;
        int i = 0;
        Random rand = new Random();
        for(Contaminant c : cl) {
            if(c.isOverLegalLimit)
                color = "red";
            else if(c.isOverHealthLimit)
                color = "yellow";
            else
                color = "green";
            Bitmap b = getBubbleBitmap(color);
            int angleInDegree = rand.nextInt(360);
            bubbles.add(new ContaminantBubble(c, b, i, 768, 1038, angleInDegree));
            i++;
        }
        invalidate();
    }

    @Override
    protected void onSizeChanged(int xNew, int yNew, int xOld, int yOld){
        width = xNew;
        height = yNew;
        Random rand = new Random();
        Log.d("WIDTH HEIGHT", width+" "+height);
        box.set(0, 0, width, height);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        // Draw the components
        super.onDraw(canvas);
        box.draw(canvas);

        if(!bubbles.isEmpty()) {
            for(int i = 0; i < bubbles.size(); i++) {
                int temp = i;
                for(int j = i+1; j < bubbles.size(); j++) {
                    if(bubbles.get(i).bubbleCollisionDetection(bubbles.get(j))) {
                        bubbles.get(i).bubbleCollisionHandler(bubbles.get(j));
                    }
                }
                // Update position of the bubble
            }
            for(ContaminantBubble b: bubbles) {
                b.moveWithCollisionDetection(box);
                b.draw(canvas);
            }
        }

        // Delay
        try {
            Thread.sleep(30);
        } catch (InterruptedException e) { }

        invalidate();  // Force a re-draw
    }

    // Touch-input handler
    @Override
    public boolean onTouchEvent(MotionEvent event) {
        float currentX = event.getX();
        float currentY = event.getY();
        float deltaX, deltaY;
        for(ContaminantBubble b : bubbles) {
            if(b.bubbleCollisionDetection(currentX, currentY)) {
                switch (event.getAction()) {
                    case MotionEvent.ACTION_MOVE:
                        setSoundEffectsEnabled(true);
                        playSoundEffect(0);
                        // Modify rotational angles according to movement
                        deltaX = currentX - b.getX();
                        deltaY = currentY - b.getY();
                        float speed = (float)Math.sqrt(deltaX * deltaX + deltaY * deltaY);
                        float angle = (float)Math.toDegrees(Math.atan2(deltaY, deltaX));
                        b.setSpeedX(speed*(float)Math.cos(Math.toRadians(angle)));
                        b.setSpeedY(speed*(float)Math.sin(Math.toRadians(angle)));
                }
            }
        }
        return true;
    }
}

