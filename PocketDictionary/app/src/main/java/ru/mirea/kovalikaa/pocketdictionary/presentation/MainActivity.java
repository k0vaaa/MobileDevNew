package ru.mirea.kovalikaa.pocketdictionary.presentation;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import ru.mirea.kovalikaa.pocketdictionary.R;
import ru.mirea.kovalikaa.pocketdictionary.data.repository.WordRepositoryImpl;
import ru.mirea.kovalikaa.pocketdictionary.domain.model.WordDefinition;
import ru.mirea.kovalikaa.pocketdictionary.domain.repository.WordRepository;
import ru.mirea.kovalikaa.pocketdictionary.domain.usecases.GetWordDefinitionUseCase;
import ru.mirea.kovalikaa.pocketdictionary.domain.usecases.SaveFavoriteWordUseCase;

public class MainActivity extends AppCompatActivity {

    private GetWordDefinitionUseCase getWordDefinitionUseCase;
    private SaveFavoriteWordUseCase saveFavoriteWordUseCase;

    private EditText editTextWord;
    private TextView textViewResult;
    private WordDefinition currentWord;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        WordRepository wordRepository = new WordRepositoryImpl();
        getWordDefinitionUseCase = new GetWordDefinitionUseCase(wordRepository);
        saveFavoriteWordUseCase = new SaveFavoriteWordUseCase(wordRepository);

        editTextWord = findViewById(R.id.editTextWord);
        textViewResult = findViewById(R.id.textViewResult);
        Button buttonSearch = findViewById(R.id.buttonSearch);
        Button buttonSave = findViewById(R.id.buttonSave);

        buttonSearch.setOnClickListener(v -> {
            String word = editTextWord.getText().toString();
            currentWord = getWordDefinitionUseCase.execute(word);
            String resultText = "Word: " + currentWord.getWord() + "\n\nDefinition: " + currentWord.getDefinition();
            textViewResult.setText(resultText);
        });

        buttonSave.setOnClickListener(v -> {
            if (currentWord != null) {
                boolean success = saveFavoriteWordUseCase.execute(currentWord);
                Toast.makeText(this, "Save successful: " + success, Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(this, "Search for a word first!", Toast.LENGTH_SHORT).show();
            }
        });
    }
}