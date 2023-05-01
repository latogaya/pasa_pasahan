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
        mFeedbackAdapter = new FeedbackAdapter(this, R.layout.seller_list_view, mProducts);
        mProductsListView.setAdapter(mFeedbackAdapter);

        // Create some dummy products
        Feedbacks product1 = new Feedbacks("Product 1", "Seller A", "Good Quality");
        Feedbacks product2 = new Feedbacks("Product 2", "Seller B", "Fine Quality");
        Feedbacks product3 = new Feedbacks("Product 3", "Seller A", "Best Quality");

        // Add the products to the list
        mProducts.add(product1);
        mProducts.add(product2);
        mProducts.add(product3);

        // Notify the adapter that the data set has changed
        mFeedbackAdapter.notifyDataSetChanged();

        mProductsListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
                Feedbacks product = mProducts.get(position);
                Intent intent = new Intent(OrderMAIN.this, OrderCRUD.class);
                intent.putExtra("product", product);
                startActivity(intent);
            }
        });

        Button addButton = findViewById(R.id.rateButton);
        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(OrderMAIN.this, OrderCRUD.class);
                startActivityForResult(intent, REQUEST_CODE_ADD_PRODUCT);
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == REQUEST_CODE_ADD_PRODUCT && resultCode == RESULT_OK) {
            // Get the new product from the Intent data
            Feedbacks newProduct = data.getParcelableExtra("product");

            // Add the new product to the list
            mProducts.add(newProduct);

            // Notify the adapter that the data set has changed
            mFeedbackAdapter.notifyDataSetChanged();
        }
    }
}
