package myhealthylife.dataservice.document.client;

import java.net.URL;
import java.util.List;
import java.util.UUID;

import javax.xml.namespace.QName;
import javax.xml.ws.Service;

import myhealthylife.dataservice.model.People;
import myhealthylife.dataservice.model.entities.HealthProfile;
import myhealthylife.dataservice.model.entities.Person;
import myhealthylife.dataservice.soap.DataService;
import myhealthylife.dataservice.soap.DataServiceImpl;

public class TestClient {
	  public static void main(String[] args) throws Exception {
	        URL url = new URL("http://127.0.1.1:6902/ws/data_service?wsdl");
	        //1st argument service URI, refer to wsdl document above
	        //2nd argument is service name, refer to wsdl document above
	        QName qname = new QName("http://soap.dataservice.myhealthylife/", "DataService");
	        Service service = Service.create(url, qname);

	        DataService ds = service.getPort(DataService.class);
	        
	        Person p1=new Person();
	        p1.setLastname("Bitta");
	        p1.setFirstname("Paolo");
	        p1.setUsername("pbitta");
	        
	        p1=ds.register(p1);
	        
	        System.out.println(p1.getIdPerson());
	    }
}
