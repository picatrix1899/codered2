package org.codered.rendering.shader;

import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL20;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.codered.GLCom;
import org.codered.engine.CriticalEngineStateError;

public class ShaderProgram
{
	private int id;

	private Map<String,Integer> attribs = new HashMap<>();
	private List<Uniform> fixedUniforms = new ArrayList<>();
	
	public ShaderProgram()
	{
		this.id = GLCom.genProgram();
	}
	
	public void start()
	{
		GL20.glUseProgram(this.id);
	}
	
	public void stop()
	{
		GL20.glUseProgram(0);
	}
	
	public int getId()
	{
		return this.id;
	}
	
	public void compile()
	{
		for(String name : this.attribs.keySet())
		{
			GL20.glBindAttribLocation(this.id, this.attribs.get(name), name);
		}
		
		GL20.glLinkProgram(this.id);
		if(GL20.glGetProgrami(this.id, GL20.GL_LINK_STATUS) == GL11.GL_FALSE)
		{
			System.err.println(GL20.glGetProgramInfoLog(this.id, 1000));
			
			throw new CriticalEngineStateError();
		}
		
		GL20.glValidateProgram(this.id);
		if(GL20.glGetProgrami(this.id, GL20.GL_VALIDATE_STATUS) == GL11.GL_FALSE)
		{
			System.err.println(GL20.glGetProgramInfoLog(this.id, 1000));
			
			throw new CriticalEngineStateError();
		}
		
		for(Uniform u : this.fixedUniforms)
		{
			u.loadUniformLocations(this.getId());
		}
	}
	
	protected void addFixedUniform(Uniform uniform)
	{
		this.fixedUniforms.add(uniform);
	}
	
	protected void addAttribute(int attrib, String name)
	{
		this.attribs.put(name, attrib);
	}
	
	public void addFixedShaderPart(ShaderPart part)
	{
		GL20.glAttachShader(this.id, part.getId());
	}

	public void release()
	{
		GLCom.deleteProgram(this.id);
	}
}
