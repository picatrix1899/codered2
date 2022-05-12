package org.codered.rendering.shader;

import org.codered.rendering.Texture2D;

public class UniformTexture2D extends Uniform
{
	private int unit;
	
	private int location = -1;
	
	public UniformTexture2D(String name, int unit)
	{
		super(name);
		this.unit = unit;
	}
	
	public void set(Texture2D texture)
	{
		UniformUtils.loadTexture2D(this.location, this.unit, texture);
	}
	
	public void set(int textureId)
	{
		UniformUtils.loadTexture2D(this.location, this.unit, textureId);
	}

	@Override
	public void loadUniformLocations(int shaderProgrammId)
	{
		this.location = getLocationFor(this.name, shaderProgrammId);
	}

}
