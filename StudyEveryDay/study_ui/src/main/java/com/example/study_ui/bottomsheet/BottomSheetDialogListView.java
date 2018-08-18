package com.example.study_ui.bottomsheet;

import android.annotation.SuppressLint;
import android.content.Context;
import android.support.design.widget.CoordinatorLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ListView;

/**
 * Created by lgh on 18-4-9.
 * <p>
 * BottomSheetDialogListView
 * 1，能够在 BottomSheetDialog 分展开的情况下，滑动显示完整数据
 * 2，能够像网易云音乐一样，上滑的时候，避免触发 expend 状态
 */

public class BottomSheetDialogListView extends RecyclerView {

    private float downY;
    private float moveY;
    public boolean isOverScroll = false;
    private CoordinatorLayout bottomCoordinator;

    public BottomSheetDialogListView(Context context) {
        super(context);
        init();
    }

    public BottomSheetDialogListView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public BottomSheetDialogListView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    @SuppressWarnings("all")
    private void init() {
        // todo
    }

    public void setCoordinatorDisallow() {
        if (bottomCoordinator == null)
            return;
        bottomCoordinator.requestDisallowInterceptTouchEvent(true);
    }

    /**
     * 绑定需要被拦截 intercept 的 CoordinatorLayout
     *
     * @param contentView View
     */
    public void bindBottomSheetDialog(View contentView) {
        // try throw illegal
        try {
            FrameLayout parentOne = (FrameLayout) contentView.getParent();
            this.bottomCoordinator = (CoordinatorLayout) parentOne.getParent();
            setOnTouchListener(
                    new OnTouchListener() {
                        @SuppressLint("ClickableViewAccessibility")
                        @Override
                        public boolean onTouch(View v, MotionEvent event) {
                            if (bottomCoordinator == null)
                                return false;
                            LinearLayoutManager layoutManager = (LinearLayoutManager) getLayoutManager();
                            int firstVisiblePos = layoutManager.findFirstCompletelyVisibleItemPosition();
                            switch (event.getAction()) {
                                case MotionEvent.ACTION_DOWN:
                                    downY = event.getRawY();
                                    bottomCoordinator.requestDisallowInterceptTouchEvent(true);
                                    break;
                                case MotionEvent.ACTION_MOVE:
                                    moveY = event.getRawY();
                                    if ((moveY - downY) > 10) {
                                        if (firstVisiblePos == 0 && isOverScroll && getChildAt(0).getTop() >= getPaddingTop()) {
                                            bottomCoordinator.requestDisallowInterceptTouchEvent(false);
                                            break;
                                        }
                                    }
                                    bottomCoordinator.requestDisallowInterceptTouchEvent(true);
                                    break;
                                case MotionEvent.ACTION_UP:
                                    break;
                            }
                            return false;
                        }
                    }
            );
        } catch (Exception e) {
            // maybe 可能是强转异常
            // todo
        }
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        if (bottomCoordinator == null) {
            super.onMeasure(widthMeasureSpec, heightMeasureSpec);
            return;
        }
        int size = (int) ((float) (getResources().getDisplayMetrics().heightPixels * 0.618));
        @SuppressLint("WrongConstant") int newHeightSpec = MeasureSpec.makeMeasureSpec(
                size, MeasureSpec.EXACTLY// mode
        );
        super.onMeasure(widthMeasureSpec, newHeightSpec);
    }

    @Override
    protected void onOverScrolled(int scrollX, int scrollY, boolean clampedX, boolean clampedY) {
        super.onOverScrolled(scrollX, scrollY, clampedX, clampedY);
        isOverScroll = clampedY;
    }

    @Override
    protected void onScrollChanged(int l, int t, int oldl, int oldt) {
        super.onScrollChanged(l, t, oldl, oldt);

    }

}

















