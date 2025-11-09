package ru.mirea.kovalikaa.pocketdictionary.presentation;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import ru.mirea.kovalikaa.pocketdictionary.R;

public class MainActivity extends AppCompatActivity {

    private MainViewModel viewModel;
    private EditText editTextWord;
    private TextView textViewResult;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        viewModel = new ViewModelProvider(this, new MainViewModelFactory(this))
                .get(MainViewModel.class);

        editTextWord = findViewById(R.id.editTextWord);
        textViewResult = findViewById(R.id.textViewResult);
        Button buttonSearch = findViewById(R.id.buttonSearch);
        Button buttonSave = findViewById(R.id.buttonSave);

        buttonSearch.setOnClickListener(v -> {
            String word = editTextWord.getText().toString();
            viewModel.searchWord(word);
        });

        buttonSave.setOnClickListener(v -> {
            viewModel.saveCurrentWord();
        });

        viewModel.wordDefinition.observe(this, definition -> {
            if (definition != null) {
                String resultText = "Word: " + definition.getWord() + "\n\nDefinition: " + definition.getDefinition();
                textViewResult.setText(resultText);
            }
        });

        viewModel.toastMessage.observe(this, message -> {
            if (message != null && !message.isEmpty()) {
                Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
            }
        });
    }
}