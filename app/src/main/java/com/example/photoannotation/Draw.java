package com.example.photoannotation;

import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;

import android.util.Pair;
import android.view.View;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.view.MotionEvent;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import java.security.acl.Group;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by badiparvaneh on 2/11/18.
 */


public class Draw extends View {

    private class Touch {
        public Path p;
        public int brushSize;
        public int color;

        public Touch(Path path, int size, int color) {
            this.p = path;
            this.brushSize = size;
            this.color = color;
        }
    }

    private float xCoordinate, yCoordiante;
    private Paint paint;
    private Paint bitPaint = new Paint(Paint.DITHER_FLAG);
    private Path path;
    private int brushSize;
    private int colorSel;
    private Canvas canvas;
    private Bitmap bitmap;
    private ArrayList<Touch> drawing = new ArrayList<>();
    private boolean eraseEnable = false;

    public Draw(Context c) {
        this(c, null);
    }

    public Draw(Context c, AttributeSet a) {

        super(c, a);

        paint = new Paint();
        paint.setColor(Color.BLACK);
        paint.setDither(true);
        paint.setAntiAlias(true);
        paint.setColor(Color.BLUE);
        paint.setStyle(Paint.Style.STROKE);
        paint.setStrokeCap(Paint.Cap.ROUND);
        paint.setStrokeJoin(Paint.Join.ROUND);
        paint.setXfermode(null);
        paint.setAlpha(0xff);
    }

    public void makeCanvas(DisplayMetrics metrics) {
        int h = metrics.heightPixels;
        int w = metrics.widthPixels;

        bitmap = Bitmap.createBitmap(w, h, Bitmap.Config.ARGB_8888);
        canvas = new Canvas(bitmap);

        colorSel = Color.BLACK;
        brushSize = 25; //default size is 25
    }

    public void brush() {
        eraseEnable = false;
        invalidate();
    }

    public void erase() {
        eraseEnable = true;
        if (eraseEnable)
            paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.CLEAR));
        else
            paint.setXfermode(null);
        invalidate();
    }

    public void clearAll() {
        //backgroundColor = DEFAULT_BG_COLOR;
        drawing.clear();
        invalidate();
    }

    public void sizeSelect(int size) {
        brushSize = size;
    }

    public void colorPick(int c) {
        colorSel = c;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        canvas.save();
        canvas.drawColor(Color.TRANSPARENT);

        for (Touch t : drawing) {
            paint.setColor(colorSel);
            paint.setStrokeWidth(t.brushSize);
            canvas.drawPath(t.p, paint);

        }
        canvas.drawBitmap(bitmap, 0, 0, bitPaint);
        canvas.restore();
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {

        float xVal = event.getX();
        float yVal = event.getY();

        switch(event.getAction()) {
            case MotionEvent.ACTION_UP :
                path.lineTo(xCoordinate, yCoordiante);
                invalidate();
                break;

            case MotionEvent.ACTION_DOWN :
                path = new Path();
                Touch t = new Touch(path, brushSize, colorSel);
                drawing.add(t);
                path.reset();
                path.moveTo(xVal, yVal);
                xCoordinate = xVal;
                yCoordiante = yVal;
                invalidate();
                break;

            case MotionEvent.ACTION_MOVE :
                path.quadTo(xCoordinate, yCoordiante, (xVal + xCoordinate) / 2, (yVal + yCoordiante) / 2);
                xCoordinate = xVal;
                yCoordiante = yVal;
                invalidate();
                break;

        }

        return true;
    }

}
