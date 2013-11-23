package com.KRUGER.IsPureWater;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlPullParserFactory;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.lang.String;import java.util.List;
import java.util.ArrayList;

/**
 * Created by Joshua on 11/22/13.
 */
public class ParseData {

    private XmlPullParserFactory factory;
    XmlPullParser xpp;

    public ParseData() throws XmlPullParserException {
        factory = XmlPullParserFactory.newInstance();
        factory.setNamespaceAware(true);
        xpp = factory.newPullParser();
    }

    public List<String> get_systems(String county) throws XmlPullParserException, IOException
    {
        xpp.setInput(new BufferedReader(new FileReader("NYTimes_TexasWater.txt")));

        int eventType = xpp.getEventType();
        List<String> systems = new ArrayList<String>();

        while (eventType != XmlPullParser.END_DOCUMENT) {
            if(eventType == XmlPullParser.START_TAG) {
                if (xpp.getName()=="county_name")
                {
                    xpp.next();
                    if (xpp.getText()== county)
                    {
                        eventType = xpp.next();
                        while (eventType != XmlPullParser.END_TAG && xpp.getName() != "county")
                        {
                            if (eventType == XmlPullParser.START_TAG && xpp.getName() == "system_name")
                            {
                                eventType = xpp.next();
                                systems.add(xpp.getText());
                            }
                            else eventType = xpp.next();
                        }
                        return systems;
                    }
                }
                else xpp.nextTag();
            }
            else xpp.nextTag();
        }
        return systems;
    }

    public List<String> get_bubbles(String county, String w_system) throws XmlPullParserException, IOException {


        xpp.setInput(new BufferedReader(new FileReader("NYTimes_TexasWater.txt")));

        int eventType = xpp.getEventType();
        List<String> bubbles = new ArrayList<String>();

        while (eventType != XmlPullParser.END_DOCUMENT) {
            if(eventType == XmlPullParser.START_TAG) {
                if (xpp.getName()=="county_name")
                {
                    xpp.next();
                    if (xpp.getText()== county)
                    {
                        eventType = xpp.next();
                        while (eventType != XmlPullParser.END_TAG && xpp.getName() != "county")
                        {
                            if (eventType == XmlPullParser.START_TAG && xpp.getName() == "system_name")
                            {
                                xpp.next();
                                if (xpp.getText() == w_system)
                                {
                                    xpp.next();
                                    eventType = xpp.next();
                                    int i = 0;
                                    while (eventType != XmlPullParser.END_TAG && xpp.getName() != "water_system")
                                    {
                                        String line;
                                        xpp.next();
                                        xpp.next();
                                        line = "<contaminant>" + xpp.getText();
                                        xpp.next();xpp.next();xpp.next();
                                        line = line + "<average>"+xpp.getText();
                                        xpp.next();xpp.next();xpp.next();
                                        line = line + "<max>"+xpp.getText();
                                        xpp.next();xpp.next();xpp.next();
                                        line = line + "<health_limit>"+xpp.getText();
                                        xpp.next();xpp.next();xpp.next();
                                        line = line + "<legal_limit>"+xpp.getText();
                                        xpp.next();xpp.next();
                                        eventType = xpp.next();

                                        bubbles.add(line);

                                    }
                                    return bubbles;
                                }
                                else eventType = xpp.nextTag();
                            }
                            else eventType = xpp.nextTag();
                        }

                    }
                }
                eventType = xpp.nextTag();
            }
            else eventType = xpp.nextTag();
        }
        return bubbles;
    }
}
