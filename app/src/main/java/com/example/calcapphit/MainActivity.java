package com.example.calcapphit;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    String lastSign;
    TextView tv;
    String num1="",num2="";
    float sol;

   FirebaseDatabase database = FirebaseDatabase.getInstance();
   DatabaseReference myRef = database.getReference("message");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        tv = findViewById(R.id.tv);

        if(savedInstanceState != null){
            sol = savedInstanceState.getInt("sol");
            tv.setText(String.valueOf(sol));
        }
    }

    public void onClick(View v) {

        if(v instanceof Button){  //Poly
            Button btn = (Button)v;
            String txt = ((Button) v).getText().toString();
            if(txt.equals("+")|| txt.equals("-")||txt.equals("*")||txt.equals("/")){
                num1=num2;
                num2="";
                lastSign=txt;
        }
            else if(txt.equals("AC"))
            {
                num1="";
                num2="";
                tv.setText("0");
            }
            else if(txt.equals("SIN")||txt.equals("COS")||txt.equals("TAN")){
                float sol = Float.valueOf(num2);
                float t=0;
                if(txt.equals("TAN"))
                {
                    t=(float)Math.tan(Math.toRadians(sol));
                }
                if(txt.equals("COS"))
                {
                    t=(float)Math.cos(Math.toRadians(sol));
                }
                if(txt.equals("SIN"))
                {
                    t=(float)Math.sin(Math.toRadians(sol));
                }
                tv.setText(String.valueOf(t));
            }
            else if (txt.equals(".")){
                if(num2.indexOf(".")<0){
                    num2+=txt;
                }
                tv.setText(String.valueOf(num2));
            }
            else if (txt.equals("="))
            {
                float sol=0;
                switch (lastSign) {
                    case "+":
                        sol = Float.valueOf(num1) + Float.valueOf(num2);
                        break;
                    case "-":
                        sol = Float.valueOf(num1) - Float.valueOf(num2);
                        break;
                    case "*":
                        sol = Float.valueOf(num1) * Float.valueOf(num2);
                        break;
                    case "/":
                        sol = Float.valueOf(num1) / Float.valueOf(num2);
                        break;
                }
                tv.setText(String.valueOf(sol));
            }
           else if(Integer.parseInt(txt)>=0 && Integer.parseInt(txt)<=9){
                num2=num2+txt;
                tv.setText(num2);
            }
        }
    }


        // Read from the database
    public void readFromData(View view){
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        String Uid = user.getUid();
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference("User").child(Uid);

        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                User value = dataSnapshot.getValue(User.class);
                String email= value.getEmail();
                Toast.makeText(MainActivity.this,email, Toast.LENGTH_LONG).show();
            }
            @Override
            public void onCancelled(DatabaseError error) {
                // Failed to read value
          }

        });
    }
    @Override
    protected void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putFloat("sol",sol);

    }

   @Override
    protected void onRestoreInstanceState(@NonNull Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        savedInstanceState.getInt("sol");
    }

    public void LogOut(View view1){
        FirebaseAuth.getInstance().signOut();
        Intent intent = new Intent(this, LoginReg.class);
        startActivity(intent);
    }
}

