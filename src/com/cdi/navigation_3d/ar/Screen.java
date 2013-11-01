package com.cdi.navigation_3d.ar;

import java.io.IOException;

import android.content.Context;
import android.hardware.Camera;
import android.util.AttributeSet;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

public class Screen extends SurfaceView implements SurfaceHolder.Callback {

	public Screen(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		// TODO Auto-generated constructor stub
		// Install a SurfaceHolder.Callback so we get notified when the
		// underlying surface is created and destroyed.
		previewHolder = this.getHolder();
		previewHolder.addCallback(this);
		// ************
		previewHolder.setType(SurfaceHolder.SURFACE_TYPE_PUSH_BUFFERS);
	}

	public Screen(Context context, AttributeSet attrs) {
		super(context, attrs);
		// Install a SurfaceHolder.Callback so we get notified when the
		// underlying surface is created and destroyed.
		previewHolder = this.getHolder();
		previewHolder.addCallback(this);
		// ************
		previewHolder.setType(SurfaceHolder.SURFACE_TYPE_PUSH_BUFFERS);
	}

	public Screen(Context context) {
		super(context);
		// Install a SurfaceHolder.Callback so we get notified when the
		// underlying surface is created and destroyed.
		previewHolder = this.getHolder();
		previewHolder.addCallback(this);
		// ************
		previewHolder.setType(SurfaceHolder.SURFACE_TYPE_PUSH_BUFFERS);
	}

	/**
	 * Initialize the hardware camera. holder The holder
	 */
	public void surfaceCreated(SurfaceHolder holder) {
		// ************
		if (!isPreviewRunning)
			mCamera = Camera.open();
		else
			mCamera.stopPreview();

	}

	public void surfaceDestroyed(SurfaceHolder holder) {
		// Surface will be destroyed when we return, so stop the preview.
		// Because the CameraDevice object is not a shared resource, it's very
		// important to release it when the activity is paused.
		try {
			if (mCamera != null) {
				// **********
				mCamera.setPreviewCallback(null);
				mCamera.stopPreview();
				mCamera.release();
				// **********
				mCamera = null;
				isPreviewRunning = false;
			}
		} catch (Exception e) {

		}
	}

	public void surfaceChanged(SurfaceHolder holder, int format, int w, int h) {

		if (mCamera != null && isPreviewRunning != true) {
			try {
				mCamera.setDisplayOrientation(90);
				Camera.Parameters p = mCamera.getParameters();
				//p.setPreviewSize(w, h);
				mCamera.setPreviewDisplay(holder);
				mCamera.setParameters(p);
				mCamera.setPreviewCallback(null);
				mCamera.startPreview();
			} catch (IOException e) {
				e.printStackTrace();
			}

			isPreviewRunning = true;
		}

	}

	private Camera mCamera;
	private SurfaceHolder previewHolder;
	private boolean isPreviewRunning = false;
	// private Camera.PreviewCallback imageCaptureCallback;

}
