package com.KRUGER.IsPureWater;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

/**
 * Created with IntelliJ IDEA.
 * User: Josh
 * Date: 11/11/13
 * Time: 12:47 AM
 * To change this template use File | Settings | File Templates.
 */
public class ChooseWaterSystem extends Activity {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.choosesystem);

        String county = getIntent().getExtras().getString("County");
        TextView tv = (TextView) findViewById(R.id.textView);
        tv.setText("Select a water system within "+county);

        Button Cont = (Button) this.findViewById(R.id.ContinueButton);

        Cont.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Bundle county_name = new Bundle();
                county_name.putString("County","Brazos");

                Intent go = new Intent(ChooseWaterSystem.this, AnimatedViewActivity.class);
                go.putExtras(county_name) ;
                startActivity(go);

                }
            });




    }
}
