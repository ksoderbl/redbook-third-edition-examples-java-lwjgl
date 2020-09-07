package examples;

import org.lwjgl.LWJGLException;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.DisplayMode;
import org.lwjgl.opengl.GL11;

// Using this we don't have to write GL11. everywhere.
// import static org.lwjgl.opengl.GL11.*;

public class Example_1_2
{
    public static final int WIDTH = 1080;
    public static final int HEIGHT = 720;
    public static final String TITLE = "Example 1-2: White Rectangle on a Black Background, improved";
    public static final int FPS_CAP = 60;

    public void init() {
        GL11.glClearColor(0.0f, 0.0f, 0.0f, 1.0f);
        GL11.glMatrixMode(GL11.GL_PROJECTION);
        GL11.glLoadIdentity();
        GL11.glOrtho(0.0, 1.0, 0.0, 1.0, -1.0, 1.0);
    }
    
    public void display() {
        GL11.glClear(GL11.GL_COLOR_BUFFER_BIT);
        GL11.glColor3f(1.0f,  1.0f,  1.0f);
        GL11.glBegin(GL11.GL_POLYGON);
        GL11.glVertex3f(0.25f,  0.25f,  0);
        GL11.glVertex3f(0.75f,  0.25f,  0);
        GL11.glVertex3f(0.75f,  0.75f,  0);
        GL11.glVertex3f(0.25f,  0.75f,  0);
        GL11.glEnd();
        GL11.glFlush();
    }
    
    public Example_1_2() {
        Window window = new Window(WIDTH, HEIGHT, TITLE);
        init();
    }
    
    public void start() {
        while (!Display.isCloseRequested()) {
            display();
            Display.update();
            Display.sync(FPS_CAP);
        }
        
        Display.destroy();
    }
    
    public static void main(String[] args) {
        Example_1_2 example = new Example_1_2();
        example.start();
    }
}
