package com.ahmedreda.bekia.Fragments;

import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import com.ahmedreda.bekia.Adapters.ProductsAdapter;
import com.ahmedreda.bekia.Products;
import com.ahmedreda.bekia.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class fragment_plastic extends Fragment {
    View view;
    RecyclerView recyclerView ;
    private final int NUM_COULUMS = 2 ;
    private ProductsAdapter adapter ;
    private List<Products> products ;
    private StorageReference storageReference = FirebaseStorage.getInstance().getReference();
    public fragment_plastic() {

    }

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
        view= inflater.inflate(R.layout.plastic_fragment,container,false);

        recyclerView = view.findViewById(R.id.rv_plastic);
        recyclerView.setHasFixedSize(true);
        StaggeredGridLayoutManager staggeredGridLayoutManager = new StaggeredGridLayoutManager(NUM_COULUMS, LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(staggeredGridLayoutManager);
        adapter = new ProductsAdapter(getActivity());
        ClearAll();
        GetData();
        return view;
    }

    private void GetData() {
                    products.add(new Products("زجاجات مياه", 5, "نقطة/قطعة","https://firebasestorage.googleapis.com/v0/b/bekyaapp.appspot.com/o/product_image%2Fwaterbottles.jpg?alt=media&token=2a87889e-4c91-4e72-b3a8-4169508a6e8d" ));
                    products.add(new Products("زجاجات شامبو", 7, "نقطة/قطعة", "https://firebasestorage.googleapis.com/v0/b/bekyaapp.appspot.com/o/product_image%2Fshampoo.jpg?alt=media&token=b5ce92e2-13fc-4fc7-9372-d93af05b17f0"));
                    products.add(new Products("قارورة", 8, "نقطة/قطعة", "https://firebasestorage.googleapis.com/v0/b/bekyaapp.appspot.com/o/product_image%2Fjug.jpg?alt=media&token=c4e8700b-91dd-4ec6-8c0d-334dc30863bd"));
                    products.add(new Products("زجاجات زيت قلي", 6, "نقطة/قطعة","https://firebasestorage.googleapis.com/v0/b/bekyaapp.appspot.com/o/product_image%2Fcooking_oil.jpg?alt=media&token=96e4c3b7-15af-4846-b204-a69abc52f5a8" ));
                    products.add(new Products("جركن", 8, "نقطة/قطعة", "https://firebasestorage.googleapis.com/v0/b/bekyaapp.appspot.com/o/product_image%2Fengine_oil.jpg?alt=media&token=95b6396b-fd72-4cb6-ae74-96bcd2b914ed"));
                    adapter.setData(products);
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
