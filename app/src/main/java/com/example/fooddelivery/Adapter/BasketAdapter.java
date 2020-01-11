package com.example.fooddelivery.Adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.fooddelivery.DetailActivity;
import com.example.fooddelivery.Fragments.BasketFragment;
import com.example.fooddelivery.Models.AddToBasket;
import com.example.fooddelivery.R;
import com.google.android.gms.common.internal.Constants;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.StorageReference;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

public class BasketAdapter extends RecyclerView.Adapter<BasketAdapter.Holder> {

    private Context context;
    private List<AddToBasket> basketList;
    public int count;

    public BasketAdapter(Context context, List<AddToBasket> basketList) {
        this.context = context;
        this.basketList = basketList;
    }

    @NonNull
    @Override
    public Holder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_basket, parent, false);
        return new BasketAdapter.Holder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final Holder holder, final int position) {
        final AddToBasket addToBasket = basketList.get(position);
        holder.txt_name.setText(addToBasket.getmName());
        holder.txt_price.setText(addToBasket.getPrice());
        holder.txt_amount.setText(addToBasket.getAmount());
        Picasso.get().load(basketList.get(position).getImageUrl()).into(holder.imageView);

        final int totalPrice = ((Integer.valueOf(basketList.get(position).getPrice())));
        count = count+totalPrice;
        Toast.makeText(context, " Сумма блюд "+ count+ " cом", Toast.LENGTH_SHORT).show();

        final int[] amountCounter = {1};

        holder.txt_plus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

                DatabaseReference reference = FirebaseDatabase
                        .getInstance()
                        .getReference("uploads")
                        .child("basket")
                        .child(user.getUid());
//                reference.addValueEventListener(new ValueEventListener() {
//                    @Override
//                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
//
////                        List<AddToBasket> addToBasketList = (List<AddToBasket>) dataSnapshot.getValue();
////                        int size = addToBasketList.size();
////                        amountCounter[0]++;
////                        for (int i = 0; i < size; i++){
////                            if (addToBasketList.get(i).getmName().equals(basketList.get(position).getmName())){
////                                basketList.get(position).setAmount(String.valueOf(amountCounter[0]));
////                            }
////                        }
//
////                        Toast.makeText(context, addToBasketList, Toast.LENGTH_LONG).show();
//
//                    }
//
//                    @Override
//                    public void onCancelled(@NonNull DatabaseError databaseError) {
//
//                    }
//                });

                CharSequence zz = holder.txt_amount.getText();
                int pz= Integer.valueOf(zz.toString());
                pz++;
                holder.txt_amount.setText(Integer.toString(pz));
//                holder.txt_amount.setText(reference.toString());


            }
        });
        holder.txt_minus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CharSequence zz = holder.txt_amount.getText();
                int pz= Integer.valueOf(zz.toString());
                pz--;
                holder.txt_amount.setText(Integer.toString(pz));
            }
        });

    }

    @Override
    public int getItemCount() {
        return basketList.size();
    }

    public class Holder extends RecyclerView.ViewHolder {

//        public Button btn;
        public TextView txt_name, txt_price, txt_amount, txt_minus, txt_plus;
        public ImageView imageView;
        public CardView cardView, close_card;
        public EditText count;

        public Holder(@NonNull View itemView) {
            super(itemView);

            txt_name = itemView.findViewById(R.id.title_basket);
            txt_price = itemView.findViewById(R.id.price_basket);
            txt_amount = itemView.findViewById(R.id.txt_count_basket);
            imageView = itemView.findViewById(R.id.img_basket);
            cardView = itemView.findViewById(R.id.card_basket);
            txt_minus = itemView.findViewById(R.id.txt_minus);
            txt_plus = itemView.findViewById(R.id.txt_plus);
//            btn = itemView.findViewById(R.id.btn_order);
        }
    }
}
