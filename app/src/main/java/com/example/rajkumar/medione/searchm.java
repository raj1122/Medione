package com.example.rajkumar.medione;

import android.app.Fragment;
import android.app.FragmentManager;
import android.app.ProgressDialog;


import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
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
import android.widget.ImageButton;
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
import static com.example.rajkumar.medione.login.USER_NAME;

public class searchm extends Fragment
{

    private ListView listView;
    private ProgressDialog loading;
    String username;
    private  static  final String search_url = "http://medione.esy.es/search1.php?name=";
    public static final String KEY_NAME = "name";
    public static final String KEY_PRICE = "price";
    public static final String KEY_ID = "medid";
    public static final String KEY_AVAIL = "avail";
    public static final String KEY_LOCATION = "location";
    public static final String KEY_BOOK = "book";
    public static final String JSON_ARRAY = "result";
    public static final String[] li={"NO MEDICINES FOUND PLEASE GO BACK AND SEARCH AGAIN"};
    String USER_NAME;

    JSONArray peoples = null;

    ArrayList<HashMap<String, String>> personList;



    View view;
    String medname;
    private Boolean exit = false;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        view=inflater.inflate(R.layout.activity_searchm,container,false);
        listView=(ListView)view.findViewById(R.id.listView);
        personList = new ArrayList<HashMap<String,String>>();

        getData();

        return view;

    }




    private void getData()
    {

        SharedPreferences sharedPreferences = getActivity().getSharedPreferences(Config.SHARED_PREF_NAME, Context.MODE_PRIVATE);
        USER_NAME  = sharedPreferences.getString(Config.EMAIL_SHARED_PREF,"Not Available");
        Log.e("USERNAME",USER_NAME);
        loading = ProgressDialog.show(getActivity(), "Please Wait..Medicines are loading", null, true, true);
        loading.setCanceledOnTouchOutside(false);


        medname = getArguments().getString("medname");
        username = getArguments().getString("USER_NAME");
        Log.e("username",username);



        String url =search_url+medname;

        StringRequest stringRequest = new StringRequest(url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response)
            {
                //loading.dismiss();
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
                            Toast.makeText(getActivity().getApplicationContext(),message,Toast.LENGTH_LONG).show();
                        }
                        else if (error instanceof ServerError)
                        {
                            message = "The server could not be found. Please try again after some time!!";
                            Toast.makeText(getActivity().getApplicationContext(),message,Toast.LENGTH_LONG).show();
                        }
                        else if (error instanceof AuthFailureError)
                        {
                            message = "Cannot connect to Internet...Please check your connection!";
                            Toast.makeText(getActivity().getApplicationContext(),message,Toast.LENGTH_LONG).show();
                        }
                        else if (error instanceof ParseError)
                        {
                            message = "Parsing error! Please try again after some time!!";
                            Toast.makeText(getActivity().getApplicationContext(),message,Toast.LENGTH_LONG).show();
                        }
                        else if (error instanceof NoConnectionError)
                        {
                            message = "Cannot connect to Internet...Please check your connection!";
                            Toast.makeText(getActivity().getApplicationContext(),message,Toast.LENGTH_LONG).show();
                        }
                        else if (error instanceof TimeoutError)
                        {
                            message = "Connection TimeOut! Please check your internet connection.";
                            Toast.makeText(getActivity().getApplicationContext(),message,Toast.LENGTH_LONG).show();
                        }
                        else
                        {
                            message = "Connection TimeOut! Please check your internet connection.";
                            Toast.makeText(getActivity().getApplicationContext(),message,Toast.LENGTH_LONG).show();
                        }
                    }
                });

        RequestQueue requestQueue = Volley.newRequestQueue(getActivity().getApplicationContext());
        requestQueue.add(stringRequest);
    }

    private void showJSON(String response)
    {
        try
        {
            loading.dismiss();

            if(response.equals("error"))
            {

                mo_med_found ldf = new mo_med_found ();
                Bundle args = new Bundle();
                args.putString("retailer", "1");
                args.putString("medname", medname);
                ldf.setArguments(args);

                android.app.FragmentManager fragmentManager=getFragmentManager();
                fragmentManager.beginTransaction().replace(R.id.content_frame,ldf).addToBackStack("fragBack").commit();

            }
            else
            {
                JSONObject jsonObj = new JSONObject(response);
                peoples = jsonObj.getJSONArray(JSON_ARRAY);

                for (int i = 0; i < peoples.length(); i++)
                {
                    JSONObject c = peoples.getJSONObject(i);
                    String name = c.getString(KEY_NAME);
                    String price = c.getString(KEY_PRICE);
                    String avail = c.getString(KEY_AVAIL);
                    String book = c.getString(KEY_BOOK);
                    String medid = c.getString(KEY_ID);
                    price="₹"+price;
                    //cntr+alt+4 for indian rupee
                    HashMap<String, String> persons = new HashMap<String, String>();


                    if(!avail.equals("null"))
                    {
                        persons.put(KEY_NAME, name);
                        persons.put(KEY_PRICE, price);
                        persons.put(KEY_AVAIL, avail);
                        persons.put(KEY_BOOK, book);
                        persons.put(KEY_ID, medid);
                        personList.add(persons);


                    }



                }

                if(personList.isEmpty())
                {
                    Log.e("empt","Search");
                    mo_med_found ldf = new mo_med_found ();
                    Bundle args = new Bundle();
                    args.putString("medname", medname);
                    args.putString("retailer", "no retailer available");
                    ldf.setArguments(args);

                    android.app.FragmentManager fragmentManager=getFragmentManager();
                    fragmentManager.beginTransaction().replace(R.id.content_frame,ldf).addToBackStack("fragBack").commit();
                }
                ListAdapter adapter = new SimpleAdapter(getActivity().getApplicationContext(), personList, R.layout.list_view_layout,
                        new String[]{KEY_ID,KEY_BOOK,KEY_NAME,KEY_PRICE,KEY_AVAIL},
                        new int[]{R.id.medid,R.id.book,R.id.textViewname, R.id.textViewprice, R.id.textViewavail})
                {
                    public View getView(int position, View convertView, ViewGroup parent)
                    {


                        // get filled view from SimpleAdapter
                        View book=super.getView(position, convertView, parent);
                        // find our button there
                        View button_book=book.findViewById(R.id.textViewbbook);

                        Button mmap=((Button)book.findViewById(R.id.map));


                        final TextView medid = ((TextView) book.findViewById(R.id.medid));

                        final TextView medname = ((TextView) book.findViewById(R.id.textViewname));

                        final TextView medprice = ((TextView) book.findViewById(R.id.textViewprice));

                        final TextView medbook = ((TextView) book.findViewById(R.id.book));



                        button_book.setOnClickListener(new View.OnClickListener()
                        {
                            @Override
                            public void onClick(View v)
                            {
                                String booke=medbook.getText().toString();
                                String namess=medname.getText().toString();
                                String medidi=medid.getText().toString();
                                String pricii=medprice.getText().toString();

                                book_order ldf = new book_order ();
                                Bundle args = new Bundle();
                                args.putString("name", namess);
                                args.putString("book", booke);
                                args.putString("username", username);
                                args.putString("medid", medidi);
                                args.putString("price", pricii);
                                ldf.setArguments(args);

                                android.app.FragmentManager fragmentManager=getFragmentManager();
                                fragmentManager.beginTransaction().replace(R.id.content_frame,ldf).addToBackStack("xyz").commit();


                            }
                        });

                        mmap.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v)
                            {
                                Intent i=new Intent(getActivity().getApplicationContext(),MapsActivity.class);
                                startActivity(i);
                            }
                        });

                        return book;
                    }


                };

                listView.setAdapter(adapter);
                /*listView.setOnItemClickListener(new AdapterView.OnItemClickListener()
                {
                    @RequiresApi(api = Build.VERSION_CODES.M)
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id)
                    {
                        HashMap<String,String> pers = new HashMap<String,String>();
                        pers=(HashMap<String, String>)  parent.getItemAtPosition(position);
                        String booke=pers.get("book");
                        String namess=pers.get("name");
                        String medidi=pers.get("medid");
                        String pricii=pers.get("price");
                        Log.e("price",pricii);

                        book_order ldf = new book_order ();
                        Bundle args = new Bundle();
                        args.putString("name", namess);
                        args.putString("book", booke);
                        args.putString("username", username);
                        args.putString("medid", medidi);
                        args.putString("price", pricii);
                        ldf.setArguments(args);

                        android.app.FragmentManager fragmentManager=getFragmentManager();
                        //fragmentManager.beginTransaction().replace(R.id.content_frame,ldf).addToBackStack("fragBack").commit();
                        fragmentManager.beginTransaction().replace(R.id.content_frame,ldf).addToBackStack("xyz").commit();
                    }
                });*/
            }

        }
        catch (JSONException e)
        {
            e.printStackTrace();
        }


    }




    public void onBackPressed()
    {
        if (exit)
        {
            onBackPressed();
            return;
        }
    }

    @Override
    public void onDestroy()
    {
        super.onDestroy();


    }
}
