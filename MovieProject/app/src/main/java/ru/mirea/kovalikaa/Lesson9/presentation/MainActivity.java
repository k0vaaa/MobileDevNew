package ru.mirea.kovalikaa.lesson9.presentation;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import ru.mirea.kovalikaa.lesson9.R;
import ru.mirea.kovalikaa.lesson9.domain.models.Movie;

public class MainActivity extends AppCompatActivity {

    private TextView textView;
    private EditText editText;
    private MainViewModel mainViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mainViewModel = new ViewModelProvider(this, new ViewModelFactory(this))
                .get(MainViewModel.class);

        textView = findViewById(R.id.textViewMovie);
        editText = findViewById(R.id.editTextMovie);
        Button buttonGet = findViewById(R.id.buttonGetMovie);
        Button buttonSave = findViewById(R.id.buttonSaveMovie);

        mainViewModel.getFavoriteMovie().observe(this, s -> {
            textView.setText(s);
        });

        buttonSave.setOnClickListener(view -> {
            String movieName = editText.getText().toString();
            mainViewModel.setText(new Movie(2, movieName));
        });

        buttonGet.setOnClickListener(view -> {
            mainViewModel.getText();
        });
    }
}