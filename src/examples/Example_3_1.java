package examples;

import org.lwjgl.LWJGLException;
//import org.lwjgl.input.Mouse;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.DisplayMode;
import org.lwjgl.opengl.GL11;
import org.lwjgl.util.glu.GLU;

// Using this we don't have to write GL11. everywhere.
// import static org.lwjgl.opengl.GL11.*;

public class Example_3_1
{
    public static final int WIDTH = 1080;
    public static final int HEIGHT = 720;
    public static final String TITLE = "Example 3-1: Transformed Cube";
    public static final int FPS_CAP = 60;
    
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
    
    public void wireCube(float width) {
        float w2 = width / 2.0f;
        GL11.glBegin(GL11.GL_LINE_LOOP);
        GL11.glVertex3f(w2, w2, w2);
        GL11.glVertex3f(w2, -w2, w2);
        GL11.glVertex3f(-w2, -w2, w2);
        GL11.glVertex3f(-w2, w2, w2);
        GL11.glEnd();
        
        GL11.glBegin(GL11.GL_LINE_LOOP);
        GL11.glVertex3f(w2, w2, -w2);
        GL11.glVertex3f(w2, -w2, -w2);
        GL11.glVertex3f(-w2, -w2, -w2);
        GL11.glVertex3f(-w2, w2, -w2);
        GL11.glEnd();

        GL11.glBegin(GL11.GL_LINES);
        GL11.glVertex3f(w2, w2, w2);
        GL11.glVertex3f(w2, w2, -w2);
        GL11.glVertex3f(w2, -w2, w2);
        GL11.glVertex3f(w2, -w2, -w2);
        GL11.glVertex3f(-w2, -w2, w2);
        GL11.glVertex3f(-w2, -w2, -w2);
        GL11.glVertex3f(-w2, w2, w2);
        GL11.glVertex3f(-w2, w2, -w2);
        GL11.glEnd();
    }
    
    public void init() {
        GL11.glClearColor(1.0f, 1.0f, 1.0f, 1.0f);
        GL11.glShadeModel(GL11.GL_FLAT);
    }
    
    public void display() {
        GL11.glClear(GL11.GL_COLOR_BUFFER_BIT);
        GL11.glColor3f(0.0f,  0.0f,  0.0f);
        GL11.glLoadIdentity();
        GLU.gluLookAt(0.0f, 0.0f, 5.0f, 0.0f, 0.0f, 0.0f, 0.0f, 1.0f, 0.0f);
        GL11.glScalef(1.0f, 2.0f, 1.0f);
        wireCube(1.0f);
        GL11.glFlush();
    }
    
    public void reshape(int w, int h) {
        GL11.glViewport(0,0, w, h);
        GL11.glMatrixMode(GL11.GL_PROJECTION);
        GL11.glLoadIdentity();
        GL11.glFrustum(-1.0, 1.0, -1.0, 1.0, 1.5, 20.0);
        GL11.glMatrixMode(GL11.GL_MODELVIEW);
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
        Example_3_1 example = new Example_3_1();
        example.start();
    }
}
