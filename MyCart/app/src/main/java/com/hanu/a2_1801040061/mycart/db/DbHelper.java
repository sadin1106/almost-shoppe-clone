package com.hanu.a2_1801040061.mycart.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DbHelper extends SQLiteOpenHelper {

    private static final String DBS_NAME = "products.db";
    private static final int DBS_VERSION = 2;

    public DbHelper(Context context) {
        super(context, DBS_NAME, null, DBS_VERSION);
    }
    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE products(" +
                DbSchema.ProductTable.Cols.ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                DbSchema.ProductTable.Cols.UNITPRICE + " INTEGER, " +
                DbSchema.ProductTable.Cols.DESCRIPTION + " TEXT, " +
                DbSchema.ProductTable.Cols.THUMBNAIL + " TEXT, " +
                DbSchema.ProductTable.Cols.QUANTITY + " TEXT);");

        // other tables here
}

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + DbSchema.ProductTable.NAME);
        onCreate(db);
    }
}
