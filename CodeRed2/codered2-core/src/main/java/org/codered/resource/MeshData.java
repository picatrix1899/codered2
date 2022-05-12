package org.codered.resource;

import java.util.List;

public class MeshData
{
	private int vertexCount;
	private MaterialData material;
	private List<TriangleFaceData> faces;
	
	public MeshData(int vertexCount, List<TriangleFaceData> faces, MaterialData material)
	{
		this.vertexCount = vertexCount;
		this.faces = faces;
		this.material = material;
	}

	public int getVertexCount()
	{
		return this.vertexCount;
	}
	
	public MaterialData getMaterial()
	{
		return this.material;
	}
	
	public List<TriangleFaceData> getFaces()
	{
		return this.faces;
	}
}
