package org.codered;


public class RepeatingTickTimer
{
	private Runnable task;
	
	private long passedTicks;
	private long maxTicks;
	private boolean isEnabled;
	
	public RepeatingTickTimer() { }
	
	public RepeatingTickTimer(Runnable task)
	{
		this.task = task;
	}
	
	public RepeatingTickTimer(long maxTicks)
	{
		this.maxTicks = maxTicks;
	}
	
	public RepeatingTickTimer(long maxTicks, Runnable task)
	{
		this.maxTicks = maxTicks;
		this.task = task;
	}
	
	public void tick()
	{
		if(!this.isEnabled) return;
		
		passedTicks++;
		
		if(this.passedTicks < this.maxTicks) return;
		
		if(this.task != null) this.task.run();
		
		this.passedTicks = 0;
	}
	
	public void reset()
	{
		this.passedTicks = 0;
	}
	
	public RepeatingTickTimer setTicks(long ticks)
	{
		this.maxTicks = ticks;
		this.passedTicks = 0;
		
		return this;
	}
	
	public RepeatingTickTimer setTask(Runnable task)
	{
		this.task = task;
		
		return this;
	}
	
	public RepeatingTickTimer setEnabled(boolean enabled)
	{
		this.isEnabled = enabled;
		return this;
	}
	
	public boolean isEnabled()
	{
		return this.isEnabled;
	}
}
