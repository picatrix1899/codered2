package org.codered.demo;

import static org.lwjgl.glfw.GLFW.*;

import java.util.ArrayList;
import java.util.List;

import org.barghos.core.api.util.BufferUtil;
import org.barghos.math.api.matrix.Mat4fC;
import org.barghos.math.matrix.Mat4f;
import org.barghos.math.util.Maths;
import org.lwjgl.glfw.GLFWErrorCallback;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL20;
import org.codered.GLCom;
import org.codered.Input;
import org.codered.InputConfiguration;
import org.codered.Model;
import org.codered.Mouse;
import org.codered.RepeatingTickTimer;
import org.codered.URLUtils;
import org.codered.engine.Engine;
import org.codered.engine.FixedTimestepEngineLoop;
import org.codered.engine.IEngineRoutine;
import org.codered.engine.TickInfo;
import org.codered.game.StaticEntityModel;
import org.codered.rendering.Material;
import org.codered.rendering.MipmapSettings;
import org.codered.rendering.Texture2D;
import org.codered.rendering.TextureUtils;
import org.codered.resource.AssimpLoader;
import org.codered.resource.Mesh;
import org.codered.resource.ModelData;
import org.codered.resource.TextureData;
import org.codered.resource.TextureLoader;
import org.codered.resource.Utils;
import org.codered.window.Window;

public class Game implements IEngineRoutine
{	
	private static Game INSTANCE;
	public static Game getInstance()
	{
		return INSTANCE;
	}
	
	private int width = 800;
	private int height = 600;
	private String title = "Demo";
	
	private Engine engine;

	AssimpLoader loader;
	
	TestShader shader;
	
	Texture2D fallbackTexture;
	
	Mat4fC viewMatrix;
	Mat4fC projectionMatrix;
	
	Model model;
	
	List<StaticEntityModel> entities;
	
	GameSettings settings;
	
	InputConfiguration defaultInputConfiguration;
	
	MipmapSettings mipmapSettings;
	
	boolean anisotropicEnabled;
	
	Player player;
	
	public void start()
	{
		INSTANCE = this;

		BufferUtil.BUFFER_FACTORY = new LWJGLNioBufferFactory();
		
		Window window = new Window.Builder()
			.size(this.width, this.height)
			.title(this.title)
			.windowCloseCallback(() -> this.engine.stop())
			.samples(16)
			.resizable(false)
			.create();
		
		this.engine = new Engine.Builder()
			.engineLoop(new FixedTimestepEngineLoop(30))
			.glfwErrorCallback(GLFWErrorCallback.createPrint(System.out))
			.threadName(this.title)
			.routine(this)
			.window(window)
			.create();
		
		this.settings = new GameSettings();
		this.settings.setChangeCallback(() -> settingsChange());
		
		this.engine.start();
	}
	
	public void init() throws Exception
	{
		TextureData fallbackTextureData = new TextureData();
		fallbackTextureData.pixels = new int[] {0xFF000000};
		fallbackTextureData.width = 1;
		fallbackTextureData.height = 1;
		this.fallbackTexture = TextureUtils.genTextureWithMipmaps(fallbackTextureData, new MipmapSettings().mipmapsEnabled(false));
		
		this.loader = new AssimpLoader();
		
		this.defaultInputConfiguration = new InputConfiguration();
		this.defaultInputConfiguration.registerKey(GLFW_KEY_ESCAPE);
		this.defaultInputConfiguration.registerKey(GLFW_KEY_TAB);
		this.defaultInputConfiguration.registerKey(GLFW_KEY_W);
		this.defaultInputConfiguration.registerKey(GLFW_KEY_A);
		this.defaultInputConfiguration.registerKey(GLFW_KEY_S);
		this.defaultInputConfiguration.registerKey(GLFW_KEY_D);
		this.defaultInputConfiguration.registerKey(GLFW_KEY_Q);
		this.defaultInputConfiguration.registerKey(GLFW_KEY_E);
		this.defaultInputConfiguration.registerMouseButton(2);
		
		this.engine.getInput().pushInputConfiguration(this.defaultInputConfiguration);
		
		this.shader = new TestShader();
		this.shader.compile();
		
		loadWorldResources();
		
		loadWorld();
	}
	
	public void loadWorldResources() throws Exception
	{
		this.mipmapSettings = new MipmapSettings();
		this.mipmapSettings.lodBias(0.5f);
		this.mipmapSettings.maxMipmapLevels(16);
		this.mipmapSettings.anisotropicAmount(16.0f);
		
		Texture2D texture = TextureUtils.genTextureWithMipmaps(TextureLoader.loadResource(URLUtils.getEmbededResourceURL("barrel.png")), this.mipmapSettings);
		Texture2D textureNormal = TextureUtils.genTextureWithMipmaps(TextureLoader.loadResource(URLUtils.getEmbededResourceURL("barrel_normal.png")), this.mipmapSettings);

		ModelData modelData = this.loader.loadModel(URLUtils.getEmbededResourceURL("barrel.obj"));
		this.model = Utils.createModel(modelData);
		this.model.getMaterials().add(new Material(texture, textureNormal));
	}
	
	public void loadWorld() throws Exception
	{
		this.projectionMatrix = Mat4f.perspective(45.0f * Maths.DEG_TO_RADf, (float)this.width / (float)this.height, 0.1f, 1000.0f);
		this.projectionMatrix.mul(Mat4f.scaling3d(1.0f, 1.0f, -1.0f));
		
		this.player = new Player();
		
		this.entities = new ArrayList<>();
		
		StaticEntityModel entity = new StaticEntityModel(this.model);
		entity.setPos(0.0f, 0.0f, 20.0f).setScale(1.0f);
		this.entities.add(entity);
		
		entity = new StaticEntityModel(this.model);
		entity.setPos(0.0f, 30.0f, 10.0f).setScale(0.25f);
		this.entities.add(entity);
		
		entity = new StaticEntityModel(this.model);
		entity.setPos(30.0f, 0.0f, 0.0f).setScale(0.1f);
		this.entities.add(entity);
		
		//getEngine().getMouse().setMousePos(width / 2.0, height / 2.0);
		//getEngine().getMouse().grab(true);
	}
	
	public void preTick(TickInfo tick)
	{
		if(this.engine.getInput().isKeyPressed(GLFW_KEY_ESCAPE)) this.engine.stop();
		
		this.player.preTick(tick);
	}

	int frames;
	RepeatingTickTimer frameCountUpdater = new RepeatingTickTimer(30, () -> {
		this.engine.getWindow().setTitle(this.title + " FPS:" + (frames * 30));
	}).setEnabled(true);
	
	public void tick(TickInfo tick)
	{
		this.player.tick(tick);
		
		this.frameCountUpdater.tick();
		
		frames = 0;
	}
	
	public void preRender(TickInfo tick)
	{
		this.engine.getWindow().setViewport();
			
		GLCom.clearAll(0.0f, 0.0f, 1.0f, 1.0f);

		this.viewMatrix = this.player.toMatrix((float)tick.partialTime);
	}

	public void render(TickInfo tick)
	{	
		GL11.glEnable(GL11.GL_DEPTH_TEST);
		
		this.shader.start();

		for(StaticEntityModel entity : this.entities)
		{
			Mat4fC modelMatrix = entity.getTransform().getAbsoluteTransformationMatrix(1.0f);
			
			for(int i = 0; i < entity.getModel().getMeshes().size(); i++)
			{
				Mesh mesh = this.model.getMeshes().get(i);
				Material material = this.model.getMaterials().get(i);
				
				mesh.getVao().bind();
				
				GL20.glEnableVertexAttribArray(0);
				GL20.glEnableVertexAttribArray(1);
		
				this.shader.textureDiffuse.set(material.getDiffuse().get());
				this.shader.matrixProjection.set(this.projectionMatrix);
				this.shader.matrixView.set(this.viewMatrix);
				this.shader.matrixModel.set(modelMatrix);
				
				GL11.glDrawElements(GL11.GL_TRIANGLES, mesh.getVertexCount(), GL11.GL_UNSIGNED_INT, 0);
			}
		}
		
		this.shader.stop();
	}
	
	public void postRender(TickInfo tick)
	{
		frames++;
	}
	
	public void release()
	{
		this.model.release();
		
		this.shader.release();

		this.fallbackTexture.release();
	}
	
	public void settingsChange()
	{
		this.mipmapSettings.maxMipmapLevels(this.settings.getMipmapLevels());
		this.mipmapSettings.anisotropicAmount(this.settings.getAnisotropicLevels());

		for(Material material : this.model.getMaterials())
		{
			material.getDiffuse().get().createMipmaps(this.mipmapSettings);
			material.getNormal().get().createMipmaps(this.mipmapSettings);
		}
	}
	
	public Engine getEngine()
	{
		return this.engine;
	}
	
	public Input getInput()
	{
		return this.engine.getInput();
	}
	
	public Mouse getMouse()
	{
		return this.engine.getMouse();		
	}
}
