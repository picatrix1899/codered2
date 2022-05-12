package org.codered.rendering;

import org.codered.BindingUtils;
import org.codered.GLCom;
import org.lwjgl.opengl.EXTTextureFilterAnisotropic;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL14;
import org.lwjgl.opengl.GL30;

public class Texture2dArray
{
	private int id;
	private boolean useMipmaps;
	
	public Texture2dArray()
	{
		this.id = GLCom.genTexture();
	}
	
	public Texture2dArray useMipmaps(boolean value)
	{
		this.useMipmaps = value;
		
		return this;
	}
	
	public boolean useMipmaps()
	{
		return this.useMipmaps;
	}
	
	public int getId()
	{
		return this.id;
	}
	
	public void bind()
	{
		BindingUtils.bindTexture2DArray(this);
	}
	
	public void unbind()
	{
		BindingUtils.unbindTexture2DArray();
	}
	
	public void release()
	{
		GLCom.deleteTexture(this.id);
	}
	
	public void createMipmaps(MipmapSettings settings)
	{
		if(!this.useMipmaps) return;
		
		bind();
		
		GL11.glTexParameterf(GL30.GL_TEXTURE_2D_ARRAY, GL14.GL_TEXTURE_LOD_BIAS, settings.lodBias());
		
		GL11.glTexParameteri(GL30.GL_TEXTURE_2D_ARRAY, GL30.GL_TEXTURE_MAX_LEVEL, settings.maxMipmapLevels());
		
		GL11.glTexParameterf(GL30.GL_TEXTURE_2D_ARRAY, EXTTextureFilterAnisotropic.GL_TEXTURE_MAX_ANISOTROPY_EXT, settings.anisotropicAmount());
		
		GL30.glGenerateMipmap(GL30.GL_TEXTURE_2D_ARRAY);
		
		unbind();
	}
}
