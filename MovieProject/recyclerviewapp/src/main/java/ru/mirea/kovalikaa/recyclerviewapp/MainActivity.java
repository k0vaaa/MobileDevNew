package ru.mirea.kovalikaa.recyclerviewapp;

import android.os.Bundle;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.os.Bundle;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private EventAdapter adapter;
    private List<Event> eventList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        recyclerView = findViewById(R.id.recyclerView);


        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        generateEventList();

        adapter = new EventAdapter(eventList);
        recyclerView.setAdapter(adapter);
    }

    private void generateEventList() {
        eventList = new ArrayList<>();
        eventList.add(new Event("Крещение Руси 988 г.", "Процесс принятия христианства киевским князем Владимиром и его народом.", R.drawable.pic1));
        eventList.add(new Event("Отмена крепостного права 1861 г.", "Реформа, упразднившая крепостное право в России.", R.drawable.pic2));
        eventList.add(new Event("Бородинское сражение 1812 г.", "Крупнейшее сражение Отечественной войны 1812 года между русской и французской армиями.", R.drawable.pic3));
        eventList.add(new Event("Куликовская битва 1380 г.", "Крупное средневековое сражение между объединённым русским войском и войском Золотой Орды.", R.drawable.pic4));
        eventList.add(new Event("Ледовое побоище 1242 г.", "Битва, произошедшая на льду Чудского озера.", R.drawable.pic5));
        eventList.add(new Event("Первый полет в космос 1961 г.", "Юрий Гагарин стал первым человеком в мировой истории, совершившим полёт в космическое пространство.", R.drawable.pic6));
        eventList.add(new Event("Авария на ЧАЭС 1986 г.", "Одна из крупнейших техногенных катастроф современности.", R.drawable.pic7));
    }
}