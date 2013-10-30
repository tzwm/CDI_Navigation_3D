package com.cdi.navigation_3d;

import min3d.core.Object3dContainer;
import min3d.core.RendererActivity;
import min3d.parser.IParser;
import min3d.parser.Parser;
import min3d.vos.Color4;
import min3d.vos.Light;
import min3d.vos.LightType;
import android.util.Log;

public class MainActivity extends RendererActivity {
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
                getResources(), "com.cdi.navigation_3d:raw/cdi", false);
        parser.parse();

        monster = parser.getParsedObject();
		monster.scale().x = monster.scale().y = monster.scale().z  = .2f;
        monster.position().x = 0;
        monster.position().y = 0;
        monster.position().z = 0;
		scene.addChild(monster);

        monster.vertexColorsEnabled(true);
        monster.colorMaterialEnabled(true);

        monster.getChildByName("lab 3_149").textures().clear();
//        monster.getChildByName("lab 3_149").textures().addById("squared_robot_arm");
        monster.getChildByName("lab 3_149").colors().clear();
        monster.getChildByName("lab 3_149").defaultColor(new Color4(124, 244, 0, 255));
        monster.getChildByName("lab 3_149").colorMaterialEnabled(true);
//        monster.getChildByName("lab 3_149").colors().add(new Color4(124, 244, 0, 255));
        monster.getChildByName("lab 3_149").vertexColorsEnabled(false);

        monster.getChildByName("lab 4").textures().clear();
        monster.getChildByName("lab 4").colors().clear();
        monster.getChildByName("lab 4").defaultColor(new Color4(124, 244, 0, 255));
//        monster.getChildByName("lab 4").colorMaterialEnabled(true);
//        monster.getChildByName("lab 4").vertexColorsEnabled(true);


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
}
