package me.hasan.rpswar;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.InterstitialAd;

public class ScorePlay extends AppCompatActivity {

    private GamePlay rules;

    private int life;
    private int score;
    private String result;
    private TextView lifeTV;
    private TextView scoreTV;
    private TextView resultTV;
    private ImageView computerChoiceIV;
    private ImageView usersChoiceIV;

    public static final String ROCK = "rock";
    public static final String PAPER = "paper";
    public static final String SCISSOR = "scissor";

    private String usersChoice;
    private String computersChoice;

    InterstitialAd interstitialAd;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_score_play);
        life = 3;
        score = 0;
        result = "Tap to Play!!";

        interstitialAd = newInterstitialAd();
        loadInterstitial();
        init();
        showLife(life);
        showScore(score);
        showResult(result);



    }

    public void rockSP(View view) {
        usersChoice = ROCK;
        computersChoice = rules.computersChoice();
        setImages(usersChoice, computersChoice);
        finalResult(usersChoice,computersChoice);
        checkEndGame();

    }

    public void paperSP(View view) {
        usersChoice = PAPER;
        computersChoice = rules.computersChoice();
        setImages(usersChoice, computersChoice);
        finalResult(usersChoice,computersChoice);
        checkEndGame();
    }

    public void scissorSP(View view) {
        usersChoice = SCISSOR;
        computersChoice = rules.computersChoice();
        setImages(usersChoice, computersChoice);
        finalResult(usersChoice,computersChoice);
        checkEndGame();
    }

    public void quitSP(View view) {
        finish();
    }



    private void showScore(int score) {
        scoreTV.setText("Score : "+score);
    }

    private void showLife(int life) {
        lifeTV.setText("Life : "+life);
    }

    private void showResult(String result) {
        resultTV.setText(result);
    }

    private void finalResult(String usersChoice,String computersChoice) {
        int results;
        results = rules.usersChoice(usersChoice, computersChoice);

        if (results == 1) {
            score++;
            showScore(score);
            showResult("You Win the Battle!!");
        } else if (results == 2){
            if (life>0) {
                life--;
                showLife(life);
                showResult("Computer Win the Battle!!");
            }

        } else  {
            showResult("Battle Draw !!");
        }
    }

    private void setImages(String usersChoice, String computersChoice) {
        if (usersChoice == ROCK) {
            usersChoiceIV.setImageDrawable(getDrawable(R.drawable.rock));
        } else if (usersChoice == PAPER) {
            usersChoiceIV.setImageDrawable(getDrawable(R.drawable.paper));
        } else {
            usersChoiceIV.setImageDrawable(getDrawable(R.drawable.scissors));
        }

        if (computersChoice == ROCK) {
            computerChoiceIV.setImageDrawable(getDrawable(R.drawable.rock));
        } else if(computersChoice == PAPER) {
            computerChoiceIV.setImageDrawable(getDrawable(R.drawable.paper));
        } else {
            computerChoiceIV.setImageDrawable(getDrawable(R.drawable.scissors));
        }

    }

    private void init() {

        rules = new GamePlay();

        lifeTV = (TextView) findViewById(R.id.computerScoreTVSP);
        scoreTV = (TextView) findViewById(R.id.yourScoreTVSP);
        resultTV = (TextView) findViewById(R.id.finalResultSP);

        computerChoiceIV = (ImageView) findViewById(R.id.computersChoiceIV);
        usersChoiceIV = (ImageView) findViewById(R.id.usersChoiceIV);
    }
    private void checkEndGame() {
        if (life == 0) {
            showResult("You've Lost the Battle!");
            showInterstitial();
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }

    private InterstitialAd newInterstitialAd() {
        InterstitialAd interstitialAd = new InterstitialAd(this);
        interstitialAd.setAdUnitId(getString(R.string.interstitial_ad_unit_id));
        interstitialAd.setAdListener(new AdListener() {
            @Override
            public void onAdLoaded() {
            }

            @Override
            public void onAdFailedToLoad(int errorCode) {

            }

            @Override
            public void onAdClosed() {
                // Proceed to the next level.
                Intent resultIntent = new Intent(getApplicationContext(), GameResult.class)
                        .putExtra("Result", score)
                        .putExtra("Name", 1);
                startActivity(resultIntent);
                finish();

            }
        });
        return interstitialAd;
    }

    private void showInterstitial() {
        // Show the ad if it's ready. Otherwise toast and reload the ad.
        if (interstitialAd != null && interstitialAd.isLoaded()) {
            interstitialAd.show();
        } else {
            Intent resultIntent = new Intent(this, GameResult.class)
                    .putExtra("Result", score)
                    .putExtra("Name", 1);
            startActivity(resultIntent);
            finish();
        }
    }

    private void loadInterstitial() {
        // Disable the next level button and load the ad.
        AdRequest adRequest = new AdRequest.Builder()
                .setRequestAgent("android_studio:ad_template").build();
        interstitialAd.loadAd(adRequest);
    }
}
