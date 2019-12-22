package com.example.fooddelivery;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.ContentResolver;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.webkit.MimeTypeMap;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.fooddelivery.Models.AddToBasket;
import com.example.fooddelivery.Models.Upload;
import com.example.fooddelivery.Models.UserInfo;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.StorageTask;
import com.google.firebase.storage.UploadTask;
import com.squareup.picasso.Picasso;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class DetailActivity extends AppCompatActivity {
    TextView title, overView, price;
    ImageView imageView;
    Button btn_order;
    DatabaseReference reference;
    StorageReference storageReference;
    String image_url;
    List<AddToBasket> products = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Food Delivery");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        reference = FirebaseDatabase.getInstance().getReference();
        storageReference = FirebaseStorage.getInstance().getReference();

        title = (TextView) findViewById(R.id.title_value);
        overView = (TextView) findViewById(R.id.desc_value);
        price = (TextView) findViewById(R.id.price_value);
        imageView = (ImageView) findViewById(R.id.img);
        btn_order = (Button) findViewById(R.id.add_basket);
        btn_order.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addToBasket();
            }
        });

        final Intent intent = getIntent();

        String title_value = intent.getStringExtra("title");
        title.setText(title_value);

        String price_value = intent.getStringExtra("price");
        price.setText(price_value);

        String over_value = intent.getStringExtra("overView");
        overView.setText(over_value);

        image_url = intent.getStringExtra("img");
        Picasso.get().load(image_url).into(imageView);

    }

    private void addToBasket() {
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        storageReference = FirebaseStorage.getInstance().getReference("uploads").child("basket");
        reference = FirebaseDatabase.getInstance().getReference("uploads").child("basket");
        AddToBasket addToBasket = new AddToBasket(
                title.getText().toString(),
                price.getText().toString(),
                image_url
        );
        products.add(addToBasket);
        reference.child(user.getUid())
                .setValue(products)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        Toast.makeText(DetailActivity.this, "Бюдо добавлено в корзину", Toast.LENGTH_SHORT).show();
                    }
                });
    }
}
