package org.codered.rendering.shader;

import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL20;

import org.codered.GLCom;

public class ShaderPart
{
	private int id;

	public ShaderPart(int type, String data)
	{
		this.id = GLCom.genShader(type);
		
		GL20.glShaderSource(this.id, data);
		
		GL20.glCompileShader(this.id);
		
		if(GL20.glGetShaderi(this.id,GL20.GL_COMPILE_STATUS) == GL11.GL_FALSE)
		{
			try
			{
				throw new Exception(GL20.glGetShaderInfoLog(this.id, 500));
			}
			catch(Exception e)
			{
				e.printStackTrace();
				System.out.println(data);
			}
		}
	}
	
	public int getId()
	{
		return id;
	}

	public void release()
	{
		GLCom.deleteShader(this.id);
	}
}
