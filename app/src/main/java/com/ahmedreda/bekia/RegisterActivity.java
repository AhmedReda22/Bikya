package com.ahmedreda.bekia;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.ahmedreda.bekia.Fragments.ImageFragment;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;

public class RegisterActivity extends AppCompatActivity {
    EditText email, password, confirm_pass , phone , name ;
    Button register;
    FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
    FirebaseFirestore firebaseFirestore = FirebaseFirestore.getInstance();
    ImageFragment imageFragment = new ImageFragment();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        email = findViewById(R.id.email);
        password = findViewById(R.id.password);
        confirm_pass = findViewById(R.id.confirm_pass);
        phone = findViewById(R.id.phone_number);
        name = findViewById(R.id.name);
        register = findViewById(R.id.register);


        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String Password = password.getText().toString().trim();
                String Confirm =  confirm_pass.getText().toString().trim();
                String Name = name.getText().toString().trim() ;
                String Email = email.getText().toString().trim();
                String Phone_Num = phone.getText().toString().trim();

                if (TextUtils.isEmpty(Email)){
                    email.setError("please enter your E-mail address");
                } else if (TextUtils.isEmpty(Name)){
                    name.setError("Please enter your user_name ");
                } else if (TextUtils.isEmpty(Password)) {
                    password.setError("please enter your password");
                } else if (TextUtils.isEmpty(Confirm)) {
                    confirm_pass.setError("please renter your password");
                } else if (TextUtils.isEmpty(Phone_Num)) {
                    phone.setError("please enter your Phone number");
                }

                if(!Password.equals(Confirm)){
                    Toast.makeText(RegisterActivity.this, "Password not confirmed", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (Password.length() < 6){
                    password.setError("Password should be at least six characters");
                    return;
                }
                CreateUser(Name,Email,Password,Phone_Num);
                FragmentManager fm = getSupportFragmentManager();
                fm.beginTransaction().replace(R.id.fragment_container2 ,imageFragment).commit();
            }
        });





    }

    public void CreateUser(String Name ,String Email , String Password , String Phone_Number){
        firebaseAuth.createUserWithEmailAndPassword(Email,Password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()){
                    String uid = task.getResult().getUser().getUid() ;
                    Toast.makeText(RegisterActivity.this, "Account Created", Toast.LENGTH_SHORT).show();
                    UserData userData = new UserData(Email, Name, Phone_Number);
                    firebaseFirestore.collection("User").document(uid).set(userData);
                }else{
                    String ErrorMessage = task.getException().toString();
                    Toast.makeText(RegisterActivity.this, ErrorMessage, Toast.LENGTH_SHORT).show();
                }
            }
        });

        }

    @Override
    protected void onActivityResult(int requestCode, int resultCode,  Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        Fragment fragment = getSupportFragmentManager().findFragmentById(R.id.fragment_container2);
        fragment.onActivityResult(requestCode, resultCode, data);
    }






}