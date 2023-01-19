package com.example.messenger;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class UsersActivity extends AppCompatActivity {

    RecyclerView recyclerViewUsers;
    private UsersViewModel viewModel;
    private UsersAdapter usersAdapter;
    // Write a message to the database
    FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference myRef = database.getReference("message");


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_users);

        myRef.push().setValue("Hello, World!");
        initViews();
        viewModel = new ViewModelProvider(this).get(UsersViewModel.class);
        observeViewModel();
       /* List<User> users = new ArrayList<>();
        for (int i = 0; i < 20; i++) {
            User user = new User("id:"+i, "name"+i, "lastName"+i, i,
                    new Random().nextBoolean());
            users.add(user);
            Log.d("UserActivity", "user: "+user.getId());
        }
        usersAdapter.setUsers(users);*/
        Log.d("UserActivity", "user adapter ");
        recyclerViewUsers.setAdapter(usersAdapter);
    }

    private void initViews(){
        recyclerViewUsers= findViewById(R.id.recyclerViewUsers);
        usersAdapter = new UsersAdapter();

    }

    private void observeViewModel(){
        viewModel.getUser().observe(this, new Observer<FirebaseUser>() {
            @Override
            public void onChanged(FirebaseUser firebaseUser) {
                if (firebaseUser ==null){
                    Intent intent = LoginActivity.newIntent(UsersActivity.this);
                    startActivity(intent);
                    finish();
                }
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == R.id.item_logout){
            viewModel.logout();
        }
        return super.onOptionsItemSelected(item);

    }

    public static Intent newIntent(Context context){
        return new Intent(context, UsersActivity.class);
    }
}