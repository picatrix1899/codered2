package org.codered;

import java.util.ArrayList;
import java.util.List;

import org.codered.rendering.Material;
import org.codered.resource.Mesh;

public class Model
{
	private final List<Mesh> meshes = new ArrayList<>();
	private final List<Material> materials = new ArrayList<>();
	
	public Model(List<Mesh> meshes)
	{
		this.meshes.addAll(meshes);
	}
	
	public List<Mesh> getMeshes()
	{
		return this.meshes;
	}

	public List<Material> getMaterials()
	{
		return this.materials;
	}
	
	public void release()
	{
		for(Mesh mesh : this.meshes)
			mesh.release();
		
		for(Material material: this.materials)
			material.release();
	}
}
