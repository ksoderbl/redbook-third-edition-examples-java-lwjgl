package examples;

import java.nio.FloatBuffer;

import org.lwjgl.BufferUtils;
import org.lwjgl.LWJGLException;
import org.lwjgl.input.Mouse;
import org.lwjgl.input.Keyboard;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.DisplayMode;
import org.lwjgl.opengl.GL11;

// Using this we don't have to write GL11. everywhere.
// import static org.lwjgl.opengl.GL11.*;

public class Example_6_3
{
    public static final int WIDTH = 1080;
    public static final int HEIGHT = 720;
    public static final String TITLE = "Example 6-3: Antialiased Lines (press r to rotate)";
    public static final int FPS_CAP = 60;

    private float rotAngle = 0.0f;
   
    public void init() {
        FloatBuffer values = BufferUtils.createFloatBuffer(16);
        
        GL11.glGetFloat(GL11.GL_LINE_WIDTH_GRANULARITY, values);
        System.out.println("GL_LINE_WIDTH_GRANULARITY value is " + values.get(0));
        GL11.glGetFloat(GL11.GL_LINE_WIDTH_RANGE, values);
        System.out.println("GL_LINE_WIDTH_RANGE values are " + values.get(0) + " " + values.get(1));
        
        GL11.glEnable(GL11.GL_LINE_SMOOTH);
        GL11.glEnable(GL11.GL_BLEND);
        GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
        GL11.glHint(GL11.GL_LINE_SMOOTH_HINT, GL11.GL_DONT_CARE);
        GL11.glLineWidth(2.5f);
        
        GL11.glClearColor(0.0f, 0.0f, 0.0f, 1.0f);
    }
    
//    public FloatBuffer makeFloatBuffer(float[] array) {
//        FloatBuffer buffer = BufferUtils.createFloatBuffer(array.length);
//        buffer.put(array);
//        buffer.flip();
//        return buffer;
//    }
    
    // Draw 2 diagonal lines to form an X
    public void display() {
        GL11.glClear(GL11.GL_COLOR_BUFFER_BIT);
        
        GL11.glColor3f(0.0f, 1.0f, 0.0f);
        GL11.glPushMatrix();
        GL11.glRotatef(-rotAngle, 0.0f, 0.0f, 0.1f);
        GL11.glBegin(GL11.GL_LINES);
        GL11.glVertex2f(-0.5f,  0.5f);
        GL11.glVertex2f( 0.5f, -0.5f);
        GL11.glEnd();
        GL11.glPopMatrix();

        GL11.glColor3f(0.0f, 0.0f, 1.0f);
        GL11.glPushMatrix();
        GL11.glRotatef(rotAngle, 0.0f, 0.0f, 0.1f);
        GL11.glBegin(GL11.GL_LINES);
        GL11.glVertex2f( 0.5f,  0.5f);
        GL11.glVertex2f(-0.5f, -0.5f);
        GL11.glEnd();
        GL11.glPopMatrix();
        
        GL11.glFlush();
    }

    public void mouse() {
        if (Mouse.isButtonDown(0)) {
            System.out.println("Mouse button 0 is down");
        }
    }
    
    public void keyboard() {
        if (Keyboard.isKeyDown(Keyboard.KEY_R)) {
            rotAngle += 5.0f;
            if (rotAngle >= 360f) {
                rotAngle -= 360f;
            }
        }
        if (Keyboard.isKeyDown(Keyboard.KEY_ESCAPE)) {
            Display.destroy();
            System.exit(0);
        }
    }
    
    public void reshape(int w, int h) {
        float fw = (float)w;
        float fh = (float)h;
        GL11.glViewport(0,0, w, h);
        GL11.glMatrixMode(GL11.GL_PROJECTION);
        GL11.glLoadIdentity();
        double d = 1.0;
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

            mouse();
            keyboard();
            
            display();
            
            if (Display.wasResized()) {
                reshape(Display.getWidth(), Display.getHeight());
            }
            
            Display.update();
        }
        
        Display.destroy();
    }
    
    public static void main(String[] args) {
        Example_6_3 example = new Example_6_3();
        example.start();
    }
}
