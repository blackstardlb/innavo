package nl.cameldevstudio.innavo.ui.login;

import org.apache.commons.validator.routines.EmailValidator;

import javax.inject.Inject;

import io.reactivex.Completable;
import io.reactivex.Observable;
import io.reactivex.subjects.BehaviorSubject;
import nl.cameldevstudio.innavo.services.login.LoginService;
import nl.cameldevstudio.innavo.ui.base.BaseViewModel;

public class LoginViewModel extends BaseViewModel {
    private LoginService loginService;
    private String email;
    private String password;
    private String username;

    private BehaviorSubject<Boolean> isRegisterButtonEnabled = BehaviorSubject.createDefault(false);

    @Inject
    public LoginViewModel(LoginService loginService) {
        this.loginService = loginService;
    }

    public Completable login(String email, String password) {

        // ...

        return loginService.login(email, password);
    }

    public void setUsername(String username) {
        this.username = username;
        isRegisterButtonEnabled.onNext(areInputsValid());
    }

    public void setEmail(String email) {
        this.email = email;
        isRegisterButtonEnabled.onNext(areInputsValid());
    }

    public void setPassword(String password) {
        this.password = password;
        isRegisterButtonEnabled.onNext(areInputsValid());
    }

    public String getUsername() {
        return username;
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }

    private boolean areInputsValid() {
        return isUsernameValid(username) && isEmailValid(email) && isPasswordValid(password);
    }

    private boolean isUsernameValid(String username) {
        return ((username != null) && (username.length() < 100) && (username.length() > 5));
    }

    private boolean isEmailValid(String email) {
        return EmailValidator.getInstance().isValid(email);
    }

    private boolean isPasswordValid(String password) {
        return ((password != null) && (password.length() < 100) && (password.length() > 5));
    }

    public Observable<Boolean> getIsRegisterButtonEnabled() {
        return isRegisterButtonEnabled;
    }
}