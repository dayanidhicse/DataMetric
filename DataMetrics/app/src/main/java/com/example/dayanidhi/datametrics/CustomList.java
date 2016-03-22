package com.example.dayanidhi.datametrics;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import java.util.ArrayList;

public class CustomList extends ArrayAdapter<String>{
	
private final Activity context;
private final String[] datausage;
private final ArrayList<String> website=new ArrayList<>();
public CustomList(Activity context,
                  String[] web, ArrayList<String> imageId) {
super(context, R.layout.list_single, web);
this.context = context;
this.datausage = web;
    for (int i=0;i<imageId.size();i++)
        website.add(imageId.get(i));
}
@Override
public View getView(int position, View view, ViewGroup parent) {

LayoutInflater inflater = context.getLayoutInflater();
View rowView= inflater.inflate(R.layout.list_single, null, true);
    if(datausage[position]!=null&&website.get(position)!=null)
    {

        TextView txtTitle = (TextView) rowView.findViewById(R.id.txt);
        TextView imageView = (TextView) rowView.findViewById(R.id.img);

        txtTitle.setText(datausage[position]+" "+"kb");
        imageView.setText(website.get(position));
        return rowView;
    }

//imageView.setImageResource(imageId[position]);
return rowView;
}
}