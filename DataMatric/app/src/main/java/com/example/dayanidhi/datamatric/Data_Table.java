package com.example.dayanidhi.datamatric;

import android.app.Activity;
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
       /* for(int i=0;i<ii;i++)
        {int temp=Integer.parseInt(web[i]);
            for(int j=i+1;j<ii;j++){

                    if (imageId[i].equals(imageId[j])) {
                        imageId[j] = null;
                        temp = temp + Integer.parseInt(web[i]);
                    }

            }
            System.out.println(temp + "====");
        }*/
        CustomList adapter1 = new
                CustomList(Data_Table.this, web, imageId);
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
