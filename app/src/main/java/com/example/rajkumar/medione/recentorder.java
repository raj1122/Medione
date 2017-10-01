package com.example.rajkumar.medione;

import android.app.Fragment;
import android.app.FragmentTransaction;
import android.app.ProgressDialog;
import android.content.ClipData;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.TimeZone;



/**
 * Created by Raj Kumar on 2/14/2017.
 */

public class recentorder extends Fragment
{
    ListView listView;
    ProgressDialog loading;
    String username;
    private  static  final String recent_url = "http://medione.esy.es/recentorder.php";
    public static final String KEY_EMAIL = "email";
    public static final String KEY_NAME = "name";
    public static final String KEY_ORDERID = "orderid";
    public static final String KEY_TIME = "time";
    public static final String KEY_CANCLE = "cancle";
    public static final String KEY_ORDERDATE = "datetime";
    public static final String KEY_STATUS = "status";
    public static final String KEY_ORDERTOP = "ordertop";
    public static final String JSON_ARRAY = "result";
    private  static  final String cancel_url = "http://medione.esy.es/cnc.php";
    String orderdate;
    ImageButton bt;

    TextView ordertop;
    String formattedDate;
    int hour;
    int min;

    public static final String[] li={"NO MEDICINES FOUND PLEASE GO BACK AND SEARCH AGAIN"};
    String USER_NAME;

    JSONArray peoples = null;

    ArrayList<HashMap<String, String>> personList;
    View view;






    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        view=inflater.inflate(R.layout.recent_order,container,false);

        listView=(ListView)view.findViewById(R.id.listView);
        personList = new ArrayList<HashMap<String,String>>();

        SharedPreferences sharedPreferences = getActivity().getSharedPreferences(Config.SHARED_PREF_NAME, Context.MODE_PRIVATE);
        USER_NAME  = sharedPreferences.getString(Config.EMAIL_SHARED_PREF,"Not Available");


        userlogin(USER_NAME);

        return view;

    }


    private void userlogin(final String USER)
    {
        class UserLoginClass extends AsyncTask<String,Void,String>
        {
            ProgressDialog loading;
            @Override
            protected void onPreExecute()
            {
                super.onPreExecute();
                //loading = ProgressDialog.show(getActivity().getApplicationContext(),"Please Wait",null,true,true);
                //loading.setCanceledOnTouchOutside(false);
            }


            @Override
            protected String doInBackground(String... params)
            {
                HashMap<String,String> data = new HashMap<>();
                data.put("email",params[0]);

                RegisterUserClass ruc = new RegisterUserClass();

                String result = ruc.sendPostRequest(recent_url,data);
                //Log.e("result",result);
                return result.trim();
            }

            @Override
            protected void onPostExecute(String s)
            {
                super.onPostExecute(s);
               // loading.dismiss();

               showJSON(s);
            }
        }
        UserLoginClass ulc = new UserLoginClass();
        ulc.execute(USER);

    }

    private void showJSON(String response)
    {
        try
        {
            //loading.dismiss();

            if(response.equals("error"))
            {

               // Log.e("error","roor");

                //Toast.makeText(getActivity().getApplicationContext(),"error",Toast.LENGTH_LONG).show();
                android.app.FragmentManager fragmentManager=getFragmentManager();
                fragmentManager.beginTransaction().replace(R.id.content_frame,new no_order_found()).addToBackStack("noorder").commit();

            }
            else
            {
                //Log.e("showjson","json");
                JSONObject jsonObj = new JSONObject(response);
                peoples = jsonObj.getJSONArray(JSON_ARRAY);

                for (int i = 0; i < peoples.length(); i++)
                {
                    JSONObject c = peoples.getJSONObject(i);


                    String name = c.getString(KEY_NAME);
                    String orderid = c.getString(KEY_ORDERID);
                    orderdate= c.getString(KEY_ORDERDATE);
                    String status = c.getString(KEY_STATUS);



                    HashMap<String, String> persons = new HashMap<String, String>();







                    Calendar calender = Calendar.getInstance();
                    calender.setTimeZone(TimeZone.getTimeZone("Asia/Calcutta"));
                    java.text.SimpleDateFormat df = new java.text.SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                    formattedDate= df.format(calender.getTime());


                    boolean jd=diff();
                    //Log.e("cnc", status);
                    if(status.equals("cancle"))
                    {
                        persons.put(KEY_CANCLE, Integer.toString(R.drawable.white));
                        persons.put(KEY_TIME, "order cancle");
                        persons.put(KEY_STATUS, status);
                        persons.put(KEY_ORDERTOP, "Order "+status);
                    }
                    else
                    {
                        if(jd==true)
                        {
                            int lefttime=24-hour;
                            lefttime=lefttime*60;
                            lefttime=lefttime-min;

                            int lefthour=lefttime/60;
                            int leftmin=lefttime%60;
                            //Log.e("lefthour", String.valueOf(lefthour));
                            // Log.e("leftmin", String.valueOf(leftmin));
                            String left=lefthour+":"+leftmin;
                            //Log.e("left", String.valueOf(left));



                            persons.put(KEY_CANCLE, Integer.toString(R.drawable.cancel));
                            persons.put(KEY_TIME, left);
                            persons.put(KEY_STATUS, status);
                            persons.put(KEY_ORDERTOP, "Order "+status);
                        }
                        else
                        {
                            //Log.e("whiet",Integer.toString(R.drawable.white));
                            persons.put(KEY_CANCLE, Integer.toString(R.drawable.white));
                            persons.put(KEY_TIME, "order expire ");
                            persons.put(KEY_STATUS, "expire");
                            persons.put(KEY_ORDERTOP, "Order expire");
                        }
                    }






                    persons.put(KEY_NAME, name);
                    persons.put(KEY_ORDERID, orderid);
                    persons.put(KEY_ORDERDATE, orderdate);
                    personList.add(persons);
                }






                ListAdapter adapter = new SimpleAdapter(getActivity().getApplicationContext(), personList, R.layout.list_layout_recent,
                        new String[]{KEY_ORDERTOP,KEY_CANCLE,KEY_TIME,KEY_ORDERID,KEY_ORDERDATE,KEY_NAME,KEY_STATUS},
                        new int[]{R.id.ordertop,R.id.cancle,R.id.ttime,R.id.textVieworderid,R.id.textVieworderdate,R.id.textViewmedname,R.id.textViewstatus}) {
                    public View getView(int position, View convertView, ViewGroup parent)
                    {


                        // get filled view from SimpleAdapter
                        View itemView=super.getView(position, convertView, parent);
                        // find our button there
                        View cancle=itemView.findViewById(R.id.cancle);



                        final TextView textView = ((TextView) itemView.findViewById(R.id.textVieworderid));


                        cancle.setOnClickListener(new View.OnClickListener()
                        {
                            @Override
                            public void onClick(View v)
                            {

                                View parentView = (View) view.getParent();
                                //TextView textView = ((TextView) v.findViewById(R.id.textVieworderid));
                                String oid=textView.getText().toString();
                                Log.e("text",oid);

                                ImageButton imageButton=(ImageButton)v.findViewById(R.id.cancle);
                                imageButton.getResources();
                                if(imageButton.getDrawable().getConstantState().equals(getActivity().getApplicationContext().getResources().getDrawable(R.drawable.cancel).getConstantState()))
                                {
                                    String c="cancle";
                                    Log.e("ca",c);
                                    cnc(c,USER_NAME,oid);

                                }


                            }
                        });


                        return itemView;
                    }

                };


                listView.setAdapter(adapter);



            }

        }
        catch (JSONException e)
        {


        }
        catch (ParseException e)
        {
            e.printStackTrace();
        }


    }


    boolean diff() throws ParseException
    {

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

        Date dateObj1 = sdf.parse(orderdate);
        Date dateObj2 = sdf.parse(formattedDate);
        long diff = dateObj2.getTime() - dateObj1.getTime();
        double diffInHours = diff / ((double) 1000 * 60 * 60);
        //Log.e("diffInHours", String.valueOf(diffInHours));

        hour=(int)diffInHours;
        //Log.e("Hours", String.valueOf((int)diffInHours));
        double min1= ((diffInHours - (int)diffInHours)*60);
        min= (int) min1;
        //Log.e("hour ", String.valueOf(hour));
        //Log.e("Min ", String.valueOf(min));
        //Log.e("Minutes ", String.valueOf((diffInHours - (int)diffInHours)*60));

        if(diffInHours>24)
        {
            return false;
        }


        return true;
    }

    private void cnc(final String cnc,final  String USER_NAME,final String oid)
    {
        class UserLoginClass extends AsyncTask<String,Void,String>
        {
            ProgressDialog loading;
            @Override
            protected void onPreExecute()
            {
                super.onPreExecute();
                //loading = ProgressDialog.show(getActivity().getApplicationContext(),"Please Wait",null,true,true);
                //loading.setCanceledOnTouchOutside(false);
            }


            @Override
            protected String doInBackground(String... params)
            {
                HashMap<String,String> data = new HashMap<>();
                data.put("cnc",params[0]);
                data.put("useremail",params[1]);
                data.put("oid",params[2]);


                RegisterUserClass ruc = new RegisterUserClass();

                String result = ruc.sendPostRequest(cancel_url,data);
                //Log.e("result",result);
                return result.trim();
            }

            @Override
            protected void onPostExecute(String s)
            {
                super.onPostExecute(s);
                //loading.dismiss();
                //Log.e("error login",s);
                if(s.equalsIgnoreCase("success"))
                {


                    Toast.makeText(getActivity().getApplicationContext(),"Your order has been cancled",Toast.LENGTH_LONG).show();

                    //to refresh the fragment
                    FragmentTransaction ft = getFragmentManager().beginTransaction();
                    ft.detach(recentorder.this).attach(recentorder.this).commit();

                }
                else
                {
                    Toast.makeText(getActivity().getApplicationContext(),s,Toast.LENGTH_LONG).show();
                }
            }
        }
        UserLoginClass ulc = new UserLoginClass();
        ulc.execute(cnc,USER_NAME,oid);

    }



}
