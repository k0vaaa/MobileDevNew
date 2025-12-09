package ru.mirea.kovalikaa.pocketdictionary.presentation;


import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.lifecycle.ViewModelProvider;

import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;

import ru.mirea.kovalikaa.pocketdictionary.R;

public class MainActivity extends AppCompatActivity {

    private MainViewModel viewModel;
    private EditText editTextWord;
    private TextView textViewResult;
    private ImageView headerImageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar toolbar = findViewById(R.id.toolbarMain);
        setSupportActionBar(toolbar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setHomeAsUpIndicator(R.drawable.exit);
        }

        Picasso.get().setLoggingEnabled(true);

        viewModel = new ViewModelProvider(this, new MainViewModelFactory(this))
                .get(MainViewModel.class);

        headerImageView = findViewById(R.id.headerImageView);
        editTextWord = findViewById(R.id.editTextWord);
        textViewResult = findViewById(R.id.textViewResult);
        Button buttonSearch = findViewById(R.id.buttonSearch);
        Button buttonSave = findViewById(R.id.buttonSave);
        Button buttonGoToFavorites = findViewById(R.id.buttonGoToFavorites);

        buttonSearch.setOnClickListener(v -> {
            String word = editTextWord.getText().toString();
            viewModel.searchWord(word);
        });

        buttonSave.setOnClickListener(v -> {
            viewModel.saveCurrentWord();
        });

        buttonGoToFavorites.setOnClickListener(v -> {
            startActivity(new Intent(MainActivity.this, FavoritesActivity.class));
        });

        viewModel.wordDefinition.observe(this, definition -> {
            if (definition != null) {
                String resultText = "Word: " + definition.getWord() + "\n\nDefinition: " + definition.getDefinition();
                textViewResult.setText(resultText);

                String imageUrl = definition.getImageUrl();
                if (imageUrl != null && !imageUrl.isEmpty()) {
                    Log.d("Picasso_Debug", "Attempting to load URL: " + imageUrl);
                    Picasso.get()
                            .load(imageUrl)
                            .placeholder(R.drawable.ic_launcher_background)
                            .error(R.drawable.ic_error)
                            .into(headerImageView, new Callback() {
                                @Override
                                public void onSuccess() {
                                    Log.d("Picasso_Debug", "Image loaded successfully!");
                                }

                                @Override
                                public void onError(Exception e) {
                                    Log.e("Picasso_Debug", "Error loading image", e);
                                }
                            });
                } else {
                    Log.d("Picasso_Debug", "Image URL is null or empty. Setting error image.");
                    headerImageView.setImageResource(R.drawable.ic_error);
                }
            }
        });

        viewModel.toastMessage.observe(this, message -> {
            if (message != null && !message.isEmpty()) {
                Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            viewModel.logout();
            Intent intent = new Intent(MainActivity.this, AuthActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(intent);
            finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}