package com.example.fooddelivery.Adapter;

import android.content.Context;
import android.content.Intent;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
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
import com.google.firebase.storage.FirebaseStorage;
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
    private int count;
    private OnItemClickListener mListener;

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

        final int totalPrice = ((Integer.parseInt(basketList.get(position).getPrice())));
        count = count+totalPrice;
        Toast.makeText(context, " Сумма блюд "+ count+ " cом", Toast.LENGTH_SHORT).show();

        holder.txt_plus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                CharSequence zz = holder.txt_amount.getText();
                int pz= Integer.parseInt(zz.toString());
                pz++;
                holder.txt_amount.setText(Integer.toString(pz));
            }
        });
        holder.txt_minus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CharSequence zz = holder.txt_amount.getText();
                int pz= Integer.parseInt(zz.toString());
                pz--;
                holder.txt_amount.setText(Integer.toString(pz));
//                if (pz <-1){
//
//                }
            }
        });

    }

    @Override
    public int getItemCount() {
        return basketList.size();
    }

    public class Holder extends RecyclerView.ViewHolder implements View.OnClickListener,
            View.OnCreateContextMenuListener, MenuItem.OnMenuItemClickListener {

        TextView txt_name, txt_price, txt_amount, txt_minus, txt_plus;
        ImageView imageView;
        CardView cardView;

        public Holder(@NonNull View itemView) {
            super(itemView);

            txt_name = itemView.findViewById(R.id.title_basket);
            txt_price = itemView.findViewById(R.id.price_basket);
            txt_amount = itemView.findViewById(R.id.txt_count_basket);
            imageView = itemView.findViewById(R.id.img_basket);
            cardView = itemView.findViewById(R.id.card_basket);
            txt_minus = itemView.findViewById(R.id.txt_minus);
            txt_plus = itemView.findViewById(R.id.txt_plus);

            itemView.setOnClickListener(this);
            itemView.setOnCreateContextMenuListener(this);
        }

        @Override
        public void onClick(View v) {
            if (mListener != null) {
                int position = getAdapterPosition();
                if (position != RecyclerView.NO_POSITION) {
                    mListener.onItemClick(position);
                }
            }
        }

        @Override
        public boolean onMenuItemClick(MenuItem item) {
            if (mListener != null) {
                int position = getAdapterPosition();
                if (position != RecyclerView.NO_POSITION) {
                    if (item.getItemId() == 1) {
                        mListener.onDeleteClick(position);
                        return true;
                    }
                }
            }
            return false;
        }

        @Override
        public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
            menu.setHeaderTitle("Выберите действие");
            MenuItem delete = menu.add(Menu.NONE, 1, 1, "Удалить?");

            delete.setOnMenuItemClickListener(this);
        }
    }

    public interface OnItemClickListener {

        void onItemClick(int position);

        void onDeleteClick(int position);
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        mListener = listener;
    }

}
