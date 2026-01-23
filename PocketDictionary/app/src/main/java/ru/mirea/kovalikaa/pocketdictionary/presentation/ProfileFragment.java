package ru.mirea.kovalikaa.pocketdictionary.presentation;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import ru.mirea.kovalikaa.pocketdictionary.R;


public class ProfileFragment extends Fragment {

    private MainViewModel viewModel;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_profile, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        viewModel = new ViewModelProvider(requireActivity(), new MainViewModelFactory(requireContext()))
                .get(MainViewModel.class);

        TextView textViewEmail = view.findViewById(R.id.textViewUserEmail);
        Button buttonAction = view.findViewById(R.id.buttonLogout);

        String email = viewModel.getCurrentUserEmail();

        if (email != null) {
            textViewEmail.setText(email);
            buttonAction.setText("Выйти из аккаунта");
            buttonAction.setOnClickListener(v -> {
                viewModel.logout();
                goToAuth();
            });
        } else {
            textViewEmail.setText("Guest Mode");
            buttonAction.setText("Войти / Регистрация");
            buttonAction.setOnClickListener(v -> {
                goToAuth();
            });
        }
    }

    private void goToAuth() {
        Intent intent = new Intent(getActivity(), AuthActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
        if (getActivity() != null) getActivity().finish();
    }
}