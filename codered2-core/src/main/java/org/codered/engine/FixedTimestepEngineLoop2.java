package org.codered.engine;

public class FixedTimestepEngineLoop2 extends EngineLoop
{
	private static final double SECOND = 1000000000;
	
	private double tickTime;
	private double frameTime;
	private double frames;
	
	private long lastTime;
	private double unprocessedTime;
	
	private long startTime;
	private long passedTime;
	
	public FixedTimestepEngineLoop2(double ticks, double frames)
	{
		this.tickTime = 1.0d / ticks;
		this.frameTime = frames / this.tickTime;
	}
	
	protected void init()
	{
		this.lastTime = System.nanoTime();
		this.unprocessedTime = 0;
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
		
		double delta = this.unprocessedTime / this.tickTime; // 0-1
		
		double passedFrames = (int) delta / this.frameTime;
		double frameMin = this.frameTime * passedFrames;
		double frameMax = this.frameTime * Math.max(passedFrames + 1, this.frames);
		double inFrameDelta = round(delta - frameMin, frameMin, frameMax);
		
		TickInfo tick = new TickInfo();
		tick.tickTime = this.tickTime;
		tick.partialTime = inFrameDelta;
		
		preRender(tick);
		render(tick);
		postRender(tick);
	}
	
	private double round(double value, double min, double max)
	{
		return value - min <= max - value ? min : max;
	}
}
