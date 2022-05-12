package org.codered.rendering.shader;

import org.codered.rendering.Texture2dArray;

public class UniformTexture2DArray extends Uniform
{
	private int unit;
	
	private int location = -1;
	
	public UniformTexture2DArray(String name, int unit)
	{
		super(name);
		this.unit = unit;
	}
	
	public void set(Texture2dArray texture)
	{
		UniformUtils.loadTexture2DArray(this.location, this.unit, texture);
	}
	
	public void set(int textureId)
	{
		UniformUtils.loadTexture2DArray(this.location, this.unit, textureId);
	}

	@Override
	public void loadUniformLocations(int shaderProgrammId)
	{
		this.location = getLocationFor(this.name, shaderProgrammId);
	}

}
