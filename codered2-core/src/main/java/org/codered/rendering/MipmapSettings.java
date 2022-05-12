package org.codered.rendering;

import org.lwjgl.opengl.EXTTextureFilterAnisotropic;
import org.lwjgl.opengl.GL11;

public class MipmapSettings
{
	private int maxMipmapLevels;
	private float lodBias;
	private float anisotropicAmount;
	
	private boolean mipmapsEnabled;
	private boolean anisotropicEnabled;
	
	public MipmapSettings maxMipmapLevels(int value)
	{
		this.maxMipmapLevels = value;
		
		this.mipmapsEnabled = this.maxMipmapLevels > 0;
		
		return this;
	}
	
	public MipmapSettings mipmapsEnabled(boolean value)
	{
		this.mipmapsEnabled = value;
		
		return this;
	}
	
	public MipmapSettings lodBias(float value)
	{
		this.lodBias = value;
		
		return this;
	}
	
	public MipmapSettings anisotropicAmount(float value)
	{
		this.anisotropicAmount = Math.min(value, GL11.glGetFloat(EXTTextureFilterAnisotropic.GL_MAX_TEXTURE_MAX_ANISOTROPY_EXT));
		
		this.anisotropicEnabled = this.anisotropicAmount > 1.0f;
		
		return this;
	}
	
	public MipmapSettings anisotropicEnabled(boolean value)
	{
		this.anisotropicEnabled = value;
		
		return this;
	}
	
	public int maxMipmapLevels()
	{
		return mipmapsEnabled() ? this.maxMipmapLevels : 0;
	}
	
	public boolean mipmapsEnabled()
	{
		return this.mipmapsEnabled && this.maxMipmapLevels > 0;
	}
	
	public float lodBias()
	{
		return this.lodBias;
	}
	
	public float anisotropicAmount()
	{
		return anisotropicEnabled() ? this.anisotropicAmount : 1.0f;
	}
	
	public boolean anisotropicEnabled()
	{
		return this.mipmapsEnabled() && this.anisotropicEnabled && this.anisotropicAmount > 1.0f;
	}
}
