package com.example.fooddelivery.Fragments;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.fooddelivery.Adapter.BasketAdapter;
import com.example.fooddelivery.Models.AddToBasket;
import com.example.fooddelivery.R;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.StorageReference;

import java.util.ArrayList;
import java.util.List;

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
               orderNow();
            }
        });

        addToBasketsList = new ArrayList<>();
        getFirstCategory();
        return view;
    }

    private void orderNow() {

        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference("Order");
        reference.child(user.getUid())
                .setValue(addToBasketsList)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        Toast.makeText(getActivity(), "Блюдо заказано", Toast.LENGTH_SHORT).show();
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
