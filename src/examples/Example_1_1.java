package examples;

import org.lwjgl.LWJGLException;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.DisplayMode;
import org.lwjgl.opengl.GL11;

public class Example_1_1
{
    public static final int WIDTH = 1080;
    public static final int HEIGHT = 720;
    public static final String TITLE = "Example 1-1: White Rectangle on a Black Background";
    public static final int FPS_CAP = 60;

    public Example_1_1() {
        Window window = new Window(WIDTH, HEIGHT, TITLE);
        System.out.println(GL11.glGetString(GL11.GL_VERSION));
        GL11.glClearColor(0.0f, 0.0f, 0.0f, 1.0f);

        GL11.glColor3f(1.0f,  1.0f,  1.0f);
        GL11.glOrtho(0, 1, 0, 1, -1, 1);
    }
    
    public void start() {
        while (!Display.isCloseRequested()) {
            GL11.glBegin(GL11.GL_POLYGON);
            GL11.glVertex3f(0.25f,  0.25f,  0);
            GL11.glVertex3f(0.75f,  0.25f,  0);
            GL11.glVertex3f(0.75f,  0.75f,  0);
            GL11.glVertex3f(0.25f,  0.75f,  0);
            GL11.glEnd();
            GL11.glFlush();
            Display.update();
            Display.sync(FPS_CAP);
        }
        Display.destroy();
    }
    
    public static void main(String[] args) {
        Example_1_1 example = new Example_1_1();
        example.start();
    }
}
