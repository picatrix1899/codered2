package org.codered.rendering.shader;

import org.barghos.math.api.matrix.Mat4fR;

public class UniformMatrix4f extends Uniform
{
	private int location = -1;
	
	public UniformMatrix4f(String name)
	{
		super(name);
	}

	public void set(Mat4fR mat)
	{
		UniformUtils.loadMatrix4f(this.location, mat);
	}
	
	@Override
	public void loadUniformLocations(int shaderProgrammId)
	{
		this.location = getLocationFor(this.name, shaderProgrammId);
	}

}
