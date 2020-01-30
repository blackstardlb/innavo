package nl.cameldevstudio.innavo.ui.login;

import android.app.ActionBar;
import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthUserCollisionException;
import com.google.firebase.auth.FirebaseUser;

import java.net.MalformedURLException;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.OnTextChanged;
import nl.cameldevstudio.innavo.InnavoApplication;
import nl.cameldevstudio.innavo.R;
import nl.cameldevstudio.innavo.services.login.LoginService;
import nl.cameldevstudio.innavo.ui.base.BaseFragment;
import nl.cameldevstudio.innavo.ui.main.MainActivity;

public class RegisterFragment extends BaseFragment {

    private static final String TAG = "RegisterFragment";

    @BindView(R.id.et_username)
    protected EditText et_username;
    @BindView(R.id.et_email)
    protected EditText et_email;
    @BindView(R.id.et_password)
    protected EditText et_password;
    @BindView(R.id.b_fragment_register_register)
    protected Button b_fragment_register_register;

    @Inject
    protected LoginService loginService;
    private RegisterViewModel registerViewModel;


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        registerViewModel = getPrivateViewModel(RegisterViewModel.class);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_register, container, false);
        ButterKnife.bind(this, view);


        et_username.setText("foobarfoo");
        et_email.setText("foobarfoo@gmail.com");
        et_password.setText("boontjes1");

        getCompositeDisposable().add(
            registerViewModel.getIsRegisterButtonEnabled().subscribe(result -> b_fragment_register_register.setEnabled(result))
        );
        return view;
    }

    @OnTextChanged(R.id.et_username)
    protected void onUsernameChanged(CharSequence s) {
        registerViewModel.setUsername(s.toString());
    }

    @OnTextChanged(R.id.et_email)
    protected void onEmailChanged(CharSequence s) {
        registerViewModel.setEmail(s.toString());
    }

    @OnTextChanged(R.id.et_password)
    protected void onPasswordChanged(CharSequence s) {
        registerViewModel.setPassword(s.toString());
    }

    @OnClick(R.id.b_fragment_register_register)
    protected void onRegisterClick() {
        getCompositeDisposable().add(
            registerViewModel.register(et_username.getText().toString(), et_email.getText().toString(), et_password.getText().toString())
            .subscribe(() -> {
                FirebaseUser currentUser = FirebaseAuth.getInstance().getCurrentUser();
                if (currentUser != null) {
                    Log.d(TAG, currentUser.getDisplayName());
                }
                Log.d(TAG, "Success");
            }, error -> {
                if (error instanceof FirebaseAuthUserCollisionException) {
                    Log.d(TAG, "User already exists.");
                }
            })
        );
    }
}
