package com.KRUGER.IsPureWater;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Adapter;
import android.widget.ListView;

import org.xmlpull.v1.XmlPullParserException;
import java.io.IOException;

import java.util.ArrayList;
import java.util.List;

import static android.R.layout.simple_dropdown_item_1line;


/**
 * Created with IntelliJ IDEA.
 * User: Josh
 * Date: 11/11/13
 * Time: 12:47 AM
 * To change this template use File | Settings | File Templates.
 */
public class ChooseWaterSystem extends Activity  {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.choosesystem);

        final String county = getIntent().getExtras().getString("County");
        TextView tv = (TextView) findViewById(R.id.textView);
        tv.setText("Select a water system within "+county);


        // list of water systems


        final ListView system_list = (ListView) this.findViewById(R.id.listView);
        ParseData forCounty = null;
        try {
            forCounty = new ParseData(getApplicationContext());
            ArrayList<String> systems = forCounty.get_systems(county);

            ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
                    simple_dropdown_item_1line, systems);

            system_list.setAdapter(adapter);



        } catch (XmlPullParserException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        // Temporary button to go to animatedviewactivity
        Button ContinueButton = (Button) this.findViewById(R.id.ContinueButton);
        ContinueButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                Intent go = new Intent(ChooseWaterSystem.this, AnimatedViewActivity.class);
                Bundle county_system_name = new Bundle();
                county_system_name.putString("County",county);
                county_system_name.putString("System", "test");
                go.putExtras(county_system_name);
                startActivity(go);

            }
        });

        system_list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Object o = system_list.getItemAtPosition(i);
                String system = o.toString();
                Log.w("IsPureWater", system);

                Bundle county_name = new Bundle();
                county_name.putString("County",county);
                county_name.putString("System", system);

                Intent go = new Intent(ChooseWaterSystem.this, AnimatedViewActivity.class);
                go.putExtras(county_name) ;
                startActivity(go);
            }

        });




    }
}
