package com.cdi.navigation_3d.map;

import java.util.Iterator;
import java.util.List;

import min3d.Shared;
import min3d.Utils;
import min3d.core.Object3dContainer;
import min3d.core.RendererActivity;
import min3d.parser.IParser;
import min3d.parser.Parser;
import min3d.vos.Light;
import min3d.vos.LightType;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Toast;

import com.cdi.navigation_3d.R;
import com.cdi.navigation_3d.alg.Arc;
import com.cdi.navigation_3d.alg.Graph;
import com.cdi.navigation_3d.alg.Node;
import com.cdi.navigation_3d.location.LocationOnWayBean;
import com.cdi.navigation_3d.location.OnLocationChangedListener;
import com.cdi.navigation_3d.ui.G;

public class MapRendererActivity extends RendererActivity implements
		OnClickListener {
	private final String MATERIAL2COLOR[][] = {
			{ "011", "8d8d8d" },
			{ "012", "a7a7a7" },
			{ "013", "ffffff" },
			{ "014", "ffffff" },
			{ "ele", "ffffff" },
			// {"i1", "ff69e7"},
			// {"i2", "ff00e1"},
			// {"i3", "ff00e1"},
			// {"i4", "ff00e1"},
			{ "lab 1", "ffc8c8" }, { "lab 2", "bbf5a7" },
			{ "lab 3", "fff400" }, { "lab 4", "a3e8ff" },
			{ "worksh", "ffffff" } };
	private final String LABCOLOR[] = { "ffc8c8", "bbf5a7", "fff400", "a3e8ff" };

	private final float CAM_RADIUS_X = 40;
	private final float CAM_RADIUS_Y = 40;
	private final float CAM_RADIUS_Z = 40;
	private final float ROTATION_SPEED = 1;
	private float degrees;

	private Object3dContainer monster;
	private List<Arc> resultsEdgeList;
	private List<Arc> edgesList;
	private int currentView;
	private int currentLayer;

	private float cam_radius_x;
	private float cam_radius_y;
	private float cam_radius_z;
	private Boolean isRotate;

	private float downX, downY;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		scene.reset();
	}

	@Override
	public void initScene() {

		resultsEdgeList = Graph.nodes2arcs(G.result);
		currentView = 0;
		currentLayer = 2;
		isRotate = false;

		Light light = new Light();
		light.type(LightType.DIRECTIONAL);
		light.position.setAll(300, 150, 150);
		scene.lights().add(light);

		IParser parser = Parser.createParser(Parser.Type.MAX_3DS,
				getResources(), "com.cdi.navigation_3d:raw/cdi", true);
		parser.parse();

		monster = parser.getParsedObject();
		monster.scale().x = monster.scale().y = monster.scale().z = .24f;
		scene.addChild(monster);

		monster.vertexColorsEnabled(true);
		monster.colorMaterialEnabled(true);
		initTexture();
		loadAllTexture();
		updateRoute();
		currentLayer = 1;
//		loadLabTexture(2);

		changeToView2();
		
		G.location.setOnLocationChangedListener(new OnLocationChangedListener() {
			
			@Override
			public void LocationChanged(LocationOnWayBean lb) {
				// TODO Auto-generated method stub
				if (G.to!=null){
					resultsEdgeList = Graph.nodes2arcs(
							G.g.spfa(lb.getNearestNode().getName(), G.to.name)
					);
				}
				
				cleanRoute();
				updateRoute();
			}
		});
	}

	public void cleanRoute() {
		for (int i = 0; i < G.g.nodeCount(); i++) {
			Node d = G.g.getNode(i);
			for (Arc j = d.arc; j!=null; j=j.next) {
				monster.getChildByName(j.name).isVisible(false);
			}
		}
		
	}
	
	public void updateRoute() {
		Iterator iter = resultsEdgeList.iterator();
		while (iter.hasNext()) {
			Arc node = (Arc) iter.next();
			if (node.name.charAt(1) == (char) (currentLayer + (int) '0')) {
				monster.getChildByName(node.name).isVisible(true);
				monster.getChildByName(node.name).textures().clear();
				monster.getChildByName(node.name).textures().addById("ff00e1");
			}
		}
	}

	private void changeToView1() {
		currentView = 1;
		monster.scale().x = monster.scale().y = monster.scale().z = .23f;
		monster.position().x = -12;
		monster.position().y = -3;
		monster.position().z = 0;

		cam_radius_x = -7;
		cam_radius_y = 30;
		cam_radius_z = -25;
		scene.camera().position
				.setAll(cam_radius_x, cam_radius_y, cam_radius_z);
		scene.camera().target.setAll(0, 0, 0);

		isRotate = false;
	}

	private void changeToView2() {
		currentView = 2;
		monster.scale().x = monster.scale().y = monster.scale().z = .23f;
		monster.position().x = -10;
		monster.position().y = -5;
		monster.position().z = 0;

		cam_radius_x = 0;
		cam_radius_y = 20;
		cam_radius_z = -40;
		scene.camera().position
				.setAll(cam_radius_x, cam_radius_y, cam_radius_z);
		scene.camera().target.setAll(0, 0, 0);

		monster.getChildByName("012").isVisible(false);

		isRotate = true;
	}

	private void changeToView3() {
		currentView = 3;
		monster.scale().x = monster.scale().y = monster.scale().z = .27f;
		monster.position().x = -12;
		monster.position().y = -19;
		monster.position().z = 0;

		cam_radius_x = 0;
		cam_radius_y = 32;
		cam_radius_z = -3;
		scene.camera().position
				.setAll(cam_radius_x, cam_radius_y, cam_radius_z);
		scene.camera().target.setAll(0, 0, 0);

		isRotate = false;
	}

	@Override
	public void updateScene() {
		if(!isRotate)
			return;
		
		float radians = degrees * ((float) Math.PI / 180);

		scene.camera().position.x = (float) Math.cos(radians) * CAM_RADIUS_X;
		// scene.camera().position.y = (float)Math.sin(radians) * CAM_RADIUS_Y;
		scene.camera().position.z = (float) Math.sin(radians) * CAM_RADIUS_Z;

		degrees += ROTATION_SPEED;
	}

	private void loadLabTexture(int num) {
		String lab = "lab " + num;
		String lab_color = LABCOLOR[num - 1];

		monster.textures().clear();

		int numChildren = monster.numChildren();
		for (int i = 0; i < numChildren; i++) {
			String name = monster.getChildAt(i).name();

			if (name.startsWith(lab)) {
				monster.getChildAt(i).isVisible(true);
				monster.getChildAt(i).textures().clear();
				monster.getChildAt(i).textures().addById(lab_color);
			} else
				monster.getChildAt(i).isVisible(false);
		}

		if (num == 3) {
			monster.getChildByName("lab 3_149").isVisible(false);
		}
		if (num == 2) {
			monster.getChildByName("lab 3_190").isVisible(false);
		}
		if (num == 1) {
			monster.getChildByName("lab 2_151").isVisible(false);
		}
		
		Toast.makeText(this, "Layer "+num, Toast.LENGTH_SHORT).show();
	}

	private void loadAllTexture() {
		monster.textures().clear();

		int numChildren = monster.numChildren();
		for (int i = 0; i < numChildren; i++) {
			String name = monster.getChildAt(i).name();

			Boolean o = false;
			for (int j = 0; j < 10; j++) {
				if (name.startsWith(MATERIAL2COLOR[j][0])) {
					monster.getChildAt(i).isVisible(true);
					monster.getChildAt(i).textures().clear();
					monster.getChildAt(i).textures()
							.addById(MATERIAL2COLOR[j][1]);
					o = true;
					break;
				}
			}
			if (!o) {
				// monster.getChildAt(i).textures().clear();
				// monster.getChildAt(i).textures().addById("ff00e1");
				monster.getChildAt(i).isVisible(false);
			}
		}
	}

	private void initTexture() {
		Bitmap b;
		b = Utils.makeBitmapFromResourceId(R.drawable.c_8d8d8d);
		Shared.textureManager().addTextureId(b, "8d8d8d");
		b.recycle();

		b = Utils.makeBitmapFromResourceId(R.drawable.c_a3e8ff);
		Shared.textureManager().addTextureId(b, "a3e8ff");
		b.recycle();

		b = Utils.makeBitmapFromResourceId(R.drawable.c_a7a7a7);
		Shared.textureManager().addTextureId(b, "a7a7a7");
		b.recycle();

		b = Utils.makeBitmapFromResourceId(R.drawable.c_bbf5a7);
		Shared.textureManager().addTextureId(b, "bbf5a7");
		b.recycle();

		b = Utils.makeBitmapFromResourceId(R.drawable.c_ff00e1);
		Shared.textureManager().addTextureId(b, "ff00e1");
		b.recycle();

		b = Utils.makeBitmapFromResourceId(R.drawable.c_ff69e7);
		Shared.textureManager().addTextureId(b, "ff69e7");
		b.recycle();

		b = Utils.makeBitmapFromResourceId(R.drawable.c_ffc8c8);
		Shared.textureManager().addTextureId(b, "ffc8c8");
		b.recycle();

		b = Utils.makeBitmapFromResourceId(R.drawable.c_fff400);
		Shared.textureManager().addTextureId(b, "fff400");
		b.recycle();

		b = Utils.makeBitmapFromResourceId(R.drawable.c_ffffff);
		Shared.textureManager().addTextureId(b, "ffffff");
		b.recycle();
	}

	@Override
	public boolean onTouchEvent(MotionEvent event) {
		if (event.getAction() == MotionEvent.ACTION_DOWN) {
			downX = event.getX();
			downY = event.getY();
		}

		if (event.getAction() == MotionEvent.ACTION_UP) {
			if (downY - event.getY() >= 100) {
				if (currentLayer == 4) {
					return true;
				}
				loadLabTexture(++currentLayer);
				updateRoute();
				return true;
			}
			if (event.getY() - downY >= 100) {
				if (currentLayer == 1) {
					return true;
				}
				loadLabTexture(--currentLayer);
				updateRoute();
				return true;
			}

			switch (currentView) {
			case 1:
				changeToView2();
				break;
			case 2:
				changeToView3();
				break;
			case 3:
				changeToView1();
				break;
			default:
				break;
			}
		}

		return true;
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
	}	
}
