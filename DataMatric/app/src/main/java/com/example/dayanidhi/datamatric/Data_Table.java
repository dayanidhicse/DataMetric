package com.example.dayanidhi.datamatric;

import android.app.Activity;
import android.content.Context;
import android.content.SyncAdapterType;
import android.media.Image;
import android.os.Bundle;
import android.provider.Settings;
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
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.ListIterator;

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
    String website[]=new String[51];
    String datausage[]=new String[51];

    ArrayList<String> arrayList=new ArrayList<>();
    ArrayList<String> we=new ArrayList<>();
    ArrayList<String> da=new ArrayList<>();
    String strDate,stringa,stringb,stringc,stringmy,android_id,dat,webst;
    DataHandler handler1;
    int i1;
    String a[]=new String[51];
    RadioGroup rg1;
    RadioButton rb;
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
            //enter the insertion

            //divahar commit for the normal list display
            Date now = new Date();
            Date alsoNow = Calendar.getInstance().getTime();
            strDate = new SimpleDateFormat("dd-MM-yyyy").format(now);
            String[] str_array = strDate.split("-");
            stringa = str_array[0];
            stringb = str_array[1];
            stringc=str_array[2];
            stringmy = stringb+stringc;
            System.out.println(stringmy);
            android_id= Settings.Secure.getString(getApplicationContext().getContentResolver(),
                    Settings.Secure.ANDROID_ID);




            List data1=getretrive();

            for ( i1 = 0; i1 < data1.size(); i1++) {
                a[i1]=data1.get(i1)+"";
                String[] str_array1 = a[i1].split("=");
                website[i1] = str_array1[0];
                 datausage[i1]= str_array1[1];
                System.out.println(website[i1]+datausage[i1]);

            }


        }
        String[] sad=new String[i1];
        for(int ab=0;ab<i1;ab++)
        {
            sad[ab]=datausage[ab];
        }
        listdiaplay(sad,website);
//divahar commit for the normal list diaplay finished

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
;
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
            //divahar commit

                List data1 = getmonthretrive();

                for (i1 = 0; i1 < data1.size(); i1++) {
                    a[i1] = data1.get(i1) + "";
                    String[] str_array1 = a[i1].split("=");
                    website[i1] = str_array1[0];
                    datausage[i1] = str_array1[1];
                    System.out.println(website[i1] + datausage[i1]);

                }

            //divahar commit
            btnClosePopup.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    String[] sad1=new String[i1];
                    for(int ab=0;ab<i1;ab++)
                    {
                        sad1[ab]=datausage[ab];
                    }
                    listdiaplay(sad1,website);
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
    public List getretrive(){
        //divahar commit
        handler1 = new DataHandler(getBaseContext());
        handler1.open();

        List data = handler1.getAllDatamonth(stringmy);
        handler1.close();
        System.out.println(data);

        return data;
        //divahar commit finished
    }
//divahar commit
    public List getmonthretrive(){
        handler1 = new DataHandler(getBaseContext());
        handler1.open();

        List data = handler1.getAllDatamonthonly(stringb);
        handler1.close();
        System.out.println(data);

        return data;
    }
    //divahar commit finished
public void listdiaplay(String [] data,String [] website1 ){

    CustomList adapter1 = new
            CustomList(Data_Table.this, data, website1);

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
