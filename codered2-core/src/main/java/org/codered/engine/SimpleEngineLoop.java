package org.codered.engine;


public class SimpleEngineLoop extends EngineLoop
{
	private long lastTime;
	private long startTime;
	private long passedTime;
	
	protected void init()
	{
		this.lastTime = System.nanoTime();
	}
	
	protected void cycle()
	{
		this.startTime = System.nanoTime();
		this.passedTime = this.startTime - this.lastTime;
		
		TickInfo updateTick = new TickInfo();
		updateTick.tickTime = this.passedTime;
		
		preTick(updateTick);
		tick(updateTick);
		postTick(updateTick);
		
		TickInfo renderTick = new TickInfo();
		renderTick.tickTime = this.passedTime;
		
		preRender(renderTick);
		render(renderTick);
		postRender(renderTick);
	}
}
