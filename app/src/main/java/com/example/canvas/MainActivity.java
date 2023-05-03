package com.example.canvas;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.res.ResourcesCompat;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Point;
import android.graphics.Rect;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

public class MainActivity extends AppCompatActivity {

    //properti untuk menggambar
    private Canvas mCanvas;
    private Paint mPaint = new Paint();
    private Paint mPaintText = new Paint(Paint.UNDERLINE_TEXT_FLAG);
    private Bitmap mBitmap;
    private ImageView mImageView;

    //yang akan digambar dan ditempel
    private Rect mRect = new Rect();
    private Rect mBounds = new Rect();

    //batasan
    private static final int OFFSET = 80;
    private int mOffset = OFFSET;
    private static final int MULTIPLER = 100;

    private int mColorBackground;
    private int mColorRectangle;
    private int mColorCircle;
    private int mColorText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mColorBackground = ResourcesCompat.getColor(getResources(), R.color.colorBackground, null);
        mColorRectangle = ResourcesCompat.getColor(getResources(), R.color.colorRectangle, null);
        mColorCircle = ResourcesCompat.getColor(getResources(), R.color.colorAccent, null);
        mColorText = ResourcesCompat.getColor(getResources(), R.color.black, null);

        //mewarnai background
        mPaint.setColor(mColorBackground);
        mPaintText.setColor(mColorText);
        mPaintText.setTextSize(70);

        mImageView = findViewById(R.id.my_image_view);
        mImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                drawSomething(view);
            }
        });
    }

    public void drawSomething(View view){
        int vWidth = view.getWidth();
        int vHeight = view.getHeight();

        int halfWidth = vWidth/2;
        int halfHeight = vHeight/2;

        if (mOffset == OFFSET){
            mBitmap = Bitmap.createBitmap(vWidth, vHeight, Bitmap.Config.ARGB_8888);
            mImageView.setImageBitmap(mBitmap);
            mCanvas = new Canvas(mBitmap);
            mCanvas.drawColor(mColorBackground);

            mCanvas.drawText(getString(R.string.keep_tapping), 100, 100, mPaintText);
            mOffset += OFFSET;
        }else{
            if (mOffset < halfWidth && mOffset < halfHeight){
                mPaint.setColor(mColorRectangle - MULTIPLER*mOffset);
                mRect.set(mOffset, mOffset, vWidth-mOffset, vHeight-mOffset);
                mCanvas.drawRect(mRect, mPaint);
                mOffset += OFFSET;
            } else {
                //gambar lingkaran
                mPaint.setColor(mColorCircle - MULTIPLER*mOffset);
                mCanvas.drawCircle(halfWidth, halfHeight, halfHeight/3, mPaint);

                String text = getString(R.string.done);
                mPaintText.getTextBounds(text, 0, text.length(),mBounds);

                int x = halfWidth - mBounds.centerX();
                int y = halfHeight - mBounds.centerY();
                mCanvas.drawText(text, x, y, mPaintText);
                mOffset += OFFSET;

                //gambar segitiga
                mPaint.setColor(mColorBackground - MULTIPLER*mOffset);
                Point a = new Point(halfWidth-50, halfHeight-50);
                Point b = new Point(halfWidth+50, halfHeight-50);
                Point c = new Point(halfWidth, halfHeight+150);

                Path path = new Path();
                path.setFillType(Path.FillType.EVEN_ODD);
                path.lineTo(a.x, a.y);
                path.lineTo(b.x, b.y);
                path.lineTo(c.x, c.y);
                path.lineTo(a.x, a.y);
                path.close();

                mCanvas.drawPath(path, mPaint);
                mOffset += OFFSET;
            }
        }
        view.invalidate();
    }
}