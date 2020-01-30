package nl.cameldevstudio.innavo.ui.example;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnTextChanged;
import nl.cameldevstudio.innavo.R;
import nl.cameldevstudio.innavo.ui.base.BaseFragment;

public class ExampleFragment extends BaseFragment {
    @BindView(R.id.email_address_input)
    protected EditText editText;

    @BindView(R.id.email_address_error)
    protected TextView textView;

    @BindView(R.id.submit_button)
    protected Button submitButton;

    private ExampleViewModel exampleViewModel;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        exampleViewModel = getPrivateViewModel(ExampleViewModel.class);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_example, container, false);
        unbinder = ButterKnife.bind(this, view);

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

        return view;
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
