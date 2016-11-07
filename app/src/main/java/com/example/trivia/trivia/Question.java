package com.example.trivia.trivia;

/**
 * Created by cheng on 11/1/2016.
 */
import org.json.*;
import java.io.Serializable;
import java.util.Random;
import org.apache.commons.lang3.StringEscapeUtils;

public class Question implements Serializable {

    private String question;
    private String type;
    private String correctAnswer;
    private String[] wrongAnswers;

    public Question(String json) {
        try {
            JSONObject jObj = new JSONObject(json);
            JSONArray arr = jObj.getJSONArray("results");
            JSONObject obj = arr.getJSONObject(0);
            type = obj.getString("type");
            question = StringEscapeUtils.unescapeHtml4(obj.getString("question"));
            switch (type) {
                case "boolean":
                    wrongAnswers = new String[1];
                    break;
                case "multiple":
                    wrongAnswers = new String[3];
                    break;
                default:
                    break;
            }
            correctAnswer = StringEscapeUtils.unescapeHtml4(obj.getString("correct_answer"));
            JSONArray wrong = obj.getJSONArray("incorrect_answers");
            for (int i = 0; i < wrong.length(); i++) {
                wrongAnswers[i] = StringEscapeUtils.unescapeHtml4(wrong.getString(i));
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public String getQuestion() {
        return question;
    }

    public String getCorrectAnswer() {
        return correctAnswer;
    }

    public String[] getAnswers() {
        String[] answers = new String[wrongAnswers.length + 1];
        int i = 0;
        for (String wrong: wrongAnswers) {
            answers[i] = wrong;
            i++;
        }
        answers[answers.length - 1] = correctAnswer;
        String[] shuffledAnswers = shuffleArray(answers);
        return shuffledAnswers;
    }

    public String getType() {
        return type;
    }

    private String[] shuffleArray(String[] ar)
    {
        // If running on Java 6 or older, use `new Random()` on RHS here
        Random rnd = new Random();
        for (int i = ar.length - 1; i > 0; i--)
        {
            int index = rnd.nextInt(i + 1);
            // Simple swap
            String a = ar[index];
            ar[index] = ar[i];
            ar[i] = a;
        }
        return ar;
    }
}
