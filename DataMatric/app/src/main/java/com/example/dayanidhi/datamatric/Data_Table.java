package com.example.dayanidhi.datamatric;

import android.app.ActionBar;
import android.app.Activity;
import android.content.Context;
import android.content.SyncAdapterType;
import android.media.Image;
import android.os.Bundle;
import android.provider.Settings;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class Data_Table extends AppCompatActivity {
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
    String strDate, stringa, stringb, stringc, stringmy, android_id, dat, webst;
    DataHandler handler1;
    String website[] = new String[51];
    String datausage[] = new String[51];
    int i1;
    String a[] = new String[51];
    String web[] = new String[51];
    String imageId[] = new String[51];
    ArrayList<String> arrayList = new ArrayList<>();
    RadioGroup rg;
    int pos;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_data__table);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        filter = (ImageView) findViewById(R.id.filt);
        web = getIntent().getExtras().getStringArray("Data");
        arrayList = (ArrayList) getIntent().getSerializableExtra("Web");
        int i = 0;
        for (String val : arrayList) {
            imageId[i++] = val;
        }
        int ii = getIntent().getExtras().getInt("size");
        String[] webb = new String[ii];
        String[] imageidd = new String[ii];
        for (int ia = 0; ia < ii; ia++) {
            webb[ia] = web[ia];
            imageidd[ia] = imageId[ia];
            Date now = new Date();
            Date alsoNow = Calendar.getInstance().getTime();
            strDate = new SimpleDateFormat("dd-MM-yyyy").format(now);
            String[] str_array = strDate.split("-");
            stringa = str_array[0];
            stringb = str_array[1];
            stringc = str_array[2];
            stringmy = stringb + stringc;
            System.out.println(stringmy);
            android_id = Settings.Secure.getString(getApplicationContext().getContentResolver(),
                    Settings.Secure.ANDROID_ID);


            List data1 = getretrive();

            for (i1 = 0; i1 < data1.size(); i1++) {
                a[i1] = data1.get(i1) + "";
                String[] str_array1 = a[i1].split("=");
                website[i1] = str_array1[0];
                datausage[i1] = str_array1[1];
                System.out.println(website[i1] + datausage[i1]);

            }


        }
        String[] sad = new String[i1];
        for (int ab = 0; ab < i1; ab++) {
            sad[ab] = datausage[ab];
        }
        listdiaplay(sad, website);

                filter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                initiatePopupWindow();
            }
        });

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        getMenuInflater().inflate(R.menu.main, menu);

        return true;
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Take appropriate action for each action item click
        switch (item.getItemId()) {
            case R.id.jusweb:
                // search action
                Toast.makeText(getBaseContext(), "Comming Soon",Toast.LENGTH_SHORT).show();
                return true;
            case android.R.id.home:
                finish();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
    List data1;

    private PopupWindow pwindo;
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

          //  btnClosePopup = (Button) layout.findViewById(R.id.btn_close_popup);
           img=(ImageView) layout.findViewById(R.id.btn1_close_popup);
            img.setOnClickListener(cancel_button_click_listener);

            rg = (RadioGroup) layout.findViewById(R.id.radioGroup);
            rg.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {

                @Override
                public void onCheckedChanged(RadioGroup group, int checkedId) {
                    // TODO Auto-generated method stub

                    pos = group.getCheckedRadioButtonId();//rg.indexOfChild(findViewById(R.id.radioGroup));
                    //Toast.makeText(getBaseContext(), pos+"",Toast.LENGTH_SHORT).show();
                    switch (pos) {
                        case 2131492998:


                            data1 = getmonthretrive();


                            for (i1 = 0; i1 < data1.size(); i1++) {
                                a[i1] = data1.get(i1) + "";
                                String[] str_array1 = a[i1].split("=");
                                website[i1] = str_array1[0];
                                datausage[i1] = str_array1[1];
                                //System.out.println(website[i1] + datausage[i1]);

                            }

                            String[] month=new String[i1];
                            for(int ab=0;ab<i1;ab++)
                            {
                                month[ab]=datausage[ab];
                            }
                            listdiaplay(month,website);
                            pwindo.dismiss();
                            break;
                        case 2131493000:

                            data1 = getyearretrive();

                            for (i1 = 0; i1 < data1.size(); i1++) {
                                a[i1] = data1.get(i1) + "";
                                String[] str_array1 = a[i1].split("=");
                                website[i1] = str_array1[0];
                                datausage[i1] = str_array1[1];
                                //System.out.println(website[i1] + datausage[i1]);

                            }

                            String[] year=new String[i1];
                            for(int ab=0;ab<i1;ab++)
                            {
                                year[ab]=datausage[ab];
                            }
                            listdiaplay(year,website);
                            pwindo.dismiss();
                            break;

                        default:
                            //The default selection is RadioButton 1
                            Toast.makeText(getBaseContext(), "You have Clicked RadioButton 1",
                                    Toast.LENGTH_SHORT).show();
                            data1 = getyearretrive();

                            for (i1 = 0; i1 < data1.size(); i1++) {
                                a[i1] = data1.get(i1) + "";
                                String[] str_array1 = a[i1].split("=");
                                website[i1] = str_array1[0];
                                datausage[i1] = str_array1[1];
                                //System.out.println(website[i1] + datausage[i1]);

                            }

                            String[] def=new String[i1];
                            for(int ab=0;ab<i1;ab++)
                            {
                                def[ab]=datausage[ab];
                            }
                            listdiaplay(def,website);
                            pwindo.dismiss();
                            break;
                    }
                }
            });


            ///




            //divahar commit

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
    public List getyearretrive(){
        handler1 = new DataHandler(getBaseContext());
        handler1.open();
        List data = handler1.getAllDatayearonly(stringc);
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
