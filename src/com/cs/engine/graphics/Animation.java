package com.cs.engine.graphics;

import com.cs.game.Main;

public class Animation {

	Mesh[] meshes;
	int frame = 0;
	int max;
	int inc = 1;
	int curUps;
	int needUps;
	boolean reverse;
	
	public Animation(String texPath, Shader shader, int numFrames, int numUpdates, boolean reverse) {
		this.reverse = reverse;
		max = numFrames;
		needUps = numUpdates;
		shader.setUniformMat4f("pr_matrix", Main.projection);
		meshes = new Mesh[numFrames];
		Texture tex = new Texture(texPath + (numFrames > 1 ? " 1.png" : ".png"));
		if(numFrames > 1)
			for (int i = 0; i < meshes.length; i++)
				meshes[i] = new Mesh(constructVBO(tex.getWidth(), tex.getHeight()), new Texture(texPath + " " + (i+1) + ".png"), shader);
		else
			meshes[0] = new Mesh(constructVBO(tex.getWidth(), tex.getHeight()), tex, shader);
	}
	
	public void update() {
		if(max > 1) {
			if(curUps == needUps) {
				frame += inc;
				if((frame == -1 || frame == max) && reverse)
					frame += (inc = -inc);
				else if(frame == max)
					frame = 0;
				curUps = 1;
			}
			else
				++curUps;
		}
	}
	
	public void render() {
		meshes[frame].render();
	}
	
	private float[] constructVBO(float width, float height) {
		float[] vbo = new float[] {
			0.0f,   0.0f,   0.0f, //TOP LEFT
			0.0f,  -height, 0.0f, //BOTTOM LEFT
			width, -height, 0.0f, //BOTTOM RIGHT
			width,  0.0f,   0.0f  //TOP RIGHT
		};
		return vbo;
	}
	
	public Mesh getMesh() {
		return meshes[frame];
	}
}
