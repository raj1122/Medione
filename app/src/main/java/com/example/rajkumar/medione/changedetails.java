package com.example.rajkumar.medione;

import android.app.Fragment;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.NavigationView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import java.util.HashMap;

import static com.example.rajkumar.medione.login.USER_NAME;

/**
 * Created by Raj Kumar on 3/2/2017.
 */

public class changedetails extends Fragment
{
    private  static  final String url_pass = "http://medione.esy.es/upchangepass.php";
    private  static  final String url_name = "http://medione.esy.es/upchangename.php";
    View view;
    ImageButton change;
    TextView changet,nav_user;
    String pass,name,n,p,chan;
    String USER_NAME;
    public String username,userphone,useremail, userpass;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        view=inflater.inflate(R.layout.changedetails,container,false);
        change=(ImageButton)view.findViewById(R.id.change);
        changet=(TextView)view.findViewById(R.id.changet);

        SharedPreferences sharedPreferences = getActivity().getSharedPreferences(Config.SHARED_PREF_NAME, Context.MODE_PRIVATE);
        USER_NAME  = sharedPreferences.getString(Config.EMAIL_SHARED_PREF,"Not Available");


        username = getArguments().getString("username");
        userphone = getArguments().getString("userphone");
        useremail = getArguments().getString("useremail");
        userpass = getArguments().getString("userpass");


        p = getArguments().getString("p");
        if(p.equals("1"))
        {
            changet.setHint("change password");
            change.setImageResource(R.drawable.changepassword);
        }
        else
        {
            changet.setHint("change name");
            change.setImageResource(R.drawable.changename);

        }

        change.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                if(changet.getText().length()==0)
                {
                    changet.setError("enter credentails");
                }
                else
                {
                    if(p.equals("1"))
                    {
                        pass=changet.getText().toString();
                        if(validatepass())
                        {
                            userpass=pass;

                            changepass(pass,USER_NAME);
                        }
                    }
                    else
                    {
                        Log.e("chanegdeta",userpass);
                        Log.e("chanegname",userphone);
                        Log.e("chanegname",useremail);
                        Log.e("chanegname",username);
                        name=changet.getText().toString();
                        username=name;
                        changename(name,USER_NAME);
                    }
                }



            }
        });


        return view;

    }

    private boolean validatepass()
    {
        if(pass.length()<8)
        {
            changet.setError("password is too short");
            return false;
        }
        return  true;
    }


    private void changepass(final String pass1, String email)
    {
        class RegisterUser extends AsyncTask<String, Void, String>
            {
                ProgressDialog loading;
                RegisterUserClass ruc = new RegisterUserClass();


                @Override
                protected void onPreExecute()
                {
                    super.onPreExecute();
                    loading = ProgressDialog.show(getActivity(), "Please Wait", null, true, true);
                    loading.setCanceledOnTouchOutside(false);
                }


                protected String doInBackground(String... params)
                {

                    HashMap<String, String> data = new HashMap<String, String>();
                    data.put("pass", params[0]);
                    data.put("email", params[1]);
                    String result = ruc.sendPostRequest(url_pass, data);
                    return result;
                }

                protected void onPostExecute(String s)
                {
                    super.onPostExecute(s);
                    loading.dismiss();
                    Log.e("result",s);
                    //Toast.makeText(getActivity().getApplicationContext(),s,Toast.LENGTH_LONG).show();
                   Intent intent=new Intent(getActivity().getApplicationContext(),MainActivity.class);
                    startActivity(intent);

                }
            }

            RegisterUser ru = new RegisterUser();
            ru.execute(pass1,email);

    }



    private void changename(final String name, String email)
    {
        class RegisterUser extends AsyncTask<String, Void, String>
        {
            ProgressDialog loading;
            RegisterUserClass ruc = new RegisterUserClass();


            @Override
            protected void onPreExecute()
            {
                super.onPreExecute();
                loading = ProgressDialog.show(getActivity(), "Please Wait", null, true, true);
                loading.setCanceledOnTouchOutside(false);
            }


            protected String doInBackground(String... params)
            {

                HashMap<String, String> data = new HashMap<String, String>();
                data.put("name", params[0]);
                data.put("email", params[1]);
                String result = ruc.sendPostRequest(url_name, data);
                return result;
            }

            protected void onPostExecute(String s)
            {
                super.onPostExecute(s);
                loading.dismiss();



                if (s.equals("success"))
                {
                    Log.e("changedetails",s);
                    //Toast.makeText(getActivity().getApplicationContext(),"name updated",Toast.LENGTH_LONG).show();

                    android.app.FragmentManager fragmentManager=getFragmentManager();
                    accountdetails ldf = new accountdetails();
                    Bundle args = new Bundle();



                    args.putString("username", username);
                    args.putString("useremail", useremail);
                    args.putString("userpass", userpass);
                    args.putString("userphone", userphone);

                    ldf.setArguments(args);

                    fragmentManager.beginTransaction().replace(R.id.content_frame, ldf).addToBackStack("fragBack").commit();
                }

            }
        }

        RegisterUser ru = new RegisterUser();
        ru.execute(name,email);

    }
}
