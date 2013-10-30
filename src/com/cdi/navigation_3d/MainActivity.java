package com.cdi.navigation_3d;

import min3d.Shared;
import min3d.Utils;
import min3d.core.Object3dContainer;
import min3d.core.RendererActivity;
import min3d.parser.IParser;
import min3d.parser.Parser;
import min3d.vos.Light;
import min3d.vos.LightType;
import android.graphics.Bitmap;
import android.util.Log;

public class MainActivity extends RendererActivity {
	private final String material2color[][] = {{"011", "8d8d8d"},
								  {"012", "a7a7a7"},
								  {"013", "ffffff"},
								  {"014", "ffffff"},
								  {"ele", "ffffff"},
								  {"i1", "ff69e7"},
								  {"i2", "ff00e1"},
								  {"i3", "ff00e1"},
								  {"i4", "ff00e1"},
								  {"lab 1", "ffc8c8"},
								  {"lab 2", "bbf5a7"},
								  {"lab 3", "fff400"},
								  {"lab 4", "a3e8ff"},
								  {"worksh", "ffffff"}};
	
	private final float CAM_RADIUS_X = 40;
	private final float CAM_RADIUS_Y = 40;
	private final float CAM_RADIUS_Z = 40;
	private final float ROTATION_SPEED = 1;
	private Object3dContainer monster;
	private float degrees;

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
		monster.scale().x = monster.scale().y = monster.scale().z  = .2f;
        monster.position().x = 0;
        monster.position().y = 0;
        monster.position().z = 0;
		scene.addChild(monster);

        monster.vertexColorsEnabled(true);
        monster.colorMaterialEnabled(true);
        initTexture();
        loadAllTexture();
        
        monster.getChildByName("lab 3_149").textures().clear();
        monster.getChildByName("lab 3_149").textures().addById("ff00e1");

        
		scene.camera().target = monster.position();

        scene.camera().position.x = 0;
        scene.camera().position.y = 0;
        scene.camera().position.z = 0;

	}

	@Override
	public void updateScene() {
//        scene.camera().position.x = 0;
//        scene.camera().position.y = 0;
//        scene.camera().position.z = scene.camera().position.z + ROTATION_SPEED;

		float radians = degrees * ((float)Math.PI / 180);

		scene.camera().position.x = (float)Math.cos(radians) * CAM_RADIUS_X;
		scene.camera().position.y = (float)Math.sin(radians) * CAM_RADIUS_Y;
		scene.camera().position.z = (float)Math.sin(radians) * CAM_RADIUS_Z;

        Log.d("3ds", "x:"+String.valueOf(scene.camera().position.x));
        Log.d("3ds", "y:"+String.valueOf(scene.camera().position.y));
        Log.d("3ds", "z:"+String.valueOf(scene.camera().position.z));

		degrees += ROTATION_SPEED;

	}
	
	private void loadAllTexture() {
		int numChildren = monster.numChildren();
		for(int i = 0; i < numChildren; i++) {
			String name = monster.getChildAt(i).name();
			
			Boolean o = false;
			for(int j = 0; j < 14; j++) {
				if(name.startsWith(material2color[j][0])) {
					monster.getChildAt(i).textures().clear();
					monster.getChildAt(i).textures().addById(material2color[j][1]);
					o = true;
					break;
				}
			}
			if(!o) {
				monster.getChildAt(i).textures().clear();
				monster.getChildAt(i).textures().addById("ff00e1");
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
}
