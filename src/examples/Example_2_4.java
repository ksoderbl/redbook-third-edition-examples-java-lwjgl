package examples;

import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.GL11;

// Using this we don't have to write GL11. everywhere.
// import static org.lwjgl.opengl.GL11.*;

public class Example_2_4
{
    public static final int WIDTH = 1080;
    public static final int HEIGHT = 720;
    public static final String TITLE = "Example 2-4: Other Constructs Between glBegin() and glEnd()";
    public static final int FPS_CAP = 60;
    
    private int frameNumber = 0;
    
    public void init() {
        GL11.glClearColor(0.0f, 0.0f, 0.2f, 1.0f);
        GL11.glShadeModel(GL11.GL_SMOOTH);
        GL11.glLineWidth(4.0f);
    }
    
    public void display() {
        int circlePoints = 100;
        int offset = frameNumber;
        
        double r = 4.0;
        
        GL11.glClear(GL11.GL_COLOR_BUFFER_BIT);
        
        GL11.glPushMatrix();

        GL11.glBegin(GL11.GL_LINE_LOOP);
        for (int i = 0; i < circlePoints; i++) {
            float f = (float)((i + offset) % circlePoints) / (float)circlePoints;
            double angle = 2 * Math.PI * i / circlePoints;
            float x = (float)(r * Math.cos(angle));
            float y = (float)(r * Math.sin(angle));
            GL11.glColor3f((1.0f - f) / 2.0f, (1.0f - f) / 2.0f, 1.2f - f);
            GL11.glVertex2f(x, y);
        }
        GL11.glEnd();
        GL11.glPopMatrix();
        frameNumber ++;
    }
    
    public void reshape(int w, int h) {
        double a = (double)w / (double)h;
        GL11.glViewport(0, 0, w, h);
        GL11.glMatrixMode(GL11.GL_PROJECTION);
        GL11.glLoadIdentity();
        GL11.glOrtho(-5.0 * a, 5.0 * a, -5.0, 5.0, -1.0, 1.0);
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
        Example_2_4 example = new Example_2_4();
        example.start();
    }
}
