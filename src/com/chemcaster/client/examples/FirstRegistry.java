package com.chemcaster.client.examples;

import com.chemcaster.client.ClientException;
import com.chemcaster.client.NameURI;
import com.chemcaster.client.SimpleService;

public class FirstRegistry {

	public static void main(String[] args) {
		// get the first available service
		String username = "";
		String password = "";
		// get the first available service
		try {
			SimpleService service = new SimpleService(username, password);
			NameURI services = service.getRegistries();
			if (services.size() > 0) {
				service.getSimpleRegistryWithName(services
						.getName(0));
			}
		} catch (ClientException ce) {
			System.err
					.println("Either service connection or registry retrieval failed.");
			System.err.println(ce);
			System.exit(42);
		}
		System.out.println("Success!");
	}
}
