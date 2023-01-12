package com.example.messenger;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseUser;

public class LoginActivity extends AppCompatActivity {
    private EditText editTextLoginEmail;
    private EditText editTextPassword;
    private Button buttonLogin;
    private TextView textViewForgetPassword;
    private TextView textViewRegister;

    private LoginViewModel viewModel;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        initViews();
        viewModel = new ViewModelProvider(this).get(LoginViewModel.class);
        observeViewModel();
        setupClickListener();

    }

    private void setupClickListener(){
        buttonLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String emaill = editTextLoginEmail.getText().toString().trim();
                String password = editTextPassword.getText().toString().trim();
                viewModel.login(emaill, password);
            }
        });
        textViewForgetPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = ForgetPasswordActivity.newIntent(LoginActivity.this,
                        editTextLoginEmail.getText().toString().trim());
                startActivity(intent); //forget
            }
        });
        textViewRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = RegisterActivity.newIntent(LoginActivity.this);
                startActivity(intent);
            }
        });
    }

    private void observeViewModel(){
        viewModel.getError().observe(this, new Observer<String>() {
            @Override
            public void onChanged(String errorMessage) {
                if (errorMessage != null) {
                    Toast.makeText(LoginActivity.this, errorMessage, Toast.LENGTH_SHORT).show();
                }
            }
        });
        viewModel.getUser().observe(this, new Observer<FirebaseUser>() {
            @Override
            public void onChanged(FirebaseUser firebaseUser) {
                if (firebaseUser != null){
                   // Toast.makeText(LoginActivity.this, "Authorized", Toast.LENGTH_SHORT).show();
                    Intent intent = UsersActivity.newIntent(LoginActivity.this);
                    startActivity(intent);
                    finish();
                }
            }
        });
    }

    void initViews(){
        editTextLoginEmail=findViewById(R.id.editTextEmailLogin);
        editTextPassword=findViewById(R.id.editTextPassword);
        buttonLogin=findViewById(R.id.buttonLogin);
        textViewForgetPassword =findViewById(R.id.TextViewForgetPassword);
        textViewRegister =findViewById(R.id.TextViewRegister);
    }
    public static Intent newIntent(Context context){
        return new Intent(context, LoginActivity.class);
    }
}