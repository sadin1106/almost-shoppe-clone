package com.hanu.a2_1801040061.mycart.Adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.hanu.a2_1801040061.mycart.Models.CartItem;
import com.hanu.a2_1801040061.mycart.Models.Product;
import com.hanu.a2_1801040061.mycart.R;
import com.hanu.a2_1801040061.mycart.db.ProductManager;

import java.util.ArrayList;

public class ProductsAdapter extends RecyclerView.Adapter{

    Context context;
    ArrayList<Product> data;

    public ProductsAdapter(Context context, ArrayList<Product> data) {
        this.context = context;
        this.data = data;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view;

        view = layoutInflater.inflate(R.layout.products_adapter, parent, false);
        return new ViewHolder(view);
    }


    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        ViewHolder viewHolder  = (ViewHolder) holder;
        ProductManager productManager = ProductManager.getInstance(context);
        Glide.with(context)
                .load(data.get(position).getThumbnail())
                .into(viewHolder.image);

        viewHolder.description.setText(data.get(position).getDescription());
        viewHolder.unitPrice.setText(String.valueOf(data.get(position).getUnitPrice()));
        viewHolder.cart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ProductManager product = ProductManager.getInstance(context);

                CartItem cartItem = new CartItem(
                        data.get(position).getThumbnail(),
                        data.get(position).getDescription(),
                        data.get(position).getUnitPrice(),
                        1
                );
//                Log.d("cart", "clicked" );
                if (product.isExisted(data.get(position).getThumbnail())){
                    CartItem cart = product.getCartItem(data.get(position).getThumbnail());
                    int quantity = cart.getQuantity() + 1;
                    product.editProduct(cart, quantity);
                }else {
                    productManager.add(cartItem);
                    notifyDataSetChanged();
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    public void filterList(ArrayList<Product> filteredList){
        data = filteredList;
        notifyDataSetChanged();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        ImageView image;
        TextView description;
        TextView unitPrice;
        ImageButton cart;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            this.image = itemView.findViewById(R.id.image);
            this.description = itemView.findViewById(R.id.description);
            this.unitPrice = itemView.findViewById(R.id.price);
            this.cart = itemView.findViewById(R.id.cart);
        }
    }
}
