package nl.cameldevstudio.innavo.ui.favorieten;


import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import android.widget.ImageView;

import butterknife.BindView;
import butterknife.ButterKnife;

import com.azoft.carousellayoutmanager.CarouselLayoutManager;
import com.azoft.carousellayoutmanager.CenterScrollListener;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.MultiTransformation;
import com.bumptech.glide.load.resource.bitmap.CenterCrop;
import com.bumptech.glide.request.RequestOptions;

import io.reactivex.Maybe;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import jp.wasabeef.glide.transformations.BlurTransformation;
import nl.cameldevstudio.innavo.R;
import nl.cameldevstudio.innavo.ui.base.BaseFragment;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class FavorietenFragment extends BaseFragment {
    @BindView(R.id.favorieten_recycler_view)
    protected RecyclerView recyclerView;

    @BindView(R.id.favorieten_search_view)
    protected SearchView searchView;

    @BindView(R.id.favorieten_background)
    protected ImageView favorietenBackground;

    private Disposable disposable;
    private FavorietenViewModel favorietenViewModel;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        favorietenViewModel = getPrivateViewModel(FavorietenViewModel.class);
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_favorieten, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        unbinder = ButterKnife.bind(this, view);

        final CarouselLayoutManager layoutManager = new CarouselLayoutManager(CarouselLayoutManager.VERTICAL, false);
        layoutManager.setPostLayoutListener(new MyCarouselZoomPostLayoutListener(1.1f));
        layoutManager.setMaxVisibleItems(1);

        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setHasFixedSize(true);
        FavorietenAdapter adapter = new FavorietenAdapter(favorietenViewModel.getImageReferenceService());
        recyclerView.setAdapter(adapter);
        recyclerView.addOnScrollListener(new CenterScrollListener());
        setupBlurredBackground(layoutManager);
        setUpFiltering(adapter);
    }

    private void setUpFiltering(FavorietenAdapter adapter) {
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String s) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String s) {
                favorietenViewModel.setQuery(s);
                adapter.filter(s);
                return false;
            }
        });

        searchView.setQuery(favorietenViewModel.getQuery(), false);
    }

    private void setupBlurredBackground(CarouselLayoutManager layoutManager) {
        layoutManager.addOnItemSelectionListener(adapterPosition -> loadBackgroundImage(layoutManager, adapterPosition));
    }

    private void loadBackgroundImage(CarouselLayoutManager layoutManager, int adapterPosition) {
        if (disposable != null && !disposable.isDisposed()) {
            disposable.dispose();
        }

        disposable = waitForDrawable(layoutManager, adapterPosition)
                .subscribeOn(Schedulers.computation())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(this::loadBackGroundImage, this::showError);
        getCompositeDisposable().add(disposable);
    }

    private void loadBackGroundImage(Drawable drawable) {
        Glide.with(this)
                .load(drawable)
                .apply(RequestOptions.bitmapTransform(new MultiTransformation<>(new BlurTransformation(), new CenterCrop())).placeholder(favorietenBackground.getDrawable()))
                .into(favorietenBackground);
    }

    private Maybe<Drawable> waitForDrawable(CarouselLayoutManager layoutManager, int adapterPosition) {
        return Maybe.fromCallable(() -> {
            View viewByPosition = layoutManager.findViewByPosition(adapterPosition);
            if (viewByPosition != null) {
                ImageView image = viewByPosition.findViewById(R.id.favorite_image);
                if (image != null) {
                    do {
                        try {
                            Thread.sleep(200);
                        } catch (InterruptedException ignored) {
                        }
                    } while (image.getDrawable() == null);
                    return image.getDrawable();
                }
            } else {
                showError("view position is null retrying");
                loadBackgroundImage(layoutManager, adapterPosition);
            }
            return null;
        });
    }
}
