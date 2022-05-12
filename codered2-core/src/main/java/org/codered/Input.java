package org.codered;

import java.util.Stack;

import org.codered.window.Window;

import static org.lwjgl.glfw.GLFW.*;

public class Input
{
	private long windowId;
	
	private Stack<InputConfiguration> configuration = new Stack<>();
	private boolean isConfigPushPending = false;
	private boolean isConfigPopPending = false;
	private InputConfiguration pending; 

	public int lastFreeKey;
	
	public Input(Window window)
	{
		this.windowId = window.getId();
		
		glfwSetKeyCallback(this.windowId, (w, k, s, a, m) -> keyCallback(w, k, s, a, m));
		glfwSetMouseButtonCallback(this.windowId, (w, b, a, m) -> mouseButtonCallback(w, b, a, m));
	}
	
	public void pushInputConfiguration(InputConfiguration config)
	{
		pending = config;
		this.isConfigPushPending = true;
	}
	
	public void popInputConfiguration()
	{
		this.isConfigPopPending = true;
	}
	
	public void keyCallback(long window, int key, int scancode, int action, int mods)
	{
		if(this.configuration.isEmpty()) return;
		
		this.configuration.peek().setKey(key, action != GLFW_RELEASE);
	}
	
	public void mouseButtonCallback(long window, int button, int action, int mods)
	{
		if(this.configuration.isEmpty()) return;

		this.configuration.peek().setMouseButton(button, action != GLFW_RELEASE);
	}
	
	public void tick()
	{
		if(this.isConfigPushPending)
		{
			if(!this.configuration.isEmpty())
			{
				if(!this.configuration.peek().hasActiveKeys() && !this.configuration.peek().hasActiveMouseButtons())
				{
					this.configuration.push(this.pending);
					this.pending = null;
					this.isConfigPushPending = false;
				}
			}
			else
			{
				this.configuration.push(this.pending);
				this.pending = null;
				this.isConfigPushPending = false;
			}
		}
		
		if(this.isConfigPopPending && !this.configuration.isEmpty())
		{
			if(!this.configuration.peek().hasActiveKeys() && !this.configuration.peek().hasActiveMouseButtons())
			{
				this.configuration.pop();
				this.isConfigPopPending = false;
			}
		}
		
		if(this.configuration.isEmpty()) return;
		if(this.isConfigPopPending || this.isConfigPushPending) return;
		
		this.configuration.peek().tick();
	}
	
	public boolean isKeyPressed(int key)
	{
		if(this.configuration.isEmpty()) return false;
		if(this.isConfigPopPending || this.isConfigPushPending) return false;
		
		return this.configuration.peek().isKeyPressed(key);
	}
	
	public boolean isKeyHold(int key)
	{
		if(this.configuration.isEmpty()) return false;
		if(this.isConfigPopPending || this.isConfigPushPending) return false;
		
		return this.configuration.peek().isKeyHold(key);
	}
	
	public boolean isKeyReleased(int key)
	{
		if(this.configuration.isEmpty()) return false;
		if(this.isConfigPopPending || this.isConfigPushPending) return false;
		
		return this.configuration.peek().isKeyReleased(key);
	}
	
	public boolean isMouseButtonPressed(int key)
	{
		if(this.configuration.isEmpty()) return false;
		if(this.isConfigPopPending || this.isConfigPushPending) return false;
		
		return this.configuration.peek().isMouseButtonPressed(key);
	}
	
	public boolean isMouseButtonHold(int key)
	{
		if(this.configuration.isEmpty()) return false;
		if(this.isConfigPopPending || this.isConfigPushPending) return false;
		
		return this.configuration.peek().isMouseButtonHold(key);
	}
	
	public boolean isMouseButtonReleased(int key)
	{
		if(this.configuration.isEmpty()) return false;
		if(this.isConfigPopPending || this.isConfigPushPending) return false;
		
		return this.configuration.peek().isMouseButtonReleased(key);
	}
}
