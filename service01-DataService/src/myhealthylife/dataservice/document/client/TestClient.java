package myhealthylife.dataservice.document.client;

import java.net.URL;
import java.util.List;
import java.util.UUID;

import javax.xml.namespace.QName;
import javax.xml.ws.Service;

import myhealthylife.dataservice.model.MeasureTypes;
import myhealthylife.dataservice.model.People;
import myhealthylife.dataservice.model.entities.HealthProfile;
import myhealthylife.dataservice.model.entities.Measure;
import myhealthylife.dataservice.model.entities.Person;
import myhealthylife.dataservice.soap.DataService;
import myhealthylife.dataservice.soap.DataServiceImpl;

public class TestClient {
	  public static void main(String[] args) throws Exception {
	        URL url = new URL("http://127.0.1.1:6902/ws/data_service?wsdl");
		  	//URL url = new URL("https://service01-dataservice.herokuapp.com/ws/data_service?wsdl");
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
	        
	        System.out.println("personid: "+p1.getIdPerson());
	        
	        Measure m=new Measure();
	        m.setMeasureType(MeasureTypes.steps.toString());
	        m.setMeasureValue(150);
	        
	        m=ds.saveMeasure(p1.getIdPerson(), m);
	        System.out.println("measure id: "+m.getMid());
	        System.out.println("measure date: "+m.getDateRegistered());
	        
	        System.out.println(ds.getCurrentHealth(p1.getIdPerson()).getMeasure().size());
	    }
}
