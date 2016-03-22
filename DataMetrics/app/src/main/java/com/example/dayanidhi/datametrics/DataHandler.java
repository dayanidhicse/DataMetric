package com.example.dayanidhi.datametrics;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by dayanidhi on 16/03/16.
 */
public class DataHandler {
    public static final String DEVID = "deviceid";
    public static final String DATANOW = "datenow";
    public static final String WEBSITE = "website";
    public static final String DATAUSAGE = "datausage";
    public static final String MONTHYEAR = "monthyear";
    public static final String MONTH = "month";
    public static final String YEAR = "year";
    public static final String NO_OF_CLICKS = "no_of_clicks";
    public static final String SECONDS_USED = "seconds_used";


    public static final String TABLE_NAME = "mytable";
    public static final String DATA_BASE_NAME = "mydatabase12";
    public static final int DATABASE_VERSION = 1;
    public static final String TABLE_CREATE = "create table mytable (deviceid varchar, datenow varchar, website varchar,datausage varchar,monthyear varchar,month varchar,year varchar,no_of_clicks varchar,seconds_used varchar);";
    DataBaseHelper dbhelper;
    Context ctx;
    SQLiteDatabase db;
    int newdatausage;
    String datausageupadte,cid,datausage22,i,dateupadte,newdata,noofclicks,secondsused,NO_of_CLICKS,Seconds_Used;
    int flag=0;
    List<String> list = new ArrayList<String>();
    List<String> listmonth = new ArrayList<String>();

    String []a;
    int ii=0;
    public DataHandler(Context ctx){
        this.ctx=ctx;
        dbhelper = new DataBaseHelper(ctx);
    }
    public static class DataBaseHelper extends SQLiteOpenHelper {

        public DataBaseHelper(Context ctx){
            super(ctx,DATA_BASE_NAME,null, DATABASE_VERSION);
        }

        @Override
        public void onCreate(SQLiteDatabase db) {
            try {
                db.execSQL(TABLE_CREATE);
            }
            catch(Exception e){
                e.printStackTrace();
            }
        }


        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
            db.execSQL("DROP TABLE IF EXISTS mytable");
            onCreate(db);
        }
    }
    public DataHandler open(){
        db = dbhelper.getWritableDatabase();
        return  this;
    }

    public void close(){
        dbhelper.close();
    }
    public String searchinsertupdate(String deviceid,String datenow,String website, String datausage,String monthyear,String month,String year,String no_of_clicks,String seconds_used){
        System.out.println(deviceid);
        SQLiteDatabase db =dbhelper.getWritableDatabase();
        String[] columns={DataHandler.WEBSITE,DataHandler.DATAUSAGE,DataHandler.DATANOW,DataHandler.NO_OF_CLICKS,DataHandler.SECONDS_USED};
        Cursor cursor = db.query(DataHandler.TABLE_NAME,columns,DEVID+"=?",new String[]{deviceid},null,null,null,null);
        StringBuffer buffer = new StringBuffer();
        while(cursor.moveToNext()){
            cid = cursor.getString(0);
            datausage22 = cursor.getString(1);
            dateupadte=cursor.getString(2);
            noofclicks=cursor.getString(3);
            secondsused = cursor.getString(4);

            if(website.equals(cid)&&datenow.equals(dateupadte)){
                flag=1;
                System.out.println(flag);
                break;
            }

        }
        if(flag==1) {
            System.out.println(datausage22);
            System.out.println(datausage);
            newdatausage = Integer.parseInt(datausage22) + Integer.parseInt(datausage);
            datausageupadte = Integer.toString(newdatausage);
            NO_of_CLICKS = Integer.toString(Integer.parseInt(noofclicks) + Integer.parseInt(no_of_clicks));
            Seconds_Used = Double.toString(Double.parseDouble(secondsused)+Double.parseDouble(seconds_used));
            i = updateContact(deviceid, cid, datausageupadte,NO_of_CLICKS,Seconds_Used);
           // System.out.println(i);
            if (i.equals("true")) {
                System.out.println("Success == == == > UPDATED");
            } else {
                System.out.println("Failure");
            }
        }
        else {
            ContentValues content = new ContentValues();
            content.put(DEVID, deviceid);
            content.put(DATANOW, datenow);
            content.put(WEBSITE, website);
            content.put(DATAUSAGE, datausage);
            content.put(MONTHYEAR, monthyear);
            content.put(MONTH, month);
            content.put(YEAR, year);
            content.put(NO_OF_CLICKS, no_of_clicks);
            content.put(SECONDS_USED, seconds_used);
           long i = db.insertOrThrow(TABLE_NAME, null, content);
            System.out.println("--->"+i);


        }



        return i;

    }
    public String updateContact(String deviceid1, String cid1, String datausageupadte1,String clicks,String usedsec) {
        ContentValues args = new ContentValues();
        args.put(DATAUSAGE, datausageupadte1);
        args.put(NO_OF_CLICKS,clicks);
        args.put(SECONDS_USED,usedsec);
        return String.valueOf(db.update(TABLE_NAME, args, WEBSITE +"=\"" + cid + "\"", null) > 0);
    }



    public Cursor returnData()
    {
        return db.query(TABLE_NAME, new String[]{DEVID, DATANOW, WEBSITE, DATAUSAGE}, null, null, null, null, null);
    }
    public List getAllDatamonth(String date){
        System.out.println(date);
        SQLiteDatabase db =dbhelper.getWritableDatabase();
        String[] columns={DataHandler.WEBSITE,DataHandler.DATAUSAGE};
        Cursor cursor = db.query(DataHandler.TABLE_NAME, columns, MONTHYEAR + "=?", new String[]{date},null,null,null,null);

        while(cursor.moveToNext()){
            String cid = cursor.getString(0);
            String datausage = cursor.getString(1);
            String newdata = cid+"="+datausage;
            list.add(newdata);
        }

        return list;
    }
    public List getALLData(String devid){
        System.out.println(devid);
        SQLiteDatabase db =dbhelper.getWritableDatabase();
        String[] columns={DataHandler.DATANOW,DataHandler.WEBSITE,DataHandler.DATAUSAGE,DataHandler.NO_OF_CLICKS,DataHandler.SECONDS_USED};
        Cursor cursor = db.query(DataHandler.TABLE_NAME,columns,DEVID+"=?",new String[]{devid},null,null,null,null);
        StringBuffer buffer = new StringBuffer();
        while(cursor.moveToNext()){
            String datenow = cursor.getString(0);
            String websit1 = cursor.getString(1);
            String datausage = cursor.getString(2);
            String clicks = cursor.getString(3);
            String seconds = cursor.getString(4);
            String totaldata = datenow+"="+websit1+"="+datausage+"="+clicks+"="+seconds;
            listmonth.add(totaldata);

        }
        return listmonth;
    }

    public List getAllDatayearonly(String date){
        System.out.println(date);
        SQLiteDatabase db =dbhelper.getWritableDatabase();
        String[] columns={DataHandler.WEBSITE,DataHandler.DATAUSAGE};
        Cursor cursor = db.query(DataHandler.TABLE_NAME,columns,YEAR+"=?",new String[]{date},null,null,null,null);
        StringBuffer buffer = new StringBuffer();
        while(cursor.moveToNext()){
            String cid = cursor.getString(0);
            String datausage = cursor.getString(1);
            String newdata = cid+"="+datausage;
            listmonth.add(newdata);

        }
        return listmonth;
    }
    public List getAllDatamonthonly(String date){
        System.out.println(date);
        SQLiteDatabase db =dbhelper.getWritableDatabase();
        String[] columns={DataHandler.WEBSITE,DataHandler.DATAUSAGE};
        Cursor cursor = db.query(DataHandler.TABLE_NAME,columns,MONTH+"=?",new String[]{date},null,null,null,null);
        StringBuffer buffer = new StringBuffer();
        while(cursor.moveToNext()){
            String cid = cursor.getString(0);
            String datausage = cursor.getString(1);
            String newdata = cid+"="+datausage;
            listmonth.add(newdata);

        }
        return listmonth;
    }

}
