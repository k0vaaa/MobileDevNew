package ru.mirea.kovalikaa.pocketdictionary.presentation;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import ru.mirea.kovalikaa.pocketdictionary.R;

public class SearchFragment extends Fragment {

    private MainViewModel viewModel;
    private EditText editTextWord;
    private TextView textViewResult;
    private ImageView headerImageView;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_search, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        viewModel = new ViewModelProvider(requireActivity(), new MainViewModelFactory(requireContext()))
                .get(MainViewModel.class);

        headerImageView = view.findViewById(R.id.headerImageView);
        editTextWord = view.findViewById(R.id.editTextWord);
        textViewResult = view.findViewById(R.id.textViewResult);
        Button buttonSearch = view.findViewById(R.id.buttonSearch);
        Button buttonSave = view.findViewById(R.id.buttonSave);

        buttonSearch.setOnClickListener(v -> {
            String word = editTextWord.getText().toString();
            viewModel.searchWord(word);
        });

        buttonSave.setOnClickListener(v -> {
            viewModel.saveCurrentWord();
        });

        viewModel.wordDefinition.observe(getViewLifecycleOwner(), definition -> {
            if (definition != null) {
                String resultText = "Word: " + definition.getWord() + "\n\nDefinition: " + definition.getDefinition();
                textViewResult.setText(resultText);

                String imageUrl = definition.getImageUrl();
                if (imageUrl != null && !imageUrl.isEmpty()) {
                    Picasso.get().load(imageUrl).into(headerImageView);
                } else {
                    headerImageView.setImageResource(R.drawable.ic_error);
                }
            }
        });

        viewModel.toastMessage.observe(getViewLifecycleOwner(), message -> {
            if (message != null && !message.isEmpty()) {
                Toast.makeText(getContext(), message, Toast.LENGTH_SHORT).show();
            }
        });
    }
}