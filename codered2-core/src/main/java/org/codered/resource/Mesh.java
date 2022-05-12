package org.codered.resource;

import org.codered.rendering.VAO;

public class Mesh
{
	
	private VAO vao;
	private int vertexCount;

	public Mesh(VAO vao, int vertexCount)
	{
		this.vao = vao;
		this.vertexCount = vertexCount;
	}
	
	public VAO getVao()
	{
		return this.vao;
	}
	
	public int getVertexCount()
	{
		return this.vertexCount;
	}

	public void release()
	{
		this.vao.release();
	}
}
