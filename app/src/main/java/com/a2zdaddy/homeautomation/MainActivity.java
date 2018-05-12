package com.a2zdaddy.homeautomation;

import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.CompoundButton;
import android.widget.Switch;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.io.InterruptedIOException;

public class MainActivity extends AppCompatActivity {
    Switch acswitch,lightswitch;
    TextView temp,humidity,acswitchstat,lightswitchstat,actimer,lighttimer;
    DatabaseReference db;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        acswitch=findViewById(R.id.acswitch);
        lightswitch=findViewById(R.id.lightswitch);
        temp=findViewById(R.id.temp);
        humidity=findViewById(R.id.humidity);
        acswitchstat=findViewById(R.id.acswitchstat);
        lightswitchstat=findViewById(R.id.lightswitchstat);
        actimer=findViewById(R.id.actimer);
        lighttimer=findViewById(R.id.lighttimer);

        updatetemphumidity();
         updaatelight();
         updateac();
        acswitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {

            @Override
            public void onCheckedChanged(CompoundButton buttonView,
                                         boolean isChecked) {
                db= FirebaseDatabase.getInstance().getReference();


                if(isChecked){
                    acswitchstat.setText("ON");
                    acswitchstat.setTextColor(Color.BLUE);
                    db.child("AC STATUS").setValue(1);
                }else{
                    acswitchstat.setText("OFF");
                    acswitchstat.setTextColor(Color.RED);

                    db.child("AC STATUS").setValue(0);

                }

            }
        });
        lightswitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {

            @Override
            public void onCheckedChanged(CompoundButton buttonView,
                                         boolean isChecked) {
                db= FirebaseDatabase.getInstance().getReference();


                if(isChecked){
                    lightswitchstat.setText("ON");
                    lightswitchstat.setTextColor(Color.BLUE);
                    db.child("LED_STATUS").setValue(1);
                }else{
                    lightswitchstat.setText("OFF");
                    lightswitchstat.setTextColor(Color.RED);

                    db.child("LED_STATUS").setValue(0);

                }

            }
        });

    }
    public void updatetemphumidity(){

        db= FirebaseDatabase.getInstance().getReference();
        db.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                int temperaturesrvr=dataSnapshot.child("Temperature").getValue(Integer.class);
                int humiditysrvr=dataSnapshot.child("Humidity").getValue(Integer.class);
                temp.setText(String.valueOf(temperaturesrvr));
                humidity.setText(String.valueOf(humiditysrvr));

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

    }
    public void updaatelight(){

        db= FirebaseDatabase.getInstance().getReference();
        db.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                int LED_STATUS=dataSnapshot.child("LED_STATUS").getValue(Integer.class);
                if (LED_STATUS==1){
                     lightswitch.setChecked(true);
                    acswitchstat.setText("ON");
                    acswitchstat.setTextColor(Color.BLUE);
                }
               else {

                    lightswitch.setChecked(false);
                    acswitchstat.setText("OFF");
                    acswitchstat.setTextColor(Color.RED);


                }

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

    }

    public void updateac(){

        db= FirebaseDatabase.getInstance().getReference();
        db.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                int AC_STAT=dataSnapshot.child("AC STATUS").getValue(Integer.class);
                if (AC_STAT==1){
                    acswitch.setChecked(true);
                    acswitchstat.setText("ON");
                    acswitchstat.setTextColor(Color.BLUE);

                }
                else {

                    acswitch.setChecked(false);
                    acswitchstat.setText("OFF");
                    acswitchstat.setTextColor(Color.RED);

                    }

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

    }

}
