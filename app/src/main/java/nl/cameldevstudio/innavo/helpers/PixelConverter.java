package nl.cameldevstudio.innavo.helpers;

import android.content.Context;
import android.content.res.Resources;
import android.util.DisplayMetrics;

/**
 * Helper class for converting between different types of pixels
 */
public class PixelConverter {
    /**
     * Convert density pixels to screen pixels
     *
     * @param dp      the density pixels
     * @param context the context
     * @return screen pixels as a float
     */
    public static float convertDpToPixel(float dp, Context context) {
        Resources resources = context.getResources();
        DisplayMetrics metrics = resources.getDisplayMetrics();
        return dp * ((float) metrics.densityDpi / DisplayMetrics.DENSITY_DEFAULT);
    }
}
