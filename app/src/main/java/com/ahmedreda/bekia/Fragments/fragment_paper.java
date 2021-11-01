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

public class fragment_paper extends Fragment {
    View view;
    RecyclerView recyclerView ;
    private final int NUM_COULUMS = 2 ;
    ProductsAdapter adapter ;
    private List<Products> products = new ArrayList<>();
    private StorageReference storageReference = FirebaseStorage.getInstance().getReference();

    public fragment_paper(){
    }

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
        view= inflater.inflate(R.layout.paper_fragement,container,false);
        recyclerView = view.findViewById(R.id.rv_paper);
        recyclerView.setHasFixedSize(true);
        adapter = new ProductsAdapter(getActivity());
        StaggeredGridLayoutManager staggeredGridLayoutManager = new StaggeredGridLayoutManager(NUM_COULUMS, LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(staggeredGridLayoutManager);
        ClearAll();
        GetData();
        return view;
    }

    private void GetData(){
        products.add(new Products("كرتون",
                15,"نقطة/قطعة","https://firebasestorage.googleapis.com/v0/b/bekyaapp.appspot.com/o/product_image%2Fboxes.jpg?alt=media&token=355732a0-4a52-4ef8-b9e6-5937365b876a"));
        products.add(new Products("ورق",
                12,"نقطة/قطعة","https://firebasestorage.googleapis.com/v0/b/bekyaapp.appspot.com/o/product_image%2Fpapers.jpg?alt=media&token=49511a8a-261b-4236-ac45-80f507646310"));
        products.add(new Products("جرايد",
                18,"نقطة/كجم","https://firebasestorage.googleapis.com/v0/b/bekyaapp.appspot.com/o/product_image%2Fnewspapers.jpg?alt=media&token=2172d75c-0bbe-464f-ae7d-94cd3faa483a"));
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
