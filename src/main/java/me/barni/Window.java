package me.barni;

import org.joml.Vector2f;
import org.lwjgl.Version;
import org.lwjgl.glfw.*;
import org.lwjgl.opengl.GL;
import org.lwjgl.opengl.GL30;


import static org.lwjgl.system.MemoryUtil.NULL;

public class Window {
    private String title;
    private int width, height;
    private long pWindow;
    private ShaderProgram defaultProgram;


    public Window(String title, int width, int height) {
        System.out.println("Window created");
        System.out.println("Hello LWJGL - " + Version.getVersion() + "!");
        this.title = title;
        this.width = width;
        this.height = height;
        start();
    }

    public void init() {
        //Error callback
        GLFWErrorCallback.createPrint(System.err);

        //Init GLFW
        if (!GLFW.glfwInit()) {
            throw new IllegalStateException("Unable to initialize GLFW!");
        }

        //Configure GLFW
        GLFW.glfwDefaultWindowHints();
        GLFW.glfwWindowHint(GLFW.GLFW_VISIBLE, GLFW.GLFW_FALSE);
        GLFW.glfwWindowHint(GLFW.GLFW_RESIZABLE, GLFW.GLFW_FALSE);
        //GLFW.glfwWindowHint(GLFW.GLFW_MAXIMIZED, GLFW.GLFW_TRUE);
        GLFW.glfwWindowHint(GLFW.GLFW_DECORATED, GLFW.GLFW_FALSE);

        //Create window
        pWindow = GLFW.glfwCreateWindow(width, height, title, NULL, NULL);
        if (pWindow == NULL) {
            throw new IllegalStateException("Unable to create GLFW window!");
        }

        GLFW.glfwSetCursorPosCallback(pWindow, MouseHandler::mousePosCallback);
        GLFW.glfwSetMouseButtonCallback(pWindow, MouseHandler::mouseButtonCallback);

        //Make OpenGL context current
        GLFW.glfwMakeContextCurrent(pWindow);

        //Enable V-sync
        GLFW.glfwSwapInterval(1);

        //Make window visible
        GLFW.glfwShowWindow(pWindow);

        //Critical stuff - Don't remove!!!
        GL.createCapabilities();

        camera = new Camera(new Vector2f());
        map = new Map(60,34);

        GL30.glClearColor(0f,0f,0f, 1f);
    }

    Map map;
    Camera camera;
    RenderableQuad quad;
    VertexArrayObject vaobj;

    public void start() {
        init();
        loop();

        System.out.println("finish");
        GLFW.glfwDestroyWindow(pWindow);

        GLFW.glfwTerminate();
        GLFW.glfwSetErrorCallback(null);
    }

    public void loop() {
        int fps = 144;
        float framePerTick = 1000000000 / fps;
        float delta = 0;
        long now, last;
        last = System.nanoTime();

        while (!GLFW.glfwWindowShouldClose(pWindow)) {
            GLFW.glfwPollEvents();

            now = System.nanoTime();
            delta += (now - last) / framePerTick;
            last = now;

            if (delta >= 1) {
                render();
                delta--;
            }

        }
    }

    float alpha = 0;

    public void render() {

        GL30.glClear(GL30.GL_COLOR_BUFFER_BIT);
/*
        if (alpha > 1)
            alpha = 0;

        alpha += 0.02;
*/
        if (MouseHandler.getButton(0)) {
            camera.target.x -= MouseHandler.getScrollX();
            camera.target.y -= MouseHandler.getScrollY();
        }

        MouseHandler.update();
        camera.update();


        //------------------\\

        map.render(camera, alpha);

        //------------------\\

        GLFW.glfwSwapBuffers(pWindow);
    }
}