package org.codered.rendering;

import java.util.Optional;

public class Material
{
	private Texture2D diffuse;
	private Texture2D normal;
	
	public Material(Texture2D diffuse, Texture2D normal)
	{
		this.diffuse = diffuse;
		this.normal = normal;
	}
	
	public Optional<Texture2D> getDiffuse()
	{
		return Optional.ofNullable(this.diffuse);
	}
	
	public Optional<Texture2D> getNormal()
	{
		return Optional.ofNullable(this.normal);
	}

	public void release()
	{
		if(this.diffuse != null) this.diffuse.release();
		if(this.normal != null) this.normal.release();
	}
}
