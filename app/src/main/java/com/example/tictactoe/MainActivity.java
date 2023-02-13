package com.example.tictactoe;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    EditText Player1;
    EditText Player2;
    Button Play;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        String[] values=new String[]{"1","2","3","5","10"};
        Spinner spinner=(Spinner) findViewById(R.id.spinner);

        ArrayList<String> array = new ArrayList<String>();
        array.add("1");
        array.add("2");
        array.add("3");
        array.add("5");
        array.add("10");
        ArrayAdapter<String> mAdapter;
        mAdapter = new ArrayAdapter<String>(this, R.layout.spinner_values_layout, array);
        spinner.setAdapter(mAdapter);

        Player1=(EditText) findViewById(R.id.EDplayer1);
        Player2=(EditText) findViewById(R.id.EDplayer2);
        Play=(Button) findViewById(R.id.button);

        Play.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String Name1=String.valueOf(Player1.getText());
                String Name2=String.valueOf(Player2.getText());
                boolean check=false;
                if(Name1.length()==0)
                {
                    Player1.setError("This field can not be blank");
                    check=true;
                }
                if(Name2.length()==0)
                {
                    Player2.setError("This field can not be blank");
                    check=true;
                }
                if(check)return;

                Intent intent=new Intent(MainActivity.this,PlayTheGame.class);
                intent.putExtra("Player1Name",Name1);
                intent.putExtra("Player2Name",Name2);
                intent.putExtra("Rounds",spinner.getSelectedItem().toString());

                Player1.setText("");
                Player2.setText("");
                startActivity(intent);
            }
        });

    }
}