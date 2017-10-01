package com.example.rajkumar.medione;


import android.app.Fragment;
import android.app.FragmentManager;
import android.app.ProgressDialog;
import android.graphics.drawable.ColorDrawable;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.v7.app.ActionBar;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.design.internal.NavigationMenuPresenter;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.NetworkError;
import com.android.volley.NoConnectionError;
import com.android.volley.ParseError;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.ServerError;
import com.android.volley.TimeoutError;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;

import static android.R.id.list;
import static com.example.rajkumar.medione.R.id.book;
import static com.example.rajkumar.medione.R.id.header_email;
import static com.example.rajkumar.medione.R.id.listView;
import static com.example.rajkumar.medione.searchm.JSON_ARRAY;
import static com.example.rajkumar.medione.searchm.li;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;

import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.ActionBar;
import android.util.Log;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;

import java.util.ArrayList;
import java.util.HashMap;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener
{


    String USER_NAME;
    ProgressDialog loading;
    public static boolean isMainActivityShown ;
    private boolean isStartup = true;
    String medname;
    String username,userphone,userpass;
    private  static  final String search_url = "http://medione.esy.es/useradata.php?email=";
    public static final String KEY_NAME = "name";
    public static final String KEY_PHONE = "phone";
    public static final String KEY_PASS = "pass";

    TextView nav_user,nav_email;


    JSONArray peoples = null;

    ArrayList<HashMap<String, String>> personList;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);


        setSupportActionBar(toolbar);

        toolbar.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {

                android.app.FragmentManager fragmentManager=getFragmentManager();
                fragmentManager.beginTransaction().replace(R.id.content_frame, new searchmed()).addToBackStack("fragBack").commit();
            }
        });


        getSupportActionBar().setDisplayShowTitleEnabled(false);

        loading = ProgressDialog.show(MainActivity.this, "Profile is loading...please wait", null, true, true);
        loading.setCanceledOnTouchOutside(false);
        isMainActivityShown=true;


        SharedPreferences sharedPreferences = getSharedPreferences(Config.SHARED_PREF_NAME, Context.MODE_PRIVATE);
        USER_NAME  = sharedPreferences.getString(Config.EMAIL_SHARED_PREF,"Not Available");

        Log.e("shared main",USER_NAME);

        Log.e("back oncreate", String.valueOf(getFragmentManager().getBackStackEntryCount()));

        //to set navigatiom header text
        NavigationView navigationView2 = (NavigationView) findViewById(R.id.nav_view);

        View hView =  navigationView2.getHeaderView(0);
        nav_user = (TextView)hView.findViewById(R.id.header_text);
        nav_email = (TextView)hView.findViewById(R.id.header_email);
        nav_email.setText(USER_NAME);





        getData();





        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView2.setItemIconTintList(null);//for showing navigation menu in navigation drawer
        navigationView.setNavigationItemSelectedListener(this);

    }

    private void getData()
    {


        //loading = ProgressDialog.show(getActivity().getApplicationContext(),"Please wait...","Fetching...",false,false);

        String url =search_url+USER_NAME;

        StringRequest stringRequest = new StringRequest(url, new Response.Listener<String>()
        {
            @Override
            public void onResponse(String response)
            {

                showJSON(response);
            }
        },
                new Response.ErrorListener()
                {
                    @Override
                    public void onErrorResponse(VolleyError error)
                    {
                        String message = null;
                        if (error instanceof NetworkError)
                        {
                            message = "Cannot connect to Internet...Please check your connection!";
                            Toast.makeText(MainActivity.this,message,Toast.LENGTH_LONG).show();
                        }
                        else if (error instanceof ServerError)
                        {
                            message = "The server could not be found. Please try again after some time!!";
                            Toast.makeText(MainActivity.this,message,Toast.LENGTH_LONG).show();
                        }
                        else if (error instanceof AuthFailureError)
                        {
                            message = "Cannot connect to Internet...Please check your connection!";
                            Toast.makeText(MainActivity.this,message,Toast.LENGTH_LONG).show();
                        }
                        else if (error instanceof ParseError)
                        {
                            message = "Parsing error! Please try again after some time!!";
                            Toast.makeText(MainActivity.this,message,Toast.LENGTH_LONG).show();
                        }
                        else if (error instanceof NoConnectionError)
                        {
                            message = "Cannot connect to Internet...Please check your connection!";
                            Toast.makeText(MainActivity.this,message,Toast.LENGTH_LONG).show();
                        }
                        else if (error instanceof TimeoutError)
                        {
                            message = "Connection TimeOut! Please check your internet connection.";
                            Toast.makeText(MainActivity.this,message,Toast.LENGTH_LONG).show();
                        }
                        else
                        {
                            message = "Connection TimeOut! Please check your internet connection.";
                            Toast.makeText(MainActivity.this,message,Toast.LENGTH_LONG).show();
                        }

                    }
                });

        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
        requestQueue.add(stringRequest);
    }

    private void showJSON(String response)
    {
        try
        {

            if(response.equals("error"))
            {


                Log.e("error","error");

            }
            else
            {
                JSONObject jsonObj = new JSONObject(response);
                peoples = jsonObj.getJSONArray(JSON_ARRAY);

                for (int i = 0; i < peoples.length(); i++)
                {
                    JSONObject c = peoples.getJSONObject(i);
                    username = c.getString(KEY_NAME);
                    userphone = c.getString(KEY_PHONE);
                    userpass = c.getString(KEY_PASS);

                }
                Log.e("name",username);
                Log.e("userphone",userphone);
                Log.e("userpass",userpass);

                nav_user.setText(username);

                android.app.FragmentManager fragmentManager=getFragmentManager();
                fragmentManager.beginTransaction().replace(R.id.content_frame, new searchmed()).addToBackStack("fragBack").commit();
                loading.dismiss();





            }

        }
        catch (JSONException e)
        {
            e.printStackTrace();
        }


    }


    @Override
    public void onBackPressed()
    {
        //Log.e("drawer","layout");
       // Log.e("back", String.valueOf(getFragmentManager().getBackStackEntryCount()));
        if (getFragmentManager().getBackStackEntryCount() == 1)
        {
            //Log.e("back if","pressed");
            finish();
        }

        FragmentManager fm = MainActivity.this.getFragmentManager();
        if(getFragmentManager().getBackStackEntryAt(fm.getBackStackEntryCount() - 1).getName().equals("xyz"))
        {
            Log.e("frag ","bakc");
            getFragmentManager().popBackStack("searchm",FragmentManager.POP_BACK_STACK_INCLUSIVE);

            android.app.FragmentManager fragmentManager=getFragmentManager();
            fragmentManager.beginTransaction().replace(R.id.content_frame, new searchmed()).addToBackStack("fragBack").commit();
        }

        if(getFragmentManager().getBackStackEntryAt(fm.getBackStackEntryCount() - 1).getName().equals("noorder"))
        {
            Log.e("no order ","bakc");
            getFragmentManager().popBackStack("recentorder",FragmentManager.POP_BACK_STACK_INCLUSIVE);

            android.app.FragmentManager fragmentManager=getFragmentManager();
            fragmentManager.beginTransaction().replace(R.id.content_frame, new searchmed()).addToBackStack("fragBack").commit();

        }


        if(getFragmentManager().getBackStackEntryAt(fm.getBackStackEntryCount() - 1).getName().equals("order_placed"))
        {
            Log.e("order_placed ","back");
            //getFragmentManager().popBackStack("xyz",FragmentManager.POP_BACK_STACK_INCLUSIVE);
            getFragmentManager().popBackStack("searchm",FragmentManager.POP_BACK_STACK_INCLUSIVE);

            android.app.FragmentManager fragmentManager=getFragmentManager();
            fragmentManager.beginTransaction().replace(R.id.content_frame, new searchmed()).addToBackStack("fragBack").commit();

        }





        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START))
        {

            drawer.closeDrawer(GravityCompat.START);
        }

        else
        {

            super.onBackPressed();
        }
    }

   /* @Override
    public boolean onCreateOptionsMenu(Menu menu)
    {
    // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }*/



    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item)
    {
        // Handle navigation view item clicks here.
        int id = item.getItemId();
        android.app.FragmentManager fragmentManager=getFragmentManager();


        Log.e("id", String.valueOf(id));
        if (id == R.id.nav_alarm)
        {
            Log.e("inside nav_alarm","");

            fragmentManager.beginTransaction().replace(R.id.content_frame, new alarm()).addToBackStack("fragBack").commit();

            //fragmentManager.beginTransaction().replace(R.id.content_frame,new recentorder()).commit();
            // Handle the camera action
        }


        else if (id == R.id.nav_recent_order)
        {


            fragmentManager.beginTransaction().replace(R.id.content_frame, new recentorder()).addToBackStack("recentorder").commit();
            //fragmentManager.beginTransaction().replace(R.id.content_frame,new recentorder()).commit();
            // Handle the camera action
        }
        else if (id == R.id.nav_account_details)
        {

            accountdetails ldf = new accountdetails ();
            Bundle args = new Bundle();
            args.putString("username", username);
            args.putString("useremail", USER_NAME);
            args.putString("userphone", userphone);
            args.putString("userpass",userpass);
            ldf.setArguments(args);

            fragmentManager.beginTransaction().replace(R.id.content_frame, ldf).addToBackStack("fragBack").commit();
            //fragmentManager.beginTransaction().replace(R.id.content_frame,new accountdetails()).commit();
        }
        else if(id==R.id.nav_parent)
        {
            fragmentManager.beginTransaction().replace(R.id.content_frame, new parent()).addToBackStack("fragBack").commit();
        }
        else if(id==R.id.nav_about)
        {
            fragmentManager.beginTransaction().replace(R.id.content_frame, new about()).addToBackStack("fragBack").commit();

        }
        else if(id==R.id.nav_help)
        {
            fragmentManager.beginTransaction().replace(R.id.content_frame, new help()).addToBackStack("fragBack").commit();
        }
        else if(id==R.id.nav_developer)
        {
            fragmentManager.beginTransaction().replace(R.id.content_frame, new developer()).addToBackStack("fragBack").commit();

        }
        else if(id==R.id.nav_logout)
        {
            fragmentManager.beginTransaction().replace(R.id.content_frame, new logout()).addToBackStack("fragBack").commit();

        }


        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }


}
