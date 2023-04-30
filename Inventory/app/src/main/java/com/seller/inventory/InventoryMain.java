package com.seller.inventory;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.SearchView;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class InventoryMain extends AppCompatActivity {

    private static final int REQUEST_CODE_ADD_PRODUCT = 1;

    private ListView mProductsListView;
    private List<Product> mProducts;
    private ProductAdapter mProductAdapter;

    private SearchView mSearchView; // Declare the SearchView variable


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_inventory);

        mProductsListView = findViewById(R.id.productsListView);
        mProducts = new ArrayList<>();
        mProductAdapter = new ProductAdapter(this, R.layout.inventory_recycler_view, mProducts);
        mProductsListView.setAdapter(mProductAdapter);
        Product newProduct = new Product("DBTK", "DBTK OG", 120.0, "Large", "Shirt");
        mProducts.add(newProduct);
        Product newProduct1 = new Product("Nike", "JORDAN OG", 12.0, "9.5", "Shoes");
        mProducts.add(newProduct1);
        Product newProduct2 = new Product("Adidas", "ADIDAS OG", 100.0, "10.5", "Shoes");
        mProducts.add(newProduct2);
        Product newProduct3 = new Product("Zalu", "Zalu OG", 120.0, "Large", "Shirt");
        mProducts.add(newProduct3);
        Product newProduct4 = new Product("Ambher", "Ambher OG", 12.0, "Small", "Short");
        mProducts.add(newProduct4);
        Product newProduct5 = new Product("Bastion", "Bastion OG", 100.0, "Medium", "Short");
        mProducts.add(newProduct5);

        // Initialize the SearchView variable
        mSearchView = findViewById(R.id.search_view);

        // Set a query listener on the SearchView to handle search queries
        mSearchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                // This method is called when the user submits the search query
                // Here you can handle the search query and show the search results
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                // This method is called when the user types or changes the search query
                // Here you can filter the list based on the search query
                filterProducts(newText);
                return true;
            }
        });
        Button sortButton = findViewById(R.id.sortButton);
        sortButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                sortProducts();
            }
        });

        // Find the remove button in the layout
        Button removeButton = findViewById(R.id.deleteButton);

        removeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Get the selected item position in the ListView
                int position = mProductsListView.getCheckedItemPosition();
                if (position != ListView.INVALID_POSITION) {
                    // Remove the item from the List and update the adapter
                    if (TextUtils.isEmpty(mSearchView.getQuery())) {
                        // If the query is empty, remove the item from the original list
                        mProducts.remove(position);
                    } else {
                        // If the query is not empty, remove the item from the filtered list
                        Product selectedProduct = (Product) mProductAdapter.getItem(position);
                        mProducts.remove(selectedProduct);
                    }
                    mProductAdapter.notifyDataSetChanged();
                }
            }
        });


        // Set an item click listener on the ListView to handle item selection
        mProductsListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
                // Set the item as checked in the ListView
                mProductsListView.setItemChecked(position, true);
            }
        });

        // Find the transaction button in the layout
        Button transactionButton = findViewById(R.id.transactionButton);

        // Set a click listener on the transaction button
        transactionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Get the selected item position in the ListView
                int position = mProductsListView.getCheckedItemPosition();
                if (position != ListView.INVALID_POSITION) {
                    // Get the selected product from the List
                    Product selectedProduct = mProducts.get(position);
                    // Start the TransactionDetails activity with the selected product
                    Intent intent = new Intent(InventoryMain.this, TransactionDetails.class);
                    intent.putExtra("product", selectedProduct);
                    startActivity(intent);
                }
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_CODE_ADD_PRODUCT && resultCode == RESULT_OK) {
            // Get the new product from the Intent
            Product newProduct = data.getParcelableExtra("product");

            // Add the new product to the list and update the adapter
            mProducts.add(newProduct);
            mProductAdapter.notifyDataSetChanged();
        }
    }

    private void sortProducts() {
        // Sort the products list by name using a Comparator
        Collections.sort(mProducts, new Comparator<Product>() {
            @Override
            public int compare(Product product1, Product product2) {
                return product1.getName().compareToIgnoreCase(product2.getName());
            }
        });

        // Notify the adapter that the data set has changed
        mProductAdapter.notifyDataSetChanged();
    }

    private void filterProducts(String query) {
        List<Product> filteredProducts = new ArrayList<>();

        for (Product product : mProducts) {
            if (product.getName().toLowerCase().contains(query.toLowerCase()) ||
                    product.getmBrand().toLowerCase().contains(query.toLowerCase()) ||
                    product.getSize().toLowerCase().contains(query.toLowerCase()) ||
                    product.getType().toLowerCase().contains(query.toLowerCase())) {
                filteredProducts.add(product);
            }
        }

        // Sort the filtered products list by name using a lambda expression
        filteredProducts.sort((product1, product2) -> product1.getName().compareToIgnoreCase(product2.getName()));

        // Update the adapter with the filtered products list, or show all products if the query is empty
        if (TextUtils.isEmpty(query)) {
            mProductAdapter = new ProductAdapter(this, R.layout.inventory_recycler_view, mProducts);
        } else {
            mProductAdapter = new ProductAdapter(this, R.layout.inventory_recycler_view, filteredProducts);
        }
        mProductsListView.setAdapter(mProductAdapter);
    }





}

