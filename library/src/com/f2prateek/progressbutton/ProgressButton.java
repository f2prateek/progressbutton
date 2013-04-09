/*
 * Copyright 2013 Prateek Srivastava (@f2prateek)
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * 	http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.f2prateek.progressbutton;

import android.content.Context;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.drawable.Drawable;
import android.os.Parcel;
import android.os.Parcelable;
import android.util.AttributeSet;
import android.widget.CompoundButton;

public class ProgressButton extends CompoundButton {
    private int mMax;
    private int mProgress;
    private Drawable mShadowDrawable;
    private Drawable mUnpinnedDrawable;
    private Drawable mPinnedDrawable;
    private Paint mCirclePaint;
    private Paint mProgressPaint;
    private Rect mTempRect = new Rect();
    private RectF mTempRectF = new RectF();
    private int mDrawableSize;
    private int mInnerSize;

    public ProgressButton(Context context) {
        this(context, null);
    }

    public ProgressButton(Context context, AttributeSet attrs) {
        this(context, attrs, R.attr.progressButtonStyle);
    }

    public ProgressButton(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init(context, attrs, defStyle);
    }

    private void init(Context context, AttributeSet attrs, int defStyle) {
        // Attribute initialization
        final TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.ProgressButton,
                defStyle, 0);
        final Resources res = getResources();

        mProgress = a.getInteger(R.styleable.ProgressButton_progress, 0);
        mMax = a.getInteger(R.styleable.ProgressButton_max, 100);

        int circleColor = res.getColor(R.color.progress_default_circle_color);
        circleColor = a.getColor(R.styleable.ProgressButton_circleColor, circleColor);
        int progressColor = res.getColor(R.color.progress_default_progress_color);
        progressColor = a.getColor(R.styleable.ProgressButton_progressColor, progressColor);

        int pinnedDrawable = a.getResourceId(R.styleable.ProgressButton_pinnedDrawable,
                R.drawable.pin_progress_pinned);
        mPinnedDrawable = res.getDrawable(pinnedDrawable);
        mPinnedDrawable.setCallback(this);

        int unpinnedDrawable = a.getResourceId(R.styleable.ProgressButton_unpinnedDrawable,
                R.drawable.pin_progress_unpinned);
        mUnpinnedDrawable = res.getDrawable(unpinnedDrawable);
        mUnpinnedDrawable.setCallback(this);

        int shadowDrawable = a.getResourceId(R.styleable.ProgressButton_shadowDrawable,
                R.drawable.pin_progress_shadow);
        mShadowDrawable = res.getDrawable(shadowDrawable);
        mShadowDrawable.setCallback(this);

        mInnerSize = a.getDimensionPixelSize(R.styleable.ProgressButton_innerSize, mInnerSize);

        mInnerSize = res.getDimensionPixelSize(R.dimen.progress_inner_size);
        setChecked(a.getBoolean(R.styleable.ProgressButton_pinned, false));
        setClickable(a.getBoolean(R.styleable.ProgressButton_android_clickable, false));
        setFocusable(a.getBoolean(R.styleable.ProgressButton_android_focusable, false));
        setBackground(a.getDrawable(R.styleable.ProgressButton_android_selectableItemBackground));

        a.recycle();

        mDrawableSize = mShadowDrawable.getIntrinsicWidth();

        mCirclePaint = new Paint();
        mCirclePaint.setColor(circleColor);
        mCirclePaint.setAntiAlias(true);

        mProgressPaint = new Paint();
        mProgressPaint.setColor(progressColor);
        mProgressPaint.setAntiAlias(true);
    }

    /**
     * Returns the maximum progress value.
     */
    public int getMax() {
        return mMax;
    }

    /**
     * Sets the maximum progress value. Defaults to 100.
     */
    public void setMax(int max) {
        mMax = max;
        invalidate();
    }

    /**
     * Returns the current progress from 0 to max.
     */
    public int getProgress() {
        return mProgress;
    }

    /**
     * Sets the current progress (must be between 0 and max).
     *
     * @see #setMax(int)
     */
    public void setProgress(int progress) {
        if (progress > mMax || progress < 0) {
            throw new IllegalArgumentException(
                    String.format("Progress (%d) must be between %d and %d", progress, 0, mMax));
        }
        mProgress = progress;
        invalidate();
    }

    public int getProgressColor() {
        return mProgressPaint.getColor();
    }

    public void setProgressColor(int progressColor) {
        mProgressPaint.setColor(progressColor);
        invalidate();
    }

    public int getCircleColor() {
        return mCirclePaint.getColor();
    }

    public void setCircleColor(int circleColor) {
        mCirclePaint.setColor(circleColor);
        invalidate();
    }

    public Drawable getPinnedDrawable() {
        return mPinnedDrawable;
    }

    public void setPinnedDrawable(Drawable pinnedDrawable) {
        mPinnedDrawable = pinnedDrawable;
        invalidate();
    }

    public Drawable getUnpinnedDrawable() {
        return mUnpinnedDrawable;
    }

    public void setUnpinnedDrawable(Drawable unpinnedDrawable) {
        mUnpinnedDrawable = unpinnedDrawable;
        invalidate();
    }

    public Drawable getShadowDrawable() {
        return mShadowDrawable;
    }

    public void setShadowDrawable(Drawable shadowDrawable) {
        mShadowDrawable = shadowDrawable;
        mDrawableSize = mShadowDrawable.getIntrinsicWidth();
        invalidate();
    }

    public int getInnerSize() {
        return mInnerSize;
    }

    public void setInnerSize(int innerSize) {
        mInnerSize = innerSize;
        invalidate();
    }

    public boolean isPinned() {
        return isChecked();
    }

    public void setPinned(boolean pinned) {
        setChecked(pinned);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        setMeasuredDimension(
                resolveSize(mDrawableSize, widthMeasureSpec),
                resolveSize(mDrawableSize, heightMeasureSpec));
    }

    @Override
    protected void drawableStateChanged() {
        super.drawableStateChanged();
        if (mPinnedDrawable.isStateful()) {
            mPinnedDrawable.setState(getDrawableState());
        }
        if (mUnpinnedDrawable.isStateful()) {
            mUnpinnedDrawable.setState(getDrawableState());
        }
        if (mShadowDrawable.isStateful()) {
            mShadowDrawable.setState(getDrawableState());
        }
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        mTempRect.set(0, 0, mDrawableSize, mDrawableSize);
        mTempRect.offset((getWidth() - mDrawableSize) / 2, (getHeight() - mDrawableSize) / 2);

        mTempRectF.set(-0.5f, -0.5f, mInnerSize + 0.5f, mInnerSize + 0.5f);
        mTempRectF.offset((getWidth() - mInnerSize) / 2, (getHeight() - mInnerSize) / 2);

        canvas.drawArc(mTempRectF, 0, 360, true, mCirclePaint);
        canvas.drawArc(mTempRectF, -90, 360 * mProgress / mMax, true, mProgressPaint);

        Drawable iconDrawable = isChecked() ? mPinnedDrawable : mUnpinnedDrawable;
        iconDrawable.setBounds(mTempRect);
        iconDrawable.draw(canvas);

        mShadowDrawable.setBounds(mTempRect);
        mShadowDrawable.draw(canvas);
    }

    @Override
    public Parcelable onSaveInstanceState() {
        Parcelable superState = super.onSaveInstanceState();
        if (isSaveEnabled()) {
            SavedState ss = new SavedState(superState);
            ss.mMax = mMax;
            ss.mProgress = mProgress;
            return ss;
        }
        return superState;
    }

    @Override
    public void onRestoreInstanceState(Parcelable state) {
        if (!(state instanceof SavedState)) {
            super.onRestoreInstanceState(state);
            return;
        }

        SavedState ss = (SavedState) state;
        super.onRestoreInstanceState(ss.getSuperState());

        mMax = ss.mMax;
        mProgress = ss.mProgress;
    }

    /**
     * A {@link android.os.Parcelable} representing the {@link ProgressButton}'s state.
     */
    public static class SavedState extends BaseSavedState {
        public static final Creator<SavedState> CREATOR = new Creator<SavedState>() {
            @Override
            public SavedState createFromParcel(Parcel parcel) {
                return new SavedState(parcel);
            }

            @Override
            public SavedState[] newArray(int size) {
                return new SavedState[size];
            }
        };
        private int mProgress;
        private int mMax;

        public SavedState(Parcelable superState) {
            super(superState);
        }

        private SavedState(Parcel in) {
            super(in);
            mProgress = in.readInt();
            mMax = in.readInt();
        }

        @Override
        public void writeToParcel(Parcel out, int flags) {
            super.writeToParcel(out, flags);
            out.writeInt(mProgress);
            out.writeInt(mMax);
        }
    }
}