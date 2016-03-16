package com.example.dayanidhi.datamatric;

import android.app.Activity;
import android.content.Context;
import android.provider.Settings;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class CustomList extends ArrayAdapter<String>{


    private final Activity context;
private final String[] web;
private final String[] imageId;
public CustomList(Activity context,
String[] web, String[] imageId) {
super(context, R.layout.list_single, web);
this.context = context;
this.web = web;
this.imageId = imageId;

}
@Override
public View getView(int position, View view, ViewGroup parent) {

LayoutInflater inflater = context.getLayoutInflater();
View rowView= inflater.inflate(R.layout.list_single, null, true);
    if(web[position]!=null&&imageId[position]!=null)
    {

TextView txtTitle = (TextView) rowView.findViewById(R.id.txt);

TextView imageView = (TextView) rowView.findViewById(R.id.img);

        txtTitle.setText(web[position]+" "+"kb");
        imageView.setText(imageId[position]);
        return rowView;
    }

//imageView.setImageResource(imageId[position]);
return rowView;
}


}