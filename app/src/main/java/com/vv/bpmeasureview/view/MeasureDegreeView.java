package com.vv.bpmeasureview.view;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.AttributeSet;
import android.view.View;

import com.vv.bpmeasureview.R;

/**
 * Created by ShenZhenWei on 18/3/28.
 */

public class MeasureDegreeView extends View {

    public int degree = 55;
    public Bitmap bitmapdegree;

    private int width = 0;
    private int height = 0;

    private Paint paint;
    private float scaleX;
    private float scaleY;
    private float rotateX;
    private float rotateY;

    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case 0:
                    Bundle bundle = (Bundle) msg.obj;
                    degree = bundle.getInt("angle");
                    if (degree > 65) {
                        degree = 65;
                    }
                    invalidate();
                    break;
                default:
                    break;
            }
        }
    };


    public MeasureDegreeView(Context context) {
        super(context);
        init();
    }

    public MeasureDegreeView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    private void init() {
        //构建paint时,直接就是抗锯齿
        paint = new Paint(Paint.ANTI_ALIAS_FLAG);
        bitmapdegree = BitmapFactory.decodeResource(getResources(), R.mipmap.measure_degree);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        int widthMode = MeasureSpec.getMode(widthMeasureSpec);
        int widthSize = MeasureSpec.getSize(widthMeasureSpec);
        int heightMode = MeasureSpec.getMode(heightMeasureSpec);
        int heightSize = MeasureSpec.getSize(heightMeasureSpec);
        if (widthMode == MeasureSpec.EXACTLY) {
            width = widthSize;
        }
        if (heightMode == MeasureSpec.EXACTLY) {
            height = heightSize;
        }

        int m = width * bitmapdegree.getHeight();
        int n = bitmapdegree.getWidth() * bitmapdegree.getWidth();
        float a = (float) m / n;
        if (width != 0 && height != 0) {
            /**
             * 算法 scaleX/scaleY=bitmapdegree.getWidth()/bitmapdegree.getHeight()
             *     scaleX=width/bitmapdegree.getWidth()
             *     scaleY=width*bitmapdegree.getHeight()/bitmapdegree.getWidth()*bitmapdegree.getWidth()
             */
            scaleX = (float) width / bitmapdegree.getWidth();
            scaleY = a;
            /**
             * 算法:bimap的宽度是614px,旋转中心点的坐标在181处,所以rotateX = bitmapdegree.getWidth() * 181 /  614
             *     bitmap的高度是616px,旋转中心的y左边在514出,所以ratateY=bitmapdegree.getHeight() * 514 / 616;
             */
            rotateX = bitmapdegree.getWidth() * 186 /  614;
            rotateY = bitmapdegree.getHeight() * 514 / 616;
            setMeasuredDimension(width, height);
        }
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        if (bitmapdegree!=null){
            drawDegree(canvas);
        }
    }
    private void drawDegree(Canvas canvas) {
        canvas.save();
        canvas.scale(scaleX,scaleY);
        canvas.rotate(-(degree+35-90) ,rotateX,rotateY);
        canvas.drawBitmap(bitmapdegree,0,0,paint);
        canvas.restore();
    }
    public void getAngle(int angle){
        Message msg=new Message();
        Bundle bundle=new Bundle();
        bundle.putInt("angle",angle);
        msg.what=0;
        msg.obj=bundle;
        handler.sendMessage(msg);
    }

    @Override
    public void destroyDrawingCache() {
        super.destroyDrawingCache();
        if (this.bitmapdegree!=null){
            if (this.bitmapdegree!=null && !bitmapdegree.isRecycled()){
                this.bitmapdegree.recycle();
            }
            this.bitmapdegree=null;
        }

    }
}
