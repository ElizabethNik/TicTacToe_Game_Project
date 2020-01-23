package com.example.tictactoegameproject;

import android.os.Bundle;
import android.view.View;
import android.widget.GridLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {
    //0 = Mars, 1 = Earth
    int activePlayer = 0;
    boolean isActive=true;
    boolean isPlayerOneChoice=true;

    int[] gameState = {2, 2, 2, 2, 2, 2, 2, 2, 2};
    int[][] winningPositions = {{0, 1, 2}, {3, 4, 5}, {6, 7, 8}, {0, 3, 6}, {1, 4, 7}, {2, 5, 8}, {0, 4, 8}, {2, 4, 6}};
    int[] possibleTeamChoices = {R.drawable.mercury, R.drawable.venus, R.drawable.earth, R.drawable.mars,
            R.drawable.jupiter, R.drawable.saturn, R.drawable.uranus, R.drawable.neptune, R.drawable.moon };

    private int playerOneImgRes;
    private int playerTwoImgRes;


    public void showPlayerOneTeamChoices(View view) {
        ImageView startImage = (ImageView) findViewById(R.id.galaxyImageView);
        startImage.setVisibility(View.INVISIBLE);

        LinearLayout startLayout = (LinearLayout) findViewById(R.id.startLayout);
        startLayout.setVisibility(View.INVISIBLE);

        ImageView galaxySecondPage = (ImageView) findViewById(R.id.galaxySecondPageIV);
        galaxySecondPage.setVisibility(View.VISIBLE);

        LinearLayout teamLayout = (LinearLayout) findViewById(R.id.teamLayout);
        GridLayout choicesLayout = (GridLayout) findViewById(R.id.teamChoices);

        for(int i = 0; i<choicesLayout.getChildCount() && i<possibleTeamChoices.length; i++) {
            ImageView planet = (ImageView)choicesLayout.getChildAt(i);
            planet.setImageResource(possibleTeamChoices[i]);
            planet.setTag(possibleTeamChoices[i]);
            planet.setVisibility(View.VISIBLE);
        }

        TextView playerOneChoice = (TextView) findViewById(R.id.playerOneChoiceTV);
        playerOneChoice.setVisibility(View.VISIBLE);

        playerOneChoice.animate()
                .translationYBy(50f)
                .setDuration(1500);

        teamLayout.setVisibility(View.VISIBLE);

    }

    public void chooseTeam(View view) {
        if(isPlayerOneChoice) {
            isPlayerOneChoice = false;//When we call this method for a second time, it's player two choice

            view.setOnClickListener(null);
            playerOneImgRes = (Integer) view.getTag();

            view.setVisibility(View.INVISIBLE);

            TextView playerOneChoice = (TextView) findViewById(R.id.playerOneChoiceTV);
            playerOneChoice.setVisibility(View.INVISIBLE);
            playerOneChoice.animate().translationYBy(-50f);

            TextView playerTwoChoice = (TextView) findViewById(R.id.playerTwoChoiceTV);
            playerTwoChoice.setVisibility(View.VISIBLE);
            playerTwoChoice.animate()
                    .translationYBy(50f)
                    .setDuration(1500);
        }else {

            isPlayerOneChoice = true;

            playerTwoImgRes = (Integer) view.getTag();

            TextView playerTwoChoice = (TextView) findViewById(R.id.playerTwoChoiceTV);
            playerTwoChoice.setVisibility(View.INVISIBLE);
            playerTwoChoice.animate().translationYBy(-50f);

            ImageView galaxySecondPage = (ImageView) findViewById(R.id.galaxySecondPageIV);
            galaxySecondPage.setVisibility(View.INVISIBLE);

            LinearLayout teamLayout = (LinearLayout) findViewById(R.id.teamLayout);
            teamLayout.setVisibility(View.INVISIBLE);

            GridLayout boardLayout = (GridLayout)findViewById(R.id.boardLayout);
            boardLayout.setVisibility(View.VISIBLE);

            ImageView galaxyThirdPage = (ImageView) findViewById(R.id.galaxyThirdPageIV);
            galaxyThirdPage.setVisibility(View.VISIBLE);
        }

    }

    //2 means unplayed;
    public void dropIn(View view) {
        ImageView counter = (ImageView) view; //getting the image, on which is tapped

        counter.setOnClickListener(null);
        int tappedCounter = Integer.parseInt(counter.getTag().toString());


        if (gameState[tappedCounter] == 2 && isActive) {
            gameState[tappedCounter] = activePlayer;

            counter.setTranslationY(-1000f); //then moving it 1000px up

            if (activePlayer == 0) {
                counter.setImageResource(playerOneImgRes); //then setting the mars.png to the ImageView

                activePlayer = 1;

            } else {
                counter.setImageResource(playerTwoImgRes); //then setting the mars.png to the ImageView

                activePlayer = 0;
            }
        }

        counter.animate()
                .translationYBy(1000f)
                .rotation(360)
                .setDuration(300); //animating it

//    int[][] winningPositions = {{0, 1, 2}, {3, 4, 5}, {6, 7, 8}, {0, 3, 6}, {1, 4, 7}, {2, 5, 8}, {0, 4, 8}, {2, 4, 6}};
        for (int[] winningPosition : winningPositions) { // each arr in winningPositions will be set to the arr winningPosition
            if (gameState[winningPosition[0]] == gameState[winningPosition[1]] &&
                    gameState[winningPosition[1]] == gameState[winningPosition[2]] &&
                    gameState[winningPosition[0]] != 2) {

                //Someone has won!

                isActive=false;
                String winner = "Player Two";

                if (gameState[winningPosition[0]] == 0) {
                    winner = "Player One";
                }

                TextView winnerMessage = (TextView) findViewById(R.id.winnerMessage);
                winnerMessage.setText(winner + " has won!");

                LinearLayout layout = (LinearLayout) findViewById(R.id.playAgainLayout);
                layout.setVisibility(View.VISIBLE);

                return;

            }else {
                boolean gameIsOver = true;
                for(int counterState : gameState) {
                    if(counterState == 2) {
                        gameIsOver = false;
                    }
                }
                if(gameIsOver) {

                    TextView winnerMessage = (TextView) findViewById(R.id.winnerMessage);
                    winnerMessage.setText("It's a draw");

                    LinearLayout layout = (LinearLayout) findViewById(R.id.playAgainLayout);
                    layout.setVisibility(View.VISIBLE);
                }
            }

        }
    }

    public void playAgain(View view) {
        isActive = true;

        GridLayout boardLayout = (GridLayout)findViewById(R.id.boardLayout);
        for(int i = 0; i < boardLayout.getChildCount(); i++)
        {
            boardLayout.getChildAt(i).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    dropIn(v);
                }
            });
        }

        GridLayout teamChoices = (GridLayout)findViewById(R.id.teamChoices);
        for(int i = 0; i < teamChoices.getChildCount(); i++)
        {
            teamChoices.getChildAt(i).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    chooseTeam(v);
                }
            });
        }

        ImageView galaxyThirdPage = (ImageView) findViewById(R.id.galaxyThirdPageIV);
        galaxyThirdPage.setVisibility(View.INVISIBLE);

        LinearLayout layout = (LinearLayout) findViewById(R.id.playAgainLayout  );
        layout.setVisibility(View.INVISIBLE);
        //layout.animate().alpha(1f).setDuration(2000);

        activePlayer = 0;
        for(int i=0; i<gameState.length; i++){
            gameState[i]=2;
        }

        GridLayout gridLayout = (GridLayout)findViewById(R.id.boardLayout);
        for(int i = 0; i<gridLayout.getChildCount(); i++) {
            ((ImageView)gridLayout.getChildAt(i)).setImageResource(0);
        }

        gridLayout.setVisibility(View.GONE);

        showPlayerOneTeamChoices(null);

    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }
}

