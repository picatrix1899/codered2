package org.codered.game;

import org.codered.Model;

public class StaticEntityModel extends StaticEntity
{
	private Model model;
	
	public StaticEntityModel(Model model)
	{
		setModel(model);
	}
	
	public Model getModel()
	{
		return this.model;
	}
	
	public StaticEntityModel setModel(Model model)
	{
		this.model = model;
		
		return this;
	}
}
