package org.codered.rendering;

import java.util.ArrayList;
import java.util.List;

public class VAOPointers
{
	private List<VAOPointer> pointers = new ArrayList<>();
	
	public void add(VAOPointer pointer)
	{
		this.pointers.add(pointer);
	}
	
	public void add(int index, int size, int type, boolean isNormalized, int stride, int pointer)
	{
		VAOPointer p = new VAOPointer(index, size, type, isNormalized, stride, pointer);
		
		this.pointers.add(p);
	}
	
	public void add(int index, int size, int type, boolean isNormalized, int stride)
	{
		VAOPointer p = new VAOPointer(index, size, type, isNormalized, stride, 0);
		
		this.pointers.add(p);
	}
	
	public void apply()
	{
		for(VAOPointer pointer : this.pointers)
		{
			pointer.apply();
		}
	}
}
