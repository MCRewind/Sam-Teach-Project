package com.cs.engine.graphics;

import static org.lwjgl.opengl.GL11.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.nio.ByteBuffer;

import javax.imageio.ImageIO;

import org.lwjgl.BufferUtils;

public class Texture {

	private int width, height;
	private int ID;
	
	public Texture(String path) {
		BufferedImage bi;
		try {
			bi = ImageIO.read(new File("res/img/" + path));
			width = bi.getWidth();
			height = bi.getHeight();
			
			int[] pixelsRaw = new int[width * height];
			pixelsRaw = bi.getRGB(0, 0, width, height, null, 0, width);
			
			ByteBuffer pixels = BufferUtils.createByteBuffer(width * height * 4);
			
			for (int i = 0; i < height; i++) {
				for (int j = 0; j < width; j++) {
					int pixel = pixelsRaw[i * width + j];
					pixels.put((byte)((pixel >> 16) & 0xFF));
					pixels.put((byte)((pixel >> 8 ) & 0xFF));
					pixels.put((byte)((pixel      ) & 0xFF));
					pixels.put((byte)((pixel >> 24) & 0xFF));
				}
			}
			
			pixels.flip();
			
			ID = glGenTextures();
			
			glBindTexture(GL_TEXTURE_2D, ID);
			
			glTexParameterf(GL_TEXTURE_2D, GL_TEXTURE_MIN_FILTER, GL_NEAREST);
			glTexParameterf(GL_TEXTURE_2D, GL_TEXTURE_MAG_FILTER, GL_NEAREST);
			
			glTexImage2D(GL_TEXTURE_2D, 0, GL_RGBA, width, height, 0, GL_RGBA, GL_UNSIGNED_BYTE, pixels);
			
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public int getWidth() {
		return width;
	}

	public int getHeight() {
		return height;
	}
	
	public void bind() {
		glBindTexture(GL_TEXTURE_2D, ID);
	}
	
	public void unbind() {
		glBindTexture(GL_TEXTURE_2D, 0);
	}
}
