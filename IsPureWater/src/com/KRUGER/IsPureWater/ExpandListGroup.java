package com.KRUGER.IsPureWater;

import java.util.ArrayList;

/**
 * Created with IntelliJ IDEA.
 * User: joshkruger
 * Date: 12/2/13
 * Time: 12:06 AM
 * To change this template use File | Settings | File Templates.
 */
public class ExpandListGroup {

    private String Name;

    private ArrayList<ExpandedListChild> Items;

    public String getName() {

        return Name;

    }

    public void setName(String name) {

        this.Name = name;

    }

    public ArrayList<ExpandedListChild> getItems() {

        return Items;

    }

    public void setItems(ArrayList<ExpandedListChild> Items) {

        this.Items = Items;

    }


}
