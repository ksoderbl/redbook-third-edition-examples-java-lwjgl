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

public class Example_12_2
{
    public static final int WIDTH = 1080;
    public static final int HEIGHT = 720;
    public static final String TITLE = "Example 12-2: Bezier Surface";
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
    
    public void createWindow(int width, int height, String title) {
        try {
            DisplayMode mode = new DisplayMode(width, height);
            Display.setDisplayMode(mode);
            Display.setResizable(true);
            Display.create();
            Display.setTitle(title);
        } catch (LWJGLException e) {
            e.printStackTrace();
            System.exit(1);
        }
        reshape(width, height);
        System.out.println(GL11.glGetString(GL11.GL_VERSION));
    }
    
    public void init() {
        FloatBuffer points = BufferUtils.createFloatBuffer(ctrlPoints.length);
        points.put(ctrlPoints);
        points.flip();
        
        GL11.glClearColor(0.0f, 0.0f, 0.0f, 1.0f);
        GL11.glMap2f(GL11.GL_MAP2_VERTEX_3, 0, 1, 3, 4, 0, 1, 12, 4, points);
        GL11.glEnable(GL11.GL_MAP2_VERTEX_3);
        GL11.glMapGrid2f(20, 0.0f, 1.0f, 20, 0.0f, 1.0f);
        GL11.glEnable(GL11.GL_DEPTH_TEST);
        GL11.glShadeModel(GL11.GL_FLAT);
    }
    
    public void display() {
        GL11.glClear(GL11.GL_COLOR_BUFFER_BIT|GL11.GL_DEPTH_BUFFER_BIT);
        GL11.glColor3f(1.0f,  1.0f,  1.0f);
        GL11.glPushMatrix();
        GL11.glRotatef(85.0f, 1.0f,  1.0f,  1.0f);
        for (int j = 0; j <= 8; j++) {
            GL11.glBegin(GL11.GL_LINE_STRIP);
            for (int i = 0; i <= 30; i++) {
                GL11.glEvalCoord2f(i / 30.0f, j / 8.0f);
            }
            GL11.glEnd();
            GL11.glBegin(GL11.GL_LINE_STRIP);
            for (int i = 0; i <= 30; i++) {
                GL11.glEvalCoord2f(j / 8.0f, i / 30.0f);
            }
            GL11.glEnd();
        }
        GL11.glPopMatrix();
        GL11.glFlush();
    }
    
    public void reshape(int w, int h) {
        float fw = (float)w;
        float fh = (float)h;
        GL11.glViewport(0,0, w, h);
        GL11.glMatrixMode(GL11.GL_PROJECTION);
        GL11.glLoadIdentity();
        double d = 8.0;
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
        createWindow(WIDTH, HEIGHT, TITLE);
        init();

        while (!Display.isCloseRequested()) {
            Display.sync(FPS_CAP);
            
            display();
            
//            if (!Mouse.isButtonDown(0)) {
//            }
            
            if (Display.wasResized()) {
                reshape(Display.getWidth(), Display.getHeight());
            }
            
            Display.update();
        }
        
        Display.destroy();
    }
    
    public static void main(String[] args) {
        Example_12_2 example = new Example_12_2();
        example.start();
    }
}
