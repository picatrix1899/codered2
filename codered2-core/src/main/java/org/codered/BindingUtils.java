package org.codered;

import org.codered.rendering.Texture2D;
import org.codered.rendering.Texture2dArray;
import org.codered.rendering.VAO;
import org.codered.rendering.VBO;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL13;
import org.lwjgl.opengl.GL30;

public class BindingUtils
{
	private static int lastActiveTextureUnit = GL13.GL_TEXTURE0;
	private static int currentActiveTextureUnit = GL13.GL_TEXTURE0;
	
	public static void setActiveTextureUnit(int unit)
	{
		if(currentActiveTextureUnit == unit) return;
		
		int newUnit = GL13.GL_TEXTURE0 + unit;
		
		lastActiveTextureUnit = currentActiveTextureUnit;
		GL13.glActiveTexture(newUnit);
		currentActiveTextureUnit = newUnit;
	}
	
	public static int getCurrentActiveTextureUnit()
	{
		return currentActiveTextureUnit;
	}
	
	public static int getLastActiveTextureUnit()
	{
		return lastActiveTextureUnit;
	}
	
	public static void bindVbo(VBO vbo, int target)
	{
		GL30.glBindBuffer(target, vbo.getId());
	}
	
	public static void unbindVbo(int target)
	{
		GL30.glBindBuffer(target, 0);
	}
	
	public static void bindVao(VAO vao)
	{
		GL30.glBindVertexArray(vao.getId());
	}
	
	public static void unbindVao()
	{
		GL30.glBindVertexArray(0);
	}

	public static void bindTexture(Texture2D texture, int target, int unit)
	{
		bindTexture(texture.getId(), target, unit);
	}
	
	public static void bindTexture(int textureId, int target, int unit)
	{
		setActiveTextureUnit(unit);
		GL11.glBindTexture(target, textureId);
	}
	
	public static void unbindTexture(int target, int unit)
	{
		setActiveTextureUnit(unit);
		GL11.glBindTexture(target, 0);
	}
	
	public static void bindTexture(Texture2D texture, int target)
	{
		bindTexture(texture.getId(), target);
	}
	
	public static void bindTexture(int textureId, int target)
	{
		bindTexture(textureId, target, 0);
	}
	
	public static void unbindTexture(int target)
	{
		unbindTexture(target, 0);
	}

	public static void bindTexture2D(Texture2D texture, int unit)
	{
		bindTexture2D(texture.getId(), unit);
	}
	
	public static void bindTexture2D(int textureId, int unit)
	{
		bindTexture(textureId, GL11.GL_TEXTURE_2D, unit);
	}
	
	public static void unbindTexture2D(int unit)
	{
		unbindTexture(GL11.GL_TEXTURE_2D, unit);
	}
	
	public static void bindTexture2D(Texture2D texture)
	{
		bindTexture2D(texture.getId());
	}
	
	public static void bindTexture2D(int textureId)
	{
		bindTexture(textureId, GL11.GL_TEXTURE_2D);
	}
	
	public static void unbindTexture2D()
	{
		unbindTexture(GL11.GL_TEXTURE_2D);
	}
	
	public static void bindTexture2DArray(int textureId, int unit)
	{
		bindTexture(textureId, GL30.GL_TEXTURE_2D_ARRAY, unit);
	}
	
	public static void unbindTexture2DArray(int unit)
	{
		unbindTexture(GL30.GL_TEXTURE_2D_ARRAY, unit);
	}
	
	public static void bindTexture2DArray(int textureId)
	{
		bindTexture(textureId, GL30.GL_TEXTURE_2D_ARRAY);
	}
	
	public static void bindTexture2DArray(Texture2dArray texture)
	{
		bindTexture2DArray(texture.getId());
	}
	
	public static void unbindTexture2DArray()
	{
		unbindTexture(GL30.GL_TEXTURE_2D_ARRAY);
	}
}
