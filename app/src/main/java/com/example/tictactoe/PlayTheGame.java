package com.example.tictactoe;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

public class PlayTheGame extends AppCompatActivity implements View.OnClickListener {

    TextView Player1Display;
    TextView Player1Score;
    String Player1Name;

    TextView Player2Display;
    TextView Player2Score;
    String Player2Name;

    TextView RoundIndicator;
    TextView TurnIndicator;
    int CurrentTurn;
    int TotalRounds;
    int CurrentRound;

    int Player1ScoreCount;
    int Player2ScoreCount;

    int isVisited[]=new int[9];
    int pattern[]=new int[9];
    
    int Winner;

    //Game Buttons
    Button btn0;
    Button btn1;
    Button btn2;
    Button btn3;
    Button btn4;
    Button btn5;
    Button btn6;
    Button btn7;
    Button btn8;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_play_the_game);

        //Showing Rules Through Dialogue BOX


        Dialog dialog=new Dialog(PlayTheGame.this);
        dialog.setContentView(R.layout.dialog_login);
        dialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT,ViewGroup.LayoutParams.WRAP_CONTENT);
        dialog.setCancelable(false);

        Button PlayFromDialog=(Button) dialog.findViewById(R.id.BtnDialouge);

        PlayFromDialog.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(PlayTheGame.this, "Best Of Luck!", Toast.LENGTH_SHORT).show();
                dialog.dismiss();
            }
        });
        dialog.show();


        // Retrieving the Data from INTENT
        Intent intent=getIntent();
        Player1Name=String.valueOf(intent.getStringExtra("Player1Name"));
        Player2Name=String.valueOf(intent.getStringExtra("Player2Name"));
        TotalRounds=Integer.parseInt(intent.getStringExtra("Rounds"));


        //Taking References From Layout
        Player1Display=(TextView) findViewById(R.id.Player1Display);
        Player1Score=(TextView) findViewById(R.id.Player1Score);

        Player2Display=(TextView) findViewById(R.id.Player2Display);
        Player2Score=(TextView) findViewById(R.id.Player2Score);

        RoundIndicator=(TextView) findViewById(R.id.RoundIndicator);
        TurnIndicator=(TextView) findViewById(R.id.TurnIndicator);

        //Taking references for the buttons of The game (Playing Buttons)
        btn0 = (Button) findViewById (R.id.btn0);
        btn1 = (Button) findViewById (R.id.btn1);
        btn2 = (Button) findViewById (R.id.btn2);
        btn3 = (Button) findViewById (R.id.btn3);
        btn4 = (Button) findViewById (R.id.btn4);
        btn5 = (Button) findViewById (R.id.btn5);
        btn6 = (Button) findViewById (R.id.btn6);
        btn7 = (Button) findViewById (R.id.btn7);
        btn8 = (Button) findViewById (R.id.btn8);

       


        //Setting Initial Values of All Components Of Acticity Play Layout!

        CurrentRound=1;
        Player1Display.setText(Player1Name);
        Player2Display.setText(Player2Name);
        Player1Score.setText("0");
        Player2Score.setText("0");
        Winner=0;
        Player1ScoreCount=0;
        Player2ScoreCount=0;

        TurnIndicator.setText(Player1Name+"'s TURN");
        CurrentTurn=1;

        //Game Started!

    }



    public void onClick(View v)
    {
        Button ClickedButton=findViewById(v.getId());
        int Tag=Integer.parseInt((String) ClickedButton.getTag());

        if(isVisited[Tag]==1)return;

        if(CurrentTurn==1)
        {
            ClickedButton.setTextColor(ColorStateList.valueOf(Color.rgb(244,67,54)));
            ClickedButton.setText("O");
            ClickedButton.setBackgroundTintList(ColorStateList.valueOf(Color.argb(60, 255, 17, 00)));
            CurrentTurn=2;
            TurnIndicator.setText(Player2Name+"'s Turn..");
            pattern[Tag]=1;
        }
        else if(CurrentTurn==2)
        {
            ClickedButton.setTextColor(ColorStateList.valueOf(Color.rgb(0,255,10)));
            ClickedButton.setText("X");
            ClickedButton.setBackgroundTintList(ColorStateList.valueOf(Color.argb(100, 76, 175, 80)));
            CurrentTurn=1;
            TurnIndicator.setText(Player1Name+"'s Turn..");
            pattern[Tag]=2;
        }
        isVisited[Tag]=1;
        Winner=checkForWinner();


        if(Winner!=0)
        {
            CurrentRound++;

            Dialog dialog=new Dialog(PlayTheGame.this);
            dialog.setContentView(R.layout.winner_dialouge);
            dialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT,ViewGroup.LayoutParams.WRAP_CONTENT);
            dialog.setCancelable(false);

            Button PlayFromDialog=(Button) dialog.findViewById(R.id.btnNextRound);
            TextView GameResultOnDialog=(TextView) dialog.findViewById(R.id.GameResultIndicator);
            TextView GameWinnerOnDialog=(TextView) dialog.findViewById(R.id.WinnerName);
            GameResultOnDialog.setText("WINNER");


            if(Winner==1)
            {
                GameWinnerOnDialog.setTextColor(ColorStateList.valueOf(Color.rgb(244,67,54)));
                GameWinnerOnDialog.setText(Player1Name);
                Player1ScoreCount+=2;
            }
            else if(Winner==2)
            {
                GameWinnerOnDialog.setTextColor(ColorStateList.valueOf(Color.rgb(0,255,10)));
                GameWinnerOnDialog.setText(Player2Name);
                Player2ScoreCount+=2;
            }
            if(CurrentRound==TotalRounds+1)
            {
                Player1Score.setText(String.valueOf(Player1ScoreCount));
                Player2Score.setText(String.valueOf(Player2ScoreCount));
                GameResultOnDialog.setText("WINNER OF GAME");
                PlayFromDialog.setText("Game Over!");
                if(Player1ScoreCount>Player2ScoreCount)
                {
                    GameWinnerOnDialog.setText(Player1Name);
                }
                else if(Player2ScoreCount>Player1ScoreCount)
                {
                    GameWinnerOnDialog.setText(Player2Name);
                }
                else
                {
                    GameResultOnDialog.setText("GAME DRAWN!");
                    GameWinnerOnDialog.setText("");
                }
            }
            PlayFromDialog.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                   // Toast.makeText(PlayTheGame.this, "Best Of Luck!", Toast.LENGTH_SHORT).show();

                    Player1Score.setText(String.valueOf(Player1ScoreCount));
                    Player2Score.setText(String.valueOf(Player2ScoreCount));
                    if(CurrentRound==TotalRounds+1)
                    {
                        dialog.dismiss();
                        finish();
                        return;
                    }
                    isVisited=new int[9];
                    pattern=new int[9];
                    InitialseButtons();
                    dialog.dismiss();
                    return;
                }
            });
            Toast.makeText(PlayTheGame.this, Player2Name+" is Winner!", Toast.LENGTH_SHORT).show();
            dialog.show();

            return;
        }

        if(IsItaTie())
        {
            CurrentRound++;
            Player1ScoreCount+=1;
            Player2ScoreCount+=1;
            Dialog dialog=new Dialog(PlayTheGame.this);
            dialog.setContentView(R.layout.winner_dialouge);
            dialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT,ViewGroup.LayoutParams.WRAP_CONTENT);
            dialog.setCancelable(false);

            Button PlayFromDialog=(Button) dialog.findViewById(R.id.btnNextRound);
            TextView GameResultOnDialog=(TextView) dialog.findViewById(R.id.GameResultIndicator);
            TextView GameWinnerOnDialog=(TextView) dialog.findViewById(R.id.WinnerName);
            GameWinnerOnDialog.setText("");
            GameResultOnDialog.setText("DRAW!");

            if(CurrentRound==TotalRounds+1)
            {
                Player1Score.setText(String.valueOf(Player1ScoreCount));
                Player2Score.setText(String.valueOf(Player2ScoreCount));
                GameResultOnDialog.setText("WINNER OF GAME");
                PlayFromDialog.setText("Game Over!");
                if(Player1ScoreCount>Player2ScoreCount)
                {
                    GameWinnerOnDialog.setText(Player1Name);
                }
                else if(Player2ScoreCount>Player1ScoreCount)
                {
                    GameWinnerOnDialog.setText(Player2Name);
                }
                else
                {
                    GameResultOnDialog.setText("GAME DRAWN!");
                    GameWinnerOnDialog.setText("");
                    Player1Score.setText(String.valueOf(Player1ScoreCount));
                    Player2Score.setText(String.valueOf(Player2ScoreCount));
                }
            }

            PlayFromDialog.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    // Toast.makeText(PlayTheGame.this, "Best Of Luck!", Toast.LENGTH_SHORT).show();
                    isVisited=new int[9];
                    pattern=new int[9];
                    Player1Score.setText(String.valueOf(Player1ScoreCount));
                    Player2Score.setText(String.valueOf(Player2ScoreCount));
                    if(CurrentRound==TotalRounds+1)
                    {
                        dialog.dismiss();
                        finish();
                        return;
                    }
                    InitialseButtons();
                    dialog.dismiss();
                    return;
                }
            });
            //Toast.makeText(PlayTheGame.this, Player2Name+" is Winner!", Toast.LENGTH_SHORT).show();
            dialog.show();
        }
    }


    int checkForWinner()
    {
        int[][] WinningSituations={{0,1,2},{3,4,5},{6,7,8},{0,3,6},{1,4,7},{2,5,8},{0,4,8},{2,4,6}};

        for(int i=0;i<8;i++)
        {
            if(pattern[WinningSituations[i][0]]==pattern[WinningSituations[i][1]] && pattern[WinningSituations[i][0]]==pattern[WinningSituations[i][2]] && pattern[WinningSituations[i][0]]!=0)
            {
                return pattern[WinningSituations[i][0]];
            }
        }
        return 0;
    }

    boolean IsItaTie()
    {
        for(int i=0;i<9;i++)
        {
            if(pattern[i]==0)
                return false;
        }
        return true;
    }

    void InitialseButtons()
    {
        btn0.setText("");
        btn0.setBackgroundTintList(ColorStateList.valueOf(Color.argb(15, 255, 255, 255)));
        btn1.setText("");
        btn1.setBackgroundTintList(ColorStateList.valueOf(Color.argb(15, 255, 255, 255)));
        btn2.setText("");
        btn2.setBackgroundTintList(ColorStateList.valueOf(Color.argb(15, 255, 255, 255)));
        btn3.setText("");
        btn3.setBackgroundTintList(ColorStateList.valueOf(Color.argb(15, 255, 255, 255)));
        btn4.setText("");
        btn4.setBackgroundTintList(ColorStateList.valueOf(Color.argb(15, 255, 255, 255)));
        btn5.setText("");
        btn5.setBackgroundTintList(ColorStateList.valueOf(Color.argb(15, 255, 255, 255)));
        btn6.setText("");
        btn6.setBackgroundTintList(ColorStateList.valueOf(Color.argb(15, 255, 255, 255)));
        btn7.setText("");
        btn7.setBackgroundTintList(ColorStateList.valueOf(Color.argb(15, 255, 255, 255)));
        btn8.setText("");
        btn8.setBackgroundTintList(ColorStateList.valueOf(Color.argb(15, 255, 255, 255)));

        RoundIndicator.setText("ROUND-"+String.valueOf(CurrentRound));
    }

};