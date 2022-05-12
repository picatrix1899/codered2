package org.codered.engine;

public abstract class EngineLoop
{
	protected boolean isRunning;
	
	protected IEngineLoopReceiver receiver;
	
	public void start()
	{
		this.isRunning = true;

		init();
		
		run();
		
		release();
	}
	
	protected void run()
	{
		while(this.isRunning)
		{
			cycle();
		}
	}
	
	public void stop()
	{
		this.isRunning = false;
	}
	
	protected void init() { }
	
	protected abstract void cycle();
	
	protected void release() { }
	
	protected void preTick(TickInfo tick)
	{
		this.receiver.preTick(tick);
	}
	
	protected void tick(TickInfo tick)
	{
		this.receiver.tick(tick);
	}
	
	protected void postTick(TickInfo tick)
	{
		this.receiver.postTick(tick);
	}
	
	protected void preRender(TickInfo tick)
	{
		this.receiver.preRender(tick);
	}
	
	protected void render(TickInfo tick)
	{
		this.receiver.render(tick);
	}
	
	protected void postRender(TickInfo tick)
	{
		this.receiver.postRender(tick);
	}
	
	public void setReceiver(IEngineLoopReceiver receiver)
	{
		this.receiver = receiver;
	}
}
