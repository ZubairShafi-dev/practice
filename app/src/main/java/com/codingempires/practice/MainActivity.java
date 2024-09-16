package com.codingempires.practice;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.widget.ImageView;
import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;

public class MainActivity extends AppCompatActivity {

    private static final int IMAGE_PICK_CODE = 1000; // Request code for image picker intent
    private ImageView imageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Initialize the ImageView
        imageView = findViewById(R.id.imageView);

        // Set a click listener on the ImageView
        imageView.setOnClickListener(v -> {
            // Open gallery when ImageView is clicked
            openGallery();
        });
    }

    private void openGallery() {
        // Create an intent to pick an image from the gallery
        Intent intent = new Intent(Intent.ACTION_PICK);
        intent.setType("image/*");
        startActivityForResult(intent, IMAGE_PICK_CODE);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == Activity.RESULT_OK && requestCode == IMAGE_PICK_CODE) {
            // Get the selected image URI from the Intent data
            Uri selectedImageUri = data.getData();

            if (selectedImageUri != null) {
                // Use Glide to load the selected image into the ImageView
                Glide.with(this)
                        .load(selectedImageUri)
                        .into(imageView);
            }
        }
    }
}
