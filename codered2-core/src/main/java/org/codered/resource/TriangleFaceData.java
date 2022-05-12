package org.codered.resource;

import org.barghos.core.api.tuple3.Tup3fR;
import org.barghos.math.vector.Vec3f;

public class TriangleFaceData
{
	private VertexData vertexA;
	private VertexData vertexB;
	private VertexData vertexC;
	private Vec3f normal = new Vec3f();
	
	public TriangleFaceData(VertexData vA, VertexData vB, VertexData vC, Tup3fR normal)
	{
		this.vertexA = vA;
		this.vertexB = vB;
		this.vertexC = vC;
		this.normal.set(normal);
	}
	
	public VertexData getVertexA()
	{
		return this.vertexA;
	}
	
	public VertexData getVertexB()
	{
		return this.vertexB;
	}
	
	public VertexData getVertexC()
	{
		return this.vertexC;
	}
	
	public Vec3f getNormal()
	{
		return this.normal;
	}
}
