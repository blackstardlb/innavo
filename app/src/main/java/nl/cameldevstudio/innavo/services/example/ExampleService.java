package nl.cameldevstudio.innavo.services.example;


import javax.annotation.Nullable;

import io.reactivex.Single;

public interface ExampleService {
    Single<Boolean> testEmail(@Nullable String email);
}
