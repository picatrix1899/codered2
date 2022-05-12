package org.codered.demo;

import java.nio.ByteBuffer;
import java.nio.DoubleBuffer;
import java.nio.FloatBuffer;
import java.nio.IntBuffer;
import java.nio.LongBuffer;
import java.nio.ShortBuffer;

import org.barghos.core.api.util.NioBufferFactory;
import org.lwjgl.system.MemoryUtil;

public class LWJGLNioBufferFactory implements NioBufferFactory
{

	public ByteBuffer createByteBuffer(int capacity)
	{
		return MemoryUtil.memAlloc(capacity);
	}

	public ShortBuffer createShortBuffer(int capacity)
	{
		return MemoryUtil.memAllocShort(capacity);
	}

	public IntBuffer createIntBuffer(int capacity)
	{
		return MemoryUtil.memAllocInt(capacity);
	}

	public LongBuffer createLongBuffer(int capacity)
	{
		return MemoryUtil.memAllocLong(capacity);
	}

	public FloatBuffer createFloatBuffer(int capacity)
	{
		return MemoryUtil.memAllocFloat(capacity);
	}

	public DoubleBuffer createDoubleBuffer(int capacity)
	{
		return MemoryUtil.memAllocDouble(capacity);
	}

	public void destroyBuffer(ByteBuffer buffer)
	{
		MemoryUtil.memFree(buffer);
	}

	public void destroyBuffer(ShortBuffer buffer)
	{
		MemoryUtil.memFree(buffer);
	}

	public void destroyBuffer(IntBuffer buffer)
	{
		MemoryUtil.memFree(buffer);
	}

	public void destroyBuffer(LongBuffer buffer)
	{
		MemoryUtil.memFree(buffer);
	}

	public void destroyBuffer(FloatBuffer buffer)
	{
		MemoryUtil.memFree(buffer);
	}

	public void destroyBuffer(DoubleBuffer buffer)
	{
		MemoryUtil.memFree(buffer);
	}

}
