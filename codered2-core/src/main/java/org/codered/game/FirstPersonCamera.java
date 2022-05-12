package org.codered.game;

import org.barghos.core.api.tuple3.Tup3fR;
import org.barghos.math.api.matrix.Mat4fC;
import org.barghos.math.api.transform.ITransform3f;
import org.barghos.math.api.vector.QuatfR;
import org.barghos.math.api.vector.Vec3fC;
import org.barghos.math.matrix.Mat4f;
import org.barghos.math.transform.SweptTransform3f;
import org.barghos.math.vector.Vec3f;
import org.codered.engine.TickInfo;

public class FirstPersonCamera
{
	private final SweptTransform3f transform = new SweptTransform3f();
	public float angle = 0;
	public float yaw = 0;
	
	public void preTick(TickInfo tick)
	{
		this.transform.update();
	}
	
	public SweptTransform3f getTransform()
	{
		return this.transform;
	}
	
	public FirstPersonCamera setPos(float x, float y, float z)
	{
		this.transform.setPos(x, y, z);
		
		return this;
	}
	
	public FirstPersonCamera move(Tup3fR v)
	{
		this.transform.move(v.getX(), v.getY(), v.getZ());
		
		return this;
	}
	
	public FirstPersonCamera move(float x, float y, float z)
	{
		this.transform.move(x, y, z);
		
		return this;
	}
	
	public FirstPersonCamera rotate(QuatfR q)
	{
		this.transform.rotate(q);

		return this;
	}
	
	public FirstPersonCamera rotate(Tup3fR axis, float angle)
	{
		this.transform.rotate(axis, angle);

		return this;
	}
	
	public FirstPersonCamera setScale(float x, float y, float z)
	{
		this.transform.setScale(x, y, z);
		
		return this;
	}
	
	public FirstPersonCamera setOrientation(Tup3fR forward, Tup3fR up)
	{
		this.transform.setOrientation(forward, up);
		
		return this;
	}
	
	public Mat4fC getViewMatrix(double alpha)
	{
		Mat4fC translation = Mat4f.translation3d(this.transform.getPos(alpha).negate());
		
		Vec3fC forward = this.transform.getOrientation(alpha).getForward();
		Vec3fC right = this.transform.getOrientation(alpha).getRight();
		Vec3fC up = this.transform.getOrientation(alpha).getUp();
		
		Mat4fC rotation = new Mat4f().initBaseChangeRH(right, up, forward).transpose();

		return rotation.mul(translation);
	}
}
