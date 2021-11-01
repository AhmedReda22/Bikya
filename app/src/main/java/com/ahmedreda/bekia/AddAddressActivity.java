package com.ahmedreda.bekia;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import android.widget.Toolbar;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class AddAddressActivity extends AppCompatActivity {

    EditText name, address, city, postalCode, phoneNumber;
    Toolbar toolbar;
    Button addAddressBtn;

    FirebaseFirestore firestore;
    FirebaseAuth auth;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_address);

        toolbar=findViewById(R.id.add_address_toolbar);
        //setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        name = findViewById(R.id.ad_name);
        address = findViewById(R.id.ad_address);
        city = findViewById(R.id.ad_city);
        postalCode = findViewById(R.id.ad_code);
        phoneNumber = findViewById(R.id.ad_phone);
        addAddressBtn = findViewById(R.id.ad_add_address);

        auth= FirebaseAuth.getInstance();
        firestore=FirebaseFirestore.getInstance();

        addAddressBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String userName = name.getText().toString();
                String userCity = city.getText().toString();
                String userAddress = address.getText().toString();
                String userCode = postalCode.getText().toString();
                String userNumber = phoneNumber.getText().toString();

                String final_address = "";

                if (!userName.isEmpty()){
                    final_address+=userName;
                }
                if (!userCity.isEmpty()){
                    final_address+=userCity;
                }
                if (!userAddress.isEmpty()){
                    final_address+=userAddress;
                }
                if (!userCode.isEmpty()){
                    final_address+=userCode;
                }
                if (!userNumber.isEmpty()){
                    final_address+=userNumber;
                }

                if (!userName.isEmpty() && !userCity.isEmpty() && !userAddress.isEmpty() && !userCode.isEmpty() && !userNumber.isEmpty()) {
                    Map<String,String> map=new HashMap<>();
                    map.put("userAddress",final_address);

                    firestore.collection("CurrentUser").document(auth.getCurrentUser().getUid())
                            .collection("Address").add(map).addOnCompleteListener(new OnCompleteListener<DocumentReference>() {
                        @Override
                        public void onComplete(@NonNull Task<DocumentReference> task) {
                            if (task.isSuccessful()){
                                Toast.makeText(AddAddressActivity.this, "تم إضافة العــنوان", Toast.LENGTH_SHORT).show();

                            }
                        }
                    });

                }else {
                    Toast.makeText(AddAddressActivity.this, "رجاءً املأ الفراغات", Toast.LENGTH_SHORT).show();
                }

            }
        });
    }


}