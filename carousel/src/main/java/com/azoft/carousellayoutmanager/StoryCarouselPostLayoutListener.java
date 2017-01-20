package com.azoft.carousellayoutmanager;

import android.support.annotation.NonNull;
import android.util.Log;
import android.view.View;

public class StoryCarouselPostLayoutListener implements CarouselLayoutManager.PostLayoutListener {

    private static final double RAW_DEGREE = 20;

    private static final double MIN_SCALE = 0.35;

    private static final double MAX_SCALE = 1;

    private int mMaxVisibleItems;

    public void setMaxVisibleItems(int maxVisibleItems) {
        mMaxVisibleItems = maxVisibleItems;
    }

    @Override
    public ItemTransformation transformChild(@NonNull final View child, final float itemPositionToCenterDiff, final int orientation) {
        //final float scale = (float) ((2 * (2 * -StrictMath.atan(Math.abs(itemPositionToCenterDiff) + 1.0) / Math.PI + 1)));

        float scale = 0;

        // because scaling will make view smaller in its center, then we should move this item to the top or bottom to make it visible
        final float translateY;
        final float translateX;
        if (CarouselLayoutManager.VERTICAL == orientation) {
            final float translateYGeneral = child.getMeasuredHeight() * (1 - scale) / 2f;
            translateY = Math.signum(itemPositionToCenterDiff) * translateYGeneral;
            translateX = 0;
        } else {

            float parentWidth = ((View) child.getParent()).getWidth();
            float radius = parentWidth / 2;

            float gapDegree = 90 / (mMaxVisibleItems +1) ;

            translateX = ((float) (radius * Math.sin(Math.toRadians(itemPositionToCenterDiff * gapDegree))));

            float rawRadius = ((float) (radius * Math.cos(Math.toRadians(itemPositionToCenterDiff * gapDegree))));
                    
            translateY = ((float) (rawRadius * Math.sin(Math.toRadians(RAW_DEGREE)))) - (child.getMeasuredHeight() / 2);
            
            float childRawZ = ((float) (rawRadius * Math.cos(Math.toRadians(RAW_DEGREE))));
            
            float centerRawZ = ((float) (radius * Math.cos(Math.toRadians(RAW_DEGREE))));

            scale = ((float) ((MAX_SCALE - MIN_SCALE) * childRawZ / centerRawZ + MIN_SCALE));
        }

        return new ItemTransformation(scale , scale , translateX, translateY);
    }
}