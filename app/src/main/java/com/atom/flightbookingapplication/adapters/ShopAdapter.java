package com.atom.flightbookingapplication.adapters;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.atom.flightbookingapplication.R;
import com.atom.flightbookingapplication.databinding.ShopDetailCardviewBinding;
import com.atom.flightbookingapplication.models.Shop;

import java.util.List;

public class ShopAdapter extends RecyclerView.Adapter<ShopAdapter.ViewHolder> {

    private final List<Shop> shopList;
    private LayoutInflater layoutInflater;

    public ShopAdapter(List<Shop> shopList) {
        this.shopList = shopList;
    }

    @NonNull
    @Override
    public ShopAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        if(layoutInflater==null){
            layoutInflater = LayoutInflater.from(parent.getContext());
        }
        ShopDetailCardviewBinding shopDetailCardviewBinding =
                DataBindingUtil.inflate(layoutInflater,R.layout.shop_detail_cardview,parent,false);
        return new ViewHolder(shopDetailCardviewBinding);
    }

    @Override
    public void onBindViewHolder(@NonNull ShopAdapter.ViewHolder holder, int position) {
        holder.bindShops(shopList.get(position));
    }

    @Override
    public int getItemCount() {
        return shopList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        private final ShopDetailCardviewBinding shopDetailCardviewBinding;
        public ViewHolder(@NonNull ShopDetailCardviewBinding shopDetailCardviewBinding) {
            super(shopDetailCardviewBinding.getRoot());
            this.shopDetailCardviewBinding = shopDetailCardviewBinding;
        }

        public void bindShops(Shop shop){
            shopDetailCardviewBinding.setShop(shop);
            shopDetailCardviewBinding.executePendingBindings();
        }
    }
}
