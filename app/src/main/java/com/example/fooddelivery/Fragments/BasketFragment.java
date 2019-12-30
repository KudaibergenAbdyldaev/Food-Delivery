package com.example.fooddelivery.Fragments;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
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
import com.example.fooddelivery.Models.AddToBasket;
import com.example.fooddelivery.Models.UserInfo;
import com.example.fooddelivery.R;
import com.firebase.ui.auth.data.model.User;
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
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class BasketFragment extends Fragment {

    private RecyclerView recyclerView;
    private DatabaseReference databaseReference;
    private List<AddToBasket> addToBasketsList;
    FirebaseUser user;
    TextView title,amount,price;
    ImageView imageView;
    Button button_order;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_basket, container, false);
        Toolbar toolbar = view.findViewById(R.id.toolbar);
        ((AppCompatActivity)getActivity()).setSupportActionBar(toolbar);
        ((AppCompatActivity)getActivity()).setTitle("Оформить заказ");

        recyclerView =(RecyclerView) view.findViewById(R.id.recycler_view_basket);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        user = FirebaseAuth.getInstance().getCurrentUser();

        final String[] addressToDeliver = new String[1];
        final String[] clientName = new String[1];
        final String[] clientPhone = new String[1];

        DatabaseReference reference = FirebaseDatabase.getInstance().getReference("Users")
                .child(user.getUid());
        reference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                UserInfo userInfo = dataSnapshot.getValue(UserInfo.class);

                addressToDeliver[0] = userInfo.getAddress();
                clientName[0] = userInfo.getUsername();
                clientPhone[0] = userInfo.getPhone();
                Toast.makeText(getContext(), "address: " + addressToDeliver[0], Toast.LENGTH_LONG).show();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        getClientData();

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
        getFirstCategory(addressToDeliver[0], clientName[0], clientPhone[0]);
        return view;
    }

    private void getClientData(){

    }

    private void orderNow() {

        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference("Order");
//        String userId = databaseReference.push().getKey();
        reference.child(user.getUid())
                .setValue(addToBasketsList)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        Toast.makeText(getActivity(), "Блюдо заказано", Toast.LENGTH_SHORT).show();
                    }
                });
    }

    private void getFirstCategory(final String addressToDeliver, final String clientName, final String clientPhone){
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        databaseReference = FirebaseDatabase.getInstance().getReference("uploads").child("basket").child(user.getUid());

        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot postSnapshot : dataSnapshot.getChildren()) {
                    AddToBasket addToBasket = postSnapshot.getValue(AddToBasket.class);
                    assert addToBasket != null;
                    Toast.makeText(getContext(),"Address to deliver: "+ addressToDeliver, Toast.LENGTH_LONG).show();
                    addToBasket.setAddressToDeliver(addressToDeliver);
                    addToBasket.setClientName(clientName);
                    addToBasket.setClientPhone(clientPhone);

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
