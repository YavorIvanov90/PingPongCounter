package com.example.yavor.pingpongcounter;

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
    private EditText playerOneNameView;
    private EditText playerTwoNameView;

    private TextView playerOneServeView;
    private TextView playerTwoServeView;

    private TextView playerOneScoreView;
    private TextView playerTwoScoreView;
    private TextView playerOneGameView;
    private TextView playerTwoGameView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        player1 = new Player();
        player2 = new Player();

        player1.setName("Player1");
        player2.setName("Player2");

        playerOneNameView = findViewById(R.id.playerOneName);
        playerTwoNameView = findViewById(R.id.playerTwoName);

        playerOneServeView = findViewById(R.id.playerOneServe);
        playerTwoServeView = findViewById(R.id.playerTwoServe);

        playerOneScoreView = findViewById(R.id.playerOneScore);
        playerTwoScoreView = findViewById(R.id.playerTwoScore);

        playerOneGameView = findViewById(R.id.playerOneGames);
        playerTwoGameView = findViewById(R.id.playerTwoGames);

        playerOneNameView.setText(player1.getName());
        playerTwoNameView.setText(player2.getName());

        pOneB = findViewById(R.id.playerOneServeButton);
        pTwoB = findViewById(R.id.playerTwoServeButton);


        playerOneServeView.setVisibility(View.INVISIBLE);
        playerTwoServeView.setVisibility(View.INVISIBLE);


        playerOneNameView.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                player1.setName(playerOneNameView.getText().toString());
              //  playerOneNameView.clearFocus();
            }
        });

        playerTwoNameView.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                player2.setName(playerTwoNameView.getText().toString());
               // playerTwoNameView.clearFocus();
            }
        });

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
                playerTwoServeMain();
                return player1.getName();
            }
        }

        if (playerTwoPoints >= 11) {
            if (playerOnePoints < 10) {
                player2.addGame();
                return player2.getName();
            } else if (playerTwoPoints - playerOnePoints >= 2) {
                player2.addGame();
                return player2.getName();
            }
        }
        return null;
    }

    public void resetScoreButton(View view) {
        reset();
    }

    public void resetGameButton(View view) {
        player1.resetGames();
        player2.resetGames();

        reset();

    }

    private void reset() {


        player1.resetPoints();
        player2.resetPoints();

        playerOneScoreView.setText("0");
        playerTwoScoreView.setText("0");

        playerOneGameView.setText(Integer.toString(player1.getGames()));
        playerTwoGameView.setText(Integer.toString(player2.getGames()));

        resetServe();

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

        setServe = false;
    }

    public void playerOneServe(View view) {
        playerOneServeMain();
    }

    private void playerOneServeMain() {
        player1.setServe(true);
        player2.setServe(false);

        playerOneServeView.setVisibility(View.VISIBLE);
        playerTwoServeView.setVisibility(View.INVISIBLE);
        pOneB.setClickable(false);
        pTwoB.setClickable(false);

        pOneB.animate().alpha(0.5f);
        pTwoB.animate().alpha(0.5f);

        setServe = true;
    }

    public void playerTwoServe(View view) {
        playerTwoServeMain();
    }

    private void playerTwoServeMain() {
        player1.setServe(false);
        player2.setServe(true);

        playerOneServeView.setVisibility(View.INVISIBLE);
        playerTwoServeView.setVisibility(View.VISIBLE);

        pOneB.setClickable(false);
        pTwoB.setClickable(false);

        pOneB.animate().alpha(0.5f);
        pTwoB.animate().alpha(0.5f);

        setServe = true;
    }

    private void calculateServe() {
        int points = player1.getPoints() + player2.getPoints();
        if (points % 2 == 0) {
            if (player1.getServe()) {
                playerTwoServeMain();
            } else if (player2.getServe()) {
                playerOneServeMain();
            }
        }
    }


    private void updateScores() {
        String winner = "";
        calculateServe();

        playerOneScoreView.setText(Integer.toString(player1.getPoints()));
        playerOneGameView.setText(Integer.toString(player1.getGames()));
        playerTwoScoreView.setText(Integer.toString(player2.getPoints()));
        playerTwoGameView.setText(Integer.toString(player2.getGames()));
        winner = wonGame();
        if (winner != null) {
            Toast.makeText(this, winner, Toast.LENGTH_LONG).show();
            reset();
        }
    }


}
