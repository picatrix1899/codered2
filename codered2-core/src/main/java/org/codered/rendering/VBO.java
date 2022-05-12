package org.codered.rendering;

import java.nio.FloatBuffer;
import java.nio.IntBuffer;

import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL15;
import org.lwjgl.system.MemoryUtil;
import org.barghos.core.api.tuple2.Tup2fR;
import org.barghos.core.api.tuple3.Tup3fR;
import org.barghos.core.api.util.BufferUtil;
import org.codered.BindingUtils;
import org.codered.GLCom;

public class VBO
{
	private int id;
	private int dataType;
	private int target;
	private int drawFlag;
	
	public VBO()
	{
		this.id = GLCom.genBuffer();
	}
	
	public int getId()
	{
		return this.id;
	}
	
	public int getDataType()
	{
		return this.dataType;
	}
	
	public int getTarget()
	{
		return this.target;
	}
	
	public int getDrawFlag()
	{
		return this.drawFlag;
	}
	
	public void storeData(FloatBuffer buffer, int target, int drawFlag)
	{
		this.dataType = GL11.GL_FLOAT;
		this.target = target;
		this.drawFlag = drawFlag;

		GL15.glBufferData(target, buffer, drawFlag);
	}
	
	public void storeData(float[] data, int target, int drawFlag)
	{
		FloatBuffer buffer = BufferUtil.createFloatBuffer(data.length);
		BufferUtil.putAndFlipFloat(buffer, data);
		
		storeData(buffer, target, drawFlag);
		
		BufferUtil.destroyBuffer(buffer);
	}
	
	public void storeData(IntBuffer buffer, int target, int drawFlag)
	{
		this.dataType = GL11.GL_INT;
		this.target = target;
		this.drawFlag = drawFlag;
		
		GL15.glBufferData(target, buffer, drawFlag);
	}
	
	public void storeData(int[] data, int target, int drawFlag)
	{
		IntBuffer buffer = BufferUtil.createIntBuffer(data.length);
		BufferUtil.putAndFlipInt(buffer, data);
		
		storeData(buffer, target, drawFlag);
		
		BufferUtil.destroyBuffer(buffer);
	}
	
	public void storeData(Tup2fR[] data, int target, int drawFlag)
	{
		FloatBuffer buffer = BufferUtil.createFloatBuffer(data.length * 2);
		BufferUtil.putAndFlipTuple2f(buffer, data);
		
		storeData(buffer, target, drawFlag);
		
		BufferUtil.destroyBuffer(buffer);
	}
	
	public void storeData(Tup3fR[] data, int target, int drawFlag)
	{
		FloatBuffer buffer = BufferUtil.createFloatBuffer(data.length * 3);
		BufferUtil.putAndFlipTuple3f(buffer, data);
		
		storeData(buffer, target, drawFlag);
		
		BufferUtil.destroyBuffer(buffer);
	}

	public void bind(int target)
	{
		BindingUtils.bindVbo(this, target);
	}
	
	public void unbind(int target)
	{
		BindingUtils.unbindVbo(target);
	}
	
	public void release()
	{
		GLCom.deleteBuffer(this.id);
	}
}
