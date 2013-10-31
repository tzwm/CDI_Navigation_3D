package com.cdi.navigation_3d.ui;

import java.util.List;

import min3d.core.Object3d;
import min3d.core.Object3dContainer;
import min3d.core.RendererActivity;
import min3d.objectPrimitives.SkyBox;
import min3d.parser.IParser;
import min3d.parser.Parser;
import min3d.vos.Light;
import android.content.res.AssetManager;
import android.os.Bundle;
import android.util.Log;
import android.view.GestureDetector;
import android.view.GestureDetector.OnGestureListener;
import android.view.GestureDetector.SimpleOnGestureListener;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;

import com.cdi.navigation_3d.alg.Graph;
import com.cdi.navigation_3d.alg.Node;

public class MainActivity extends RendererActivity implements OnTouchListener{
	private Object3dContainer os;
	private Object3d o;
	private int index;
	private GestureDetector gd;
	private float lastX = 0;
	private float lastY = 0;
	private SkyBox sky;
	private float phy=0;
	private float seta=0;
	private Light l=new Light();
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		gd=new GestureDetector(this, gdl);
		AssetManager asset=getAssets();
		try {
			Graph g=Graph.build(asset.open("map.txt"));
			g.reset();
			g.dfs(g.getNode(0));
			List<Node> result=g.spfa(0, 10);
			for (Node n:result) Log.i("path",n.getIndex()+","+n.getDistance());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	@Override
	public void initScene() {
		_glSurfaceView.setOnTouchListener(this);		
		scene.lights().add(l);
		sky=new SkyBox(100, 1);
		//scene.addChild(sky);
		IParser parser = Parser.createParser(Parser.Type.MAX_3DS,
				getResources(), "com.innermap:raw/mod", false);
		parser.parse();
		os=parser.getParsedObject();
		os.colorMaterialEnabled(true);
		
		scene.addChild(os);
		Object3d o= os.getChildByName("o41");
	
		scene.camera().position.setAllFrom(o.position());
		scene.camera().target.setAll(0, 15, 0);
		index=0;

		scene.lightingEnabled(true);
		
		super.initScene();
	}	
	private double d2r(double degree){
		return degree*Math.PI/180;
	}
	@Override
	public void updateScene() {
		int len=5;
		//double z=Math.sin(d2r(seta))*len;
		//double t=Math.cos(d2r(seta))*len;
		//double x=Math.sin(d2r(phy))*t;
		//double y=Math.cos(d2r(phy))*t;
		
		//scene.camera().target.x=(float)x+scene.camera().position.x;
		//scene.camera().target.y=(float)y+scene.camera().position.y;
		//scene.camera().target.z=(float)z+scene.camera().position.z;
		//Log.d("coordinate","("+scene.camera().target.x+","+scene.camera().target.y+","+scene.camera().target.z+")"+
				//"("+scene.camera().position.x+","+scene.camera().position.y+","+scene.camera().position.z+")");
		l.position.setAllFrom(scene.camera().position);
		
		super.updateScene();
	}
	
	
	@Override
	public boolean onTouch(View v, MotionEvent event) {
		switch(event.getAction()){
		case MotionEvent.ACTION_DOWN:
			lastX=event.getX();
			lastY=event.getY();
			return true;
		case MotionEvent.ACTION_UP:
			lastX=0;
			lastY=0;
			return true;
		case MotionEvent.ACTION_MOVE:
			seta-=(event.getX()-lastX);
			//while (phy>180) phy-=180;
			//while (phy<-180) phy+=180;
			phy-=(event.getY()-lastY);
			//while (seta>90) seta-=90;
			//while (seta<-90) seta+=90;
			lastX=event.getX();
			lastY=event.getY();
			Log.d("angle",phy+","+seta);
			return true;
		}
		return false;//gd.onTouchEvent(event);
	}
	
	private OnGestureListener gdl=new SimpleOnGestureListener() {
		public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
			Log.d("Gesture","fling");
			scene.camera().position.x+=e2.getX()-e1.getX();
			scene.camera().position.y+=velocityY;
			return true;
			
		};		
		public boolean onDown(MotionEvent e) {
			Log.d("Gesture","down");
			return true;
		};
	};
}
