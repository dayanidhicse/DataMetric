package com.example.dayanidhi.datametrics;

import android.content.Context;
import android.provider.Settings;

import java.util.Calendar;
import java.util.Date;

/**
 * Created by dayanidhi on 19/03/16.
 */
public class MobileInfo {
    Context context;
    Date today = Calendar.getInstance().getTime();

    public MobileInfo(Context context){
        this.context=context;
    }
    public String getDate()
    {

        return today.getDate()+"";
    }
    public String getMonth(){
        return today.getMonth()+1+"";
    }
    public String getYear()
    {
        return today.getYear()+1900+"";
    }
    public String getDeviceid()
    {
        return Settings.Secure.getString(context.getContentResolver(),
                Settings.Secure.ANDROID_ID);
    }

}
