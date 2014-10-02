package com.xjy.slidedownview;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Point;
import android.graphics.PointF;
import android.os.Handler;
import android.os.Message;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;

public class SlideDownView extends View {

    int width = 500, height = 100;
    PointF start,move;
    float offset = 0;
    float n = 0, currentTime = 0, beginValue = 0, changeValue = 0, duringTime = 0;
    Paint mPaint = new Paint();

    Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case 1:
                    if (offset > 0) {
                        offset -= n;
                        invalidate();
                        mHandler.sendEmptyMessageDelayed(1,100);
                    } else {
                        offset = 0;
                        n = 0;
                        invalidate();
                    }
                    break;
                case 2:
                    if (offset >= 0.1) {
                        Bounce out = Bounce.OUT;
                        offset = out.getValue(currentTime, beginValue, changeValue, duringTime);
                        Log.d("SlideDownView", "offset is :" + offset);
                        currentTime += 50;
                        invalidate();
                        mHandler.sendEmptyMessageDelayed(2, 50);
                    } else {
                        offset = 0;
                        invalidate();
                    }

                    break;
            }
        }
    };

    public SlideDownView(Context context) {
        super(context);
        mPaint.setAntiAlias(true);
        mPaint.setColor(Color.GRAY);
        mPaint.setStyle(Paint.Style.FILL_AND_STROKE);
    }

    public SlideDownView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public SlideDownView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        canvas.drawColor(Color.WHITE);
        Path mPath = new Path();

        mPath.moveTo(0, 0);
        mPath.lineTo(width, 0);
        mPath.lineTo(width, height);
//        mPath.quadTo(width/2, height+100, width/2 + 50, height+100);
        mPath.quadTo(width/2, height+offset, 0, height);
//        mPath.lineTo(100, 100);
//        mPath.cubicTo(width/2+50, height+100, width/2 -50, height+100,width/2,height+100);
//        mPath.moveTo(0, height);
//        mPath.quadTo(width/2, height+100, width/2 -50, height+100);
//        mPath.moveTo(0, height);
        mPath.lineTo(0, 0);
        canvas.drawPath(mPath, mPaint);
       /* Path path = new Path();                     //Path对象
        path.moveTo(50, 100);                           //起始点
        path.lineTo(50, 300);                           //连线到下一点
        path.lineTo(100, 500);                      //连线到下一点
        path.lineTo(400, 500);                      //连线到下一点
        path.lineTo(300, 300);                      //连线到下一点
        path.lineTo(450, 50);                           //连线到下一点
        path.lineTo(200, 200);                      //连线到下一点
        canvas.drawPath(path, mPaint);                   //绘制任意多边形*/
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                start = new PointF(event.getX(), event.getY());
//                Log.d("SlideDownView", " down !!!!");
                break;
            case MotionEvent.ACTION_MOVE:
                if (start != null) {
                    offset = event.getY() - start.y;
//                    Log.d("SlideDownView", "move offset :" + offset);
                    if (offset <= 0) {
                        offset = 0;
                    }
                    invalidate();
                }
                break;
            case MotionEvent.ACTION_UP:
                start = null;
                if (offset < 100) {
                    n = offset / 10;
                    mHandler.sendEmptyMessage(1);
                } else {
                    beginValue = offset;
                    currentTime = 0;
                    changeValue = height - offset;
                    duringTime = 1000;
                    mHandler.sendEmptyMessage(2);
                }
                break;
        }
        return true;
    }
}
