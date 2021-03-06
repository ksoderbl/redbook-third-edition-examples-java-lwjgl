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

public class Example_5_1
{
    public static final int WIDTH = 1080;
    public static final int HEIGHT = 720;
    public static final String TITLE = "Example 5-1: Drawing a Lit Sphere";
    public static final int FPS_CAP = 60;
    
    public FloatBuffer makeFloatBuffer(float[] array) {
        FloatBuffer buffer = BufferUtils.createFloatBuffer(array.length);
        buffer.put(array);
        buffer.flip();
        return buffer;
    }
    
    public void init() {
        float matSpecular[] = { 1.0f, 1.0f, 1.0f, 1.0f };
        float matShininess[] = { 50.0f, 0.0f, 0.0f, 0.0f };
        float lightPosition[] = { 1.0f, 1.0f, 1.0f, 0.0f };
        float whiteLight[] = { 1.0f, 1.0f, 1.0f, 1.0f };
        
        GL11.glMaterial(GL11.GL_FRONT, GL11.GL_SPECULAR, makeFloatBuffer(matSpecular));
        GL11.glMaterial(GL11.GL_FRONT, GL11.GL_SHININESS, makeFloatBuffer(matShininess));
        
        GL11.glLight(GL11.GL_LIGHT0, GL11.GL_POSITION, makeFloatBuffer(lightPosition));
        GL11.glLight(GL11.GL_LIGHT0, GL11.GL_DIFFUSE, makeFloatBuffer(whiteLight));
        GL11.glLight(GL11.GL_LIGHT0, GL11.GL_SPECULAR, makeFloatBuffer(whiteLight));

        GL11.glEnable(GL11.GL_LIGHTING);
        GL11.glEnable(GL11.GL_LIGHT0);
        GL11.glEnable(GL11.GL_DEPTH_TEST);

        // useful?
        GL11.glEnable(GL11.GL_NORMALIZE);
    }

    
//    radius
//    The radius of the sphere. 
//    slices
//    The number of subdivisions around the Z axis (similar to lines of longitude). 
//    stacks
//    The number of subdivisions along the Z axis (similar to lines of latitude). 
    public void solidSphere(double radius, int slices, int stacks) {
        float w2 = 1.0f;
        GL11.glBegin(GL11.GL_LINE_LOOP);
        GL11.glVertex3f(w2, w2, w2);
        GL11.glVertex3f(w2, -w2, w2);
        GL11.glVertex3f(-w2, -w2, w2);
        GL11.glVertex3f(-w2, w2, w2);
        GL11.glEnd();
        
        GL11.glBegin(GL11.GL_LINE_LOOP);
        GL11.glVertex3f(w2, w2, -w2);
        GL11.glVertex3f(w2, -w2, -w2);
        GL11.glVertex3f(-w2, -w2, -w2);
        GL11.glVertex3f(-w2, w2, -w2);
        GL11.glEnd();

        GL11.glBegin(GL11.GL_LINES);
        GL11.glVertex3f(w2, w2, w2);
        GL11.glVertex3f(w2, w2, -w2);
        GL11.glVertex3f(w2, -w2, w2);
        GL11.glVertex3f(w2, -w2, -w2);
        GL11.glVertex3f(-w2, -w2, w2);
        GL11.glVertex3f(-w2, -w2, -w2);
        GL11.glVertex3f(-w2, w2, w2);
        GL11.glVertex3f(-w2, w2, -w2);
        GL11.glEnd();
        
        double twopi = 2 * Math.PI;
        
        // Normals are not correct
        
//        // torus
//        for (int i = 0; i < stacks; i++) {
//            GL11.glBegin(GL11.GL_QUAD_STRIP);
//            for (int j = 0; j <= slices; j++) {
//                for (int k = 1; k >= 0; k--) {
//                    double s = (i + k) % stacks + 0.5;
//                    double t = (double) (j % slices);
//                    double x = (1 + .3 * Math.cos(s * twopi / stacks)) * Math.cos(t * twopi / slices);
//                    double y = (1 + .3 * Math.cos(s * twopi / stacks)) * Math.sin(t * twopi / slices);
//                    double z = .3 * Math.sin(s * twopi / stacks);
//
//                    GL11.glNormal3d(x, y, z);
//                    GL11.glVertex3d(x, y, z);
//                }
//            }
//            GL11.glEnd();
//        }

//        // hourglass
//        for (int i = 0; i < stacks; i++) {
//            GL11.glBegin(GL11.GL_QUAD_STRIP);
//            for (int j = 0; j <= slices; j++) {
//                for (int k = 1; k >= 0; k--) {
//                    double s = (i + k) % stacks + 0.5;
//                    double t = (double) (j % slices);
//                    double x = radius * Math.cos(t * twopi / slices) * Math.sin(s * twopi / stacks);
//                    double y = radius * Math.sin(t * twopi / slices) * Math.sin(s * twopi / stacks);
//                    double z = radius * Math.sin(s * twopi / stacks);
//                    
//                    GL11.glNormal3d(x, y, z);
//                    GL11.glVertex3d(x, y, z);
//                }
//            }
//            GL11.glEnd();
//        }
//

        // sphere
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
    
    public void display() {
        GL11.glClear(GL11.GL_COLOR_BUFFER_BIT | GL11.GL_DEPTH_BUFFER_BIT);
        //GL11.glLoadIdentity();
        //GLU.gluLookAt(2.0f, 2.0f, 5.0f, 0.0f, 0.0f, 0.0f, 0.0f, 1.0f, 0.0f);
        solidSphere(1.0, 60, 60);
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
        GLU.gluLookAt(2f, 2f, 5f, 0f, 0f, 0f, 0f, 1f, 0f);
//        
//        GL11.glViewport(0,0, w, h);
//        GL11.glMatrixMode(GL11.GL_PROJECTION);
//        GL11.glLoadIdentity();
//        GL11.glFrustum(-1.0, 1.0, -1.0, 1.0, 1.5, 20.0);
//        GL11.glMatrixMode(GL11.GL_MODELVIEW);
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
        Example_5_1 example = new Example_5_1();
        example.start();
    }
}
