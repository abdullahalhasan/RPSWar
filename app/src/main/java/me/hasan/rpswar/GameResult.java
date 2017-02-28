package me.hasan.rpswar;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;

public class GameResult extends AppCompatActivity {

    public static final String SHARED_PREFERENCES_NAME = "Saved_High_Score";
    public static final String SCORE_PLAY_NAME = "Score_Play_High_Score";
    public static final String STAGE_PLAY_NAME = "Stage_Play_High_Score";

    private int score;
    private int scorePlayHS;
    private int stagePlayHS;
    private int level;
    private int name;

    private TextView newHighscoreTV;
    private TextView finalScoreTV;
    private SharedPreferences preferences;
    private SharedPreferences.Editor editor;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result);

        AdView adViewTop = (AdView) findViewById(R.id.adViewResultTop);
        AdView adViewBottom = (AdView) findViewById(R.id.adViewResultBottom);
        AdRequest adRequest = new AdRequest.Builder().build();

        adViewTop.loadAd(adRequest);
        adViewBottom.loadAd(adRequest);

        level = getIntent().getIntExtra("Level", level);
        score = getIntent().getIntExtra("Result",score);
        name = getIntent().getIntExtra("Name",name);

        finalScoreTV = (TextView) findViewById(R.id.finalScoreTV);
        newHighscoreTV = (TextView) findViewById(R.id.newHighScoreTV);

        preferences = getSharedPreferences(SHARED_PREFERENCES_NAME,MODE_PRIVATE);
        editor = preferences.edit();

        scorePlayHS = preferences.getInt(GameResult.SCORE_PLAY_NAME,0);
        stagePlayHS = preferences.getInt(GameResult.STAGE_PLAY_NAME,1);

        showFinalResult();
        saveHighScore();
    }

    private void showFinalResult() {
        if (name == 1) {
            finalScoreTV.setText("Your Final Score : " + score);
        } else if (name == 0) {
            finalScoreTV.setText("Your Final Level : "+ level);
        }
    }

    public void saveHighScore() {
        if (name == 1) {
            if (score > scorePlayHS) {
                editor.putInt(SCORE_PLAY_NAME, score);
                editor.commit();
                newHighscoreTV.setText("New High Score !!");
            }
        } else if (name == 0) {
            if (level > stagePlayHS) {
                editor.putInt(STAGE_PLAY_NAME, level);
                editor.commit();
                newHighscoreTV.setText("New Highest Level !!");
            }
        }
    }

    public void quitGame(View view) {
        finish();
    }

    public void playAgain(View view) {
        if (name == 1) {
            startActivity(new Intent(this, ScorePlay.class));
            finish();
        } else if (name == 0) {
            startActivity(new Intent(this, StagePlay.class));
            finish();
        }
    }
}
