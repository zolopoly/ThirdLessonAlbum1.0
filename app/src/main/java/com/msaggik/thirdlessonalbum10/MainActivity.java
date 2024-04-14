package com.msaggik.thirdlessonalbum10;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioGroup;
import android.widget.Switch;

public class MainActivity extends AppCompatActivity {

    // поля
    private ImageView buttonTools;
    private LinearLayout buttons;
    private boolean buttonsCheck = false; // поле включения кнопок
    private ImageView buttonLine, buttonCircle, buttonDraw, buttonPalette, buttonClearScreen;
    private AlbumView album; // создание объекта представления View
    private Bitmap bitmapPalette;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // привязка кнопок к разметке
        buttonTools = findViewById(R.id.button_tools);
        buttonLine = findViewById(R.id.button_line);
        buttonCircle = findViewById(R.id.button_circle);
        buttonDraw = findViewById(R.id.button_draw);
        buttons = findViewById(R.id.buttons);
        buttonPalette = findViewById(R.id.button_palette);
        buttonClearScreen = findViewById(R.id.button_clear_screen);
        album = findViewById(R.id.album); // создание объекта представления View

        // обработка нажатия кнопок
        buttonTools.setOnClickListener(listener);
        buttonLine.setOnClickListener(listener);
        buttonCircle.setOnClickListener(listener);
        buttonDraw.setOnClickListener(listener);
        buttonPalette.setOnClickListener(listener);
        buttonClearScreen.setOnClickListener(listener);
    }

    // слушатель для кнопок
    private View.OnClickListener listener = new View.OnClickListener() {
        @SuppressLint("ClickableViewAccessibility")
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
                case R.id.button_draw:
                    album.setFigure("drawing");
                    manageButtonToolsPannel();
                    break;
                case R.id.button_palette:
                    Dialog dialogAlbumPalette = new Dialog(MainActivity.this, R.style.DialogPalette);
                    dialogAlbumPalette.setTitle(R.string.palette_title);
                    dialogAlbumPalette.setContentView(R.layout.album_palette);

                    // выбор цвета и вывод его во View с id = colorInfoView
                    ImageView colorSelection = dialogAlbumPalette.findViewById(R.id.colorSelection); // создание поля картинки палитры и привязка к разметке по id
                    View colorInfoView = dialogAlbumPalette.findViewById(R.id.colorInfoView); // создание поля View для выбранного цвета и привязка к разметке по id

                    colorSelection.setDrawingCacheEnabled(true); // включение задания кэша картинки
                    colorSelection.buildDrawingCache(true); // включение постройки кэша картинки

                    // задание слушателя обработки касания картинки
                    colorSelection.setOnTouchListener((view1, motionEvent) -> {
                        // если пользователь коснулся экрана или поводил по нему пальцем, то
                        if (motionEvent.getAction() == MotionEvent.ACTION_DOWN || motionEvent.getAction() == MotionEvent.ACTION_MOVE) {

                            bitmapPalette = colorSelection.getDrawingCache(); // запись пикселя картинки в растровое изображение
                            int pixel = bitmapPalette.getPixel((int) motionEvent.getX(), (int) motionEvent.getY()); // определение цвета выбранного пикселя
                            // считывание отдельных цветовых параметров пикселя
                            int a = Color.alpha(pixel);
                            int r = Color.red(pixel);
                            int g = Color.green(pixel);
                            int b = Color.blue(pixel);

                            // задание цвета фона для View выбора цвета
                            colorInfoView.setBackgroundColor(Color.argb(a, r, g, b));

                            // задание нового цвета для кисти
                            album.setColor(pixel);
                        }
                        return true;
                    });

                    // обработка сохранения внесённых изменений
                    Button btnYes = dialogAlbumPalette.findViewById(R.id.btnYes);
                    Button btnNo = dialogAlbumPalette.findViewById(R.id.btnNo);
                    btnYes.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View viewYes) {
                            album.invalidate(); // обновление View
                            dialogAlbumPalette.dismiss(); // закрытие диалога
                        }
                    });
                    btnNo.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View viewNo) {
                            album.invalidate(); // обновление View
                            dialogAlbumPalette.cancel(); // выход из диалога
                        }
                    });

                    dialogAlbumPalette.show(); // показ диалогового окна
                    manageButtonToolsPannel();
                    break;
                case R.id.button_clear_screen:
                    // код для очистки View
                    AlertDialog.Builder broomDialog = new AlertDialog.Builder(MainActivity.this); // создание диалогового окна типа AlertDialog
                    broomDialog.setTitle(R.string.clear_canvas_title); // заголовок диалогового окна
                    broomDialog.setMessage(R.string.clear_canvas_message); // сообщение диалога

                    broomDialog.setPositiveButton(R.string.common_yes, new DialogInterface.OnClickListener() { // пункт выбора "да"
                        public void onClick(DialogInterface dialog, int which) {
                            album.clear(); // метод очистки кастомизированного View
                            dialog.dismiss(); // закрыть диалог
                        }
                    });
                    broomDialog.setNegativeButton(R.string.common_cancel, new DialogInterface.OnClickListener() {  // пункт выбора "нет"
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