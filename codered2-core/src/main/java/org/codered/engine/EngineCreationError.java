package org.codered.engine;


public class EngineCreationError extends CriticalEngineStateError
{
	private static final long serialVersionUID = 1L;
	
	public EngineCreationError()
	{
		super();
	}
	
	public EngineCreationError(String message)
	{
		super(message);
	}
	
	public String getMessage()
	{
		return "Engine Creation Error: " + super.getMessage();
	}
}
