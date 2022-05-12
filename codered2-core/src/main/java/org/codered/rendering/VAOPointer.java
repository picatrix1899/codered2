package org.codered.rendering;

import org.lwjgl.opengl.GL20;

public class VAOPointer
{
	private int index;
	private int size;
	private int type;
	private boolean isNormalized;
	private int stride;
	private int pointer;
	
	public VAOPointer(int index, int size, int type, boolean isNormalized, int stride, int pointer)
	{
		this.index = index;
		this.size = size;
		this.type = type;
		this.isNormalized = isNormalized;
		this.stride = stride;
		this.pointer = pointer;
	}
	
	public void apply()
	{
		GL20.glVertexAttribPointer(this.index, this.size, this.type, this.isNormalized, this.stride, this.pointer);
	}
}
