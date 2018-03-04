package com.example.yavor.pingpongcounter;

import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EdgeEffect;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

public class MainActivity extends AppCompatActivity {


    boolean setServe = false;
    private Player player1;
    private Player player2;
    private Button pOneB;
    private Button pTwoB;

    private TextView playerOneServeView;
    private TextView playerTwoServeView;

    private TextView playerOneScoreView;
    private TextView playerTwoScoreView;
    private TextView playerOneGameView;
    private TextView playerTwoGameView;

    private Button playerOnePlusScoreButton;
    private Button playerTwoPlusScoreButton;

    private Button playerOneMinusScoreButton;
    private Button playerTwoMinusScoreButton;
    private SharedPreferences saves;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        saves = getApplicationContext().getSharedPreferences("MyPref", MODE_PRIVATE);

        player1 = new Player();
        player2 = new Player();

        player1.setName(getString(R.string.playerOne));
        player2.setName(getString(R.string.playerTwo));


        playerOneServeView = findViewById(R.id.playerOneServe);
        playerTwoServeView = findViewById(R.id.playerTwoServe);

        playerOneScoreView = findViewById(R.id.playerOneScore);
        playerTwoScoreView = findViewById(R.id.playerTwoScore);

        playerOneGameView = findViewById(R.id.playerOneGames);
        playerTwoGameView = findViewById(R.id.playerTwoGames);

        playerOnePlusScoreButton = findViewById(R.id.playerOneScorePlusOne);
        playerTwoPlusScoreButton = findViewById(R.id.playerTwoScorePlusOne);

        playerOneMinusScoreButton = findViewById(R.id.playerOneScoreMinusOne);
        playerTwoMinusScoreButton = findViewById(R.id.playerTwoScoreMinusOne);

        pOneB = findViewById(R.id.playerOneServeButton);
        pTwoB = findViewById(R.id.playerTwoServeButton);


        playerOneServeView.setVisibility(View.INVISIBLE);
        playerTwoServeView.setVisibility(View.INVISIBLE);

        if (saves != null) {
            player1.setPoints(saves.getInt("playerOnePoints", 0));
            player1.setGames(saves.getInt("playerOneGames", 0));
            player1.setServe(saves.getBoolean("playerOneServe", false));

            player2.setPoints(saves.getInt("playerTwoPoints", 0));
            player2.setGames(saves.getInt("playerTwoGames", 0));
            player2.setServe(saves.getBoolean("playerTwoServe", false));

            if(player1.getServe()){
                playerOneServeView.setVisibility(View.VISIBLE);
            }
            if(player2.getServe()){
                playerTwoServeView.setVisibility(View.VISIBLE);
            }

            setServe = saves.getBoolean("setServe", false);

            playerOneScoreView.setText(String.valueOf(player1.getPoints()));
            playerOneGameView.setText(String.valueOf(player1.getGames()));

            playerTwoScoreView.setText(String.valueOf(player2.getPoints()));
            playerTwoGameView.setText(String.valueOf(player2.getGames()));

        }
        enableButtons();


    }

    public void addPointPlayerOne(View view) {
        player1.addPoint();
        updateScores();
    }

    public void subPointPlayerOne(View view) {
        player1.subPoint();
        updateScores();
    }

    public void subPointPlayerTwo(View view) {
        player2.subPoint();
        updateScores();
    }

    public void addPointPlayerTwo(View view) {
        player2.addPoint();
        updateScores();
    }

    private String wonGame() {
        int playerOnePoints = player1.getPoints();
        int playerTwoPoints = player2.getPoints();

        if (playerOnePoints >= 11) {
            if (playerTwoPoints < 10) {
                player1.addGame();
                playerOneServeMain();
                return player1.getName();
            } else if (playerOnePoints - playerTwoPoints >= 2) {
                player1.addGame();
                playerOneServeMain();
                return player1.getName();
            }
        }

        if (playerTwoPoints >= 11) {
            if (playerOnePoints < 10) {
                player2.addGame();
                playerTwoServeMain();
                return player2.getName();
            } else if (playerTwoPoints - playerOnePoints >= 2) {
                player2.addGame();
                playerTwoServeMain();
                return player2.getName();
            }
        }
        return null;
    }

    public void resetScoreButton(View view) {
        reset();
    }

    public void resetMatchButton(View view) {
        player1.resetGames();
        player2.resetGames();
        resetServe();
        reset();
        setServe = false;
        enableButtons();

    }

    private void reset() {


        player1.resetPoints();
        player2.resetPoints();

        playerOneScoreView.setText(getText(R.string.zero));
        playerTwoScoreView.setText(getText(R.string.zero));

        playerOneGameView.setText(Integer.toString(player1.getGames()));
        playerTwoGameView.setText(Integer.toString(player2.getGames()));
        // resetServe();

    }

    private void resetServe() {
        player1.setServe(false);
        player2.setServe(false);

        playerOneServeView.setVisibility(View.INVISIBLE);
        playerTwoServeView.setVisibility(View.INVISIBLE);
        pOneB.setClickable(true);
        pTwoB.setClickable(true);

        pOneB.animate().alpha(1.0f);
        pTwoB.animate().alpha(1.0f);

    }

    public void playerOneServe(View view) {
        playerOneServeMain();
    }

    private void playerOneServeMain() {
        player1.setServe(true);
        player2.setServe(false);

        setServe = true;
        enableButtons();

        playerOneServeView.setVisibility(View.VISIBLE);
        playerTwoServeView.setVisibility(View.INVISIBLE);
        pOneB.setClickable(false);
        pTwoB.setClickable(false);

        pOneB.animate().alpha(0.5f);
        pTwoB.animate().alpha(0.5f);

    }

    public void playerTwoServe(View view) {
        playerTwoServeMain();
    }

    private void playerTwoServeMain() {
        player1.setServe(false);
        player2.setServe(true);
        setServe = true;
        enableButtons();

        playerOneServeView.setVisibility(View.INVISIBLE);
        playerTwoServeView.setVisibility(View.VISIBLE);

        pOneB.setClickable(false);
        pTwoB.setClickable(false);

        pOneB.animate().alpha(0.5f);
        pTwoB.animate().alpha(0.5f);


    }

    private void calculateServe() {
        if (setServe) {
            int points = player1.getPoints() + player2.getPoints();
            if (points % 2 == 0 && points > 0) {
                if (player1.getServe()) {
                    playerTwoServeMain();
                } else if (player2.getServe()) {
                    playerOneServeMain();
                }
            }
        }
    }

    private void enableButtons() {
        if (setServe) {
            playerOnePlusScoreButton.setClickable(true);
            playerTwoPlusScoreButton.setClickable(true);
            playerOneMinusScoreButton.setClickable(true);
            playerTwoMinusScoreButton.setClickable(true);

            playerOnePlusScoreButton.animate().alpha(1f);
            playerTwoPlusScoreButton.animate().alpha(1f);
            playerOneMinusScoreButton.animate().alpha(1f);
            playerTwoMinusScoreButton.animate().alpha(1f);
        } else {
            playerOnePlusScoreButton.setClickable(false);
            playerTwoPlusScoreButton.setClickable(false);

            playerOneMinusScoreButton.setClickable(false);
            playerTwoMinusScoreButton.setClickable(false);

            playerOnePlusScoreButton.animate().alpha(0.5f);
            playerTwoPlusScoreButton.animate().alpha(0.5f);
            playerOneMinusScoreButton.animate().alpha(0.5f);
            playerTwoMinusScoreButton.animate().alpha(0.5f);
        }


    }

    private void updateScores() {
        String winner;
        calculateServe();

        playerOneScoreView.setText(Integer.toString(player1.getPoints()));
        playerOneGameView.setText(Integer.toString(player1.getGames()));
        playerTwoScoreView.setText(Integer.toString(player2.getPoints()));
        playerTwoGameView.setText(Integer.toString(player2.getGames()));
        winner = wonGame();
        if (winner != null) {
            Toast.makeText(this, getString(R.string.winner) + " " + winner, Toast.LENGTH_LONG).show();
            reset();
        }
    }

    @Override
    protected void onDestroy() {

        final SharedPreferences.Editor editor = saves.edit();

        editor.putInt("playerOnePoints", player1.getPoints());
        editor.putInt("playerOneGames", player1.getGames());
        editor.putBoolean("playerOneServe", player1.getServe());

        editor.putInt("playerTwoPoints", player2.getPoints());
        editor.putInt("playerTwoGames", player2.getGames());
        editor.putBoolean("playerTwoServe", player2.getServe());

        editor.putBoolean("setServe", setServe);
        editor.commit();
        super.onDestroy();
    }
}
