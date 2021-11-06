package com.hanu.a2_1801040061.mycart.Adapter;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.hanu.a2_1801040061.mycart.CartActivity;
import com.hanu.a2_1801040061.mycart.Models.CartItem;
import com.hanu.a2_1801040061.mycart.Models.Product;
import com.hanu.a2_1801040061.mycart.R;
import com.hanu.a2_1801040061.mycart.db.ProductManager;

import java.util.ArrayList;

public class CartAdapter extends RecyclerView.Adapter{

    Context context;
    ArrayList<CartItem> data;
    int quantity;

    public CartAdapter(Context context, ArrayList<CartItem> data) {
        this.context = context;
        this.data = data;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view;
        view = layoutInflater.inflate(R.layout.cart_adapter, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        ViewHolder viewHolder = (ViewHolder) holder;
        ProductManager productManager = ProductManager.getInstance(context);
        quantity = data.get(position).getQuantity();
        int p = data.get(position).getUnitPrice();
        int finalPrice = p * quantity;

        Glide.with(context)
                .load(data.get(position).getThumbnail())
                .into(viewHolder.image);
        viewHolder.description.setText(data.get(position).getDescription());
        viewHolder.price.setText(String.valueOf(data.get(position).getUnitPrice()));
        viewHolder.finalPrice.setText(String.valueOf(finalPrice));
        viewHolder.multiply.setText(String.valueOf(quantity));

        viewHolder.add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                quantity++;
                viewHolder.multiply.setText(String.valueOf(quantity));
                int finalP = p * quantity;
                viewHolder.finalPrice.setText(String.valueOf(finalP));
                productManager.editProduct(data.get(position), quantity);
            }
        });

        viewHolder.remove.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (quantity == 0){
                    new AlertDialog.Builder(context)
                            .setCancelable(true)
                            .setMessage("Don't wanna buy this product ?")
                            .setPositiveButton("Delete", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    productManager.delete(data.get(position).getId());
                                    data.clear();
                                    data.addAll(productManager.all());
                                    notifyDataSetChanged();
                                }
                            })
                            .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                }
                            }).show();
                }else {
                    quantity--;
                    viewHolder.multiply.setText(String.valueOf(quantity));
                    int finalP = p * quantity;
                    viewHolder.finalPrice.setText(String.valueOf(finalP));
                    productManager.editProduct(data.get(position), quantity);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView description, price, multiply, finalPrice;
        ImageView image;
        ImageButton add, remove;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            description = itemView.findViewById(R.id.description);
            price = itemView.findViewById(R.id.price);
            multiply = itemView.findViewById(R.id.multiply);
            finalPrice = itemView.findViewById(R.id.finalPrice);
            image = itemView.findViewById(R.id.image);
            add = itemView.findViewById(R.id.add);
            remove = itemView.findViewById(R.id.remove);
        }
    }
}

