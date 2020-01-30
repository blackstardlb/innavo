package nl.cameldevstudio.innavo.ui.example;

import javax.inject.Inject;

import io.reactivex.Observable;
import io.reactivex.subjects.BehaviorSubject;
import nl.cameldevstudio.innavo.services.example.ExampleService;
import nl.cameldevstudio.innavo.ui.base.BaseViewModel;

/**
 * No android classes here. For example no (Activities / Fragments / Views)
 */
public class ExampleViewModel extends BaseViewModel {
    private BehaviorSubject<String> emailSubject = BehaviorSubject.createDefault("");
    private ExampleService exampleService;

    @Inject
    public ExampleViewModel(ExampleService exampleService) {
        this.exampleService = exampleService;
    }

    public Observable<String> getEmail() {
        return emailSubject;
    }

    public void setEmail(String email) {
        emailSubject.onNext(email);
    }

    public Observable<Boolean> isEmailValid() {
        return getEmail().flatMap(email -> exampleService.testEmail(email).toObservable());
    }

    public Observable<String> getEmailErrorMessage() {
        return isEmailValid().map(isEmailValid -> isEmailValid ? "" : "Email is not valid");
    }
}
