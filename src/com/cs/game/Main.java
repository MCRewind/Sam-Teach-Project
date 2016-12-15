package com.cs.game;

import static org.lwjgl.glfw.GLFW.*;

import java.util.HashMap;
import java.util.Map;

import org.joml.Matrix4f;

import com.cs.engine.GameObject;
import com.cs.engine.Window;
import com.cs.engine.graphics.ShaderLibrary;

public class Main {
	
	Window window;
	ShaderLibrary shaders;
	boolean running = true;
	Map<String, GameObject> objects = new HashMap<>();
	GameObject block1;
	GameObject block2;
	public static Matrix4f projection;
	
	public Main() {
		window = new Window(1000, 700, true);
		shaders = new ShaderLibrary();
		projection = new Matrix4f().ortho2D(-window.getWidth() / 2, window.getWidth() / 2, -window.getHeight() / 2, window.getHeight() / 2);
		run();
	}
	
	public void run() {
		objects.put("Stone Block", new GameObject("Stone Block", shaders.getDefault()));
		objects.put("Spring", new GameObject("Spring", shaders.getDefault(), 4, 2, true));
		block1 = objects.get("Spring").newInstanceOf(0.0f, 0.0f, 0.0f, 0.0f, 8.0f, false);
		while(running) {
			input();
			update(block1);
			render(block1);
			running = !window.shouldClose();
		}
		window.cleanup();
	}
	
	public void input() {
		float speed = 5.0f;
		if(window.isKeyPressed(GLFW_KEY_D)) block1.setXVelocity( speed);
		if(window.isKeyPressed(GLFW_KEY_W)) block1.setYVelocity( speed);
		if(window.isKeyPressed(GLFW_KEY_A)) block1.setXVelocity(-speed);
		if(window.isKeyPressed(GLFW_KEY_S)) block1.setYVelocity(-speed);
		if(window.isKeyPressed(GLFW_KEY_ESCAPE)) window.close();
	}
	
	public void update(GameObject... gameObjects) {
		window.update();
		for (int i = 0; i < gameObjects.length; i++)
			gameObjects[i].update();
	}
	
	public void render(GameObject... gameObjects) {
		window.clear();
		for (int i = 0; i < gameObjects.length; i++)
			gameObjects[i].render();
		window.swap();
	}
	
	public static void main(String[] args) {
		new Main();
	}
}
