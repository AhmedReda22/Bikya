package com.ahmedreda.bekia.Fragments;

import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import com.ahmedreda.bekia.Adapters.ProductsAdapter;
import com.ahmedreda.bekia.Products;
import com.ahmedreda.bekia.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.util.ArrayList;
import java.util.List;

public class fragment_electronic extends Fragment {

    public fragment_electronic(){
    }

    private View view;
    private RecyclerView recyclerView ;
    private final int NUM_COULUMS = 2 ;
    private ProductsAdapter adapter ;
    private List<Products> products = new ArrayList<>();
    private StorageReference storageReference = FirebaseStorage.getInstance().getReference();

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
        view = inflater.inflate(R.layout.electronic_fragement,container,false);
        recyclerView = view.findViewById(R.id.rv_electronic);
         recyclerView.setHasFixedSize(true);
         adapter = new ProductsAdapter(getActivity());
        StaggeredGridLayoutManager staggeredGridLayoutManager = new StaggeredGridLayoutManager(NUM_COULUMS, LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(staggeredGridLayoutManager);
        ClearAll();
        GetData();
        return view;
    }

    private void GetData(){
        products.add(new Products("خلاط",
                15,"نقطة/قطعة","https://firebasestorage.googleapis.com/v0/b/bekyaapp.appspot.com/o/product_image%2Fblender.jpg?alt=media&token=02ae5add-c617-4d79-9dc9-8614ee4ca4fa"));
        products.add(new Products("شاحن",
                12,"نقطة/قطعة","https://firebasestorage.googleapis.com/v0/b/bekyaapp.appspot.com/o/product_image%2Fcharger.jpg?alt=media&token=16fb4760-44c8-479c-a03f-851bd55d7fc4"));
        products.add(new Products("بوتجاز",
                18,"نقطة/قطعة","https://firebasestorage.googleapis.com/v0/b/bekyaapp.appspot.com/o/product_image%2Fcooker.jpg?alt=media&token=4b623082-821e-463f-8ce1-70099fcd5eb2"));
        products.add(new Products("مكواة",
                18,"نقطة/قطعة","https://firebasestorage.googleapis.com/v0/b/bekyaapp.appspot.com/o/product_image%2Firon.jpg?alt=media&token=6c185ef3-5278-4116-a289-68e6dc5fa16b"));
        products.add(new Products("لاب توب",
                18,"نقطة/قطعة","https://firebasestorage.googleapis.com/v0/b/bekyaapp.appspot.com/o/product_image%2Flaptop.jpg?alt=media&token=a8b1542c-023d-46fa-b213-891d31049449"));
        products.add(new Products("موبيل",
                18,"نقطة/قطعة","https://firebasestorage.googleapis.com/v0/b/bekyaapp.appspot.com/o/product_image%2Fphone.jpg?alt=media&token=01422d6b-b213-4cc6-8001-8fddacd6ff9a"));
        adapter.setData(products);
        recyclerView.setAdapter(adapter);

    }


    private  void ClearAll(){
        if (products != null){
            products.clear();
        }
        products = new ArrayList<>();
    }



}