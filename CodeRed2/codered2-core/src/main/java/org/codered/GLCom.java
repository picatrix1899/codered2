package org.codered;

import java.nio.IntBuffer;

import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL20;
import org.lwjgl.opengl.GL30;

public class GLCom
{
	public static int genBuffer()
	{
		return GL30.glGenBuffers();
	}
	
	public static int[] genBuffers(int[] res)
	{
		GL30.glGenBuffers(res);
		
		return res;
	}
	
	public static int[] genBuffers(int amount)
	{
		int[] res = new int[amount];
		
		return genBuffers(res);
	}
	
	public static IntBuffer genBuffers(IntBuffer buffer)
	{
		GL30.glGenBuffers(buffer);
		
		return buffer;
	}
	
	public static void deleteBuffer(int buffer)
	{
		GL30.glDeleteBuffers(buffer);
	}
	
	public static void deleteBuffers(int[] buffers)
	{
		GL30.glDeleteBuffers(buffers);
	}
	
	public static void deleteBuffers(IntBuffer buffers)
	{
		GL30.glDeleteBuffers(buffers);
	}
	
	public static int genVertexArray()
	{
		return GL30.glGenVertexArrays();
	}
	
	public static int[] genVertexArrays(int[] res)
	{
		GL30.glGenVertexArrays(res);
		
		return res;
	}
	
	public static int[] genVertexArrays(int amount)
	{
		int[] res = new int[amount];
		
		return genVertexArrays(res);
	}
	
	public static IntBuffer genVertexArrays(IntBuffer buffers)
	{
		GL30.glGenVertexArrays(buffers);
		
		return buffers;
	}
	
	public static void deleteVertexArray(int array)
	{
		GL30.glDeleteVertexArrays(array);
	}
	
	public static void deleteVertexArrays(int[] arrays)
	{
		GL30.glDeleteVertexArrays(arrays);
	}
	
	public static void deleteVertexArrays(IntBuffer buffers)
	{
		GL30.glDeleteVertexArrays(buffers);
	}
	
	public static int genTexture()
	{
		return GL11.glGenTextures();
	}
	
	public static int[] genTextures(int[] res)
	{
		GL11.glGenTextures(res);
		
		return res;
	}
	
	public static int[] genTextures(int amount)
	{
		int[] res = new int[amount];
		
		return genTextures(res);
	}
	
	public static IntBuffer genTextures(IntBuffer buffer)
	{
		GL11.glGenTextures(buffer);
		
		return buffer;
	}
	
	public static void deleteTexture(int texture)
	{
		GL11.glDeleteTextures(texture);
	}
	
	public static void deleteTextures(int[] textures)
	{
		GL11.glDeleteTextures(textures);
	}
	
	public static void deleteTextures(IntBuffer buffer)
	{
		GL11.glDeleteTextures(buffer);
	}
	
	public static int genProgram()
	{
		return GL20.glCreateProgram();
	}
	
	public static void deleteProgram(int program)
	{
		GL20.glDeleteProgram(program);
	}
	
	public static int genShader(int type)
	{
		return GL20.glCreateShader(type);
	}
	
	public static void deleteShader(int shader)
	{
		GL20.glDeleteShader(shader);
	}
	
	public static void setClearColor(float r, float g, float b, float a)
	{
		GL11.glClearColor(r, g, b, a);
	}
	
	public static void clearAll(float r, float g, float b, float a)
	{
		setClearColor(r, g, b, a);
		clearAll();
	}
	
	public static void clearAll()
	{
		GL11.glClear(GL11.GL_COLOR_BUFFER_BIT | GL11.GL_DEPTH_BUFFER_BIT | GL11.GL_STENCIL_BUFFER_BIT);
	}
	
	public static void clear(boolean color, boolean depth, boolean stencil)
	{
		int flags = 0;
		if(color) flags |= GL11.GL_COLOR_BUFFER_BIT;
		if(depth) flags |= GL11.GL_DEPTH_BUFFER_BIT;
		if(stencil) flags |= GL11.GL_STENCIL_BUFFER_BIT;
		
		GL11.glClear(flags);
	}
	
	public static void clearColor()
	{
		GL11.glClear(GL11.GL_COLOR_BUFFER_BIT);
	}
	
	public static void clearDepth()
	{
		GL11.glClear(GL11.GL_DEPTH_BUFFER_BIT);
	}
	
	public static void clearStencil()
	{
		GL11.glClear(GL11.GL_STENCIL_BUFFER_BIT);
	}
}
