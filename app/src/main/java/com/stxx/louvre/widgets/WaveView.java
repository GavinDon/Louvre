package com.stxx.louvre.widgets;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;

/**
 * description: 仿百度个人中心效果
 * Created by liNan on 2018/2/9 14:31
 */

public class WaveView extends View {
    private Paint mAbovePaint;
    private Path mAbovePath;
    private Paint mBelowPaint;
    private Path mBelowPath;

    private float φ;

    private OnWaveInterface mOnWaveInterface;


    public WaveView(Context context) {
        this(context, null);
    }

    public WaveView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public WaveView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initViews();
    }


    private void initViews() {
        mAbovePath = new Path();
        mBelowPath = new Path();
        mAbovePaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mBelowPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mAbovePaint.setDither(true);
        mAbovePaint.setAntiAlias(true);
        mAbovePaint.setColor(Color.WHITE);
        mAbovePaint.setStyle(Paint.Style.FILL);
        mBelowPaint.setDither(true);
        mBelowPaint.setAntiAlias(true);
        mBelowPaint.setColor(Color.WHITE);
        mBelowPaint.setStyle(Paint.Style.FILL);
        mBelowPaint.setAlpha(80);

    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        mAbovePath.reset();
        mBelowPath.reset();
        mAbovePath.moveTo(getLeft(), getBottom());
        mBelowPath.moveTo(getLeft(), getBottom() + 240);
        double ω = 2 * Math.PI / getWidth();//ω 为角频率 公式：2π/<></>
        φ -= 0.1f;
        //y=Asin(ωx+φ)+k   k、ω和φ是常数  (ωx+φ)——相位，反映变量y所处的状态。
        for (int i = 0; i <=getWidth(); i ++) {
            float aboveY = (float) (16 * Math.cos(ω * i + φ) + 12);
            float belowY = (float) (16 * Math.sin(ω * i + φ));
            mAbovePath.lineTo(i, aboveY); //画出余弦曲线
            mBelowPath.lineTo(i, belowY); //画出正弦曲线
            if (mOnWaveInterface!=null){
                mOnWaveInterface.onWaveAnimation(aboveY);
            }
        }
        mAbovePath.lineTo(getRight(), getBottom());
        mBelowPath.lineTo(getRight(), getBottom());
        canvas.drawPath(mAbovePath, mAbovePaint);
        canvas.drawPath(mBelowPath, mBelowPaint);
        postInvalidateDelayed(20);

    }

    public void setOnWaveInterface(OnWaveInterface l) {
        this.mOnWaveInterface = l;

    }

    public interface OnWaveInterface {
        void onWaveAnimation(float y);

    }

}
