package myhealthylife.dataservice.model.util;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.WebTarget;

import org.glassfish.jersey.client.ClientConfig;

public class ServicesLocator {
	public static WebTarget getWeatherService(){
		ClientConfig clientConfig=new ClientConfig();
		Client client=ClientBuilder.newClient(clientConfig);
		return client.target("http://api.openweathermap.org");
	}
}
