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

public class Example_7_1
{
    public static final int WIDTH = 1080;
    public static final int HEIGHT = 720;
    public static final String TITLE = "Example 7-1: Creating a Display List (press x or y to rotate, i to reset)";
    public static final int FPS_CAP = 60;

    private int theTorus;
   
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
    
    public void torus(int numc, int numt) {
        double twopi = 2 * Math.PI;
        
        for (int i = 0; i < numc; i++) {
            GL11.glBegin(GL11.GL_QUAD_STRIP);
            for (int j = 0; j <= numt; j++) {
                for (int k = 1; k >= 0; k--) {
                    double s = (i + k) % numc + 0.5;
                    double t = (double) (j % numt);
                    double x = (1 + .1 * Math.cos(s * twopi / numc)) * Math.cos(t * twopi / numt);
                    double y = (1 + .1 * Math.cos(s * twopi / numc)) * Math.sin(t * twopi / numt);
                    double z = .1 * Math.sin(s * twopi / numc);
                    GL11.glVertex3d(x, y, z);
                }
            }
            GL11.glEnd();
        }
    }

    
    public void init() {
        theTorus = GL11.glGenLists(1);
        GL11.glNewList(theTorus, GL11.GL_COMPILE);
        torus(8, 25);
        GL11.glEndList();
        
        GL11.glShadeModel(GL11.GL_FLAT);
        GL11.glClearColor(0.0f, 0.0f, 0.0f, 1.0f);
    }

    public void display() {
        GL11.glClear(GL11.GL_COLOR_BUFFER_BIT);
        GL11.glColor3f(1.0f, 1.0f, 1.0f);
        GL11.glCallList(theTorus);
        GL11.glFlush();
    }

    public void mouse() {
        if (Mouse.isButtonDown(0)) {
            System.out.println("Mouse button 0 is down");
        }
    }
    
    public void keyboard() {
        if (Keyboard.isKeyDown(Keyboard.KEY_X)) {
            GL11.glRotatef( 2f, 1.0f, 0.0f, 0.0f);
        }
        if (Keyboard.isKeyDown(Keyboard.KEY_W)) {
            GL11.glRotatef(-2f, 1.0f, 0.0f, 0.0f);
        }
        if (Keyboard.isKeyDown(Keyboard.KEY_S)) {
            GL11.glRotatef( 2f, 1.0f, 0.0f, 0.0f);
        }

        if (Keyboard.isKeyDown(Keyboard.KEY_Y)) {
            GL11.glRotatef(2f, 0.0f, 1.0f, 0.0f);
        }
        if (Keyboard.isKeyDown(Keyboard.KEY_A)) {
            GL11.glRotatef(-2f, 0.0f, 1.0f, 0.0f);
        }
        if (Keyboard.isKeyDown(Keyboard.KEY_D)) {
            GL11.glRotatef(2f, 0.0f, 1.0f, 0.0f);
        }

        if (Keyboard.isKeyDown(Keyboard.KEY_I)) {
            GL11.glLoadIdentity();
            GLU.gluLookAt(0f, 0f, 10f, 0f, 0f, 0f, 0f, 1f, 0f);
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
        GLU.gluPerspective(30.0f, fw / fh, 1.0f, 100.0f);
        GL11.glMatrixMode(GL11.GL_MODELVIEW);
        GL11.glLoadIdentity();
        GLU.gluLookAt(0f, 0f, 10f, 0f, 0f, 0f, 0f, 1f, 0f);
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
        Example_7_1 example = new Example_7_1();
        example.start();
    }
}
