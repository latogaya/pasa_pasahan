package com.seller.inventory;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class ProductDetails extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_transaction_details);

        Button doneButton = findViewById(R.id.doneButton);
        doneButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Create an Intent to return to the InventoryMain activity
                Intent intent = new Intent(ProductDetails.this, OrderMAIN.class);
                startActivity(intent);

                // Finish the current activity and remove it from the back stack
                finish();
            }
        });
    }
}
