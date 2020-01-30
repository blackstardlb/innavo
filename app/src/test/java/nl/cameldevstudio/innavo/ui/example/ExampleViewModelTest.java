package nl.cameldevstudio.innavo.ui.example;


import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.junit.MockitoJUnitRunner;

import io.reactivex.Single;
import nl.cameldevstudio.innavo.services.example.ExampleService;

import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class ExampleViewModelTest {
    private ExampleService exampleService = mock(ExampleService.class);
    private ExampleViewModel exampleViewModel;

    @Before
    public void setup() {
        exampleViewModel = new ExampleViewModel(exampleService);
    }

    @Test
    public void getFirstEmail() {
        exampleViewModel
                .getEmail()
                .test()
                .assertValue("");
    }

    @Test
    public void getChangedEmail() {
        String expected = "test@email.com";
        exampleViewModel.setEmail(expected);
        exampleViewModel
                .getEmail()
                .test()
                .assertValue(expected);
    }


    @Test
    public void setEmail() {
        String expected = "test@email.com";
        exampleViewModel.setEmail(expected);
        exampleViewModel
                .getEmail()
                .test()
                .assertValue(expected);
    }

    @Test
    public void isEmailValidForValidEmail() {
        String expected = "test@email.com";
        when(exampleService.testEmail(expected)).thenReturn(Single.just(true));
        exampleViewModel.setEmail(expected);
        exampleViewModel
                .isEmailValid()
                .test()
                .assertValue(true);
    }


    @Test
    public void isEmailValidForInvalidEmail() {
        when(exampleService.testEmail(anyString())).thenReturn(Single.just(false));
        exampleViewModel
                .isEmailValid()
                .test()
                .assertValue(false);
    }

    @Test
    public void getEmailErrorMessageForValidEmail() {
        String validEmail = "test@email.com";
        String expected = "";
        when(exampleService.testEmail(validEmail)).thenReturn(Single.just(true));
        exampleViewModel.setEmail(validEmail);
        exampleViewModel
                .getEmailErrorMessage()
                .test()
                .assertValue(expected);
    }

    public void getEmailErrorMessageForInvalidEmail() {
        String expected = "Email is not valid";
        when(exampleService.testEmail(anyString())).thenReturn(Single.just(false));
        exampleViewModel
                .getEmailErrorMessage()
                .test()
                .assertValue(expected);
    }
}