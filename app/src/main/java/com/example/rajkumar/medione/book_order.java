package com.example.rajkumar.medione;

        import android.app.Fragment;
        import android.content.Intent;
        import android.icu.text.DateFormat;
        import android.icu.text.SimpleDateFormat;
        import android.icu.util.TimeZone;
        import android.os.Build;
        import android.os.Bundle;
        import android.support.annotation.RequiresApi;
        import android.support.v4.app.FragmentActivity;
        import android.text.Html;
        import android.util.Log;
        import android.view.LayoutInflater;
        import android.view.View;
        import android.view.ViewGroup;
        import android.widget.Button;
        import android.widget.ImageButton;
        import android.widget.TextView;
        import android.widget.Toast;

        import com.android.volley.NetworkError;
        import com.android.volley.NoConnectionError;
        import com.android.volley.ParseError;
        import com.android.volley.Request;
        import com.android.volley.RequestQueue;
        import com.android.volley.Response;
        import com.android.volley.ServerError;
        import com.android.volley.TimeoutError;
        import com.android.volley.VolleyError;
        import com.android.volley.toolbox.StringRequest;
        import com.android.volley.toolbox.Volley;
        import android.support.v7.app.AppCompatActivity;
        import android.os.Bundle;
        import android.view.Menu;
        import android.view.MenuItem;
        import android.view.View;
        import android.widget.Button;
        import android.widget.EditText;
        import android.widget.Toast;

        import com.android.volley.AuthFailureError;
        import com.android.volley.Request;
        import com.android.volley.RequestQueue;
        import com.android.volley.Response;
        import com.android.volley.VolleyError;
        import com.android.volley.toolbox.JsonObjectRequest;
        import com.android.volley.toolbox.StringRequest;
        import com.android.volley.toolbox.Volley;

        import org.json.JSONException;
        import org.json.JSONObject;

        import java.text.ParseException;
        import java.util.Calendar;
        import java.util.Date;
        import java.util.HashMap;
        import java.util.Map;
/**
 * Created by Raj Kumar on 2/22/2017.
 */

public class book_order extends Fragment
{
    String name,book,useremail,medid,currentDateandTime,price;
    View view;
    private  static  final String order_id = "http://medione.esy.es/orderid.php";
    public static final String KEY_MEDID = "medid";
    public static final String KEY_USEREMAIL = "useremail";
    public static final String KEY_RETAILID = "retailid";
    public static final String KEY_DATETIME = "datetime";
    public static final String KEY_STATUS = "status";
    TextView med,order_confirm,medi;
    TextView price1,subprice,amountpay,qtyno;
    String formattedDate;
    int quantiy;
    Button confirm;
    ImageButton plus,minus;






    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        view = inflater.inflate(R.layout.activity_book_order, container, false);

        name = getArguments().getString("name");
        book=getArguments().getString("book");
        Log.e("book",book);
        useremail=getArguments().getString("username");
        Log.e("username",useremail);
        medid=getArguments().getString("medid");
        Log.e("medid",medid);
        price=getArguments().getString("price");
        price=getArguments().getString("price");
        Log.e("price",price);


        med=(TextView)view.findViewById(R.id.med);
        order_confirm=(TextView)view.findViewById(R.id.order_confirm);
        price1=(TextView)view.findViewById(R.id.price);
        subprice=(TextView)view.findViewById(R.id.subprice);
        amountpay=(TextView)view.findViewById(R.id.amountpay);
        medi=(TextView)view.findViewById(R.id.medi);
        qtyno=(TextView)view.findViewById(R.id.qtyno);
        confirm=(Button)view.findViewById(R.id.confirm);
        plus=(ImageButton)view.findViewById(R.id.plus);
        minus=(ImageButton)view.findViewById(R.id.minus);

        String mad="Medione is a techonlogy platfrom to facilitate transaction of buisness.\nThe product and services" +
                " are offered for sale by the sellers.The user\nwill be responsile for taking the goods from the store.\n" +
                "For details read <u><font color='#0de7bf'>Terms and Condition</font></u>.";
        medi.setText(Html.fromHtml(mad), TextView.BufferType.SPANNABLE);


        med.setText(name);
        price1.setText(price);
        subprice.setText(price);
        amountpay.setText(price);
        quantiy=Integer.parseInt(qtyno.getText().toString());

        plus.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                if(quantiy<10)
                {
                    quantiy=quantiy+1;

                }

                qtyno.setText(String.valueOf(quantiy));
            }
        });


        minus.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                if(quantiy!=1)
                {
                    quantiy=quantiy-1;
                }
                qtyno.setText(String.valueOf(quantiy));
            }
        });

        /*SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss dd-MM-yyyy");
        String currentDateandTime = sdf.format(new Date());*/
        confirm.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                getData();

            }
        });



        return view;
    }

    private void getData()
    {

        StringRequest stringRequest = new StringRequest(Request.Method.POST, order_id,new Response.Listener<String>()
        {
                    @Override
                    public void onResponse(String response)
                    {
                        Log.e("insert",response);
                        if (!response.equals("error"))
                        {



                            order_placed ldf = new order_placed ();
                            Bundle args = new Bundle();
                            args.putString("order", response);
                            ldf.setArguments(args);


                            android.app.FragmentManager fragmentManager=getFragmentManager();

                            fragmentManager.beginTransaction().replace(R.id.content_frame,ldf).addToBackStack("order_placed").commit();

                        }
                        else
                        {
                            Toast.makeText(getActivity().getApplicationContext(),response,Toast.LENGTH_LONG).show();
                        }

                    }
                },
                new Response.ErrorListener() {
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
                }){
            @RequiresApi(api = Build.VERSION_CODES.N)
            @Override
            protected Map<String,String> getParams()
            {
                Map<String,String> params = new HashMap<String, String>();
                params.put(KEY_MEDID,medid);
                params.put(KEY_USEREMAIL,useremail);
                params.put(KEY_RETAILID,book );


                Calendar calender = Calendar.getInstance();

                calender.setTimeZone(java.util.TimeZone.getTimeZone("Asia/Calcutta"));

                java.text.SimpleDateFormat df = new java.text.SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                formattedDate= df.format(calender.getTime());
                params.put(KEY_DATETIME,formattedDate );
                Log.e("tome", formattedDate);


                params.put(KEY_STATUS,"pending" );
                return params;
            }

        };

        RequestQueue requestQueue = Volley.newRequestQueue(getActivity().getApplicationContext());
        requestQueue.add(stringRequest);
    }






           //android.app.FragmentManager fragmentManager=getFragmentManager();
            //fragmentManager.beginTransaction().replace(R.id.content_frame, new searchmed()).addToBackStack("fragBack").commit();




}
