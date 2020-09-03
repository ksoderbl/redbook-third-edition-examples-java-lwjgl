package examples;

import java.nio.FloatBuffer;

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

public class Example_6_5
{
    public static final int WIDTH = 1080;
    public static final int HEIGHT = 720;
    public static final String TITLE = "Example 6-5: Five Fogged Spheres in RGBA Mode (press f to change fog mode)";
    public static final int FPS_CAP = 60;
    
    private int fogMode;
    private boolean keyFIsDown = false;
    
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

    public FloatBuffer makeFloatBuffer(float[] array) {
        FloatBuffer buffer = BufferUtils.createFloatBuffer(array.length);
        buffer.put(array);
        buffer.flip();
        return buffer;
    }
    
    public void init() {
        float lightPosition[] = { 0.5f, 0.5f, 3.0f, 0.0f };
        float whiteLight[] = { 1.0f, 1.0f, 1.0f, 1.0f };
        
        GL11.glEnable(GL11.GL_DEPTH_TEST);
        
        GL11.glLight(GL11.GL_LIGHT0, GL11.GL_POSITION, makeFloatBuffer(lightPosition));
        GL11.glLight(GL11.GL_LIGHT0, GL11.GL_DIFFUSE, makeFloatBuffer(whiteLight));
        GL11.glLight(GL11.GL_LIGHT0, GL11.GL_SPECULAR, makeFloatBuffer(whiteLight));
        
        GL11.glEnable(GL11.GL_LIGHTING);
        GL11.glEnable(GL11.GL_LIGHT0);

        float matAmbient[]   = { 0.17450f, 0.01175f, 0.01175f, 0.0f };
        float matDiffuse[]   = { 0.61424f, 0.04136f, 0.04136f, 0.0f };
        float matSpecular[]  = { 0.727811f, 0.6269569f, 0.6269569f, 0.0f };
        float matShininess[] = { 0.6f * 128.0f, 0.0f, 0.0f, 0.0f };
        
        GL11.glMaterial(GL11.GL_FRONT, GL11.GL_AMBIENT, makeFloatBuffer(matAmbient));
        GL11.glMaterial(GL11.GL_FRONT, GL11.GL_DIFFUSE, makeFloatBuffer(matDiffuse));
        GL11.glMaterial(GL11.GL_FRONT, GL11.GL_SPECULAR, makeFloatBuffer(matSpecular));
        GL11.glMaterial(GL11.GL_FRONT, GL11.GL_SHININESS, makeFloatBuffer(matShininess));

        // useful?
        GL11.glEnable(GL11.GL_NORMALIZE);
        
        GL11.glEnable(GL11.GL_FOG);
        
        float fogColor[] = { 0.5f, 0.5f, 0.5f, 1.0f };
        fogMode = GL11.GL_EXP;
        GL11.glFogi(GL11.GL_FOG_MODE, fogMode);
        GL11.glFog(GL11.GL_FOG_COLOR, makeFloatBuffer(fogColor));
        GL11.glFogf(GL11.GL_FOG_DENSITY, 0.35f);
        GL11.glHint(GL11.GL_FOG_HINT, GL11.GL_DONT_CARE);
        GL11.glFogf(GL11.GL_FOG_START, 1.0f);
        GL11.glFogf(GL11.GL_FOG_END, 5.0f);
        
        GL11.glClearColor(fogColor[0], fogColor[1], fogColor[2], fogColor[3]);
    }

    
//    radius
//    The radius of the sphere. 
//    slices
//    The number of subdivisions around the Z axis (similar to lines of longitude). 
//    stacks
//    The number of subdivisions along the Z axis (similar to lines of latitude). 
    public void solidSphere(double radius, int slices, int stacks) {
        double twopi = 2 * Math.PI;

        for (int i = 0; i < stacks; i++) {
            GL11.glBegin(GL11.GL_QUAD_STRIP);
            for (int j = 0; j <= slices; j++) {
                for (int k = 1; k >= 0; k--) {
                    double s = (i + k) % stacks + 0.5;
                    double t = (double) (j % slices);
                    double x = radius * Math.cos(t * twopi / slices) * Math.sin(s * twopi / stacks);
                    double y = radius * Math.sin(t * twopi / slices) * Math.sin(s * twopi / stacks);
                    double z = radius * Math.cos(s * twopi / stacks);
                    
                    // Length of vector (x, y, z) equals radius, thefore divide each of x, y and z in the glNormal3d
                    // by radius.
                    
                    GL11.glNormal3d(x / radius, y / radius, z / radius);
                    GL11.glVertex3d(x, y, z);
                    
                }
            }
            GL11.glEnd();
        }
    }
    
    void renderSphere(float x, float y, float z) {
        GL11.glPushMatrix();
        GL11.glTranslatef(x, y, z);
        solidSphere(0.5, 20, 20);
        GL11.glPopMatrix();
    }
    
    public void display() {
        GL11.glClear(GL11.GL_COLOR_BUFFER_BIT | GL11.GL_DEPTH_BUFFER_BIT);

        renderSphere(-2.0f, -0.5f, -1.0f);
        renderSphere(-1.0f, -0.5f, -2.0f);
        renderSphere( 0.0f, -0.5f, -3.0f);
        renderSphere( 1.0f, -0.5f, -4.0f);
        renderSphere( 2.0f, -0.5f, -5.0f);
        
        GL11.glFlush();
    }
    
    public void mouse() {
        if (Mouse.isButtonDown(0)) {
            System.out.println("Mouse button 0 is down");
        }
    }
    
    public void keyboard() {
        if (Keyboard.isKeyDown(Keyboard.KEY_F)) {
            if (!keyFIsDown) {
                if (fogMode == GL11.GL_EXP) {
                    fogMode = GL11.GL_EXP2;
                    System.out.println("Fog mode is GL_EXP2");
                }
                else if (fogMode == GL11.GL_EXP2) {
                    fogMode = GL11.GL_LINEAR;
                    System.out.println("Fog mode is GL_LINEAR");
                }
                else if (fogMode == GL11.GL_LINEAR) {
                    fogMode = GL11.GL_EXP;
                    System.out.println("Fog mode is GL_EXP");
                }
                GL11.glFogi(GL11.GL_FOG_MODE, fogMode);
                keyFIsDown = true;
            }
        }
        else {
            keyFIsDown = false;
        }
        
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
            //GLU.gluLookAt(0f, 0f, 10f, 0f, 0f, 0f, 0f, 1f, 0f);
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
        float r = 2.5f;
        GL11.glOrtho(-r, r, -r * fh / fw, r * fh / fw, -4 * r, 4 * r);
        GL11.glMatrixMode(GL11.GL_MODELVIEW);
        GL11.glLoadIdentity();
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
        Example_6_5 example = new Example_6_5();
        example.start();
    }
}
