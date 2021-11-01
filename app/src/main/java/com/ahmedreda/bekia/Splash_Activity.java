package com.ahmedreda.bekia;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import com.google.firebase.auth.FirebaseAuth;

public class Splash_Activity extends AppCompatActivity {

    FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_);

      //  getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
               if (firebaseAuth.getCurrentUser() == null) {
                   // go to login screen
                   Intent intent = new Intent(Splash_Activity.this , MainActivity.class );
                   startActivity(intent);
                   finish();
                   // ba7ot retyrn 3shan mfish haga tatnfz ta7t
                   return;
               }else{
                   Intent intent = new Intent(Splash_Activity.this, Products_Activity.class);
                   startActivity(intent);
                   finish();
               }
            }
        },3000);
    }

}