package com.example.library10;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = "" ;
    Button btnSignIn, btnRegisterAccount, btnForgotPassword;
EditText txtEmail, txtPassword;
ArrayAdapter<String> adapterLanguage;
ArrayList<String> arrLanguage;


FirebaseAuth firebaseAuth=FirebaseAuth.getInstance();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        addControls();
        addEvents();

    }

    private void addEvents() {
btnSignIn.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View v) {

       if(txtPassword.getText().toString().length()==0||txtEmail.getText().toString().length()==0){
            Toast.makeText(MainActivity.this,"No Input Found!",Toast.LENGTH_SHORT).show();
        }else{
        firebaseAuth.signInWithEmailAndPassword(txtEmail.getText().toString(),txtPassword.getText().toString()).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful()){
                    Toast.makeText(MainActivity.this,"Successful",Toast.LENGTH_SHORT).show();
                    Intent intent=new Intent(MainActivity.this,LibraryTabActivity.class);
                    startActivity(intent);
                }else{
                    Toast.makeText(MainActivity.this,"Failed",Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
    }
});

btnRegisterAccount.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View v) {
        Intent intent=new Intent(MainActivity.this,RegistrationActivity.class);
        startActivity(intent);

    }
});
btnForgotPassword.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View v) {
Intent intent=new Intent(MainActivity.this,ForgotPasswordActivity.class);
startActivity(intent);
    }
});

    }

    private void addControls() {
        btnSignIn=findViewById(R.id.btnLogin);
        btnRegisterAccount=findViewById(R.id.btnRegisterAccount);
        btnForgotPassword=findViewById(R.id.btnForgotPassword);
        txtEmail=findViewById(R.id.txtEmail);
        txtPassword=findViewById(R.id.txtPassword);
    }
}
