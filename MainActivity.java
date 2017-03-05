package com.example.slax.notificationtest1;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import android.support.v4.app.NotificationCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.Switch;
import android.widget.Toast;


public class MainActivity extends AppCompatActivity {

    int noteId=0,lstatus=0;
    Button b1;
    EditText tTitile,tNote;
    NotificationManager manager;
    Switch switchButton;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initializeVars();


        b1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String txtGetTitle=tTitile.getText().toString();
                final String txtGetNote=tNote.getText().toString();

                if (txtGetNote.equals("") && txtGetTitle.equals("")) {
                    errmsg("Enter at least Title or Note");
                }
                else{
                    if(lstatus==1){
                        addNotificationLock(txtGetTitle,txtGetNote,noteId);
                    }

                    else
                        addNotification(txtGetTitle,txtGetNote,noteId);

                }
            }
        });




        //-------------------button----------------

        switchButton.setChecked(false);
        switchButton.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean bChecked) {
                if (bChecked) {
                    System.out.println("switch on");
                    lstatus=1;

                } else {
                    System.out.println("switch off");
                    addNotificationUnLock(noteId);
                }
            }
        });

        if (switchButton.isChecked()) {
            System.out.println("switch on");
        } else {
            System.out.println("switch off");
            addNotificationUnLock(noteId);

        }

        //-------------------button----------------

    }

    private void errmsg(String s) {
        Context context = getApplicationContext();
        CharSequence text = s;
        int duration = Toast.LENGTH_SHORT;
        Toast toast = Toast.makeText(context, text, duration);
        toast.show();
    }


    private void initializeVars() {
        b1 = (Button)findViewById(R.id.b1);
        tTitile=(EditText) findViewById(R.id.txtNote);
        tNote=(EditText) findViewById(R.id.txtTitle);
        switchButton = (Switch) findViewById(R.id.switch1);
    }

    private void addNotificationLock(String noteP,String titleP,int id) {
        NotificationCompat.Builder builder =
                new NotificationCompat.Builder(this)
                        .setSmallIcon(R.drawable.noti)
                        .setContentTitle(titleP)
                        .setContentText(noteP)
                        .setTicker("Locked note added : "+noteP)
                        .setOngoing(true);
        ;

        Intent notificationIntent = new Intent(this, MainActivity.class);
        PendingIntent contentIntent = PendingIntent.getActivity(this, 0, notificationIntent,
                PendingIntent.FLAG_UPDATE_CURRENT);
        builder.setContentIntent(contentIntent);

        // Add as notification
        manager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        manager.notify(id, builder.build());
    }


    private void addNotification(String noteP,String titleP,int id) {
        NotificationCompat.Builder builder =
                new NotificationCompat.Builder(this)
                        .setSmallIcon(R.drawable.noti)
                        .setContentTitle(titleP)
                        .setContentText(noteP)
                        .setTicker("Note added : "+noteP);
        ;

        Intent notificationIntent = new Intent(this, MainActivity.class);
        PendingIntent contentIntent = PendingIntent.getActivity(this, 0, notificationIntent,
                PendingIntent.FLAG_UPDATE_CURRENT);
        builder.setContentIntent(contentIntent);

        // Add as notification
        manager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        manager.notify(id, builder.build());
    }



    private void addNotificationUnLock(int id) {
        NotificationCompat.Builder builder =
                new NotificationCompat.Builder(this)
                        .setOngoing(false);
        ;

        Intent notificationIntent = new Intent(this, MainActivity.class);
        PendingIntent contentIntent = PendingIntent.getActivity(this, 0, notificationIntent,
                PendingIntent.FLAG_UPDATE_CURRENT);
        builder.setContentIntent(contentIntent);

        // Add as notification
        manager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        manager.notify(id, builder.build());
    }




    public void cancelNotification(int id) {
        manager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        manager.cancel(id); // Notification ID to cancel
    }

}
