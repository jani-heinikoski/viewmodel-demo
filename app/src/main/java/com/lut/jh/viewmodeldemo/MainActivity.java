package com.lut.jh.viewmodeldemo;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.RecyclerView;

import android.graphics.Color;
import android.os.Bundle;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;

import com.google.android.material.snackbar.Snackbar;

import java.util.Locale;

public class MainActivity extends AppCompatActivity {

    private MainActivityViewModel viewModel;
    private ItemRecyclerAdapter adapter;
    private EditText itemDescEditText;
    private ProgressBar itemCountProgressBar;
    private InputMethodManager inputMethodManager;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        // Get required references
        this.viewModel = new ViewModelProvider(this).get(MainActivityViewModel.class);
        this.adapter = new ItemRecyclerAdapter(this.viewModel.getItems());
        this.itemDescEditText = findViewById(R.id.item_desc_edittext);
        this.itemCountProgressBar = findViewById(R.id.item_count_progressbar);
        this.itemCountProgressBar.setMax(MainActivityViewModel.MAX_ITEMS);
        this.inputMethodManager = (InputMethodManager) getSystemService(MainActivity.INPUT_METHOD_SERVICE);
        // Set the adapter to the RecyclerView
        ((RecyclerView) findViewById(R.id.item_recyclerview)).setAdapter(this.adapter);
        // Register observers and event handlers
        this.registerEventHandlers();
        this.registerObservers();
    }

    private void registerEventHandlers() {
        // Add item button on click
        Button addItemButton = findViewById(R.id.add_item_button);
        addItemButton.setOnClickListener(view -> {
            // Don't allow empty items to be added
            if (MainActivity.this.itemDescEditText.getText().toString().isEmpty()) {
                Snackbar.make(view, "Can't add empty item", Snackbar.LENGTH_SHORT).setTextColor(Color.RED).show();
                return;
            }
            // Add item to list
            Item itemToInsert = new Item(MainActivity.this.itemDescEditText.getText().toString());
            int insertedAt = MainActivity.this.viewModel.addItem(itemToInsert);
            if (insertedAt == -1) {
                Snackbar.make(view, "Can't add more items", Snackbar.LENGTH_SHORT).setTextColor(Color.RED).show();
                return;
            }
            // Notify the adapter about the insertion and close keyboard
            MainActivity.this.adapter.notifyItemInserted(insertedAt);
            MainActivity.this.inputMethodManager.hideSoftInputFromWindow(MainActivity.this.itemDescEditText.getWindowToken(), 0);
            Snackbar.make(view, "Item inserted", Snackbar.LENGTH_SHORT).setTextColor(Color.GREEN).show();
        });
        // Remove items button on click
        Button removeItemsButton = findViewById(R.id.remove_items_button);
        removeItemsButton.setOnClickListener(view -> {
            int nRemoved = MainActivity.this.viewModel.removeItems();
            if (nRemoved > 0) {
                MainActivity.this.adapter.notifyItemRangeRemoved(0, nRemoved);
                Snackbar.make(view, String.format(Locale.getDefault(), "Removed %d items", nRemoved), Snackbar.LENGTH_SHORT).setTextColor(Color.GREEN).show();
            } else {
                Snackbar.make(view, "There are no items", Snackbar.LENGTH_SHORT).setTextColor(Color.RED).show();
            }
        });
    }

    private void registerObservers() {
        this.viewModel.getItemCount().observe(this, count -> {
            // Observes changes in itemCount. When changed, this function receives the updated count as a parameter.
            // Benefit: we only have to subscribe to the LiveData only once and determine what happens when
            // the data changes. Otherwise, we would have to update the progressbar after each C/D-operation manually.
            // It is up to the ViewHolder to update the counter accordingly. Maybe not the best example as a simple
            // getter for the size of items would suffice however the idea is the same with more complex computations as well.
            MainActivity.this.itemCountProgressBar.setProgress(count);
        });
    }

}