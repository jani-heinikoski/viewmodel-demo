package com.lut.jh.viewmodeldemo;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import java.util.ArrayList;

public class MainActivityViewModel extends AndroidViewModel {
    public static final short MAX_ITEMS = 15;

    private final ArrayList<Item> items;

    private final MutableLiveData<Integer> itemCount;

    public MainActivityViewModel(@NonNull Application application) {
        super(application);
        this.items = ItemRepository.loadItems(application);
        this.itemCount = new MutableLiveData<>(this.items.size());
    }

    public int addItem(Item item) {
        if (this.items.size() < MainActivityViewModel.MAX_ITEMS) {
            this.items.add(item);
            this.itemCount.setValue(this.items.size());
            ItemRepository.saveItems(this.items, getApplication());
            return this.items.size() - 1;
        }
        return -1;
    }

    public int removeItems() {
        final int nRemoved = this.items.size();
        this.items.clear();
        this.itemCount.setValue(0);
        ItemRepository.saveItems(this.items, getApplication());
        return nRemoved;
    }

    @NonNull
    public ArrayList<Item> getItems() {
        return this.items;
    }

    @NonNull
    public LiveData<Integer> getItemCount() {
        return this.itemCount;
    }
}
