package com.cs.engine;

import org.joml.Matrix4f;
import org.joml.Vector3f;

import com.cs.engine.graphics.*;
import com.cs.game.Main;

public class GameObject {

	float velX = 0.0f;
	float velY = 0.0f;
	float accelX = 0.0f;
	float accelY = 0.0f;
	boolean solid;
	Vector3f position;
	Vector3f size;
	float rotation;
	float mass;
	float friction;
	Animation anim;
	public static Matrix4f projection;
	
	public GameObject(String texPath, Shader shader) {
		anim = new Animation(texPath, shader, 1, 0, false);
	}
	
	public GameObject(String texPath, Shader shader, int numFrames, int numberUpdates, boolean loop) {
		anim = new Animation(texPath, shader, numFrames, numberUpdates, loop);
	}
	
	public GameObject(Animation anim) {
		this.anim = anim;
	}
	
	public GameObject newInstanceOf(float x, float y, float z, float rotation, float scale, float mass, float friction, boolean solid) {
		GameObject ret = new GameObject(anim);
		ret.setVars(x, y, z, rotation, scale, mass, friction, solid);
		return ret;
	}
	
	public void setVars(float x, float y, float z, float rotation, float scale, float mass, float friction, boolean solid) {
		position = new Vector3f(x, y, z);
		this.rotation = rotation;
		size = new Vector3f(scale, scale, 0.0f);
		this.solid = solid;
		this.mass = mass > 0 ? mass : 1.0f;
		this.friction = friction;
	}
	
	public void setScale(float scale) {
		size.x = scale;
		size.y = scale;
	}
	
	public void setX(float x) {
		position.x = x;
	}
	
	public void setY(float y) {
		position.y = y;
	}
	
	public void setVelocityX(float velX) {
		this.velX = velX;
	}
	
	public void setVelocityY(float velY) {
		this.velY = velY;
	}
	
	public void setMass(float mass) {
		this.mass = mass;
	}
	
	public void setFriction(float friction) {
		this.friction = friction;
	}
	
	public void addForce(float angle, float force) {
		float xComp = (float)Math.cos(Math.toRadians(angle)) * force;
		float yComp = (float)Math.sin(Math.toRadians(angle)) * force;
		accelX += xComp / mass;
		accelY += yComp / mass;
	}
	
	public void update() {
		anim.update();
		if(velX != 0 || velY != 0) {
			float zeroStrength = mass * (float) Math.sqrt(accelX * accelX + accelY * accelY);
			System.out.println(zeroStrength + ", " + friction);
			addForce(getAngle() + 180, (float) Math.min(friction, zeroStrength));
		}
		position.x += velX;
		position.y += velY;
		velX += accelX;
		velY += accelY;
	}
	
	public float getAngle() {
		float hyp = (float) Math.sqrt(velX * velX + velY * velY);
		float angle = (float) ((velY / (float) Math.abs(velY)) * (float) Math.acos(velX / hyp));
		System.out.println((float) Math.toDegrees(angle));
		return (float) Math.toDegrees(angle);
	}
	
	public void render() {
		int x = (int) position.x;
		int y = (int) position.y;
		int z = (int) position.z;
		position = new Vector3f(x, y, z);
		Matrix4f translate = new Matrix4f().translate(position);
		Matrix4f rotate = new Matrix4f().rotateZ((float)Math.toRadians(rotation));
		Matrix4f scale = new Matrix4f().scale(size);
		anim.getMesh().getShader().setUniformMat4f("vw_matrix", translate.mul(rotate).mul(scale));
		anim.getMesh().render();
	}
}
