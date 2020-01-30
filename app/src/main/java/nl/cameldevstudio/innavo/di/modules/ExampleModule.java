package nl.cameldevstudio.innavo.di.modules;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import nl.cameldevstudio.innavo.services.example.ExampleService;
import nl.cameldevstudio.innavo.services.example.ExampleServiceImpl;

@Module
public class ExampleModule {
    @Provides
    @Singleton
    public ExampleService exampleService() {
        return new ExampleServiceImpl();
    }
}
