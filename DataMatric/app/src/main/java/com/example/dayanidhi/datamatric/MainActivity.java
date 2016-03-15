package com.example.dayanidhi.datamatric;

import android.app.ActionBar;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.app.AlertDialog;
import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.net.TrafficStats;
import android.os.Handler;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Filter;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.net.URI;
import java.net.URL;
import java.util.ArrayList;
import java.util.Comparator;

public class MainActivity extends ActionBarActivity {

    private TextView tvSupported, tvDataUsageWiFi, tvDataUsageMobile, tvDataUsageTotal;
    private ListView lvApplications;
    private static final String TAG = "Main";
    private ProgressDialog progressBar;
    private Handler mHandler = new Handler();
    private long dataUsageTotalLast = 0;
    String ss="0",web1;
    ArrayAdapter<ApplicationItem> adapterApplications;
    int a=0,b=0,c=0,ii=0;
    String aa[]=new String[51];
    String ab[]=new String[51];
    ArrayList<String> arrayList=new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);



/*

        ActionBar mActionBar = getActionBar();
       mActionBar.setDisplayShowHomeEnabled(true);
        mActionBar.setDisplayShowTitleEnabled(true);
        LayoutInflater mInflater = LayoutInflater.from(this);

        View mCustomView = mInflater.inflate(R.layout.custom_actionbar, null);
        TextView mTitleTextView = (TextView) mCustomView.findViewById(R.id.title_text);
        mTitleTextView.setText("My Own Title");

        ImageButton imageButton = (ImageButton) mCustomView
                .findViewById(R.id.imageButton);
        imageButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                Toast.makeText(getApplicationContext(), "Refresh Clicked!",
                        Toast.LENGTH_LONG).show();
            }
        });

        mActionBar.setCustomView(mCustomView);
        mActionBar.setDisplayShowCustomEnabled(true);



*/
        tvSupported = (TextView) findViewById(R.id.tvSupported);
        tvDataUsageWiFi = (TextView) findViewById(R.id.tvDataUsageWiFi);
        tvDataUsageMobile = (TextView) findViewById(R.id.tvDataUsageMobile);
        tvDataUsageTotal = (TextView) findViewById(R.id.tvDataUsageTotal);
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(MainActivity.this, Data_Table.class);
                i.putExtra("Web", arrayList);
                i.putExtra("Data",aa);
                i.putExtra("size",ii);
                startActivity(i);
                //Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG).setAction("Action", null).show();
            }
        });


        final WebView browser = (WebView) findViewById(R.id.webview);

       //getSupportActionBar().setDisplayShowHomeEnabled(true);
      //  getSupportActionBar().setIcon(R.drawable.juspay_icon);

        WebSettings settings = browser.getSettings();
        settings.setJavaScriptEnabled(true);
        browser.setScrollBarStyle(WebView.SCROLLBARS_OUTSIDE_OVERLAY);

        final AlertDialog alertDialog = new AlertDialog.Builder(this).create();

        progressBar = ProgressDialog.show(MainActivity.this, "", "Loading...");

        browser.setWebViewClient(new WebViewClient() {
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                Log.i(TAG, "Processing webview url click..."+">>>>>>"+ss);
                b = Integer.parseInt(ss);
                System.out.println("---------->b"+b);
                c=b-a;


                System.out.println("result=>web="+web1+"-->size :"+c+"kb");
              //  aa[ii]=c+"";

                //ab[ii]=web1;
                if(arrayList.contains(web1)){
                    int index=arrayList.indexOf(web1);
                    int old=Integer.parseInt(aa[index]);
                    old+=c;
                    aa[index]=old+"";
                    //arrayList.set(index,""+old);
                }
                else{
                    arrayList.add(web1);
                    aa[ii]=""+c;
                    ii++;

                }
                view.loadUrl(url);
                return true;
            }

            public void onPageFinished(WebView view, String url) {

                Log.i(TAG, "Finished loading URL: " + url + "=======>" + ss);
                a = Integer.parseInt(ss);
                System.out.println("---------->a"+a);
                    String host = ConvertToUrl(url).getHost();
                    System.out.println(host + "");
                web1=host;
                    if (progressBar.isShowing()) {
                    progressBar.dismiss();
                }
            }

            public void onReceivedError(WebView view, int errorCode, String description, String failingUrl) {
                Log.e(TAG, "Error: " + description);
                Toast.makeText(MainActivity.this, "Oh no! " + description, Toast.LENGTH_SHORT).show();
                alertDialog.setTitle("Error");
                alertDialog.setMessage(description);
                alertDialog.setButton("Ok",new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        return;
                    }
                });
                alertDialog.show();
            }
        });
        browser.loadUrl("https://www.google.co.in/");
        FloatingActionButton fab1 = (FloatingActionButton) findViewById(R.id.fab1);
        fab1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                EditText et=(EditText) findViewById(R.id.et);

                browser.loadUrl(et.getText().toString());
                //Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG).setAction("Action", null).show();
            }
        });


///
        if (TrafficStats.getTotalRxBytes() != TrafficStats.UNSUPPORTED && TrafficStats.getTotalTxBytes() != TrafficStats.UNSUPPORTED) {
            handler.postDelayed(runnable, 0);

            initAdapter();
            lvApplications = (ListView) findViewById(R.id.lvInstallApplication);
            lvApplications.setAdapter(adapterApplications);

        } else {

            tvSupported.setVisibility(View.VISIBLE);
        }



        ////
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }
    public Handler handler = new Handler();
    public Runnable runnable = new Runnable() {
        public void run() {
            long mobile = TrafficStats.getMobileRxBytes() + TrafficStats.getMobileTxBytes();
            long total = TrafficStats.getTotalRxBytes() + TrafficStats.getTotalTxBytes();
            tvDataUsageWiFi.setText("" + (total - mobile) / 1024 + " Kb");
            tvDataUsageMobile.setText("" + mobile / 1024 + " Kb");
            tvDataUsageTotal.setText("" + total / 1024 + " Kb");
            if (dataUsageTotalLast != total) {
                dataUsageTotalLast = total;
                updateAdapter();
            }
            handler.postDelayed(runnable, 5000);
        }
    };

    public void initAdapter() {

        adapterApplications = new ArrayAdapter<ApplicationItem>(getApplicationContext(), R.layout.item_install_application) {
            @Override
            public View getView(int position, View convertView, ViewGroup parent) {
                ApplicationItem app = getItem(position);

                final View result;
                if (convertView == null) {
                    result = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_install_application, parent, false);
                } else {
                    result = convertView;
                }

                TextView tvAppName = (TextView) result.findViewById(R.id.tvAppName);
                TextView tvAppTraffic = (TextView) result.findViewById(R.id.tvAppTraffic);

                // TODO: resize once

                final int iconSize = Math.round(32 * getResources().getDisplayMetrics().density);
                tvAppName.setCompoundDrawablesWithIntrinsicBounds(
                        //app.icon,
                        new BitmapDrawable(getResources(), Bitmap.createScaledBitmap(
                                ((BitmapDrawable) app.getIcon(getApplicationContext().getPackageManager())).getBitmap(), iconSize, iconSize, true)
                        ),
                        null, null, null
                );
                if(app.getApplicationLabel(getApplicationContext().getPackageManager()).equals("DataMetric")) {
                    tvAppName.setText(app.getApplicationLabel(getApplicationContext().getPackageManager()));
              // System.out.println(app.getApplicationLabel(getApplicationContext().getPackageManager()) + "===>");
                   // Toast.makeText(MainActivity.this, Integer.toString(app.getTotalUsageKb()), Toast.LENGTH_SHORT).show();

                    tvAppTraffic.setText(Integer.toString(app.getTotalUsageKb()) + " Kb");
              //  if(app.getApplicationLabel(getApplicationContext().getPackageManager()).equals("DataMetric")) {

                    ss = app.getTotalUsageKb() + "";

                }

                return result;
            }
            @Override
            public int getCount() {
                return super.getCount();
            }

            @Override
            public Filter getFilter() {
                return super.getFilter();
            }
        };

// TODO: resize icon once
        for (ApplicationInfo app : getApplicationContext().getPackageManager().getInstalledApplications(0)) {
            ApplicationItem item = ApplicationItem.create(app);
            if(item != null) {
                adapterApplications.add(item);
            }
        }
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


    public void updateAdapter() {
        for (int i = 0, l = adapterApplications.getCount(); i < l; i++) {
            ApplicationItem app = adapterApplications.getItem(i);
            app.update();
        }

        adapterApplications.sort(new Comparator<ApplicationItem>() {
            @Override
            public int compare(ApplicationItem lhs, ApplicationItem rhs) {
                return (int)(rhs.getTotalUsageKb() - lhs.getTotalUsageKb());
            }
        });
        adapterApplications.notifyDataSetChanged();
    }
}
