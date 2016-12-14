package com.cs.engine.graphics;

import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.opengl.GL15.*;
import static org.lwjgl.opengl.GL20.*;
import static org.lwjgl.opengl.GL30.*;

import java.nio.ByteBuffer;
import java.nio.FloatBuffer;

import org.lwjgl.BufferUtils;

public class Mesh {

	private Shader shader;
	private Texture texture;
	private int count;
	private int vaoID, vboID, iboID, tcboID;
	private float[] vbo, tcbo;
	private byte[] ibo;
	
	
	public Mesh(float[] vbo, byte[] ibo, float[] tcbo, Texture texture, Shader shader) {
		this.vbo = vbo;
		this.ibo = ibo;
		this.tcbo = tcbo;
		this.texture = texture;
		this.shader = shader;
		count = ibo.length;
		init();
	}
	
	public Mesh(float[] vbo, Texture texture, Shader shader) {
		this.vbo = vbo;
		this.ibo = defaultIBO();
		this.tcbo = defaultTCBO();
		this.texture = texture;
		this.shader = shader;
		count = ibo.length;
		init();
	}
	
	public Mesh(float[] vbo, String texPath, String vertPath, String fragPath) {
		this.vbo = vbo;
		this.ibo = defaultIBO();
		this.tcbo = defaultTCBO();
		this.texture = new Texture(texPath);
		this.shader = new Shader(vertPath, fragPath);
		count = ibo.length;
		init();
	}
	
	private void init() {
		vaoID = glGenVertexArrays();
		glBindVertexArray(vaoID);
		
		vboID = glGenBuffers();
		glBindBuffer(GL_ARRAY_BUFFER, vboID);
		glBufferData(GL_ARRAY_BUFFER, createFloatBuffer(vbo), GL_STATIC_DRAW);
		glVertexAttribPointer(Shader.VERT_ATTRIB, 3, GL_FLOAT, false, 0, 0);
		glEnableVertexAttribArray(Shader.VERT_ATTRIB);
		
		iboID = glGenBuffers();
		glBindBuffer(GL_ELEMENT_ARRAY_BUFFER, iboID);
		glBufferData(GL_ELEMENT_ARRAY_BUFFER, createByteBuffer(ibo), GL_STATIC_DRAW);
		
		tcboID = glGenBuffers();
		glBindBuffer(GL_ARRAY_BUFFER, tcboID);
		glBufferData(GL_ARRAY_BUFFER, createFloatBuffer(tcbo), GL_STATIC_DRAW);
		glVertexAttribPointer(Shader.TEX_COORD_ATTRIB, 2, GL_FLOAT, false, 0, 0);
		glEnableVertexAttribArray(Shader.TEX_COORD_ATTRIB);
		
		glBindBuffer(GL_ARRAY_BUFFER, 0);
		glBindBuffer(GL_ELEMENT_ARRAY_BUFFER, 0);
		glBindVertexArray(0);
	}
	
	public void render() {
		shader.enable();
		texture.bind();
		glBindVertexArray(vaoID);
		glBindBuffer(GL_ELEMENT_ARRAY_BUFFER, iboID);
		glDrawElements(GL_TRIANGLES, count, GL_UNSIGNED_BYTE, 0);
		glBindBuffer(GL_ARRAY_BUFFER, 0);
		glBindBuffer(GL_ELEMENT_ARRAY_BUFFER, 0);
		glBindVertexArray(0);
		texture.unbind();
		shader.enable();
	}
	
	public byte[] defaultIBO() {
		return new byte[] {
			0, 1, 2,
			0, 2, 3
		};
	}
	
	public float[] defaultTCBO() {
		return new float[] {
			1, 0,
			1, 1,
			0, 1,
			0, 0,
		};
	}
	
	public Shader getShader() {
		return shader;
	}
	
	public FloatBuffer createFloatBuffer(float[] data) {
		FloatBuffer buffer = BufferUtils.createFloatBuffer(data.length);
		buffer.put(data);
		buffer.flip();
		return buffer;
	}
	
	public ByteBuffer createByteBuffer(byte[] data) {
		ByteBuffer buffer = BufferUtils.createByteBuffer(data.length);
		buffer.put(data);
		buffer.flip();
		return buffer;
	}
}
