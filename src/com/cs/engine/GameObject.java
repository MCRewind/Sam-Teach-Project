package com.cs.engine;

import com.cs.engine.graphics.Mesh;
import com.cs.engine.math.Vector2f;

public class GameObject {

	Vector2f position;
	float scale;
	float rotation;
	Mesh mesh;
	
	
	public GameObject(Mesh mesh) {
		position = new Vector2f(0, 0);
		scale = 1.0f;
		rotation = 0.0f;
		this.mesh = mesh;
	}
	
	public GameObject(Vector2f position, Mesh mesh) {
		this.position = position;
		scale = 1.0f;
		rotation = 0.0f;
		this.mesh = mesh;
	}

	public GameObject(Vector2f position, int scale, int rotation, Mesh mesh) {
		this.position = position;
		this.scale = scale;
		this.rotation = rotation;
		this.mesh = mesh;
	}
	
	public GameObject(int x, int y, Mesh mesh) {
		position = new Vector2f(x, y);
		scale = 1.0f;
		rotation = 0.0f;
		this.mesh = mesh;
	}

	public GameObject(int x, int y, int scale, int rotation, Mesh mesh) {
		position = new Vector2f(x, y);
		this.scale = scale;
		this.rotation = rotation;
		this.mesh = mesh;
	}
}
