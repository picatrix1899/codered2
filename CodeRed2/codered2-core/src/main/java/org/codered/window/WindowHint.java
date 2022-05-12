package org.codered.window;

import static org.lwjgl.glfw.GLFW.*;

public class WindowHint
{
	public static void resizable(boolean resizable)
	{
		glfwWindowHint(GLFW_RESIZABLE, resizable ? GLFW_TRUE : GLFW_FALSE);
	}
	
	public static void glVersion(int major, int minor, GLProfile profile)
	{
		glfwWindowHint(GLFW_CONTEXT_VERSION_MAJOR, major);
		glfwWindowHint(GLFW_CONTEXT_VERSION_MINOR, minor);
		
		int p = 0;
		switch(profile)
		{
			case ANY:
				p = GLFW_OPENGL_ANY_PROFILE;
				break;
			case COMPAT:
				p = GLFW_OPENGL_COMPAT_PROFILE;
				break;
			case CORE:
				p = GLFW_OPENGL_CORE_PROFILE;
				break;
		}
		
		glfwWindowHint(GLFW_OPENGL_PROFILE, p);
	}
	
	public static void glVersion(String version)
	{
		String[] parts = version.split(" ");
		String[] versionParts = parts[0].split("\\.");
		
		glfwWindowHint(GLFW_CONTEXT_VERSION_MAJOR, Integer.parseInt(versionParts[0]));
		glfwWindowHint(GLFW_CONTEXT_VERSION_MINOR, Integer.parseInt(versionParts[1]));
		
		int profile = GLFW_OPENGL_ANY_PROFILE;
		if(parts.length > 1)
		{
			String profileText = parts[1].trim();
		
			if(profileText.equalsIgnoreCase("core"))
			{
				profile = GLFW_OPENGL_CORE_PROFILE;
			}
			else if(profileText.equalsIgnoreCase("compat"))
			{
				profile = GLFW_OPENGL_COMPAT_PROFILE;
			}
		}
		
		glfwWindowHint(GLFW_OPENGL_PROFILE, profile);
	}
	
	public static void depthBits(int bits)
	{
		glfwWindowHint(GLFW_DEPTH_BITS, bits);
	}
	
	public static void samples(int samples)
	{
		glfwWindowHint(GLFW_SAMPLES, samples);
	}
	
	public static void doubleBuffering(boolean doublebuffering)
	{
		glfwWindowHint(GLFW_DOUBLEBUFFER, doublebuffering ? GLFW_TRUE : GLFW_FALSE);
	}
	
	public static void startVisible(boolean auto)
	{
		glfwWindowHint(GLFW_VISIBLE, auto ? GLFW_TRUE : GLFW_FALSE);
	}
	
	public static enum GLProfile
	{
		ANY,
		COMPAT,
		CORE
	}
}
