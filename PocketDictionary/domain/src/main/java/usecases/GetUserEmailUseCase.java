package usecases;
import repository.AuthRepository;

public class GetUserEmailUseCase {
    private final AuthRepository authRepository;

    public GetUserEmailUseCase(AuthRepository authRepository) {
        this.authRepository = authRepository;
    }

    public String execute() {
        return authRepository.getCurrentUserEmail();
    }
}