package com.example.pirun.ftracker;

import android.app.Activity;
import android.app.MediaRouteButton;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseException;
import com.google.firebase.FirebaseTooManyRequestsException;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthProvider;
import com.google.firebase.crash.FirebaseCrash;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.concurrent.TimeUnit;


public class SignUpActivity extends Activity implements View.OnClickListener {
        String phoneNumber;
        EditText mPhoneNumber, mVerification;
        Button   mResendBtn;
        ImageButton mVarifyBtn, mSendBtn;
        private ProgressDialog dialog;
        private FirebaseAuth mAuth;
        private PhoneAuthProvider.ForceResendingToken mResendToken;
        private PhoneAuthProvider.OnVerificationStateChangedCallbacks mCallbacks;
        String mVerificationId;
        private static final String TAG = "MainAct";

        @Override
        protected void onCreate (@Nullable Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_sign_up);
            final TextView tv = SignUpActivity.this.findViewById(R.id.phoneText);
            tv.setVisibility(View.VISIBLE);
            final ImageButton IBtn = SignUpActivity.this.findViewById(R.id.NumberEntryBtn);
            IBtn.setVisibility(View.VISIBLE);
            FirebaseCrash.log("error");
            FirebaseCrash.report(new Exception("Android non-fatal error"));
            mPhoneNumber = (EditText) findViewById(R.id.phoneText);
            mVerification = (EditText) findViewById(R.id.varifyText);
            dialog= new ProgressDialog(SignUpActivity.this);
            mSendBtn= (ImageButton) findViewById(R.id.NumberEntryBtn);
            mVarifyBtn= (ImageButton) findViewById(R.id.verifyBtn);
            mResendBtn = (Button) findViewById(R.id.resendBtn);

            mSendBtn.setOnClickListener(this);
            mResendBtn.setOnClickListener(this);
            mVarifyBtn.setOnClickListener(this);

            mAuth = FirebaseAuth.getInstance();




            mCallbacks = new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {
                @Override
                public void onVerificationCompleted(PhoneAuthCredential credential) {
                    Log.d(TAG, "onVerificationCompleted:" + credential);

                }

                @Override
                public void onVerificationFailed(FirebaseException e) {
                    Log.w(TAG, "onVerificationFailed", e);
                    if (e instanceof FirebaseAuthInvalidCredentialsException) {
                        mPhoneNumber.setError("Invalid phone number.");
                    } else if (e instanceof FirebaseTooManyRequestsException) {
                        Snackbar.make(findViewById(android.R.id.content), "Quota exceeded.",
                                Snackbar.LENGTH_SHORT).show();

                    }
                    if (dialog.isShowing()) {
                        dialog.dismiss();
                    }
                }

                @Override
                public void onCodeSent(String verificationId,
                                       PhoneAuthProvider.ForceResendingToken token) {
                    Log.d(TAG, "onCodeSent:" + verificationId);
                    mVerificationId = verificationId;
                    mResendToken = token;
                    tv.setVisibility(View.INVISIBLE);
                    IBtn.setVisibility(View.INVISIBLE);
                    final TextView var = SignUpActivity.this.findViewById(R.id.varifyText);
                    var.setVisibility(View.VISIBLE);
                    final ImageButton IBtn1 = SignUpActivity.this.findViewById(R.id.verifyBtn);
                    IBtn1.setVisibility(View.VISIBLE);
                    final Button IBtn2 = SignUpActivity.this.findViewById(R.id.resendBtn);
                    IBtn2.setVisibility(View.VISIBLE);
                    if (dialog.isShowing()) {
                        dialog.dismiss();
                    }
                }
            };
        }
        public int getPhone(){
            int pn = Integer.parseInt(mPhoneNumber.getText().toString());
            return pn;
        }

    private void signInWithPhoneAuthCredential(PhoneAuthCredential credential) {
        mAuth.signInWithCredential(credential)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            Log.d(TAG, "signInWithCredential:success");
                            DatabaseReference reference = FirebaseDatabase.getInstance().getReference("Users");


                            reference.addListenerForSingleValueEvent(new ValueEventListener() {
                                @Override
                                public void onDataChange(DataSnapshot dataSnapshot) {
                                    if (dataSnapshot.child(phoneNumber).exists()) {

                                        Intent i =new Intent(SignUpActivity.this, UserActivity.class);
                                        startActivity(i);
                                        finish();
                                    }else{
                                        Intent I =new Intent(SignUpActivity.this,UserActivity.class);
                                        Bundle b =new Bundle();
                                        b.putString("phone", phoneNumber);

                                        I.putExtras(b);
                                        startActivity(I);
                                        finish();
                                    }
                                }

                                @Override
                                public void onCancelled(DatabaseError databaseError) {

                                }
                            });


                        } else {
                            Log.w(TAG, "signInWithCredential:failure", task.getException());
                            if (task.getException() instanceof FirebaseAuthInvalidCredentialsException) {
                                if (dialog.isShowing()) {
                                    dialog.dismiss();
                                }
                                mVerification.setError("Invalid code.");
                            }
                        }
                    }
                });
    }


    private void startPhoneNumberVerification(String phoneNumber) {
        PhoneAuthProvider.getInstance().verifyPhoneNumber(
                phoneNumber,        // Phone number to verify
                60,                 // Timeout duration
                TimeUnit.SECONDS,   // Unit of timeout
                this,               // Activity (for callback binding)
                mCallbacks);        // OnVerificationStateChangedCallbacks
    }

    private void verifyPhoneNumberWithCode(String verificationId, String code) {
        PhoneAuthCredential credential = PhoneAuthProvider.getCredential(verificationId, code);
        signInWithPhoneAuthCredential(credential);




    }

    private void resendVerificationCode(String phoneNumber,
                                        PhoneAuthProvider.ForceResendingToken token) {
        PhoneAuthProvider.getInstance().verifyPhoneNumber(
                phoneNumber,        // Phone number to verify
                60,                 // Timeout duration
                TimeUnit.SECONDS,   // Unit of timeout
                this,               // Activity (for callback binding)
                mCallbacks,         // OnVerificationStateChangedCallbacks
                token);             // ForceResendingToken from callbacks
    }

    private boolean validatePhoneNumber() {
        phoneNumber = mPhoneNumber.getText().toString();
        if (TextUtils.isEmpty(phoneNumber)) {
            mPhoneNumber.setError("Invalid phone number.");
            return false;
        }
        return true;
    }



    @Override
    public void onClick(View view) {

        switch (view.getId()) {
            case R.id.NumberEntryBtn:
                dialog.setMessage("Start Verfication.....");
                dialog.show();
                if (!validatePhoneNumber()) {
                    return;
                }
                startPhoneNumberVerification(mPhoneNumber.getText().toString());



                break;
            case R.id.verifyBtn:
                dialog.setMessage("Verify this Number.....");
                dialog.show();
                String code = mVerification.getText().toString();
                if (TextUtils.isEmpty(code)) {
                    mVerification.setError("Cannot be empty.");
                    return;
                }

                verifyPhoneNumberWithCode(mVerificationId, code);



                break;
            case R.id.resendBtn:
                dialog.setMessage("Verify this Number.....");
                dialog.show();
                resendVerificationCode(mPhoneNumber.getText().toString(), mResendToken);

                break;
        }

    }


}


