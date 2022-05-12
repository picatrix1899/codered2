package org.codered.engine;

public class FixedTimestepEngineLoop extends EngineLoop
{
	private static final double SECOND = 1000000000.0;
	
	private double tickTime;
	
	private long lastTime;
	private double unprocessedTime;
	
	private long startTime;
	private long passedTime;

	public FixedTimestepEngineLoop(double ticks)
	{
		this.tickTime = 1.0 / ticks;
	}
	
	protected void init()
	{
		this.lastTime = System.nanoTime();
		this.unprocessedTime = 0.0;
	}

	protected void cycle()
	{
		this.startTime = System.nanoTime();
		this.passedTime = this.startTime - this.lastTime;
		this.lastTime = this.startTime;
		
		this.unprocessedTime += this.passedTime / SECOND;

		while(this.unprocessedTime >= this.tickTime)
		{
			TickInfo tick = new TickInfo();
			tick.tickTime = this.tickTime;

			preTick(tick);
			tick(tick);
			postTick(tick);
			
			this.unprocessedTime -= this.tickTime;	
		}
		
		double delta = this.unprocessedTime / this.tickTime;
		
		TickInfo tick = new TickInfo();
		tick.tickTime = this.tickTime;
		tick.partialTime = delta;
		
		preRender(tick);
		render(tick);
		postRender(tick);
	}
}
