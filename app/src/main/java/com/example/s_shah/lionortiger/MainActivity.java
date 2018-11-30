package com.example.s_shah.lionortiger;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.GridLayout;
import android.widget.ImageView;
import android.widget.Toast;

import static java.lang.Integer.parseInt;

public class MainActivity extends AppCompatActivity {

    enum Player {
        ONE, TWO, No
    }

    Player currentPlayer = Player.ONE;
    Player[] playerChoices = new Player[9];
    int[][] winningColumns = {{0, 1, 2}, {3, 4, 5}, {6, 7, 8}, {0, 3, 6}, {1, 4, 7}, {2, 5, 8}, {0, 4, 8}, {2, 4, 6}};

    private boolean gameOver = false;
    private Button reset_btn;
    private android.support.v7.widget.GridLayout gridLayout;
    private int counter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        for (int index = 0; index <playerChoices.length; index++){
            playerChoices[index] = Player.No;
        }

        reset_btn = findViewById(R.id.reset_btn);
        gridLayout = findViewById(R.id.gridLayout);
        counter = 0;

        reset_btn.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                resetgame();
            }
        });
    }

    public void imageViewIsTapped(View imageView) {
        ImageView tappedImageView = (ImageView) imageView;
        int tiTag = Integer.parseInt(tappedImageView.getTag().toString());


        if (counter == playerChoices.length){reset_btn.setVisibility(View.VISIBLE);
        gameOver = true;
        Toast.makeText(this, "It's a draw!", Toast.LENGTH_LONG).show();
        }

        if(playerChoices[tiTag]==Player.No && gameOver == false) {
            tappedImageView.setTranslationX(-2000);
            playerChoices[tiTag] = currentPlayer;

            if (currentPlayer == Player.ONE) {
                tappedImageView.setImageResource(R.drawable.lion);
                currentPlayer = Player.TWO;
                counter+=1;
            } else if (currentPlayer == Player.TWO) {
                tappedImageView.setImageResource(R.drawable.tiger);
                currentPlayer = Player.ONE;
                counter+=1;
            }
            tappedImageView.animate().translationXBy(2000).alpha(1).rotation(3600).setDuration(1000);
            // Toast.makeText(this, tappedImageView.getTag().toString(), Toast.LENGTH_SHORT).show();

            for (int[] winnerColumns : winningColumns) {
                if (playerChoices[winnerColumns[0]] == playerChoices[winnerColumns[1]]
                        && playerChoices[winnerColumns[1]]
                        == playerChoices[winnerColumns[2]] && playerChoices[winnerColumns[0]] != Player.No) {

                    reset_btn.setVisibility(View.VISIBLE);
                    gameOver = true;
                    String winnerOfGame = "";

                    if (currentPlayer == Player.ONE) {
                        winnerOfGame = "Tiger";
                    } else if (currentPlayer == Player.TWO) {
                        winnerOfGame = "Lion";
                    }

                    Toast.makeText(this, winnerOfGame + " Wins!", Toast.LENGTH_LONG).show();
                }
            }
        }
    }

    //RESET GAME FUNCTION
    private void resetgame(){
        for(int index = 0; index < gridLayout.getChildCount(); index++){
            ImageView imageView = (ImageView) gridLayout.getChildAt(index);
            imageView.setImageDrawable(null);
            imageView.setAlpha(0.2f);
        }
        currentPlayer = Player.ONE;
        for (int index = 0; index <playerChoices.length; index++){
            playerChoices[index] = Player.No;
        }

        gameOver = false;
        counter = 0;
        reset_btn.setVisibility(View.GONE);
    }
}

