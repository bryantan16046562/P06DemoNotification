package com.a16046562.demonotification;

import android.app.Activity;
import android.app.AlarmManager;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Build;
import android.support.v4.app.NotificationCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import java.util.Calendar;

public class MainActivity extends AppCompatActivity {
    int requestcode = 123;
    int requestcode2 = 12345;
    int notificationid = 888;
    Button btnsetalarmm, btnnotifyy1, btnnotifyy2, btnschedulee;
    AlarmManager am;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        btnsetalarmm = (Button) this.findViewById(R.id.btnsetalarm);
        btnsetalarmm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Calendar cal = Calendar.getInstance();
                cal.add(Calendar.SECOND,5);

                //create pendingintent and add to alarmmanager
                Intent intent = new Intent(MainActivity.this, alarmreceiveractivity.class);
                int reqcode = 12345;
                PendingIntent pendingIntent = PendingIntent.getActivity(MainActivity.this, reqcode, intent, PendingIntent.FLAG_CANCEL_CURRENT);

                //get alarmmanager instance
                am = (AlarmManager) getSystemService(Activity.ALARM_SERVICE);
                //set alarm
                am.set(AlarmManager.RTC_WAKEUP,cal.getTimeInMillis(),pendingIntent);
            }
        });

        btnnotifyy2 = (Button) findViewById(R.id.btnnotify2);
        btnnotifyy2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                NotificationManager notificationManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);

                //channels only exists in Oreo and after
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                    NotificationChannel channel = new NotificationChannel
                            ("default", "Default Channel", NotificationManager.IMPORTANCE_DEFAULT);

                    channel.setDescription("this is for default notification");
                    notificationManager.createNotificationChannel(channel);
                }
                //activity to be launch if notification is clicked
                Intent intent = new Intent(MainActivity.this,MainActivity.class);
                PendingIntent pintent = PendingIntent.getActivity(MainActivity.this,requestcode,intent,PendingIntent.FLAG_CANCEL_CURRENT);

                //setting notification styles
                NotificationCompat.BigTextStyle bigtext = new NotificationCompat.BigTextStyle();
                bigtext.setBigContentTitle("Big Text - Long Content");
                bigtext.bigText("aaa" + "bbb" + "\nccc");
                bigtext.setSummaryText("Reflection Journal");

                //build notification details
                NotificationCompat.Builder builder = new NotificationCompat.Builder(MainActivity.this,"default");
                builder.setContentTitle("Amazing Offer!");
                builder.setContentText("Subject");
                builder.setSmallIcon(android.R.drawable.btn_star_big_off);
                builder.setContentIntent(pintent);
                builder.setStyle(bigtext);
                builder.setAutoCancel(true);

                Notification n = builder.build();

                notificationManager.notify(notificationid, n);
                finish();
            }
        });

        btnnotifyy1 = (Button) findViewById(R.id.btnnotify1);
        btnnotifyy1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                NotificationManager notificationManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);

                //channels only exists in Oreo and after
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                    NotificationChannel channel = new NotificationChannel
                            ("default", "Default Channel", NotificationManager.IMPORTANCE_HIGH);

                    channel.setDescription("this is for default notification");
                    notificationManager.createNotificationChannel(channel);
                }
                //activity to be launch if notification is clicked
                Intent intent = new Intent(MainActivity.this,MainActivity.class);
                PendingIntent pintent = PendingIntent.getActivity(MainActivity.this,requestcode,intent,PendingIntent.FLAG_CANCEL_CURRENT);

                //build notification details
                NotificationCompat.Builder builder = new NotificationCompat.Builder(MainActivity.this,"default");
                builder.setContentTitle("Amazing Offer!");
                builder.setContentText("Subject");
                builder.setSmallIcon(android.R.drawable.btn_star_big_off);
                builder.setContentIntent(pintent);
                builder.setAutoCancel(true);

                //set sound if intentions to set sound & not heads-up
                Uri uri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
                builder.setSound(uri);
                //set priority of builder notification
                builder.setPriority(Notification.PRIORITY_HIGH);
                Notification n = builder.build();

                notificationManager.notify(notificationid, n);
                finish();
            }
        });

        btnschedulee = (Button) findViewById(R.id.btnschedule);
        btnschedulee.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Calendar cal2 = Calendar.getInstance();
                cal2.add(Calendar.SECOND, 5);

                Intent intent2 = new Intent(MainActivity.this,ScheduledNotificationReceiver.class);
                PendingIntent pintent2 = PendingIntent.getBroadcast(MainActivity.this,requestcode2,intent2,PendingIntent.FLAG_CANCEL_CURRENT);

                AlarmManager amm = (AlarmManager) getSystemService(Activity.ALARM_SERVICE);
                amm.set(AlarmManager.RTC_WAKEUP,cal2.getTimeInMillis(),pintent2);
            }
        });
    }
}
