package com.ahmedreda.bekia.Adapters;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.media.Image;
import android.net.Uri;
import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.ahmedreda.bekia.Cart_Item;
import com.ahmedreda.bekia.Products;
import com.ahmedreda.bekia.R;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class ProductsAdapter extends RecyclerView.Adapter<ProductsAdapter.ViewHolder> {

    public Context context ;
    public List<Products> products = new ArrayList<>();
    private final FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
    private final FirebaseFirestore firestore = FirebaseFirestore.getInstance();
    int count  ;
    float result ;

    public ProductsAdapter(Context context) {
        this.context = context;
    }

    public void setData(List<Products> products) {
        this.products = products;
    }


    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.fragment_design,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Products product = products.get(position);
        holder.txt_title.setText(product.getProduct_name());
        holder.txt_points.setText(String.valueOf(product.getPoints()));
        holder.txt_value.setText(product.getProduct_value());
        RequestOptions requestOptions = new RequestOptions().placeholder(R.drawable.ic_launcher_background);
        Glide.with(context).asBitmap().load(product.getImage()).apply(requestOptions).into(holder.imageView);
        String ProductID = String.valueOf(System.currentTimeMillis());

        holder.plus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                count++ ;
                result = (float) (product.getPoints()* (float) count);
                holder.txt_count.setText(String.valueOf(count));
                holder.txt_result.setText(String.valueOf(result)+" "+"نقطة");
            }
        });
        holder.minus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (count != 0) {
                    count--;
                    result = (float) (product.getPoints()* (float) count);
                    holder.txt_count.setText(String.valueOf(count));
                    holder.txt_result.setText(String.valueOf(result)+" " + "نقطة");
                    if (count == 0){
                        holder.txt_result.setText(String.valueOf(0)+" "+"نقطة");
                    }
                } else {
                    count = 0;
                    holder.txt_count.setText(String.valueOf(count));
                    holder.txt_result.setText(String.valueOf(count)+" "+"نقطة");
                }

            }
        });

        holder.add_cart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                        double price =  Math.round(result*0.25);
                        double Total_price = price + price ;
                        String UID = firebaseAuth.getCurrentUser().getUid().toString();
                        String ProductID = String.valueOf(System.currentTimeMillis());
                                Cart_Item cart_item = new Cart_Item(ProductID,UID,product.getProduct_name(),
                                        count,Total_price,product.getImage());
                                UploadCart_Item(cart_item,product);
                    }
        });


    }

    @Override
    public int getItemCount() {
        return products.size();
    }

    public void UploadCart_Item(Cart_Item cart_item,Products product){
        firestore.collection("Orders").document(cart_item.getCardId()).set(cart_item).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull  Task<Void> task) {
                if (task.isSuccessful()){
                    Toast.makeText(context, "Order Created", Toast.LENGTH_SHORT).show();
                }
            }
        });

    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView txt_title, txt_points,txt_value , txt_count , txt_result ;
        ImageView imageView ;
        Button plus , minus , add_cart ;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            txt_title= itemView.findViewById(R.id.txt_title);
            txt_points= itemView.findViewById(R.id.txt_points);
            txt_value= itemView.findViewById(R.id.txt_value);
            imageView = itemView.findViewById(R.id.img_rv);
            txt_count = itemView.findViewById(R.id.count);
            txt_result = itemView.findViewById(R.id.result);
            plus = itemView.findViewById(R.id.plus);
            minus = itemView.findViewById(R.id.minus);
            add_cart = itemView.findViewById(R.id.add_btn);
        }
    }









}
