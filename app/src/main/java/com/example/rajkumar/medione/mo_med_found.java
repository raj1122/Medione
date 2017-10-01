package com.example.rajkumar.medione;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

/**
 * Created by Raj Kumar on 3/6/2017.
 */

public class mo_med_found extends Fragment
{
    String medname;
    TextView medi;
    View view;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        view=inflater.inflate(R.layout.mo_med_found,container,false);
        medname = getArguments().getString("medname");
        String retailer = getArguments().getString("retailer");
        Log.e("medname",medname);
        medi=(TextView)view.findViewById(R.id.med);
        if (retailer.equals("1"))
        {
            medi.setText("We have nothing to show for "+"'"+medname+"'."+"\n\t"+"Try a different Keyword");
        }
        else
        {
            medi.setText("No Retailer available for  "+"'"+medname+"'."+"\n\t"+"Try a different Keyword");
        }

        new Thread(new Runnable()
        {
            @Override
            public void run()
            {
                try
                {
                    Thread.sleep(4000);
                    android.app.FragmentManager fragmentManager=getFragmentManager();
                    fragmentManager.beginTransaction().replace(R.id.content_frame,new searchmed()).addToBackStack("fragBack").commit();
                }
                catch (InterruptedException e)
                {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
            }
        }).start();

        return view;

    }
}
