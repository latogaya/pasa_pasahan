package com.example.products;

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
import com.models.Product;


public class ProductDetailActivity extends AppCompatActivity {
    private ProductDBHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.product_detail);

        ImageView profilePic = findViewById(R.id.profile_pic);
        CircleTransform.applyCircularTransform(profilePic);

        Button backButton = findViewById(R.id.back_home);
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
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
                "clothes_image"
        };

        String selection = "clothes_id = ?";
        String[] selectionArgs = { "1" };
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
            String descriptionContent = cursor.getString(cursor.getColumnIndexOrThrow("description_content"));
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

            TextView tvDescriptionContent = findViewById(R.id.description_content);
            tvDescriptionContent.setText(descriptionContent);

        }

        cursor.close();

    }
}
