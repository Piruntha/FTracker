package com.example.pirun.ftracker;

import android.app.Activity;
import android.content.Intent;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class UserActivity extends Activity {

    private FirebaseDatabase database;
    DatabaseReference ref;
    EditText name;
    UserDetails user;
    SignUpActivity su;
    ImageButton Btn;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_user);

        name=(EditText) findViewById(R.id.userName);
        Btn=(ImageButton) findViewById(R.id.userBtn);

        //startActivity(new Intent(UserActivity.this, HomeActivity.class));

        database=FirebaseDatabase.getInstance();
        ref=database.getReference("User");
        user=new UserDetails();
        Btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                UserClick();
            }
        });
        Btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent i = new Intent(getApplicationContext(),HomeActivity.class);
                startActivity(i);
            }
        });

    }

    public void UserClick(){
        user.setName(name.getText().toString());
        int phone= su.getPhone();
        //user.setNumber(phone);
        System.out.print(phone);
        ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                ref.child("user1").setValue(user);
                System.out.print("success");
                Toast.makeText(UserActivity.this, "", Toast.LENGTH_SHORT).show();

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }






}
