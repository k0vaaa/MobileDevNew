package ru.mirea.kovalikaa.pocketdictionary.presentation;

import android.os.Bundle;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.appbar.MaterialToolbar;

import ru.mirea.kovalikaa.pocketdictionary.R;
import ru.mirea.kovalikaa.pocketdictionary.presentation.adapter.FavoritesAdapter;

public class FavoritesActivity extends AppCompatActivity {

    private FavoritesViewModel viewModel;
    private FavoritesAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_favorites);
        MaterialToolbar toolbar = findViewById(R.id.toolbarFavorites);
        setSupportActionBar(toolbar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }

        viewModel = new ViewModelProvider(this, new FavoritesViewModelFactory(this))
                .get(FavoritesViewModel.class);

        RecyclerView recyclerView = findViewById(R.id.favoritesRecyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        adapter = new FavoritesAdapter();
        recyclerView.setAdapter(adapter);

        adapter.setOnDeleteClickListener(word -> {
            viewModel.deleteWord(word);
        });

        viewModel.favoritesList.observe(this, favorites -> {
            adapter.setFavorites(favorites);
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        viewModel.loadFavorites();
    }

    @Override
    public boolean onOptionsItemSelected(android.view.MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}