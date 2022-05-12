package org.codered.resource;

public class MaterialData
{
	private TextureData diffuse;
	private TextureData normal;
	
	public MaterialData(TextureData diffuse, TextureData normal)
	{
		this.diffuse = diffuse;
		this.normal = normal;
	}
	
	public TextureData getDiffuse()
	{
		return this.diffuse;
	}
	
	public TextureData getNormal()
	{
		return this.normal;
	}
}
