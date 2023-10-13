package com.example.myapplicationawdawdawd;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import org.w3c.dom.Text;

public class register extends AppCompatActivity {
    TextView alreadyHaveAccount;
    EditText inputUsername, inputEmail, inputPassword, InputrePassword;
    Button btnRegister;
    String emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";
    ProgressDialog progressDialog;

    FirebaseAuth mAuth;
    FirebaseUser mUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);

        alreadyHaveAccount=findViewById(R.id.alreadyHaveAccount);

        inputUsername=findViewById(R.id.inputUsername);
        inputEmail=findViewById(R.id.inputEmail);
        inputPassword=findViewById(R.id.inputPassword);
        InputrePassword=findViewById(R.id.Inputrepassword);
        btnRegister=findViewById(R.id.btnregister);
        progressDialog=new ProgressDialog(this);
        mAuth=FirebaseAuth.getInstance();
        mUser=mAuth.getCurrentUser();

        alreadyHaveAccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(register.this, login.class));
            }
        });

        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                checkCredentials();
                PerformAuth();

            }
        });


    }

    private void PerformAuth() {
        String username=inputUsername.getText().toString();
        String email=inputEmail.getText().toString();
        String password=inputPassword.getText().toString();
        String repassword=InputrePassword.getText().toString();

        if (username.isEmpty() || (username.length() < 7))
        {
            showError(inputUsername,"Your username is not valid!");
        } else if (!email.matches(emailPattern)) {
            inputEmail.setError("Enter Correct Email");
        } else if (password.isEmpty() || password.length() < 8) {
            inputPassword.setError("Password must be at least 8 characters.");
        }  else if (!password.equals(repassword))
        {
            InputrePassword.setError("Password not match!");
        }  else
        {
            progressDialog.setMessage("Registering...");
            progressDialog.setTitle("Registration");
            progressDialog.setCanceledOnTouchOutside(false);
            progressDialog.show();

        }



    }

    private void checkCredentials() {
        String username=inputUsername.getText().toString();
        String email=inputEmail.getText().toString();
        String password=inputPassword.getText().toString();
        String repassword=InputrePassword.getText().toString();

        if (username.isEmpty() || (username.length() < 7))
        {
            showError(inputUsername,"Your username is not valid!");
        }
        else if (!email.matches(emailPattern))
        {
            inputEmail.setError("Email id not valid.");
        }
        else if (password.isEmpty() || password.length() < 8)
        {
            inputPassword.setError("Password must be at least 8 characters.");
        }
        else if (password.equals(repassword))
        {
            InputrePassword.setError("Password not match!");
        }
        else
        {
            progressDialog.setMessage("Registering...");
            progressDialog.setTitle("Registration");
            progressDialog.setCanceledOnTouchOutside(false);
            progressDialog.show();

            mAuth.createUserWithEmailAndPassword(email,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    if (task.isSuccessful())
                    {
                        progressDialog.dismiss();
                        sendUserToNextActivity();
                        Toast.makeText(register.this, "Registration Successful", Toast.LENGTH_SHORT).show();
                    }
                    else
                    {
                        progressDialog.dismiss();
                        Toast.makeText(register.this, ""+task.getException(), Toast.LENGTH_SHORT).show();
                    }
                }
            });
        }


    }

    private void sendUserToNextActivity() {
        Intent intent=new Intent(register.this, login.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK|Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
    }

    private void showError(EditText input, String s) {
        input.setError(s);
        input.requestFocus();
    }
}

