package nl.cameldevstudio.innavo.services.login;

import io.reactivex.Completable;

public interface LoginService {
    Completable register(String username, String email, String pass);
    Completable login(String email, String pass);
}
