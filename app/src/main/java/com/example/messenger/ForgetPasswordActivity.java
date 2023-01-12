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
import android.widget.Toast;

public class ForgetPasswordActivity extends AppCompatActivity {
    private EditText editTextEmail;
    private Button buttonSubmit;
    private ForgetPasswordViewModel viewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forget_password);
        initViews();
        viewModel = new ViewModelProvider(this).get(ForgetPasswordViewModel.class);
        observeViewModel();
        String email = getIntent().getStringExtra("email");
        editTextEmail.setText(email);
        buttonSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String email = editTextEmail.getText().toString().trim();
                viewModel.resetPassword(email);
            }
        });
    }

    private void observeViewModel (){
        viewModel.getError().observe(this, new Observer<String>() {
            @Override
            public void onChanged(String errorMessage) {
                if (errorMessage!=null){
                    Toast.makeText(ForgetPasswordActivity.this, errorMessage, Toast.LENGTH_SHORT).show();
                }
            }
        });
        viewModel.isSuccess().observe(this, new Observer<Boolean>() {
            @Override
            public void onChanged(Boolean success) {
                if (success) {
                    Toast.makeText(ForgetPasswordActivity.this, "Link has been sent", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    void initViews() {
        editTextEmail = findViewById(R.id.editTextEmail);
        buttonSubmit = findViewById(R.id.buttonSubmit);
    }

    public static Intent newIntent(Context context, String email) {
        Intent intent = new Intent(context, ForgetPasswordActivity.class);
        intent.putExtra("email", email);
        return intent;
    }
}