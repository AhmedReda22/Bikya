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

public class fragment_metal extends Fragment {
    private View view;
    private RecyclerView recyclerView ;
    private final int NUM_COULUMS = 2 ;
    public ProductsAdapter adapter ;
    private List<Products> products = new ArrayList<>();
    private StorageReference storageReference = FirebaseStorage.getInstance().getReference();

    public fragment_metal(){
    }

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
        view= inflater.inflate(R.layout.metal_fragement,container,false);
        recyclerView = view.findViewById(R.id.rv_metal);

         recyclerView.setHasFixedSize(true);

        StaggeredGridLayoutManager staggeredGridLayoutManager = new StaggeredGridLayoutManager(NUM_COULUMS, LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(staggeredGridLayoutManager);
        adapter = new ProductsAdapter(getActivity());
        ClearAll();
        GetData();
        return view;
    }

    private void GetData(){
        products.add(new Products("حلة",
                15,"نقطة/قطعة","https://firebasestorage.googleapis.com/v0/b/bekyaapp.appspot.com/o/product_image%2Fpots.jpg?alt=media&token=27b5e14f-7f5c-454c-ad2b-776d03230d1f"));
        products.add(new Products("طاسة",
                12,"نقطة/قطعة","https://firebasestorage.googleapis.com/v0/b/bekyaapp.appspot.com/o/product_image%2Fpan.jpg?alt=media&token=36f20689-9019-4c21-8e1c-9ea948bc6c02"));
        products.add(new Products("خرضوات معدنية",
                18,"نقطة/كجم","https://firebasestorage.googleapis.com/v0/b/bekyaapp.appspot.com/o/product_image%2Fscrapmetal.jpg?alt=media&token=527c513c-f53c-44a4-9e05-3c3441fd4661"));
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
