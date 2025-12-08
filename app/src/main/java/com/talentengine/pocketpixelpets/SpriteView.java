package com.talentengine.pocketpixelpets;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.view.View;

public class SpriteView extends View {

    private Bitmap spriteSheet;
    private int cols = 1;
    private int rows = 1;
    private int frameWidth;
    private int frameHeight;
    private int frameCount;
    private int currentFrame = 0;

    private long frameDuration = 120;
    private boolean running = false;

    private final Rect srcRect = new Rect();
    private final Rect dstRect = new Rect();

    private final Runnable animator = new Runnable() {
        @Override
        public void run() {
            if (!running || spriteSheet == null) return;

            currentFrame = (currentFrame + 1) % frameCount;
            invalidate();
            postDelayed(this, frameDuration);
        }
    };

    public SpriteView(Context context) {
        super(context);
    }

    public SpriteView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public SpriteView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public void setSpriteSheet(int resId, int cols, int rows) {
        this.cols = cols;
        this.rows = rows;

        spriteSheet = BitmapFactory.decodeResource(getResources(), resId);
        if (spriteSheet == null) return;

        int sheetWidth = spriteSheet.getWidth();
        int sheetHeight = spriteSheet.getHeight();

        frameWidth = sheetWidth / cols;
        frameHeight = sheetHeight / rows;
        frameCount = cols * rows;

        requestLayout();
        invalidate();
    }

    public void setFrameDuration(long millis) {
        frameDuration = millis;
    }

    public void start() {
        if (running || spriteSheet == null) return;
        running = true;
        removeCallbacks(animator);
        post(animator);
    }

    public void stop() {
        running = false;
        removeCallbacks(animator);
    }

    @Override
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        stop();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        if (spriteSheet == null) return;

        int col = currentFrame % cols;
        int row = currentFrame / cols;

        int srcLeft = col * frameWidth;
        int srcTop = row * frameHeight;
        int srcRight = srcLeft + frameWidth;
        int srcBottom = srcTop + frameHeight;
        srcRect.set(srcLeft, srcTop, srcRight, srcBottom);

        int vw = getWidth();
        int vh = getHeight();
        int size = Math.min(vw, vh);

        int dstLeft = (vw - size) / 2;
        int dstTop = (vh - size) / 2;
        int dstRight = dstLeft + size;
        int dstBottom = dstTop + size;
        dstRect.set(dstLeft, dstTop, dstRight, dstBottom);

        canvas.drawBitmap(spriteSheet, srcRect, dstRect, null);
    }
}
