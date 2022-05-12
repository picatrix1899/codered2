package org.codered.engine;

import static org.lwjgl.glfw.GLFW.*;

import org.lwjgl.glfw.GLFWErrorCallback;
import org.codered.Input;
import org.codered.Mouse;
import org.codered.window.Window;
import org.codered.window.WindowHint;

public class Engine implements IEngineLoopReceiver
{
	public static final String GL_VERSION = "4.2";
	public static final String GL_PROFILE = "core";
	
	protected EngineLoop engineLoop;
	protected Window window;
	protected IEngineRoutine routine;
	protected GLFWErrorCallback glfwErrorCallback;
	protected String threadName;
	protected Input input;
	protected Mouse mouse;
	
	private Engine()
	{
		
	}
	
	public synchronized void start()
	{
		try
		{
			glInit();
		
			internalInit();
			
			init();
			
			this.engineLoop.start();
		}
		catch (CriticalEngineStateError e)
		{
			e.printStackTrace();
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		
		try
		{
			release();
		
			glRelease();
		}
		catch (CriticalEngineStateError e)
		{
			e.printStackTrace();
		}
	}
	
	public synchronized void stop()
	{
		this.engineLoop.stop();
	}
	
	protected void glInit()
	{
		setThreadName(this.threadName);
		
		if(!glfwInit())
		{
			throw new EngineCreationError("Cannot initialize GLFW context.");
		}
		
		setGLFWErrorCallback(this.glfwErrorCallback);
		
		WindowHint.glVersion(GL_VERSION + " " + GL_PROFILE);
	}
	
	protected void glRelease()
	{
		glfwTerminate();
	}
	
	protected void internalInit()
	{
		this.engineLoop.setReceiver(this);
		
	}
	
	protected void init() throws Exception
	{
		this.window.init();
		this.input = new Input(this.window);
		this.mouse = new Mouse(this.window);
		this.routine.init();
	}
	
	public void preTick(TickInfo tick)
	{
		this.window.tick(tick);
		this.input.tick();
		this.routine.preTick(tick);
	}
	
	public void tick(TickInfo tick)
	{
		this.routine.tick(tick);
	}
	
	public void postTick(TickInfo tick)
	{
		this.routine.postTick(tick);
		this.mouse.tick();
	}
	
	public void preRender(TickInfo tick)
	{
		this.routine.preRender(tick);
	}
	
	public void render(TickInfo tick)
	{
		this.routine.render(tick);
	}
	
	public void postRender(TickInfo tick)
	{
		this.routine.postRender(tick);
		this.window.render(tick);
	}
	
	protected void release()
	{
		this.routine.release();
		this.window.release();
	}
	
	public Window getWindow()
	{
		return this.window;
	}
	
	public long getWindowId()
	{
		return this.window.getId();
	}
	
	public EngineLoop getEngineLoop()
	{
		return this.engineLoop;
	}
	
	public IEngineRoutine getRoutine()
	{
		return this.routine;
	}
	
	public GLFWErrorCallback getGLFWErrorCallback()
	{
		return this.glfwErrorCallback;
	}
	
	public void setGLFWErrorCallback(GLFWErrorCallback callback)
	{
		glfwSetErrorCallback(callback);
		this.glfwErrorCallback = callback;
	}
	
	public String getThreadName()
	{
		return this.threadName;
	}
	
	public void setThreadName(String name)
	{
		Thread.currentThread().setName(this.threadName);
		this.threadName = name;
	}
	
	public Input getInput()
	{
		return this.input;
	}
	
	public Mouse getMouse()
	{
		return this.mouse;
	}
	
	public static class Builder
	{
		private EngineLoop engineLoop;
		private Window window;
		private IEngineRoutine routine;
		private GLFWErrorCallback glfwErrorCallback;
		private String threadName;
		
		public Builder engineLoop(EngineLoop loop)
		{
			this.engineLoop = loop;
			
			return this;
		}
		
		public Builder window(Window window)
		{
			this.window = window;
			
			return this;
		}
		
		public Builder routine(IEngineRoutine routine)
		{
			this.routine = routine;
			
			return this;
		}
		
		public Builder glfwErrorCallback(GLFWErrorCallback callback)
		{
			this.glfwErrorCallback = callback;
			
			return this;
		}
		
		public Builder threadName(String name)
		{
			this.threadName = name;
			
			return this;
		}
		
		public Engine create()
		{
			Engine engine = new Engine();
			
			engine.engineLoop = this.engineLoop;
			if(engine.engineLoop == null) this.engineLoop = new SimpleEngineLoop();
			
			engine.window = this.window;
			if(engine.window == null) throw new EngineCreationError("No window specified for Engine.");
			
			engine.routine = this.routine;
			if(engine.window == null) throw new EngineCreationError("No routine specified for Engine.");
			
			engine.glfwErrorCallback = this.glfwErrorCallback;
			if(engine.glfwErrorCallback == null) engine.glfwErrorCallback = glfwSetErrorCallback(GLFWErrorCallback.createPrint(System.out));
			
			engine.threadName = this.threadName;
			if(engine.threadName == null || engine.threadName.isBlank()) engine.threadName = "CodeRed Engine 2";

			return engine;
		}
	}
}
