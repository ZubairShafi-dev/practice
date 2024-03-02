package com.codingempires.practice;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.codingempires.practice.databinding.ActivitySignupBinding;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import org.checkerframework.checker.nullness.qual.NonNull;

public class Activity_Signup extends AppCompatActivity {

    private ActivitySignupBinding binding;
    private FirebaseFirestore firestore; // Add this line to declare the FirebaseFirestore object

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivitySignupBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        firestore = FirebaseFirestore.getInstance();

        binding.editTextEmail.getText();
        binding.editTextName.getText();
        binding.editTextPassword.getText();
        binding.btnSignUp.setOnClickListener(v -> {

            String Username = String.valueOf(binding.editTextName.getText());
            String Email = String.valueOf(binding.editTextEmail.getText());
            String Password = String.valueOf(binding.editTextPassword.getText());

            registration(Email, Password, Username);
        });
    }

    private void registration(String Email, String Password, String Username) {
        UserModel modelUser = new UserModel(Email, Password, Username, null);

        firestore.collection("users").whereEqualTo("email", Email).get()
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        QuerySnapshot querySnapshot = task.getResult();
                        if (querySnapshot != null && !querySnapshot.isEmpty()) {
                            // Email already exists
                            Toast.makeText(Activity_Signup.this, "Email is already in use", Toast.LENGTH_SHORT).show();
                        } else {

                            firestore.collection("users").add(modelUser)
                                    .addOnCompleteListener(registerTask -> {
                                        if (registerTask.isSuccessful()) {
                                            String documentId = registerTask.getResult().getId();

                                            modelUser.setId(documentId);

                                            // Now, update the user's document with the ID
                                            firestore.collection("users").document(documentId)
                                                    .set(modelUser)
                                                    .addOnSuccessListener(new OnSuccessListener<Void>() {
                                                        @Override
                                                        public void onSuccess(Void aVoid) {
                                                            Toast.makeText(Activity_Signup.this, "Registration successful", Toast.LENGTH_SHORT).show();
                                                            startActivity(new Intent(Activity_Signup.this, MainActivity.class));
                                                            // Optionally, navigate to another screen or perform other actions
                                                        }
                                                    })
                                                    .addOnFailureListener(new OnFailureListener() {
                                                        @Override
                                                        public void onFailure(@NonNull Exception e) {
                                                            Toast.makeText(Activity_Signup.this, "Failed to update user data", Toast.LENGTH_SHORT).show();
                                                        }
                                                    });
                                        } else {
                                            Toast.makeText(Activity_Signup.this, "Failed to register", Toast.LENGTH_SHORT).show();
                                        }
                                    });
                        }

                    }
                });
    }
}
