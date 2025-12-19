package ru.mirea.kovalikaa.pocketdictionary.presentation;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import ru.mirea.kovalikaa.pocketdictionary.R;
import ru.mirea.kovalikaa.pocketdictionary.presentation.adapter.FavoritesAdapter;


public class FavoritesFragment extends Fragment {

    private FavoritesViewModel viewModel;
    private FavoritesAdapter adapter;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_favorites, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        viewModel = new ViewModelProvider(requireActivity(), new FavoritesViewModelFactory(requireContext()))
                .get(FavoritesViewModel.class);

        RecyclerView recyclerView = view.findViewById(R.id.favoritesRecyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        adapter = new FavoritesAdapter();
        recyclerView.setAdapter(adapter);

        adapter.setOnDeleteClickListener(word -> {
            viewModel.deleteWord(word);
        });

        viewModel.favoritesList.observe(getViewLifecycleOwner(), favorites -> {
            adapter.setFavorites(favorites);
        });
    }

    @Override
    public void onResume() {
        super.onResume();
        if (viewModel != null) {
            viewModel.loadFavorites();
        }
    }
}