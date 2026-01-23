package repository;

public interface AuthRepository {

    interface AuthCallback {
        void onSuccess();
        void onFailure(String message);
    }

    void login(String email, String password, AuthCallback callback);
    void register(String email, String password, AuthCallback callback);
    void logout();
    String getCurrentUserEmail();
}