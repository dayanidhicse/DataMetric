package com.example.dayanidhi.datametrics;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.TrafficStats;
import android.os.Bundle;
import android.os.Handler;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.EditText;
import android.widget.Toast;

import java.net.URI;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class HomeActivity extends AppCompatActivity {
    private static final String TAG = "Main";
    private ProgressDialog progressBar;
    private Handler Handler = new Handler();
    DataHandler handler1;
    private WebView browser;
    private DataHandler datahandler;
    private int startpoint,endpoint,totaldata;

    private String data,website;
    private int count=0;
    private MobileInfo info;
    private long Start_time;
    private ArrayList<String> Dataupdate1 = new ArrayList<String>();
    private  ArrayList<String> arrayList=new ArrayList<String>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        final EditText url=(EditText) findViewById(R.id.url);
        //This is to check the net status and get the data useage  of the app
        this.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
        browser = (WebView) findViewById(R.id.webview);
        info=new MobileInfo(getApplicationContext());
        long mStartRX = TrafficStats.getTotalRxBytes();
        long mStartTX = TrafficStats.getTotalTxBytes();

        if (mStartRX == TrafficStats.UNSUPPORTED || mStartTX ==     TrafficStats.UNSUPPORTED) {

            AlertDialog.Builder alert = new AlertDialog.Builder(this);

            alert.setTitle("Uh Oh!");

            alert.setMessage("Your device does not support traffic stat monitoring.");

            alert.show();

        } else {

            Handler.postDelayed(mRunnable, 1000);

        }

        //To store the webpage cache
        WebSettings settings = browser.getSettings();
        settings.setJavaScriptEnabled(true);
        browser.setScrollBarStyle(WebView.SCROLLBARS_OUTSIDE_OVERLAY);

        browser.getSettings().setAppCacheMaxSize(5 * 1024 * 1024); // 5MB
        browser.getSettings().setAppCachePath(getApplicationContext().getCacheDir().getAbsolutePath());
        browser.getSettings().setAllowFileAccess(true);
        browser.getSettings().setAppCacheEnabled(true);
        browser.getSettings().setJavaScriptEnabled(true);
        browser.getSettings().setCacheMode( WebSettings.LOAD_DEFAULT ); // load online by default


        if ( !isNetworkAvailable() ) { // loading offline
            browser.getSettings().setCacheMode(WebSettings.LOAD_CACHE_ELSE_NETWORK);
        }

        //This contains the webview and progress calculation for datauseage
        final AlertDialog alertDialog = new AlertDialog.Builder(this).create();


        progressBar = ProgressDialog.show(HomeActivity.this, "", "Loading...");

        browser.setWebViewClient(new WebViewClient() {

            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                progressBar.show();

                long End_time = System.currentTimeMillis();
                long Total_time = End_time - Start_time;
                double Seconds_Used = Total_time / 1000.0;
               int visited = 1;
                endpoint = Integer.parseInt(data);

                totaldata = endpoint - startpoint;
                datahandler = new DataHandler(getBaseContext());
                datahandler.open();

                datahandler.searchinsertupdate(info.getDeviceid(), info.getDate() + "-" + info.getMonth() + "-" + info.getYear(), website, Integer.toString(totaldata), info.getMonth() + info.getYear(), info.getMonth(), info.getYear(), Integer.toString(visited), Double.toString(Seconds_Used));

                // Toast.makeText(getBaseContext(),"Data Inserted",Toast.LENGTH_LONG).show();
                datahandler.close();
                if (arrayList.contains(website)) {
                    int index = arrayList.indexOf(website);
                    int old = Integer.parseInt(Dataupdate1.get(index));
                    old += totaldata;
                    Dataupdate1.add(index,old+"");

                } else {
                    arrayList.add(website);
                    Dataupdate1.add(count,totaldata+"");
                    count++;

                }
                view.loadUrl(url);
                return true;
            }

            public void onPageFinished(WebView view, String Url) {
                progressBar.dismiss();
                url.setText(Url);
                Start_time = System.currentTimeMillis();
                startpoint = Integer.parseInt(data);
                String host = ConvertToUrl(Url).getHost();
                website = host;
                if (progressBar.isShowing()) {
                    progressBar.dismiss();
                }
            }

            public void onReceivedError(WebView view, int errorCode, String description, String failingUrl) {
                Log.e(TAG, "Error: " + description);
                Toast.makeText(HomeActivity.this, "Oh no! " + description, Toast.LENGTH_SHORT).show();
                alertDialog.setTitle("Error");
                alertDialog.setMessage(description);
                alertDialog.setButton("Ok", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        return;
                    }
                });
                alertDialog.show();
            }
        });
        browser.loadUrl("https://www.google.co.in/");

//This contains some functions to show the web url in the edit text area

        url.setOnKeyListener(new View.OnKeyListener() {

            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if (v != null) {
                    InputMethodManager Inputmethod = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                    Inputmethod.hideSoftInputFromWindow(v.getWindowToken(), 0);
                }
                // If the event is a key-down event on the "enter" button
                if ((event.getAction() == KeyEvent.ACTION_DOWN) &&
                        (keyCode == KeyEvent.KEYCODE_ENTER)) {
                    // Perform action on key press

                    browser.loadUrl(url.getText().toString());
                    url.setText("");
                    return true;
                }
                return false;
            }
        });

        //This floating bar is to switch to metrics page
        FloatingActionButton metrics = (FloatingActionButton) findViewById(R.id.metrics);
        metrics.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(HomeActivity.this, MetricsActivity.class);
                startActivity(intent);
            }
        });

    }
    @Override
    public void onPause() {
        super.onPause();
        long End_time = System.currentTimeMillis();
        long Total_time = End_time - Start_time;
        double Seconds_Used = Total_time / 1000.0;
       int visited=1;
        endpoint = Integer.parseInt(data);

        totaldata = endpoint - startpoint;
        datahandler = new DataHandler(getBaseContext());
        datahandler.open();

        datahandler.searchinsertupdate(info.getDeviceid(), info.getDate()+"-"+info.getMonth()+"-"+info.getYear(), website, Integer.toString(totaldata), info.getMonth()+info.getYear(), info.getMonth(), info.getYear(),Integer.toString(visited), Double.toString(Seconds_Used));

        // Toast.makeText(getBaseContext(),"Data Inserted",Toast.LENGTH_LONG).show();
        datahandler.close();
        if (arrayList.contains(website)) {
            int index = arrayList.indexOf(website);
            int old = Integer.parseInt(Dataupdate1.get(index));
            old += totaldata;
            Dataupdate1.add(index,old+"");

        } else {
            arrayList.add(website);
            Dataupdate1.add(count,totaldata+"");
            count++;

        }
        Toast.makeText(getApplicationContext(),"14. onPause()", Toast.LENGTH_SHORT).show();
    }
    @Override
    public void onStop() {
        super.onStop();
        long End_time = System.currentTimeMillis();
        long Total_time = End_time - Start_time;
        double Seconds_Used = Total_time / 1000.0;
        int visited=1,i1;
        String splitdata[]=new String[100];
        ArrayList<String> website1=new ArrayList<String>();
        ArrayList<String> datausage=new ArrayList<String>();
        ArrayList<String> datenw=new ArrayList<String>();
        ArrayList<String> clicks=new ArrayList<String>();
        ArrayList<String> usd_seconds=new ArrayList<String>();
        endpoint = Integer.parseInt(data);

        totaldata = endpoint - startpoint;
        datahandler = new DataHandler(getBaseContext());
        datahandler.open();

        datahandler.searchinsertupdate(info.getDeviceid(), info.getDate()+"-"+info.getMonth()+"-"+info.getYear(), website, Integer.toString(totaldata), info.getMonth()+info.getYear(), info.getMonth(), info.getYear(),Integer.toString(visited), Double.toString(Seconds_Used));

        // Toast.makeText(getBaseContext(),"Data Inserted",Toast.LENGTH_LONG).show();
        datahandler.close();
        if (arrayList.contains(website)) {
            int index = arrayList.indexOf(website);
            int old = Integer.parseInt(Dataupdate1.get(index));
            old += totaldata;
            Dataupdate1.add(index,old+"");

        } else {
            arrayList.add(website);
            Dataupdate1.add(count,totaldata+"");
            count++;

        }
      //divahar
        List dataretrival=getretrive();

        for ( i1 = 0; i1 < dataretrival.size(); i1++) {
            splitdata[i1]=dataretrival.get(i1)+"";
            String[] str_array1 = splitdata[i1].split("=");

            datenw.add(i1,str_array1[0]);
            website1.add(i1, str_array1[1]);
            datausage.add(i1, str_array1[2]);
            clicks.add(i1,str_array1[3]);
            usd_seconds.add(i1,str_array1[4]);
            System.out.println(datenw.get(i1) + website1.get(i1) + datausage.get(i1) + clicks.get(i1) + usd_seconds.get(i1));
            storemysql(info.getDeviceid(),datenw.get(i1),website1.get(i1), datausage.get(i1),clicks.get(i1),usd_seconds.get(i1));

        }

      //divahar
        Toast.makeText(getApplicationContext(),"15. onStop()", Toast.LENGTH_SHORT).show();
    }

    //This is to convert the String to url
    private URL ConvertToUrl(String urlStr) {
        try {
            URL url = new URL(urlStr);
            URI uri = new URI(url.getProtocol(), url.getUserInfo(),
                    url.getHost(), url.getPort(), url.getPath(),
                    url.getQuery(), url.getRef());
            url = uri.toURL();
            return url;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    //This is to check the network status
    private boolean isNetworkAvailable() {

        ConnectivityManager connectivityManager = (ConnectivityManager) getSystemService( CONNECTIVITY_SERVICE );
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
    }

    //This is to enable the browser back button

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if ((keyCode == KeyEvent.KEYCODE_BACK) && browser.canGoBack()) {
            browser.goBack();
            return true;
        }
        else
        {
            finish();
        }
        return super.onKeyDown(keyCode, event);
    }

    //This is to get the data useage of the application

    private final Runnable mRunnable = new Runnable() {
        long transmission,reciveing;
        public void run() {

            try {
                ApplicationInfo app = HomeActivity.this.getPackageManager().getApplicationInfo("com.example.dayanidhi.datametrics", 0);
                transmission= TrafficStats.getUidTxBytes(app.uid);
                reciveing=TrafficStats.getUidRxBytes(app.uid);

            } catch (PackageManager.NameNotFoundException e) {
               // Toast toast = makeText(HomeActivity.this, e+"", LENGTH_SHORT);
               // toast.show();
                e.printStackTrace();
            }
            String Dataused=Math.round((transmission + reciveing) / 1024)+"";

            data=Dataused;
            Handler.postDelayed(mRunnable, 500);

        }

    };

    public List getretrive(){
        //divahar commit
        handler1 = new DataHandler(getBaseContext());
        handler1.open();

        List datafetch = handler1.getALLData(info.getDeviceid());
        handler1.close();
        System.out.println(datafetch);

        return datafetch;
        //divahar commit finished
    }

    public void storemysql(String id,String date, String web, String dataused, String clicks, String seconds_used){
        BackgroundTask backgroundTask = new BackgroundTask(HomeActivity.this);
        backgroundTask.execute(id, date, web, dataused, clicks, seconds_used);
    }


    //okhttp

}
