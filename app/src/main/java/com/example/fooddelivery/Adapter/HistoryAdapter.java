package com.example.fooddelivery.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.fooddelivery.Models.AddToBasket;
import com.example.fooddelivery.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class HistoryAdapter extends RecyclerView.Adapter<HistoryAdapter.Holder>  {

    private Context context;
    private List<AddToBasket> addToBasketList;

    public HistoryAdapter(Context context, List<AddToBasket> addToBasketList) {
        this.context = context;
        this.addToBasketList = addToBasketList;
    }

    @NonNull
    @Override
    public HistoryAdapter.Holder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_history, parent, false);
        return new HistoryAdapter.Holder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull HistoryAdapter.Holder holder, int position) {
        final AddToBasket addToBasket = addToBasketList.get(position);
        holder.txt_name.setText(addToBasket.getmName());
        holder.txt_price.setText(addToBasket.getPrice());
        Picasso.get().load(addToBasketList.get(position).getImageUrl()).into(holder.imageView);

    }

    @Override
    public int getItemCount() {
        return addToBasketList.size();
    }

    public class Holder extends RecyclerView.ViewHolder {

        public TextView txt_name, txt_price;
        public ImageView imageView;
        public CardView cardView;

        public Holder(@NonNull View itemView) {
            super(itemView);
            txt_name = itemView.findViewById(R.id.title_basket);
            txt_price = itemView.findViewById(R.id.price_basket);
            imageView = itemView.findViewById(R.id.img_basket);
            cardView = itemView.findViewById(R.id.card_basket);
        }
    }
}
