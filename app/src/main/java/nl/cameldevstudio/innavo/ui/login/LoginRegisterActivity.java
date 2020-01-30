package nl.cameldevstudio.innavo.ui.login;

import android.support.annotation.NonNull;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import butterknife.BindView;
import butterknife.ButterKnife;
import nl.cameldevstudio.innavo.R;

public class LoginRegisterActivity extends AppCompatActivity implements LoginRegisterFragment.LoginListener {

    private static final String TAG = "LoginRegisterActivity";

    @BindView(R.id.vp_foo)
    protected ViewPager vp_foo;


    private SectionsStagePagerAdapter mSectionsStagePagerAdapter;
    private FirebaseAuth mAuth;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        ButterKnife.bind(this);
        setupViewPager(vp_foo);
        mAuth = FirebaseAuth.getInstance();
        FirebaseUser currentUser = mAuth.getCurrentUser();

    }

    public void setViewPager(int i) {
        Log.d(TAG, "Set view to " + i);
        vp_foo.setCurrentItem(i);
    }

    private void setupViewPager(ViewPager viewPager) {
        SectionsStagePagerAdapter adapter = new SectionsStagePagerAdapter(getSupportFragmentManager());
        adapter.AddFragment(new LoginRegisterFragment(), "LoginRegisterFragment");
        adapter.AddFragment(new LoginFragment(), "LoginFragment");
        adapter.AddFragment(new RegisterFragment(), "RegisterFragment");
        viewPager.setAdapter(adapter);
    }

    @Override
    public void login(String email, String pass) {

    }


    @Override
    public void onBackPressed() {

        int activeScreen = vp_foo.getCurrentItem();

        if (activeScreen != 0) {
            setViewPager(0);
        }
    }
}
