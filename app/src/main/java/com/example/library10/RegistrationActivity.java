package com.example.library10;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.model.User;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import static android.content.ContentValues.TAG;

public class RegistrationActivity extends AppCompatActivity {

    EditText txtAccRegistration_Email, txtAccRegistration_Password, txtAccRegistration_Phone, txtAccRegistration_Name;
    Button btnAccRegistration_Register;

    FirebaseAuth firebaseAuth=FirebaseAuth.getInstance();
    private DatabaseReference mDatabase= FirebaseDatabase.getInstance().getReference();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);
        addControls();
        addEvents();
    }
    private void addControls() {
        txtAccRegistration_Email=findViewById(R.id.txtAccRegistration_Email);
        txtAccRegistration_Password=findViewById(R.id.txtAccRegistration_Password);
        txtAccRegistration_Phone=findViewById(R.id.txtAccRegistration_Phone);
        txtAccRegistration_Name=findViewById(R.id.txtAccRegistration_Name);
        btnAccRegistration_Register=findViewById(R.id.btnAccRegistration_Register);
    }
    private void addEvents() {
        btnAccRegistration_Register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                User user= new User(txtAccRegistration_Email.getText().toString(),
                        txtAccRegistration_Password.getText().toString(),
                        txtAccRegistration_Name.getText().toString(),
                        txtAccRegistration_Phone.getText().toString());
                firebaseAuth.createUserWithEmailAndPassword(user.getEmail(),user.getPassword()).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful()){
                            sendVerificationEmail();
                            Toast.makeText(RegistrationActivity.this,"Registration succesful!",Toast.LENGTH_SHORT).show();
                        }else{
                            Toast.makeText(RegistrationActivity.this,"Registration failed!",Toast.LENGTH_SHORT).show();
                        }
                    }
                });
                //Add a new user's information into the database
                addUser(user);
                txtAccRegistration_Email.setText("");
                txtAccRegistration_Name.setText("");
                txtAccRegistration_Password.setText("");
                txtAccRegistration_Phone.setText("");
                Intent intent = new Intent(RegistrationActivity.this,MainActivity.class);
                startActivity(intent);
            }
        });
    }

    //EVENT'S FUNCTIONS:
    private void sendVerificationEmail() {
        FirebaseUser user = firebaseAuth.getCurrentUser();
        user.sendEmailVerification().addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if(task.isSuccessful()){
                    Log.d(TAG, "Email sent.");}
                else{
                    Log.d(TAG, "failed");
                }
            }
        });
    }
    public void addUser(User user){
        User databaseUser=new User(user.getEmail(),user.getPhone(),user.getName());
        mDatabase.child("root").child("User").push().setValue(databaseUser);
    }
}
