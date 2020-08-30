package examples;

//import java.nio.FloatBuffer;

//import org.lwjgl.BufferUtils;
import org.lwjgl.LWJGLException;
import org.lwjgl.input.Mouse;
import org.lwjgl.input.Keyboard;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.DisplayMode;
import org.lwjgl.opengl.GL11;

// Using this we don't have to write GL11. everywhere.
// import static org.lwjgl.opengl.GL11.*;

public class Example_6_1
{
    public static final int WIDTH = 1080;
    public static final int HEIGHT = 720;
    public static final String TITLE = "Example 6-1: Blending example (press t to toggle draw order)";
    public static final int FPS_CAP = 60;

    private boolean leftFirst = true;
    private boolean keyTIsDown = false;
   
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
        GL11.glEnable(GL11.GL_BLEND);
        GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
        GL11.glShadeModel(GL11.GL_FLAT);
        GL11.glClearColor(0.0f, 0.0f, 0.0f, 1.0f);
    }
    
//    public FloatBuffer makeFloatBuffer(float[] array) {
//        FloatBuffer buffer = BufferUtils.createFloatBuffer(array.length);
//        buffer.put(array);
//        buffer.flip();
//        return buffer;
//    }
    
    public void drawLeftTriangle() {
        // draw yellow triangle on LHS of screen
        GL11.glBegin(GL11.GL_TRIANGLES);
        GL11.glColor4f(1.0f, 1.0f, 0.0f, 0.75f);
        GL11.glVertex3f(0.1f,  0.9f,  0.0f);
        GL11.glVertex3f(0.1f,  0.1f,  0.0f);
        GL11.glVertex3f(0.7f,  0.5f,  0.0f);
        GL11.glEnd();
    }

    public void drawRightTriangle() {
        // draw cyan triangle on RHS of screen
        GL11.glBegin(GL11.GL_TRIANGLES);
        GL11.glColor4f(0.0f, 1.0f, 1.0f, 0.75f);
        GL11.glVertex3f(0.9f,  0.9f,  0.0f);
        GL11.glVertex3f(0.3f,  0.5f,  0.0f);
        GL11.glVertex3f(0.9f,  0.1f,  0.0f);
        GL11.glEnd();
    }

    // Draw 2 diagonal lines to form an X
    public void display() {
        GL11.glClear(GL11.GL_COLOR_BUFFER_BIT);
        
        if (leftFirst) {
            drawLeftTriangle();
            drawRightTriangle();
        }
        else {
            drawRightTriangle();
            drawLeftTriangle();
        }
        GL11.glFlush();
    }

    public void reshape(int w, int h) {
        GL11.glViewport(0, 0, w, h);
        GL11.glMatrixMode(GL11.GL_PROJECTION);
        GL11.glLoadIdentity();
        if (w <= h) {
            GL11.glOrtho(0.0, 1.0, 0.0, 1.0, -1.0, 1.0);
        }
        else {
            GL11.glOrtho(0.0, 1.0, 0.0, 1.0, -1.0, 1.0);
        }
        GL11.glMatrixMode(GL11.GL_MODELVIEW);
        GL11.glLoadIdentity();
    }
    
    public void mouse() {
        if (Mouse.isButtonDown(0)) {
            System.out.println("Mouse button 0 is down");
        }
    }
    
    public void keyboard() {
        if (Keyboard.isKeyDown(Keyboard.KEY_T)) {
            if (!keyTIsDown) {
                leftFirst = !leftFirst;
                keyTIsDown = true;
            }
        }
        else {
            keyTIsDown = false;
        }
        if (Keyboard.isKeyDown(Keyboard.KEY_ESCAPE)) {
            Display.destroy();
            System.exit(0);
        }
    }
    
    public void start() {
        createWindow(WIDTH, HEIGHT, TITLE);
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
        Example_6_1 example = new Example_6_1();
        example.start();
    }
}
