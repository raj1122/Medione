package com.example.rajkumar.medione;



import android.support.v7.app.ActionBar;
import android.app.Activity;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;
import org.json.JSONObject;
import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;

import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;

public class register extends AppCompatActivity
{

    private EditText name, pass, repass, email, phone;

    private  static  final String url = "http://medione.esy.es/register.php";




    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        getSupportActionBar().setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
        getSupportActionBar().setCustomView(R.layout.actionbar);

        name = (EditText) findViewById(R.id.name);
        pass = (EditText) findViewById(R.id.pass);
        repass = (EditText) findViewById(R.id.repass);
        email = (EditText) findViewById(R.id.email);
        phone = (EditText) findViewById(R.id.phone);
    }


    public void reg(View v)
    {
        if (validate())
        {
            Log.e("succesfeul","validate");


            String nam = name.getText().toString().trim();
            String pas = pass.getText().toString().trim();
            String emai = email.getText().toString().trim();
            String phon = phone.getText().toString().trim();



            insertToDatabase(nam,pas,emai,phon);
        }
    }


    private void insertToDatabase(String nam, String pas, String emai, String phon)
    {






        class RegisterUser extends AsyncTask<String, Void, String>
        {
            ProgressDialog loading;
            RegisterUserClass ruc = new RegisterUserClass();


            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                loading = ProgressDialog.show(register.this, "Please Wait", null, true, true);
                loading.setCanceledOnTouchOutside(false);
            }



            @Override
            protected String doInBackground(String... params)
            {

                HashMap<String, String> data = new HashMap<String, String>();
                data.put("name", params[0]);
                data.put("pass", params[1]);
                data.put("email", params[2]);
                data.put("phone", params[3]);

                String result = ruc.sendPostRequest(url, data);

                return result;
            }

            @Override
            protected void onPostExecute(String s)
            {
                super.onPostExecute(s);
                loading.dismiss();
                Log.e("post",s);
                s=s.trim();
                if(s.equals("Connected successfullyusername or email already exist"))
                {
                    Toast.makeText(getApplicationContext(), "email already exist...Try a different email", Toast.LENGTH_LONG).show();
                }
                else if(s.length()==0)
                {
                    Toast.makeText(getApplicationContext(), "Internet connection is too slow", Toast.LENGTH_LONG).show();

                }
                else
                {
                    Toast.makeText(getApplicationContext(),"Login using Register email id",Toast.LENGTH_LONG).show();
                    Intent i=new Intent(register.this,login.class);
                    startActivity(i);
                }


            }
        }

        RegisterUser ru = new RegisterUser();
        ru.execute(nam, pas, emai, phon);
    }







    public void login(View v)
    {
        Intent i = new Intent();
        i.setClass(this, login.class);
        startActivity(i);
        finish();
    }

    public boolean validate()
    {


        if (name.getText().toString().length() == 0)
        {
            Toast.makeText(getApplicationContext(), "name cannot be blank", Toast.LENGTH_LONG).show();
            name.setError("name cannot be blank");
            return false;
        }




        if (pass.getText().toString().length() == 0)
        {
            Toast.makeText(getApplicationContext(), "Password cannot be Blank", Toast.LENGTH_LONG).show();
            pass.setError("Password cannot be Blank");
            return false;
        }




        if (repass.getText().toString().length() == 0)
        {
            Toast.makeText(getApplicationContext(), "Password cannot be Blank", Toast.LENGTH_LONG).show();
            repass.setError("Password cannot be Blank");
            return false;
        }




        if (email.getText().toString().length() == 0)
        {
            Toast.makeText(getApplicationContext(), "Email cannot be blank", Toast.LENGTH_LONG).show();
            email.setError("Email cannot be blank");
            return false;
        }

        if(phone.getText().length()==0)
        {
            Toast.makeText(getApplicationContext(), "Phone no. cannot be blank", Toast.LENGTH_LONG).show();
            phone.setError("Phone no. cannot be blank");
            return false;
        }

        if(pass.getText().toString().length()<8)
        {
            Toast.makeText(getApplicationContext(), "Password too short", Toast.LENGTH_LONG).show();
            pass.setError("atleast 8 character");
            return false;
        }

        if(repass.getText().toString().length()<8)
        {
            Toast.makeText(getApplicationContext(), "Password too short", Toast.LENGTH_LONG).show();
            repass.setError("atleast 8 character");
            return false;
        }

        if(!pass.getText().toString().equals(repass.getText().toString()))
        {
            Toast.makeText(getApplicationContext(), "Password not match", Toast.LENGTH_LONG).show();
            pass.setError("Password not match");
            repass.setError("Password not match");
            return false;

        }

        if (!android.util.Patterns.EMAIL_ADDRESS.matcher(email.getText().toString()).matches())
        {
            //Validation for Invalid Email Address
            Toast.makeText(getApplicationContext(), "Invalid Email", Toast.LENGTH_LONG).show();
            email.setError("Invalid Email");
            return false;
        }



        if(phone.getText().toString().length()!=10)
        {
            Toast.makeText(getApplicationContext(), "enter a correct indian no.", Toast.LENGTH_LONG).show();
            repass.setError("atleast 10 number");
            return false;
        }




        return true;

    }

}
