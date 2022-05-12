package org.codered.engine;


public interface IEngineLoopReceiver
{
	default void preTick(TickInfo tick) {}
	default void tick(TickInfo tick) {}
	default void postTick(TickInfo tick) {}
	
	default void preRender(TickInfo tick) {}
	default void render(TickInfo tick) {}
	default void postRender(TickInfo tick) {}
}
