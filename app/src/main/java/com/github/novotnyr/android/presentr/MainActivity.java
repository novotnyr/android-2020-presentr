package com.github.novotnyr.android.presentr;

import android.os.Bundle;
import android.view.View;
import android.widget.*;

import java.util.ArrayList;

import androidx.appcompat.app.AppCompatActivity;
import butterknife.*;

public class MainActivity extends AppCompatActivity {
    @BindView(R.id.userListView)
    ListView userListView;

    ArrayAdapter<User> userListViewAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        userListViewAdapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, new ArrayList<>());
        userListView.setAdapter(userListViewAdapter);
    }

    public void onFloatingActionButtonClick(View view) {
        SendPresenceAsyncTask task = new SendPresenceAsyncTask();
        task.execute(new User("John"));
    }
}
