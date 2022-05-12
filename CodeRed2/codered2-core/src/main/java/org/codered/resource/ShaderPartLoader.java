package org.codered.resource;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import java.net.URL;

public class ShaderPartLoader
{
	public static ShaderPartData loadResource(URL url) throws IOException
	{
		try(InputStream stream = url.openStream())
		{
			return loadResource(stream);
		}
	}
	
	public static ShaderPartData loadResource(InputStream stream) throws IOException
	{
		StringBuilder shaderSource = new StringBuilder();
		
		String line = "";
		BufferedReader reader = new BufferedReader(new InputStreamReader(stream));

		try
		{
			while((line = reader.readLine()) != null)
			{
				shaderSource.append(line).append("\n");					
			}
		}
		catch (IOException e)
		{
			e.printStackTrace();
		}

		return new ShaderPartData(shaderSource.toString());
	}
}
