package com.shevart.google_map.ui.screens.select_last_place;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.shevart.google_map.R;
import com.shevart.google_map.models.TripPoint;
import com.shevart.google_map.util.UiUtil;

import java.util.ArrayList;
import java.util.List;

import static com.shevart.google_map.util.Util.checkNonNull;
import static com.shevart.google_map.util.Util.isNullOrEmpty;

class TripPointsHistoryRVAdapter extends RecyclerView.Adapter<TripPointsHistoryRVAdapter.ViewHolder> {
    private List<TripPoint> items = new ArrayList<>();

    private TripPointSelectListener listener;

    TripPointsHistoryRVAdapter(@NonNull TripPointSelectListener listener) {
        checkNonNull(listener);
        this.listener = listener;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ViewHolder(UiUtil.inflate(parent, R.layout.item_trip_point_history));
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        TripPoint point = items.get(position);
        holder.tvTripPointName.setText(!isNullOrEmpty(point.getName()) ? point.getName() :
                holder.rootView.getContext().getString(R.string.no_name));
        holder.tvTripPointAddress.setText(point.getAddress());
        holder.rootView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                listener.onTripPointSelected(items.get(holder.getAdapterPosition()));
            }
        });
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    void updateItem(@NonNull List<TripPoint> items) {
        this.items.clear();
        this.items.addAll(items);
        notifyDataSetChanged();
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        final TextView tvTripPointName;
        final TextView tvTripPointAddress;
        final View rootView;
        ViewHolder(View itemView) {
            super(itemView);
            tvTripPointName = itemView.findViewById(R.id.tvTripPointName);
            tvTripPointAddress = itemView.findViewById(R.id.tvTripPointAddress);
            rootView = itemView.findViewById(R.id.llTripPoint);
        }
    }

    interface TripPointSelectListener {
        void onTripPointSelected(@NonNull TripPoint tripPoint);
    }
}
