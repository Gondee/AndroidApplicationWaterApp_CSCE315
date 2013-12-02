package com.KRUGER.IsPureWater;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.*;

import java.util.ArrayList;

import static android.R.layout.simple_dropdown_item_1line;

public class MyActivity extends Activity {
    /**
     * Called when the activity is first created.
     */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);




        final AutoCompleteTextView search = (AutoCompleteTextView) this.findViewById(R.id.autoCompleteCounty);
        LoadData newLoad = new LoadData();
        final ArrayList<String> County_List = newLoad.get_county_list(getApplicationContext());



        ArrayAdapter<String> adapter;
        adapter = new ArrayAdapter<String>(this,
                simple_dropdown_item_1line, County_List);
        search.setAdapter(adapter);

        //Set county map reference

        final WebView map = (WebView) this.findViewById(R.id.webViewMap);
        //map.getSettings().setLoadWithOverviewMode(true);
        map.getSettings().setUseWideViewPort(true);
        map.getSettings().setBuiltInZoomControls(true);
        map.setScrollbarFadingEnabled(true);
        map.getSettings().setLayoutAlgorithm(WebSettings.LayoutAlgorithm.SINGLE_COLUMN);
        map.setBackgroundColor(Color.parseColor("#C0C0C0"));
        map.loadUrl("file:///android_asset/ClickableCountyMap.html");

        map.setWebViewClient(new WebViewClient() {
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {

                LoadData CP = new LoadData();
                String Clicked_County = CP.StripURLForCounty(url);
                boolean match = false;

                for(int i =0; i< County_List.size();i++){

                    if(Clicked_County.equals(County_List.get(i))){
                        match = true;
                    }

                }

                if (match) {
                    Toast toast = Toast.makeText(getApplicationContext(), "County: "+ Clicked_County, Toast.LENGTH_SHORT);
                    toast.show();
                    //Intent to switch windows
                    Bundle county_name = new Bundle();
                    county_name.putString("County",Clicked_County);
                    Intent go = new Intent(MyActivity.this,ChooseWaterSystem.class);
                    go.putExtras(county_name);
                    startActivity(go);





                    return true;
                } else {

                    Toast toast = Toast.makeText(getApplicationContext(), "Un-Indexed County", Toast.LENGTH_SHORT);
                    toast.show();
                    return true;
                }
            }
        });

        ListView CountyListing = (ListView) this.findViewById(R.id.listView);
        CountyListing.setAdapter(adapter);


        search.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {

                  search.clearComposingText();
                  search.setText("");
                return false;
            }
        });//On click listener

        CountyListing.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {

                // selected item
                LoadData temp = new LoadData();
                String selected = temp.get_county_list(getApplicationContext()).get(position);

                search.setText(selected);

                //Toast toast = Toast.makeText(getApplicationContext(), selected, Toast.LENGTH_SHORT);
                //toast.show();

            }
        });

        Button Continue = (Button)this.findViewById(R.id.MoveOn);

        Continue.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

            EditText et = (EditText) MyActivity.this.findViewById(R.id.autoCompleteCounty);
            String finalCounty = et.getText().toString();
            LoadData l = new LoadData();
            ArrayList<String> CountyCheck = l.get_county_list(getApplicationContext());
            boolean valid = false;
            for(int i =0; i<CountyCheck.size();i++){

                   if((finalCounty.toLowerCase()).equals(CountyCheck.get(i).toLowerCase())) {
                       valid = true;
                       break;
                   }


                }
                if(valid){
                    Bundle county_name = new Bundle();
                    county_name.putString("County",finalCounty);
                    Toast toast = Toast.makeText(getApplicationContext(), finalCounty, Toast.LENGTH_SHORT);
                    toast.show();

                    Intent go = new Intent(MyActivity.this,ChooseWaterSystem.class);
                    go.putExtras(county_name);
                    startActivity(go);
                }
                else{
                String Invalid_County = finalCounty + " : Is not a valid County in Texas!";
                Toast toast = Toast.makeText(getApplicationContext(), Invalid_County, Toast.LENGTH_SHORT);
                toast.show();
            }


            }
        });//On click listener




    }
}
