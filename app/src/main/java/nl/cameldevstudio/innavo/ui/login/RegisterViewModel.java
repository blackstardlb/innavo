package nl.cameldevstudio.innavo.ui.login;

import android.util.Log;

import javax.inject.Inject;

import io.reactivex.Completable;
import io.reactivex.Observable;
import io.reactivex.subjects.BehaviorSubject;
import nl.cameldevstudio.innavo.services.login.LoginService;
import nl.cameldevstudio.innavo.ui.base.BaseViewModel;
import org.apache.commons.validator.routines.EmailValidator;

public class RegisterViewModel extends BaseViewModel {
    private static final String TAG = "RegisterViewModel";
    private LoginService loginService;
    private String email;
    private String password;
    private String username;

    // This is the observeable object. In the class we modify the state of this object. Other classes are interested in the state of this object.
    private BehaviorSubject<Boolean> isRegisterButtonEnabled = BehaviorSubject.createDefault(false);

    @Inject
    public RegisterViewModel(LoginService loginService) {
        this.loginService = loginService;
    }

    public Completable register(String username, String email, String password) {

        // ...

        return loginService.register(username, email, password);
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
        Log.d(TAG, "isUserNameValid: "  + isUsernameValid(username) + ", isEmailValid: " + isEmailValid(email) + ", isPasswordValid:" + isPasswordValid(password));
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