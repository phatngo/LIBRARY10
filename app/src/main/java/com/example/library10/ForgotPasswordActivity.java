package com.example.library10;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;

import static android.content.ContentValues.TAG;

public class ForgotPasswordActivity extends AppCompatActivity {
TextView txtForgotPassword_Email;
Button btnForgotPassword_Confirm;
FirebaseAuth firebaseAuth=FirebaseAuth.getInstance();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgot_password);
        addControls();
        addEvents();
    }

    private void addEvents() {
        btnForgotPassword_Confirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                resetPassword(txtForgotPassword_Email.getText().toString());
            }
        });
    }

    private void addControls() {
        txtForgotPassword_Email=findViewById(R.id.txtForgotPassword_Email);
        btnForgotPassword_Confirm=findViewById(R.id.btnForgotPassword_Confirm);
    }

    public void resetPassword(String emailAddress){
        firebaseAuth.sendPasswordResetEmail(emailAddress)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()) {
                            Log.d(TAG, "Email sent.");
                            Toast.makeText(ForgotPasswordActivity.this,"Email sent!",Toast.LENGTH_SHORT).show();
                        }else{
                            Log.d(TAG, "Email not found!");
                            Toast.makeText(ForgotPasswordActivity.this,"Email not found!",Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }


}
