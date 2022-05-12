package org.codered.engine;


public interface IEngineRoutine
{
	default void init() throws Exception { }
	
	default void preTick(TickInfo tick) {}
	default void tick(TickInfo tick) {}
	default void postTick(TickInfo tick) {}
	
	default void preRender(TickInfo tick) {}
	default void render(TickInfo tick) {}
	default void postRender(TickInfo tick) {}
	
	default void release() { }
}
