package examples;

//import java.nio.ByteBuffer;

//import org.lwjgl.BufferUtils;
import org.lwjgl.LWJGLException;
import org.lwjgl.input.Keyboard;
import org.lwjgl.input.Mouse;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.DisplayMode;
import org.lwjgl.opengl.GL11;
import org.lwjgl.util.glu.GLU;

// Using this we don't have to write GL11. everywhere.
// import static org.lwjgl.opengl.GL11.*;

public class Example_4_1
{
    public static final int WIDTH = 1080;
    public static final int HEIGHT = 720;
    public static final String TITLE = "Example 4-1: Drawing a Smooth-Shaded Triangle";
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

//    public ByteBuffer makeByteBuffer(byte[] array) {
//        ByteBuffer buffer = BufferUtils.createByteBuffer(array.length);
//        buffer.put(array);
//        buffer.flip();
//        return buffer;
//    }
    
    public void init() {
        GL11.glClearColor(0.0f, 0.0f, 0.0f, 0.0f);
        GL11.glShadeModel(GL11.GL_SMOOTH);
    }

    public void triangle() {
        GL11.glBegin(GL11.GL_TRIANGLES);
        GL11.glColor3f(1.0f, 0.0f, 0.0f);
        GL11.glVertex2f(5.0f, 5.0f);
        GL11.glColor3f(0.0f, 1.0f, 0.0f);
        GL11.glVertex2f(25.0f, 5.0f);
        GL11.glColor3f(0.0f, 0.0f, 1.0f);
        GL11.glVertex2f(5.0f, 25.0f);
        GL11.glEnd();
    }
    
    public void display() {
        GL11.glClear(GL11.GL_COLOR_BUFFER_BIT);
        triangle();
        GL11.glFlush();
    }

    public void mouse() {
        if (Mouse.isButtonDown(0)) {
            System.out.println("Mouse button 0 is down");
        }
    }
    
    public void keyboard() {
        if (Keyboard.isKeyDown(Keyboard.KEY_ESCAPE)) {
            Display.destroy();
            System.exit(0);
        }
    }
    
    public void reshape(int w, int h) {
        float fw, fh;
        fw = (float)w;
        fh = (float)h;
        GL11.glViewport(0,0, w, h);
        GL11.glMatrixMode(GL11.GL_PROJECTION);
        GL11.glLoadIdentity();
        GLU.gluOrtho2D(0.0f, 30.0f, 0.0f, 30.0f);
        GL11.glMatrixMode(GL11.GL_MODELVIEW);
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
        Example_4_1 example = new Example_4_1();
        example.start();
    }
}
