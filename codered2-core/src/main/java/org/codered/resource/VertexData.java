package org.codered.resource;

import org.barghos.core.api.tuple2.Tup2fR;
import org.barghos.core.api.tuple3.Tup3fR;
import org.barghos.math.vector.Vec2f;
import org.barghos.math.vector.Vec3f;

public class VertexData
{
	private Vec3f position = new Vec3f();
	private Vec3f normal = new Vec3f();
	private Vec3f tangent = new Vec3f();
	private Vec2f uv = new Vec2f();
	
	public VertexData(Tup3fR position, Tup3fR normal, Tup3fR tangent, Tup2fR uv)
	{
		this.position.set(position);
		this.normal.set(normal);
		this.tangent.set(tangent);
		this.uv.set(uv);
	}
	
	public Vec3f getPosition()
	{
		return this.position;
	}
	
	public Vec3f getNormal()
	{
		return this.normal;
	}
	
	public Vec3f getTangent()
	{
		return this.tangent;
	}
	
	public Vec2f getUV()
	{
		return this.uv;
	}
}
