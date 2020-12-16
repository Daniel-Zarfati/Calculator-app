package com.example.calcapphit;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.firestore.FirebaseFirestore;

public class SignUp extends AppCompatActivity {
    private FirebaseAuth mAuth;
    FirebaseFirestore db = FirebaseFirestore.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);
        mAuth = FirebaseAuth.getInstance();
    }


    public void Signup(View view) {

        TextView t = findViewById(R.id.SiEmail);
        String email = t.getText().toString();
        TextView s = findViewById(R.id.SiPassword);
        String password = s.getText().toString();
        TextView p = findViewById(R.id.SiPhoneNumber);
        String PhoneNumber = p.getText().toString();
        TextView a = findViewById(R.id.SiAge);
        String Age = a.getText().toString();

        mAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            Toast.makeText(SignUp.this, "Authentication Succeeded.",
                                    Toast.LENGTH_LONG).show();

                            String Uid=mAuth.getCurrentUser().getUid();
                            FirebaseDatabase database = FirebaseDatabase.getInstance();
                            DatabaseReference myRef = database.getReference("User").child(Uid);
                            User user=new User(email,password,PhoneNumber);
                            myRef.setValue(user);

                            Intent intent = new Intent(SignUp.this, LoginReg.class);
                            startActivity(intent);
                        } else {
                            // If sign in fails, display a message to the user.
                            Toast.makeText(SignUp.this, "Authentication failed.",
                                    Toast.LENGTH_LONG).show();
                        }
                        // ...
                    }
                });

        }

    }

