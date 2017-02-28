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

public class StagePlay extends AppCompatActivity {
    private GamePlay rules;

    private String usersChoice;
    private String computersChoice;
    private int computersScore;
    private int yourScore;
    private int stageLevel;
    private String result;

    private TextView stageLevelTV;
    private TextView computerScoreTV;
    private TextView yourScoreTV;
    private TextView resultTV;
    private ImageView computerChoiceIV;
    private ImageView usersChoiceIV;

    public static final String ROCK = "rock";
    public static final String PAPER = "paper";
    public static final String SCISSOR = "scissor";

    InterstitialAd interstitialAd;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_stage_play);
        computersScore = 0;
        yourScore = 0;
        stageLevel = 1;
        result = "Tap to Play!!";
        init();
        interstitialAd = newInterstitialAd();
        loadInterstitial();
        showcomputersScore(computersScore);
        showYourScore(yourScore);
        showResult(result);
        showStageLevel(stageLevel);


    }

    public void rockSP(View view) {
        usersChoice = ROCK;
        computersChoice = rules.computersChoice();
        setImages(usersChoice, computersChoice);
        finalResult(usersChoice,computersChoice);
        checkEndGame();
        checkStageLevel();

    }

    public void paperSP(View view) {
        usersChoice = PAPER;
        computersChoice = rules.computersChoice();
        setImages(usersChoice, computersChoice);
        finalResult(usersChoice,computersChoice);
        checkEndGame();
        checkStageLevel();
    }

    public void scissorSP(View view) {
        usersChoice = SCISSOR;
        computersChoice = rules.computersChoice();
        setImages(usersChoice, computersChoice);
        finalResult(usersChoice,computersChoice);
        checkEndGame();
        checkStageLevel();
    }

    public void quitSP(View view) {
        finish();
    }



    private void showYourScore(int score) {
        yourScoreTV.setText("You : "+score);
    }

    private void showcomputersScore(int computersScore) {
        computerScoreTV.setText("Computer : "+computersScore);
    }

    private void showResult(String result) {
        resultTV.setText(result);
        resultTV.setTextSize(15);
    }

    private void finalResult(String usersChoice,String computersChoice) {
        int results;
        results = rules.usersChoice(usersChoice, computersChoice);

        if (results == 1) {
            yourScore++;
            showYourScore(yourScore);
            showResult("You Win the Battle!!");
        } else if (results == 2){
            computersScore++;
            showcomputersScore(computersScore);
            showResult("Computer Win the Battle!!");

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

        computerScoreTV = (TextView) findViewById(R.id.computerScoreTVSP);
        yourScoreTV = (TextView) findViewById(R.id.yourScoreTVSP);
        resultTV = (TextView) findViewById(R.id.finalResultSP);
        stageLevelTV = (TextView) findViewById(R.id.stageLevelTV);
        computerChoiceIV = (ImageView) findViewById(R.id.computersChoiceIV);
        usersChoiceIV = (ImageView) findViewById(R.id.usersChoiceIV);
    }
    private void checkEndGame() {
        if (computersScore == 10) {
            showResult("You've Lost the Battle!");
            showInterstitial();
        }
    }
    private void checkStageLevel () {
        if (yourScore == 10) {
            stageLevel++;
            yourScore = 0;
            computersScore = 0;
            showStageLevel(stageLevel);
            showcomputersScore(computersScore);
            showYourScore(yourScore);
            resultTV.setText("Level UP");
            resultTV.setTextSize(30);
        }

    }

    private void showStageLevel (int stageLevel) {
        stageLevelTV.setText("Stage "+stageLevel);
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
                        .putExtra("Level", stageLevel)
                        .putExtra("Name", 0);
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
                    .putExtra("Level", stageLevel)
                    .putExtra("Name", 0);
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
