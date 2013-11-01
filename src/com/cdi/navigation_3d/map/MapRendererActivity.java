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
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;

import com.cdi.navigation_3d.R;
import com.cdi.navigation_3d.alg.Node;

public class MapRendererActivity extends RendererActivity implements OnClickListener{
	private final String MATERIAL2COLOR[][] = {{"011", "8d8d8d"},
								  {"012", "a7a7a7"},
								  {"013", "ffffff"},
								  {"014", "ffffff"},
								  {"ele", "ffffff"},
//								  {"i1", "ff69e7"},
//								  {"i2", "ff00e1"},
//								  {"i3", "ff00e1"},
//								  {"i4", "ff00e1"},
								  {"lab 1", "ffc8c8"},
								  {"lab 2", "bbf5a7"},
								  {"lab 3", "fff400"},
								  {"lab 4", "a3e8ff"},
								  {"worksh", "ffffff"}};
	private final String LABCOLOR[] = {"ffc8c8", "bbf5a7", "fff400", "a3e8ff"};
	
	private final float CAM_RADIUS_X = 40;
	private final float CAM_RADIUS_Y = 40;
	private final float CAM_RADIUS_Z = 40;
	private final float ROTATION_SPEED = 1;
	private float degrees;

	private Object3dContainer monster;
	private List<Node> resultsList;
	private int currentView;
	
	
	@Override
	public void initScene() {
        Light light = new Light();
        light.type(LightType.DIRECTIONAL);
        light.position.setAll(300, 150, 150);
        scene.lights().add(light);

        IParser parser = Parser.createParser(Parser.Type.MAX_3DS,
                getResources(), "com.cdi.navigation_3d:raw/cdi", true);
        parser.parse();
        
        monster = parser.getParsedObject();
        monster.scale().x = monster.scale().y = monster.scale().z  = .24f;
		scene.addChild(monster);
		
        monster.vertexColorsEnabled(true);
        monster.colorMaterialEnabled(true);
        initTexture();
        loadAllTexture();
//        loadLabTexture(3);
		
        changeToView2();
	}
	
	public void setResults(List<Node> _results) {
		this.resultsList = _results;
	}
	
	public void updateMap() {
		loadAllTexture();
		
		Iterator iter = resultsList.iterator();
		while(iter.hasNext()) {
			Node node = (Node)iter.next();
			monster.getChildByName(node.name).textures().addById("ff00e1");
		}
	}
	
	private void changeToView1() {
		currentView = 1;
		monster.scale().x = monster.scale().y = monster.scale().z  = .23f;
        monster.position().x = -12;
        monster.position().y = -3;
        monster.position().z = 0;
        
        scene.camera().position.setAll(-7, 30, -25);
		scene.camera().target.setAll(0, 0, 0);
		
		monster.getChildByName("lab 3_055").isVisible(false);
		monster.getChildByName("lab 3_149").isVisible(false);
		monster.getChildByName("lab 3_190").isVisible(false);
	}

	private void changeToView2() {
		currentView = 2;
		monster.scale().x = monster.scale().y = monster.scale().z  = .23f;
        monster.position().x = -10;
        monster.position().y = -5;
        monster.position().z = 0;
        
        scene.camera().position.setAll(0, 20, -40);
		scene.camera().target.setAll(0, 0, 0);
		
		monster.getChildByName("012").isVisible(false);
	}
	
	private void changeToView3() {
		currentView = 3;
		monster.scale().x = monster.scale().y = monster.scale().z  = .27f;
        monster.position().x = -12;
        monster.position().y = -19;
        monster.position().z = 0;
        
        scene.camera().position.setAll(0, 32, -3);
		scene.camera().target.setAll(0, 0, 0);
		
		monster.getChildByName("lab 3_055").isVisible(false);
		monster.getChildByName("lab 3_149").isVisible(false);
		monster.getChildByName("lab 3_190").isVisible(false);
	}
	
	@Override
	public void updateScene() {
//		float radians = degrees * ((float)Math.PI / 180);
//
//		scene.camera().position.x = (float)Math.cos(radians) * CAM_RADIUS_X;
//		scene.camera().position.y = (float)Math.sin(radians) * CAM_RADIUS_Y;
//		scene.camera().position.z = (float)Math.sin(radians) * CAM_RADIUS_Z;
//
//		degrees += ROTATION_SPEED;
	}

	
	private void loadLabTexture(int num) {
		String lab = "lab " + num;
		String lab_color = LABCOLOR[num-1];
		
		monster.textures().clear();
		
		int numChildren = monster.numChildren();
		for(int i = 0; i < numChildren; i++) {
			String name = monster.getChildAt(i).name();
			
			if (name.startsWith(lab)) {
				monster.getChildAt(i).textures().clear();
				monster.getChildAt(i).textures().addById(lab_color);
			}
			else {
				monster.getChildAt(i).isVisible(false);
			}
		}
	}
	
	private void loadAllTexture() {
		monster.textures().clear();
		
		int numChildren = monster.numChildren();
		for(int i = 0; i < numChildren; i++) {
			String name = monster.getChildAt(i).name();
			
			Boolean o = false;
			for(int j = 0; j < 10; j++) {
				if(name.startsWith(MATERIAL2COLOR[j][0])) {
					monster.getChildAt(i).textures().clear();
					monster.getChildAt(i).textures().addById(MATERIAL2COLOR[j][1]);
					o = true;
					break;
				}
			}
			if(!o) {
//				monster.getChildAt(i).textures().clear();
//				monster.getChildAt(i).textures().addById("ff00e1");
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
        Shared.textureManager().addTextureId(b,"a7a7a7");
        b.recycle();
        
        b = Utils.makeBitmapFromResourceId(R.drawable.c_bbf5a7);
        Shared.textureManager().addTextureId(b,"bbf5a7");
        b.recycle();
        
        b = Utils.makeBitmapFromResourceId(R.drawable.c_ff00e1);
        Shared.textureManager().addTextureId(b,"ff00e1");
        b.recycle();
       
        b = Utils.makeBitmapFromResourceId(R.drawable.c_ff69e7);
        Shared.textureManager().addTextureId(b,"ff69e7");
        b.recycle();
        
        b = Utils.makeBitmapFromResourceId(R.drawable.c_ffc8c8);
        Shared.textureManager().addTextureId(b,"ffc8c8");
        b.recycle();

        b = Utils.makeBitmapFromResourceId(R.drawable.c_fff400);
        Shared.textureManager().addTextureId(b,"fff400");
        b.recycle();
       
        b = Utils.makeBitmapFromResourceId(R.drawable.c_ffffff);
        Shared.textureManager().addTextureId(b,"ffffff");
        b.recycle();
	}
	
    @Override
	public boolean onTouchEvent(MotionEvent event){
		if(event.getAction() == MotionEvent.ACTION_UP) {
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
