package com.example.tictactoegame;

import android.graphics.Color;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    //make matrix for button 3*3
    private Button[][] buttons = new Button[3][3];
    private boolean player1Turn = true;

    private boolean player2Turn = true;
    //count each round
    private int roundCount;

    //wining player point
    private int player1Points;
    private int player2Points;

    //show result in text
    private TextView textViewPlayer1;
    private TextView textViewPlayer2;

    //for count drow
    int drowCount = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        textViewPlayer1 = findViewById(R.id.text_view_p1);
        textViewPlayer2 = findViewById(R.id.text_view_p2);

        //identify all button
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                String buttonID = "button_" + i + j;
                int resID = getResources().getIdentifier(buttonID, "id", getPackageName());
                buttons[i][j] = findViewById(resID);
                buttons[i][j].setOnClickListener(this);
            }
        }

        Button buttonReset = findViewById(R.id.button_reset);
        buttonReset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                resetGame();
            }
        });
    }


    // make all button as a button click
    @Override
    public void onClick(View v) {
        if (!((Button) v).getText().toString().equals("")) {
            return;
        }

        if (player1Turn) {
            ((Button) v).setText("X");
            ((Button) v).setTextColor(Color.parseColor("#ff0000"));
        }else {
            ((Button) v).setText("O");
        }

        roundCount++;

        if (checkForWin()) {
            if (player1Turn) {
                player1Wins();
            } else {
                player2Wins();
            }
        } else if (roundCount == 9) {
            draw();
        } else {
            player1Turn = !player1Turn;
        }

    }

    //check which player is win
    private boolean checkForWin() {
        String[][] field = new String[3][3];

        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                field[i][j] = buttons[i][j].getText().toString();
            }
        }

        for (int i = 0; i < 3; i++) {
            if (field[i][0].equals(field[i][1])
                    && field[i][0].equals(field[i][2])
                    && !field[i][0].equals("")) {
                return true;
            }
        }

        for (int i = 0; i < 3; i++) {
            if (field[0][i].equals(field[1][i])
                    && field[0][i].equals(field[2][i])
                    && !field[0][i].equals("")) {
                return true;
            }
        }

        if (field[0][0].equals(field[1][1])
                && field[0][0].equals(field[2][2])
                && !field[0][0].equals("")) {
            return true;
        }

        if (field[0][2].equals(field[1][1])
                && field[0][2].equals(field[2][0])
                && !field[0][2].equals("")) {
            return true;
        }

        return false;
    }

    private void player1Wins() {
        player1Points++;

            LayoutInflater inflater = getLayoutInflater();
            View layout = inflater.inflate(R.layout.toast_layout, (ViewGroup) findViewById(R.id.toast_root));

            TextView toastText = layout.findViewById(R.id.toast_text);
            ImageView toastImage = layout.findViewById(R.id.toast_image);
            toastImage.setImageResource(R.drawable.emoji);
            Toast toast = new Toast(getApplicationContext());
            toast.setGravity(Gravity.CENTER, 0, 0);
            toast.setDuration(Toast.LENGTH_SHORT);
            toast.setView(layout);
            toast.show();

        textViewPlayer1.setText("Player 1 Winning Time: " + player1Points);
        //for reset board
        resetBoard();

        updatePointsText();
    }

    private void player2Wins() {
        player2Points++;

        LayoutInflater inflater = getLayoutInflater();
        View layout = inflater.inflate(R.layout.toast_layout, (ViewGroup) findViewById(R.id.toast_root));

        TextView toastText = layout.findViewById(R.id.toast_text);
        ImageView toastImage = layout.findViewById(R.id.toast_image);
        toastImage.setImageResource(R.drawable.emoji);
        Toast toast = new Toast(getApplicationContext());
        toast.setGravity(Gravity.CENTER, 0, 0);
        toast.setDuration(Toast.LENGTH_SHORT);
        toast.setView(layout);
        toast.show();

        textViewPlayer2.setText("Player 2 Winning Time: " + player2Points);
        resetBoard();

        updatePointsText();
    }

    private void draw() {

        drowCount++;

        LayoutInflater inflater = getLayoutInflater();
        View layout = inflater.inflate(R.layout.toast_draw, (ViewGroup) findViewById(R.id.toast_draw_id));

        TextView toastText = layout.findViewById(R.id.toast_draw_text);

        toastText.setText(" Match Draw \uD83D\uDE0D .Try Again ");

        Toast toast = new Toast(getApplicationContext());
        toast.setGravity(Gravity.CENTER, 0, 0);
        toast.setDuration(Toast.LENGTH_SHORT);
        toast.setView(layout);
        toast.show();

        resetBoard();
    }

    private void updatePointsText() {

        int count=player1Points + player2Points + drowCount;

        if(count == 11){

            resetBoard();
            resetGame();

            textViewPlayer1.setText("Player 1 Winning Time: 0");
            textViewPlayer2.setText("Player 2 Winning Time: 0");

            LayoutInflater inflater = getLayoutInflater();
            View layout = inflater.inflate(R.layout.complete_level, (ViewGroup) findViewById(R.id.toast_level_complete));

            TextView toastText = layout.findViewById(R.id.level_complete_text);

            toastText.setText(" Finished 10 level successfully \uD83D\uDE0D .Try Again ");

            Toast toast = new Toast(getApplicationContext());
            toast.setGravity(Gravity.CENTER, 0, 0);
            toast.setDuration(Toast.LENGTH_SHORT);
            toast.setView(layout);
            toast.show();

        }
    }

    //reset all button text
    private void resetBoard() {
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                buttons[i][j].setText("");
            }
        }

        roundCount = 0;
        player1Turn = true;
    }

    //code for reset option
    private void resetGame() {
        player1Points = 0;
        player2Points = 0;

        textViewPlayer1.setText("Player 1 Winning Time: 0");
        textViewPlayer2.setText("Player 2 Winning Time: 0");

        updatePointsText();
        resetBoard();
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);

        outState.putInt("roundCount", roundCount);
        outState.putInt("player1Points", player1Points);
        outState.putInt("player2Points", player2Points);
        outState.putBoolean("player1Turn", player1Turn);
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);

        roundCount = savedInstanceState.getInt("roundCount");
        player1Points = savedInstanceState.getInt("player1Points");
        player2Points = savedInstanceState.getInt("player2Points");
        player1Turn = savedInstanceState.getBoolean("player1Turn");
    }
}
