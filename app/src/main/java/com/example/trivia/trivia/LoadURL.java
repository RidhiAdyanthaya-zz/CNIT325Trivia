package com.example.trivia.trivia;

import android.os.AsyncTask;
import android.view.View;
import android.widget.ProgressBar;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by cheng on 11/1/2016.
 */

public class LoadURL extends AsyncTask<String, Void, String> {
    public AsyncResponse delegate = null;

    @Override
    protected String doInBackground(String... params) {
        String url = params[0];

        try {
            return load(url);
        }
        catch (IOException e) {
            e.printStackTrace();
        }
        return "failure";
    }

    @Override
    protected void onPostExecute(String result) {
        delegate.processFinish(result);
    }

    @Override
    protected void onPreExecute() {}

    @Override
    protected void onProgressUpdate(Void... values) {}

    private String load(String urlToRead) throws IOException {
        StringBuilder result = new StringBuilder();
        URL url = new URL(urlToRead);
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setRequestMethod("GET");
        BufferedReader rd = new BufferedReader(new InputStreamReader(conn.getInputStream()));
        String line;
        while ((line = rd.readLine()) != null) {
            result.append(line);
        }
        rd.close();
        return result.toString();
    }
}