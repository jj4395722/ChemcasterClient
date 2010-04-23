package com.chemcaster.client.examples;

import com.chemcaster.client.ClientException;
import com.chemcaster.client.SimpleService;

public class ServiceConnect {

	public static void main(String[] args) {
		// connect to Chemcaster using the standard URL
		String username = "";
		String password = "";
		try {
			 new SimpleService(username, password);
		} catch (ClientException ce) {
			System.err.println("Connection to the default service failed.");
			System.err.println(ce);
			System.exit(42);
		}
		System.out.println("Success!");
	}

}
