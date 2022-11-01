package com.example.dogapp.viewmodel;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.GestureDetector;
import android.view.LayoutInflater;
import android.view.MotionEvent;
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
import java.util.Locale;

public class DogAdapter extends RecyclerView.Adapter<DogAdapter.ViewHolder> implements Filterable {
    private ArrayList<DogBreed> dogBreedList;
    private ArrayList<DogBreed> dogBreedsCopy;

    public DogAdapter(ArrayList<DogBreed> dogBreeds){
        this.dogBreedList = dogBreeds;
        this.dogBreedsCopy = dogBreeds;
    }

    public Filter getFilter() {
        return new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence charSequence) {
                String searchStr = charSequence.toString().toLowerCase();
                List<DogBreed> resultData = new ArrayList<DogBreed>();
                if (searchStr.isEmpty()) {
                    resultData.addAll(dogBreedsCopy);
                } else {
                    for (DogBreed dog: dogBreedsCopy) {
                        if (dog.getName().toLowerCase().contains(searchStr)){
                            resultData.add(dog);
                        }
                    }
                }
                FilterResults filterResults = new FilterResults();
                filterResults.count = resultData.size();
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

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    DogBreed dog = dogBreedList.get(getAdapterPosition());
                    Bundle bundle = new Bundle();
                    bundle.putSerializable("dogBreed", dog);
                    Navigation.findNavController(view).navigate(R.id.detailsFragment,bundle);
                }
            });
            itemView.setOnTouchListener(new onSwipeTouchListener(){
                @Override
                public void onSwipeLeft(){
                    if (binding.layout1.getVisibility() == View.GONE){
                        binding.layout1.setVisibility(View.VISIBLE);
                        binding.layout2.setVisibility(View.GONE);
                    } else {
                        binding.layout1.setVisibility(View.GONE);
                        binding.layout2.setVisibility(View.VISIBLE);
                    }
                    super.onSwipeLeft();
                }
            });
        }
    }

    public class onSwipeTouchListener implements View.OnTouchListener {
        private final GestureDetector gestureDetector = new GestureDetector(new GestureListener());

        @Override
        public boolean onTouch(View v, MotionEvent event) {
            return gestureDetector.onTouchEvent(event);
        }

        private final class GestureListener extends GestureDetector.SimpleOnGestureListener {

            private static final int SWIPE_THRESHOLD = 50;
            private static final int SWIPE_VELOCITY_THRESHOLD = 50;

            @Override
            public boolean onDown(MotionEvent e) {
                return true;
            }

            @Override
            public boolean onSingleTapUp(MotionEvent e) {
                onClick();
                return true;
            }

            @Override
            public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
                boolean result = false;
                try {
                    float diffY = e2.getY() - e1.getY();
                    float diffX = e2.getX() - e1.getX();
                    if (Math.abs(diffX) > Math.abs(diffY)) {
                        if (Math.abs(diffX) > SWIPE_THRESHOLD && Math.abs(velocityX) > SWIPE_VELOCITY_THRESHOLD) {
                            if (diffX > 0) {
                                onSwipeRight();
                            } else {
                                onSwipeLeft();
                            }
                        }
                        result = true;
                    } else if (Math.abs(diffY) > SWIPE_THRESHOLD && Math.abs(velocityY) > SWIPE_VELOCITY_THRESHOLD) {
                        if (diffY > 0) {
                            onSwipeBottom();
                        } else {
                            onSwipeTop();
                        }
                    }
                    result = true;

                } catch (Exception exception) {
                    exception.printStackTrace();
                }
                return result;
            }
        }

        public void onSwipeRight() {
        }

        public void onSwipeLeft() {
        }

        public void onSwipeTop() {
        }

        public void onSwipeBottom() {
        }

        public void onClick() {
        }
    }

}
