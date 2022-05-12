package org.codered.game;

import static org.barghos.core.api.tuple.TupleConstants.*;

import org.barghos.core.api.tuple3.Tup3fC;
import org.barghos.core.api.tuple3.Tup3fR;
import org.barghos.core.api.util.ExtractParam;
import org.barghos.math.api.matrix.Mat4fC;
import org.barghos.math.api.model.EulerAngle3fC;
import org.barghos.math.api.transform.SystemOrientation3fC;
import org.barghos.math.api.vector.Vec3fC;
import org.barghos.math.api.vector.Vec3fUtil;
import org.barghos.math.matrix.Mat4f;
import org.barghos.math.model.EulerAngle3f;
import org.barghos.math.transform.SystemOrientation3f;
import org.barghos.math.vector.Vec3f;

public class FreeCamera3f implements Camera3f
{
	private Vec3fC oldPos = new Vec3f();
	private Vec3fC currentPos = new Vec3f();
	
	private SystemOrientation3fC oldOrientation = new SystemOrientation3f();
	private SystemOrientation3fC currentOrientation = new SystemOrientation3f();
	
	private Vec3fC velocity = new Vec3f();
	
	private EulerAngle3fC angularVelocity = new EulerAngle3f();
	
	private static <T extends Tup3fC> T lerp(Tup3fR t1, Tup3fR t2, float alpha, @ExtractParam T res)
	{
		return lerp(t1.getX(), t1.getY(), t1.getZ(), t2.getX(), t2.getY(), t2.getZ(), alpha, res);
	}
	
	private static <T extends Tup3fC> T lerp(float x1, float y1, float z1, float x2, float y2, float z2, float alpha, @ExtractParam T res)
	{
		res.set(Math.fma(1.0f - alpha, x1, alpha * x2), Math.fma(1.0f - alpha, y1, alpha * y2), Math.fma(1.0f - alpha, z1, alpha * z2));
		
		return res;
	}
	
	public Vec3fC getPos(float alpha)
	{
		return getPos(alpha, new Vec3f());
	}

	public <T extends Tup3fC> T getPos(float alpha, T res)
	{
		Vec3fC deltaDisplacement = this.velocity.mulN(alpha);
		deltaDisplacement.add(this.oldPos);
		
		res.set(deltaDisplacement);
		
		return res;
	}

	public float[] getPos(float alpha, float[] res)
	{
		Vec3fC deltaDisplacement = this.velocity.mulN(alpha);
		deltaDisplacement.add(this.oldPos);
		
		res[COMP_X] = deltaDisplacement.getX();
		res[COMP_Y] = deltaDisplacement.getY();
		res[COMP_Z] = deltaDisplacement.getZ();
		
		return res;
	}

	public Vec3fC getRight(float alpha)
	{
		return getRight(alpha, new Vec3f());
	}

	public <T extends Tup3fC> T getRight(float alpha, T res)
	{
		Mat4fC m = new Mat4f().initRotation(new Vec3f(0.0f, 1.0f, 0.0f), this.angularVelocity.getYaw() * alpha);
		m.revMul(new Mat4f().initRotation(new Vec3f(1.0f, 0.0f, 0.0f), this.angularVelocity.getPitch() * alpha));
		
		Vec3fC right = new Vec3f(this.oldOrientation.getRight());
		right.transform(m);
		
		res.set(right);
		
//		lerp(this.oldOrientation.getRight(), this.currentOrientation.getRight(), alpha, res);

		return res;
	}

	public float[] getRight(float alpha, float[] res)
	{
		Vec3fUtil.lerp(this.oldOrientation.getRight(), this.currentOrientation.getRight(), alpha, res);
		return res;
	}

	public Vec3fC getLeft(float alpha)
	{
		return getLeft(alpha, new Vec3f());
	}

	public <T extends Tup3fC> T getLeft(float alpha, T res)
	{
		Mat4fC m = new Mat4f().initRotation(new Vec3f(0.0f, 1.0f, 0.0f), this.angularVelocity.getYaw() * alpha);
		m.revMul(new Mat4f().initRotation(new Vec3f(1.0f, 0.0f, 0.0f), this.angularVelocity.getPitch() * alpha));
		
		Vec3fC left = new Vec3f(this.oldOrientation.getLeft());
		left.transform(m);
		
		res.set(left);
		
//		lerp(this.oldOrientation.getLeft(), this.currentOrientation.getLeft(), alpha, res);
		
		return res;
	}

	public float[] getLeft(float alpha, float[] res)
	{
		Vec3fUtil.lerp(this.oldOrientation.getLeft(), this.currentOrientation.getLeft(), alpha, res);
		return res;
	}

	public Vec3fC getUp(float alpha)
	{
		return getUp(alpha, new Vec3f());
	}

	public <T extends Tup3fC> T getUp(float alpha, T res)
	{
		Mat4fC m = new Mat4f().initRotation(new Vec3f(0.0f, 1.0f, 0.0f), this.angularVelocity.getYaw() * alpha);
		m.revMul(new Mat4f().initRotation(new Vec3f(1.0f, 0.0f, 0.0f), this.angularVelocity.getPitch() * alpha));
		
		Vec3fC up = new Vec3f(this.oldOrientation.getUp());
		up.transform(m);
		
		res.set(up);
		
//		lerp(this.oldOrientation.getUp(), this.currentOrientation.getUp(), alpha, res);
		
		return res;
	}

	public float[] getUp(float alpha, float[] res)
	{
		Vec3fUtil.lerp(this.oldOrientation.getUp(), this.currentOrientation.getUp(), alpha, res);
		return res;
	}

	public Vec3fC getDown(float alpha)
	{
		return getDown(alpha, new Vec3f());
	}

	public <T extends Tup3fC> T getDown(float alpha, T res)
	{
		Mat4fC m = new Mat4f().initRotation(new Vec3f(0.0f, 1.0f, 0.0f), this.angularVelocity.getYaw() * alpha);
		m.revMul(new Mat4f().initRotation(new Vec3f(1.0f, 0.0f, 0.0f), this.angularVelocity.getPitch() * alpha));
		
		Vec3fC down = new Vec3f(this.oldOrientation.getDown());
		down.transform(m);
		
		res.set(down);
		
//		lerp(this.oldOrientation.getDown(), this.currentOrientation.getDown(), alpha, res);
		return res;
	}

	public float[] getDown(float alpha, float[] res)
	{
		Vec3fUtil.lerp(this.oldOrientation.getDown(), this.currentOrientation.getDown(), alpha, res);
		return res;
	}

	public Vec3fC getForward(float alpha)
	{
		return getForward(alpha, new Vec3f());
	}

	public <T extends Tup3fC> T getForward(float alpha, T res)
	{
		Mat4fC m = new Mat4f().initRotation(new Vec3f(0.0f, 1.0f, 0.0f), this.angularVelocity.getYaw() * alpha);
		m.revMul(new Mat4f().initRotation(new Vec3f(1.0f, 0.0f, 0.0f), this.angularVelocity.getPitch() * alpha));
		
		Vec3fC forward = new Vec3f(this.oldOrientation.getForward());
		forward.transform(m);
		
		res.set(forward);
		
//		lerp(this.oldOrientation.getForward(), this.currentOrientation.getForward(), alpha, res);
		
		return res;
	}

	public float[] getForward(float alpha, float[] res)
	{
		Vec3fUtil.lerp(this.oldOrientation.getForward(), this.currentOrientation.getForward(), alpha, res);
		return res;
	}

	public Vec3fC getBack(float alpha)
	{
		return getBack(alpha, new Vec3f());
	}

	public <T extends Tup3fC> T getBack(float alpha, T res)
	{
		Mat4fC m = new Mat4f().initRotation(new Vec3f(0.0f, 1.0f, 0.0f), this.angularVelocity.getYaw() * alpha);
		m.revMul(new Mat4f().initRotation(new Vec3f(1.0f, 0.0f, 0.0f), this.angularVelocity.getPitch() * alpha));
		
		Vec3fC back = new Vec3f(this.oldOrientation.getBack());
		back.transform(m);
		
		res.set(back);
		
//		lerp(this.oldOrientation.getBack(), this.currentOrientation.getBack(), alpha, res);
		
		return res;
	}

	public float[] getBack(float alpha, float[] res)
	{
		Vec3fUtil.lerp(this.oldOrientation.getBack(), this.currentOrientation.getBack(), alpha, res);
		return res;
	}

	public Mat4fC getViewMatrix(float alpha)
	{
		return getViewMatrix(alpha, new Mat4f());
	}

	public <T extends Mat4fC> T getViewMatrix(float alpha, T res)
	{
		Mat4fC m = new Mat4f().initRotation(new Vec3f(0.0f, 1.0f, 0.0f), this.angularVelocity.getYaw() * alpha);
		m.revMul(new Mat4f().initRotation(new Vec3f(1.0f, 0.0f, 0.0f), this.angularVelocity.getPitch() * alpha));
		
		SystemOrientation3fC o = this.oldOrientation.clone().transform(m);

		res.set(o.toMatrixRH().transpose());
		
		res.mul(Mat4f.translation3d(getPos().negate()));
		
		return res;
	}
	
	public Camera3f move(float x, float y, float z)
	{
		this.currentPos.add(x, y, z);
		this.velocity.add(x, y, z);
		
		return this;
	}

	public Camera3f rotate(float x, float y, float z, float angle)
	{
		this.currentOrientation.transform(new Mat4f().initRotationRH(x, y, z, angle));
		
		this.angularVelocity.set(this.angularVelocity.getPitch() + x * angle, this.angularVelocity.getYaw() + y * angle, this.angularVelocity.getRoll() + z * angle);
		
		return this;
	}

	public Camera3f setPos(float x, float y, float z)
	{
		this.currentPos.set(x, y, z);
		
		return this;
	}

	public Camera3f setRight(float x, float y, float z)
	{
		this.currentOrientation.setRight(x, y, z);
		
		return this;
	}

	public Camera3f setUp(float x, float y, float z)
	{
		this.currentOrientation.setUp(x, y, z);
		
		return this;
	}

	public Camera3f setForward(float x, float y, float z)
	{
		this.currentOrientation.setForward(x, y, z);
		
		return this;
	}

	public Camera3f update()
	{
		this.oldOrientation.set(this.currentOrientation);
		
		Mat4fC m = new Mat4f().initRotation(new Vec3f(0.0f, 1.0f, 0.0f), this.angularVelocity.getYaw());
		m.revMul(new Mat4f().initRotation(new Vec3f(1.0f, 0.0f, 0.0f), this.angularVelocity.getPitch()));
//		this.oldOrientation.transform(m);
		
//		this.oldPos.set(this.currentPos);
		
		this.oldPos.add(this.velocity);
		
		this.velocity.set(0.0f);
		this.angularVelocity.set(0.0f, 0.0f, 0.0f);
		
		return this;
	}
}
