package me.barni;

import org.lwjgl.glfw.GLFW;

public class MouseHandler {

    private static float x;
    private static float y;

    private static float prevX;
    private static float prevY;
    private static boolean buttons[] = new boolean[5];

    public MouseHandler() {
        x = 0;
        y = 0;
    }

    public static float getScrollX() {
        return x-prevX;
    }

    public static float getScrollY() {
        return y-prevY;
    }

    public static float getX() {
        return x;
    }

    public static float getY() {
        return y;
    }

    public static boolean getButton(int b) {
        return buttons[b];
    }

    public static void mousePosCallback(long window, double xpos, double ypos) {
        prevX = x;
        prevY = y;
        x = (float) xpos;
        y = (float) ypos;
    }

    public static void update()
    {
        prevX = x;
        prevY = y;
    }

    public static void mouseButtonCallback(long window, int button, int action, int mods) {
        if (button < buttons.length)
            buttons[button] = action == GLFW.GLFW_PRESS;
    }
}
