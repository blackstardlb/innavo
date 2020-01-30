package nl.cameldevstudio.innavo.ui.example;

import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnTextChanged;
import nl.cameldevstudio.innavo.R;
import nl.cameldevstudio.innavo.ui.base.BaseActivity;

public class ExampleActivity extends BaseActivity {

    @BindView(R.id.toolbar)
    protected Toolbar toolbar;

    @BindView(R.id.email_address_input)
    protected EditText editText;

    @BindView(R.id.email_address_error)
    protected TextView textView;

    @BindView(R.id.submit_button)
    protected Button submitButton;

    private ExampleViewModel exampleViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_example);
        ButterKnife.bind(this);
        setSupportActionBar(toolbar);
        exampleViewModel = getViewModel(ExampleViewModel.class);

        getCompositeDisposable()
                .add(
                        exampleViewModel
                                .getEmail()
                                .firstElement()
                                .subscribe(email -> editText.setText(email), this::showError)
                );

        getCompositeDisposable()
                .add(
                        exampleViewModel
                                .isEmailValid()
                                .subscribe(this::onEmailValidityChanged, this::showError)
                );

        getCompositeDisposable()
                .add(
                        exampleViewModel
                                .getEmailErrorMessage()
                                .subscribe(
                                        errorMessage -> textView.setText(errorMessage),
                                        this::showError
                                )
                );
    }

    private void onEmailValidityChanged(boolean isEmailValid) {
        submitButton.setEnabled(isEmailValid);
        if (!isEmailValid) {
            textView.setVisibility(View.VISIBLE);
        } else {
            textView.setVisibility(View.GONE);
        }
    }

    @OnTextChanged(R.id.email_address_input)
    protected void onTextChanged(CharSequence charSequence) {
        exampleViewModel.setEmail(charSequence.toString());
    }
}
