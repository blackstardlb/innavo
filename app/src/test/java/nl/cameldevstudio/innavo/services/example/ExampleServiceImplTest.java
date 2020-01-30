package nl.cameldevstudio.innavo.services.example;

import org.junit.Test;

import io.reactivex.Single;
import java8.util.J8Arrays;

public class ExampleServiceImplTest {
    private ExampleService exampleService = new ExampleServiceImpl();

    private static final String[] validEmailAddresses = new String[]{"journaldev@yahoo.com", "journaldev-100@yahoo.com",
            "journaldev.100@yahoo.com", "journaldev111@journaldev.com", "journaldev-100@journaldev.net",
            "journaldev.100@journaldev.com.au", "journaldev@1.com", "journaldev@gmail.com.com",
            "journaldev+100@gmail.com", "journaldev-100@yahoo-test.com", "journaldev_100@yahoo-test.ABC.CoM"};

    private static final String[] invalidEmailAddresses = new String[]{"journaldev", "journaldev@.com.my",
            "journaldev123@gmail.a", "journaldev123@.com", "journaldev123@.com.com", ".journaldev@journaldev.com",
            "journaldev()*@gmail.com", "journaldev@%*.com", "journaldev..2002@gmail.com", "journaldev.@gmail.com",
            "journaldev@journaldev@gmail.com", "journaldev@gmail.com.1a"};

    @Test
    public void testValidEmail() {
        J8Arrays.stream(validEmailAddresses).forEach(email -> {
            Single<Boolean> booleanSingle = exampleService.testEmail(email);
            booleanSingle
                    .test()
                    .assertResult(true);
        });
    }

    @Test
    public void testInvalidEmail() {
        J8Arrays.stream(invalidEmailAddresses).forEach(email -> {
            Single<Boolean> booleanSingle = exampleService.testEmail(email);
            booleanSingle
                    .test()
                    .assertResult(false);
        });
    }

    @Test
    public void testNullEmail() {
        Single<Boolean> booleanSingle = exampleService.testEmail(null);
        booleanSingle
                .test()
                .assertResult(false);
    }
}