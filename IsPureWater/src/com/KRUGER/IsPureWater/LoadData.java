package com.KRUGER.IsPureWater;
import android.content.Context;
import android.content.res.AssetManager;
import android.widget.Toast;

import java.io.*;
import java.util.ArrayList;


/**
 * Created with IntelliJ IDEA.
 * User: Josh
 * Date: 11/10/13
 * Time: 10:18 PM
 * To change this template use File | Settings | File Templates.
 */
public class LoadData {


    public ArrayList<String> get_county_list(Context t){

         //Temporary List of counties before XML is imported
        ArrayList<String> county = new ArrayList();



            // Open the file that is the first
            // command line parameter
        try {
            AssetManager am = t.getAssets();
            InputStream is = am.open("countylist.txt");
            String str;
            BufferedReader reader = new BufferedReader(new InputStreamReader(is));
            if (is!=null) {

                    while ((str = reader.readLine()) != null) {
                        county.add(str);
                        //System.out.println(str);
                    }

            }
            is.close();

        } catch (IOException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        }

            return county;
    }

    public String StripURLForCounty(String original){

            int start = 21;  //22 characters
            String CountyName= "";

        if(original.length() <= 21)
            return "NOT A COUNTY";

          for(int i =0;i<original.length();i++){

            if(i >= start){

                if(!(original.charAt(i) == '/')){

                    if(original.charAt(i) == '_'){
                        CountyName += " ";
                    }
                    else
                        CountyName += original.charAt(i);
                }

            }

          }

        return CountyName;

    }



}
