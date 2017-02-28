package me.hasan.rpswar;

import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;

public class HighScore extends AppCompatActivity {

    private TextView scorePlayHSTV;
    private TextView stagePlayHSTV;
    private int scorePlayHS;
    private int stagePlayHS;

    SharedPreferences preferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_high_score);

        AdView adViewTop = (AdView) findViewById(R.id.adViewScoreTop);
        AdView adViewBottom = (AdView) findViewById(R.id.adViewScoreBottom);
        AdRequest adRequest = new AdRequest.Builder().build();

        adViewTop.loadAd(adRequest);
        adViewBottom.loadAd(adRequest);

        scorePlayHSTV = (TextView) findViewById(R.id.scorePlayHSTV);
        stagePlayHSTV = (TextView) findViewById(R.id.stagePlayHSTV);

        preferences = getSharedPreferences(GameResult.SHARED_PREFERENCES_NAME,MODE_PRIVATE);

        scorePlayHS = preferences.getInt(GameResult.SCORE_PLAY_NAME,0);
        stagePlayHS = preferences.getInt(GameResult.STAGE_PLAY_NAME,1);

        scorePlayHSTV.setText("Score Play : "+scorePlayHS+" Points");
        stagePlayHSTV.setText("Stage Play : Stage "+stagePlayHS);

    }
}
