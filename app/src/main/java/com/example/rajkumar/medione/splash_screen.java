package com.example.rajkumar.medione;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.widget.ProgressBar;

public class splash_screen extends Activity
{
    ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);

        //progressDialog = ProgressDialog.show(splash_screen.this, "", null, true, true);
        //progressDialog.setCanceledOnTouchOutside(false);

        new Thread(new Runnable()
        {
            @Override
            public void run()
            {
                try
                {

                    Thread.sleep(6000);

                    Intent intent=new Intent(splash_screen.this,login.class);
                    startActivity(intent);
                }
                catch (InterruptedException e)
                {
                    e.printStackTrace();
                }


            }
        }).start();
    }
}
