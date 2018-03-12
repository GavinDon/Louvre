package com.stxx.louvre.utils;

import android.content.Context;
import android.content.res.ColorStateList;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.GradientDrawable;
import android.graphics.drawable.RippleDrawable;
import android.graphics.drawable.StateListDrawable;
import android.os.Build;
import android.view.View;

import static android.graphics.drawable.GradientDrawable.RECTANGLE;

/**
 * description:
 * Created by liNan on 2018/3/8 9:05
 */

public class DrawableHelper {
    private StateListDrawable stateListDrawable;
    private GradientDrawable normalGradientDrawable;
    private GradientDrawable selectedGradientDrawable;
    private Builder builder;

    private DrawableHelper(Builder builder) {
        this.builder = builder;
        initDrawable();
    }

    private void initDrawable() {
        normalGradientDrawable = new GradientDrawable();
        selectedGradientDrawable = new GradientDrawable();
        stateListDrawable = new StateListDrawable();
        normalGradientDrawable.setShape(builder.normalShape);
        normalGradientDrawable.setColor(builder.normalSolidColor);
        selectedGradientDrawable.setShape(builder.selectedShape);
        selectedGradientDrawable.setColor(builder.selectedSolidColor);
    }


    public void bindView(View view) {
        bindView(view, true);
    }

    public void bindView(View view, boolean clickable) {
        Drawable drawable = generateDrawable(view.getContext());
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
            view.setBackground(drawable);
        } else {
            view.setBackgroundDrawable(drawable);
        }
        view.setClickable(clickable);
    }

    public Drawable generateBitmapDrawable(Context context, int... resId) {
        if (resId == null || resId.length <= 0) {
            return stateListDrawable;
        }
        int normalResId = 0, selectedResId = 0;
        if (resId.length == 1) {
            normalResId = resId[0];
            selectedResId = resId[0];
        } else if (resId.length == 1) {
            selectedResId = resId[1];
        }
        BitmapDrawable drawableSelected = new BitmapDrawable(context.getResources(), BitmapFactory.decodeResource(context.getResources(), selectedResId));
        stateListDrawable.addState(new int[]{android.R.attr.state_pressed}, drawableSelected);
        stateListDrawable.addState(new int[]{android.R.attr.state_selected}, drawableSelected);
        stateListDrawable.addState(new int[]{android.R.attr.state_checkable}, drawableSelected);

        BitmapDrawable normalDrawable = new BitmapDrawable(context.getResources(), BitmapFactory.decodeResource(context.getResources(), normalResId));
        stateListDrawable.addState(new int[]{}, normalDrawable);
        return stateListDrawable;
    }

    public Drawable generateDrawable(Context context) {
        cornerRadii(context, normalGradientDrawable, builder.normalTopLeftRadius, builder.normalTopRightRadius, builder.normalBottomLeftRadius, builder.normalBottomRightRadius);
        cornerRadii(context, selectedGradientDrawable, builder.selectedTopLeftRadius, builder.selectedTopRightRadius, builder.selectedBottomLeftRadius, builder.selectedBottomRightRadius);
        normalGradientDrawable.setStroke((int) dp2px(context, builder.normalStrokeWidth), builder.normalStrokeColor);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            selectedGradientDrawable.setStroke((int) dp2px(context, builder.selectedStrokeWidth), Color.parseColor("#00000000"));
            RippleDrawable rippleDrawable = new RippleDrawable(ColorStateList.valueOf(builder.selectedSolidColor), normalGradientDrawable, selectedGradientDrawable);
            return rippleDrawable;
        }
        stateListDrawable.addState(new int[]{android.R.attr.state_pressed}, selectedGradientDrawable);
        stateListDrawable.addState(new int[]{android.R.attr.state_selected}, selectedGradientDrawable);
        stateListDrawable.addState(new int[]{android.R.attr.state_checkable}, selectedGradientDrawable);
        stateListDrawable.addState(new int[]{}, normalGradientDrawable);
        return stateListDrawable;
    }


    private void cornerRadii(Context context, GradientDrawable gradientDrawable, float topLeftRadius, float topRightRadius, float bottomRightRadius, float bottomLeftRadius) {
        topLeftRadius = dp2px(context, topLeftRadius);
        topRightRadius = dp2px(context, topRightRadius);
        bottomLeftRadius = dp2px(context, bottomLeftRadius);
        bottomRightRadius = dp2px(context, bottomRightRadius);
        gradientDrawable.setCornerRadii(new float[]{topLeftRadius, topLeftRadius, topRightRadius, topRightRadius, bottomRightRadius, bottomRightRadius, bottomLeftRadius, bottomLeftRadius});
    }


    private float dp2px(Context context, float dp) {
        if (context == null) {
            return -1;
        }
        return context.getResources().getDisplayMetrics().density;
    }

    public static class Builder {
        private static float noDefaultSize = -1;
        private static Builder instance;

        private int normalShape = RECTANGLE;
        private int selectedShape = RECTANGLE;

        private int normalSolidColor;
        private int selectedSolidColor;

        private float normalRadius = noDefaultSize;
        private float normalBottomRightRadius = noDefaultSize;
        private float normalBottomLeftRadius = noDefaultSize;
        private float normalTopLeftRadius = noDefaultSize;
        private float normalTopRightRadius = noDefaultSize;

        private float selectedRadius = noDefaultSize;
        private float selectedBottomRightRadius = noDefaultSize;
        private float selectedBottomLeftRadius = noDefaultSize;
        private float selectedTopLeftRadius = noDefaultSize;
        private float selectedTopRightRadius = noDefaultSize;

        private int normalStrokeColor;
        private float normalStrokeWidth = noDefaultSize;
        private int selectedStrokeColor;
        private float selectedStrokeWidth = noDefaultSize;

        private Builder() {
        }

        public static Builder getInstance() {
            instance = new Builder();
            return instance;
        }

        public Builder shape(int... shape) {
            if (shape == null || shape.length <= 0) {
                return this;
            }
            normalShape = shape[0];
            selectedShape = shape[shape.length == 1 ? 0 : 1];
            return this;
        }

        public Builder solidColor(int... color) {
            if (color == null || color.length <= 0) {
                return this;
            }
            normalSolidColor = color[0];
            selectedSolidColor = color[color.length == 1 ? 0 : 1];
            return this;
        }

        public Builder solidColor(String... color) {
            if (color == null || color.length <= 0) {
                return this;
            }
            int array[] = new int[color.length];
            for (int i = 0; i < color.length; i++) {
                array[i] = Color.parseColor(color[i]);
            }
            solidColor(array);
            return this;
        }

        public Builder strokeColor(int... color) {
            if (color == null || color.length <= 0) {
                return this;
            }
            normalStrokeColor = color[0];
            selectedStrokeColor = color[color.length == 1 ? 0 : 1];
            return this;
        }

        public Builder strokeColor(String... color) {
            if (color == null || color.length <= 0) {
                return this;
            }
            int array[] = new int[color.length];
            for (int i = 0; i < color.length; i++) {
                array[i] = Color.parseColor(color[i]);
            }
            strokeColor(array);
            return this;
        }

        public Builder strokeWidth(float... width) {
            if (width == null || width.length <= 0) {
                return this;
            }
            normalStrokeWidth = width[0];
            selectedStrokeWidth = width[width.length == 1 ? 0 : 1];
            return this;
        }

        public Builder bottomLeftRadius(float... radius) {
            if (radius == null || radius.length <= 0) {
                return this;
            }
            normalBottomLeftRadius = radius[0];
            selectedBottomLeftRadius = radius[radius.length == 1 ? 0 : 1];
            return this;
        }

        public Builder bottomRightRadius(float... radius) {
            if (radius == null || radius.length <= 0) {
                return this;
            }
            normalBottomRightRadius = radius[0];
            selectedBottomRightRadius = radius[radius.length == 1 ? 0 : 1];
            return this;
        }

        public Builder topLeftRadius(float... radius) {
            if (radius == null || radius.length <= 0) {
                return this;
            }
            normalTopLeftRadius = radius[0];
            selectedTopLeftRadius = radius[radius.length == 1 ? 0 : 1];
            return this;
        }

        public Builder topRightRadius(float... radius) {
            if (radius == null || radius.length <= 0) {
                return this;
            }
            normalTopRightRadius = radius[0];
            selectedTopRightRadius = radius[radius.length == 1 ? 0 : 1];
            return this;
        }

        public Builder radius(float... radius) {
            if (radius == null || radius.length <= 0) {
                return this;
            }
            normalRadius = radius[0];
            selectedRadius = radius[radius.length == 1 ? 0 : 1];
            return this;
        }


        public DrawableHelper build() {

            if (normalRadius > 0) {
                normalBottomLeftRadius = normalRadius;
                normalBottomRightRadius = normalRadius;
                normalTopLeftRadius = normalRadius;
                normalTopRightRadius = normalRadius;
            }

            if (selectedRadius > 0) {
                selectedBottomLeftRadius = selectedRadius;
                selectedBottomRightRadius = selectedRadius;
                selectedTopLeftRadius = selectedRadius;
                selectedTopRightRadius = selectedRadius;
            }

            return new DrawableHelper(this);
        }
    }
}
