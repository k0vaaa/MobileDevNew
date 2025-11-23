package ru.mirea.kovalikaa.listviewapp;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private String[] countries = { "Россия", "Бразилия", "Китай", "Индия", "ЮАР", "Иран", "Египет", "ОАЭ"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ListView listView = findViewById(R.id.listView);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(
                this,
                android.R.layout.simple_list_item_2,
                android.R.id.text1,
                countries
        ) {
            @Override
            public View getView(int position, View convertView, ViewGroup parent) {
                View view = super.getView(position, convertView, parent);

                TextView text1 = view.findViewById(android.R.id.text1);
                TextView text2 = view.findViewById(android.R.id.text2);

                text1.setText(String.valueOf(position + 1));

                text2.setText(getItem(position));

                return view;
            }
        };

        listView.setAdapter(adapter);
    }
    }
//    private List<String> generateBookList() {
//        List<String> list = new ArrayList<>();
//        list.add("Фрэнк Герберт - Дюна");
//        list.add("Айзек Азимов - Основание");
//        list.add("Джордж Оруэлл - 1984");
//        list.add("Артур Кларк - Космическая одиссея 2001");
//        list.add("Уильям Гибсон - Нейромант");
//        list.add("Филип Дик - Мечтают ли андроиды об электроовцах?");
//        list.add("Роберт Хайнлайн - Звёздный десант");
//        list.add("Станислав Лем - Солярис");
//        list.add("Братья Стругацкие - Пикник на обочине");
//        list.add("Олдос Хаксли - О дивный новый мир");
//        list.add("Рэй Брэдбери - 451 градус по Фаренгейту");
//        list.add("Дэн Симмонс - Гиперион");
//        list.add("Нил Стивенсон - Лавина");
//        list.add("Курт Воннегут - Бойня номер пять");
//        list.add("Чайна Мьевиль - Вокзал потерянных снов");
//        list.add("Джон Уиндэм - День триффидов");
//        list.add("Сергей Лукьяненко - Ночной Дозор");
//        list.add("Виктор Пелевин - Чапаев и Пустота");
//        list.add("Евгений Замятин - Мы");
//        list.add("Андрей Платонов - Котлован");
//        list.add("Владимир Сорокин - Голубое сало");
//        list.add("Михаил Булгаков - Мастер и Маргарита");
//        list.add("Габриэль Гарсиа Маркес - Сто лет одиночества");
//        list.add("Умберто Эко - Имя розы");
//        list.add("Харпер Ли - Убить пересмешника");
//        list.add("Джером Сэлинджер - Над пропастью во ржи");
//        list.add("Кен Кизи - Пролетая над гнездом кукушки");
//        list.add("Чак Паланик - Бойцовский клуб");
//        list.add("Ирвин Уэлш - На игле");
//        list.add("Хантер Томпсон - Страх и ненависть в Лас-Вегасе");
//        list.add("Терри Пратчетт - Цвет волшебства");
//        return list;
//    }
