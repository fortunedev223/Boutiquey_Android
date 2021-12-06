package webkul.opencart.mobikul.helper;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.support.annotation.Px;
import android.util.AttributeSet;

import org.jetbrains.annotations.Nullable;

import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by manish.choudhary on 23/10/17.
 */

public class MyImageView extends CircleImageView {
    private String strokeColor;
    private int width;
    public MyImageView(Context context) {
        super(context);
    }

    public MyImageView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public MyImageView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        Paint stroke=new Paint();
        stroke.setFlags(Paint.ANTI_ALIAS_FLAG);
        stroke.setColor(Color.parseColor(strokeColor));
        int h=this.getHeight();
        int w=this.getWidth();
        int diameter=((h>w)?h:w);
        int r=diameter/2;
        canvas.drawCircle(diameter/2,diameter/2,r-width,stroke);
        super.onDraw(canvas);
    }


    public void setStrokeColor(String strokeColor) {
        this.strokeColor = strokeColor;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    @Override
    public void setPadding(@Px int left, @Px int top, @Px int right, @Px int bottom) {
        super.setPadding(left, top, right, bottom);
    }
}
