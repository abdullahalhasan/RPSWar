package me.hasan.rpswar;

import android.view.View;

import java.util.Random;

/**
 * Created by hasan on 2/27/17.
 */

public class GamePlay {

    private Random randomGenerator = new Random();

    public GamePlay() {

    }

    public int usersChoice(String usersChoice, String computersChoice) {

        if (usersChoice == computersChoice) {
            return 3; //match draw
        }

        else {
            if (usersChoice == ScorePlay.ROCK) {
                if (computersChoice == ScorePlay.PAPER) {
                    return 2; //computer win
                } else if (computersChoice == ScorePlay.SCISSOR){
                    return 1; //user win
                }
            } else if (usersChoice == ScorePlay.PAPER) {
                if (computersChoice == ScorePlay.ROCK) {
                    return 1; //user win
                } else if (computersChoice == ScorePlay.SCISSOR){
                    return 2; //computer win
                }
            } else if (usersChoice == ScorePlay.SCISSOR){
                if (computersChoice == ScorePlay.PAPER) {
                    return 1; //user win
                } else if (computersChoice == ScorePlay.ROCK){
                    return 2; //computer win
                }
            }
        }
        return 0;
    }

    public String computersChoice() {
        int computer = random();

        if (computer<34) {
            return "rock";
        } else if (computer>33 && computer<67) {
            return "paper";
        } else if (computer>66) {
            return "scissor";
        }
        return "Computer Gone Mad!!";
    }


    public int random() {
        int number;
        number = randomGenerator.nextInt(100);
        return number;
    }
}
