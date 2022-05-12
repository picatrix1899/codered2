package org.codered.rendering;

import org.codered.BindingUtils;
import org.codered.GLCom;
import org.lwjgl.opengl.EXTTextureFilterAnisotropic;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL14;
import org.lwjgl.opengl.GL30;

public class Texture2D
{
	private int id;
	private boolean useMipmaps;
	
	public Texture2D()
	{
		this.id = GLCom.genTexture();
	}
	
	public Texture2D useMipmaps(boolean value)
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
		BindingUtils.bindTexture2D(this);
	}
	
	public void unbind()
	{
		BindingUtils.unbindTexture2D();
	}
	
	public void release()
	{
		GLCom.deleteTexture(this.id);
	}
	
	public void createMipmaps(MipmapSettings settings)
	{
		if(!this.useMipmaps) return;
		
		bind();
		
		GL11.glTexParameterf(GL11.GL_TEXTURE_2D, GL14.GL_TEXTURE_LOD_BIAS, settings.lodBias());
		
		GL11.glTexParameteri(GL11.GL_TEXTURE_2D, GL30.GL_TEXTURE_MAX_LEVEL, settings.maxMipmapLevels());
		
		GL11.glTexParameterf(GL11.GL_TEXTURE_2D, EXTTextureFilterAnisotropic.GL_TEXTURE_MAX_ANISOTROPY_EXT, settings.anisotropicAmount());
		
		GL30.glGenerateMipmap(GL11.GL_TEXTURE_2D);
		
		unbind();
	}
}
