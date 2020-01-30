package nl.cameldevstudio.innavo.helpers;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.drawable.Drawable;
import android.support.annotation.DrawableRes;

import com.google.android.gms.maps.model.BitmapDescriptor;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;

import javax.annotation.Nonnull;

/**
 * Helper class for images / bitmaps
 */
public class ImageUtils {
    /**
     * Get resource {@link BitmapDescriptor} for a drawable resource file at the specified size.
     *
     * @param context the context to fetch the resource
     * @param res     the drawable resource file
     * @param size    the specified size in dp
     * @return the {@link BitmapDescriptor} for the resource file
     */
    @Nonnull
    public static BitmapDescriptor getMarkerIconFromDrawable(@Nonnull Context context, @DrawableRes int res, int size) {
        Drawable drawable = context.getResources().getDrawable(res);
        return getMarkerIconFromDrawable(drawable, (int) PixelConverter.convertDpToPixel(size, context));
    }

    /**
     * Get {@link BitmapDescriptor} for a {@link Drawable} at the specified size
     *
     * @param drawable the {@link Drawable}
     * @param size     the size in sp
     * @return the {@link BitmapDescriptor} for the {@link Drawable}
     */
    @Nonnull
    private static BitmapDescriptor getMarkerIconFromDrawable(@Nonnull Drawable drawable, int size) {
        Canvas canvas = new Canvas();
        Bitmap bitmap = Bitmap.createBitmap(size, size, Bitmap.Config.ARGB_8888);
        canvas.setBitmap(bitmap);
        drawable.setBounds(0, 0, size, size);
        drawable.draw(canvas);
        return BitmapDescriptorFactory.fromBitmap(bitmap);
    }
}
