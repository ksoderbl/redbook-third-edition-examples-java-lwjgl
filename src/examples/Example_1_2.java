package examples;

import org.lwjgl.LWJGLException;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.DisplayMode;
import org.lwjgl.opengl.GL11;

// Using this we don't have to write GL11. everywhere.
// import static org.lwjgl.opengl.GL11.*;

public class Example_1_2
{
	public static final int WIDTH = 1080;
    public static final int HEIGHT = 720;
    public static final String TITLE = "Example 1-2: White Rectangle on a Black Background, improved";
    public static final int FPS_CAP = 60;

    public void createWindow(int width, int height, String title) {
    	try {
    		DisplayMode mode = new DisplayMode(width, height);
    		Display.setDisplayMode(mode);
        	Display.create();
        	Display.setTitle(title);
    	} catch (LWJGLException e) {
    		e.printStackTrace();
    		System.exit(1);
    	}
    	GL11.glViewport(0,0, width, height);
    	System.out.println(GL11.glGetString(GL11.GL_VERSION));
	}
    
    public void init() {
    	GL11.glClearColor(0.0f, 0.0f, 0.0f, 1.0f);
    	GL11.glMatrixMode(GL11.GL_PROJECTION);
    	GL11.glLoadIdentity();
    	GL11.glOrtho(0.0, 1.0, 0.0, 1.0, -1.0, 1.0);
    }
    
    public void display() {
    	GL11.glClear(GL11.GL_COLOR_BUFFER_BIT);
    	GL11.glColor3f(1.0f,  1.0f,  1.0f);
	    GL11.glBegin(GL11.GL_POLYGON);
	    GL11.glVertex3f(0.25f,  0.25f,  0);
	    GL11.glVertex3f(0.75f,  0.25f,  0);
	    GL11.glVertex3f(0.75f,  0.75f,  0);
	    GL11.glVertex3f(0.25f,  0.75f,  0);
	    GL11.glEnd();
	    GL11.glFlush();
    }
    
    public Example_1_2() {
    	createWindow(WIDTH, HEIGHT, TITLE);
    	init();

    	while (!Display.isCloseRequested()) {
			display();
		    Display.update();
		    Display.sync(FPS_CAP);
		}
		
		Display.destroy();
    }
    
    public static void main(String[] args) {
    	Example_1_2 example = new Example_1_2();
    }
}
