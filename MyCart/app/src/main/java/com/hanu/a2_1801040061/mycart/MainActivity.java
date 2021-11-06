package com.hanu.a2_1801040061.mycart;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.constraintlayout.widget.ConstraintSet;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;


import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.hanu.a2_1801040061.mycart.Adapter.ProductsAdapter;
import com.hanu.a2_1801040061.mycart.Models.Product;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;


public class MainActivity extends AppCompatActivity {

    private Toolbar toolbar;
    private RequestQueue requestQueue;
    ArrayList<Product> data = new ArrayList<>();
    RecyclerView apiLoader;
    ProductsAdapter adapter;
    ProgressDialog pd;
    TextView searchBox;
    String url = "https://mpr-cart-api.herokuapp.com/products";
    private ImageButton cartActivity, searchButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        toolbar =  findViewById(R.id.ToolBar);
        apiLoader = findViewById(R.id.apiLoader);
        cartActivity = findViewById(R.id.cartActivity);

        requestQueue = Volley.newRequestQueue(this);

        cartActivity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, CartActivity.class);
                startActivity(intent);
            }
        });

        pd = new ProgressDialog(this);
        pd.setCancelable(false);

        adapter = new ProductsAdapter(this,data);

        apiLoader.setLayoutManager(new GridLayoutManager(this,2));
        apiLoader.setAdapter(adapter);

        setSupportActionBar(toolbar);

        RestLoader restLoader = new RestLoader();
        restLoader.execute(url);

        searchBox = findViewById(R.id.searchBox);
        searchBox.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {}

            @Override
            public void afterTextChanged(Editable s) {
                filter(s.toString());
            }
        });
    }

    private void filter(String text){
        ArrayList<Product> filteredList = new ArrayList<>();
        for(Product product : data){
            if(product.getDescription().toLowerCase().contains(text.toLowerCase())){
                filteredList.add(product);
            }
        }
        adapter.filterList(filteredList);
    }

    private class RestLoader extends AsyncTask<String, Void, String>{

        @Override
        protected void onPreExecute() {
            super.onPreExecute();

            pd.setMessage("Loading...............");
            pd.show();
        }

        @Override
        protected String doInBackground(String... strings) {

            StringRequest request = new StringRequest(
                    Request.Method.GET,
                    url,
                    response -> {
                        pd.dismiss();
//                        Log.d(TAG, "onResponse: "+response);
                        try {
                            JSONArray array = new JSONArray(response);
                            for (int i = 0; i < array.length();i++){
                                JSONObject object  = array.getJSONObject(i);
                                data.add(
                                        new Product(
                                                object.getLong("id"),
                                                object.getString("thumbnail"),
                                                object.getString("name"),
                                                object.getInt("unitPrice")
                                        )
                                );
//                                Log.d(TAG, "onResponse: "+data.get(i).getId());
                            }
                            adapter.notifyDataSetChanged();

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    },
                    new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            Log.d("error", "onErrorResponse: "+error);
                        }
                    }
            );
            try {
                Thread.sleep(3000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            requestQueue.add(request);

            return "Enjoy your shopping time :)";
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);

            Toast.makeText(MainActivity.this, s, Toast.LENGTH_SHORT).show();
            pd.dismiss();
        }
    }
}