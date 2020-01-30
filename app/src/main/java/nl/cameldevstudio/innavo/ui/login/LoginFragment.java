package nl.cameldevstudio.innavo.ui.login;

import android.arch.lifecycle.ViewModel;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthUserCollisionException;
import com.google.firebase.auth.FirebaseUser;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import nl.cameldevstudio.innavo.R;
import nl.cameldevstudio.innavo.services.login.LoginService;
import nl.cameldevstudio.innavo.ui.base.BaseFragment;
import nl.cameldevstudio.innavo.ui.main.MainActivity;

public class LoginFragment extends BaseFragment {

    private static final String TAG = "LoginFragment";
    public static final String EXTRA_MESSAGE = "nl.cameldevstudio.innavo.ui.login";

    @BindView(R.id.et_login_email)
    protected EditText et_login_email;
    @BindView(R.id.et_login_password)
    protected EditText et_login_password;
    @BindView(R.id.b_fragment_login_login)
    protected Button b_fragment_login_login;
    @BindView(R.id.iv_logo)
    protected ImageView iv_logo;

    @Inject
    protected LoginService loginService;
    private LoginViewModel loginViewModel;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        loginViewModel = getPrivateViewModel(LoginViewModel.class);

    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_login, container, false);

        ButterKnife.bind(this, view);

        et_login_email.setText("foobarfoo@gmail.com");
        et_login_password.setText("boontjes1");

        return view;
    }

    @OnClick(R.id.b_fragment_login_login)
    protected void onLoginClick() {
        getCompositeDisposable().add(
                loginViewModel.login(et_login_email.getText().toString(), et_login_password.getText().toString())
                        .subscribe(() -> {
                            FirebaseUser currentUser = FirebaseAuth.getInstance().getCurrentUser();
                            if (currentUser != null) {
                                Intent intent = new Intent(getActivity(), MainActivity.class);
                                startActivity(intent);
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
