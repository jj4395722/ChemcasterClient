package com.chemcaster.client.examples;

import com.chemcaster.client.ClientException;
import com.chemcaster.client.SimpleRegistry;
import com.chemcaster.client.SimpleService;

public class CreateRegistry {

	public static void main(String[] args) {
		String username = "";
		String password = "";
		String registryName = "FIPCO Stockroom";
		Boolean deletable = false;
		try {
			SimpleService service = new SimpleService(username, password);
			new SimpleRegistry(service, registryName, deletable);
		} catch (ClientException ce) {
			System.err.println("Registry creation failed. Possible duplicate");
			System.err.println("name or max registry count exceeded.");
			System.err.println(ce);
			System.exit(42);
		}
		System.out.println("Success!");
	}
}
