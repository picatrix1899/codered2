package org.codered.demo;

import static org.lwjgl.glfw.GLFW.*;

import org.barghos.math.api.matrix.Mat4fC;
import org.barghos.math.api.transform.SystemOrientation3fC;
import org.barghos.math.vector.Quatf;
import org.barghos.math.api.vector.QuatfC;
import org.barghos.math.api.vector.Vec3fC;
import org.barghos.math.matrix.Mat4f;
import org.barghos.math.model.EulerAngle3f;
import org.barghos.math.util.Maths;
import org.barghos.math.vector.Vec2f;
import org.barghos.math.vector.Vec3f;
import org.codered.Input;
import org.codered.engine.TickInfo;
import org.codered.game.Camera3f;
import org.codered.game.FreeCamera3f;

public class Player
{
	private Camera3f camera = new FreeCamera3f();
	
	private EulerAngle3f r = new EulerAngle3f();
	private EulerAngle3f angularVelocity = new EulerAngle3f();
	private Vec3fC pos = new Vec3f();
	private Vec3fC velocity = new Vec3f();
	
	public Player()
	{
		this.camera.setPos(0.0f, 0.0f, -10.0f);
		this.camera.setForward(0.0f, 0.0f, 1.0f);
		this.camera.setUp(0.0f, 1.0f, 0.0f);
		this.camera.setRight(1.0f, 0.0f, 0.0f);
		
		this.pos.set(0.0f, 0.0f, -10.0f);
	}
	
	public void preTick(TickInfo tick)
	{
		//this.camera.update();
		
		this.r.setYaw(this.r.getYaw() + this.angularVelocity.getYaw());
		this.r.setPitch(this.r.getPitch() + this.angularVelocity.getPitch());
		
		this.pos.add(this.velocity);
		
		this.velocity.set(0.0f);
		this.angularVelocity.set(0.0f, 0.0f, 0.0f);
	}
	
	public void tick(TickInfo tick)
	{
		Input input = Game.getInstance().getInput();
		
		Vec3f direction = new Vec3f();
		
		if(input.isKeyHold(GLFW_KEY_W))
		{
			direction.add(new Vec3f(0.0f, 0.0f, 1.0f));
//			direction.add(this.camera.getForward().normal());
		}
		
		if(input.isKeyHold(GLFW_KEY_S))
		{
			direction.add(new Vec3f(0.0f, 0.0f, -1.0f));
//			direction.add(this.camera.getBack().normal());
		}
		
		if(input.isKeyHold(GLFW_KEY_A))
		{
			direction.add(new Vec3f(-1.0f, 0.0f, 0.0f));
//			direction.add(this.camera.getLeft().normal());
		}
		
		if(input.isKeyHold(GLFW_KEY_D))
		{
			direction.add(new Vec3f(1.0f, 0.0f, 0.0f));
//			direction.add(this.camera.getRight().normal());
		}

		if(input.isMouseButtonPressed(2))
		{
			Game.getInstance().getMouse().grab(true);
		}
		
		if(input.isMouseButtonReleased(2))
		{
			Game.getInstance().getMouse().grab(false);
		}
		
		if(!direction.isExactlyZero())
		{
			direction.normal();
			
			float speed = 0.1f;
			
			Vec3fC velocity = direction.mulN(speed);
			
			QuatfC q1 = new Quatf().setByAxisAngle(0.0f, 1.0f, 0.0f, r.getYaw()).normal();
			QuatfC q2 = new Quatf().setByAxisAngle(1.0f, 0.0f, 0.0f, r.getPitch()).normal();
			
			QuatfC q = q1.mul(q2).normal();
			
			Mat4fC m = new Mat4f().initRotationRH(q1);
			
			velocity.transform(m);
			
			this.velocity = velocity;
			
//			this.camera.move(velocity);
//			pos.add(velocity);
		}
		
		Vec2f mouseDelta = Game.getInstance().getMouse().getDeltaPos();
		
		float pitch = mouseDelta.getY() * 0.5f * 0.006f;
		float yaw =  mouseDelta.getX() * 0.5f * 0.006f;
		
		angularVelocity.setPitch(pitch);
		angularVelocity.setYaw(yaw);
		
//		this.camera.rotate(0.0f, 1.0f, 0.0f, yaw);
		
//		this.camera.rotate(this.camera.getRight(), pitch);
	}
	
	public Camera3f getCamera()
	{
		return this.camera;
	}
	
	public Mat4fC toMatrix(float alpha)
	{
		float newYaw = r.getYaw() + angularVelocity.getYaw() * alpha;
		float newPitch = r.getPitch() + angularVelocity.getPitch() * alpha;
		
		QuatfC q1 = new Quatf().setByAxisAngle(0.0f, 1.0f, 0.0f, newYaw).normal();
		QuatfC q2 = new Quatf().setByAxisAngle(1.0f, 0.0f, 0.0f, newPitch).normal();
		
		QuatfC q = q1.mul(q2).normal();
		
		Mat4fC m = new Mat4f();
		m.revMul(new Mat4f().initRotationRH(q1).transpose());
		
		
		
//		Mat4fC m = new Mat4f().initRotation(new Vec3f(0.0f, 1.0f, 0.0f), this.angularVelocity.getYaw() * alpha);
//		m.revMul(new Mat4f().initRotation(new Vec3f(1.0f, 0.0f, 0.0f), this.angularVelocity.getPitch() * alpha));
		
//		SystemOrientation3fC o = this.oldOrientation.clone().transform(m);
		
		Vec3fC newPos = this.pos.addN(this.velocity.mulN(alpha));
		
		m.mul(Mat4f.translation3d(newPos.negateN()));
		
		return m;//this.camera.getViewMatrix(1.0f);
	}
}
