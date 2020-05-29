package com.shashimaltec.ml;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;

public class IntroActivity extends AppCompatActivity {

    Button Next ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        SharedPreferences pref = getSharedPreferences("com.android.app.users", MODE_PRIVATE);
        String status = pref.getString("UserStatus", "NotRegistered");
        if (status.equals("Registered")) {
            this.finish();
            Intent intent = new Intent(IntroActivity.this, CameraActivity.class);
            startActivity(intent);
        }

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_intro);
        Next =(Button) findViewById(R.id.next);
        AdView adView3 = (AdView) findViewById(R.id.adView3);
        AdRequest adRequest = new AdRequest.Builder().build();
        adView3.loadAd(adRequest);

        Next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SharedPreferences.Editor editor = pref.edit();
                editor.putString("UserStatus","Registered");
                editor.apply();

                Intent i = new Intent(IntroActivity.this, CameraActivity.class);
                startActivity(i);
            }
        });

    }

}
