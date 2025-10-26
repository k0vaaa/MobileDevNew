package ru.mirea.kovalikaa.pocketdictionary.presentation;

import android.content.Intent;
import android.os.Bundle;
import android.widget.EditText;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import android.widget.Button;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;

import repository.AuthRepository;
import ru.mirea.kovalikaa.pocketdictionary.R;
import ru.mirea.kovalikaa.pocketdictionary.data.repository.AuthRepositoryImpl;
import usecases.LoginUseCase;
import usecases.RegisterUseCase;

public class AuthActivity extends AppCompatActivity {

    private EditText editTextEmail;
    private EditText editTextPassword;
    private LoginUseCase loginUseCase;
    private RegisterUseCase registerUseCase;
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mAuth = FirebaseAuth.getInstance();
        if (mAuth.getCurrentUser() != null) {
            goToMainActivity();
            return;
        }

        setContentView(R.layout.activity_auth);

        AuthRepository authRepository = new AuthRepositoryImpl(mAuth);
        loginUseCase = new LoginUseCase(authRepository);
        registerUseCase = new RegisterUseCase(authRepository);

        editTextEmail = findViewById(R.id.editTextEmail);
        editTextPassword = findViewById(R.id.editTextPassword);
        Button buttonLogin = findViewById(R.id.buttonLogin);
        Button buttonRegister = findViewById(R.id.buttonRegister);

        buttonLogin.setOnClickListener(v -> {
            String email = editTextEmail.getText().toString();
            String password = editTextPassword.getText().toString();

            loginUseCase.execute(email, password, new AuthRepository.AuthCallback() {
                @Override
                public void onSuccess() {
                    Toast.makeText(AuthActivity.this, "Login successful!", Toast.LENGTH_SHORT).show();
                    goToMainActivity();
                }

                @Override
                public void onFailure(String message) {
                    Toast.makeText(AuthActivity.this, "Login failed: " + message, Toast.LENGTH_SHORT).show();
                }
            });
        });

        buttonRegister.setOnClickListener(v -> {
            String email = editTextEmail.getText().toString();
            String password = editTextPassword.getText().toString();

            registerUseCase.execute(email, password, new AuthRepository.AuthCallback() {
                @Override
                public void onSuccess() {
                    Toast.makeText(AuthActivity.this, "Registration successful! Please login.", Toast.LENGTH_SHORT).show();
                }

                @Override
                public void onFailure(String message) {
                    Toast.makeText(AuthActivity.this, "Registration failed: " + message, Toast.LENGTH_SHORT).show();
                }
            });
        });
    }

    private void goToMainActivity() {
        startActivity(new Intent(AuthActivity.this, MainActivity.class));
        finish();
    }
}