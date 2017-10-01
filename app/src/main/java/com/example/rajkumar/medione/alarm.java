package com.example.rajkumar.medione;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.TimePicker;

import java.util.Calendar;

import static android.content.Context.ALARM_SERVICE;


public class alarm extends Fragment
{
    AlarmManager alarmManager;
    TimePicker timePicker;
    TextView update;
    Context context;
    Button set,unset;
    PendingIntent pendingIntent;

    View view;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState)
    {
        // Inflate the layout for this fragment
        view= inflater.inflate(R.layout.fragment_alarm, container, false);
        alarmManager=(AlarmManager)getActivity().getSystemService(ALARM_SERVICE);
        timePicker= (TimePicker)view.findViewById(R.id.timePicker);
        update=(TextView)view.findViewById(R.id.update);
        set=(Button) view.findViewById(R.id.set);
        unset=(Button)view.findViewById(R.id.unset);



        final Calendar calendar=Calendar.getInstance();

        final Intent my_intent=new Intent(getActivity().getApplicationContext(),Alarm_Receiver.class);

        set.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                calendar.set(Calendar.HOUR_OF_DAY,timePicker.getCurrentHour());
                calendar.set(Calendar.MINUTE,timePicker.getCurrentMinute());


                //extra remove it
                if(calendar.before(Calendar.getInstance()))
                {
                    calendar.add(Calendar.DATE, 1);
                }


                int hour=timePicker.getCurrentHour();
                int minute=timePicker.getCurrentMinute();


                String hour_string=String.valueOf(hour);
                String minute_string=String.valueOf(minute);

                if(hour>12)
                {
                    hour_string=String.valueOf(hour-12);
                }

                if(minute<10)
                {
                    minute_string="0"+String.valueOf(minute);
                }
                update.setText("Alarm on"+hour_string+":"+minute_string);

                my_intent.putExtra("extra","alarm on");


                pendingIntent=PendingIntent.getBroadcast(getActivity().getApplicationContext(),0,my_intent,PendingIntent.FLAG_UPDATE_CURRENT);

                //alarmManager.set(AlarmManager.RTC_WAKEUP,calendar.getTimeInMillis(),pendingIntent);
                if (Build.VERSION.SDK_INT >= 23)
                {
                    // Wakes up the device in Doze Mode
                    alarmManager.setExactAndAllowWhileIdle(AlarmManager.RTC_WAKEUP,calendar.getTimeInMillis(),pendingIntent);
                }
                else if (Build.VERSION.SDK_INT >= 19)
                {
                    // Wakes up the device in Idle Mode
                    alarmManager.setExact(AlarmManager.RTC_WAKEUP,calendar.getTimeInMillis(), pendingIntent);
                }
                else
                {
                    // Old APIs
                    alarmManager.set(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), pendingIntent);
                }
            }
        });

        unset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                update.setText("Alarm off");

                my_intent.putExtra("extra","alarm off");
                alarmManager.cancel(pendingIntent);

                getActivity().sendBroadcast(my_intent);
            }
        });


        return view;
    }

    // TODO: Rename method, update argument and hook method into UI event





}
