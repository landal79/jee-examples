package org.landal.jee.jaxws.provider;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;

import javax.xml.ws.BindingProvider;

import org.landal.jee.jaxws.client.HelloPortType;
import org.landal.jee.jaxws.client.HelloService;

public class HelloServiceProvider {

	private static final String WSDL_URL = "http://localhost:8080/HelloService?wsdl";	

	private static HelloServiceProvider INSTANCE = null;

	public static synchronized HelloServiceProvider getInstance() {
		if (INSTANCE == null) {
			INSTANCE = new HelloServiceProvider();
		}
		return INSTANCE;
	}

	private HelloService service;

	private HelloServiceProvider() {				
		try {
			service = new HelloService(new URL(WSDL_URL));
		} catch (MalformedURLException e) {
			throw new RuntimeException();
		}
	}
	
	
	public HelloPortType getHelloPort(String location) {
		return getHelloPort(location, null, 0);
	}

	public HelloPortType getHelloPort(String location,
			List<String> timeOutKeys, int timeout) {
		HelloPortType managementSoap = service.getHelloPort();

		BindingProvider bp = (BindingProvider) managementSoap;
		bp.getRequestContext().put(BindingProvider.ENDPOINT_ADDRESS_PROPERTY,
				location);

		// this is for timeout
		if (timeOutKeys != null) {
			for (String key : timeOutKeys) {
				bp.getRequestContext().put(key, timeout);
			}

		}

		return managementSoap;
	}



}
