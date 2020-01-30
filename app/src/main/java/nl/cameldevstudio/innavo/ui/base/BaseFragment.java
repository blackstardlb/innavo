package nl.cameldevstudio.innavo.ui.base;

import android.arch.lifecycle.ViewModel;
import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;

import javax.annotation.Nonnull;
import javax.inject.Inject;

import butterknife.Unbinder;
import io.reactivex.disposables.CompositeDisposable;
import nl.cameldevstudio.innavo.InnavoApplication;
import nl.cameldevstudio.innavo.di.modules.viewmodel.ViewModelFactory;
import nl.cameldevstudio.innavo.helpers.TagHelper;

public abstract class BaseFragment extends Fragment {
    @Inject
    protected ViewModelFactory viewModelFactory;

    protected Unbinder unbinder;

    private CompositeDisposable compositeDisposable = new CompositeDisposable();

    @Nonnull
    protected CompositeDisposable getCompositeDisposable() {
        return compositeDisposable;
    }

    /**
     * Get a view model that is bound to the lifecycle of this fragment.
     *
     * @param modelClass the view model class
     * @param <T>        the view model type
     * @return view model of type T
     */
    protected <T extends ViewModel> T getPrivateViewModel(Class<T> modelClass) {
        return ViewModelProviders.of(this, viewModelFactory).get(modelClass);
    }

    /**
     * Get a view model that is bound to the lifecycle of the activity this fragment is mounted to
     * and can be shared with other fragments.
     *
     * @param modelClass the view model class
     * @param <T>        the view model type
     * @return view model of type T
     */
    protected <T extends ViewModel> T getSharedViewModel(Class<T> modelClass) {
        //noinspection ConstantConditions
        return ViewModelProviders.of(getActivity(), viewModelFactory).get(modelClass);
    }

    protected void showError(@Nullable Throwable throwable) {
        Log.e(TagHelper.getTag(), "", throwable);
    }

    protected void showError(@Nullable String error) {
        Log.e(TagHelper.getTag(), error);
    }

    protected void showMessage(@Nullable String message) {
        Log.d(TagHelper.getTag(), message);
    }

    @Override
    public void onDestroyView() {
        compositeDisposable.dispose();
        compositeDisposable = new CompositeDisposable();
        if (unbinder != null) {
            unbinder.unbind();
        }
        super.onDestroyView();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        InnavoApplication.getInnavoComponent().inject(this);
    }
}
