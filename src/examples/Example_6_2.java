package examples;

import java.nio.FloatBuffer;

import org.lwjgl.BufferUtils;
import org.lwjgl.input.Keyboard;
import org.lwjgl.input.Mouse;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.GL11;
import org.lwjgl.util.glu.GLU;

// Using this we don't have to write GL11. everywhere.
// import static org.lwjgl.opengl.GL11.*;

public class Example_6_2
{
    public static final int WIDTH = 1080;
    public static final int HEIGHT = 720;
    public static final String TITLE = "Example 6-2: Three-Dímensional Blending (press a to animate, r to reset)";
    public static final int FPS_CAP = 60;
    
    public static final float MAXZ = 8.0f;
    public static final float MINZ = -8.0f;
    public static final float ZINC = 0.1f;
    
    private float solidZ = MAXZ;
    private float transparentZ = MINZ;
    private int sphereList;
    private int cubeList;
    private boolean animation = true;
    
    public FloatBuffer makeFloatBuffer(float[] array) {
        FloatBuffer buffer = BufferUtils.createFloatBuffer(array.length);
        buffer.put(array);
        buffer.flip();
        return buffer;
    }

//  radius
//  The radius of the sphere. 
//  slices
//  The number of subdivisions around the Z axis (similar to lines of longitude). 
//  stacks
//  The number of subdivisions along the Z axis (similar to lines of latitude). 
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
  
    public void solidCube(double size) {
        float s2 = (float) size / 2.0f;
        
        GL11.glBegin(GL11.GL_QUADS);
        GL11.glNormal3f(0.0f, 0.0f, 1.0f);
        GL11.glVertex3f( s2,  s2,  s2); /* front face */
        GL11.glVertex3f(-s2,  s2,  s2);
        GL11.glVertex3f(-s2, -s2,  s2);
        GL11.glVertex3f( s2, -s2,  s2);

        GL11.glNormal3f(1.0f, 0.0f, 0.0f);
        GL11.glVertex3f( s2,  s2, -s2); /* right face */
        GL11.glVertex3f( s2,  s2,  s2);
        GL11.glVertex3f( s2, -s2,  s2);
        GL11.glVertex3f( s2, -s2, -s2);

        GL11.glNormal3f(0.0f, 0.0f, -1.0f);
        GL11.glVertex3f(-s2,  s2, -s2); /* back face */
        GL11.glVertex3f( s2,  s2, -s2);
        GL11.glVertex3f( s2, -s2, -s2);
        GL11.glVertex3f(-s2, -s2, -s2);


        GL11.glNormal3f(-1.0f, 0.0f, 0.0f);
        GL11.glVertex3f(-s2,  s2,  s2); /* left face */
        GL11.glVertex3f(-s2,  s2, -s2);
        GL11.glVertex3f(-s2, -s2, -s2);
        GL11.glVertex3f(-s2, -s2,  s2);

        GL11.glNormal3f(0.0f, 1.0f, 0.0f);
        GL11.glVertex3f( s2,  s2, -s2); /* top face */
        GL11.glVertex3f(-s2,  s2, -s2);
        GL11.glVertex3f(-s2,  s2,  s2);
        GL11.glVertex3f( s2,  s2,  s2);

        GL11.glNormal3f(0.0f, -1.0f, 0.0f);
        GL11.glVertex3f( s2, -s2,  s2); /* bottom face */
        GL11.glVertex3f(-s2, -s2,  s2);
        GL11.glVertex3f(-s2, -s2, -s2);
        GL11.glVertex3f( s2, -s2, -s2);
        GL11.glEnd();
    }
    
    public void init() {
        float matSpecular[]  = { 1.0f, 1.0f, 1.0f, 0.15f };
        float matShininess[] = { 100.0f, 0.0f, 0.0f, 0.0f };
        float lightPosition[] = { 0.5f, 0.5f, 1.0f, 0.0f };

        GL11.glMaterial(GL11.GL_FRONT, GL11.GL_SPECULAR, makeFloatBuffer(matSpecular));
        GL11.glMaterial(GL11.GL_FRONT, GL11.GL_SHININESS, makeFloatBuffer(matShininess));
        GL11.glLight(GL11.GL_LIGHT0, GL11.GL_POSITION, makeFloatBuffer(lightPosition));
        
        GL11.glEnable(GL11.GL_LIGHTING);
        GL11.glEnable(GL11.GL_LIGHT0);
        GL11.glEnable(GL11.GL_DEPTH_TEST);
        
        // useful?
        GL11.glEnable(GL11.GL_NORMALIZE);
        
        sphereList = GL11.glGenLists(1);
        GL11.glNewList(sphereList, GL11.GL_COMPILE);
        solidSphere(0.4, 16, 16);
        GL11.glEndList();
        
        cubeList = GL11.glGenLists(1);
        GL11.glNewList(cubeList, GL11.GL_COMPILE);
        solidCube(0.6);
        GL11.glEndList();
    }

    public void display() {
        if (animation) {
            animate();
        }
        
        float matSolid[]  = { 0.75f, 0.75f, 0.0f, 1.0f };
        float matZero[]  = { 0.0f, 0.0f, 0.0f, 1.0f };
        float matTransparent[]  = { 0.0f, 0.8f, 0.8f, 0.6f };
        float matEmission[]  = { 0.0f, 0.3f, 0.3f, 0.6f };
        
        GL11.glClear(GL11.GL_COLOR_BUFFER_BIT | GL11.GL_DEPTH_BUFFER_BIT);

        GL11.glPushMatrix();
        GL11.glTranslatef(-0.5f, -0.15f, solidZ);
        GL11.glMaterial(GL11.GL_FRONT, GL11.GL_EMISSION, makeFloatBuffer(matZero));
        GL11.glMaterial(GL11.GL_FRONT, GL11.GL_DIFFUSE, makeFloatBuffer(matSolid));
        GL11.glCallList(sphereList);
        GL11.glPopMatrix();
        
        GL11.glPushMatrix();
        GL11.glTranslatef(0.15f, 0.15f, transparentZ);
        GL11.glRotatef(15.0f, 1.0f, 1.0f, 0.0f);
        GL11.glRotatef(30.0f, 0.0f, 1.0f, 0.0f);
        GL11.glMaterial(GL11.GL_FRONT, GL11.GL_EMISSION, makeFloatBuffer(matEmission));
        GL11.glMaterial(GL11.GL_FRONT, GL11.GL_DIFFUSE, makeFloatBuffer(matTransparent));
        GL11.glEnable(GL11.GL_BLEND);
        GL11.glDepthMask(false);
        GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE);
        GL11.glCallList(cubeList);
        GL11.glDepthMask(true);
        GL11.glDisable(GL11.GL_BLEND);
        GL11.glPopMatrix();

    }
    
    public void mouse() {
        if (Mouse.isButtonDown(0)) {
            System.out.println("Mouse button 0 is down");
        }
    }

    public void animate() {
        if (solidZ <= MINZ || transparentZ >= MAXZ) {
            animation = false;
        }
        else {
            solidZ -= ZINC;
            transparentZ += ZINC;
        }
    }
    
    public void keyboard() {
//        if (Keyboard.isKeyDown(Keyboard.KEY_F)) {
//            if (!keyFIsDown) {
//                if (fogMode == GL11.GL_EXP) {
//                    fogMode = GL11.GL_EXP2;
//                    System.out.println("Fog mode is GL_EXP2");
//                }
//                else if (fogMode == GL11.GL_EXP2) {
//                    fogMode = GL11.GL_LINEAR;
//                    System.out.println("Fog mode is GL_LINEAR");
//                }
//                else if (fogMode == GL11.GL_LINEAR) {
//                    fogMode = GL11.GL_EXP;
//                    System.out.println("Fog mode is GL_EXP");
//                }
//                GL11.glFogi(GL11.GL_FOG_MODE, fogMode);
//                keyFIsDown = true;
//            }
//        }
//        else {
//            keyFIsDown = false;
//        }
//        
//        if (Keyboard.isKeyDown(Keyboard.KEY_X)) {
//            GL11.glRotatef( 2f, 1.0f, 0.0f, 0.0f);
//        }
//        if (Keyboard.isKeyDown(Keyboard.KEY_W)) {
//            GL11.glRotatef(-2f, 1.0f, 0.0f, 0.0f);
//        }
//        if (Keyboard.isKeyDown(Keyboard.KEY_S)) {
//            GL11.glRotatef( 2f, 1.0f, 0.0f, 0.0f);
//        }
//
//        if (Keyboard.isKeyDown(Keyboard.KEY_Y)) {
//            GL11.glRotatef(2f, 0.0f, 1.0f, 0.0f);
//        }
//        if (Keyboard.isKeyDown(Keyboard.KEY_A)) {
//            GL11.glRotatef(-2f, 0.0f, 1.0f, 0.0f);
//        }
//        if (Keyboard.isKeyDown(Keyboard.KEY_D)) {
//            GL11.glRotatef(2f, 0.0f, 1.0f, 0.0f);
//        }

        if (Keyboard.isKeyDown(Keyboard.KEY_A)) {
            solidZ = MAXZ;
            transparentZ = MINZ;
            animation = true;
        }
        if (Keyboard.isKeyDown(Keyboard.KEY_R)) {
            solidZ = MAXZ;
            transparentZ = MINZ;
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
//        float fw = (float)w;
//        float fh = (float)h;
//        GL11.glViewport(0, 0, w, h);
//        GL11.glMatrixMode(GL11.GL_PROJECTION);
//        GL11.glLoadIdentity();
//        float r = 1.5f;
//        GL11.glOrtho(-r, r, -r * fh / fw, r * fh / fw, -10.0, 10.0);
//        GL11.glMatrixMode(GL11.GL_MODELVIEW);
//        GL11.glLoadIdentity();
        
        float fw = (float)w;
        float fh = (float)h;
        GL11.glViewport(0,0, w, h);
        GL11.glMatrixMode(GL11.GL_PROJECTION);
        GL11.glLoadIdentity();
        GLU.gluPerspective(60.0f, fw / fh, 1.0f, 30.0f);
        GL11.glMatrixMode(GL11.GL_MODELVIEW);
        GL11.glLoadIdentity();
        GL11.glTranslatef(0.0f, 0.0f, -10.0f);
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
        Example_6_2 example = new Example_6_2();
        example.start();
    }
}
