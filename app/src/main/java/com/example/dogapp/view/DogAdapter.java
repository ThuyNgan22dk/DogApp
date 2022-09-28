package com.example.dogapp.view;

import android.content.Context;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.dogapp.R;
import com.example.dogapp.model.DogBreed;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class DogAdapter extends RecyclerView.Adapter<DogAdapter.RecyclerViewHolder> implements Filterable {

    private ArrayList<DogBreed> dogLists;
    private ArrayList<DogBreed> dogListOlds;
    private Context mcontext;

    public DogAdapter(ArrayList<DogBreed> dogArrayList, Context mcontext) {
        this.dogLists = dogArrayList;
        this.mcontext = mcontext;
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
        DogBreed dogList = dogLists.get(position);
        holder.dogName.setText(dogList.getName());
        holder.dogImg.setImageURI(Uri.parse(dogList.getUrl()));
    }

    @Override
    public int getItemCount() {
        // this method returns the size of recyclerview
        return dogLists.size();
    }

    @Override
    public Filter getFilter() {
        return new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence charSequence) {
                String strSearch = charSequence.toString();
                if (strSearch.isEmpty()){
                    dogLists = dogListOlds;
                } else {
                    ArrayList<DogBreed> dogBreedList = new ArrayList<>();
                    for (DogBreed dog : dogListOlds) {
                        if (dog.getName().toLowerCase().contains(strSearch.toLowerCase())) {
                            dogBreedList.add(dog);
                        }
                    }
                    dogLists = dogBreedList;
                }
                FilterResults filterResults = new FilterResults();
                filterResults.values = dogLists;
                return filterResults;
            }

            @Override
            protected void publishResults(CharSequence charSequence, FilterResults filterResults) {
                dogLists = (ArrayList<DogBreed>) filterResults.values;
                notifyDataSetChanged();
            }
        };
    }

    // View Holder Class to handle Recycler View.
    public static class RecyclerViewHolder extends RecyclerView.ViewHolder {

        private TextView dogName;
        private ImageView dogImg;

        public RecyclerViewHolder(@NonNull View itemView) {
            super(itemView);
            dogName = (TextView) itemView.findViewById(R.id.id_dog_name);
            dogImg = (ImageView) itemView.findViewById(R.id.id_dog_img);
        }
    }

}
