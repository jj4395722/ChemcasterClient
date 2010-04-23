package com.chemcaster.client.examples;

import com.chemcaster.client.ClientException;
import com.chemcaster.client.SimpleRegistry;

public class URIRegistry {

	public static void main(String[] args) {
		String username = "";
		String password = "";
		// connect to a registry based on its URI
		String URI = "https://chemcaster.com/registries/8675309"; // insert your uri here
		try {
			new SimpleRegistry(URI, username, password);
		} catch (ClientException ce) {
			System.err.println("Registry retrieval based on URI failed.");
			System.err.println("Did you set the URI correctly in URIRegistry.java?");
			System.err.println(ce);
			System.exit(42);
		}
		System.out.println("Success!");
	}
}
