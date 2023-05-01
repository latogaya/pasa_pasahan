package com.seller.inventory;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class OrderCRUD extends AppCompatActivity {

    private static final int REQUEST_CODE_ADD_PRODUCT = 1;
    private EditText sellerFeedback;
    private EditText itemFeedback;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_inventory_crud);

        sellerFeedback = findViewById(R.id.sellerFeedbackEdit);
        itemFeedback = findViewById(R.id.itemFeedbackInsert);

        Button rateButton = findViewById(R.id.rateButton);
        rateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Get the product information from the EditTexts
                String seller = sellerFeedback.getText().toString();
                String item = itemFeedback.getText().toString();

                // Create a new Product object
                Feedbacks newProduct = new Feedbacks(seller, item);

                // Create an Intent to return the new product to the SellerRatings activity
                Intent intent = new Intent(OrderCRUD.this, SellerRatings.class);
                intent.putExtra("product", newProduct);
                startActivityForResult(intent, REQUEST_CODE_ADD_PRODUCT);
            }
        });

        Button cancelButton = findViewById(R.id.cancelButton);
        cancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(OrderCRUD.this, OrderMAIN.class);
                startActivity(intent);
                finish();
            }
        });

    }
}
