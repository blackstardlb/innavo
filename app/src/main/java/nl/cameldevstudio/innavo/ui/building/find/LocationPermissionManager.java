package nl.cameldevstudio.innavo.ui.building.find;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageManager;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;

import java.lang.ref.WeakReference;

import javax.annotation.Nonnull;

import java8.util.function.Consumer;
import nl.cameldevstudio.innavo.R;
import nl.cameldevstudio.innavo.ui.base.BaseActivity;

public class LocationPermissionManager {
    private static final int MY_PERMISSIONS_REQUEST_LOCATION = 99;
    private WeakReference<Activity> activityWeakReference;
    private Consumer<Boolean> onResult;

    public LocationPermissionManager(@Nonnull Activity activity, @Nonnull Consumer<Boolean> onResult) {
        this.activityWeakReference = new WeakReference<>(activity);
        this.onResult = onResult;
    }

    public void ensureHavePermission() {
        Activity activity = activityWeakReference.get();
        if (activity != null) {
            if (ContextCompat.checkSelfPermission(activity, Manifest.permission.ACCESS_FINE_LOCATION)
                    != PackageManager.PERMISSION_GRANTED) {

                // Should we show an explanation?
                if (ActivityCompat.shouldShowRequestPermissionRationale(activity,
                        Manifest.permission.ACCESS_FINE_LOCATION)) {
                    // Show an explanation to the user *asynchronously* -- don't block
                    // this thread waiting for the user's response! After the user
                    // sees the explanation, try again to request the permission.
                    new AlertDialog.Builder(activity)
                            .setTitle(R.string.title_location_permission)
                            .setMessage(R.string.text_location_permission)
                            .setPositiveButton(R.string.ok, (dialogInterface, i) -> {
                            })
                            .setOnDismissListener(dialog -> {
                                //Prompt the user once explanation has been shown
                                ActivityCompat.requestPermissions(activity,
                                        new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                                        MY_PERMISSIONS_REQUEST_LOCATION);
                            })
                            .create()
                            .show();

                } else {
                    // No explanation needed, we can request the permission.
                    ActivityCompat.requestPermissions(activity,
                            new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                            MY_PERMISSIONS_REQUEST_LOCATION);
                }
            } else {
                onResult.accept(true);
            }
        }
    }

    public void onRequestPermissionsResult(int requestCode, int[] grantResults) {
        if (requestCode == MY_PERMISSIONS_REQUEST_LOCATION) {
            Activity activity = activityWeakReference.get();
            if (activity != null) {
                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {

                    // permission was granted, yay! Do the
                    // location-related task you need to do.
                    if (ContextCompat.checkSelfPermission(activity,
                            Manifest.permission.ACCESS_FINE_LOCATION)
                            == PackageManager.PERMISSION_GRANTED) {
                        onResult.accept(true);
                        return;
                    }
                }
                onResult.accept(false);
            }
        }
    }
}
