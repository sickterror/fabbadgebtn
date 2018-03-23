package com.timelesssoftware.fabbadge;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.content.Context;
import android.content.res.ColorStateList;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.graphics.drawable.Drawable;

import android.support.design.widget.FloatingActionButton;
import android.support.v4.view.animation.FastOutLinearInInterpolator;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;


/**
 * Created by Luka on 9.3.2018.
 */

public class FabBadge extends RelativeLayout {

    private int fabBtnIconTint;
    private int fabBadgeTextColor;
    private int fabBadgeColor;
    private Drawable fabBtnDrawable;
    private int fabBackgroundColor;


    FloatingActionButton fabBtn;
    TextView badge;

    private int mAnimState;

    static final int ANIM_STATE_NONE = 0;
    static final int ANIM_STATE_HIDING = 1;
    static final int ANIM_STATE_SHOWING = 2;

    private boolean isBadgeShown = false;

    //FabBadge self;

    public FabBadge(Context context) {
        super(context);
    }


    public FabBadge(Context context, AttributeSet attrs) {
        super(context, attrs);

        TypedArray a = context.getTheme().obtainStyledAttributes(
                attrs,
                R.styleable.FabBadge,
                0, 0);

        try {
            fabBackgroundColor = a.getColor(R.styleable.FabBadge_add_cart_fab_backgroundTint, Color.WHITE);
            fabBtnDrawable = a.getDrawable(R.styleable.FabBadge_add_cart_fab_icon);
            fabBtnIconTint = a.getColor(R.styleable.FabBadge_add_cart_fab_IconTint, Color.WHITE);
            fabBadgeColor = a.getColor(R.styleable.FabBadge_add_cart_fab_badge_backgroundTint, Color.WHITE);
            fabBadgeTextColor = a.getColor(R.styleable.FabBadge_add_cart_fab_badge_textColor, Color.BLACK);
        } catch (Exception e) {
            a.recycle();
        }
        a.recycle();
        init();
    }

    public FabBadge(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public void init() {
        inflate(getContext(), R.layout.fabBadgeBtn_layout, this);
        fabBtn.setImageDrawable(fabBtnDrawable);
        fabBtn.setImageTintList(ColorStateList.valueOf(fabBtnIconTint));
        fabBtn.setBackgroundTintList(ColorStateList.valueOf(fabBackgroundColor));
        badge.setBackgroundTintList(ColorStateList.valueOf(fabBadgeColor));
        badge.setTextColor(fabBadgeTextColor);
        badge.setVisibility(INVISIBLE);
    }

    public void hide() {
        if (isOrWillBeHidden()) {
            // We either are or will soon be visible, skip the call
            return;
        }
        this.animate().cancel();
        mAnimState = ANIM_STATE_HIDING;
        this.animate()
                .scaleX(0f)
                .scaleY(0f)
                .alpha(0f)
                .setDuration(200)
                .setInterpolator(new FastOutLinearInInterpolator())
                .setListener(new AnimatorListenerAdapter() {
                    private boolean mCancelled;

                    @Override
                    public void onAnimationStart(Animator animation) {
                        setVisibility(View.VISIBLE);
                        mCancelled = false;
                    }

                    @Override
                    public void onAnimationCancel(Animator animation) {
                        mCancelled = true;
                    }

                    @Override
                    public void onAnimationEnd(Animator animation) {
                        setVisibility(INVISIBLE);
                    }
                });
    }

    public void show() {
        if (isOrWillBeShown()) {
            // We either are or will soon be visible, skip the call
            return;
        }
        this.animate().cancel();
        mAnimState = ANIM_STATE_SHOWING;
        this.animate()
                .scaleX(1f)
                .scaleY(1f)
                .alpha(1f)
                .setDuration(200)
                .setInterpolator(new FastOutLinearInInterpolator())
                .setListener(new AnimatorListenerAdapter() {
                    private boolean mCancelled;

                    @Override
                    public void onAnimationStart(Animator animation) {
                        mCancelled = false;
                        setVisibility(VISIBLE);
                    }

                    @Override
                    public void onAnimationCancel(Animator animation) {
                        mCancelled = true;
                    }

                    @Override
                    public void onAnimationEnd(Animator animation) {

                    }
                });
    }

    boolean isOrWillBeShown() {
        if (getVisibility() != View.VISIBLE) {
            // If we not currently visible, return true if we're animating to be shown
            return mAnimState == ANIM_STATE_SHOWING;
        } else {
            // Otherwise if we're visible, return true if we're not animating to be hidden
            return mAnimState != ANIM_STATE_HIDING;
        }
    }

    boolean isOrWillBeHidden() {
        if (getVisibility() == View.VISIBLE) {
            // If we currently visible, return true if we're animating to be hidden
            return mAnimState == ANIM_STATE_HIDING;
        } else {
            // Otherwise if we're not visible, return true if we're not animating to be shown
            return mAnimState != ANIM_STATE_SHOWING;
        }
    }

    public void setBadgeNumber(int count) {
        if (count == 0) {
            hideBadge(false);
            return;
        }
        badge.setText(String.valueOf(count));
        showBadge(false);
    }

    public void setBadgeNumberAnimate(int count) {
        if (count == 0) {
            hideBadge(true);
            return;
        }
        badge.setText(String.valueOf(count));
        showBadge(true);
    }

    private void hideBadge(boolean animate) {
        isBadgeShown = false;
        if (animate)
            badge.animate().scaleX(0f)
                    .scaleY(0f)
                    .alpha(0f)
                    .setDuration(200);
    }

    private void showBadge(boolean animate) {
        isBadgeShown = true;
        badge.setVisibility(VISIBLE);
        if (animate)
            badge.animate().scaleX(1f)
                    .scaleY(1f)
                    .alpha(1f)
                    .setDuration(200);
    }
}
