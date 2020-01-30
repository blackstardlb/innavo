package nl.cameldevstudio.innavo.services.login;

import android.app.Activity;
import android.content.Context;
import android.util.EventLogTags;
import android.util.Log;

import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;

import durdinapps.rxfirebase2.RxFirebaseAuth;
import durdinapps.rxfirebase2.RxFirebaseUser;
import io.reactivex.Completable;
import io.reactivex.Maybe;
import io.reactivex.annotations.NonNull;

public class LoginServiceImpl implements LoginService {

    private static final String TAG = "LoginService";

    private FirebaseAuth mAuth;

    public LoginServiceImpl(FirebaseAuth firebaseAuth) {
        mAuth = firebaseAuth;
    }

    @Override
    public Completable register(String username, String email, String pass) {
        UserProfileChangeRequest userProfileChangeRequest = new UserProfileChangeRequest.Builder().setDisplayName(username).build();
        return RxFirebaseAuth.createUserWithEmailAndPassword(mAuth, email, pass)
                .flatMapCompletable(it -> {
                    FirebaseUser currentUser = mAuth.getCurrentUser();
                    if (currentUser != null) {
                        return RxFirebaseUser.updateProfile(currentUser, userProfileChangeRequest);
                    } else {
                        throw new Exception("User object was null");
                    }
                });
    }

    //@Override
    //public Completable login(String email, String pass) {
    //    return RxFirebaseAuth.signInWithEmailAndPassword(mAuth, email, pass)
    //            .flatMapCompletable(it -> {
    //                FirebaseUser currentUser = mAuth.getCurrentUser();
    //                if (currentUser != null) {
    //                    return RxFirebaseUser.updateProfile(currentUser, userProfileChangeRequest);
    //                } else {
    //                    throw new Exception("User object was null");
    //                }
    //            });
    //}
    public Completable login(String email, String pass) {
      return RxFirebaseAuth.signInWithEmailAndPassword(mAuth, email, pass).flatMapCompletable(result -> Completable.complete());
    }
}
