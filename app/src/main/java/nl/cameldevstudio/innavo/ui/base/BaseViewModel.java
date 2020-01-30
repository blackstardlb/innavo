package nl.cameldevstudio.innavo.ui.base;

import android.arch.lifecycle.ViewModel;

import javax.annotation.Nonnull;

import io.reactivex.disposables.CompositeDisposable;

public abstract class BaseViewModel extends ViewModel {
    private CompositeDisposable compositeDisposable = new CompositeDisposable();

    @Nonnull
    protected CompositeDisposable getCompositeDisposable() {
        return compositeDisposable;
    }

    @Override
    protected void onCleared() {
        super.onCleared();
        compositeDisposable.dispose();
    }
}
