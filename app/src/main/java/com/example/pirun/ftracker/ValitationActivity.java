package com.example.pirun.ftracker;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.ImageButton;

public class ValitationActivity extends Activity {
    public ImageButton btn1;
    public void valitationEntryClick() {
        btn1 = (ImageButton) findViewById(R.id.CodeEntryBtn);
        btn1.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                startActivity(new Intent(ValitationActivity.this, UserActivity.class));
            }
        });
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_valitation);
        valitationEntryClick();
    }
}
