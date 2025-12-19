package ru.mirea.kovalikaa.resultapifragmentapp;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.google.android.material.bottomsheet.BottomSheetDialogFragment;

public class BottomSheetFragment extends BottomSheetDialogFragment {
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_bottom_sheet, container, false);

        EditText editText = view.findViewById(R.id.editTextBottomSheet);
        Button sendButton = view.findViewById(R.id.buttonSendResult);

        sendButton.setOnClickListener(v -> {
            String text = editText.getText().toString();

            Bundle result = new Bundle();
            result.putString("bundleKey", text);

            getParentFragmentManager().setFragmentResult("requestKey", result);

            dismiss();
        });

        return view;
    }
}
