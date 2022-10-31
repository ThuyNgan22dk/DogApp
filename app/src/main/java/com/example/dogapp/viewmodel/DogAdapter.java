package com.example.dogapp.viewmodel;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.RecyclerView;

import com.example.dogapp.DogItemActivity;
import com.example.dogapp.R;
import com.example.dogapp.databinding.ActivityDogItemBinding;
import com.example.dogapp.model.DogBreed;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class DogAdapter extends RecyclerView.Adapter<DogAdapter.ViewHolder> implements Filterable {
    private ArrayList<DogBreed> dogBreedList;
    private ArrayList<DogBreed> dogBreedsCopy;

    public DogAdapter(ArrayList<DogBreed> dogBreeds){
        this.dogBreedList = dogBreeds;
        this.dogBreedsCopy = dogBreeds;
    }

    public Filter getFilter() {
        Filter filter = new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence charSequence) {
                FilterResults filterResults = new FilterResults();
                String searchStr = charSequence.toString().toLowerCase();
                List<DogBreed> resultData = new ArrayList<DogBreed>();
                if (searchStr.isEmpty()) {
                    resultData.addAll(dogBreedsCopy);
                } else {
                    for (DogBreed dogBreed: dogBreedsCopy) {
                        if (dogBreed.getName().contains(searchStr)){
                            resultData.add(dogBreed);
                        }
                    }
                }
//                filterResults.count = resultData.size();
                filterResults.values = resultData;
                return filterResults;
            }

            @Override
            protected void publishResults(CharSequence charSequence, FilterResults filterResults) {
                dogBreedList = new ArrayList<>();
                dogBreedList.addAll((List)filterResults.values);
                notifyDataSetChanged();
            }
        };
        return filter;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
//        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.activity_dog_item,parent, false);
        ActivityDogItemBinding binding = DataBindingUtil.inflate(
                LayoutInflater.from(parent.getContext()),R.layout.activity_dog_item,parent,false);
        return new ViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
//        holder.tvName.setText(dogBreedList.get(position).getName());
//        holder.tvOrigin.setText(dogBreedList.get(position).getOrigin());
        holder.binding.setDog(dogBreedList.get(position));
        Picasso.get().load(dogBreedList.get(position).getUrl()).into(holder.binding.ivAvatar);
    }

    @Override
    public int getItemCount() {
        return dogBreedList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        public ActivityDogItemBinding binding;

        public ViewHolder(ActivityDogItemBinding itemBinding) {
            super(itemBinding.getRoot());
            this.binding = itemBinding;

            binding.ivAvatar.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    DogBreed dog = dogBreedList.get(getAdapterPosition());
                    Bundle bundle = new Bundle();
                    bundle.putSerializable("dogBreed", dog);
                    Navigation.findNavController(view).navigate(R.id.detailsFragment,bundle);
                }
            });
        }
    }

}
