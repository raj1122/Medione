package com.example.rajkumar.medione;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;



public class no_order_found extends Fragment
{





    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState)
    {
        View view;
        // Inflate the layout for this fragment
        view=inflater.inflate(R.layout.fragment_no_order_found, container, false);

        /*new Thread(new Runnable()
        {
            @Override
            public void run()
            {
                try
                {
                    Thread.sleep(3000);
                    android.app.FragmentManager fragmentManager=getFragmentManager();
                    fragmentManager.beginTransaction().replace(R.id.content_frame,new searchmed()).addToBackStack("fragBack").commit();
                }
                catch (InterruptedException e)
                {
                    e.printStackTrace();
                }


            }
        }).start();*/

        return  view;
    }

}
