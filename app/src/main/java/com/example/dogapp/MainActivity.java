package com.example.dogapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.SearchView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.dogapp.model.DogBreed;
import com.example.dogapp.viewmodel.DogsApiService;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.annotations.NonNull;
import io.reactivex.rxjava3.observers.DisposableSingleObserver;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class MainActivity extends AppCompatActivity {
    private Context context;
    private SearchView searchView;
    private DogsApiService apiService;
    private ArrayList<DogBreed> dogList;
    private DogAdapter adapter;

    private GridView gvDog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        context = this;

        gvDog = findViewById(R.id.id_gv_dog);

        // created new array list..
        dogList = new ArrayList<>();

        apiService = new DogsApiService();
        apiService.getDogs()
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(new DisposableSingleObserver<List<DogBreed>>() {
                    @Override
                    public void onSuccess(@NonNull List<DogBreed> dogBreeds) {
                        Log.d("DEBUG", "Success");
                        Toast.makeText(MainActivity.this, "Request successfull ...", Toast.LENGTH_LONG).show();
                        for (DogBreed dog: dogBreeds){
                            Log.d("DEBUG", "" + dog.getName());
                            dogList.add(new DogBreed(dog.getId(),dog.getName(),dog.getLifeSpan(),dog.getOrigin(),dog.getUrl()));
                        }
                        adapter = new DogAdapter(dogList, MainActivity.this);
                        gvDog.setAdapter(adapter);
                    }
                    @Override
                    public void onError(@NonNull Throwable e) {
                        Log.d("DEBUG", "Fail");
                        Toast.makeText(MainActivity.this, "An error occurred try again later", Toast.LENGTH_LONG).show();
                    }
                });

//        Search dog
        searchView = findViewById(R.id.search_view);
        searchView.clearFocus();
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String s) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String s) {
                filterList(s);
                return true;
            }
        });

//        see dog item
        gvDog.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                String message = "Name : " + dogList.get(i).getName();
                Toast.makeText(MainActivity.this, message, Toast.LENGTH_SHORT).show();
                startActivity(new Intent(MainActivity.this,DogItemActivity.class).putExtra("data", dogList.get(i)));
            }
        });
    }

    private void filterList(String s) {
        List<DogBreed> filteredList = new ArrayList<>();
        for (DogBreed dog : dogList){
            if (dog.getName().toLowerCase().contains(s.toLowerCase())){
                filteredList.add(dog);
            }
        }
        
        if (filteredList.isEmpty()){
            Toast.makeText(this, "No data found", Toast.LENGTH_SHORT).show();
        }else{
            adapter.setFilteredList(filteredList);
        }
    }

//    @Override
//    public boolean onCreateOptionsMenu(Menu menu) {
//        getMenuInflater().inflate(R.menu.top_bar_app, menu);
//
//        searchView = (SearchView) menu.findItem(R.id.actionSearch).getActionView();
//        searchView.setMaxWidth(Integer.MAX_VALUE);
//
//        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
//            @Override
//            public boolean onQueryTextSubmit(String s) {
//                return true;
//            }
//
//            @Override
//            public boolean onQueryTextChange(String s) {
//                adapter.getFilter().filter(s);
//                return true;
//            }
//        });
//        return true;
//    }
//
//    @Override
//    public boolean onOptionsItemSelected(MenuItem item) {
//        int id = item.getItemId();
//        if (id == R.id.actionSearch) {
//            return true;
//        }
//        return  super.onOptionsItemSelected(item);
//    }

    public class DogAdapter extends BaseAdapter implements Filterable {
        private List<DogBreed> dogBreedList;
        private Context context;
        private LayoutInflater layoutInflater;

        public DogAdapter(List<DogBreed> dogBreedList, Context context) {
            this.dogBreedList = dogBreedList;
            this.context = context;
            this.layoutInflater = (LayoutInflater) context.getSystemService(LAYOUT_INFLATER_SERVICE);
        }

        @Override
        public int getCount() {
            return dogBreedList.size();
        }

        @Override
        public Object getItem(int i) {
            return null;
        }

        @Override
        public long getItemId(int i) {
            return 0;
        }

        @Override
        public View getView(int i, View view, ViewGroup viewGroup) {
            if (view == null) {
                view = layoutInflater.inflate(R.layout.card_item, viewGroup, false);
            }
            ImageView imageView = view.findViewById(R.id.id_dog_img);
            TextView textView = view.findViewById(R.id.id_dog_name);

            textView.setText(dogBreedList.get(i).getName());
            Glide.with(context).load(dogBreedList.get(i).getUrl()).into(imageView);

            return view;
        }

        @Override
        public Filter getFilter() {
            Filter filter = new Filter() {
                @Override
                protected FilterResults performFiltering(CharSequence charSequence) {
                    FilterResults filterResults = new FilterResults();
                    if (charSequence == null || charSequence.length() == 0) {
                        filterResults.count = dogBreedList.size();
                        filterResults.values = dogBreedList;
                    } else {
                        String searchStr = charSequence.toString().toLowerCase();
                        List<DogBreed> resultData = new ArrayList<>();
                        for (DogBreed dogBreed: dogList) {
                            if (dogBreed.getName().contains(searchStr)){
                                resultData.add(dogBreed);
                            }
                            filterResults.count = resultData.size();
                            filterResults.values = resultData;
                        }
                    }
                    return filterResults;
                }

                @Override
                protected void publishResults(CharSequence charSequence, FilterResults filterResults) {

                }
            };
            return filter;
        }

        public void setFilteredList(List<DogBreed> filteredList) {
            this.dogBreedList = filteredList;
            notifyDataSetChanged();
        }
    }
}

