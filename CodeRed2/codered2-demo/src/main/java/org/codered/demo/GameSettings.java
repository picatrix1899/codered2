package org.codered.demo;


public class GameSettings
{
	private Runnable changeCallback = new Runnable() { public void run() {}};
	
	private int mipmapLevels = 0;
	private int anisotropicLevels = 0;
	
	public void reset()
	{
		boolean hasChanged = false;
		if(setMipmapLevels0(0)) hasChanged = true;
		if(setAnisotropicLevels0(0)) hasChanged = true;
		
		if(hasChanged) this.changeCallback.run();
	}
	
	public void setMipmapLevels(int levels)
	{
		if(setMipmapLevels0(levels)) notifyCallback();
	}
	
	private boolean setMipmapLevels0(int levels)
	{
		if(this.mipmapLevels == levels) return false;
		
		this.mipmapLevels = levels;
		
		return true;
	}
	
	public void setAnisotropicLevels(int levels)
	{
		if(setAnisotropicLevels0(levels)) notifyCallback();
	}
	
	private boolean setAnisotropicLevels0(int levels)
	{
		if(this.anisotropicLevels == levels) return false;
		
		this.anisotropicLevels = levels;
		
		return true;
	}
	
	public int getMipmapLevels()
	{
		return this.mipmapLevels;
	}
	
	public int getAnisotropicLevels()
	{
		return this.anisotropicLevels;
	}
	
	public void setChangeCallback(Runnable callback)
	{
		this.changeCallback = callback;
	}
	
	protected void notifyCallback()
	{
		this.changeCallback.run();
	}
}
