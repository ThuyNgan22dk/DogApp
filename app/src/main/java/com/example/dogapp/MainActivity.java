package com.example.dogapp;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.SearchView;
import android.widget.Toast;

import com.example.dogapp.model.DogBreed;
import com.example.dogapp.viewmodel.DogAdapter;
import com.example.dogapp.viewmodel.DogsApiService;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

//        apiService = new DogsApiService();
//        apiService.getDogs()
//                .subscribeOn(Schedulers.newThread())
//                .observeOn(AndroidSchedulers.mainThread())
//                .subscribeWith(new DisposableSingleObserver<List<DogBreed>>() {
//                    @Override
//                    public void onSuccess(@NonNull List<DogBreed> dogBreeds) {
//                        Log.d("DEBUG", "Success");
//                        Toast.makeText(MainActivity.this, "Request successfull ...", Toast.LENGTH_LONG).show();
//                        for (DogBreed dog: dogBreeds){
//                            Log.d("DEBUG", "" + dog.getName());
//                            dogList.add(new DogBreed(dog.getId(),dog.getName(),dog.getLifeSpan(),dog.getOrigin(),dog.getUrl()));
//                        }
//                        adapter = new DogAdapter(dogList, MainActivity.this);
//                        gvDog.setAdapter(adapter);
//                    }
//                    @Override
//                    public void onError(@NonNull Throwable e) {
//                        Log.d("DEBUG", "Fail");
//                        Toast.makeText(MainActivity.this, "An error occurred try again later", Toast.LENGTH_LONG).show();
//                    }
//                });



    }

}

