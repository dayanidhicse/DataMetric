package com.example.dayanidhi.datametrics;


import android.app.AlertDialog;
import android.content.Context;
import android.os.AsyncTask;

import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;

/**
 * Created by prabeesh on 7/14/2015.
 */
public class BackgroundTask extends AsyncTask<String,Void,String> {
    AlertDialog alertDialog;
    Context ctx;
    BackgroundTask(Context ctx)
    {
        this.ctx =ctx;
    }
    @Override
    protected void onPreExecute() {
        alertDialog = new AlertDialog.Builder(ctx).create();

    }

    @Override
    protected String doInBackground(String... params) {
        String chk_url = "http://juspaydatametrics.esy.es/checkweb.php";

        String deviceid = params[0];
        String datenow = params[1];
        String website = params[2];
        String datausage = params[3];
        String clicks = params[4];
        String seconds_used = params[5];

        try {
            URL url = new URL(chk_url);
            HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
            httpURLConnection.setRequestMethod("POST");
            httpURLConnection.setDoOutput(true);


            OutputStream OS = httpURLConnection.getOutputStream();
            BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(OS, "UTF-8"));
            String data = URLEncoder.encode("deviceid", "UTF-8") + "=" + URLEncoder.encode(deviceid, "UTF-8") + "&" +
                    URLEncoder.encode("datenow", "UTF-8") + "=" + URLEncoder.encode(datenow, "UTF-8") + "&" +
                    URLEncoder.encode("website", "UTF-8") + "=" + URLEncoder.encode(website, "UTF-8") + "&" +
                    URLEncoder.encode("datausage", "UTF-8") + "=" + URLEncoder.encode(datausage, "UTF-8") + "&" +
                    URLEncoder.encode("clicks", "UTF-8") + "=" + URLEncoder.encode(clicks, "UTF-8") + "&" +
                    URLEncoder.encode("seconds_used", "UTF-8") + "=" + URLEncoder.encode(seconds_used, "UTF-8");
            bufferedWriter.write(data);
            bufferedWriter.flush();
            bufferedWriter.close();
            OS.close();
            InputStream IS = httpURLConnection.getInputStream();
            IS.close();
            //httpURLConnection.connect();
            InputStream inputStream = httpURLConnection.getInputStream();

            inputStream.close();
            //httpURLConnection.connect();
            httpURLConnection.disconnect();
            return "Success";

        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }



        return null;
    }

    @Override
    protected void onProgressUpdate(Void... values) {
        super.onProgressUpdate(values);
    }

    @Override
    protected void onPostExecute(String result) {
        if(result.equals("Success")){
            System.out.println(result);
        }
    }
}
