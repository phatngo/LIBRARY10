package com.example.library10;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class ChangePassword extends AppCompatActivity {
TextView txtEmail_ChangePasswordUI, txtPassword_ChangePasswordUI, txtNewPassword_ChangePasswordUI;
Button btnChangePassword_ChangePassword;
FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
FirebaseAuth auth=FirebaseAuth.getInstance();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_password);
        addControls();
        addEvents();
    }
    private void addEvents() {
        btnChangePassword_ChangePassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
        auth.signInWithEmailAndPassword(txtEmail_ChangePasswordUI.getText().toString(),txtPassword_ChangePasswordUI.getText().toString()).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
    @Override
    public void onComplete(@NonNull Task<AuthResult> task) {
        if(task.isSuccessful()){
       user.updatePassword(txtNewPassword_ChangePasswordUI.getText().toString()).addOnCompleteListener(new OnCompleteListener<Void>() {
           @Override
           public void onComplete(@NonNull Task<Void> task) {
          if(task.isSuccessful()){
              Toast.makeText(ChangePassword.this,"Password Changed!",Toast.LENGTH_SHORT).show();
          }
           }
       });
   }else{
       Toast.makeText(ChangePassword.this,"Wrong Password",Toast.LENGTH_SHORT).show();
   }
    }
});
            }
        });
    }
    private void addControls() {
txtEmail_ChangePasswordUI=findViewById(R.id.txtEmail_ChangePasswordUI);
txtEmail_ChangePasswordUI.setText(user.getEmail());
txtPassword_ChangePasswordUI=findViewById(R.id.txtPassword_ChangePasswordUI);
txtNewPassword_ChangePasswordUI=findViewById(R.id.txtNewPassword_ChangePasswordUI);
btnChangePassword_ChangePassword=findViewById(R.id.btnChangePassword_ChangePassword);
    }
}
