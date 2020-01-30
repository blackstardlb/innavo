package nl.cameldevstudio.innavo.ui.login;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import nl.cameldevstudio.innavo.R;

public class LoginRegisterFragment extends Fragment {

    private static final String TAG = "LoginRegisterFragment";

    @BindView(R.id.b_login)
    protected Button b_login;
    @BindView(R.id.b_register)
    protected Button b_register;

    // With the activityCommander this fragment can communicate with the activity it is in.
    LoginListener activityCommander;

    public interface LoginListener {
        public void login(String email, String pass);
        public void setViewPager(int i);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        try {
            activityCommander = (LoginListener) getActivity();
        } catch (ClassCastException e) {
            throw new ClassCastException(getActivity().toString());
        }
    }


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_login_register, container, false);
        ButterKnife.bind(this, view);

        return view;
    }

    @OnClick(R.id.b_login)
    protected void onLoginClicked() {
        activityCommander.setViewPager(1);
    }

    @OnClick(R.id.b_register)
    protected void onRegisterClicked() {
        activityCommander.setViewPager(2);
    }
}
