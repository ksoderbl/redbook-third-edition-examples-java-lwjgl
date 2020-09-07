package examples;

import org.lwjgl.LWJGLException;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.DisplayMode;
import org.lwjgl.opengl.GL11;

public class Window {
    public Window(int width, int height, String title) {
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
        GL11.glViewport(0, 0, width, height);
        System.out.println(GL11.glGetString(GL11.GL_VERSION));
    }
    
    public int getWidth() {
        return Display.getWidth();
    }
    
    public int getHeight() {
        return Display.getHeight();
    }

}
