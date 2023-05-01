package com.seller.inventory;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

public class SellerRatings extends AppCompatActivity {

    private ListView sellerListView;
    private List<Feedbacks> mProducts;
    private FeedbackAdapter mFeedbackAdapter;

    private static final int REQUEST_CODE_ADD_PRODUCT = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_seller_ratings);


        sellerListView = findViewById(R.id.sellerListView);
        mProducts = new ArrayList<>();
        mFeedbackAdapter = new FeedbackAdapter(this, R.layout.inventory_recycler_view, mProducts);
        sellerListView.setAdapter(mFeedbackAdapter);

        // Receive the new product from the InventoryCRUD activity
        Intent intent = getIntent();
        Feedbacks newProduct = intent.getParcelableExtra("product");

        // Add the new product to the list and update the adapter
        mProducts.add(newProduct);
        mFeedbackAdapter.notifyDataSetChanged();

        Button doneButton = findViewById(R.id.doneButton);
        doneButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SellerRatings.this, OrderCRUD.class);
                startActivity(intent);
            }
        });


    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_CODE_ADD_PRODUCT && resultCode == RESULT_OK) {
            // Get the new product from the Intent
            Feedbacks newProduct = data.getParcelableExtra("product");

            // Add the new product to the list and update the adapter
            mProducts.add(newProduct);
            mFeedbackAdapter.notifyDataSetChanged();
        }
    }

}
