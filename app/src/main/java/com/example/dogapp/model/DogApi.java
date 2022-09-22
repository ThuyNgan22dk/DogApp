package com.example.dogapp.model;

import java.util.List;

import io.reactivex.rxjava3.core.Single;
import retrofit2.http.GET;

public interface DogApi {
    @GET("DevTides/DogsApi/master/dogs.json?fbclid=IwAR3B9UApfhGzaV7VsraGxP153yCh7EZ3ZHv8Pqie9cAdLTN5k2ZZYzSUMGQ")
    Single<List<DogBreed>> getDogs();
}
