package org.codered;

import org.lwjgl.glfw.GLFW;
import org.barghos.math.vector.Vec2f;
import org.codered.window.Window;

public class Mouse
{
	private Window window;
	private long windowId;
	
	private boolean grabbed;
	
	private Vec2f currentPos = new Vec2f();
	private Vec2f delta = new Vec2f();
	
	private Vec2f returnPos = new Vec2f();
	
	private Vec2f center = new Vec2f();
	
	public Mouse(Window window)
	{
		this.window = window;
		this.windowId = this.window.getId();
		
		this.center.set(this.window.getWidth() / 2.0f, this.window.getHeight() / 2.0f);
		
		GLFW.glfwSetCursorPosCallback(this.windowId, (w, x, y) -> cursorPosCallback(w, x, y));
	}
	
	public void setMousePos(Vec2f pos)
	{
		GLFW.glfwSetCursorPos(this.windowId, pos.x, pos.y);
		this.currentPos.set(pos);
	}
	
	public void setMousePos(double x, double y)
	{
		GLFW.glfwSetCursorPos(this.windowId, x, y);
		this.currentPos.set((float)x, (float)y);
	}
	
	public void grab(boolean state)
	{
		this.grabbed = state;
		
		if(state)
		{
			GLFW.glfwSetInputMode(this.windowId, GLFW.GLFW_CURSOR, GLFW.GLFW_CURSOR_DISABLED);
			this.returnPos.set(this.currentPos);
			
			GLFW.glfwSetCursorPos(this.windowId, this.center.x, this.center.y);
			this.delta.set(0, 0);
		}
		else
		{
			GLFW.glfwSetInputMode(this.windowId, GLFW.GLFW_CURSOR, GLFW.GLFW_CURSOR_NORMAL);
			GLFW.glfwSetCursorPos(this.windowId, this.returnPos.x, this.returnPos.y);
			this.delta.set(0, 0);
		}
	}
	
	public void cursorPosCallback(long window, double x, double y)
	{
		this.currentPos.set((float)x, (float)y);
		
		if((this.grabbed && (this.center.x != x || this.center.y != y)))
		{
			this.delta.set(this.currentPos.subN(this.center));
		}

	}
	
	public void tick()
	{
		if(this.grabbed)
		{
			this.currentPos.set(this.center);
			this.delta.set(0, 0);
			GLFW.glfwSetCursorPos(this.windowId, this.center.x, this.center.y);
		}
	}
	
	public Vec2f getCurrentPos()
	{
		return this.currentPos;
	}
	
	public Vec2f getDeltaPos()
	{
		return this.delta;
	}
}
