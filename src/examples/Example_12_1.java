package examples;

import java.nio.FloatBuffer;

import org.lwjgl.BufferUtils;
import org.lwjgl.LWJGLException;
//import org.lwjgl.input.Mouse;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.DisplayMode;
import org.lwjgl.opengl.GL11;
import org.lwjgl.util.glu.GLU;

// Using this we don't have to write GL11. everywhere.
// import static org.lwjgl.opengl.GL11.*;

public class Example_12_1
{
    public static final int WIDTH = 1080;
    public static final int HEIGHT = 720;
    public static final String TITLE = "Example 12-1: Bezier Curve with Four Control Points";
    public static final int FPS_CAP = 60;
    
    private float[] ctrlPoints = {
            -4.0f, -4.0f, 0.0f,
            -2.0f,  4.0f, 0.0f,
            2.0f, -4.0f, 0.0f,
            4.0f,  4.0f, 0.0f
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
        GL11.glShadeModel(GL11.GL_FLAT);
        GL11.glMap1f(GL11.GL_MAP1_VERTEX_3, 0.0f, 1.0f, 3, 4, points);
        GL11.glEnable(GL11.GL_MAP1_VERTEX_3);
    }
    
    public void display() {
        GL11.glClear(GL11.GL_COLOR_BUFFER_BIT);
        GL11.glColor3f(1.0f,  1.0f,  1.0f);
        GL11.glBegin(GL11.GL_LINE_STRIP);
        for (int i = 0; i <= 30; i++) {
            GL11.glEvalCoord1f(i / 30.0f);
        }
        GL11.glEnd();
        // The following code displays the control points as dots.
        GL11.glPointSize(5.0f);
        GL11.glColor3f(1.0f,  1.0f,  1.0f);
        GL11.glBegin(GL11.GL_POINTS);
        for (int i = 0; i < 4; i++) {
            GL11.glVertex3f(ctrlPoints[0 + 3 * i], ctrlPoints[1 + 3 * i], ctrlPoints[2 + 3 * i]);
        }
        GL11.glEnd();
        GL11.glFlush();
    }
    
    public void reshape(int w, int h) {
        float fw = (float)w;
        float fh = (float)h;
        GL11.glViewport(0,0, w, h);
        GL11.glMatrixMode(GL11.GL_PROJECTION);
        GL11.glLoadIdentity();
        double d = 8.0;
        if (w <= h) {
            GL11.glOrtho(-d, d, -d * fh / fw, d * fh / fw, -d, d);
        }
        else {
            GL11.glOrtho(-d * fh / fw, d * fh / fw, -d, d, -d, d);
        }
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
        Example_12_1 example = new Example_12_1();
        example.start();
    }
}
