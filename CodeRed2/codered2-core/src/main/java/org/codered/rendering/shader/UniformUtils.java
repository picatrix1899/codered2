package org.codered.rendering.shader;

import java.nio.FloatBuffer;

import org.barghos.core.api.tuple2.Tup2fR;
import org.barghos.core.api.tuple3.Tup3fR;
import org.barghos.math.api.matrix.Mat4fR;
import org.codered.BindingUtils;
import org.codered.rendering.Texture2D;
import org.codered.rendering.Texture2dArray;
import org.lwjgl.opengl.GL13;
import org.lwjgl.opengl.GL20;
import org.lwjgl.system.MemoryUtil;

public class UniformUtils
{
	public static void loadInt(int uniform, int value)
	{
		GL20.glUniform1i(uniform, value);
	}
	
	public static void loadFloat(int uniform, float value)
	{
		GL20.glUniform1f(uniform, value);
	}
	
	public static void loadVector2f(int uniform, float x, float y)
	{
		GL20.glUniform2f(uniform, x, y);
	}
	
	public static void loadVector2f(int uniform, Tup2fR v)
	{
		loadVector2f(uniform, v.getX(), v.getY());
	}
	
	public static void loadVector3f(int uniform, float x, float y, float z)
	{
		GL20.glUniform3f(uniform, x, y, z);
	}
	
	public static void loadVector3f(int uniform, Tup3fR v)
	{
		loadVector3f(uniform, v.getX(), v.getY(), v.getZ());
	}

	public static void loadVector4f(int uniform, float x, float y, float z, float w)
	{
		GL20.glUniform4f(uniform, x, y, z, w);
	}
	
	public static void loadMatrix4f(int uniform, Mat4fR m)
	{
		FloatBuffer buffer = MemoryUtil.memAllocFloat(4 * 4);
		
		m.toBufferColumnMajor(buffer);

		buffer.flip();
		
		GL20.glUniformMatrix4fv(uniform, false, buffer);
		
		MemoryUtil.memFree(buffer);
	}
	
	public static void loadTexture2D(int uniform, int unit, int textureId)
	{
		BindingUtils.bindTexture2D(textureId, GL13.GL_TEXTURE0 + unit);
		loadInt(uniform, unit);
	}
	
	public static void loadTexture2D(int uniform, int unit, Texture2D texture)
	{
		loadTexture2D(uniform, unit, texture.getId());
	}
	
	public static void loadTexture2DArray(int uniform, int unit, int textureId)
	{
		BindingUtils.bindTexture2DArray(textureId, GL13.GL_TEXTURE0 + unit);
		loadInt(uniform, unit);
	}
	
	public static void loadTexture2DArray(int uniform, int unit, Texture2dArray texture)
	{
		loadTexture2DArray(uniform, unit, texture.getId());
	}
}
