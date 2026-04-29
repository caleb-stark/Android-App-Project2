package com.example.project2_android_app.viewholders;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.ListAdapter;
import androidx.recyclerview.widget.RecyclerView;

import com.example.project2_android_app.R;
import com.example.project2_android_app.database.entities.Item;

/**
 * RecyclerView adapter for items inside a ShoppingList.
 * Uses ListAdapter + DiffUtil so LiveData updates animate smoothly.
 *
 * Author: Himansu Yapa
 */
public class ItemAdapter extends ListAdapter<Item, ItemAdapter.VH> {

    public interface OnItemClick { void onClick(Item item); }
    public interface OnCheckChanged { void onChanged(Item item, boolean checked); }

    private final OnItemClick onClick;
    private final OnCheckChanged onCheck;

    public ItemAdapter(OnItemClick onClick, OnCheckChanged onCheck) {
        super(DIFF);
        this.onClick = onClick;
        this.onCheck = onCheck;
    }

    @NonNull
    @Override
    public VH onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.row_item, parent, false);
        return new VH(v);
    }

    @Override
    public void onBindViewHolder(@NonNull VH holder, int position) {
        Item item = getItem(position);
        holder.name.setText(item.getItemName());
        holder.qty.setText("Qty: " + item.getQuantity());

        // Detach old listener before setting state (avoids spurious onCheckChanged on rebind):
        holder.checkBox.setOnCheckedChangeListener(null);
        holder.checkBox.setChecked(item.isBought());
        holder.checkBox.setOnCheckedChangeListener((b, isChecked) -> onCheck.onChanged(item, isChecked));

        holder.itemView.setOnClickListener(v -> onClick.onClick(item));
    }

    static class VH extends RecyclerView.ViewHolder {
        final TextView name, qty;
        final CheckBox checkBox;
        VH(View v) {
            super(v);
            name = v.findViewById(R.id.text_item_name);
            qty = v.findViewById(R.id.text_item_qty);
            checkBox = v.findViewById(R.id.checkbox_item_bought);
        }
    }

    private static final DiffUtil.ItemCallback<Item> DIFF = new DiffUtil.ItemCallback<Item>() {
        @Override public boolean areItemsTheSame(@NonNull Item a, @NonNull Item b) {
            return a.getItemId() == b.getItemId();
        }
        @Override public boolean areContentsTheSame(@NonNull Item a, @NonNull Item b) {
            return a.getItemName().equals(b.getItemName())
                    && a.getQuantity() == b.getQuantity()
                    && a.isBought() == b.isBought()
                    && a.getDescription().equals(b.getDescription());
        }
    };
}
