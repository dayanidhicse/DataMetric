package com.example.dayanidhi.datametrics;

import android.content.Intent;
import android.graphics.PointF;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.widget.Toast;

import com.dlazaro66.qrcodereaderview.QRCodeReaderView;
import com.firebase.client.Firebase;

public class JuswebActivity extends AppCompatActivity implements QRCodeReaderView.OnQRCodeReadListener{
    private QRCodeReaderView mydecoderview;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_jusweb);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        mydecoderview = (QRCodeReaderView) findViewById(R.id.qrdecoderview);
        mydecoderview.setOnQRCodeReadListener(JuswebActivity.this);


    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Take appropriate action for each action item click
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    // Called when a QR is decoded
    // "text" : the text encoded in QR
    // "points" : points where QR control points are placed
    private MobileInfo info;
    @Override
    public void onQRCodeRead(String text, PointF[] points) {
        Intent i = new Intent(JuswebActivity.this, HomeActivity.class);
        startActivity(i);
        info=new MobileInfo(getApplicationContext());
        Firebase.setAndroidContext(this);

     Firebase   mBef = new Firebase("https://sizzling-inferno-6307.firebaseio.com/");

                mBef.child(text).child("id").setValue(info.getDeviceid());
        Toast.makeText(getBaseContext(),text, Toast.LENGTH_SHORT).show();

        /*
        Firebase firebase = new Firebase("https://datametrics.firebaseio.com/");
       Firebase mFirebaseRef = new Firebase(firebase.toString()).child(text);
        String Unique_code=text+Settings.Secure.getString(getApplicationContext().getContentResolver(),
                 Settings.Secure.ANDROID_ID);

        mFirebaseRef.child("dd").setValue(text+"dayya");*/
      //  Toast.makeText(getBaseContext(),info.getDeviceid(), Toast.LENGTH_SHORT).show();
        // TX.setText(text+android_id);
    }


    // Called when your device have no camera
    @Override
    public void cameraNotFound() {

    }

    // Called when there's no QR codes in the camera preview image
    @Override
    public void QRCodeNotFoundOnCamImage() {

    }

    @Override
    protected void onResume() {
        super.onResume();
        mydecoderview.getCameraManager().startPreview();
    }

    @Override
    protected void onPause() {
        super.onPause();
        mydecoderview.getCameraManager().stopPreview();
    }

}
