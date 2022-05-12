package org.codered.game;

import org.barghos.core.api.tuple3.Tup3fR;
import org.barghos.math.api.transform.IHirarchicalTransform3f;
import org.barghos.math.api.vector.QuatfR;
import org.barghos.math.transform.Transform3f;
import org.barghos.math.vector.Vec3f;

public class StaticEntity extends Entity
{
	private final Transform3f transform = new Transform3f();
	
	public IHirarchicalTransform3f getTransform()
	{
		return this.transform;
	}
	
	public Vec3f getPos()
	{
		return this.transform.getPos(1);
	}
	
	public Vec3f getScale()
	{
		return this.transform.getScale(1);
	}
	
	public StaticEntity setPos(Tup3fR v)
	{
		this.transform.setPos(v.getX(), v.getY(), v.getZ());
		
		return this;
	}
	
	public StaticEntity setPos(float x, float y, float z)
	{
		this.transform.setPos(x, y, z);
		
		return this;
	}
	
	public StaticEntity move(Tup3fR v)
	{
		return move(v.getX(), v.getY(), v.getZ());
	}
	
	public StaticEntity move(float x, float y, float z)
	{
		this.transform.move(x, y, z);
		
		return this;
	}
	
	public StaticEntity setScale(Tup3fR v)
	{
		return setScale(v.getX(), v.getY(), v.getZ());
	}
	
	public StaticEntity setScale(float factor)
	{
		return setScale(factor, factor, factor);
	}
	
	public StaticEntity setScale(float x, float y, float z)
	{
		this.transform.setScale(x, y, z);
		
		return this;
	}
	
	public StaticEntity rotate(Tup3fR axis, float angle)
	{
		this.transform.rotate(axis, angle);
		
		return this;
	}
	
	public StaticEntity rotate(QuatfR q)
	{
		this.transform.rotate(q);
		
		return this;
	}
}
