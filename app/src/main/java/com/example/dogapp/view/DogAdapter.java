package com.example.dogapp.view;

import android.content.Context;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.dogapp.R;
import com.example.dogapp.model.DogBreed;

import java.util.ArrayList;

public class DogAdapter extends RecyclerView.Adapter<DogAdapter.RecyclerViewHolder> {

    private ArrayList<DogBreed> dogBreedArrayList;
    private Context mcontext;

    public DogAdapter(ArrayList<DogBreed> dogArrayList, Context mcontext) {
        this.dogBreedArrayList = dogArrayList;
        this.mcontext = mcontext;
    }

    // method for filtering our recyclerview items.
    public void filterList(ArrayList<DogBreed> filterlist) {
        // below line is to add our filtered
        // list in our course array list.
        dogBreedArrayList = filterlist;
        // below line is to notify our adapter
        // as change in recycler view data.
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public RecyclerViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        // Inflate Layout
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.card_item, parent, false);
        return new RecyclerViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerViewHolder holder, int position) {
        // Set the data to textview and imageview.
        DogBreed dogList = dogBreedArrayList.get(position);
        holder.dogName.setText(dogList.getName());
        holder.dogImg.setImageURI(Uri.parse(dogList.getUrl()));
    }

    @Override
    public int getItemCount() {
        // this method returns the size of recyclerview
        return dogBreedArrayList.size();
    }

    // View Holder Class to handle Recycler View.
    public static class RecyclerViewHolder extends RecyclerView.ViewHolder {

        private TextView dogName;
        private ImageView dogImg;

        public RecyclerViewHolder(@NonNull View itemView) {
            super(itemView);
            dogName = itemView.findViewById(R.id.id_dog_name);
            dogImg = itemView.findViewById(R.id.id_dog_img);
        }
    }
}
