package org.codered.rendering;

import java.nio.ByteBuffer;

import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL30;
import org.lwjgl.opengl.GL45;
import org.lwjgl.system.MemoryUtil;

import org.codered.BindingUtils;
import org.codered.resource.TextureData;

public class TextureUtils
{
	public static Texture2D genTextureWithMipmaps(TextureData data, MipmapSettings mipmapSettings)
	{
		Texture2D t = new Texture2D();
		t.useMipmaps(true);
		
		BindingUtils.bindTexture2D(t);

		GL11.glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_MAG_FILTER, GL11.GL_LINEAR);

		ByteBuffer buffer = MemoryUtil.memAlloc(data.width * data.height * 4);

		for(int i = 0; i < data.pixels.length; i++)
		{
			int pixel = data.pixels[i];
			
			buffer.put((byte)((pixel >> 16) & 0xFF)); // Red component
			buffer.put((byte)((pixel >> 8) & 0xFF)); // Green component
			buffer.put((byte)(pixel & 0xFF)); // Blue component
			buffer.put((byte)((pixel >> 24) & 0xFF)); //Alpha component
		}

		buffer.flip();
		
		GL11.glTexImage2D(GL11.GL_TEXTURE_2D, 0, GL11.GL_RGBA8, data.width, data.height, 0, GL11.GL_RGBA, GL11.GL_UNSIGNED_BYTE, buffer);
		
		MemoryUtil.memFree(buffer);

		BindingUtils.unbindTexture2D();

		t.createMipmaps(mipmapSettings);
		
		return t;
	}
	
	public static Texture2dArray genTextureArrayWithMipmaps(TextureData[] data, MipmapSettings mipmapSettings)
	{
		Texture2dArray t = new Texture2dArray();
		t.useMipmaps(true);
		
		BindingUtils.bindTexture2DArray(t);

		int maxWidth = 0;
		int maxHeight = 0;
		
		for(TextureData d : data)
		{
			if(maxWidth < d.width) maxWidth = d.width;
			if(maxHeight < d.height) maxHeight = d.height;
		}
		
		GL11.glTexParameteri(GL30.GL_TEXTURE_2D_ARRAY, GL11.GL_TEXTURE_MAG_FILTER, GL11.GL_LINEAR);

		GL45.glTexStorage3D(GL30.GL_TEXTURE_2D_ARRAY, 1, GL11.GL_RGBA8, maxWidth, maxHeight, data.length);
		
		int layerIndex = 0;
		
		for(TextureData d : data)
		{
			ByteBuffer buffer = MemoryUtil.memAlloc(d.width * d.height * 4);
	
			for(int i = 0; i < d.pixels.length; i++)
			{
				int pixel = d.pixels[i];
				
				buffer.put((byte)((pixel >> 16) & 0xFF)); // Red component
				buffer.put((byte)((pixel >> 8) & 0xFF)); // Green component
				buffer.put((byte)(pixel & 0xFF)); // Blue component
				buffer.put((byte)((pixel >> 24) & 0xFF)); //Alpha component
			}
	
			buffer.flip();
			
			GL45.glTexSubImage3D(GL30.GL_TEXTURE_2D_ARRAY, 0, 0, 0, layerIndex, d.width, d.height, 1, GL11.GL_RGBA, GL11.GL_UNSIGNED_BYTE, buffer);
	
			MemoryUtil.memFree(buffer);
			
			layerIndex++;
		}

		BindingUtils.unbindTexture2DArray();

		t.createMipmaps(mipmapSettings);
		
		return t;
	}
}
