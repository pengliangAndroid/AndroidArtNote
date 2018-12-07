package com.funny.androidartnote.chapter3;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewConfiguration;
import android.widget.Scroller;

/**
 * @author pengl
 */
public class DragView extends View {
    Scroller scroller;

    Paint paint;

    int lastX,lastY;
    int touchSlop;


    public DragView(Context context) {
        this(context, null);
    }

    public DragView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, -1);
    }

    public DragView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        scroller = new Scroller(context);

        touchSlop = ViewConfiguration.get(context).getScaledTouchSlop();

        paint = new Paint();
        paint.setAntiAlias(true);
        paint.setColor(Color.BLUE);
        paint.setStyle(Paint.Style.FILL);
    }

    @Override
    public void computeScroll() {
        super.computeScroll();
        if(scroller.computeScrollOffset()){
            scrollTo(scroller.getCurrX(),scroller.getCurrY());
            postInvalidate();
        }
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        Rect rect = new Rect(lastX,lastY,lastX + 80 ,lastY + 80);
        canvas.drawRect(rect,paint);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        int x = (int) event.getX();
        int y = (int) event.getY();
        switch (event.getAction()){
            case MotionEvent.ACTION_DOWN:
                lastX = x;
                lastY = y;
                break;
            case MotionEvent.ACTION_MOVE:

                break;
            case MotionEvent.ACTION_UP:
                /*int deltaX = x - lastX;
                int deltaY = y - lastY;

                if(deltaX < touchSlop && deltaY < touchSlop)
                    break;

                if(!scroller.isFinished()) {
                    scroller.abortAnimation();
                }

                scroller.startScroll(lastX, lastY,  deltaX, deltaY);
                invalidate();*/
                break;
        }

        lastX = x;
        lastY = y;
        invalidate();

        return true;
    }
}
