package com.example.messenger;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.google.firebase.auth.FirebaseUser;

public class RegisterActivity extends AppCompatActivity {

    EditText editTextEmail;
    EditText editTextPassword;
    EditText editTextPasswordConfirm;
    EditText editTextNickName;
    EditText editTextName;
    EditText editTextLastName;
    EditText editTextAge;
    Button buttonSubmit;

    private RegisterViewModel viewModel;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        initViews();

        viewModel = new ViewModelProvider(this).get(RegisterViewModel.class);
        observeViewModel();
        buttonSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String email = getValue(editTextEmail);
                String password = getValue(editTextPassword);
                String passwordConfirm = getValue(editTextPasswordConfirm);
                String nickName = getValue(editTextNickName);
                String lastName = getValue(editTextLastName);
                int age = Integer.parseInt(getValue(editTextAge));
                if (password.equals(passwordConfirm)){
                viewModel.signUp(email, password, nickName, lastName, age);}
                else Toast.makeText(getApplicationContext(),"Confirm password error!", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void observeViewModel(){
        viewModel.getError().observe(this, new Observer<String>() {
            @Override
            public void onChanged(String errorMessage) {
                if (errorMessage!=null){
                    Toast.makeText(RegisterActivity.this, errorMessage, Toast.LENGTH_SHORT ).show();
                }
            }
        });
        viewModel.getUser().observe(this, new Observer<FirebaseUser>() {
            @Override
            public void onChanged(FirebaseUser firebaseUser) {
                if (firebaseUser!=null){
                    Intent intent = UsersActivity.newIntent(RegisterActivity.this, firebaseUser.getUid());
                    startActivity(intent);
                    finish();
                }
            }
        });
    }

    void initViews(){
        editTextEmail = findViewById(R.id.editTextEmail);
        editTextPassword = findViewById(R.id.editTextPassword);
        editTextPasswordConfirm = findViewById(R.id.editTextPasswordConfirm);
        editTextNickName = findViewById(R.id.editTextNickName);
        editTextName = findViewById(R.id.editTextName);
        editTextLastName = findViewById(R.id.editTextLastName);
        editTextAge = findViewById(R.id.editTextAge);
        buttonSubmit = findViewById(R.id.buttonSubmit);
    }
    private String getValue(EditText editText){
        return editText.getText().toString().trim();
    }
    public static Intent newIntent(Context context){
        return new Intent(context, RegisterActivity.class);
    }
}