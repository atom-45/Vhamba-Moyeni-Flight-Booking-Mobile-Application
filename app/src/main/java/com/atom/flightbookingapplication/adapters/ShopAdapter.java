package com.atom.flightbookingapplication.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.atom.flightbookingapplication.R;
import com.atom.flightbookingapplication.models.Shop;

import java.util.List;
import java.util.Locale;

public class ShopAdapter extends RecyclerView.Adapter<ShopAdapter.ViewHolder> {

    private final List<Shop> shopList;

    public ShopAdapter(List<Shop> shopList) {
        this.shopList = shopList;
    }

    @NonNull
    @Override
    public ShopAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        CardView cardView = (CardView) LayoutInflater.from(parent.getContext()).
                inflate(R.layout.shop_detail_cardview,parent,false);
        return new ViewHolder(cardView);
    }

    @Override
    public void onBindViewHolder(@NonNull ShopAdapter.ViewHolder holder, int position) {

        CardView shopCardView = holder.cardView;
        ImageView shopImage = shopCardView.findViewById(R.id.shop_imageView);
        ImageView shopLogo = shopCardView.findViewById(R.id.shop_logo);
        TextView shopDescription = shopCardView.findViewById(R.id.shop_description);
        TextView shopTime = shopCardView.findViewById(R.id.opening_closingTime_textView);

        shopImage.setImageResource(shopList.get(position).getImageBackgroundID());
        shopLogo.setImageResource(shopList.get(position).getImageLogoID());
        shopDescription.setText(shopList.get(position).getDescription());
        shopTime.setText(String.format(Locale.ENGLISH,"%1$s - %2$s",
                shopList.get(position).getOpeningTime(), shopList.get(position).getClosingTime()));

    }

    @Override
    public int getItemCount() {
        return shopList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        private final CardView cardView;
        public ViewHolder(@NonNull CardView cardView) {
            super(cardView);
            this.cardView = cardView;
        }
    }
}
