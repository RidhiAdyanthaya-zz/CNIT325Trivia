package com.example.trivia.trivia;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.StrictMode;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Spinner;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class PlayActivity extends AppCompatActivity implements AsyncResponse{

    Spinner category, difficulty, type;
    String url;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_play);

        category = (Spinner) findViewById(R.id.category);
        difficulty = (Spinner) findViewById(R.id.difficulty);
        type = (Spinner) findViewById(R.id.difficulty);
    }

    public void start(View view) throws IOException {
        int c = (int) category.getSelectedItemId() + 8;
        String d = difficulty.getSelectedItem().toString();
        String t = type.getSelectedItem().toString();
        url = "https://www.opentdb.com/api.php?amount=1";
        //&category=25&difficulty=easy&type=multiple
        if (!(c == 8)) {
            url += "&category=" + c;
        }
        if (!d.equals("Any")) {
            url += "&difficulty=" + d.toLowerCase();
        }
        if (!t.equals("Any")) {
            switch (t) {
                case "True/False":
                    url += "&type=boolean";
                    break;
                case "Multiple Choice":
                    url += "&type=multiple";
                    break;
                default:
                    break;
            }
        }
        LoadURL aTask = new LoadURL();
        aTask.delegate = this;
        aTask.execute(url);
    }

    @Override
    public void processFinish(String output){
        Question question = new Question(output);
        Intent intent = new Intent(this, QuestionActivity.class);
        intent.putExtra("question", question);
        intent.putExtra("url", url);
        startActivity(intent);
    }

    /*
    public static String getHTML(String urlToRead) throws IOException {
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

    private class LoadURL extends AsyncTask<String, Void, String> {

        @Override
        protected String doInBackground(String... params) {
            String url = params[0];

            try {
                return getHTML(url);
            }
            catch (IOException e) {
                e.printStackTrace();
            }
            return "failure";
        }

        @Override
        protected void onPostExecute(String result) {
            System.out.println(result);
        }

        @Override
        protected void onPreExecute() {}

        @Override
        protected void onProgressUpdate(Void... values) {}
    }
    */
}
