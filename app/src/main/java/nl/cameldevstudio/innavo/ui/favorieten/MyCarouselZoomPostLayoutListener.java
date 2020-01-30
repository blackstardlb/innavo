package nl.cameldevstudio.innavo.ui.favorieten;

import android.support.annotation.NonNull;
import android.view.View;
import com.azoft.carousellayoutmanager.CarouselLayoutManager;
import com.azoft.carousellayoutmanager.ItemTransformation;

public class MyCarouselZoomPostLayoutListener implements CarouselLayoutManager.PostLayoutListener {
    float spacingScale;

    public MyCarouselZoomPostLayoutListener(float spacingScale) {
        this.spacingScale = spacingScale;
    }

    @Override
    public ItemTransformation transformChild(@NonNull final View child, final float itemPositionToCenterDiff, final int orientation) {
        final float scale = (float) (2 * (2 * -StrictMath.atan(Math.abs(itemPositionToCenterDiff) + 1.0) / Math.PI + 1));

        // because scaling will make view smaller in its center, then we should move this item to the top or bottom to make it visible
        final float translateY;
        final float translateX;
        if (CarouselLayoutManager.VERTICAL == orientation) {
            final float translateYGeneral = child.getMeasuredHeight() * (1 - scale) / spacingScale;
            translateY = Math.signum(itemPositionToCenterDiff) * translateYGeneral;
            translateX = 0;
        } else {
            final float translateXGeneral = child.getMeasuredWidth() * (1 - scale) / spacingScale;
            translateX = Math.signum(itemPositionToCenterDiff) * translateXGeneral;
            translateY = 0;
        }

        return new ItemTransformation(scale, scale, translateX, translateY);
    }
}
