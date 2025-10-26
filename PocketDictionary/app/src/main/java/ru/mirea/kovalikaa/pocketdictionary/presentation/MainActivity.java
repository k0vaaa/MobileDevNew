package ru.mirea.kovalikaa.pocketdictionary.presentation;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.room.Room;

import model.WordDefinition;
import repository.WordRepository;
import ru.mirea.kovalikaa.pocketdictionary.R;
import ru.mirea.kovalikaa.pocketdictionary.data.network.NetworkApi;
import ru.mirea.kovalikaa.pocketdictionary.data.repository.WordRepositoryImpl;
import ru.mirea.kovalikaa.pocketdictionary.data.storage.SharedPrefWordStorage;
import ru.mirea.kovalikaa.pocketdictionary.data.storage.WordStorage;
import ru.mirea.kovalikaa.pocketdictionary.data.storage.room.AppDatabase;
import ru.mirea.kovalikaa.pocketdictionary.data.storage.room.WordDao;
import usecases.GetWordDefinitionUseCase;
import usecases.SaveFavoriteWordUseCase;


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


        NetworkApi networkApi = new NetworkApi();

        AppDatabase database = Room.databaseBuilder(getApplicationContext(), AppDatabase.class, "dictionary-db")
                .allowMainThreadQueries()
                .build();
        WordDao wordDao = database.wordDao();

        WordStorage sharedPrefStorage = new SharedPrefWordStorage(this);

        WordRepository wordRepository = new WordRepositoryImpl(sharedPrefStorage, wordDao, networkApi);

        getWordDefinitionUseCase = new GetWordDefinitionUseCase(wordRepository);
        saveFavoriteWordUseCase = new SaveFavoriteWordUseCase(wordRepository);


        editTextWord = findViewById(R.id.editTextWord);
        textViewResult = findViewById(R.id.textViewResult);
        Button buttonSearch = findViewById(R.id.buttonSearch);
        Button buttonSave = findViewById(R.id.buttonSave);

        buttonSearch.setOnClickListener(v -> {
            String word = editTextWord.getText().toString();
            if (word.isEmpty()) {
                Toast.makeText(this, "Please enter a word", Toast.LENGTH_SHORT).show();
                return;
            }
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