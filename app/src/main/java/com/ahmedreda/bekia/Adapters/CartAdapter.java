package com.ahmedreda.bekia.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.ahmedreda.bekia.R;
import com.ahmedreda.bekia.Cart_Item;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;

import java.util.ArrayList;
import java.util.List;

public class CartAdapter extends RecyclerView.Adapter<CartAdapter.ViewHolder> {

    public Context context ;
    public List<Cart_Item> items = new ArrayList<>();
    public OnButtonClick onButtonClick ;

    public CartAdapter(Context context) {
        this.context = context;
    }

    public CartAdapter(Context context,List<Cart_Item> items, OnButtonClick onButtonClick) {
        this.context = context;
        this.items = items ;
        this.onButtonClick = onButtonClick ;
    }


    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.card_item,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Cart_Item cart_item = items.get(position);
        RequestOptions requestOptions = new RequestOptions().placeholder(R.drawable.ic_launcher_background);
        Glide.with(context).asBitmap().load(cart_item.getImage()).apply(requestOptions).into(holder.imageView);
        holder.product_name.setText(cart_item.getTitle());
        holder.quntity_txt.setText(String.valueOf(cart_item.getQuantity()));
        holder.price_txt.setText(String.valueOf(cart_item.getPrice()+"جنيه"));
        holder.delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onButtonClick.onRemoveClick(cart_item,position);
            }
        });
    }

    @Override
    public int getItemCount() {
        return items.size() ;
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        public ImageView imageView ;
        public TextView product_name , quntity_txt , price_txt ;
        public ImageButton delete ;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.productImageView);
            delete = itemView.findViewById(R.id.deleteProductButton);
            product_name = itemView.findViewById(R.id.productNameTextView);
            quntity_txt = itemView.findViewById(R.id.quantity_txt);
            price_txt = itemView.findViewById(R.id.productTotalPriceTextView);
        }
    }

    public void RemoveItem(int position,Cart_Item cart_item){
        items.remove(cart_item);
        notifyItemRemoved(position);
    }

    public interface OnButtonClick{
        void onRemoveClick(Cart_Item cart_item,int position);
    }



}
