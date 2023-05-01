package com.seller.inventory;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

public class OrderMAIN extends AppCompatActivity {

    private static final int REQUEST_CODE_ADD_PRODUCT = 1;

    private ListView mProductsListView;
    private List<Feedbacks> mProducts;
    private FeedbackAdapter mFeedbackAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_inventory);

        mProductsListView = findViewById(R.id.orderListView);
        mProducts = new ArrayList<>();
        mFeedbackAdapter = new FeedbackAdapter(this, R.layout.inventory_recycler_view, mProducts);
        mProductsListView.setAdapter(mFeedbackAdapter);

        // Create some dummy products
        Feedbacks product1 = new Feedbacks("Product 1", "Seller A");
        Feedbacks product2 = new Feedbacks("Product 2", "Seller B");
        Feedbacks product3 = new Feedbacks("Product 3", "Seller A");

        // Add the products to the list
        mProducts.add(product1);
        mProducts.add(product2);
        mProducts.add(product3);

        // Notify the adapter that the data set has changed
        mFeedbackAdapter.notifyDataSetChanged();


        // Set an item click listener on the ListView to handle item selection
        mProductsListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
                // Set the item as checked in the ListView
                mProductsListView.setItemChecked(position, true);
            }
        });

        // Find the transaction button in the layout
        Button moreButton = findViewById(R.id.moreButton);

        // Set a click listener on the transaction button
        moreButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Get the selected item position in the ListView
                int position = mProductsListView.getCheckedItemPosition();
                if (position != ListView.INVALID_POSITION) {
                    // Get the selected product from the List
                    Feedbacks selectedProduct = mProducts.get(position);
                    // Start the TransactionDetails activity with the selected product
                    Intent intent = new Intent(OrderMAIN.this, ProductDetails.class);
                    intent.putExtra("product", selectedProduct);
                    startActivity(intent);
                }
            }
        });

        // Find the rate button in the layout
        Button rateButton = findViewById(R.id.rateButton);

        // Set a click listener on the rate button
        rateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Start the InventoryCRUD activity
                Intent intent = new Intent(OrderMAIN.this, OrderCRUD.class);
                startActivity(intent);
            }
        });
    }


}
