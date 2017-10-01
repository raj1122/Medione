package com.example.rajkumar.medione;

import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.Html;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * Created by Raj Kumar on 2/14/2017.
 */

public class accountdetails extends Fragment
{
    View view;

    String username,userphone,useremail, userpass,accntdetails;
    TextView txtname,txtemail,txtpass,txtphone,acdetails;
    ImageView imagename,imagepass;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        view=inflater.inflate(R.layout.account_details,container,false);


         username = getArguments().getString("username");
         userphone = getArguments().getString("userphone");
         useremail = getArguments().getString("useremail");
         userpass = getArguments().getString("userpass");




        acdetails=(TextView)view.findViewById(R.id.textView7);
        txtname=(TextView)view.findViewById(R.id.txtname);
        txtpass=(TextView)view.findViewById(R.id.txtpass);
        txtemail=(TextView)view.findViewById(R.id.txtemail);
        txtphone=(TextView)view.findViewById(R.id.txtphone);
        imagename=(ImageView)view.findViewById(R.id.imagename);
        imagepass=(ImageView)view.findViewById(R.id.imagepass);


        accntdetails="<u><b>Account details</b></u>";
        acdetails.setText(Html.fromHtml(accntdetails));



        txtname.setText(username);
        txtpass.setText(userpass);
        txtemail.setText(useremail);
        txtphone.setText(userphone);

        imagepass.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                Log.e("dsta","oass");
                android.app.FragmentManager fragmentManager=getFragmentManager();
                changedetails ldf = new changedetails();
                Bundle args = new Bundle();
                String pass=txtpass.getText().toString();


                args.putString("p", "1");

                ldf.setArguments(args);

                fragmentManager.beginTransaction().replace(R.id.content_frame, ldf).addToBackStack("fragBack").commit();
            }
        });


        imagename.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                Log.e("name","name");
                android.app.FragmentManager fragmentManager=getFragmentManager();
                changedetails ldf = new changedetails();
                Bundle args = new Bundle();
                String name=txtname.getText().toString();

                args.putString("p", "0");
                args.putString("username", username);
                args.putString("useremail", useremail);
                args.putString("userpass", userpass);
                args.putString("userphone", userphone);

                ldf.setArguments(args);

                fragmentManager.beginTransaction().replace(R.id.content_frame, ldf).addToBackStack("fragBack").commit();

            }
        });
        return view;

    }


}
