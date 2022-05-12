package org.codered.rendering;

import java.nio.IntBuffer;

import org.lwjgl.opengl.GL15;
import org.lwjgl.system.MemoryUtil;
import org.barghos.core.api.util.BufferUtil;
import org.codered.BindingUtils;
import org.codered.GLCom;

public class VAO
{
	private int id;
	
	private int indexBufferId;
	
	public VAO()
	{
		this.id = GLCom.genVertexArray();
	}
	
	public int getId()
	{
		return this.id;
	}
	
	public void bind()
	{
		BindingUtils.bindVao(this);
	}
	
	public void unbind()
	{
		BindingUtils.unbindVao();
	}
	
	public void release()
	{
		GLCom.deleteVertexArray(this.id);
	}
	
	public void setPointersForBuffer(VBO vbo, int target, VAOPointer pointer)
	{
		vbo.bind(target);
		
		pointer.apply();
		
		vbo.unbind(target);
	}
	
	public void setPointersForBuffer(VBO vbo, int target, VAOPointers pointers)
	{
		vbo.bind(target);
		
		pointers.apply();
		
		vbo.unbind(target);
	}
	
	public void storeIndices(int[] indices, int drawflag)
	{
		IntBuffer buffer = MemoryUtil.memAllocInt(indices.length);
		
		BufferUtil.putAndFlipInt(buffer, indices);
				
		storeIndices(buffer, drawflag);
		
		MemoryUtil.memFree(buffer);
	}

	public void storeIndices(IntBuffer buffer, int drawflag)
	{
		if(this.indexBufferId == 0) this.indexBufferId = GLCom.genBuffer();
		
		GL15.glBindBuffer(GL15.GL_ELEMENT_ARRAY_BUFFER, this.indexBufferId);
		
		GL15.glBufferData(GL15.GL_ELEMENT_ARRAY_BUFFER, buffer, drawflag);
	}
}
