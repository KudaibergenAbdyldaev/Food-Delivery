package com.example.fooddelivery.Fragments;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.fooddelivery.Adapter.BasketAdapter;
import com.example.fooddelivery.Adapter.FoodAdapter;
import com.example.fooddelivery.DetailActivity;
import com.example.fooddelivery.Models.AddToBasket;
import com.example.fooddelivery.Models.OrderModel;
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

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Objects;

public class BasketFragment extends Fragment {

    private RecyclerView recyclerView;
    private DatabaseReference databaseReference;
    private List<AddToBasket> addToBasketsList;
    FirebaseUser user;
    TextView title,amount,price;
    DatabaseReference reference;
    StorageReference storageReference;
    String image_url;
    ImageView imageView;
    List<AddToBasket> products = new ArrayList<>();
    Button button_order;
    private List<OrderModel> products_order = new ArrayList<>();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_basket, container, false);
        Toolbar toolbar = view.findViewById(R.id.toolbar);
        ((AppCompatActivity)getActivity()).setSupportActionBar(toolbar);
        ((AppCompatActivity)getActivity()).setTitle("Офортмить заказ");

        recyclerView =(RecyclerView) view.findViewById(R.id.recycler_view_basket);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        user = FirebaseAuth.getInstance().getCurrentUser();

        title = (TextView) view.findViewById(R.id.title_basket);
        amount = (TextView) view.findViewById(R.id.txt_count);
        price = (TextView) view.findViewById(R.id.price_basket);
        imageView = (ImageView) view.findViewById(R.id.img_basket);
        button_order=(Button) view.findViewById(R.id.btn_order);
        button_order.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                orderNow();
            }
        });

        addToBasketsList = new ArrayList<>();
        getFirstCategory();
        return view;
    }

    private void orderNow() {

        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        storageReference = FirebaseStorage.getInstance().getReference("Order");
        reference = FirebaseDatabase.getInstance().getReference("Order");
        OrderModel orderNow = new OrderModel(
                title.getText().toString(),
                price.getText().toString(),
                amount.getText().toString(),
                image_url
        );
        products_order.add(orderNow);
        reference.child(user.getUid())
                .setValue(products_order)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        Toast.makeText(getActivity(), "Бюдо добавлено в корзину", Toast.LENGTH_SHORT).show();
                    }
                });

    }

    private void getFirstCategory(){
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        databaseReference = FirebaseDatabase.getInstance().getReference("uploads").child("basket").child(user.getUid());

        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot postSnapshot : dataSnapshot.getChildren()) {
                    AddToBasket addToBasket = postSnapshot.getValue(AddToBasket.class);
                    addToBasketsList.add(addToBasket);
                }
                BasketAdapter adapter = new BasketAdapter(getContext(), addToBasketsList);

                recyclerView.setAdapter(adapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

}
