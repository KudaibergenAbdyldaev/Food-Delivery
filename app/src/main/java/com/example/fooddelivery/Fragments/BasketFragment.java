package com.example.fooddelivery.Fragments;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
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
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class BasketFragment extends Fragment implements BasketAdapter.OnItemClickListener {

    private RecyclerView recyclerView;
    private FirebaseStorage firebaseStorage;
    private DatabaseReference databaseReference;
    private ValueEventListener mDBListener;
    private List<AddToBasket> addToBasketsList = new ArrayList<>();
    FirebaseUser user;
    TextView title,amount,price;
    ImageView imageView;
    Button button_order;
    final String[] addressToDeliver = new String[1];
    final String[] clientName = new String[1];
    final String[] clientPhone = new String[1];
    BasketAdapter adapter;

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

        adapter = new BasketAdapter(getContext(), addToBasketsList);
        adapter.setOnItemClickListener(BasketFragment.this);
        recyclerView.setAdapter(adapter);

        user = FirebaseAuth.getInstance().getCurrentUser();
        firebaseStorage = FirebaseStorage.getInstance();
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        databaseReference = FirebaseDatabase.getInstance().getReference("uploads").child("basket").child(user.getUid());

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

        {
            final DatabaseReference reference = FirebaseDatabase
                    .getInstance()
                    .getReference("Users")
                    .child(user.getUid());
            reference.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                    UserInfo userInfo = dataSnapshot.getValue(UserInfo.class);

                    addressToDeliver[0] = userInfo.getAddress();
                    clientName[0] = userInfo.getUsername();
                    clientPhone[0] = userInfo.getPhone();
                    getFirstCategory(addressToDeliver[0], clientName[0], clientPhone[0]);

                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {

                }
            });
        }

        return view;
    }



    private void orderNow() {

        final FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference("Order");
        AddToBasket basket = new AddToBasket();

        reference.child(user.getUid())
                .setValue(addToBasketsList, basket.getClientName())
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        FirebaseUser user= FirebaseAuth.getInstance().getCurrentUser();
                        DatabaseReference mPostReference = FirebaseDatabase.getInstance().getReference("uploads")
                                .child("basket").child(user.getUid());
                        mPostReference.removeValue();
                        Toast.makeText(getActivity(), "Блюдо заказано", Toast.LENGTH_SHORT).show();
                    }
                });

    }

    private void getFirstCategory(final String address, final String name, final String phone){


        mDBListener = databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                addToBasketsList.clear();
                for (DataSnapshot postSnapshot : dataSnapshot.getChildren()) {
                    AddToBasket addToBasket = postSnapshot.getValue(AddToBasket.class);
                    {
                        addToBasket.setAddressToDeliver(address);
                        addToBasket.setClientName(name);
                        addToBasket.setClientPhone(phone);
                        addToBasket.setAmount("1");
                    }
                    addToBasket.setKey(postSnapshot.getKey());
                    addToBasketsList.add(addToBasket);
                }

                adapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        setHasOptionsMenu(true);
        super.onCreate(savedInstanceState);
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.close_menu, menu);
        super.onCreateOptionsMenu(menu, inflater);
    }
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();
        if(id == R.id.delete_item){

            return true;
        }

        return super.onOptionsItemSelected(item);
    }


    @Override
    public void onItemClick(int position) {
        Toast.makeText(getActivity(), "Normal click at position: " + position, Toast.LENGTH_SHORT).show();

    }

    @Override
    public void onDeleteClick(int position) {
        AddToBasket selectedItem = addToBasketsList.get(position);
        final String selectedKey = selectedItem.getKey();

        databaseReference.child(selectedKey).removeValue();

    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        databaseReference.removeEventListener(mDBListener);
    }
}
