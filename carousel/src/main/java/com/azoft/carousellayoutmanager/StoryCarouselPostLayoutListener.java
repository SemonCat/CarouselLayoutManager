package com.azoft.carousellayoutmanager;

import android.support.annotation.NonNull;
import android.util.Log;
import android.view.View;

/**
 * Created by semoncat on 2017/1/20.
 */

public class StoryCarouselPostLayoutListener implements CarouselLayoutManager.PostLayoutListener {

    private int mCircleOffset = 100;
    private float mDegToRad = 1.0f / 180.0f * (float) Math.PI;
    private float mScalingRatio = 0.001f;
    private float mTranslationRatio = 0.2f;

    @Override
    public ItemTransformation transformChild(@NonNull final View child, final float itemPositionToCenterDiff, final int orientation) {
        final float scale = (float) ((2 * (2 * -StrictMath.atan(Math.abs(itemPositionToCenterDiff) + 1.0) / Math.PI + 1)));

        // because scaling will make view smaller in its center, then we should move this item to the top or bottom to make it visible
        final float translateY;
        final float translateX;
        if (CarouselLayoutManager.VERTICAL == orientation) {
            final float translateYGeneral = child.getMeasuredHeight() * (1 - scale) / 2f;
            translateY = Math.signum(itemPositionToCenterDiff) * translateYGeneral;
            translateX = 0;
        } else {
            final float translateXGeneral = child.getMeasuredWidth() * (1 - scale) / 2f;
            translateX = Math.signum(itemPositionToCenterDiff) * translateXGeneral;

            translateY = 0;
        }

        return new ItemTransformation(scale / 2 , scale / 2 , translateX, translateY);

        /*
        if (itemPositionToCenterDiff == 1) {
            return new ItemTransformation(1 , 1 , -375, 0);
        }

        return new ItemTransformation(1 , 1 , 0, 0);
        */
    }
}