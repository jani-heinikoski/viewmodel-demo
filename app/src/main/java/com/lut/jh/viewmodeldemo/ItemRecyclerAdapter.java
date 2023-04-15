package com.lut.jh.viewmodeldemo;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class ItemRecyclerAdapter extends RecyclerView.Adapter<ItemRecyclerAdapter.ViewHolder> {

    private ArrayList<Item> items;

    public final static class ViewHolder extends RecyclerView.ViewHolder {
        private final TextView contentTextView;

        public ViewHolder(View view) {
            super(view);
            this.contentTextView = (TextView) view.findViewById(R.id.item_content);
        }

        public TextView getContentTextView() {
            return contentTextView;
        }
    }

    public ItemRecyclerAdapter(ArrayList<Item> items) {
        this.items = items;
    }

    // Create new views (invoked by the layout manager)
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        // Create a new view, which defines the UI of the list item
        View view = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.item, viewGroup, false);

        return new ViewHolder(view);
    }

    // Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, final int position) {
        viewHolder.getContentTextView().setText(this.items.get(position).getContent());
    }

    // Return the size of your dataset (invoked by the layout manager)
    @Override
    public int getItemCount() {
        return this.items.size();
    }

}
