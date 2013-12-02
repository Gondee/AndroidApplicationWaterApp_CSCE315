package com.KRUGER.IsPureWater;

import android.app.Activity;
import android.os.*;
import android.widget.ExpandableListView;
import android.widget.*;

import java.util.ArrayList;


/**
 * Created with IntelliJ IDEA.
 * User: joshkruger
 * Date: 11/11/13
 * Time: 8:33 PM
 * To change this template use File | Settings | File Templates.
 */
public class ContaminentInfo extends Activity {

    private ExpandedListAdapter ExpAdapter;
    private ArrayList<ExpandListGroup> ExpListItems;
    private ExpandableListView ExpandList;
    ArrayList<Contaminant> ContList;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.contaminents);



        ContList = (ArrayList<Contaminant>) getIntent().getSerializableExtra("contaminants");



        ExpandList = (ExpandableListView) findViewById(R.id.expandableContaminentsView);
        ExpListItems = SetStandardGroups();

        ExpAdapter = new ExpandedListAdapter(ContaminentInfo.this, ExpListItems);
        ExpandList.setAdapter(ExpAdapter);



    }

    public ArrayList<ExpandListGroup> SetStandardGroups() {

        ArrayList<ExpandListGroup> list = new ArrayList<ExpandListGroup>();
        ArrayList<ExpandedListChild> list2 = new ArrayList<ExpandedListChild>();


        for(int i=0;i<ContList.size();i++){
            ExpandListGroup group = new ExpandListGroup();
            ArrayList<ExpandedListChild> nodes = new ArrayList<ExpandedListChild>();
            Contaminant newCont = ContList.get(i); //Working Contraminent

            group.setName("         "+newCont.getName());

            ExpandedListChild cAvg = new ExpandedListChild();
            ExpandedListChild cMax = new ExpandedListChild();
            ExpandedListChild cHL = new ExpandedListChild();  //HL is health limit
            ExpandedListChild cLL = new ExpandedListChild();  //LL is legal limit
            ExpandedListChild cMU = new ExpandedListChild();  //Mesurment Unit

            cAvg.setName("\t Average: "+ newCont.getAverage());
            cMax.setName("\t Max: "+newCont.getMax());
            cHL.setName("\t Health Limit: "+newCont.getHealth_Limit());
            cLL.setName("\t Legal Limit: "+newCont.getLegal_Limit());
            cMU.setName("\t Unit Type: "+newCont.getUnits());

            cAvg.setTag(null);
            cMax.setTag(null);
            cHL.setTag(null);
            cLL.setTag(null);
            cMU.setTag(null);

            nodes.add(cAvg);
            nodes.add(cMax);
            nodes.add(cHL);
            nodes.add(cLL);
            nodes.add(cMU);

            group.setItems(nodes);
            list.add(group);

        }

        return list;
    }

}
