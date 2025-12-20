package ru.mirea.kovalikaa.resultapifragmentapp;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class DataFragment extends Fragment {
    private TextView textViewResult;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getParentFragmentManager().setFragmentResultListener("requestKey", this, (requestKey, bundle) -> {
            String result = bundle.getString("bundleKey");
            textViewResult.setText("Полученный результат: " + result);
        });
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_data, container, false);
        textViewResult = view.findViewById(R.id.textViewResult);
        Button openButton = view.findViewById(R.id.buttonOpenBottomSheet);

            openButton.setOnClickListener(v -> {
                new BottomSheetFragment().show(getParentFragmentManager(), "BottomSheetFragment");
            });

        return view;
    }
}