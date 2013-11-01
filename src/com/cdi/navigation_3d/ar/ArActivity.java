package com.cdi.navigation_3d.ar;

import com.cdi.navigation_3d.R;

import min3d.core.Object3d;
import min3d.core.RendererActivity;
import min3d.vos.Light;
import min3d.vos.Number3d;
import min3d.vos.RenderType;
import android.content.pm.ActivityInfo;
import android.graphics.PixelFormat;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.util.Log;
import android.widget.FrameLayout;
import android.widget.LinearLayout;

public class ArActivity extends RendererActivity {
	private final float FILTERING_FACTOR = .3f;

	// private SkyBox mSkyBox;
	private SensorManager mSensorManager;
	private Sensor mAccelerometer;
	private Number3d mAccVals;

	private static final String TAG = "sensor";

	@Override
	protected void onCreateSetContentView() {
		// setContentView(_glSurfaceView);
		setContentView(R.layout.activity_ar);		
		FrameLayout frameLayout = (FrameLayout) findViewById(R.id.acview);
		frameLayout.addView(_glSurfaceView);
	}

	@Override
	protected void glSurfaceViewConfig() {
		// !important
		_glSurfaceView.setEGLConfigChooser(8, 8, 8, 8, 16, 0);
		_glSurfaceView.getHolder().setFormat(PixelFormat.TRANSLUCENT);
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		scene.reset();
		mSensorManager = (SensorManager) getSystemService(SENSOR_SERVICE);
		mAccelerometer = mSensorManager
				.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
		mAccVals = new Number3d();

		mSensorManager.registerListener(myListener, mAccelerometer,
				SensorManager.SENSOR_DELAY_UI);
	}

	@Override
	public void onPause() {
		mSensorManager.unregisterListener(myListener);
		super.onPause();
	}
	
	@Override
	public void onResume() {
		super.onResume();
		try {
		mSensorManager.registerListener(myListener, mAccelerometer,
				SensorManager.SENSOR_DELAY_NORMAL);
		} catch (Exception e) {
			
		}
	}

	Object3d square;

	public void initScene() {

		scene.backgroundColor().setAll(0x00000000);

		scene.lights().add(new Light());

		square = new Object3d(3, 1, false, false, false);
		square.vertices()
				.addVertex(new Number3d(0, 0, -0.2f), null, null, null);
		square.vertices().addVertex(new Number3d(-0.5f, 0, 0.2f), null, null,
				null);
		square.vertices().addVertex(new Number3d(0.5f, 0, 0.2f), null, null,
				null);

		square.renderType(RenderType.TRIANGLE_STRIP);
		square.ignoreFaces(true);

		scene.camera().position.setAll(0, 5f, 0);
		scene.camera().target.setAll(0, 0, 0);

		scene.addChild(square);
	}

	public void updateScene() {
	}

	final SensorEventListener myListener = new SensorEventListener() {
		public void onSensorChanged(SensorEvent sensorEvent) {
			if (sensorEvent.sensor.getType() != Sensor.TYPE_ACCELEROMETER)
	            return;
			
			if (square == null) {
				return;
			}
			scene.camera().position.z = sensorEvent.values[1];
			if (scene.camera().position.z >= 0) {
				square.rotation().y = 0;
			} else {
				square.rotation().y = 180;
			}
		}

		public void onAccuracyChanged(Sensor sensor, int accuracy) {
		}
	};
}
