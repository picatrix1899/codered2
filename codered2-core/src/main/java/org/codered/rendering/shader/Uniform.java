package org.codered.rendering.shader;

import org.lwjgl.opengl.GL20;

public abstract class Uniform
{
	protected String name;
	
	public Uniform(String name, Object... data)
	{
		this.name = name;
	}
	
	public abstract void loadUniformLocations(int shaderProgrammId);
	
	protected int getLocationFor(String uniform, int shaderProgrammId)
	{
		return GL20.glGetUniformLocation(shaderProgrammId, uniform);
	}
}
