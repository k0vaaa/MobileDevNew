package usecases;


import repository.AuthRepository;

public class LogoutUseCase {
    private final AuthRepository authRepository;
    public LogoutUseCase(AuthRepository authRepository) { this.authRepository = authRepository; }
    public void execute() { authRepository.logout(); }
}