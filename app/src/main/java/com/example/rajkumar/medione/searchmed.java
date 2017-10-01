package com.example.rajkumar.medione;

import android.app.Fragment;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;



/**
 * Created by Raj Kumar on 2/28/2017.
 */

public class searchmed extends Fragment
{
    View view;
    Button search;
    String USER_NAME;
    EditText searchmed;


    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        view = inflater.inflate(R.layout.searchmed, container, false);
        search=(Button)view.findViewById(R.id.search);
        searchmed=(EditText)view.findViewById(R.id.searchmed);

        SharedPreferences sharedPreferences = getActivity().getSharedPreferences(Config.SHARED_PREF_NAME, Context.MODE_PRIVATE);
        USER_NAME  = sharedPreferences.getString(Config.EMAIL_SHARED_PREF,"Not Available");
        Log.e("USERNAME searchmed",USER_NAME);

        search.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {

                String medname=searchmed.getText().toString();
                if(medname.length()==0)
                {
                    searchmed.setError("enter the medicine name");
                }
                else
                {


                    searchm ldf = new searchm ();
                    Bundle args = new Bundle();
                    args.putString("medname", medname);
                    args.putString("USER_NAME", USER_NAME);
                    ldf.setArguments(args);


                    android.app.FragmentManager fragmentManager=getFragmentManager();

                    fragmentManager.beginTransaction().replace(R.id.content_frame,ldf).addToBackStack("searchm").commit();

                }





            }
        });
        return view;
    }





}
