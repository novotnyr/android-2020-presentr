package com.github.novotnyr.android.presentr;

import android.os.AsyncTask;
import android.util.Log;

import java.io.IOException;

import retrofit2.Call;

public class SendPresenceAsyncTask extends AsyncTask<User, Void, Boolean> {

    @Override
    protected Boolean doInBackground(User... users) {
        try {
            Call<Void> call = PresentrApi.API.login(users[0].getLogin());
            call.execute();
            return true;
        } catch (IOException e) {
            Log.e(getClass().getName(), "Unable to send presence", e);
            return false;
        }
    }
}