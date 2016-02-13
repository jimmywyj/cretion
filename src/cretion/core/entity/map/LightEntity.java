package cretion.core.entity.map;

import cretion.core.entity.Entity;
import cretion.gui.CretionCamera;
import cretion.states.GameState;
import org.newdawn.slick.SlickException;
import shader.Shader;

public class LightEntity extends Entity {
    private static int LightCount;
    private static int PositionID;
    private static int ColorID;
    private static int AmbientID;
    private static int FalloffID;
    private static int DirectionID;
    private static float[] LightPositions;
    private static float[] LightColors;
    private static float[] AmbientColors;
    private static float[] LightFalloffs;
    private static float[] LightDirections;
    private static Shader shader;

    static {
        final int MAX_LIGHTS = 10;
        LightPositions = new float[MAX_LIGHTS * 2];
        LightColors = new float[MAX_LIGHTS * 4];
        AmbientColors = new float[MAX_LIGHTS * 4];
        LightFalloffs = new float[MAX_LIGHTS * 3];
        LightDirections = new float[MAX_LIGHTS * 2];
        LightCount = 0;

        try {
            shader = Shader.makeShader("shaders/LightShader.vert", "shaders/LightShader.frag");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private float x;
    private float y;
    private int positionID;
    private int colorID;
    private int ambientID;
    private int falloffID;
    private int directionID;

    public LightEntity(float _x, float _y) throws SlickException {
        x = _x;
        y = _y;

        positionID = PositionID;
        colorID = ColorID;
        ambientID = AmbientID;
        falloffID = FalloffID;
        directionID = DirectionID;

        PositionID += 2;
        ColorID += 4;
        AmbientID += 4;
        FalloffID += 3;
        DirectionID += 2;
        LightCount++;

        setLightColor(1.f, 0.8f, 0.6f, 1.f);
        setAmbientColor(0.6f, 0.6f, 1.f, 0.2f);
        setFalloff(0.4f, 3.f, 20.f);
        setDirection(0, -1);
    }

    public void setDirection(float _x, float _y) {
        LightDirections[directionID] = _x;
        LightDirections[directionID + 1] = _y;
    }

    public void setLightColor(float _r, float _g, float _b, float _a) {
        LightColors[colorID] = _r;
        LightColors[colorID + 1] = _g;
        LightColors[colorID + 2] = _b;
        LightColors[colorID + 3] = _a;
    }

    public void setAmbientColor(float _r, float _g, float _b, float _a) {
        AmbientColors[ambientID] = _r;
        AmbientColors[ambientID + 1] = _g;
        AmbientColors[ambientID + 2] = _b;
        AmbientColors[ambientID + 3] = _a;
    }

    public void setFalloff(float _x, float _y, float _z) {
        LightFalloffs[falloffID] = _x;
        LightFalloffs[falloffID + 1] = _y;
        LightFalloffs[falloffID + 2] = _z;
    }

    public void updateShader() {
        LightPositions[positionID] = x + (CretionCamera.CameraTranslateX / GameState.container.getWidth());
        LightPositions[positionID + 1] = y + (CretionCamera.CameraTranslateY / GameState.container.getHeight());

        shader.setUniformFloatVariable("ResolutionX", GameState.container.getWidth());
        shader.setUniformFloatVariable("ResolutionY", GameState.container.getHeight());
        shader.setUniformIntVariable("lightCount", LightCount);
        shader.setUniformFloatVariable("lightPositions", LightPositions);
        shader.setUniformFloatVariable("lightColors", LightColors);
        shader.setUniformFloatVariable("ambientColors", AmbientColors);
        shader.setUniformFloatVariable("lightFalloffs", LightFalloffs);
        shader.setUniformFloatVariable("lightDirections", LightDirections);
    }

    public static void StartShader() {
        shader.startShader();
    }

    public static void EndShaders() {
        Shader.forceFixedShader();
    }
}
