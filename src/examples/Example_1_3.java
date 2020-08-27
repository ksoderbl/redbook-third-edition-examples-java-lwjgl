package examples;

import org.lwjgl.LWJGLException;
import org.lwjgl.input.Mouse;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.DisplayMode;
import org.lwjgl.opengl.GL11;

// Using this we don't have to write GL11. everywhere.
// import static org.lwjgl.opengl.GL11.*;

public class Example_1_3
{
	public static final int WIDTH = 720;
    public static final int HEIGHT = 720;
    public static final String TITLE = "Example 1-3: Double-Buffered Program";
    public static final int FPS_CAP = 60;
    
    private float spinAngle = 0.0f;

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
    	GL11.glClearColor(0.0f, 0.0f, 0.0f, 1.0f);
    	GL11.glShadeModel(GL11.GL_FLAT);
    }
    
    public void display() {
    	GL11.glClear(GL11.GL_COLOR_BUFFER_BIT);
    	GL11.glPushMatrix();
    	GL11.glRotatef(spinAngle,  0.0f,  0.0f,  1.0f);
    	GL11.glColor3f(1.0f,  1.0f,  1.0f);
    	GL11.glRectf(-25.0f, -25.0f, 25.0f, 25.0f);
	    GL11.glPopMatrix();
    }
    
    public void spinDisplay() {
    	spinAngle = spinAngle + 2.0f;
    	if (spinAngle > 360.0f) {
    		spinAngle -= 360.0f;
    	}
    }
    
    public void reshape(int w, int h) {
    	GL11.glViewport(0,0, w, h);
    	GL11.glMatrixMode(GL11.GL_PROJECTION);
    	GL11.glLoadIdentity();
    	GL11.glOrtho(-50.0, 50.0, -50.0, 50.0, -1.0, 1.0);
    	GL11.glMatrixMode(GL11.GL_MODELVIEW);
    	GL11.glLoadIdentity();
    }
    
    public void start() {
    	createWindow(WIDTH, HEIGHT, TITLE);
    	init();

    	while (!Display.isCloseRequested()) {
    	    Display.sync(FPS_CAP);
    		
			display();
			
			if (!Mouse.isButtonDown(0)) {
				spinDisplay();
			}
			
	        if (Display.wasResized()) {
	        	reshape(Display.getWidth(), Display.getHeight());
	        }
	        
		    Display.update();
		}
		
		Display.destroy();
    }
    
    public static void main(String[] args) {
    	Example_1_3 example = new Example_1_3();
    	example.start();
    }
}
