package org.codered.window;

import static org.lwjgl.glfw.GLFW.*;

import org.barghos.math.vector.Vec2f;
import org.codered.engine.TickInfo;
import org.lwjgl.opengl.GL;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GLCapabilities;

public class Window
{
	private Vec2f size;
	private String title;
	
	private IWindowCloseCallback windowCloseCallback;
	private IWindowResizeCallback windowResizeCallback;
	private IWindowFramebufferResizeCallback windowFramebufferResizeCallback;
	
	private WindowHints windowHints;
	
	private long id;
	
	private GLCapabilities capabilities;
	
	private boolean isReleased;

	private Window() { }
	
	public void init()
	{
		WindowHint.startVisible(windowHints.startVisible);
		WindowHint.samples(windowHints.samples);
		WindowHint.doubleBuffering(windowHints.doubleBuffer);
		WindowHint.resizable(windowHints.resizable);
		
		this.id = glfwCreateWindow((int)this.size.x, (int)this.size.y, this.title, 0, 0);
		
		if(this.id == 0)
		{
			throw new Error();
		}
		
		glfwMakeContextCurrent(this.id);
		
		this.capabilities = GL.createCapabilities();
		
		glfwSetWindowSizeCallback(this.id, (id, w, h) -> onWindowResize(id, w, h));
		glfwSetFramebufferSizeCallback(this.id, (id, w, h) -> onWindowFramebufferResize(id, w, h));
		
		glfwSwapInterval(0);
	}
	
	public void render(TickInfo tick)
	{
		if(isReleased()) return;

		glfwSwapBuffers(this.id);
	}
	
	public void tick(TickInfo tick)
	{
		if(isReleased()) return;

		glfwPollEvents();
		
		if(glfwWindowShouldClose(this.id))
			onWindowClose();
	}

	public void release()
	{
		this.isReleased = true;
		
		glfwDestroyWindow(this.id);
	}
	
	public void show()
	{
		glfwShowWindow(this.id);
	}
	
	public void hide()
	{
		glfwHideWindow(this.id);
	}

	public boolean isReleased()
	{
		return this.isReleased;
	}
	
	public Vec2f getSize()
	{
		return this.size;
	}
	
	public int getWidth()
	{
		return (int)this.size.x;
	}
	
	public int getHeight()
	{
		return (int)this.size.y;
	}
	
	public long getId()
	{
		return this.id;
	}
	
	public GLCapabilities getGLCapabilities()
	{
		return this.capabilities;
	}
	
	public String getTitle()
	{
		return this.title;
	}
	
	public void setViewport()
	{
		GL11.glViewport(0, 0, (int)this.size.x, (int)this.size.y);
	}
	
	public void setTitle(String title)
	{
		this.title = title;
		glfwSetWindowTitle(this.id, title);
	}
	
	public void setResizeCallback(IWindowResizeCallback del)
	{
		this.windowResizeCallback = del;
	}
	
	public void setCloseCallback(IWindowCloseCallback del)
	{
		this.windowCloseCallback = del;
	}
	
	protected void onWindowResize(long windowId, int width, int height)
	{
		this.size.set(width, height);
		
		this.windowResizeCallback.invoke(this.size);
	}
	
	protected void onWindowClose()
	{
		this.windowCloseCallback.invoke();
	}
	
	protected void onWindowFramebufferResize(long window, int width, int height)
	{
		this.windowFramebufferResizeCallback.invoke(new Vec2f(width, height));
	}
	
	public static class Builder
	{
		private Vec2f size;
		private String title;
		
		private IWindowCloseCallback windowCloseCallback;
		private IWindowResizeCallback windowResizeCallback;
		private IWindowFramebufferResizeCallback windowFramebufferResizeCallback;
		
		private WindowHints windowHints = new WindowHints();
		
		public Builder size(int width, int height)
		{
			if(this.size == null) this.size = new Vec2f();
			this.size.x = width;
			this.size.y = height;
			
			return this;
		}
		
		public Builder size(Vec2f size)
		{
			this.size = size;
			
			return this;
		}
		
		public Builder title(String title)
		{
			this.title = title;
			
			return this;
		}
		
		public Builder windowCloseCallback(IWindowCloseCallback callback)
		{
			this.windowCloseCallback = callback;
			
			return this;
		}
		
		public Builder windowResizeCallback(IWindowResizeCallback callback)
		{
			this.windowResizeCallback = callback;
			
			return this;
		}
		
		public Builder windowFramebufferResizeCallback(IWindowFramebufferResizeCallback callback)
		{
			this.windowFramebufferResizeCallback = callback;
			
			return this;
		}
		
		public Builder startVisible(boolean state)
		{
			this.windowHints.startVisible = state;
			
			return this;
		}
		
		public Builder samples(int samples)
		{
			this.windowHints.samples = samples;
			
			return this;
		}
		
		public Builder doubleBuffer(boolean state)
		{
			this.windowHints.doubleBuffer = state;
			
			return this;
		}
		
		public Builder resizable(boolean state)
		{
			this.windowHints.resizable = state;
			
			return this;
		}
		
		public Window create()
		{
			Window window = new Window();
			
			window.size = this.size;
			if(window.size == null) window.size = new Vec2f(100, 100);
			
			window.title = this.title;
			if(window.title == null) window.title = "CodeRed Engine 2";
			
			window.windowCloseCallback = this.windowCloseCallback;
			if(window.windowCloseCallback == null) window.windowCloseCallback = EmptyWindowCloseCallback.getInstance();
			
			window.windowResizeCallback = this.windowResizeCallback;
			if(window.windowResizeCallback == null) window.windowResizeCallback = EmptyWindowResizeCallback.getInstance();
			
			window.windowFramebufferResizeCallback = this.windowFramebufferResizeCallback;
			if(window.windowFramebufferResizeCallback == null) window.windowFramebufferResizeCallback = EmptyWindowFramebufferResizeCallback.getInstance();
			
			window.windowHints = this.windowHints;
			
			return window;
		}
	}
	
	public static interface IWindowCloseCallback
	{
		void invoke();
	}
	
	public static interface IWindowResizeCallback
	{
		void invoke(Vec2f newSize);
	}
	
	public static interface IWindowFramebufferResizeCallback
	{
		void invoke(Vec2f newSize);
	}
	
	public static final class EmptyWindowCloseCallback implements IWindowCloseCallback
	{
		private static EmptyWindowCloseCallback INSTANCE;
		
		public static EmptyWindowCloseCallback getInstance()
		{
			if(INSTANCE == null) INSTANCE = new EmptyWindowCloseCallback();
			
			return INSTANCE;
		}
		
		private EmptyWindowCloseCallback() {}
		
		public void invoke() {}
	}
	
	public static final class EmptyWindowResizeCallback implements IWindowResizeCallback
	{
		private static EmptyWindowResizeCallback INSTANCE;
			
		public static EmptyWindowResizeCallback getInstance()
		{
			if(INSTANCE == null) INSTANCE = new EmptyWindowResizeCallback();
			
			return INSTANCE;
		}
		
		private EmptyWindowResizeCallback() {}
		
		public void invoke(Vec2f newSize) {}
	}
	
	public static final class EmptyWindowFramebufferResizeCallback implements IWindowFramebufferResizeCallback
	{
		private static EmptyWindowFramebufferResizeCallback INSTANCE;
			
		public static EmptyWindowFramebufferResizeCallback getInstance()
		{
			if(INSTANCE == null) INSTANCE = new EmptyWindowFramebufferResizeCallback();
			
			return INSTANCE;
		}
		
		private EmptyWindowFramebufferResizeCallback() {}
		
		public void invoke(Vec2f newSize) {}
	}
}
