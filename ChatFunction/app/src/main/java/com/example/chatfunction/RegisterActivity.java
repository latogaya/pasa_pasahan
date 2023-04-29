package com.example.chatfunction;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;


public class RegisterActivity extends AppCompatActivity {

    EditText name, email, password;
    Button registerButton;
    FirebaseAuth auth = FirebaseAuth.getInstance();
    DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReferenceFromUrl("https://gotcha-6c562-default-rtdb.asia-southeast1.firebasedatabase.app/");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        name = findViewById(R.id.newNameET);
        email = findViewById(R.id.newEmailET);
        password = findViewById(R.id.newPasswordET);
        registerButton = findViewById(R.id.regisBtn);

        registerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String nameTxt = name.getText().toString();
                String emailTxt = email.getText().toString();
                String passwordTxt = password.getText().toString();

                if (nameTxt.isEmpty() || emailTxt.isEmpty() || passwordTxt.isEmpty()) {
                    Toast.makeText(RegisterActivity.this, "All fields are required!", Toast.LENGTH_SHORT).show();
                } else if (passwordTxt.length() < 8) {
                    Toast.makeText(RegisterActivity.this, "Password must be at least 8 characters", Toast.LENGTH_SHORT).show();
                } else {
                    databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {

                            if (snapshot.child("Users").hasChild(nameTxt)){
                                Toast.makeText(RegisterActivity.this, "Name already exists", Toast.LENGTH_SHORT).show();
                            }else{
                                databaseReference.child("Users").child(nameTxt).child("email").setValue(emailTxt);
                                databaseReference.child("Users").child(nameTxt).child("password").setValue(passwordTxt);

                                Toast.makeText(RegisterActivity.this, "Success!", Toast.LENGTH_SHORT).show();

                                Intent intent = new Intent(RegisterActivity.this, ChatMainActivity.class);
                                startActivity(intent);
                                finish();
                            }
                        }
                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {
                        }
                    });

                    register(nameTxt, emailTxt, passwordTxt);
                }
            }
        });
    }
    private void register(String name, String email, String password){
        auth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            FirebaseUser firebaseUser = auth.getCurrentUser();
                            String userid = firebaseUser.getUid();

                            databaseReference = FirebaseDatabase.getInstance().getReference("Users").child(userid);

                            HashMap<String, String> hashMap = new HashMap<>();
                            hashMap.put("id", userid);
                            hashMap.put("name", name);
                            hashMap.put("imageURL", "default");

                            databaseReference.setValue(hashMap).addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    if (task.isSuccessful()){
                                        Intent intent = new Intent(RegisterActivity.this, LoginActivity.class);
                                        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                                        startActivity(intent);
                                        finish();
                                    }
                                }
                            });
                        }else{
                            Toast.makeText(RegisterActivity.this, "Invalid email or password", Toast.LENGTH_SHORT).show();
                        }
                    }
                });

    }

}
