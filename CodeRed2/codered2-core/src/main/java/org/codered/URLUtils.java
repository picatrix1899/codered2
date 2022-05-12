package org.codered;

import java.net.URL;

public class URLUtils
{
	public static URL getEmbededResourceURL(String path)
	{
		return URLUtils.class.getResource("/" + path);
	}
}
