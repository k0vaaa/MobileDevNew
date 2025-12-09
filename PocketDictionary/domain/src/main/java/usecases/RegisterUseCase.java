package usecases;

import repository.AuthRepository;

public class RegisterUseCase {
    private final AuthRepository authRepository;
    public RegisterUseCase(AuthRepository authRepository) { this.authRepository = authRepository; }

    public void execute(String email, String password, AuthRepository.AuthCallback callback) {
        authRepository.register(email, password, callback);
    }
}