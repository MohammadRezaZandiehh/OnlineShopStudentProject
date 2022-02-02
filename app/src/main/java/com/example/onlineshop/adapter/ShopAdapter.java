package com.example.onlineshop.adapter;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.telephony.SmsManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.onlineshop.R;
import com.example.onlineshop.database.ShopDatabase;
import com.example.onlineshop.listener.ListenerInterface;
import com.example.onlineshop.listener.OnAddProduct;
import com.example.onlineshop.model.Example;

import java.util.List;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

public class ShopAdapter extends RecyclerView.Adapter<ShopAdapter.ShopViewHolder> {

    List<Example> exampleList;
    Context context;

    public ShopAdapter(List<Example> exampleList, Context context/*,OnAddProduct onAddProduct*/) {
        this.exampleList = exampleList;
        this.context = context;
    }

    @NonNull
    @Override
    public ShopViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.list_item_shop, parent, false);

        return new ShopViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ShopViewHolder holder, int position) {
        Example example = exampleList.get(position);

        Glide.with(context).load(example.getImage()).circleCrop().into(holder.imageViewProduct);

        holder.textViewDescription.setText(example.getDescription());
        holder.textVieCategory.setText(example.getCategory());
        holder.textViewCount.setText(example.getRating().getCount().toString());
        holder.textViewRate.setText(example.getRating().getRate().toString());
        holder.textViewPrice.setText(example.getPrice().toString());
        holder.textViewTitle.setText(example.getTitle());

    }

    @Override
    public int getItemCount() {
        return exampleList.size();
    }


    class ShopViewHolder extends RecyclerView.ViewHolder implements ListenerInterface {
        ImageView imageViewProduct,
                imageViewFavourite,
                imageViewSendMessage;
        TextView textViewDescription,
                textVieCategory,
                textViewCount,
                textViewPrice,
                textViewRate,
                textViewTitle;

        public ShopViewHolder(@NonNull View itemView) {
            super(itemView);


            imageViewProduct = itemView.findViewById(R.id.imageViewProduct);
            imageViewFavourite = itemView.findViewById(R.id.imageViewFavourite);
            imageViewSendMessage = itemView.findViewById(R.id.imageViewSendMessage);

            textViewDescription = itemView.findViewById(R.id.textViewDescription);
            textVieCategory = itemView.findViewById(R.id.textVieCategory);
            textViewCount = itemView.findViewById(R.id.textViewCount);
            textViewPrice = itemView.findViewById(R.id.textViewPrice);
            textViewRate = itemView.findViewById(R.id.textViewRate);
            textViewTitle = itemView.findViewById(R.id.textViewTitle);

            imageViewFavourite.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Example example = exampleList.get(getAdapterPosition());
                    ShopDatabase shopDatabase = ShopDatabase.getInstance(context);
                    Executor executor = Executors.newSingleThreadExecutor();
                    executor.execute(new Runnable() {
                        @Override
                        public void run() {
                            shopDatabase.productDao().addProduct(example);
                        }
                    });
                }
            });

            imageViewSendMessage.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Example example = exampleList.get(getAdapterPosition());
                    Intent send_message = new Intent();
                    send_message.setAction(Intent.ACTION_VIEW);
                    send_message.setData(Uri.parse("sms:+33228976453"));
                    send_message.putExtra("sms_body", example.getTitle());
                    context.startActivity(send_message);

//                    String messageText = "message!";
//                    short SMS_PORT = 8901; //you can use a different port if you'd like. I believe it just has to be an int value.
//                    SmsManager smsManager = SmsManager.getDefault();
//                    smsManager.sendDataMessage("8675309", null, SMS_PORT, messageText.getBytes(), null, null);
                }
            });
        }

        @Override
        public void onListenerDescription() {
            textVieCategory.setVisibility(View.GONE);
            textViewTitle.setVisibility(View.GONE);
            textViewDescription.setVisibility(View.VISIBLE);
        }

        @Override
        public void onListenerTitle() {
            textViewTitle.setVisibility(View.VISIBLE);
            textVieCategory.setVisibility(View.GONE);
            textViewDescription.setVisibility(View.GONE);
        }

        @Override
        public void onListenerCategory() {
            textVieCategory.setVisibility(View.VISIBLE);
            textViewTitle.setVisibility(View.GONE);
            textViewDescription.setVisibility(View.GONE);
        }
    }
}