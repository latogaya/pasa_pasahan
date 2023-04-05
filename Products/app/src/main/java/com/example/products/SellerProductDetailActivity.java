package com.example.products;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.databases.ProductDBHelper;

public class SellerProductDetailActivity extends AppCompatActivity {
    private ProductDBHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.seller_product_detail);
        ImageView profilePic1 = findViewById(R.id.profile_pic1);
        CircleTransform.applyCircularTransform(profilePic1);
        ImageView profilePic2 = findViewById(R.id.profile_pic2);
        CircleTransform.applyCircularTransform(profilePic2);
        Button doneButton = findViewById(R.id.done);
        doneButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SellerProductDetailActivity.this, SellerHomeActivity.class);
                startActivity(intent);
            }
        });

        dbHelper = new ProductDBHelper(this);
        String[] projection = {
                "clothes_name",
                "clothes_brand",
                "clothes_price",
                "clothes_size",
                "clothes_type",
                "clothes_condition",
                "description_content",
                "clothes_image"
        };

        String selection = "clothes_id = ?";
        String[] selectionArgs = { "5" };
        String sortOrder = "clothes_name DESC";
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        Cursor cursor = db.query(
                "product_details",
                projection,
                selection,
                selectionArgs,
                null,
                null,
                sortOrder
        );

        if (cursor.moveToFirst()) {
            String clothesName = cursor.getString(cursor.getColumnIndexOrThrow("clothes_name"));
            String clothesBrand = cursor.getString(cursor.getColumnIndexOrThrow("clothes_brand"));
            double clothesPrice = cursor.getInt(cursor.getColumnIndexOrThrow("clothes_price"));
            String clothesSize = cursor.getString(cursor.getColumnIndexOrThrow("clothes_size"));
            String clothesType = cursor.getString(cursor.getColumnIndexOrThrow("clothes_type"));
            String clothesCondition = cursor.getString(cursor.getColumnIndexOrThrow("clothes_condition"));
            byte[] clothesImage = cursor.getBlob(cursor.getColumnIndexOrThrow("clothes_image"));
            String formattedPrice = "$" + String.format("%.2f", clothesPrice);

            ImageView ivClothesImage = findViewById(R.id.clothes_image);
            Bitmap bitmap = BitmapFactory.decodeByteArray(clothesImage, 0, clothesImage.length);
            ivClothesImage.setImageBitmap(bitmap);

            TextView tvClothesName = findViewById(R.id.clothes_name);
            tvClothesName.setText(clothesName);

            TextView tvClothesBrand = findViewById(R.id.clothes_brand);
            tvClothesBrand.setText(clothesBrand);

            TextView tvClothesPrice = findViewById(R.id.clothes_price);
            tvClothesPrice.setText(formattedPrice);

            TextView tvClothesSize = findViewById(R.id.clothes_size);
            tvClothesSize.setText(clothesSize);

            TextView tvClothesType = findViewById(R.id.clothes_type);
            tvClothesType.setText(clothesType);

            TextView tvClothesCondition = findViewById(R.id.clothes_condition);
            tvClothesCondition.setText(clothesCondition);

        }

        cursor.close();
    }
}
