package com.msaggik.thirdlessonalbum10;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;

public class MainActivity extends AppCompatActivity {

    // поля
    private ImageView buttonTools;
    private LinearLayout buttons;
    private boolean buttonsCheck = false; // поле включения кнопок
    private ImageView buttonLine, buttonCircle, buttonPalette, buttonClearScreen;
    private AlbumView album; // создание объекта представления View

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // привязка кнопок к разметке
        buttonTools = findViewById(R.id.button_tools);
        buttonLine = findViewById(R.id.button_line);
        buttonCircle = findViewById(R.id.button_circle);
        buttons = findViewById(R.id.buttons);
        buttonPalette = findViewById(R.id.button_palette);
        buttonClearScreen = findViewById(R.id.button_clear_screen);
        album = findViewById(R.id.album); // создание объекта представления View

        // обработка нажатия кнопок
        buttonTools.setOnClickListener(listener);
        buttonLine.setOnClickListener(listener);
        buttonCircle.setOnClickListener(listener);
        buttonPalette.setOnClickListener(listener);
        buttonClearScreen.setOnClickListener(listener);
    }

    // слушатель для кнопок
    private View.OnClickListener listener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {

            switch (view.getId()) {
                case R.id.button_tools:
                    manageButtonToolsPannel();
                    break;
                case R.id.button_line:
                    album.setFigure("line");
                    manageButtonToolsPannel();
                    break;
                case R.id.button_circle:
                    album.setFigure("circle");
                    manageButtonToolsPannel();
                    break;
                case R.id.button_palette:
                    album.setFigure("drawing");
                    manageButtonToolsPannel();
                    break;
                case R.id.button_clear_screen:
                    // код для очистки View
                    AlertDialog.Builder broomDialog = new AlertDialog.Builder(MainActivity.this); // создание диалогового окна типа AlertDialog
                    broomDialog.setTitle("Очистка рисунка"); // заголовок диалогового окна
                    broomDialog.setMessage("Очистить область рисования (имеющийся рисунок будет удалён)?"); // сообщение диалога

                    broomDialog.setPositiveButton("Да", new DialogInterface.OnClickListener() { // пункт выбора "да"
                        public void onClick(DialogInterface dialog, int which) {
                            album.clear(); // метод очистки кастомизированного View
                            dialog.dismiss(); // закрыть диалог
                        }
                    });
                    broomDialog.setNegativeButton("Отмена", new DialogInterface.OnClickListener() {  // пункт выбора "нет"
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.cancel(); // выход из диалога
                        }
                    });
                    broomDialog.show(); // отображение на экране данного диалога
                    manageButtonToolsPannel();
                    break;
            }
        }
    };

    private void manageButtonToolsPannel() {
        if (buttonsCheck) {
            buttonsCheck = false;
            buttons.setVisibility(View.INVISIBLE);
        } else {
            buttonsCheck = true;
            buttons.setVisibility(View.VISIBLE);
        }
    }
}