package com.KRUGER.IsPureWater;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

/**
 * Created with IntelliJ IDEA.
 * User: joshkruger
 * Date: 11/11/13
 * Time: 7:51 PM
 * To change this template use File | Settings | File Templates.
 */
public class AnimatedViewActivity extends Activity {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.animated_view);

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
