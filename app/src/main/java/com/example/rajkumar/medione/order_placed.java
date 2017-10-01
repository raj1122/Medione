package com.example.rajkumar.medione;


import android.os.Bundle;
import android.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;


/**
 * A simple {@link Fragment} subclass.
 */
public class order_placed extends Fragment
{
    View view;
    TextView order;




    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState)
    {

        // Inflate the layout for this fragment
        view= inflater.inflate(R.layout.fragment_order_placed, container, false);
        order=(TextView)view.findViewById(R.id.orderd);
        String orderid = getArguments().getString("order");
        order.setText(orderid);





        /*new Thread(new Runnable()
        {
            @Override
            public void run()
            {
                try
                {
                    Thread.sleep(4000);
                    //android.app.FragmentManager fragmentManager=getFragmentManager();
                    //fragmentManager.beginTransaction().replace(R.id.content_frame,new searchmed()).addToBackStack("fragBack").commit();
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
