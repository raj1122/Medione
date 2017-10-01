package com.example.rajkumar.medione;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.util.Log;
import android.widget.Toast;

/**
 * Created by Raj Kumar on 3/19/2017.
 */

public class RingtonePlayingService extends Service
{
    MediaPlayer media_song;
    boolean isRunning;

    @Nullable
    @Override
    public IBinder onBind(Intent intent)
    {
        return null;
    }

    public int onStartCommand(Intent intent,int flags,int startId)
    {



        Log.e("Local Service","Receiver id"+startId+"!"+intent);

        String state=intent.getExtras().getString("extra");
        Log.e("Ringtone state:",state);




        assert state !=null;
        switch (state)
        {
            case "alarm on":
                startId = 1;
                Log.e("Start id:",state);
                break;
            case "alarm off":
                startId = 0;
                Log.e("Start id:",state);
                break;
            default:
                startId = 0;
                break;
        }

        if (!this.isRunning && startId==1)
        {
            Log.e("there is no music ","you want start");
            media_song=MediaPlayer.create(this,R.raw.chakdeindia);
            media_song.start();

            this.isRunning=true;
            startId=0;

            //setup notifaction service
            NotificationManager notificationManager=(NotificationManager)getSystemService(NOTIFICATION_SERVICE);

            //set the intent to reminder activity
            Intent rem=new Intent(this.getApplicationContext(),MainActivity.class);

            //setup pending intent
            PendingIntent pendingIntent_reminder=PendingIntent.getActivity(this,0, rem,0);

            //make the notification paramets
            Notification notification_popup=new Notification.Builder(this).setContentTitle("alarm is going off")
                    .setContentText("Click me").setContentIntent(pendingIntent_reminder)
                    .setSmallIcon(R.drawable.develop)
                    .setAutoCancel(true).build();


            //set up notification manager
            notificationManager.notify(0,notification_popup);



        }

        //if there is music playing and user press alarm off
        else if (this.isRunning && startId==0)
        {
            Log.e("there is  music ","you want end");

            //stop the ringtone
            media_song.stop();
            media_song.reset();
            this.isRunning=false;

            startId=0;

        }

        //users presess  random button
        //just to bug proof
        //if there is no music playing and user presses alarm off
        //do nothing
        else if (!this.isRunning && startId==0)
        {
            Log.e("there is no music ","you want end");

            this.isRunning=false;
            startId=0;

        }
        //if there is music playing and user presses alarm on
        //do nothing
        else if (this.isRunning && startId==1)
        {
            Log.e("there is  music ","you want start");

            this.isRunning=true;
            startId=1;

        }
        //just to catch odd events
        else
        {
            Log.e("else","somehow you reached");


        }

        return  START_NOT_STICKY;
    }

    public void onDestroy()
    {
        Log.e("on destroy called","ta da" );
        super.onDestroy();
        this.isRunning=true;
    }
}
