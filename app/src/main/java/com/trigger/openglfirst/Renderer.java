package com.trigger.openglfirst;

import android.content.Context;
import android.util.Log;
import android.view.MotionEvent;

import org.rajawali3d.lights.DirectionalLight;
import org.rajawali3d.materials.Material;
import org.rajawali3d.materials.methods.DiffuseMethod;
import org.rajawali3d.materials.textures.ATexture;
import org.rajawali3d.materials.textures.Texture;
import org.rajawali3d.primitives.RectangularPrism;
import org.rajawali3d.renderer.RajawaliRenderer;

public class Renderer extends RajawaliRenderer {
    private Context context;

    private DirectionalLight directionalLight;
    public RectangularPrism pcbBox;

    public Renderer(Context context) {
        super(context);
        this.context = context;
        setFrameRate(60);
    }

    public Renderer(Context context, boolean registerForResources) {
        super(context, registerForResources);
    }

    @Override
    public void initScene() {
        getCurrentScene().setBackgroundColor(0xffffff);

        directionalLight = new DirectionalLight(1f, .2f, -1.0f);
        directionalLight.setColor(1.0f, 1.0f, 1.0f);
        directionalLight.setPower(2);
        getCurrentScene().addLight(directionalLight);

        createModel();

        //getCurrentCamera().setZ(4.2f);
        getCurrentCamera().setZ(3.2f);
        getCurrentCamera().setY(0.5f);
    }

    public void createModel() {
        getCurrentScene().clearChildren();

        Material material = new Material();
        material.enableLighting(true);
        material.setDiffuseMethod(new DiffuseMethod.Lambert());
        material.setColor(0);

        Texture pcbTexture = new Texture("PCB", R.drawable.pcb);
        try{
            material.addTexture(pcbTexture);

        } catch (ATexture.TextureException error){
            Log.d("DEBUG", "TEXTURE ERROR");
        }

        pcbBox = new RectangularPrism(1f, 0.1f, 0.75f);
        pcbBox.setMaterial(material);
        getCurrentScene().addChild(pcbBox);
    }

    @Override
    protected void onRender(long ellapsedRealtime, double deltaTime) {
        super.onRender(ellapsedRealtime, deltaTime);

//        pcbBox.rotate(Vector3.Axis.Y, 1.0);
//        pcbBox.moveRight(0.01);
    }

    @Override
    public void onOffsetsChanged(float xOffset, float yOffset, float xOffsetStep, float yOffsetStep, int xPixelOffset, int yPixelOffset) {

    }

    @Override
    public void onTouchEvent(MotionEvent event) {

    }
}
