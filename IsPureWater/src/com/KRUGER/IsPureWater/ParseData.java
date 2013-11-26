package com.KRUGER.IsPureWater;

import android.content.Context;
import android.content.res.AssetManager;
import android.util.Log;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlPullParserFactory;
import android.app.Activity;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.lang.String;import java.util.List;
import java.util.ArrayList;

/**
 * Created by Joshua on 11/22/13.
 */
public class ParseData {

    private XmlPullParserFactory factory;
    XmlPullParser xpp;
    ArrayList<String> returnList = new ArrayList<String>();
    ArrayList<Contaminant> contaminantList = new ArrayList<Contaminant>();

    
    public ParseData() throws XmlPullParserException {
        factory = XmlPullParserFactory.newInstance();
        factory.setNamespaceAware(true);
        xpp = factory.newPullParser();
    }

    public ArrayList<String> get_systems(String county, Context t) throws XmlPullParserException, IOException
    {
        AssetManager am = t.getAssets();
        InputStream is = am.open("NYTimes_TexasWater.xml");
        BufferedReader reader = new BufferedReader(new InputStreamReader(is));
        xpp.setInput(reader);

        int eventType = xpp.getEventType();

        while (eventType != XmlPullParser.END_DOCUMENT) {
            if(eventType == XmlPullParser.START_TAG) {
                if (xpp.getName().equals("county_name"))
                {

                    xpp.next();

                    if (xpp.getText().equals(county))
                    {
                        eventType = xpp.next();
                        while (eventType != XmlPullParser.END_TAG || !xpp.getName().equals("county"))
                        {
                            if (eventType == XmlPullParser.START_TAG && xpp.getName().equals("system_name"))
                            {
                                eventType = xpp.next();
                                returnList.add(xpp.getText());
                            }
                            else eventType = xpp.next();
                        }
                        is.close();
                        return returnList;
                    }
                    eventType = xpp.next();
                }
                else eventType = xpp.next();
            }

            else eventType = xpp.next();
        }
        is.close();
        return returnList;
    }

    public ArrayList<Contaminant> get_contaminants(String county, String w_system, Context t) throws XmlPullParserException, IOException {


        AssetManager am = t.getAssets();
        InputStream is = am.open("NYTimes_TexasWater.xml");
        BufferedReader reader = new BufferedReader(new InputStreamReader(is));
        xpp.setInput(reader);

        int eventType = xpp.getEventType();

        while (eventType != XmlPullParser.END_DOCUMENT) {
            if(eventType == XmlPullParser.START_TAG) {
                if (xpp.getName().equals("county_name"))
                {
                    xpp.next();
                    if (xpp.getText().equals(county))
                    {
                        eventType = xpp.next();
                        while (eventType != XmlPullParser.END_TAG || !xpp.getName().equals("county"))
                        {
                            if (eventType == XmlPullParser.START_TAG && xpp.getName().equals("system_name"))
                            {
                                xpp.next();
                                if (xpp.getText().equals(w_system))
                                {
                                    xpp.next();xpp.next();
                                    eventType = xpp.next();
                                    while (eventType != XmlPullParser.END_TAG || !xpp.getName().equals("water_system"))
                                    {
                                        String line = new String();
                                        ArrayList<String> tempContaminant = new ArrayList<String>();

                                        while (eventType != XmlPullParser.END_TAG || !xpp.getName().equals("contaminant"))
                                        {
                                            if (eventType == XmlPullParser.TEXT ){
                                                if (xpp.getText().indexOf("\t") == -1 && xpp.getText().indexOf("\n") == -1) {
                                                    line = line + xpp.getText() + "&";
                                                    tempContaminant.add(xpp.getText());
                                                }
                                            }

                                            eventType = xpp.next();
                                        }

                                        Log.d("IsPureWater", "line: " + line);
                                        contaminantList.add(new Contaminant(tempContaminant.get(0), tempContaminant.get(1), tempContaminant.get(2), tempContaminant.get(3), tempContaminant.get(4)));
                                        Log.d("IsPureWater", xpp.getName());
                                        eventType = xpp.next();
                                        while (eventType != XmlPullParser.START_TAG && eventType != XmlPullParser.END_TAG)
                                            eventType = xpp.next();
                                        Log.d("IsPureWater", xpp.getName());

                                    }
                                    is.close();
                                    return contaminantList;
                                }
                                else eventType = xpp.next();
                            }
                            else eventType = xpp.next();
                        }

                    }
                }
                eventType = xpp.next();
            }
            else eventType = xpp.next();
        }
        is.close();
        return contaminantList;
    }


}
