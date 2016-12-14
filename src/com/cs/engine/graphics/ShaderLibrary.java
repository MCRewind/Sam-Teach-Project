package com.cs.engine.graphics;

import java.util.HashMap;
import java.util.Map;

public class ShaderLibrary {

	Map<String, Shader> shaders;
	
	public ShaderLibrary() {
		shaders = new HashMap<>();
		init();
	}
	
	private void init() {
		shaders.put("default", new Shader("vert.vs", "frag.fs"));
	}
	
	public void add(String name, Shader shader) {
		if(!shaders.containsKey(name))
			shaders.put(name, shader);
	}
	
	public Shader get(String name) {
		if(shaders.containsKey(name))
			return shaders.get(name);
		else
			throw new RuntimeException("Shader of name '" + name + "' has not been create yet!");
	}
	
	public Shader getDefault() {
		return shaders.get("default");
	}
}
