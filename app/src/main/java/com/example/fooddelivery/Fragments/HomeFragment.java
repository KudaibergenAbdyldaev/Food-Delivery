package com.example.fooddelivery.Fragments;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;

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
import android.widget.TextView;
import android.widget.Toast;

import com.example.fooddelivery.Adapter.FoodAdapter;
import com.example.fooddelivery.Models.Upload;
import com.example.fooddelivery.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.w3c.dom.Text;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class HomeFragment extends Fragment {

    private RecyclerView recyclerView;
    private FoodAdapter adapter;
    private CardView card_baking, card_dessert, card_drinks, card_meat, card_milk;
    CardView card_add_product;
    private DatabaseReference databaseReference;
    private List<Upload> uploadList;
    private TextView txt_baking, txt_meat, txt_milk, txt_drink, txt_dessert;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_home, container, false);

        Toolbar toolbar = view.findViewById(R.id.toolbar);
        ((AppCompatActivity)getActivity()).setSupportActionBar(toolbar);
        ((AppCompatActivity)getActivity()).setTitle("Food Delivery");

        recyclerView =(RecyclerView) view.findViewById(R.id.recycler_view);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new GridLayoutManager(getActivity(),2));

        txt_baking = (TextView) view.findViewById(R.id.txt_baking);
        txt_meat = (TextView) view.findViewById(R.id.txt_meat);
        txt_milk = (TextView) view.findViewById(R.id.txt_milk);
        txt_drink = (TextView) view.findViewById(R.id.txt_drinks);
        txt_dessert = (TextView) view.findViewById(R.id.txt_dessert);

        uploadList = new ArrayList<>();

        getFirstCategory();

        card_baking = (CardView) view.findViewById(R.id.card_baking);
        card_baking.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                databaseReference = FirebaseDatabase.getInstance().getReference("uploads").child("food").child("baking");

                txt_baking.setTextColor(getResources().getColor(R.color.white));
                card_baking.setCardBackgroundColor(getResources().getColor(R.color.colorPrimary));

                txt_meat.setTextColor(getResources().getColor(R.color.colorPrimary));
                card_meat.setCardBackgroundColor(getResources().getColor(R.color.white));
                txt_milk.setTextColor(getResources().getColor(R.color.colorPrimary));
                card_milk.setCardBackgroundColor(getResources().getColor(R.color.white));
                txt_drink.setTextColor(getResources().getColor(R.color.colorPrimary));
                card_drinks.setCardBackgroundColor(getResources().getColor(R.color.white));
                txt_dessert.setTextColor(getResources().getColor(R.color.colorPrimary));
                card_dessert.setCardBackgroundColor(getResources().getColor(R.color.white));

                final List<Upload> finalUploadList = uploadList;
                databaseReference.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        uploadList.clear();
                        for (DataSnapshot postSnapshot : dataSnapshot.getChildren()) {
                            Upload upload = postSnapshot.getValue(Upload.class);
                            finalUploadList.add(upload);
                        }

                        adapter = new FoodAdapter(getActivity(), finalUploadList);

                        recyclerView.setAdapter(adapter);
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {
                        Toast.makeText(getActivity(), databaseError.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });
        card_meat = (CardView) view.findViewById(R.id.card_meat);
        card_meat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                databaseReference = FirebaseDatabase.getInstance().getReference("uploads").child("food").child("meat");

                txt_meat.setTextColor(getResources().getColor(R.color.white));
                card_meat.setCardBackgroundColor(getResources().getColor(R.color.colorPrimary));

                txt_baking.setTextColor(getResources().getColor(R.color.colorPrimary));
                card_baking.setCardBackgroundColor(getResources().getColor(R.color.white));
                txt_milk.setTextColor(getResources().getColor(R.color.colorPrimary));
                card_milk.setCardBackgroundColor(getResources().getColor(R.color.white));
                txt_drink.setTextColor(getResources().getColor(R.color.colorPrimary));
                card_drinks.setCardBackgroundColor(getResources().getColor(R.color.white));
                txt_dessert.setTextColor(getResources().getColor(R.color.colorPrimary));
                card_dessert.setCardBackgroundColor(getResources().getColor(R.color.white));

                final List<Upload> finalUploadList = uploadList;

                databaseReference.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        uploadList.clear();
                        for (DataSnapshot postSnapshot : dataSnapshot.getChildren()) {
                            Upload upload = postSnapshot.getValue(Upload.class);
                            finalUploadList.add(upload);
                        }

                        adapter = new FoodAdapter(getActivity(), finalUploadList);

                        recyclerView.setAdapter(adapter);
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {
                        Toast.makeText(getActivity(), databaseError.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });

        card_milk = (CardView) view.findViewById(R.id.card_milk);
        card_milk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                databaseReference = FirebaseDatabase.getInstance().getReference("uploads").child("food").child("milk");
                txt_milk.setTextColor(getResources().getColor(R.color.white));
                card_milk.setCardBackgroundColor(getResources().getColor(R.color.colorPrimary));

                txt_meat.setTextColor(getResources().getColor(R.color.colorPrimary));
                card_meat.setCardBackgroundColor(getResources().getColor(R.color.white));
                txt_baking.setTextColor(getResources().getColor(R.color.colorPrimary));
                card_baking.setCardBackgroundColor(getResources().getColor(R.color.white));
                txt_drink.setTextColor(getResources().getColor(R.color.colorPrimary));
                card_drinks.setCardBackgroundColor(getResources().getColor(R.color.white));
                txt_dessert.setTextColor(getResources().getColor(R.color.colorPrimary));
                card_dessert.setCardBackgroundColor(getResources().getColor(R.color.white));

                final List<Upload> finalUploadList = uploadList;
                databaseReference.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        uploadList.clear();
                        for (DataSnapshot postSnapshot : dataSnapshot.getChildren()) {
                            Upload upload = postSnapshot.getValue(Upload.class);
                            finalUploadList.add(upload);
                        }

                        adapter = new FoodAdapter(getActivity(), finalUploadList);

                        recyclerView.setAdapter(adapter);
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {
                        Toast.makeText(getActivity(), databaseError.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });

        card_drinks = (CardView) view.findViewById(R.id.card_drinks);
        card_drinks.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                databaseReference = FirebaseDatabase.getInstance().getReference("uploads").child("food").child("drinks");
                txt_drink.setTextColor(getResources().getColor(R.color.white));
                card_drinks.setCardBackgroundColor(getResources().getColor(R.color.colorPrimary));

                txt_milk.setTextColor(getResources().getColor(R.color.colorPrimary));
                card_milk.setCardBackgroundColor(getResources().getColor(R.color.white));
                txt_meat.setTextColor(getResources().getColor(R.color.colorPrimary));
                card_meat.setCardBackgroundColor(getResources().getColor(R.color.white));
                txt_baking.setTextColor(getResources().getColor(R.color.colorPrimary));
                card_baking.setCardBackgroundColor(getResources().getColor(R.color.white));
                txt_dessert.setTextColor(getResources().getColor(R.color.colorPrimary));
                card_dessert.setCardBackgroundColor(getResources().getColor(R.color.white));

                final List<Upload> finalUploadList = uploadList;
                databaseReference.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        uploadList.clear();
                        for (DataSnapshot postSnapshot : dataSnapshot.getChildren()) {
                            Upload upload = postSnapshot.getValue(Upload.class);
                            finalUploadList.add(upload);
                        }

                        adapter = new FoodAdapter(getActivity(), finalUploadList);

                        recyclerView.setAdapter(adapter);
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {
                        Toast.makeText(getActivity(), databaseError.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });

        card_dessert = (CardView)view.findViewById(R.id.dessert);
        card_dessert.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                databaseReference = FirebaseDatabase.getInstance().getReference("uploads").child("food").child("dessert");
                txt_dessert.setTextColor(getResources().getColor(R.color.white));
                card_dessert.setCardBackgroundColor(getResources().getColor(R.color.colorPrimary));

                txt_drink.setTextColor(getResources().getColor(R.color.colorPrimary));
                card_drinks.setCardBackgroundColor(getResources().getColor(R.color.white));
                txt_milk.setTextColor(getResources().getColor(R.color.colorPrimary));
                card_milk.setCardBackgroundColor(getResources().getColor(R.color.white));
                txt_meat.setTextColor(getResources().getColor(R.color.colorPrimary));
                card_meat.setCardBackgroundColor(getResources().getColor(R.color.white));
                txt_baking.setTextColor(getResources().getColor(R.color.colorPrimary));
                card_baking.setCardBackgroundColor(getResources().getColor(R.color.white));

                final List<Upload> finalUploadList = uploadList;
                databaseReference.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        uploadList.clear();
                        for (DataSnapshot postSnapshot : dataSnapshot.getChildren()) {
                            Upload upload = postSnapshot.getValue(Upload.class);
                            finalUploadList.add(upload);
                        }

                        adapter = new FoodAdapter(getActivity(), finalUploadList);

                        recyclerView.setAdapter(adapter);
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {
                        Toast.makeText(getActivity(), databaseError.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });




        return view;
    }

    private void getFirstCategory(){
        databaseReference = FirebaseDatabase.getInstance().getReference("uploads").child("food").child("baking");

        final List<Upload> finalUploadList = uploadList;
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                uploadList.clear();
                for (DataSnapshot postSnapshot : dataSnapshot.getChildren()) {
                    Upload upload = postSnapshot.getValue(Upload.class);
                    finalUploadList.add(upload);
                }

                adapter = new FoodAdapter(getActivity(), finalUploadList);

                recyclerView.setAdapter(adapter);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Toast.makeText(getActivity(), databaseError.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

}
