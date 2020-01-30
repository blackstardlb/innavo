package nl.cameldevstudio.innavo.ui.base;

import android.arch.lifecycle.ViewModel;
import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import javax.annotation.Nonnull;
import javax.inject.Inject;

import io.reactivex.disposables.CompositeDisposable;
import nl.cameldevstudio.innavo.InnavoApplication;
import nl.cameldevstudio.innavo.di.modules.viewmodel.ViewModelFactory;
import nl.cameldevstudio.innavo.helpers.TagHelper;

public abstract class BaseActivity extends AppCompatActivity {
    @Inject
    protected ViewModelFactory viewModelFactory;

    private CompositeDisposable compositeDisposable = new CompositeDisposable();

    @Nonnull
    protected CompositeDisposable getCompositeDisposable() {
        return compositeDisposable;
    }

    protected <T extends ViewModel> T getViewModel(Class<T> modelClass) {
        return ViewModelProviders.of(this, viewModelFactory).get(modelClass);
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        InnavoApplication.getInnavoComponent().inject(this);
    }

    @Override
    protected void onDestroy() {
        compositeDisposable.dispose();
        super.onDestroy();
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
}
