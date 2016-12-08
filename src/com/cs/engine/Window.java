package com.cs.engine;

import org.lwjgl.glfw.GLFWErrorCallback;
import org.lwjgl.glfw.GLFWKeyCallback;
import org.lwjgl.glfw.GLFWVidMode;
import org.lwjgl.glfw.GLFWWindowSizeCallback;
import static org.lwjgl.opengl.GL.*;
import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.glfw.GLFW.*;
import static org.lwjgl.system.MemoryUtil.NULL;

public class Window {

	private long window;
	
	private String title;
	private int width;
	private int height;
	private boolean vSync;
	
	private GLFWErrorCallback errorCallback;
	private GLFWKeyCallback keyCallback;
	private GLFWWindowSizeCallback windowSizeCallback;

	
	public Window(String title, int width, int height, boolean vSync) {
		this.title  =  title;
		this.width  =  width;
		this.height = height;
		this.vSync  =  vSync;
	}
	
	public void init() {
		//Sets up the ErrorCallback
		glfwSetErrorCallback(errorCallback = GLFWErrorCallback.createPrint(System.err));
		
		//Intializes glfw and test for an error
		if(!glfwInit())
			throw new IllegalStateException("Unable to initalize GLFW!");
	
		//Sets all the properties of the window
		glfwWindowHint(GLFW_RESIZABLE,            GLFW_FALSE);
		glfwWindowHint(GLFW_VISIBLE,              GLFW_FALSE);
		glfwWindowHint(GLFW_CONTEXT_VERSION_MAJOR,         3);
		glfwWindowHint(GLFW_CONTEXT_VERSION_MINOR,         2);
		glfwWindowHint(GLFW_OPENGL_FORWARD_COMPAT, GLFW_TRUE);
		
		//Creates the window and tests for an error
		window = glfwCreateWindow(width, height, title, NULL, NULL);
		if(window == NULL)
			throw new RuntimeException("Unable to create GLFW window!");
		
		//Set up the WindowSizeCallback
		glfwSetWindowSizeCallback(window, (long window, int width, int height) -> {
			this.width  =  width;
			this.height = height;
		});
				
		//Set up the KeyCallback
		glfwSetKeyCallback(window, (long window, int key, int scancode, int action, int mode) -> {
			
		});
		
		//Get the size of the primary monitor and then center the window on the screen
		GLFWVidMode vidMode = glfwGetVideoMode(glfwGetPrimaryMonitor());
		glfwSetWindowPos(window, (vidMode.width() - width) / 2, (vidMode.height() - height) / 2);
		
		//Make the opengl context current
		glfwMakeContextCurrent(window);
		
		//Sets up vSync if requested
		if(vSync)
			glfwSwapInterval(1);
		
		//Sets the background color of the window
		glClearColor(0.0f, 0.0f, 0.0f, 1.0f);
		
		//Enables transparency
		glEnable(GL_BLEND);
		glBlendFunc(GL_SRC_ALPHA, GL_ONE_MINUS_SRC_ALPHA);
	}
	
	public long getWindowHandle() {
		return window;
	}
	
	public int getWidth() {
		return width;
	}
	
	public int getHeight() {
		return height;
	}
	
	public boolean isKeyPressed(int keyCode) {
		return glfwGetKey(window, keyCode) == GLFW_PRESS;
	}
	
	public boolean windowShouldClose() {
		return glfwWindowShouldClose(window);
	}
	
	public void update() {
		glfwSwapBuffers(window);
		glfwPollEvents();
	}
}