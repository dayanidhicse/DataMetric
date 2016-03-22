package com.example.dayanidhi.datametrics;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ListView;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class MetricsActivity extends AppCompatActivity {
    ListView list;
    private MobileInfo info ;
    ArrayList<String> website=new ArrayList<>();
    ArrayList<String> datausage=new ArrayList<>();
    int i1;
    DataHandler handler1;
    String a[] = new String[51];
    String  stringm;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_metrics);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);//this is to enable the up arrow
        getPermissionToCamera();//This is to access the camera for qr runtime permission
        info = new MobileInfo(getApplicationContext());
        List data1 = getretrive();

        for (i1 = 0; i1 < data1.size(); i1++) {
            a[i1] = data1.get(i1) + "";
            String[] str_array1 = a[i1].split("=");
            website.add (str_array1[0]);
            datausage.add(str_array1[1]);
           // System.out.println(website[i1] + datausage[i1]);

        }

        String[] sad = new String[i1];
        for (int ab = 0; ab < i1; ab++) {
            sad[ab] = datausage.get(ab);
        }
        listdiaplay(sad, website);

        //
        FloatingActionButton filter = (FloatingActionButton) findViewById(R.id.filter);
        filter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               // initiatePopupWindow();
               Snackbar.make(view, "Filter", Snackbar.LENGTH_LONG).setAction("Action", null).show();
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
                Intent i = new Intent(MetricsActivity.this, JuswebActivity.class);
                startActivity(i);

                return true;
            case android.R.id.home:
                finish();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
//These code which gets the value from sqllite update,insert
public List getretrive(){
    handler1 = new DataHandler(getBaseContext());
    handler1.open();

    List data = handler1.getAllDatamonth(info.getMonth()+info.getYear());
    handler1.close();
    System.out.println(data);

    return data;
    //divahar commit finished
}
    //divahar commit
    public List getmonthretrive(){
        handler1 = new DataHandler(getBaseContext());
        handler1.open();

        List data = handler1.getAllDatamonthonly(info.getMonth());
        handler1.close();
        System.out.println(data);

        return data;
    }
    public List getyearretrive(){
        handler1 = new DataHandler(getBaseContext());
        handler1.open();
        List data = handler1.getAllDatayearonly(info.getYear());
        handler1.close();
        System.out.println(data);
        return data;
    }
    public void listdiaplay(String [] data,ArrayList<String> website11 ){

        CustomList adapter1 = new
                CustomList(MetricsActivity.this, data, website11);

        list=(ListView)findViewById(R.id.list);
        list.setAdapter(adapter1);

    }
    //This is for the popup window


    //
    // Identifier for the permission request
    private static final int CAMERA_PERMISSIONS_REQUEST = 1;

    // Called when the user is performing an action which requires the app to read the
    // user's contacts
    public void getPermissionToCamera() {
        // 1) Use the support library version ContextCompat.checkSelfPermission(...) to avoid
        // checking the build version since Context.checkSelfPermission(...) is only available
        // in Marshmallow
        // 2) Always check for permission (even if permission has already been granted)
        // since the user can revoke permissions at any time through Settings
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA)
                != PackageManager.PERMISSION_GRANTED) {

            // The permission is NOT already granted.
            // Check if the user has been asked about this permission already and denied
            // it. If so, we want to give more explanation about why the permission is needed.
            if (shouldShowRequestPermissionRationale(
                    Manifest.permission.CAMERA)) {
                // Show our own UI to explain to the user why we need to read the contacts
                // before actually requesting the permission and showing the default UI
            }

            // Fire off an async request to actually get the permission
            // This will show the standard permission request dialog UI
            requestPermissions(new String[]{Manifest.permission.CAMERA},
                    CAMERA_PERMISSIONS_REQUEST);
        }
    }
    // Callback with the request from calling requestPermissions(...)
    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           @NonNull String permissions[],
                                           @NonNull int[] grantResults) {
        // Make sure it's our original READ_CONTACTS request
        if (requestCode == CAMERA_PERMISSIONS_REQUEST) {
            if (grantResults.length == 1 &&
                    grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                Toast.makeText(this, "camera permission granted", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(this, "camera permission denied", Toast.LENGTH_SHORT).show();
            }
        } else {
            super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        }
    }

}
