package com.example.fooddelivery.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.fooddelivery.Models.AddToBasket;
import com.example.fooddelivery.Models.FavouriteModel;
import com.example.fooddelivery.Models.Upload;
import com.example.fooddelivery.R;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class FavouriteAdapter extends RecyclerView.Adapter<FavouriteAdapter.Holder> {

    StorageReference storageReference;
    DatabaseReference reference;
    List<FavouriteModel> products = new ArrayList<>();

    private Context context;
    private List<FavouriteModel> uploadList;

    public FavouriteAdapter(Context context, List<FavouriteModel> uploadList) {
        this.context = context;
        this.uploadList = uploadList;
    }

    @NonNull
    @Override
    public Holder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_favourite, parent, false);
        return new FavouriteAdapter.Holder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull Holder holder, final int position) {
        final FavouriteModel uploadCurrent = uploadList.get(position);
        holder.txt_name.setText(uploadCurrent.getmName());
        holder.txt_price.setText(uploadCurrent.getPrice());
        Picasso.get()
                .load(uploadCurrent.getImageUrl())
                .fit()
                .centerCrop()
                .into(holder.imageView);

        holder.card_add_product.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
                storageReference = FirebaseStorage.getInstance().getReference("uploads").child("favourite");
                reference = FirebaseDatabase.getInstance().getReference("uploads").child("favourite");
                FavouriteModel addToBasket = new FavouriteModel(
                        uploadList.get(position).getmName(),
                        uploadList.get(position).getPrice(),
                        uploadList.get(position).getImageUrl()
                );
                products.add(addToBasket);
                if (!products.isEmpty()) {
                    reference.child(user.getUid())
                            .setValue(products)
                            .addOnSuccessListener(new OnSuccessListener<Void>() {
                                @Override
                                public void onSuccess(Void aVoid) {
                                    Toast.makeText(context, "Блюдо добавлено в корзину", Toast.LENGTH_SHORT).show();
                                }
                            });
                } else {
                    Toast.makeText(context, "Basket is empty", Toast.LENGTH_SHORT).show();
                }

            }
        });

//        holder.card_favourite.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//
//                FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
//                storageReference = FirebaseStorage.getInstance().getReference("uploads").child("favourite");
//                reference = FirebaseDatabase.getInstance().getReference("uploads").child("favourite");
//                AddToBasket addToBasket = new AddToBasket(
//                        uploadList.get(position).getmName(),
//                        uploadList.get(position).getPrice(),
//                        uploadList.get(position).getmImageUrl()
//                );
//                products.add(addToBasket);
//                if (!products.isEmpty()) {
//                    reference.child(user.getUid())
//                            .setValue(products)
//                            .addOnSuccessListener(new OnSuccessListener<Void>() {
//                                @Override
//                                public void onSuccess(Void aVoid) {
//                                    Toast.makeText(context, "Блюдо добавлено в избранные", Toast.LENGTH_SHORT).show();
//                                }
//                            });
//                } else {
//                    Toast.makeText(context, "Basket is empty", Toast.LENGTH_SHORT).show();
//                }
//
//            }
//        });

    }

    @Override
    public int getItemCount() {
        return uploadList.size();
    }

    public class Holder extends RecyclerView.ViewHolder {

        public TextView txt_name, txt_price;
        public ImageView imageView;
        public CardView card_add_product, cardView, card_favourite;

        public Holder(@NonNull View itemView) {
            super(itemView);

            txt_name = itemView.findViewById(R.id.name_product_item);
            txt_price = itemView.findViewById(R.id.price_item);
            imageView = itemView.findViewById(R.id.img_item);
            card_add_product = itemView.findViewById(R.id.add_product_item);
            cardView = itemView.findViewById(R.id.card_view_item);
            card_favourite = itemView.findViewById(R.id.add_favourite_item);

        }
    }
}
