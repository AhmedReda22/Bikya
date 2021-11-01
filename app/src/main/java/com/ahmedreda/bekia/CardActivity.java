package com.ahmedreda.bekia;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.Toolbar;

import com.ahmedreda.bekia.Adapters.CartAdapter;
import com.ahmedreda.bekia.Fragments.ConfirmFragment;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

public class CardActivity extends AppCompatActivity  {

    private RecyclerView recyclerView ;
    private CartAdapter adapter ;
    private List<Cart_Item> items = new ArrayList<>();
    private TextView total ;
    private Button place_order ;
    private double total_price ;
    private double price ;
    private final FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
    private final FirebaseFirestore firestore = FirebaseFirestore.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_card);

        recyclerView = findViewById(R.id.rec_card);
        place_order = findViewById(R.id.place_btn);
        total = findViewById(R.id.total);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        adapter = new CartAdapter(this, items,new CartAdapter.OnButtonClick() {
             @Override
             public void onRemoveClick(Cart_Item cart_item,int position) {
                     price = cart_item.getPrice() ;
                     total_price -= price;
                     total.setText(String.valueOf(total_price));
             }
         });
        recyclerView.setAdapter(adapter);
        getItems();
        getTotalPrice();


         place_order.setOnClickListener(new View.OnClickListener() {
             @Override
             public void onClick(View v) {
                 ConfirmFragment confirmFragment = new ConfirmFragment();
                 getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,confirmFragment).addToBackStack(null).commit();
                 startActivity(new Intent(CardActivity.this,AddressActivity.class));
             }
         });


    }

    private void getItems(){
        String UID = firebaseAuth.getCurrentUser().getUid().toString();
        firestore.collection("Orders").whereEqualTo("userID", UID).addSnapshotListener(new EventListener<QuerySnapshot>() {
            @Override
            public void onEvent(@Nullable QuerySnapshot value, @Nullable  FirebaseFirestoreException error) {
                if (error == null) {
                    items.clear();
                    for (DocumentSnapshot documentSnapshot : value.getDocuments()) {
                       Cart_Item cart_item = documentSnapshot.toObject(Cart_Item.class);
                       items.add(cart_item);
                    }
                    adapter.notifyDataSetChanged();
                }else{
                    Toast.makeText(CardActivity.this, error.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
                }
            }
        });
    }


    public void getTotalPrice(){
                    for (Cart_Item cartItem : items) {
                        price = cartItem.getPrice() ;
                        total_price += price;
                        total.setText(String.valueOf(total_price));
                    }
            }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent intent = new Intent(CardActivity.this, Products_Activity.class);
        startActivity(intent);
    }





}