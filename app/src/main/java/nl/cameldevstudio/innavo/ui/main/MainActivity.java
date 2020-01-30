package nl.cameldevstudio.innavo.ui.main;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.IdRes;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.util.Pair;
import android.support.v4.view.ViewPager;
import android.view.View;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;

import butterknife.BindView;
import butterknife.ButterKnife;
import java8.util.Optional;
import java8.util.stream.StreamSupport;
import nl.cameldevstudio.innavo.R;
import nl.cameldevstudio.innavo.ui.indoorAtlas.WayfindingOverlayActivity;
import nl.cameldevstudio.innavo.ui.base.BaseActivity;
import nl.cameldevstudio.innavo.ui.base.BaseFragment;
import nl.cameldevstudio.innavo.ui.building.find.FindBuildingFragment;
import nl.cameldevstudio.innavo.ui.example.ExampleFragment;
import nl.cameldevstudio.innavo.ui.favorieten.FavorietenFragment;
import nl.cameldevstudio.innavo.ui.profile.ProfileFragment;

public class MainActivity extends BaseActivity {
    @BindView(R.id.bottom_navigation_bar)
    protected BottomNavigationView bottomNavigationView;

    @BindView(R.id.slide_viewpager)
    protected ViewPager viewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        setUpBottomNavigationView();
    }

    private void setUpBottomNavigationView() {
        List<Pair<Integer, BaseFragment>> fragments =
                Arrays.asList(
                        new Pair<>(R.id.action_kaart, new FindBuildingFragment()),
                        new Pair<>(R.id.action_favorieten, new FavorietenFragment()),
                        new Pair<>(R.id.action_inchecken, new ExampleFragment()),
                        new Pair<>(R.id.action_vrienden, new ExampleFragment()),
                        new Pair<>(R.id.action_profiel, new ProfileFragment())
                );

        viewPager.setAdapter(new FragmentPagerAdapter(getSupportFragmentManager()) {
            @Override
            public Fragment getItem(int i) {
                return fragments.get(i).second;
            }

            @Override
            public int getCount() {
                return fragments.size();
            }
        });
        bottomNavigationView.setOnNavigationItemSelectedListener(item -> {
            Optional<Pair<Integer, BaseFragment>> first = StreamSupport.stream(fragments).filter(pair -> Objects.equals(pair.first, item.getItemId())).findFirst();
            if (first.isPresent()) {
                viewPager.setCurrentItem(fragments.indexOf(first.get()));
                return true;
            }
            return false;
        });

        setStartingAction(R.id.action_kaart, fragments);
    }

    @SuppressWarnings("SameParameterValue")
    private void setStartingAction(@IdRes int action, List<Pair<Integer, BaseFragment>> fragments) {
        Optional<Pair<Integer, BaseFragment>> pairOptional = StreamSupport.stream(fragments).filter(pair -> Objects.equals(pair.first, action)).findFirst();
        if (pairOptional.isPresent()) {
            viewPager.setCurrentItem(fragments.indexOf(pairOptional.get()));
            bottomNavigationView.setSelectedItemId(action);
        }
    }

    public void launchIndoorAtlas(View view) {
        startActivity(new Intent(MainActivity.this, WayfindingOverlayActivity.class));
    }
}
