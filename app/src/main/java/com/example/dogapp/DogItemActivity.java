package com.example.dogapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.dogapp.model.DogBreed;

public class DogItemActivity extends AppCompatActivity {
    private DogBreed dogBreed;
    private TextView textView;
    private ImageView imageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dog_item);

        textView = findViewById(R.id.id_name);
        imageView = findViewById(R.id.id_img);

        Intent intent = getIntent();
        if (intent.getExtras() != null){
            dogBreed = (DogBreed) intent.getSerializableExtra("data");
            textView.setText(dogBreed.getName());
            Glide.with(this).load(dogBreed.getUrl()).into(imageView);
        }

    }
}