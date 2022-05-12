package org.codered;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class InputConfiguration
{
	private List<Integer> registeredKeys = new ArrayList<>();
	private Map<Integer,Boolean> lastKeyStates = new HashMap<>();
	private Map<Integer,Boolean> currentKeyStates = new HashMap<>();
	private Map<Integer,Boolean> pendingKeyStates = new HashMap<>();
	
	private List<Integer> registeredMouseButtons = new ArrayList<>();
	private Map<Integer,Boolean> lastMouseButtonStates = new HashMap<>();
	private Map<Integer,Boolean> currentMouseButtonStates = new HashMap<>();
	private Map<Integer,Boolean> pendingMouseButtonStates = new HashMap<>();
	
	public void registerKey(int key)
	{
		this.registeredKeys.add(key);
		this.lastKeyStates.put(key, false);
		this.currentKeyStates.put(key, false);
	}
	
	public void unregisterKey(int key)
	{
		this.registeredKeys.remove((Object)key);
		this.lastKeyStates.remove(key);
		this.currentKeyStates.remove(key);
	}
	
	public void registerMouseButton(int button)
	{
		this.registeredMouseButtons.add(button);
		this.lastMouseButtonStates.put(button, false);
		this.currentMouseButtonStates.put(button, false);
	}
	
	public void unregisterMouseButton(int button)
	{
		this.registeredMouseButtons.remove((Object)button);
		this.lastMouseButtonStates.remove(button);
		this.currentMouseButtonStates.remove(button);
	}
	
	public List<Integer> getRegisteredKeys()
	{
		return this.registeredKeys;
	}
	
	public List<Integer> getRegisteredMouseButtons()
	{
		return this.registeredMouseButtons;
	}

	public boolean hasActiveKeys()
	{
		return this.lastKeyStates.containsValue(true) || this.currentKeyStates.containsValue(true);
	}
	
	public boolean hasActiveMouseButtons()
	{
		return this.lastMouseButtonStates.containsValue(true) || this.currentMouseButtonStates.containsValue(true);
	}
	
	public void setKey(int key, boolean value)
	{
		this.pendingKeyStates.put(key, value);
	}
	
	public void setMouseButton(int button, boolean value)
	{
		this.pendingMouseButtonStates.put(button, value);
	}
	
	public boolean isKeyPressed(int key)
	{
		return !this.lastKeyStates.get(key) && this.currentKeyStates.get(key);
	}
	
	public boolean isKeyHold(int key)
	{
		return (!this.lastKeyStates.get(key) && this.currentKeyStates.get(key)) ||
				(this.lastKeyStates.get(key) && this.currentKeyStates.get(key)) ||
				(this.lastKeyStates.get(key) && !this.currentKeyStates.get(key));
	}
	
	public boolean isKeyReleased(int key)
	{
		return this.lastKeyStates.get(key) && !this.currentKeyStates.get(key);
	}
	
	public boolean isMouseButtonPressed(int key)
	{
		return !this.lastMouseButtonStates.get(key) && this.currentMouseButtonStates.get(key);
	}
	
	public boolean isMouseButtonHold(int key)
	{
		return (!this.lastMouseButtonStates.get(key) && this.currentMouseButtonStates.get(key)) ||
				(this.lastMouseButtonStates.get(key) && this.currentMouseButtonStates.get(key)) ||
				(this.lastMouseButtonStates.get(key) && !this.currentMouseButtonStates.get(key));
	}
	
	public boolean isMouseButtonReleased(int key)
	{
		return this.lastMouseButtonStates.get(key) && !this.currentMouseButtonStates.get(key);
	}
	
	public void tick()
	{
		for(int key : this.registeredKeys)
		{
			this.lastKeyStates.put(key, this.currentKeyStates.get(key));
			
			if(this.pendingKeyStates.containsKey(key)) this.currentKeyStates.put(key, this.pendingKeyStates.get(key));
		}
		
		for(int button : this.registeredMouseButtons)
		{
			this.lastMouseButtonStates.put(button, this.currentMouseButtonStates.get(button));
			
			if(this.pendingMouseButtonStates.containsKey(button)) this.currentMouseButtonStates.put(button, this.pendingMouseButtonStates.get(button));
		}
	}
}
