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
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class FabBadge extends RelativeLayout {


    public boolean enableAnimation = true;
    public boolean isBadgeShown = false;

    private int fabBtnIconTint;
    private int fabBadgeTextColor;
    private int fabBadgeColor;
    private Drawable fabBtnDrawable;
    private int fabBackgroundColor;
    private FloatingActionButton fabBtn;
    private TextView badge;
    private int mAnimState;
    static final int ANIM_STATE_NONE = 0;
    static final int ANIM_STATE_HIDING = 1;
    static final int ANIM_STATE_SHOWING = 2;


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
            fabBackgroundColor = a.getColor(R.styleable.FabBadge_fab_background_tint, Color.WHITE);
            fabBtnDrawable = a.getDrawable(R.styleable.FabBadge_fab_icon);
            fabBtnIconTint = a.getColor(R.styleable.FabBadge_fab_icon_tint, Color.WHITE);
            fabBadgeColor = a.getColor(R.styleable.FabBadge_fab_badge_background_tint, Color.WHITE);
            fabBadgeTextColor = a.getColor(R.styleable.FabBadge_fab_badge_textColor, Color.BLACK);
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
        inflate(getContext(), R.layout.fab_badge_btn_layout, this);
        fabBtn = findViewById(R.id.atcf_fab);
        badge = findViewById(R.id.atcf_badge);
        fabBtn.setImageDrawable(fabBtnDrawable);
        fabBtn.setImageTintList(ColorStateList.valueOf(fabBtnIconTint));
        fabBtn.setBackgroundTintList(ColorStateList.valueOf(fabBackgroundColor));
        badge.setBackgroundTintList(ColorStateList.valueOf(fabBadgeColor));
        badge.setTextColor(fabBadgeTextColor);
        badge.setVisibility(INVISIBLE);
    }

    /**
     * Hides the button
     * If animations are enabled it animates the btn
     * <p>
     * Taken from FloatingActionBtn
     */
    public void hide() {
        if (isOrWillBeHidden()) {
            // We either are or will soon be visible, skip the call
            return;
        }

        if (!enableAnimation) {
            fabBtn.setVisibility(View.VISIBLE);
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

    /**
     * Shows the button. If animations are enabled showing is animated
     * <p>
     * Taken from FloatingActionBtn
     */
    public void show() {
        if (isOrWillBeShown()) {
            // We either are or will soon be visible, skip the call
            return;
        }

        if (!enableAnimation) {
            fabBtn.setVisibility(VISIBLE);
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


    /**
     * Sets the badge number that will be show in the btn
     * It's recommended that max number is 99 since there is not enough space for more numbers
     *
     * @param count - setting the count to zero will hide badge
     */
    public void setBadgeNumber(int count) {
        if (count == 0) {
            hideBadge();
            return;
        }
        badge.setText(String.valueOf(count));
        showBadge(enableAnimation);
        isBadgeShown = true;
    }

    /**
     * Hides the badge
     * The number is not reset
     */
    public void hideBadge() {
        isBadgeShown = false;
        if (!enableAnimation)
            badge.animate().scaleX(0f)
                    .scaleY(0f)
                    .alpha(0f)
                    .setDuration(200);

        isBadgeShown = false;
    }

    /**
     * @param color
     */
    public void setFabBackgroundColor(int color){
        fabBtn.setBackgroundTintList(ColorStateList.valueOf(color));
    }

    /**
     *
     * @param color
     */
    public void setBadgeBackgroundColor(int color) {
        badge.setBackgroundTintList(ColorStateList.valueOf(color));
    }

    /**
     *
     * @param color
     */
    public void setBadgeTextColor(int color) {
        badge.setTextColor(color);
    }

    /**
     *
     * @param icon
     */
    public void setFabIcon(Drawable icon) {
        fabBtn.setImageDrawable(icon);
    }

    /**
     *
     * @param tint
     */
    public void setFabIconTint(int tint) {
        fabBtn.setImageTintList(ColorStateList.valueOf(tint));
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


    /**
     * TODO: animation not working gotta fix
     *
     * @param animate
     */
    private void showBadge(boolean animate) {
        isBadgeShown = true;
        if (animate) {
            badge.animate().scaleX(1f)
                    .scaleY(1f)
                    .alpha(1f)
                    .setDuration(2000);
        }
        badge.setVisibility(VISIBLE);
    }
}
