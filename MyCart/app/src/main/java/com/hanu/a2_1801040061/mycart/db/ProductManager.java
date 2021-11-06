package com.hanu.a2_1801040061.mycart.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteStatement;
import android.util.Log;

import com.hanu.a2_1801040061.mycart.Models.CartItem;
import com.hanu.a2_1801040061.mycart.Models.Product;

import java.util.ArrayList;

public class ProductManager {
    private static ProductManager instance;
    private static final String INSERT_STMT =
            "INSERT INTO " + DbSchema.ProductTable.NAME + "(unitPrice, description, thumbnail, quantity) VALUES(?, ?, ?, ?)";

    private DbHelper dbHelper;
    private SQLiteDatabase db;

    public static ProductManager getInstance(Context context){
        if(instance == null){
            instance = new ProductManager(context);
        }
        return instance;
    }
    private ProductManager(Context context){
        dbHelper = new DbHelper(context);
        db = dbHelper.getWritableDatabase();
    }

    public ArrayList<CartItem> all(){
        String sql = " SELECT * FROM products";
        Cursor cursor   = db.rawQuery(sql,null );
        ProductCursorWrapper cursorWrapper = new ProductCursorWrapper(cursor);
        return cursorWrapper.getProducts();
    }
    public boolean add(CartItem cartItem) {
        SQLiteStatement statement = db.compileStatement(INSERT_STMT);
        statement.bindString(1, String.valueOf(cartItem.getUnitPrice()));
        statement.bindString(2, cartItem.getDescription());
        statement.bindString(3, cartItem.getThumbnail());
        statement.bindString(4, String.valueOf(cartItem.getQuantity()));
        long id = statement.executeInsert();

        if (id > 0) {
            cartItem.setId(id);
            return true;
        }

        return false;
    }

    public boolean isExisted(String thumbnail){
//        Log.d("find", String.valueOf(id));
        String sql = " SELECT * FROM products WHERE thumbnail = ?";
        Cursor cursor = db.rawQuery(sql,new String[] { thumbnail +""} );
        boolean exist = (cursor.getCount() > 0);
        cursor.close();
        return exist;
    }

    public CartItem getCartItem(String thumbnail){
        String sql = " SELECT * FROM products WHERE thumbnail = ?";
        Cursor cursor = db.rawQuery(sql, new String[] { thumbnail +""} );
        CartItem cartItem = new CartItem();
        if (cursor.moveToFirst()){
            cartItem = new CartItem(
                    cursor.getLong(0),
                    cursor.getString(1),
                    cursor.getString(2),
                    cursor.getInt(3),
                    cursor.getInt(4)
            );
        }
        cursor.close();
        return cartItem;
    }

    public boolean editProduct(CartItem cartItem, int quantity) {
        ContentValues args = new ContentValues();
        args.put(DbSchema.ProductTable.Cols.QUANTITY, quantity);
        int result =  db.update(DbSchema.ProductTable.NAME, args, "id=?", new String[]{cartItem.getId() + ""});
        Log.d("update", "editProduct: updated success");
        return result > 0;
    }

    public boolean delete(long id) {
        return (db.delete(DbSchema.ProductTable.NAME, "id=?", new String[]{id+""}) > 0);
    }
}
