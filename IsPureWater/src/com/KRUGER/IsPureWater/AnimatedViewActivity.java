package com.KRUGER.IsPureWater;

import android.app.Activity;
import android.content.Intent;
import android.hardware.Sensor;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;

import org.xmlpull.v1.XmlPullParserException;

import java.io.IOException;
import java.util.ArrayList;

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

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.animated_view);

        AnimatedView bubbles = (AnimatedView) this.findViewById(R.id.bubbles);

        final String county = getIntent().getExtras().getString("County");
        final String system = getIntent().getExtras().getString("System");

        bubbles.set_County(county);
        bubbles.set_System(system);

        new AnimatedView(getApplicationContext());

        ParseData forBubbles = null;
        try {
            forBubbles = new ParseData();
            ArrayList<String> bubble_info = forBubbles.get_bubbles(county, system, getApplicationContext());
        } catch (XmlPullParserException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }


        final Bundle Send_Contaminents = new Bundle();

        Button OverLegalButton= (Button) this.findViewById(R.id.buttonOverLegal);
        Button OverSafteyButton = (Button) this.findViewById(R.id.buttonOverSaftey);
        Button HarmlessButton = (Button) this.findViewById(R.id.buttonHarmless);

        OverLegalButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                Send_Contaminents.putString("ContaminentIndex","example");

                Intent go = new Intent(AnimatedViewActivity.this, ContaminentInfo.class);
                go.putExtras(Send_Contaminents) ;
                startActivity(go);

            }
        });

        OverSafteyButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                Send_Contaminents.putString("ContaminentIndex","example");

                Intent go = new Intent(AnimatedViewActivity.this, ContaminentInfo.class);
                go.putExtras(Send_Contaminents) ;
                startActivity(go);

            }
        });

        HarmlessButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                Send_Contaminents.putString("ContaminentIndex","example");

                Intent go = new Intent(AnimatedViewActivity.this, ContaminentInfo.class);
                go.putExtras(Send_Contaminents) ;
                startActivity(go);

            }
        });


    }

}
