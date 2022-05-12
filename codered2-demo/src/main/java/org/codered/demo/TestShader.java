package org.codered.demo;

import java.io.IOException;

import org.codered.URLUtils;
import org.codered.rendering.shader.ShaderPart;
import org.codered.rendering.shader.ShaderProgram;
import org.codered.rendering.shader.UniformMatrix4f;
import org.codered.rendering.shader.UniformTexture2D;
import org.codered.resource.ShaderPartData;
import org.codered.resource.ShaderPartLoader;
import org.lwjgl.opengl.GL20;

public class TestShader extends ShaderProgram
{
	private ShaderPart vertexShader;
	private ShaderPart fragmentShader;
	
	public final UniformTexture2D textureDiffuse;
	
	public final UniformMatrix4f matrixProjection;
	public final UniformMatrix4f matrixView;
	public final UniformMatrix4f matrixModel;
	
	public TestShader() throws IOException
	{
		ShaderPartData vertexData = ShaderPartLoader.loadResource(URLUtils.getEmbededResourceURL("shader.vs"));
		this.vertexShader = new ShaderPart(GL20.GL_VERTEX_SHADER, vertexData.getData());
		
		ShaderPartData fragmentData = ShaderPartLoader.loadResource(URLUtils.getEmbededResourceURL("shader.fs"));
		this.fragmentShader = new ShaderPart(GL20.GL_FRAGMENT_SHADER, fragmentData.getData());

		this.textureDiffuse = new UniformTexture2D("diffuse", 0);

		this.matrixProjection = new UniformMatrix4f("T_projection");
		this.matrixView = new UniformMatrix4f("T_view");
		this.matrixModel = new UniformMatrix4f("T_model");
		
		addAttribute(0, "coords");
		addAttribute(1, "texCoords");
		
		addFixedShaderPart(this.vertexShader);
		addFixedShaderPart(this.fragmentShader);
		
		addFixedUniform(this.textureDiffuse);
		addFixedUniform(this.matrixProjection);
		addFixedUniform(this.matrixView);
		addFixedUniform(this.matrixModel);
		
	}
	
	public void release()
	{
		super.release();
		
		this.vertexShader.release();
		this.fragmentShader.release();
	}
}
