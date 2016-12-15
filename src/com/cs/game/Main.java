package com.cs.game;

import static org.lwjgl.glfw.GLFW.*;
import java.util.HashMap;
import java.util.Map;
import java.util.ArrayList;
import org.joml.Matrix4f;
import com.cs.engine.GameObject;
import com.cs.engine.Window;
import com.cs.engine.graphics.ShaderLibrary;

public class Main {
	
	Window window;
	ShaderLibrary shaders;
	boolean running = true;
	Map<String, GameObject> objects = new HashMap<>(); //Stores an object with a mesh but isn't rendered
	ArrayList<GameObject> instances = new ArrayList<>(); //Stores the instances of the objects and is what gets rendered
	public static Matrix4f projection;
	
	public Main() {
		window = new Window(0, 0, true);
		shaders = new ShaderLibrary();
		projection = new Matrix4f().ortho2D(-window.getWidth() / 2, window.getWidth() / 2, -window.getHeight() / 2, window.getHeight() / 2);
		run();
	}
	
	public void run() {
		while(running) {
			input();
			update();
			render();
			running = !window.shouldClose();
		}
		window.cleanup();
	}
	
	public void input() {
		/*EXAMPLE: if(window.isKeyPressed(GLFW_KEY_*insert key name*) ...; */
	}
	
	public void update() {
		window.update();
		for (int i = 0; i < instances.size(); i++)
			instances.get(i).update();
	}
	
	public void render() {
		window.clear();
		for (int i = 0; i < instances.size(); i++)
			instances.get(i).render();
		window.swap();
	}
	
	public static void main(String[] args) {
		new Main();
	}
}
