package org.codered.engine;


public class CriticalEngineStateError extends Error
{
	private static final long serialVersionUID = 1L;

	public CriticalEngineStateError()
	{
		super();
	}
	
	public CriticalEngineStateError(String message)
	{
		super(message);
	}
}
