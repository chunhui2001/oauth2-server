package com.strategicgains.oauth;

import java.io.FileNotFoundException;
import java.io.IOException;

import org.restexpress.util.Environment;

public class Main
{
	public static void main(String[] args) throws Exception
	{
		OAuth2Server server = newServer(args).start();
		server.awaitShutdown();
	}

	public static OAuth2Server newServer(String[] args)
	throws FileNotFoundException, IOException
    {
		Configuration config = Environment.load(args, Configuration.class);
		return new OAuth2Server(config);
    }
}
