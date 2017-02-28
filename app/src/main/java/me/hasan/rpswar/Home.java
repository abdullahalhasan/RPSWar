package me.hasan.rpswar;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.InterstitialAd;
import com.google.android.gms.ads.reward.RewardedVideoAd;

public class Home extends AppCompatActivity {

    private InterstitialAd interstitialAd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        AdView adViewTop = (AdView) findViewById(R.id.adViewHomeTop);
        AdView adViewBottom = (AdView) findViewById(R.id.adViewHomeBottom);
        AdRequest adRequest = new AdRequest.Builder().build();

        interstitialAd = newInterstitialAd();
        loadInterstitial();

        adViewTop.loadAd(adRequest);
        adViewBottom.loadAd(adRequest);
    }

    public void scorePlay(View view) {
        Intent scorePlayIntent = new Intent(this, ScorePlay.class);
        startActivity(scorePlayIntent);
    }

    public void stagePlay(View view) {
        startActivity(new Intent(this, StagePlay.class));
    }

    public void highScore(View view) {
        showInterstitial();
    }

    public void instructions(View view) {
        startActivity(new Intent(getApplicationContext(), Instruction.class));
    }

    public void reinforcement(View view) {
        startActivity(new Intent(getApplicationContext(), Reinforcement.class));
    }

    public void share(View view) {
        Intent intent = new Intent(Intent.ACTION_SEND);
        intent.setType("text/plain");
        String shareBody = "https://play.google.com/store/apps/details?id=me.hasan.rpswar&hl=en";
        String shareSubject = "MIU Portal";

        intent.putExtra(Intent.EXTRA_SUBJECT, shareSubject);
        intent.putExtra(Intent.EXTRA_TEXT, shareBody);

        startActivity(Intent.createChooser(intent, "Share Using"));
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
                startActivity(new Intent(getApplicationContext(), HighScore.class));

            }
        });
        return interstitialAd;
    }

    private void showInterstitial() {
        // Show the ad if it's ready. Otherwise toast and reload the ad.
        if (interstitialAd != null && interstitialAd.isLoaded()) {
            interstitialAd.show();
        } else {
            startActivity(new Intent(getApplicationContext(), HighScore.class));
        }
    }

    private void loadInterstitial() {
        // Disable the next level button and load the ad.
        AdRequest adRequest = new AdRequest.Builder()
                .setRequestAgent("android_studio:ad_template").build();
        interstitialAd.loadAd(adRequest);
    }
}