package com.example.dayanidhi.datamatric;

import android.app.Activity;
import android.content.Context;
import android.content.SyncAdapterType;
import android.media.Image;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.PopupWindow;
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
    ImageView filter;
    String web[]=new String[51];
    String imageId[]=new String[51];
    ArrayList<String> arrayList=new ArrayList<>();
    ArrayList<String> we=new ArrayList<>();
    ArrayList<String> da=new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_data__table);
        filter = (ImageView) findViewById(R.id.filt);
         web = getIntent().getExtras().getStringArray("Data");
         arrayList = (ArrayList)getIntent().getSerializableExtra("Web");
        int i=0;
        for(String val:arrayList){
            imageId[i++]=val;
        }
        int ii = getIntent().getExtras().getInt("size");
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
        filter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               // Snackbar.make(v, "clicked", Snackbar.LENGTH_LONG).setAction("Action", null).show();
                initiatePopupWindow();
            }
        });
    }

    private PopupWindow pwindo;
    Button btnClosePopup;
    ImageView img;
    private void initiatePopupWindow() {
        try {
// We need to get the instance of the LayoutInflater
            LayoutInflater inflater = (LayoutInflater) Data_Table.this
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            View layout = inflater.inflate(R.layout.filter,
                    (ViewGroup) findViewById(R.id.popup_element));
          //  imageView.setLayoutParams(new LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT));

            pwindo = new PopupWindow(layout, LinearLayout.LayoutParams.MATCH_PARENT,LinearLayout.LayoutParams.MATCH_PARENT, true);
            pwindo.showAtLocation(layout, Gravity.CENTER, 0, 0);

            btnClosePopup = (Button) layout.findViewById(R.id.btn_close_popup);
           img=(ImageView) layout.findViewById(R.id.btn1_close_popup);
            img.setOnClickListener(cancel_button_click_listener);
            btnClosePopup.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    pwindo.dismiss();

                }
            });

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private View.OnClickListener cancel_button_click_listener = new View.OnClickListener() {
        public void onClick(View v) {
            pwindo.dismiss();

        }
    };


}
