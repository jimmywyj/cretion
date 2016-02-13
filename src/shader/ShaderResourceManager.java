package shader;

import org.newdawn.slick.SlickException;

/**
 * @author Chronocide (Jeremy Klix)
 */
public interface ShaderResourceManager {
    int getFragementShaderID(String fragmentFileName) throws SlickException;

    int getVertexShaderID(String vertexFileName) throws SlickException;

    void createProgramShaderDependancy(int programID, int shaderID);

    void removeProgram(int programID);
}