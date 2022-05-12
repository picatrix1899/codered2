package org.codered.resource;

import java.awt.image.BufferedImage;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;

import javax.imageio.ImageIO;

public class TextureLoader
{
	public static TextureData loadResource(URL url) throws IOException
	{
		try(InputStream stream = url.openStream())
		{
			return loadResource(stream);
		}
	}

	public static TextureData loadResource(InputStream stream) throws IOException
	{
		return loadResource(ImageIO.read(stream));
	}
	
	private static TextureData loadResource(BufferedImage image) throws IOException
	{	
		int[] pixels = new int[image.getWidth() * image.getHeight()];
		image.getRGB(0, 0, image.getWidth(), image.getHeight(), pixels, 0, image.getWidth());

		TextureData data = new TextureData();
		data.pixels = pixels;
		data.width = image.getWidth();
		data.height = image.getHeight();
		
		return data;
	}
}
