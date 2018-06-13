package com.example.nuris.belajarapp;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AbsoluteLayout;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Collections;

/**
 * Created by nuris on 13/04/2018.
 */

public class GamesHill extends Activity {
    Button jbKembali;
    private TextView moveCounter;
    private TextView feedbackText;
    private Button[] buttons;
    private Boolean bad_move = false;
    //goalstate untuk gambar
    private static final Integer[] goal = new Integer[]{ 1, 2, 3, 4, 5, 6, 7, 8, 0 };
    final ArrayList<Integer> cells = new ArrayList<Integer>();
    private Button mButton;
    //goalstate untuk hillclimbing
    public static int goalState[] = { 1, 2, 3, 4, 5, 6, 7, 8, 0 };
    public static int resetValue = 0;
    public static int tempHeuristic[] = { resetValue, resetValue, resetValue, resetValue };
    public static int totalSolution = 0;
    private static Context mcontext;
    static GamesHill app;
    static String outputproses="";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layoutgames);
        mButton = (Button)findViewById(R.id.btnhill);

        mButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent msg =  getIntent();
                String n1 = msg.getStringExtra("n1");
                String n2 = msg.getStringExtra("n2");
                String n3 = msg.getStringExtra("n3");
                String n4 = msg.getStringExtra("n4");
                String n5 = msg.getStringExtra("n5");
                String n6 = msg.getStringExtra("n6");
                String n7 = msg.getStringExtra("n7");
                String n8 = msg.getStringExtra("n8");
                String n9 = msg.getStringExtra("n9");
                int n1angka = Integer.parseInt(n1);
                int n2angka = Integer.parseInt(n2);
                int n3angka = Integer.parseInt(n3);
                int n4angka = Integer.parseInt(n4);
                int n5angka = Integer.parseInt(n5);
                int n6angka = Integer.parseInt(n6);
                int n7angka = Integer.parseInt(n7);
                int n8angka = Integer.parseInt(n8);
                int n9angka = Integer.parseInt(n9);
                int currentState[] = { 0, 0, 0, 0, 0, 0, 0, 0, 0 };
                int initialState[] = { n1angka, n2angka, n3angka, n4angka,n5angka, n6angka, n7angka, n8angka, n9angka };
                copyArray(initialState, currentState);
                int childState[] = { 0, 0, 0, 0, 0, 0, 0, 0, 0 };
                // ketika proses masih belum goal maka proses berjalan
                while (hasSameElement(currentState, goalState) == 0) {
                    printState(currentState,GamesHill.this);
                    copyArray(currentState, childState);
                    moveUp(childState);
                    if (hasSameElement(childState, currentState) == 1) {
                        tempHeuristic[0] = 0;
                    } else {
                        tempHeuristic[0] = heuristicValue(childState, goalState);
                        moveDown(childState);
                    }
                    moveLeft(childState);
                    if (hasSameElement(childState, currentState) == 1) {
                        tempHeuristic[1] = 0;
                    } else {
                        tempHeuristic[1] = heuristicValue(childState, goalState);
                        moveRight(childState);
                    }
                    moveRight(childState);
                    if (hasSameElement(childState, currentState) == 1) {
                        tempHeuristic[2] = 0;
                    } else {
                        tempHeuristic[2] = heuristicValue(childState, goalState);
                        moveLeft(childState);
                    }
                    moveDown(childState);
                    if (hasSameElement(childState, currentState) == 1) {
                        tempHeuristic[3] = 0;
                    } else {
                        tempHeuristic[3] = heuristicValue(childState, goalState);
                        moveUp(childState);
                    }
                    int tempIndex = getTheBestIndex(tempHeuristic);
                    switch (tempIndex) {
                        case 0:
                            moveUp(currentState);
                            break;
                        case 1:
                            moveLeft(currentState);
                            break;
                        case 2:
                            moveRight(currentState);
                            break;
                        case 3:
                            moveDown(currentState);
                            break;
                    }
                    totalSolution++;
                }
                printState(currentState,GamesHill.this);
                AlertDialog.Builder alertDialogBuilderUserInput = new AlertDialog.Builder(GamesHill.this);
                alertDialogBuilderUserInput
                        .setMessage("\nTotal Langkah Penyelesaian Hill Climbing = " + totalSolution + " langkah")
                        .setCancelable(false)
                        .setPositiveButton("Iya", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialogBox, int id) {
                                // ToDo get user input here
                            }
                        })

                        .setNegativeButton("Tidak",
                                new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialogBox, int id) {
                                        dialogBox.cancel();
                                    }
                                });

                AlertDialog alertDialogAndroid = alertDialogBuilderUserInput.create();
                alertDialogAndroid.show();
            }
        });
        buttons = findButtons();

        //initial untuk gambar pengacakan
        Intent msg =  getIntent();
        String n1 = msg.getStringExtra("n1");
        String n2 = msg.getStringExtra("n2");
        String n3 = msg.getStringExtra("n3");
        String n4 = msg.getStringExtra("n4");
        String n5 = msg.getStringExtra("n5");
        String n6 = msg.getStringExtra("n6");
        String n7 = msg.getStringExtra("n7");
        String n8 = msg.getStringExtra("n8");
        String n9 = msg.getStringExtra("n9");


        this.cells.add(Integer.parseInt(n1));
        this.cells.add(Integer.parseInt(n2));
        this.cells.add(Integer.parseInt(n3));
        this.cells.add(Integer.parseInt(n4));
        this.cells.add(Integer.parseInt(n5));
        this.cells.add(Integer.parseInt(n6));
        this.cells.add(Integer.parseInt(n7));
        this.cells.add(Integer.parseInt(n8));
        this.cells.add(Integer.parseInt(n9));


        Collections.addAll(this.cells);
        fill_grid();

        moveCounter = (TextView)findViewById(R.id.MoveCounter);
        feedbackText = (TextView)findViewById(R.id.FeedbackText);

        //pergeseran button matrix puzzle
        for (int i = 1; i < 9; i++) {
            buttons[i].setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    makeMove((Button) view);
                }
            });
        }
        jbKembali = (Button) findViewById(R.id.btkembali);
        jbKembali.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent startMain = new Intent(Intent.ACTION_MAIN);
                startMain.addCategory(Intent.CATEGORY_HOME);
                startMain.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(startMain);
                finish();
            }
        });
        moveCounter.setText("0");
        feedbackText.setText("mulai");

    }
    private static int checkIndex(int state[]){
        int index = 0;
        for (int i = 0; i < 9; i++) {
            if (state[i] == 0) {
                index = i;
            }
        }
        return index;
    }
    public static void moveLeft(int state[]) {
        int index0 = checkIndex(state);
        if (index0 % 3 > 0) {
            int temp = state[index0];
            state[index0] = state[index0-1];
            state[index0-1] = temp;
        }
    }
    public static void moveRight(int state[]) {
        int index0 = checkIndex(state);
        if (index0 % 3 < 2) {
            int temp = state[index0];
            state[index0] = state[index0+1];
            state[index0+1] = temp;
        }
    }
    public static void moveUp(int state[]) {
        int index0 = checkIndex(state);
        if (index0 > 2) {
            int temp = state[index0];
            state[index0] = state[index0-3];
            state[index0-3] = temp;
        }
    }
    public static void moveDown(int state[]) {
        int index0 = checkIndex(state);
        if (index0 < 6) {
            int temp = state[index0];
            state[index0] = state[index0+3];
            state[index0+3] = temp;
        }
    }
    public static int hasSameElement(int array1[], int array2[]) {
        int temp = 1;
        for (int i = 0; i < 9; i++) {
            if (array1[i] != array2[i]) {
                temp = 0;
            }
        }
        return temp;
    }
    public static int sideHeuristic(int side, int currentState[], int goalState[]) {
        int temp = 0;
        int match = 0;
        switch (side) {
            case 1:
                match = 0;
                for (int i = 0; i < 2; i++) {
                    if (currentState[i] == goalState[i] && currentState[i] != 0) {
                        match++;
                    }
                }
                if (match == 1) {
                    temp = temp + 1;
                } else if (match == 2) {
                    temp = temp + 5;
                } else if (match == 3) {
                    temp = temp + 40;
                }
                break;
            case 2:
                match = 0;
                for (int i = 2; i < 9; i=i+3) {
                    if (currentState[i] == goalState[i] && currentState[i] != 0) {
                        match++;
                    }
                }
                if (match == 1) {
                    temp = temp + 1;
                } else if (match == 2) {
                    temp = temp + 5;
                } else if (match == 3) {
                    temp = temp + 40;
                }
                break;
            case 3:
                match = 0;
                for (int i = 6; i < 9; i++) {
                    if (currentState[i] == goalState[i] && currentState[i] != 0) {
                        match++;
                    }
                }
                if (match == 1) {
                    temp = temp + 1;
                } else if (match == 2) {
                    temp = temp + 5;
                } else if (match == 3) {
                    temp = temp + 40;
                }
                break;
            case 4:
                match = 0;
                for (int i = 0; i < 7; i=i+3) {
                    if (currentState[i] == goalState[i] && currentState[i] != 0) {
                        match++;
                    }
                }
                if (match == 1) {
                    temp = temp + 1;
                } else if (match == 2) {
                    temp = temp + 5;
                } else if (match == 3) {
                    temp = temp + 40;
                }
                break;
            case 5:
                match = 0;
                for (int i = 1; i < 8; i=i+3) {
                    if (currentState[i] == goalState[i]) {
                        match++;
                    }
                }
                if (match == 1) {
                    temp = temp + 1;
                } else if (match == 2) {
                    temp = temp + 5;
                } else if (match == 3) {
                    temp = temp + 5;
                }
                break;
            case 6:
                match = 0;
                for (int i = 3; i < 6; i++) {
                    if (currentState[i] == goalState[i]) {
                        match++;
                    }
                }
                if (match == 1) {
                    temp = temp + 1;
                } else if (match == 2) {
                    temp = temp + 5;
                } else if (match == 3) {
                    temp = temp + 5;
                }
                break;
        }
        return temp;
    }
    public static int getCol(int val) {
        return ((val/3)+1);
    }
    public static int getRow(int val) {
        return ((val%3)+1);
    }
    public static int stepCounter(int condition1, int condition2) {
        int temp1 = getCol(condition1)-getCol(condition2);
        if (temp1 < 0) {
            temp1 = (temp1 * -1) * 2;
        }
        int temp2 = getRow(condition1)-getRow(condition2);
        if (temp2 < 0) {
            temp2 = (temp2 * -1) * 2;
        }
        return (((temp1 + temp2) * -1) + 4);
    }
    public static int manhattanHeuristic(int currentState[], int goalState[]) {
        int temp = 0;
        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 9; j++) {
                if (currentState[i] != 0) {
                    if (currentState[i] == goalState[j]) {
                        temp = temp + stepCounter(i, j);
                    }
                }
            }
        }
        return temp;
    }
    public static int heuristicValue(int currentState[], int goalState[]) {
        return sideHeuristic(1, currentState, goalState) +
                sideHeuristic(2, currentState, goalState) + sideHeuristic(3, currentState, goalState) +
                sideHeuristic(4, currentState, goalState) + sideHeuristic(5, currentState, goalState) +
                sideHeuristic(6, currentState, goalState) + manhattanHeuristic(currentState, goalState);
    }
    public static void copyArray(int array1[], int array2[]) {
        for (int i = 0; i < 9; i++) {
            array2[i] = array1[i];
        }
    }
    public static int getTheBestIndex(int arrayHeuristic[]) {
        int temp = 0;
        int tempValue = arrayHeuristic[0];
        for (int i = 0; i < 4; i++) {
            if (arrayHeuristic[i] > tempValue) {
                tempValue = arrayHeuristic[i];
                temp = i;
            }
        }
        return temp;
    }
    public static void printState(int state[],Activity a) {
        for (int i = 0; i < 9; i++) {

            if (i % 3 == 0) {
                outputproses += "\n";
            }
            outputproses += String.valueOf(state[i]);
            outputproses += " ";
        }
        outputproses += "\n";
        AlertDialog.Builder peringatan = new AlertDialog.Builder(a);
        peringatan.setTitle("Proses Hill Climbing");
        peringatan.setMessage(outputproses);
        peringatan.setNeutralButton("Ok",null);
        peringatan.show();
    }
    public Button[] findButtons(){
        Button[] b = new Button[9];
        b[0] = (Button)findViewById(R.id.Button00);
        b[1] = (Button)findViewById(R.id.Button01);
        b[2] = (Button)findViewById(R.id.Button02);
        b[3] = (Button)findViewById(R.id.Button03);
        b[4] = (Button)findViewById(R.id.Button04);
        b[5] = (Button)findViewById(R.id.Button05);
        b[6] = (Button)findViewById(R.id.Button06);
        b[7] = (Button)findViewById(R.id.Button07);
        b[8] = (Button)findViewById(R.id.Button08);
        return b;
    }
    public void makeMove(final Button b){
        bad_move = true;
        int b_text,b_pos,zuk_pos;
        b_text = Integer.parseInt((String)b.getText());
        b_pos = find_pos(b_text);
        zuk_pos = find_pos(0);
        switch (zuk_pos){
            case (0):
                if (b_pos == 1 || b_pos == 3)
                    bad_move = false;
                break;
            case (1):
                if (b_pos == 0 || b_pos == 2 || b_pos == 4)
                    bad_move = false;
                break;
            case (2):
                if (b_pos == 1 || b_pos == 5)
                    bad_move = false;
                break;
            case (3):
                if (b_pos == 0 || b_pos == 4 || b_pos == 6)
                    bad_move = false;
                break;
            case (4):
                if (b_pos == 1 || b_pos == 3 || b_pos == 5 || b_pos == 7)
                    bad_move = false;
                break;
            case (5):
                if (b_pos == 2 || b_pos == 4 || b_pos == 8)
                    bad_move = false;
                break;
            case (6):
                if (b_pos == 3 || b_pos == 7)
                    bad_move = false;
                break;
            case (7):
                if (b_pos == 4 || b_pos == 6 || b_pos == 8)
                    bad_move = false;
                break;
            case (8):
                if (b_pos == 5 || b_pos == 7)
                    bad_move = false;
                break;
        }
        if(bad_move == true){
            feedbackText.setText("Tidak!");
            return;
        }
        feedbackText.setText("Ya");
        cells.remove(b_pos);
        cells.add(b_pos,0);
        cells.remove(zuk_pos);
        cells.add(zuk_pos,b_text);

        fill_grid();
        moveCounter.setText(Integer.toString(Integer.parseInt((String)moveCounter.getText()) + 1));
        for (int i = 0; i < 9; i++) {
            if(cells.get(i) != goal[i]){
                return;
            }
        }
        feedbackText.setText("Selesai !");

        AlertDialog.Builder peringatan = new AlertDialog.Builder(GamesHill.this);
        peringatan.setTitle("Selamat");
        peringatan.setMessage("Anda berhasil menyelesaikan permainan");
        peringatan.setNeutralButton("Ok",null);
        peringatan.show();
    }
    public void fill_grid(){
        for (int i = 0; i < 9; i++) {
            int text = cells.get(i);
            AbsoluteLayout.LayoutParams absparms = (AbsoluteLayout.LayoutParams) buttons[text].getLayoutParams();
            switch (i){
                case(0):
                    absparms.x = 5;
                    absparms.y = 5;
                    buttons[text].setLayoutParams(absparms);
                    break;
                case(1):
                    absparms.x = 160;
                    absparms.y = 5;
                    buttons[text].setLayoutParams(absparms);
                    break;
                case(2):
                    absparms.x = 315;
                    absparms.y = 5;
                    buttons[text].setLayoutParams(absparms);
                    break;
                case(3):
                    absparms.x = 5;
                    absparms.y = 160;
                    buttons[text].setLayoutParams(absparms);
                    break;
                case(4):
                    absparms.x = 160;
                    absparms.y = 160;
                    buttons[text].setLayoutParams(absparms);
                    break;
                case(5):
                    absparms.x = 315;
                    absparms.y = 160;
                    buttons[text].setLayoutParams(absparms);
                    break;
                case(6):
                    absparms.x = 5;
                    absparms.y = 315;
                    buttons[text].setLayoutParams(absparms);
                    break;
                case(7):
                    absparms.x = 160;
                    absparms.y = 315;
                    buttons[text].setLayoutParams(absparms);
                    break;
                case(8):
                    absparms.x = 315;
                    absparms.y = 315;
                    buttons[text].setLayoutParams(absparms);
                    break;

            }
        }
    }
    public int find_pos(int element){
        int i = 0;
        for (i = 0; i < 9; i++) {
            if(cells.get(i) == element){
                break;
            }
        }
        return i;
    }
}
