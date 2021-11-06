package com.hanu.a2_1801040061.mycart.db;

import android.database.Cursor;
import android.database.CursorWrapper;

import com.hanu.a2_1801040061.mycart.Models.CartItem;

import java.util.ArrayList;

public class ProductCursorWrapper extends CursorWrapper {
    /**
     * Creates a cursor wrapper.
     *
     * @param cursor The underlying cursor to wrap.
     */
    public ProductCursorWrapper(Cursor cursor) {
        super(cursor);
    }

    public CartItem getProduct(){
        int price = getInt(getColumnIndex(DbSchema.ProductTable.Cols.UNITPRICE));
        long id = getLong(getColumnIndex(DbSchema.ProductTable.Cols.ID));
        String thumbnail = getString(getColumnIndex(DbSchema.ProductTable.Cols.THUMBNAIL));
        String description = getString(getColumnIndex(DbSchema.ProductTable.Cols.DESCRIPTION));
        int quantity = getInt(getColumnIndex(DbSchema.ProductTable.Cols.QUANTITY));

        CartItem cartItem = new CartItem(id,thumbnail,description,price,quantity);
        return cartItem;
    }
    public ArrayList<CartItem> getProducts(){
        ArrayList<CartItem> cartItems = new ArrayList<>();
        moveToFirst();
        while(!isAfterLast()){
            CartItem cartItem = getProduct();
            cartItems.add(cartItem);
            moveToNext();
        }
        return cartItems;
    }
}
