package com.example.pirun.ftracker;

import android.app.Activity;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.content.Intent;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.ProgressBar;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseException;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthProvider;

import java.util.concurrent.TimeUnit;

public class SignUpActivity extends Activity {


    private LinearLayout mPhoneLayout;
    private LinearLayout mCodeLayout;
    private EditText mPhoneText;
    private EditText mCodeText;
    private ProgressBar mPhoneBar;
    private ProgressBar mCodeBar;
    private ImageButton btn;
    private PhoneAuthProvider.OnVerificationStateChangedCallbacks mCallbacks;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_sign_up);

        btn=(ImageButton) findViewById(R.id.NumberEntryBtn);
        btn.setOnClickListener(new View.OnClickListener(){
            public void onClick(View view){
                mPhoneText.setVisibility(View.VISIBLE);
                mPhoneText.setEnabled(false);
                btn.setEnabled(false);
                String phoneNumber=mPhoneText.getText().toString();
                PhoneAuthProvider.getInstance().verifyPhoneNumber(
                        phoneNumber,        // Phone number to verify
                        60,                 // Timeout duration
                        TimeUnit.SECONDS,   // Unit of timeout
                        SignUpActivity.this,               // Activity (for callback binding)
                        mCallbacks);
            }
        });
        mCallbacks = new PhoneAuthProvider.OnVerificationStateChangedCallbacks(){
            public void onVerificationCompleted(PhoneAuthCredential phoneAuthCredential){

            }
            public void onVerificationFailed(FirebaseException e){

            }
        };

    }







}
