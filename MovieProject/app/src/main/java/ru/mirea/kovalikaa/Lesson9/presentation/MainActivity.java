package ru.mirea.kovalikaa.Lesson9.presentation;

import android.os.Bundle;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import ru.mirea.kovalikaa.Lesson9.R;
import ru.mirea.kovalikaa.Lesson9.data.repository.MovieRepositoryImpl;
import ru.mirea.kovalikaa.Lesson9.domain.models.Movie;
import ru.mirea.kovalikaa.Lesson9.domain.repository.MovieRepository;
import ru.mirea.kovalikaa.Lesson9.domain.usercases.GetFavoriteFilmUseCase;
import ru.mirea.kovalikaa.Lesson9.domain.usercases.SaveFilmToFavoriteUseCase;


public class MainActivity extends AppCompatActivity {
    private TextView textView;
    private EditText editText;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        textView = findViewById(R.id.textViewMovie);
        editText = findViewById(R.id.editTextMovie);

        MovieRepository movieRepository = new MovieRepositoryImpl(this);
        GetFavoriteFilmUseCase getFavoriteFilmUseCase = new GetFavoriteFilmUseCase(movieRepository);
        SaveFilmToFavoriteUseCase saveFilmToFavoriteUseCase = new SaveFilmToFavoriteUseCase(movieRepository);
        findViewById(R.id.buttonSaveMovie).setOnClickListener(view -> {
            String movieName = editText.getText().toString();
            Boolean result = saveFilmToFavoriteUseCase.execute(new Movie(2, movieName));
            textView.setText(String.format("Save result %s", result));
        });
        findViewById(R.id.buttonGetMovie).setOnClickListener(view -> {
            Movie movie = getFavoriteFilmUseCase.execute();
            textView.setText(String.format("Result: %s", movie.getName()));
        });
    }   
}