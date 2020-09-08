package examples;

import org.lwjgl.input.Mouse;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.GL11;

// Using this we don't have to write GL11. everywhere.
// import static org.lwjgl.opengl.GL11.*;

public class Example_2_1
{
    public static final int WIDTH = 1080;
    public static final int HEIGHT = 720;
    public static final String TITLE = "Example 2-1: Reshape Callback Function";
    public static final int FPS_CAP = 60;
    
    private float spinAngle = 0.0f;

    public void init() {
        GL11.glClearColor(0.0f, 0.0f, 0.0f, 1.0f);
        GL11.glShadeModel(GL11.GL_FLAT);
    }
    
    public void display() {
        GL11.glClear(GL11.GL_COLOR_BUFFER_BIT);
        GL11.glPushMatrix();
        GL11.glTranslatef(125.0f, 125.0f, 0.0f);
        GL11.glRotatef(spinAngle,  0.0f,  0.0f,  1.0f);
        GL11.glColor3f(1.0f,  1.0f,  1.0f);
        GL11.glRectf(-125.0f, -125.0f, 125.0f, 125.0f);
        GL11.glPopMatrix();
    }
    
    public void spinDisplay() {
        spinAngle = spinAngle + 2.0f;
        if (spinAngle > 360.0f) {
            spinAngle -= 360.0f;
        }
    }
    
    public void reshape(int w, int h) {
        GL11.glViewport(0, 0, w, h);
        GL11.glMatrixMode(GL11.GL_PROJECTION);
        GL11.glLoadIdentity();
        GL11.glOrtho(0.0, w, 0.0, h, -1.0, 1.0);
    }
    
    public void start() {
        Window window = new Window(WIDTH, HEIGHT, TITLE);
        reshape(window.getWidth(), window.getHeight());
        init();

        while (!Display.isCloseRequested()) {
            Display.sync(FPS_CAP);
            
            if (!Mouse.isButtonDown(0)) {
                spinDisplay();
            }
            
            if (Display.wasResized()) {
                reshape(Display.getWidth(), Display.getHeight());
            }

            display();
            
            Display.update();
        }
        
        Display.destroy();
    }
    
    public static void main(String[] args) {
        Example_2_1 example = new Example_2_1();
        example.start();
    }
}
