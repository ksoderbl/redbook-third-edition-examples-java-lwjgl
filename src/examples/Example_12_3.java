package examples;

import java.nio.FloatBuffer;

import org.lwjgl.BufferUtils;
import org.lwjgl.LWJGLException;
//import org.lwjgl.input.Mouse;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.DisplayMode;
import org.lwjgl.opengl.GL11;

// Using this we don't have to write GL11. everywhere.
// import static org.lwjgl.opengl.GL11.*;

public class Example_12_3
{
    public static final int WIDTH = 1080;
    public static final int HEIGHT = 720;
    public static final String TITLE = "Example 12-3: Lit, Shaded Bezier Surface Drawn with a Mesh";
    public static final int FPS_CAP = 60;
    
    private float[] ctrlPoints = {
        -1.5f, -1.5f,  4.0f, -0.5f, -1.5f,  2.0f,
         0.5f, -1.5f, -1.0f,  1.5f, -1.5f,  2.0f,
        -1.5f, -0.5f,  1.0f, -0.5f, -0.5f,  3.0f,
         0.5f, -0.5f,  0.0f,  1.5f, -0.5f, -1.0f,
        -1.5f,  0.5f,  4.0f, -0.5f,  0.5f,  0.0f,
         0.5f,  0.5f,  3.0f,  1.5f,  0.5f,  4.0f,
        -1.5f,  1.5f, -2.0f, -0.5f,  1.5f, -2.0f,
         0.5f,  1.5f,  0.0f,  1.5f,  1.5f, -1.0f
    };
    
    public void init() {
        FloatBuffer points = BufferUtils.createFloatBuffer(ctrlPoints.length);
        points.put(ctrlPoints);
        points.flip();
        
        GL11.glEnable(GL11.GL_DEPTH_TEST);
        GL11.glMap2f(GL11.GL_MAP2_VERTEX_3, 0, 1, 3, 4, 0, 1, 12, 4, points);
        GL11.glEnable(GL11.GL_MAP2_VERTEX_3);
        GL11.glEnable(GL11.GL_AUTO_NORMAL);
        GL11.glMapGrid2f(20, 0.0f, 1.0f, 20, 0.0f, 1.0f);
        
        initlights();
    }
    
    public FloatBuffer makeFloatBuffer(float[] array) {
        FloatBuffer buffer = BufferUtils.createFloatBuffer(array.length);
        buffer.put(array);
        buffer.flip();
        return buffer;
    }
    
    public void initlights() {
        float ambient[] = { 0.2f, 0.2f, 0.2f, 1.0f };
        float position[] = { 0.0f, 0.0f, 2.0f, 1.0f };
        float matDiffuse[] = { 0.6f, 0.6f, 0.6f, 1.0f };
        float matSpecular[] = { 1.0f, 1.0f, 1.0f, 1.0f };
        float matShininess[] = { 50.0f, 0.0f, 0.0f, 0.0f };

        GL11.glEnable(GL11.GL_LIGHTING);
        GL11.glEnable(GL11.GL_LIGHT0);
        
        GL11.glLight(GL11.GL_LIGHT0, GL11.GL_AMBIENT, makeFloatBuffer(ambient));
        GL11.glLight(GL11.GL_LIGHT0, GL11.GL_POSITION, makeFloatBuffer(position));
        
        GL11.glMaterial(GL11.GL_FRONT, GL11.GL_DIFFUSE, makeFloatBuffer(matDiffuse));
        GL11.glMaterial(GL11.GL_FRONT, GL11.GL_SPECULAR, makeFloatBuffer(matSpecular));
        GL11.glMaterial(GL11.GL_FRONT, GL11.GL_SHININESS, makeFloatBuffer(matShininess));
    }
    
    public void display() {
        GL11.glClear(GL11.GL_COLOR_BUFFER_BIT|GL11.GL_DEPTH_BUFFER_BIT);
        GL11.glPushMatrix();
        GL11.glRotatef(85.0f, 1.0f,  1.0f,  1.0f);
        GL11.glEvalMesh2(GL11.GL_FILL, 0, 20, 0, 20);
        GL11.glPopMatrix();
        GL11.glFlush();
    }
    
    public void reshape(int w, int h) {
        float fw = (float)w;
        float fh = (float)h;
        GL11.glViewport(0,0, w, h);
        GL11.glMatrixMode(GL11.GL_PROJECTION);
        GL11.glLoadIdentity();
        double d = 6.0;
//        if (w <= h) {
            GL11.glOrtho(-d, d, -d * fh / fw, d * fh / fw, -d, d);
//        }
//        else {
//            GL11.glOrtho(-d * fh / fw, d * fh / fw, -d, d, -d, d);
//        }
        GL11.glMatrixMode(GL11.GL_MODELVIEW);
        GL11.glLoadIdentity();
    }
    
    public void start() {
        Window window = new Window(WIDTH, HEIGHT, TITLE);
        reshape(window.getWidth(), window.getHeight());
        init();

        while (!Display.isCloseRequested()) {
            Display.sync(FPS_CAP);
            
            if (Display.wasResized()) {
                reshape(Display.getWidth(), Display.getHeight());
            }

            display();

            Display.update();
        }
        
        Display.destroy();
    }
    
    public static void main(String[] args) {
        Example_12_3 example = new Example_12_3();
        example.start();
    }
}
