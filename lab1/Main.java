package mygame;

import com.jme3.animation.LoopMode;
import com.jme3.app.SimpleApplication;
import com.jme3.cinematic.MotionPath;
import com.jme3.cinematic.MotionPathListener;
import com.jme3.cinematic.events.MotionEvent;
import com.jme3.collision.CollisionResults;
import com.jme3.font.BitmapText;
import com.jme3.light.DirectionalLight;
import com.jme3.material.Material;
import com.jme3.math.ColorRGBA;
import com.jme3.math.FastMath;
import com.jme3.math.Plane;
import com.jme3.math.Quaternion;
import com.jme3.math.Ray;
import com.jme3.math.Spline;
import com.jme3.math.Vector2f;
import com.jme3.math.Vector3f;
import com.jme3.renderer.RenderManager;
import com.jme3.renderer.queue.RenderQueue.ShadowMode;
import com.jme3.scene.Geometry;
import com.jme3.scene.Node;
import com.jme3.scene.Spatial;
import com.jme3.scene.shape.Box;
import com.jme3.scene.shape.Quad;
import com.jme3.util.SkyFactory;
import com.jme3.util.TangentBinormalGenerator;
import com.jme3.water.SimpleWaterProcessor;

/**
 * test
 * @author anastasia
 */
public class Main extends SimpleApplication {
    
    /*..............................PRIVATE FIELDS...............................*/
    
    private Node player;
    private Node mainScene;
    private Geometry ocean;
    protected Spatial ship;
    protected Spatial torpedo;
    
    protected Vector3f torpedoVector;
    protected Vector3f shipVector;
  
    private final float shipSpeed = 2f;
    private final float torpedoSpeed = 2f;
    
    MotionPath pathTorpedo;
    MotionEvent controlTorpedo;
    MotionPath pathShip;
    MotionEvent controlShip;
    
    private boolean collided = false;
    
    /*...............................PUBLIC METHODS..............................*/

    public static void main(String[] args) {
        Main app = new Main();
        app.start();
    }

    @Override
    public void simpleInitApp() {
        mainScene = new Node();
       
        //set lights
        DirectionalLight sun = new DirectionalLight();
        sun.setColor(ColorRGBA.White);
        sun.setDirection(new Vector3f(-1.0f,-1.0f,-1.0f));        
        
        cam.setLocation(new Vector3f(300,500,300));
        cam.lookAt(new Vector3f(100,0, 100), new Vector3f(0,1,0));   
        flyCam.setEnabled(false);
        
        //load models
        ocean = createOcean();
        shipInit();
        torpedoInit();
        
        mainScene.attachChild(ship);
        mainScene.attachChild(torpedo);
        mainScene.addLight(sun);
        mainScene.attachChild(SkyFactory.createSky(assetManager,"Textures/Sky.png", true));
        rootNode.attachChild(mainScene);
        rootNode.attachChild(ocean);
    }
    
    @Override
    public void simpleUpdate(float tpf) {
        if(!collided)
        {
            if(areCollided())
            {
                collided = true;
            }else{
                Vector3f v = calculateShipVector();
                double angle;
                if(v.length() == 0 || shipVector.length() == 0)
                    angle = 0;
                else
                    angle = Math.acos((v.x * shipVector.x + v.z * shipVector.z) / (v.length() * shipVector.length()));
                if(Double.isNaN(angle))
                    angle = 0;
            shipVector.set(v);
       
            ship.move(new Vector3f(shipSpeed * shipVector.x, 0.0f, shipSpeed * shipVector.z));
            ship.rotate(new Quaternion().fromAngleAxis((float)(angle), Vector3f.UNIT_Y));
            Vector2f nextPoint = new Vector2f(ship.getLocalTranslation().x - torpedo.getLocalTranslation().x, 
                            ship.getLocalTranslation().z - torpedo.getLocalTranslation().z);

            pathTorpedo.addWayPoint(new Vector3f(torpedo.getLocalTranslation().x + nextPoint.x, 
                            torpedo.getLocalTranslation().y, torpedo.getLocalTranslation().z + nextPoint.y));
            }
                   
        }
        else
        {
            guiNode.detachAllChildren();
            guiFont = assetManager.loadFont("Interface/Fonts/Default.fnt");
            BitmapText text = new BitmapText(guiFont, false);
            text.setSize(1.5f * guiFont.getCharSet().getRenderedSize());
            text.setColor(ColorRGBA.Red);
            text.setText("Torpedo has shut the aim.");
            text.setLocalTranslation((cam.getWidth() - text.getLineWidth()) / 2, cam.getHeight(), 0);
            guiNode.attachChild(text);
            controlTorpedo.stop();

        }
    }

    @Override
    public void simpleRender(RenderManager rm) {
        //TODO: add render code
    }
    
    /*..............................PRIVATE METHODS..............................*/
    
    private void shipInit(){
        ship = assetManager.loadModel("Models/ship.obj");
        ship.setLocalTranslation(new Vector3f(0f,-20f,-100f));
        Material mat = new Material(assetManager,
                "Common/MatDefs/Misc/Unshaded.j3md");
        mat.setTexture("ColorMap", assetManager.loadTexture("Textures/wall2.jpg"));
        ship.setMaterial(mat);
        ship.scale(0.2f);
        
        shipVector = new Vector3f((float)(1/Math.sqrt(2)),0,(float)(1/Math.sqrt(2)));
       
    }
    
    private void torpedoInit(){
        torpedo = assetManager.loadModel("Models/torpedo.obj");
        //torpedo.setTextureMode(Sphere.TextureMode.Projected); // better quality on spheres
        TangentBinormalGenerator.generate(torpedo); 
        Material torpedoMat = new Material(assetManager, "Common/MatDefs/Light/Lighting.j3md");
        torpedoMat.setTexture("DiffuseMap", assetManager.loadTexture("Textures/metal.jpg"));
        torpedoMat.setTexture("NormalMap", assetManager.loadTexture("Textures/metal.jpg"));
        torpedoMat.setBoolean("UseMaterialColors",true);
        torpedoMat.setColor("Diffuse",ColorRGBA.White);
        torpedoMat.setColor("Specular",ColorRGBA.White);
        torpedoMat.setFloat("Shininess", 64f); 
        torpedo.setMaterial(torpedoMat);
        
        torpedo.setLocalTranslation(new Vector3f(-100f,-11f,-100f));
        
        pathTorpedo = new MotionPath();
        pathTorpedo.addWayPoint(torpedo.getLocalTranslation());
        Vector2f nextPoint = new Vector2f(ship.getLocalTranslation().x, ship.getLocalTranslation().z);
        pathTorpedo.addWayPoint(new Vector3f(nextPoint.x, torpedo.getLocalTranslation().y, nextPoint.y));
        pathTorpedo.setCycle(false);
        
        controlTorpedo = new MotionEvent(torpedo, pathTorpedo);
                controlTorpedo.setLookAt(ship.getLocalTranslation(), Vector3f.UNIT_Y);
        controlTorpedo.setDirectionType(MotionEvent.Direction.LookAt);
        controlTorpedo.setSpeed(torpedoSpeed); 
       
        controlTorpedo.play();
    }
    
    private Geometry createOcean(){
        SimpleWaterProcessor waterProcessor = new SimpleWaterProcessor(assetManager);
        waterProcessor.setReflectionScene(mainScene);
        Vector3f waterLocation = new Vector3f(0f,-10f,0f);
        waterProcessor.setLightPosition( new Vector3f(0.55f, -0.82f, 0.15f));
        waterProcessor.setPlane(new Plane(Vector3f.UNIT_Y, waterLocation.dot(Vector3f.UNIT_Y)));
        viewPort.addProcessor(waterProcessor);
        
        waterProcessor.setWaterDepth(400f);
        waterProcessor.setDistortionScale(0.8f);
        waterProcessor.setWaveSpeed(.04f);
        
        Quad bank = new Quad(6000f,6000f);
        bank.scaleTextureCoordinates(new Vector2f(6.0f,6.0f));
        
        Geometry water = new Geometry("water", bank);
        water.setLocalRotation(new Quaternion().fromAngleAxis(-FastMath.HALF_PI, Vector3f.UNIT_X));
        water.setLocalTranslation(-3000f, -10f, 2000f);
        water.setShadowMode(ShadowMode.Receive);
        water.setMaterial(waterProcessor.getMaterial());
        return water;
    }
    
    private double mod(Vector3f v){
        return Math.sqrt(Math.pow(v.x, 2) + Math.pow(v.z, 2));
    }
    
    private Vector3f calculateShipVector(){
        Vector2f cursorPos = inputManager.getCursorPosition();
        Vector3f cursorPos3D = cam.getWorldCoordinates(new Vector2f(cursorPos.x, cursorPos.y), 0f).clone();
        Vector3f direction = cam.getWorldCoordinates(new Vector2f(cursorPos.x, cursorPos.y), 1f)
                .subtractLocal(cursorPos3D).normalizeLocal();
        Ray ray = new Ray(cursorPos3D, direction);
        CollisionResults results = new CollisionResults();
        ocean.collideWith(ray, results);
        Vector3f v = results.getClosestCollision().getContactPoint();
        v.y = 0;
        return new Vector3f((float)(v.x / mod(v)), 0, (float)(v.z / mod(v)));
    }

    private boolean areEqual(Vector3f v, Vector3f u){
        if((Math.abs(v.x - u.x) < 1) && (Math.abs(v.z - u.z) < 1))
            return true;
        return false;
    }

    private boolean areCollided(){
        if((torpedo.getLocalTranslation().x < ship.getLocalTranslation().x + 2) && 
                (torpedo.getLocalTranslation().x > ship.getLocalTranslation().x - 2) &&
                (torpedo.getLocalTranslation().z < ship.getLocalTranslation().z + 2) &&
                (torpedo.getLocalTranslation().z > ship.getLocalTranslation().z - 2))
            return true;
        return false;
    }
}
