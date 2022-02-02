package com.example.onlineshop.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
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
import com.example.onlineshop.listener.OnAdapterUpdate;
import com.example.onlineshop.model.Example;

import java.util.List;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

public class FavouriteAdapter extends RecyclerView.Adapter<FavouriteAdapter.FavouriteViewHolder> {

    List<Example> exampleList;
    Context context;
    ShopDatabase database;
    Executor executor = Executors.newSingleThreadExecutor();
    OnAdapterUpdate onAdapterUpdate;

    public FavouriteAdapter(List<Example> exampleList, Context context) {
        this.exampleList = exampleList;
        this.context = context;
        this.database = ShopDatabase.getInstance(context);
    }

    public void setonAdapterUpdate(OnAdapterUpdate onadapterUpdate) {
        this.onAdapterUpdate = onadapterUpdate;
    }

    @NonNull
    @Override
    public FavouriteViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.list_item_favourite, parent, false);

        return new FavouriteViewHolder(view);
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull FavouriteViewHolder holder, int position) {
        Example example = exampleList.get(position);

        Glide.with(context).load(example.getImage()).into(holder.imageViewProduct);

        holder.textViewProductPrice.setText(example.getPrice() + "");
        holder.textViewProductDescription.setText(example.getDescription() + "");
        holder.textViewProductName.setText(example.getTitle() + "");
        holder.textVieCategory.setText(example.getCategory() + "");


    }

    @Override
    public int getItemCount() {
        return exampleList.size();
    }


    class FavouriteViewHolder extends RecyclerView.ViewHolder {

        ImageView imageViewProduct, imageViewDelete;

        TextView textViewProductPrice, textViewProductDescription, textViewProductName, textVieCategory;


        public FavouriteViewHolder(@NonNull View itemView) {
            super(itemView);
            imageViewProduct = itemView.findViewById(R.id.imageViewProduct);
            imageViewDelete = itemView.findViewById(R.id.imageViewDelete);

            textViewProductPrice = itemView.findViewById(R.id.textViewPrice);
            textViewProductDescription = itemView.findViewById(R.id.textViewDescription);
            textViewProductName = itemView.findViewById(R.id.textViewTitle);
            textVieCategory = itemView.findViewById(R.id.textVieCategory);


            imageViewDelete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    deleteProduct(exampleList.get(getAdapterPosition()));
                }
            });
        }
    }

    void deleteProduct(Example example) {
        executor.execute(new Runnable() {
            @Override
            public void run() {
                try {
                    database.productDao().deleteProduct(example);
                }catch (Exception ex){
                    ex.printStackTrace();
                }
            }
        });
        exampleList.remove(example);
        onAdapterUpdate.onAdapterUpdate();
    }
}
