package com.example.dayanidhi.datamatric;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

public class Data_Table extends Activity {
    ListView list;
    String[] web = {
            "Juspay",
            "white",
            "black"
    } ;
    String[] imageId = {
            "WWW.JUSPAY.co.in",
            "www.google.co.in",
            "www.facebook.com"
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_data__table);
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
