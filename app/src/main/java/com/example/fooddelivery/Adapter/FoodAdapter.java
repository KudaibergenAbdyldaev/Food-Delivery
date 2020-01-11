package com.example.fooddelivery.Adapter;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.fooddelivery.DetailActivity;
import com.example.fooddelivery.Models.AddToBasket;
import com.example.fooddelivery.Models.FavouriteModel;
import com.example.fooddelivery.Models.Upload;
import com.example.fooddelivery.R;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

public class FoodAdapter extends RecyclerView.Adapter<FoodAdapter.FoodHolder> {

    StorageReference storageReference;
    DatabaseReference reference;
    List<AddToBasket> products = new ArrayList<>();
    List<FavouriteModel> favouriteModelList = new ArrayList<>();
    FirebaseUser user;

    private Context context;
    private List<Upload> uploadList;

    public FoodAdapter(Context context, List<Upload> uploadList) {
        this.context = context;
        this.uploadList = uploadList;
    }

    @NonNull
    @Override
    public FoodHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_product, parent, false);

        user = FirebaseAuth.getInstance().getCurrentUser();

        DatabaseReference reference_fav = FirebaseDatabase.getInstance().getReference("uploads").child("favourite").child(user.getUid());
        reference_fav.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                favouriteModelList.clear();
                for (DataSnapshot postSnapshot : dataSnapshot.getChildren()) {
                    FavouriteModel favouriteModel = postSnapshot.getValue(FavouriteModel.class);

                    favouriteModelList.add(favouriteModel);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });


        DatabaseReference reference = FirebaseDatabase.getInstance().getReference("uploads").child("basket").child(user.getUid());
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                products.clear();
                for (DataSnapshot postSnapshot : dataSnapshot.getChildren()) {
                    AddToBasket addToBasket = postSnapshot.getValue(AddToBasket.class);

                    products.add(addToBasket);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        return new FoodHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final FoodHolder holder, final int position) {
        final Upload uploadCurrent = uploadList.get(position);
        holder.txt_name.setText(uploadCurrent.getmName());
        holder.txt_price.setText(uploadCurrent.getPrice());
        Picasso.get()
                .load(uploadCurrent.getmImageUrl())
                .fit()
                .centerCrop()
                .into(holder.imageView);


        holder.card_add_product.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                holder.card_add_product.setCardBackgroundColor(ContextCompat.getColor(context, R.color.colorPrimary));
                holder.img_plus.setColorFilter(ContextCompat.getColor(context, R.color.white));
                user = FirebaseAuth.getInstance().getCurrentUser();
                storageReference = FirebaseStorage.getInstance().getReference("uploads").child("basket");
                reference = FirebaseDatabase.getInstance().getReference("uploads").child("basket");
                AddToBasket addToBasket = new AddToBasket(
                        uploadList.get(position).getmName(),
                        uploadList.get(position).getPrice(),
                        uploadList.get(position).getmImageUrl()
                );

                addToBasket.setAmount("1");

//                Model model = new Model(
//                        edtTime.getText().toString(),
//                        edtBook.getText().toString()
//                );
//                users.child(model.getTime())
//                        .setValue(model)
//                        .addOnSuccessListener(new OnSuccessListener<Void>() {
//                            @Override
//                        });
                products.add(addToBasket);
                reference.child(user.getUid())
                        .setValue(products)
                        .addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void aVoid) {
                                Toast.makeText(context, "Блюдо добавлено в корзину", Toast.LENGTH_SHORT).show();
                            }
                        });
            }
        });

        holder.card_favourite.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                holder.img_fav.setImageResource(R.drawable.ic_favorite_24dp);
                FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
                storageReference = FirebaseStorage.getInstance().getReference("uploads").child("favourite");
                reference = FirebaseDatabase.getInstance().getReference("uploads").child("favourite");
                FavouriteModel favouriteModel = new FavouriteModel(
                        uploadList.get(position).getmName(),
                        uploadList.get(position).getPrice(),
                        uploadList.get(position).getmImageUrl()
                );
                favouriteModelList.add(favouriteModel);
                if (!products.isEmpty()) {
                    reference.child(user.getUid())
                            .setValue(favouriteModelList)
                            .addOnSuccessListener(new OnSuccessListener<Void>() {
                                @Override
                                public void onSuccess(Void aVoid) {
                                    Toast.makeText(context, "Блюдо добавлено в избранные", Toast.LENGTH_SHORT).show();
                                }
                            });
                } else {
                    Toast.makeText(context, "Basket is empty", Toast.LENGTH_SHORT).show();
                }

            }
        });


        holder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, DetailActivity.class);

                intent.putExtra("title", uploadList.get(position).getmName());
                intent.putExtra("price", uploadList.get(position).getPrice());
                intent.putExtra("overView", uploadList.get(position).getDesck());
                intent.putExtra("img", uploadList.get(position).getmImageUrl());

                context.startActivity(intent);
            }
        });

    }

    @Override
    public int getItemCount() {
        return uploadList.size();
    }

    public class FoodHolder extends RecyclerView.ViewHolder {


        public TextView txt_name, txt_price;
        public ImageView imageView, img_fav;
        public CardView card_add_product, cardView, card_favourite;
        public ImageView img_plus;

        public FoodHolder(@NonNull View itemView) {
            super(itemView);

            img_fav = itemView.findViewById(R.id.add_favourite_item);
            txt_name = itemView.findViewById(R.id.name_product);
            txt_price = itemView.findViewById(R.id.price);
            imageView = itemView.findViewById(R.id.img);
            card_add_product = itemView.findViewById(R.id.add_product);
            cardView = itemView.findViewById(R.id.card_view);
            card_favourite = itemView.findViewById(R.id.add_favourite);
            img_plus = itemView.findViewById(R.id.img_plus);
        }
    }
}
