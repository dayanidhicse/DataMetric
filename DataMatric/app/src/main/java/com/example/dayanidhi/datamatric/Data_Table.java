package com.example.dayanidhi.datamatric;

import android.app.Activity;
import android.content.SyncAdapterType;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;

public class Data_Table extends Activity {
    ListView list;
   /* String[] web = {
            "100 kb",
            "200 kb",
            "550 kb"
    } ;
    String[] imageId = {
            "WWW.JUSPAY.co.in",
            "www.google.co.in",
            "www.facebook.com"
    };*/
    String web[]=new String[51];
    String imageId[]=new String[51];
    ArrayList<String> arrayList=new ArrayList<>();
    ArrayList<String> we=new ArrayList<>();
    ArrayList<String> da=new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_data__table);
         web = getIntent().getExtras().getStringArray("Data");
         arrayList = (ArrayList)getIntent().getSerializableExtra("Web");
        int i=0;
        for(String val:arrayList){
            imageId[i++]=val;
        }
        int ii = getIntent().getExtras().getInt("size");
      //  System.out.println("--------"+ii+"--------");
        String[] webb =new String[ii];
        String[] imageidd =new String[ii];
       for(int ia=0;ia<ii;ia++)
        {
            webb[ia]=web[ia];
            imageidd[ia]=imageId[ia];
        }

        CustomList adapter1 = new
                CustomList(Data_Table.this, webb, imageidd);

        list=(ListView)findViewById(R.id.list);
        list.setAdapter(adapter1);
        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {
                Toast.makeText(Data_Table.this, "You Clicked at " + web[+position], Toast.LENGTH_SHORT).show();

            }
        });
    }
}
