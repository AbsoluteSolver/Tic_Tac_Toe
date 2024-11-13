package com.example.tictactoe;

import android.content.res.ColorStateList;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {
    private ImageButton btn1, btn2, btn3, btn4, btn5, btn6, btn7, btn8, btn9;
    private TableLayout TL;
    private boolean cross_turn;
    private TextView winView, currentTurn;
    private FrameLayout popup_layout;
    private Button restart;

   int[] statements = new int[]{-1,2,3,4,5,6,7,8,9};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        View decorView = getWindow().getDecorView();
        decorView.setSystemUiVisibility(
                View.SYSTEM_UI_FLAG_IMMERSIVE |
                View.SYSTEM_UI_FLAG_HIDE_NAVIGATION |
                View.SYSTEM_UI_FLAG_FULLSCREEN |
                View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN);


        cross_turn = true;
        btn1 = findViewById(R.id.imageButton1);
        btn2 = findViewById(R.id.imageButton2);
        btn3 = findViewById(R.id.imageButton3);
        btn4 = findViewById(R.id.imageButton4);
        btn5 = findViewById(R.id.imageButton5);
        btn6 = findViewById(R.id.imageButton6);
        btn7 = findViewById(R.id.imageButton7);
        btn8 = findViewById(R.id.imageButton8);
        btn9 = findViewById(R.id.imageButton9);
        TL = findViewById(R.id.mainLayout);
        winView = findViewById(R.id.textView);
        popup_layout = findViewById(R.id.popup_layout);
        restart = findViewById(R.id.restart);
        currentTurn = findViewById(R.id.currentTurn);

        for (int i = 0; i < TL.getChildCount(); i++) {
            TableRow TR = (TableRow) TL.getChildAt(i);

            for (int j = 0; j < TR.getChildCount(); j++) {
                int fI = i;
                int fJ = j;

                TR.getChildAt(j).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        int index = fJ + (fI * 3);
                        if (cross_turn) {
                            TR.getChildAt(fJ).setBackgroundResource(R.drawable.cross);
                            TR.getChildAt(fJ).setEnabled(false);
                            statements[index] = 1;

                            currentTurn.setText("Текущий ход: O");
                        }
                        else {
                            TR.getChildAt(fJ).setBackgroundResource(R.drawable.circle);
                            TR.getChildAt(fJ).setEnabled(false);
                            statements[index] = 0;
                            currentTurn.setText("Текущий ход: Х");
                        }

                        savedInstanceState.putIntArray("stArray", statements);

                        checkIfWin();
                        cross_turn = !cross_turn;
                    }
                });

            }
        }

        if(savedInstanceState != null){
            statements = savedInstanceState.getIntArray("stArray");

            int index = 0;

            for (int i = 0; i < TL.getChildCount(); i++) {
                TableRow TR = (TableRow) TL.getChildAt(i);

                for (int j = 0; j < TR.getChildCount(); j++) {
                    index = i + (j * 3);
                    if(statements[index] == 0)
                        TR.getChildAt(i + (j * 3)).setBackgroundResource(R.drawable.circle);
                    else if (statements[index] == 1)
                        TR.getChildAt(i + (j * 3)).setBackgroundResource(R.drawable.cross);

                    TR.getChildAt(index).setEnabled(false);
                }
            }
        }

        restart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                for (int i = 0; i < TL.getChildCount(); i++) {
                    TableRow TR = (TableRow) TL.getChildAt(i);

                    for (int j = 0; j < TR.getChildCount(); j++) {
                        int fJ = j;
                        TR.getChildAt(fJ).setBackgroundResource(R.drawable.imagebtnxml);
                        TR.getChildAt(fJ).setEnabled(true);
                        statements = new int[]{-1,2,3,4,5,6,7,8,9};
                    }
                }

                TL.setVisibility(View.VISIBLE);
                popup_layout.setVisibility(View.GONE);
                currentTurn.setVisibility(View.VISIBLE);
            }
        });


    }

    @Override
    protected void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putIntArray("stArray", statements);
    }

    void checkIfWin() {
        if (statements[0] == statements[1] && statements[1] == statements[2] ||     //1 горизонталь
                statements[0] == statements[4] && statements[4] == statements[8] || // левая диагональ
                statements[0] == statements[3] && statements[3] == statements[6] || // 1 вертикаль
                statements[1] == statements[4] && statements[4] == statements[7] || // 2 вертикаль
                statements[2] == statements[5] && statements[5] == statements[8] || // 3 вертикаль
                statements[2] == statements[4] && statements[4] == statements[6] || // правая диагональ
                statements[3] == statements[4] && statements[4] == statements[5] || // 2 горизонталь
                statements[6] == statements[7] && statements[7] == statements[8]) { // 3 горизонталь

            TL.setVisibility(View.GONE);
            popup_layout.setVisibility(View.VISIBLE);
            currentTurn.setVisibility(View.GONE);
            winView.setText(cross_turn ? " X ПОБЕДИЛ! " : " O ПОБЕДИЛ! ");
        }
        else{
            for (int i = 0; i < TL.getChildCount(); i++) {
                TableRow TR = (TableRow) TL.getChildAt(i);

                for (int j = 0; j < TR.getChildCount(); j++) {
                    int fJ = j;
                    if(TR.getChildAt(fJ).isEnabled()){
                        return;
                    }
                }
            }

            TL.setVisibility(View.GONE);
            popup_layout.setVisibility(View.VISIBLE);
            currentTurn.setVisibility(View.GONE);
            winView.setText(" Ничья! ");
        }
    }

}