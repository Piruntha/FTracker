package com.example.pirun.ftracker;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;

public class Terms_ConditionActivity extends Activity {
    public Button btn;

    public void AgreeBtnClick() {
        btn = (Button) findViewById(R.id.AgreeBtn);
        btn.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                startActivity(new Intent(Terms_ConditionActivity.this, SignUpActivity.class));
            }
        });
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //set up notitle
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_terms__condition);

        AgreeBtnClick();

    }

}