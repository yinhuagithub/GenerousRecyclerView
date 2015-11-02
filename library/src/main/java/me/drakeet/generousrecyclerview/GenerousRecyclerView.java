package me.drakeet.generousrecyclerview;

import android.content.Context;
import android.os.Build;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.view.MotionEvent;

/**
 * todo: boolean test = attrs.getAttributeBooleanValue("http://schemas.android.com/apk/res/android",
 * "clipToPadding", true);
 * Created by drakeet(http://drakeet.me)
 * Date: 15/10/23 12:11
 */
public class GenerousRecyclerView extends RecyclerView {

    public int mScrollY = 0;
    private boolean mEating = false;


    public GenerousRecyclerView(Context context) {
        this(context, null);
    }


    public GenerousRecyclerView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }


    public GenerousRecyclerView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        addOnScrollListener(new OnScrollListener() {
            @Override public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                switch (newState) {
                    case SCROLL_STATE_IDLE:
                        mEating = false;
                        break;
                    case SCROLL_STATE_DRAGGING:
                        mEating = true;
                        break;
                }
            }


            @Override public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                mScrollY += dy;
            }
        });
    }


    @Override public boolean dispatchTouchEvent(MotionEvent ev) {
        if (!mEating && !getClipToPaddingCompat() && ev.getY() + mScrollY < getPaddingTop()
                && ev.getY() < getPaddingTop()) {
            return false;
        }
        else {
            return super.dispatchTouchEvent(ev);
        }
    }


    /**
     * @return false, if sdk_int < 21. else return getClipToPadding();
     */
    private boolean getClipToPaddingCompat() {
        if (Build.VERSION.SDK_INT < 21) {
            return getLayoutManager() != null && getLayoutManager().getClipToPadding();
        }
        else {
            return getClipToPadding();
        }
    }
}
