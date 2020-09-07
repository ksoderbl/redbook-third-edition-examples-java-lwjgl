package examples;

import java.nio.ByteBuffer;

import org.lwjgl.BufferUtils;
import org.lwjgl.LWJGLException;
import org.lwjgl.input.Keyboard;
import org.lwjgl.input.Mouse;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.DisplayMode;
import org.lwjgl.opengl.GL11;
import org.lwjgl.util.glu.GLU;

// Using this we don't have to write GL11. everywhere.
// import static org.lwjgl.opengl.GL11.*;

public class Example_9_1
{
    public static final int WIDTH = 1080;
    public static final int HEIGHT = 720;
    public static final String TITLE = "Example 9-1: Texture-Mapped Checkerboard";
    public static final int FPS_CAP = 60;

    private final int checkImageWidth = 64;
    private final int checkImageHeight = 64;
    private ByteBuffer checkImage;
    
    private int texName;
   
    public ByteBuffer makeByteBuffer(byte[] array) {
        ByteBuffer buffer = BufferUtils.createByteBuffer(array.length);
        buffer.put(array);
        buffer.flip();
        return buffer;
    }
    
    public ByteBuffer makeCheckImage() {
        byte[] checkImage = new byte[checkImageHeight * checkImageWidth * 4];
        int index = 0;
        for (int i = 0; i < checkImageHeight; i++) {
            for (int j = 0; j < checkImageWidth; j++) {
                int ii = ((i & 0x8) == 0) ? 1 : 0;
                int jj = ((j & 0x8) == 0) ? 1 : 0;
                int c = (ii ^ jj) * 255;
                checkImage[index++] = (byte) c;
                checkImage[index++] = (byte) c;
                checkImage[index++] = (byte) c;
                checkImage[index++] = (byte) 255;
            }
        }
        return makeByteBuffer(checkImage);
    }
    
    public void init() {
        GL11.glClearColor(0.5f, 0.0f, 0.0f, 1.0f);
        GL11.glShadeModel(GL11.GL_FLAT);
        GL11.glEnable(GL11.GL_DEPTH_TEST);
        checkImage = makeCheckImage();
        GL11.glPixelStorei(GL11.GL_UNPACK_ALIGNMENT, 1);
        
        texName = GL11.glGenTextures();
        GL11.glBindTexture(GL11.GL_TEXTURE_2D, texName);
        
        GL11.glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_WRAP_S, GL11.GL_REPEAT);
        GL11.glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_WRAP_T, GL11.GL_REPEAT);
        GL11.glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_MAG_FILTER, GL11.GL_NEAREST);
        GL11.glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_MIN_FILTER, GL11.GL_NEAREST);
        GL11.glTexImage2D(GL11.GL_TEXTURE_2D, 0, GL11.GL_RGBA, checkImageWidth, checkImageHeight,
            0, GL11.GL_RGBA, GL11.GL_UNSIGNED_BYTE, checkImage);
    }

    public void display() {
        GL11.glClear(GL11.GL_COLOR_BUFFER_BIT | GL11.GL_DEPTH_BUFFER_BIT);
        GL11.glEnable(GL11.GL_TEXTURE_2D);
        GL11.glTexEnvi(GL11.GL_TEXTURE_ENV, GL11.GL_TEXTURE_ENV_MODE, GL11.GL_REPLACE);
        GL11.glBindTexture(GL11.GL_TEXTURE_2D, texName);

        GL11.glBegin(GL11.GL_QUADS);
        GL11.glTexCoord2f(0.0f, 0.0f); GL11.glVertex3f(-2.0f, -1.0f, 0.0f);
        GL11.glTexCoord2f(0.0f, 1.0f); GL11.glVertex3f(-2.0f,  1.0f, 0.0f);
        GL11.glTexCoord2f(1.0f, 1.0f); GL11.glVertex3f( 0.0f,  1.0f, 0.0f);
        GL11.glTexCoord2f(1.0f, 0.0f); GL11.glVertex3f( 0.0f, -1.0f, 0.0f);
        
        float r = (float) Math.sqrt(2.0);
        GL11.glTexCoord2f(0.0f, 0.0f); GL11.glVertex3f( 1.0f, -1.0f, 0.0f);
        GL11.glTexCoord2f(0.0f, 1.0f); GL11.glVertex3f( 1.0f,  1.0f, 0.0f);
        GL11.glTexCoord2f(1.0f, 1.0f); GL11.glVertex3f( r + 1f,  1.0f, -r);
        GL11.glTexCoord2f(1.0f, 0.0f); GL11.glVertex3f( r + 1f, -1.0f, -r);
        GL11.glEnd();
        
        GL11.glFlush();
        GL11.glDisable(GL11.GL_TEXTURE_2D);
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
        float fw = (float)w;
        float fh = (float)h;
        GL11.glViewport(0,0, w, h);
        GL11.glMatrixMode(GL11.GL_PROJECTION);
        GL11.glLoadIdentity();
        GLU.gluPerspective(60.0f, fw / fh, 1.0f, 30.0f);
        GL11.glMatrixMode(GL11.GL_MODELVIEW);
        GL11.glLoadIdentity();
        GL11.glTranslatef(0.0f, 0.0f, -3.6f);
    }
    
    public void start() {
        Window window = new Window(WIDTH, HEIGHT, TITLE);
        reshape(window.getWidth(), window.getHeight());
        init();

        while (!Display.isCloseRequested()) {
            Display.sync(FPS_CAP);

            mouse();
            keyboard();

            if (Display.wasResized()) {
                reshape(Display.getWidth(), Display.getHeight());
            }
            
            display();
            
            Display.update();
        }
        
        Display.destroy();
    }
    
    public static void main(String[] args) {
        Example_9_1 example = new Example_9_1();
        example.start();
    }
}
