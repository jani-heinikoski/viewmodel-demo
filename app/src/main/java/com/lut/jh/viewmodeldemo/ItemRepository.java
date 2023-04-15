package com.lut.jh.viewmodeldemo;

import android.content.Context;

import java.util.ArrayList;

public class ItemRepository {
    private static final String FILENAME = "items.ser";

    public static void saveItems(ArrayList<Item> items, Context context) {
        Serializer.serializeObject(items, context, FILENAME);
    }

    public static ArrayList<Item> loadItems(Context context) {
        ArrayList<Item> items = new ArrayList<>();
        ArrayList<?> arrayListOfUnknownType = Serializer.deserializeArraylist(context, FILENAME);
        if (arrayListOfUnknownType != null) {
            for (int i = 0; i < arrayListOfUnknownType.size(); i++) {
                if (arrayListOfUnknownType.get(i) instanceof Item) {
                    items.add((Item) arrayListOfUnknownType.get(i));
                }
            }
        }
        return items;
    }
}
