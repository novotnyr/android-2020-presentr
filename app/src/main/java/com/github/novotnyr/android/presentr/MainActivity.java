package com.github.novotnyr.android.presentr;

import android.os.Bundle;
import android.view.*;
import android.widget.*;

import java.util.ArrayList;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import butterknife.*;

public class MainActivity extends AppCompatActivity {
    @BindView(R.id.userListView)
    ListView userListView;

    ArrayAdapter<User> userListViewAdapter;

    private UserListViewModel userListViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        userListViewAdapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, new ArrayList<>());
        userListView.setAdapter(userListViewAdapter);

        userListViewModel = new ViewModelProvider(this).get(UserListViewModel.class);
        userListViewModel.getUsers().observe(this, users -> {
            userListViewAdapter.clear();
            userListViewAdapter.addAll(users);
        });
    }

    public void onFloatingActionButtonClick(View view) {
        SendPresenceAsyncTask task = new SendPresenceAsyncTask();
        task.execute(new User("John"));
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.activity_main_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

}
