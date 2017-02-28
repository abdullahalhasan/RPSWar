package me.hasan.rpswar;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;

public class Instruction extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_instruction);

        AdView adViewTop = (AdView) findViewById(R.id.adViewInstructionTop);
        AdView adViewBottom = (AdView) findViewById(R.id.adViewInstructionBottom);
        AdRequest adRequest = new AdRequest.Builder().build();

        adViewTop.loadAd(adRequest);
        adViewBottom.loadAd(adRequest);
    }
}
