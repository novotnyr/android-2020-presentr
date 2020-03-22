package com.github.novotnyr.android.presentr;

import android.os.*;
import android.util.Log;
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

    private Handler periodicHandler = new Handler();

    private Runnable periodicTask = new RefreshUsersTask();

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

    @Override
    protected void onResume() {
        super.onResume();
        periodicHandler.postDelayed(periodicTask, 60 * 1000);
    }

    @Override
    protected void onPause() {
        super.onPause();
        periodicHandler.removeCallbacks(periodicTask);
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

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.menu_item_refresh) {
            userListViewModel.refresh();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    class RefreshUsersTask implements Runnable {
        @Override
        public void run() {
            Log.d("Presentr#RefreshUsers", "Refreshing user list");
            userListViewModel.refresh();
            periodicHandler.postDelayed(periodicTask, 60 * 1000);
        }
    }
}
