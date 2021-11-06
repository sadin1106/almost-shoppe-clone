package com.hanu.a2_1801040061.mycart;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageButton;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.hanu.a2_1801040061.mycart.Adapter.CartAdapter;
import com.hanu.a2_1801040061.mycart.Models.CartItem;
import com.hanu.a2_1801040061.mycart.Models.Product;
import com.hanu.a2_1801040061.mycart.db.ProductManager;

import java.util.ArrayList;

public class CartActivity extends AppCompatActivity {
    private ProductManager productManager;
    private CartAdapter cartAdapter;
    RecyclerView rv;
    TextView totalPrice;
    private int sum = 0;
    private ArrayList<CartItem> data;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cart);
        rv = findViewById(R.id.sqlLoader);

        productManager = ProductManager.getInstance(this);
        data = productManager.all();
        cartAdapter = new CartAdapter(this, data);
        rv.setAdapter(cartAdapter);
        rv.setLayoutManager(new LinearLayoutManager(this));

        totalPrice = findViewById(R.id.totalPrice);
        for(int i = 0;i < data.size();i++){
            sum += data.get(i).getUnitPrice() * data.get(i).getQuantity();
        }
//        Log.d("total", "onCreate: " + sum);
        totalPrice.setText(String.valueOf(sum));
        cartAdapter.notifyDataSetChanged();
    }
}