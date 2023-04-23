package com.example.products;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.models.Product;

public class ProductDBHelper extends SQLiteOpenHelper {
    public ProductDBHelper(Context context) {
        super(context, "product_details.db", null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {}

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {}

    public void addProduct(Product product) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("clothes_name", product.getName());
        values.put("clothes_brand", product.getBrand());
        values.put("clothes_price", product.getPrice());
        values.put("clothes_size", product.getSize());
        values.put("clothes_condition", product.getCondition());
        values.put("clothes_type", product.getType());
        values.put("description_content", product.getDescription());
        values.put("clothes_image", product.getImage());
        db.insert("product_details", null, values);
        db.close();
    }
}
