package examples;

import org.lwjgl.input.Mouse;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.GL11;

// Using this we don't have to write GL11. everywhere.
// import static org.lwjgl.opengl.GL11.*;

public class Example_2_3
{
    public static final int WIDTH = 1080;
    public static final int HEIGHT = 720;
    public static final String TITLE = "Example 2-3: Filled Polygon";
    public static final int FPS_CAP = 60;
    
    public void init() {
        GL11.glClearColor(0.0f, 0.0f, 0.2f, 1.0f);
        GL11.glShadeModel(GL11.GL_SMOOTH);
    }
    
    public void display() {
        GL11.glClear(GL11.GL_COLOR_BUFFER_BIT);
        GL11.glPushMatrix();
        GL11.glBegin(GL11.GL_POLYGON);
        GL11.glColor3f(0.8f, 0.3f, 0.2f);
        GL11.glVertex2f(0.0f, 0.0f);
        GL11.glColor3f(1.0f, 1.0f, 0.4f);
        GL11.glVertex2f(0.0f, 3.0f);
        GL11.glColor3f(0.2f, 0.3f, 1.0f);
        GL11.glVertex2f(4.0f, 3.0f);
        GL11.glColor3f(0.8f, 0.9f, 0.7f);
        GL11.glVertex2f(6.0f, 1.5f);
        GL11.glColor3f(0.1f, 0.9f, 0.8f);
        GL11.glVertex2f(4.0f, 0.0f);
        GL11.glEnd();
        GL11.glPopMatrix();
    }
    
    public void reshape(int w, int h) {
        GL11.glViewport(0, 0, w, h);
        GL11.glMatrixMode(GL11.GL_PROJECTION);
        GL11.glLoadIdentity();
        GL11.glOrtho(-1.0, 7.0, -1.0, 4.0, -1.0, 1.0);
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
        Example_2_3 example = new Example_2_3();
        example.start();
    }
}
