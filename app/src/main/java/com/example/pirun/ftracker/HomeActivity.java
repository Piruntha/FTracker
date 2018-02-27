package com.example.pirun.ftracker;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class HomeActivity extends AppCompatActivity {
    private Button friendBtn;
    private Button locationBtn;
    private Button chatBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        friendBtn=(Button) findViewById(R.id.FriendBtn);
        locationBtn=(Button) findViewById(R.id.LocationBtn);
        chatBtn=(Button) findViewById(R.id.ChatBtn);

        locationBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent i = new Intent(getApplicationContext(),LocationActivity.class);
                startActivity(i);
            }
        });
    }
}
