package com.example.rajkumar.medione;


import android.content.ComponentName;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.net.Uri;
import android.os.Bundle;
import android.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;
import android.content.ComponentName;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import java.util.List;


import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class help extends Fragment
{


    public help() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState)
    {
        Intent myIntent = new Intent(Intent.ACTION_SEND);

        PackageManager pm = getActivity().getPackageManager();
        Intent tempIntent = new Intent(Intent.ACTION_SEND);
        tempIntent.setType("*/*");
        List<ResolveInfo> resInfo = pm.queryIntentActivities(tempIntent, 0);
        for (int i = 0; i < resInfo.size(); i++)
        {
            ResolveInfo ri = resInfo.get(i);
            if (ri.activityInfo.packageName.contains("android.gm"))
            {

                String[] TO = {"arialstarraj@gmail.com"};
                String[] CC = {"1415026@kiit.ac.in"};
                myIntent.setComponent(new ComponentName(ri.activityInfo.packageName, ri.activityInfo.name));
                myIntent.setAction(Intent.ACTION_SEND);
                myIntent.putExtra(Intent.EXTRA_EMAIL, TO);
                myIntent.putExtra(Intent.EXTRA_CC, CC);
                myIntent.putExtra(Intent.EXTRA_SUBJECT, "Feedback for medione");
                myIntent.setType("text/plain");


                myIntent.putExtra(Intent.EXTRA_STREAM, Uri.parse("uri://your/uri/string"));
            }
        }

        startActivity(myIntent);
        getActivity().finish();


        return getView();
    }



}
