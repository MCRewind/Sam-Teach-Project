package com.cs.engine;

import org.lwjgl.glfw.GLFWErrorCallback;
import org.lwjgl.glfw.GLFWKeyCallback;
import org.lwjgl.glfw.GLFWWindowSizeCallback;
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
		if(!glfwInit())
			throw new IllegalStateException("Unable to initalize GLFW!");
	
		glfwWindowHint(GLFW_RESIZABLE,            GLFW_FALSE);
		glfwWindowHint(GLFW_VISIBLE,              GLFW_FALSE);
		glfwWindowHint(GLFW_CONTEXT_VERSION_MAJOR,         3);
		glfwWindowHint(GLFW_CONTEXT_VERSION_MINOR,         2);
		glfwWindowHint(GLFW_OPENGL_FORWARD_COMPAT, GLFW_TRUE);
		
		window = glfwCreateWindow(width, height, title, NULL, NULL);
		if(window == NULL)
			throw new RuntimeException("Unable to create GLFW window!");
	}
}