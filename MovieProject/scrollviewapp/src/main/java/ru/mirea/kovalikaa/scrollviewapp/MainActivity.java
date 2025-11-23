package ru.mirea.kovalikaa.scrollviewapp;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        LinearLayout linearLayout = findViewById(R.id.linearLayoutContainer);

        LayoutInflater inflater = getLayoutInflater();

        for (int i = 0; i < 100; i++) {
            View item = inflater.inflate(R.layout.item, linearLayout, false);

            TextView textView = item.findViewById(R.id.textView);

            long value = (long) Math.pow(2, i);

            textView.setText((i + 1) + ". " + value);

            linearLayout.addView(item);
        }
    }
}