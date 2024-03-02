package com.codingempires.practice;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

public class MainActivity extends AppCompatActivity {
    TextView sinuplink;
    EditText email;
    EditText password;
    Button logInBtn;
    SharedPref mypref;
    FirebaseFirestore firestore;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        sinuplink  = findViewById(R.id.tvSignUpLink);
        email= findViewById(R.id.editTextUsername);
        password= findViewById(R.id.editTextLPassword);
        logInBtn= findViewById(R.id.btnLogin);


        firestore = FirebaseFirestore.getInstance();



        sinuplink.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(MainActivity.this, Activity_Signup.class);
                startActivity(i);
            }
        });
        logInBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String username = String.valueOf(email.getText());
                String Enteredpassword = String.valueOf(password.getText());


                authenticateUser(username, Enteredpassword);

            }

        });

    }
    private boolean authenticateUser(String email, String password) {
        firestore.collection("users")
                .whereEqualTo("email", email)
                .whereEqualTo("password", password)
                .get()
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful() && !task.getResult().isEmpty()) {

                        Toast.makeText(MainActivity.this, "Logged in", Toast.LENGTH_LONG).show();
                        String userId = task.getResult().getDocuments().get(0).getId();

                        // Save user data to SharedPref
                        firestore.collection("users").document(userId).get().addOnCompleteListener(userTask -> {
                            if (userTask.isSuccessful() && userTask.getResult() != null) {
                                DocumentSnapshot document = userTask.getResult();
                                if (document.exists()) {
                                    UserModel user = document.toObject(UserModel.class);
                                    mypref.setUser(user);

                                    startActivity(new Intent(MainActivity.this, Activity_Home.class));
                                    finish();
                                }
                            }
                        });

                    } else {
                        Toast.makeText(MainActivity.this, "Invalid email or password", Toast.LENGTH_SHORT).show();
                    }
                });
        return false;
    }
}