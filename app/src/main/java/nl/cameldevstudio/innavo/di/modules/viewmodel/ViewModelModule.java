package nl.cameldevstudio.innavo.di.modules.viewmodel;

import android.arch.lifecycle.ViewModel;
import android.arch.lifecycle.ViewModelProvider;

import dagger.Binds;
import dagger.Module;
import dagger.multibindings.IntoMap;
import nl.cameldevstudio.innavo.ui.building.find.list.BuildingsListViewModel;
import nl.cameldevstudio.innavo.ui.building.find.map.BuildingsMapViewModel;
import nl.cameldevstudio.innavo.ui.building.find.LocationViewModel;
import nl.cameldevstudio.innavo.ui.building.info.BuildingInfoViewModel;
import nl.cameldevstudio.innavo.ui.example.ExampleViewModel;
import nl.cameldevstudio.innavo.ui.login.LoginViewModel;
import nl.cameldevstudio.innavo.ui.login.RegisterViewModel;
import nl.cameldevstudio.innavo.ui.favorieten.FavorietenViewModel;

@Module()
public abstract class ViewModelModule {
    @Binds
    @IntoMap
    @ViewModelKey(ExampleViewModel.class)
    abstract ViewModel bindExampleViewModel(ExampleViewModel exampleViewModel);

    @Binds
    @IntoMap
    @ViewModelKey(BuildingsMapViewModel.class)
    abstract ViewModel bindBuildingsMapViewModel(BuildingsMapViewModel buildingsMapViewModel);

    @Binds
    @IntoMap
    @ViewModelKey(LocationViewModel.class)
    abstract ViewModel bindLocationViewModel(LocationViewModel locationViewModel);

    @Binds
    @IntoMap
    @ViewModelKey(BuildingsListViewModel.class)
    abstract ViewModel bindMapListViewModel(BuildingsListViewModel buildingsListViewModel);

    @Binds
    @IntoMap
    @ViewModelKey(BuildingInfoViewModel.class)
    abstract ViewModel bindBuildingInfoViewModel(BuildingInfoViewModel buildingInfoViewModel);

    @Binds
    @IntoMap
    @ViewModelKey(RegisterViewModel.class)
    abstract ViewModel bindRegisterViewModel(RegisterViewModel registerViewModel);

    @Binds
    @IntoMap
    @ViewModelKey(LoginViewModel.class)
    abstract ViewModel bindLoginViewModel(LoginViewModel loginViewModel);

    @ViewModelKey(FavorietenViewModel.class)
    abstract ViewModel favorietenViewModel(FavorietenViewModel favorietenViewModel);

    @Binds
    abstract ViewModelProvider.Factory bindViewModelFactory(ViewModelFactory viewModelFactory);
}

