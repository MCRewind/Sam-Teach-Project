package com.cs.engine;

import static org.lwjgl.glfw.GLFW.*;
import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.system.MemoryUtil.NULL;
import org.joml.Vector3f;
import org.lwjgl.glfw.*;
import org.lwjgl.opengl.GL;

public class Window {

	private long window;
	
	private int width, height, mx, my;
	private boolean vSync;
	
	public Window(int width, int height, boolean vSync) {
		this.width = width;
		this.height = height;
		this.vSync = vSync;
		init();
	}
	
	public void init() {
		glfwSetErrorCallback(GLFWErrorCallback.createPrint(System.err));
		
		if(!glfwInit())
			throw new IllegalStateException("Could not initalize GLFW!");

		glfwWindowHint(GLFW_RESIZABLE, GLFW_FALSE);
		glfwWindowHint(GLFW_VISIBLE,   GLFW_FALSE);
		
		GLFWVidMode vidMode = glfwGetVideoMode(glfwGetPrimaryMonitor());
		
		if(width == NULL || height == NULL) {
			window = glfwCreateWindow(vidMode.width(), vidMode.height(), "One Room", glfwGetPrimaryMonitor(), NULL);
			width = vidMode.width();
			height = vidMode.height();
		}
		else {
			window = glfwCreateWindow(width, height, "One Room", NULL, NULL);
		
			glfwSetWindowPos(window, (vidMode.width() - width) / 2, (vidMode.height() - height) / 2);
		}

		glfwSetCursorPosCallback(window, (long window, double xpos, double ypos) -> {
			mx = (int)xpos;
			my = (int)ypos;
		});
		
		glfwMakeContextCurrent(window);
		GL.createCapabilities();
		
		if(vSync)
			glfwSwapInterval(1);
		
		glfwShowWindow(window);
		
		glClearColor(0.0f, 0.0f, 0.0f, 1.0f);
		
		glEnable(GL_TEXTURE_2D);
		
		glEnable(GL_DEPTH_TEST);
		
		glEnable(GL_BLEND);
		glBlendFunc(GL_SRC_ALPHA, GL_ONE_MINUS_SRC_ALPHA);
		
		System.out.println("OpenGL Version: " + glGetString(GL_VERSION));
	}
	
	public void update() {
		glfwPollEvents();
	}
	
	public void clear() {
		glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT);
	}
	
	public void swap() {
		glfwSwapBuffers(window);
	}
	
	public void cleanup() {
		glfwDestroyWindow(window);
		glfwTerminate();
	}
	
	public Vector3f getMouseCoords() {
		return new Vector3f(mx, my, 0);
	}
	
	public boolean isMousePressed(int button) {
		return glfwGetMouseButton(window, button) == GLFW_PRESS;
	}
	
	public boolean isKeyPressed(int keyCode) {
		return glfwGetKey(window, keyCode) == GLFW_PRESS;
	}
	
	public void close() {
		glfwSetWindowShouldClose(window, true);
	}
	
	public boolean shouldClose() {
		return glfwWindowShouldClose(window);
	}
	
	public long getWindow() {
		return window;
	}

	public int getWidth() {
		return width;
	}

	public int getHeight() {
		return height;
	}
}
