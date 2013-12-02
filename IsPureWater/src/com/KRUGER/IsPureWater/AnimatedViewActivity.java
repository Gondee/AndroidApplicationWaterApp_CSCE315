package com.KRUGER.IsPureWater;

import android.app.Activity;
import android.content.Intent;
import android.hardware.Sensor;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.*;

import org.xmlpull.v1.XmlPullParserException;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedList;

import static android.R.layout.simple_dropdown_item_1line;

/**
 * Created with IntelliJ IDEA.
 * User: joshkruger
 * Date: 11/11/13
 * Time: 7:51 PM
 * To change this template use File | Settings | File Templates.
 * HARRISON KURTZ
 */

public class AnimatedViewActivity extends Activity {
    private SensorManager manager;
    private Sensor accel;
    private ArrayList<Contaminant> contaminants;
    private LinkedList<Contaminant> contaminants_shown;
    private AnimatedView bubbles;
    RelativeLayout container;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.animated_view);

        container = (RelativeLayout) findViewById(R.id.animatedLayout);

        // Create AnimatedView
        bubbles = new AnimatedView(this);
        bubbles.setLayoutParams(new RelativeLayout.LayoutParams(
                RelativeLayout.LayoutParams.MATCH_PARENT,
                RelativeLayout.LayoutParams.MATCH_PARENT));

        final String county = getIntent().getExtras().getString("County");
        final String system = getIntent().getExtras().getString("System");

        ParseData forBubbles = null;
        contaminants = new ArrayList<Contaminant>();
        try {
            forBubbles = new ParseData();
            contaminants = forBubbles.get_contaminants(county, system, getApplicationContext());
        } catch (XmlPullParserException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        bubbles.setContaminantBubbles(contaminants);
        contaminants_shown = new LinkedList<Contaminant>(contaminants);
        container.addView(bubbles, 0);

        final Button OverLegalButton= (Button) this.findViewById(R.id.buttonOverLegal);
        final Button OverSafteyButton = (Button) this.findViewById(R.id.buttonOverSaftey);
        final Button HarmlessButton = (Button) this.findViewById(R.id.buttonHarmless);
        final ToggleButton OverLegalToggle = (ToggleButton) this.findViewById(R.id.toggleOverLegal);
        final ToggleButton OverHealthToggle = (ToggleButton) this.findViewById(R.id.toggleOverHealth);
        final ToggleButton UnharmfulToggle = (ToggleButton) this.findViewById(R.id.toggleUnharmful);

        OverLegalButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ArrayList<Contaminant> overLegalContaminants = new ArrayList<Contaminant>();
                for(Contaminant c : contaminants)
                    if(c.isOverLegalLimit)
                        overLegalContaminants.add(c);

                Intent go = new Intent(AnimatedViewActivity.this, ContaminentInfo.class);
                Bundle info = new Bundle();
                info.putSerializable("contaminants", overLegalContaminants);
                go.putExtras(info);

                if(overLegalContaminants.size()==0){
                    Toast toast = Toast.makeText(getApplicationContext(), "No Contaminants to show", Toast.LENGTH_SHORT);
                    toast.show();
                }
                else{
                    startActivity(go);
                }
            }
        });

        OverSafteyButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ArrayList<Contaminant> overHealthContaminants = new ArrayList<Contaminant>();
                for(Contaminant c : contaminants)
                    if(c.isOverHealthLimit)
                        overHealthContaminants.add(c);

                Intent go = new Intent(AnimatedViewActivity.this, ContaminentInfo.class);
                Bundle info = new Bundle();
                info.putSerializable("contaminants", overHealthContaminants);
                go.putExtras(info);

                if(overHealthContaminants.size()==0){
                    Toast toast = Toast.makeText(getApplicationContext(), "No Contaminants to show", Toast.LENGTH_SHORT);
                    toast.show();
                }
                 else{
                    startActivity(go);
                 }

            }
        });

        HarmlessButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ArrayList<Contaminant> unharmfulContaminants = new ArrayList<Contaminant>();
                for(Contaminant c : contaminants)
                    if(!c.isOverLegalLimit && !c.isOverHealthLimit)
                        unharmfulContaminants.add(c);

                Intent go = new Intent(AnimatedViewActivity.this, ContaminentInfo.class);
                Bundle info = new Bundle();
                info.putSerializable("contaminants", unharmfulContaminants);
                go.putExtras(info);

                if(unharmfulContaminants.size()==0){
                    Toast toast = Toast.makeText(getApplicationContext(), "No Contaminants to show", Toast.LENGTH_SHORT);
                    toast.show();
                }
                else{
                    startActivity(go);
                }
            }
        });

        OverLegalToggle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (OverLegalToggle.isChecked()) {
                    for (Contaminant c : contaminants)
                        if (c.isOverLegalLimit)
                            contaminants_shown.add(c);

                    container.removeView(bubbles);
                    bubbles.setContaminantBubbles(contaminants_shown);
                    container.addView(bubbles, 0);
                } else {
                    Iterator<Contaminant> iter = contaminants_shown.iterator();
                    while(iter.hasNext()) {
                        Contaminant temp = iter.next();
                        if(temp.isOverLegalLimit)
                            iter.remove();
                    }

                    container.removeView(bubbles);
                    bubbles.setContaminantBubbles(contaminants_shown);
                    container.addView(bubbles, 0);
                }
            }
        });

        OverHealthToggle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(OverHealthToggle.isChecked()) {
                    for(Contaminant c : contaminants)
                        if(c.isOverHealthLimit)
                            contaminants_shown.add(c);

                    container.removeView(bubbles);
                    bubbles.setContaminantBubbles(contaminants_shown);
                    container.addView(bubbles, 0);
                }
                else {
                    Iterator<Contaminant> iter = contaminants_shown.iterator();
                    while(iter.hasNext()) {
                        Contaminant temp = iter.next();
                        if(temp.isOverHealthLimit)
                            iter.remove();
                    }

                    container.removeView(bubbles);
                    bubbles.setContaminantBubbles(contaminants_shown);
                    container.addView(bubbles, 0);
                }
            }
        });

        UnharmfulToggle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(UnharmfulToggle.isChecked()) {
                    for(Contaminant c : contaminants)
                        if(!c.isOverLegalLimit && !c.isOverHealthLimit)
                            contaminants_shown.add(c);

                    container.removeView(bubbles);
                    bubbles.setContaminantBubbles(contaminants_shown);
                    container.addView(bubbles, 0);
                }
                else {
                    Iterator<Contaminant> iter = contaminants_shown.iterator();
                    while(iter.hasNext()) {
                        Contaminant temp = iter.next();
                        if(!temp.isOverHealthLimit && !temp.isOverLegalLimit)
                            iter.remove();
                    }

                    container.removeView(bubbles);
                    bubbles.setContaminantBubbles(contaminants_shown);
                    container.addView(bubbles, 0);
                }
            }
        });

    }

}
