package com.ambergleam.glassyugioh;

import android.content.Context;
import android.graphics.Canvas;
import android.util.Log;
import android.view.SurfaceHolder;
import android.view.View;

/**
 * SurfaceHolder.Callback used to draw the duel on the timeline LiveCard.
 */
public class DuelDrawer implements SurfaceHolder.Callback {

	private static final String TAG = "DuelDrawer";

	private final DuelView mChronometerView;
	private SurfaceHolder mHolder;

	public DuelDrawer(Context context) {
		mChronometerView = new DuelView(context);
		mChronometerView.setListener(new DuelView.ChangeListener() {
			@Override
			public void onChange() {
				draw(mChronometerView);
			}
		});
		mChronometerView.setForceStart(true);
	}

	@Override
	public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {
		// Measure and layout the view with the canvas dimensions.
		int measuredWidth = View.MeasureSpec.makeMeasureSpec(width, View.MeasureSpec.EXACTLY);
		int measuredHeight = View.MeasureSpec.makeMeasureSpec(height, View.MeasureSpec.EXACTLY);
		// Alter the layout accordingly
		mChronometerView.measure(measuredWidth, measuredHeight);
		mChronometerView.layout(0, 0, mChronometerView.getMeasuredWidth(), mChronometerView.getMeasuredHeight());
	}

	@Override
	public void surfaceCreated(SurfaceHolder holder) {
		Log.d(TAG, "Surface created");
		mHolder = holder;
		mChronometerView.start();
	}

	@Override
	public void surfaceDestroyed(SurfaceHolder holder) {
		Log.d(TAG, "Surface destroyed");
		mChronometerView.stop();
		mHolder = null;
	}

	/**
	 * Draws the view in the SurfaceHolder's canvas.
	 */
	private void draw(View view) {
		Canvas canvas;
		try {
			canvas = mHolder.lockCanvas();
		} catch (Exception e) {
			return;
		}
		if (canvas != null) {
			view.draw(canvas);
			mHolder.unlockCanvasAndPost(canvas);
		}
	}

}
