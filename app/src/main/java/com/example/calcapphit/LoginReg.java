package com.example.calcapphit;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;

public class LoginReg extends AppCompatActivity {
    FirebaseFirestore db = FirebaseFirestore.getInstance();
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_reg);
        mAuth = FirebaseAuth.getInstance();

        if (mAuth.getCurrentUser() != null) {
            // User is signed in (getCurrentUser() will be null if not signed in)
            Intent intent = new Intent(LoginReg.this, MainActivity.class);
            startActivity(intent);
            finish();
        }


        //setContentView(R.layout.activity_main);
        EditText user = findViewById(R.id.Email);
        EditText pass = findViewById(R.id.Password);

        SharedPreferences sharedPreferences = getPreferences(MODE_PRIVATE); // PRIVATE FILE IN MY PHONE
        if (sharedPreferences.getString("Email", null)!= null)
            user.setText(sharedPreferences.getString("Email",null));
            pass.setText(sharedPreferences.getString("Password",null));

    }


    public void SignIn(View view) {
        TextView t = findViewById(R.id.Email);
        String email = t.getText().toString();
        TextView s = findViewById(R.id.Password);
        String password = s.getText().toString();

        mAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            Toast.makeText(LoginReg.this, "Login Succeeded.",
                                    Toast.LENGTH_LONG).show();
                            FirebaseUser user = mAuth.getCurrentUser();

                            EditText user2 = findViewById(R.id.Email);
                            EditText pass = findViewById(R.id.Password);

                            SharedPreferences sharedPreferences = getPreferences(MODE_PRIVATE); // PRIVATE FILE IN MY PHONE

                            if (sharedPreferences.getString("Email", null) == null)
                            {
                                SharedPreferences.Editor editor = sharedPreferences.edit();
                                editor.putString("Email",user2.getText().toString());
                                editor.putString("Password",pass.getText().toString()); // saved name and pass in cookie

                                editor.apply();
                                Intent intent = new Intent(LoginReg.this, MainActivity.class);
                                startActivity(intent);
                            }
                            else{
                                user2.setText(sharedPreferences.getString("Email",null));
                                pass.setText(sharedPreferences.getString("Password",null));

                            }
                            Intent intent = new Intent(LoginReg.this, MainActivity.class);
                            startActivity(intent);



                        } else {
                            // If sign in fails, display a message to the user.
                            Toast.makeText(LoginReg.this, "Login failed.",
                                    Toast.LENGTH_SHORT).show();

                        }
                    }
                });
    }




    public void movePage(View view) {
        Intent intent = new Intent(this, SignUp.class);
        startActivity(intent);
    }

   public void SignUp(View view) {

        TextView t = findViewById(R.id.Email);
        String email = t.getText().toString();
        TextView s = findViewById(R.id.Password);
        String password = s.getText().toString();

        mAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            Toast.makeText(LoginReg.this, "Authentication Succeeded.",
                                    Toast.LENGTH_LONG).show();

                            FirebaseUser user = mAuth.getCurrentUser();
                        } else {
                            // If sign in fails, display a message to the user.
                            Toast.makeText(LoginReg.this, "Authentication failed.",
                                    Toast.LENGTH_LONG).show();
                        }
                        // ...
                    }
                });

    }

}

