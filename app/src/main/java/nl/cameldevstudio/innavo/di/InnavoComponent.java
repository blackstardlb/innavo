package nl.cameldevstudio.innavo.di;

import javax.inject.Singleton;

import dagger.Component;
import nl.cameldevstudio.innavo.di.modules.ApplicationModule;
import nl.cameldevstudio.innavo.di.modules.DataModule;
import nl.cameldevstudio.innavo.di.modules.ExampleModule;
import nl.cameldevstudio.innavo.di.modules.FirebaseModule;
import nl.cameldevstudio.innavo.di.modules.viewmodel.ViewModelModule;
import nl.cameldevstudio.innavo.ui.base.BaseActivity;
import nl.cameldevstudio.innavo.ui.base.BaseFragment;
import nl.cameldevstudio.innavo.ui.login.RegisterFragment;

@Singleton
@Component(modules = {ApplicationModule.class, FirebaseModule.class, ViewModelModule.class, ExampleModule.class, DataModule.class})
public interface InnavoComponent {
    void inject(BaseActivity baseActivity);

    void inject(BaseFragment baseFragment);
}
