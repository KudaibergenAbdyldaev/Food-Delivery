package com.example.fooddelivery.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.fooddelivery.Models.AddToBasket;
import com.example.fooddelivery.R;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.storage.StorageReference;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

public class BasketAdapter extends RecyclerView.Adapter<BasketAdapter.Holder> {

    StorageReference storageReference;
    DatabaseReference reference;
    private Context context;
    private List<AddToBasket> basketList;
//    private int count = 0;

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
    public void onBindViewHolder(@NonNull Holder holder, final int position) {
        final AddToBasket addToBasket = basketList.get(position);
        holder.txt_name.setText(addToBasket.getmName());
        holder.txt_price.setText(addToBasket.getPrice());
        Picasso.get().load(basketList.get(position).getImageUrl()).into(holder.imageView);

//        holder.txt_plus.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
////                cnt=(TextView) item.findViewById(R.id.textView); //cnt - обьект "надпись"
////                CharSequence zz = cnt.getText(); // получем содержимое обьекта
////                int pz= Integer.valueOf(zz.toString()); // преобразовываем в число
////                pz++; // прибавляем 1
////                cnt.setText(Integer.toString(pz)); // преобразовываем в строку и возвращаем в обьект "надпись"
//            }
//        });

//        holder.btn.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
//                storageReference = FirebaseStorage.getInstance().getReference("Order");
//                reference = FirebaseDatabase.getInstance().getReference("Order");
//                OrderModel orderModel = new OrderModel(
//                        orderModelList.get(position).getTitle(),
//                        orderModelList.get(position).getPrice(),
//                        orderModelList.get(position).getAmount(),
//                        orderModelList.get(position).getImageUrl()
//                );
//                orderModelList.add(orderModel);
//                if(!orderModelList.isEmpty()) {
//                    reference.child(user.getUid())
//                            .setValue(orderModelList)
//                            .addOnSuccessListener(new OnSuccessListener<Void>() {
//                                @Override
//                                public void onSuccess(Void aVoid) {
//                                    Toast.makeText(context, "Блюдо добавлено в корзину", Toast.LENGTH_SHORT).show();
//                                }
//                            });
//                }else{
//                    Toast.makeText(context, "Basket is empty", Toast.LENGTH_SHORT).show();
//                }
//
//            }
//        });
    }

    @Override
    public int getItemCount() {
        return basketList.size();
    }

    public class Holder extends RecyclerView.ViewHolder {

//        public Button btn;
        public TextView txt_name, txt_price, txt_amount, txt_minus, txt_plus;
        public ImageView imageView;
        public CardView cardView;

        public Holder(@NonNull View itemView) {
            super(itemView);

            txt_name = itemView.findViewById(R.id.title_basket);
            txt_price = itemView.findViewById(R.id.price_basket);
            txt_amount = itemView.findViewById(R.id.txt_count);
            imageView = itemView.findViewById(R.id.img_basket);
            cardView = itemView.findViewById(R.id.card_basket);
            txt_minus = itemView.findViewById(R.id.txt_minus);
            txt_plus = itemView.findViewById(R.id.txt_plus);
//            btn = itemView.findViewById(R.id.btn_order);
        }
    }
}
