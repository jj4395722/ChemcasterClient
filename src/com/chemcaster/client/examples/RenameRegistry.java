package com.chemcaster.client.examples;

import com.chemcaster.client.ClientException;
import com.chemcaster.client.NameURI;
import com.chemcaster.client.SimpleRegistry;
import com.chemcaster.client.SimpleService;

public class RenameRegistry {

	public static void main(String[] args) {
		String username = "";
		String password = "";
		// rename the first registry to "FIPCO Warehouse"
		String newName = "FIPCO Warehouse";
		try {
			SimpleService service = new SimpleService(username, password);
			NameURI services = service.getRegistries();
			SimpleRegistry  registry = service.getSimpleRegistryWithName(services.getName(0));
			registry.setName(newName);
		} catch (ClientException ce) {
			System.err.println("Registry connection or rename failed.");
			System.err.println(ce);
			System.exit(42);
		}
		System.out.println("Success!");
	}
}
