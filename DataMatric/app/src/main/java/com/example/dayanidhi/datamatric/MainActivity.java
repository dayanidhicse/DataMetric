package com.example.dayanidhi.datamatric;

import android.app.ActionBar;
import android.app.ProgressDialog;
import android.app.SearchManager;
import android.content.Context;
import android.content.DialogInterface;
import android.app.AlertDialog;
import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.TrafficStats;
import android.os.Handler;
import android.provider.Settings;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Filter;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.net.URI;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Comparator;
import java.util.Date;

import static android.widget.Toast.LENGTH_SHORT;
import static android.widget.Toast.makeText;

public class MainActivity extends ActionBarActivity {

    private static final String TAG = "Main";
    private ProgressDialog progressBar;
    private Handler mHandler = new Handler();
    String ss="0",web1,strDate,stringa,stringb,stringc,stringmy,android_id,datausage;;
    int a=0,b=0,c=0,ii=0;
    String aa[]=new String[51];
    EditText et;
    DataHandler handler1;
    private long mStartRX = 0;
    private long mStartTX = 0;
    private WebView browser;

    ArrayList<String> arrayList=new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Date now = new Date();
       // Date alsoNow = Calendar.getInstance().getTime();
        strDate = new SimpleDateFormat("dd-MM-yyyy").format(now);
        String[] str_array = strDate.split("-");
        stringa = str_array[0];
        stringb = str_array[1];
        stringc=str_array[2];
        stringmy = stringb+stringc;
        android_id= Settings.Secure.getString(getApplicationContext().getContentResolver(),
                Settings.Secure.ANDROID_ID);

        this.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
         browser = (WebView) findViewById(R.id.webview);
         mStartRX = TrafficStats.getTotalRxBytes();
         mStartTX = TrafficStats.getTotalTxBytes();

        if (mStartRX == TrafficStats.UNSUPPORTED || mStartTX ==     TrafficStats.UNSUPPORTED) {

            AlertDialog.Builder alert = new AlertDialog.Builder(this);

            alert.setTitle("Uh Oh!");

            alert.setMessage("Your device does not support traffic stat monitoring.");

            alert.show();

        } else {

            mHandler.postDelayed(mRunnable, 1000);

        }

        //To store the webpage cache
        WebSettings settings = browser.getSettings();
        settings.setJavaScriptEnabled(true);
        browser.setScrollBarStyle(WebView.SCROLLBARS_OUTSIDE_OVERLAY);

        browser.getSettings().setAppCacheMaxSize( 5 * 1024 * 1024 ); // 5MB
        browser.getSettings().setAppCachePath( getApplicationContext().getCacheDir().getAbsolutePath() );
        browser.getSettings().setAllowFileAccess( true );
        browser.getSettings().setAppCacheEnabled( true );
        browser.getSettings().setJavaScriptEnabled( true );
        browser.getSettings().setCacheMode( WebSettings.LOAD_DEFAULT ); // load online by default

        if ( !isNetworkAvailable() ) { // loading offline
            browser.getSettings().setCacheMode(WebSettings.LOAD_CACHE_ELSE_NETWORK);
        }

        final AlertDialog alertDialog = new AlertDialog.Builder(this).create();

        progressBar = ProgressDialog.show(MainActivity.this, "", "Loading...");

        browser.setWebViewClient(new WebViewClient() {
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                progressBar.show();
                //Log.i(TAG, "Processing webview url click..." + ">>>>>>" + ss);
                b = Integer.parseInt(ss);
                c = b - a;
                handler1 = new DataHandler(getBaseContext());
                handler1.open();
                handler1.searchinsertupdate(android_id, strDate, web1, Integer.toString(c), stringmy, stringb, stringc);

               // Toast.makeText(getBaseContext(),"Data Inserted",Toast.LENGTH_LONG).show();
                handler1.close();
                if (arrayList.contains(web1)) {
                    int index = arrayList.indexOf(web1);
                    int old = Integer.parseInt(aa[index]);
                    old += c;
                    aa[index] = old + "";
                    //arrayList.set(index,""+old);
                } else {
                    arrayList.add(web1);
                    aa[ii] = "" + c;
                    ii++;

                }
                view.loadUrl(url);
                return true;
            }

            public void onPageFinished(WebView view, String url) {
                progressBar.dismiss();
                et.setText(url);
               // Log.i(TAG, "Finished loading URL: " + url + "=======>" + ss);
                a = Integer.parseInt(ss);
                // System.out.println("---------->a" + a);
                String host = ConvertToUrl(url).getHost();
                //System.out.println(host + "");
                web1 = host;
                if (progressBar.isShowing()) {
                    progressBar.dismiss();
                }
            }

            public void onReceivedError(WebView view, int errorCode, String description, String failingUrl) {
                Log.e(TAG, "Error: " + description);
                Toast.makeText(MainActivity.this, "Oh no! " + description, Toast.LENGTH_SHORT).show();
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

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                b = Integer.parseInt(ss);
                c = b - a;
                //System.out.println("===="+c+"===");
                if (arrayList.contains(web1)) {
                    int index = arrayList.indexOf(web1);
                    int old = Integer.parseInt(aa[index]);
                    old += c;
                    aa[index] = old + "";
                    //arrayList.set(index,""+old);
                } else {
                    arrayList.add(web1);
                    aa[ii] = "" + c;
                    ii++;

                }

                Intent i = new Intent(MainActivity.this, Data_Table.class);
                i.putExtra("Web", arrayList);
                i.putExtra("Data", aa);
                i.putExtra("size", ii);
                startActivity(i);
                //Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG).setAction("Action", null).show();
            }
        });
        et=(EditText) findViewById(R.id.et);
        et.setOnKeyListener(new View.OnKeyListener() {

    @Override
    public boolean onKey(View v, int keyCode, KeyEvent event) {
        if (v != null) {
            InputMethodManager imm = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(v.getWindowToken(), 0);
        }
        // If the event is a key-down event on the "enter" button
        if ((event.getAction() == KeyEvent.ACTION_DOWN) &&
                (keyCode == KeyEvent.KEYCODE_ENTER)) {
            // Perform action on key press

            browser.loadUrl(et.getText().toString());
            et.setText("");
            return true;
        }
        return false;
    }
});

        ImageView bt=(ImageView) findViewById(R.id.fab1);
        bt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                browser.loadUrl(et.getText().toString());
            }
        });

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

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

    private boolean isNetworkAvailable() {

        ConnectivityManager connectivityManager = (ConnectivityManager) getSystemService( CONNECTIVITY_SERVICE );
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        //System.out.println("-----------------ddd---------------"+activeNetworkInfo);
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
    }

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

    long tx,rx;
    private final Runnable mRunnable = new Runnable() {

        public void run() {
            try {
                ApplicationInfo app = MainActivity.this.getPackageManager().getApplicationInfo("com.example.dayanidhi.datamatric", 0);


                // TrafficStats.getUidTxBytes(app.uid);
                //TrafficStats.getUidRxBytes(app.uid);
                tx=TrafficStats.getUidTxBytes(app.uid);
                rx=TrafficStats.getUidRxBytes(app.uid);
                //    Math.round((tx + rx) / 1024);
              //  System.out.println("------------->"+app+"++++"+Math.round((tx + rx) / 1024));
                //return name;
            } catch (PackageManager.NameNotFoundException e) {
                Toast toast = makeText(MainActivity.this, e+"", LENGTH_SHORT);
                toast.show();
                e.printStackTrace();
            }
            String name=Math.round((tx + rx) / 1024)+"";

           ss=name;
            mHandler.postDelayed(mRunnable, 1000);

        }

    };
}
