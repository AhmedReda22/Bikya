package com.ahmedreda.bekia;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Process;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class MainActivity extends AppCompatActivity {

    EditText email, password;
    Button register, login;
    FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // super : bdol 3la constructor bta3 parent class badl mast3di constructor aw a3ml object .
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

      //  getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        email = findViewById(R.id.userId);
        password = findViewById(R.id.password);
        login = findViewById(R.id.login);
        register = findViewById(R.id.register);

        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent= new Intent(MainActivity.this,RegisterActivity.class);
                startActivity(intent);
            }
        });
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String Email = email.getText().toString().trim();
                String Password = password.getText().toString().trim();

                if (TextUtils.isEmpty(Email)){
                    email.setError("please enter your user_name");
                }else if (TextUtils.isEmpty(Password)) {
                    password.setError("please enter your password");}

                if (Password.length() < 6){
                    password.setError("Password should be at least six characters");
                    return;
                }

                firebaseAuth.signInWithEmailAndPassword(Email,Password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()){
                            Intent intent = new Intent(MainActivity.this, Products_Activity.class);
                            startActivity(intent);
                            finish();
                        }else{
                            String errorMessage = task.getException().getLocalizedMessage() ;
                            Toast.makeText(MainActivity.this, errorMessage, Toast.LENGTH_SHORT).show();
                        }
                    }
                });

            }
        });




    }


    @Override
    public void onBackPressed() {
        super.onBackPressed();
        moveTaskToBack(true);
        Process.killProcess(Process.myPid());
        System.exit(1);
    }



}


